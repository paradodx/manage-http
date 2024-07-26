package org.example.managehttp.service.Impl;

import cn.aheca.common.gm.SM4Util;
import com.esaulpaugh.headlong.abi.*;
import com.esaulpaugh.headlong.util.FastHex;
import com.example.abi.LatticeAbi;
import com.example.abi.LatticeAbiKt;
import com.example.abi.LatticeFunction;
import com.example.abi.LatticeFunctionKt;
import com.example.lattice.*;
import com.example.lattice.model.Transaction;
import com.example.model.Address;
import com.example.model.AddressKt;
import com.example.model.EthereumAddress;
import com.example.model.block.CurrentTDBlock;
import com.example.model.block.Receipt;
import com.example.model.block.SendTBlock;
import com.example.model.block.TBlock;
import com.example.model.extension.ByteArrayKt;

import com.google.protobuf.Descriptors;
import org.bouncycastle.util.encoders.Hex;
import org.example.managehttp.factory.DynamicMsgFactory;
import org.example.managehttp.factory.LatticeFactory;
import org.example.managehttp.factory.SignatureDataFactory;
import org.example.managehttp.pojo.CreateProtocol.CreateProtocolRequest;
import org.example.managehttp.pojo.CreateProtocol.CreateProtocolResponse;


import org.example.managehttp.pojo.ReadLedger.LedgerData;
import org.example.managehttp.pojo.ReadLedger.ReadLedgerRequest;
import org.example.managehttp.pojo.ReadProtocol.Protocol;
import org.example.managehttp.pojo.ReadProtocol.ReadProtocolRequest;
import org.example.managehttp.pojo.UpdateProtocol.UpdateProtocolRequest;
import org.example.managehttp.pojo.UpdateProtocol.UpdateProtocolResponse;
import org.example.managehttp.pojo.WriteLedger.WriteLedgerRequest;
import org.example.managehttp.pojo.WriteLedger.WriteLedgerResponse;
import org.example.managehttp.utils.*;
import org.example.managehttp.service.ProtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ProtoServiceImpl implements ProtoService {

    @Autowired
    private LatticeProperties latticeProperties;
    @Autowired
    private LatticeFactory latticeFactory;
    @Autowired
    private SignatureDataFactory signatureDataFactory;
    @Autowired
    private ConvertUtils convert;
    @Autowired
    private DynamicMsgFactory dynamicMsgFactory;


    // 容量/过期时间 -> 内存泄漏
    private static final Map<Integer, String> payloadMap = new ConcurrentHashMap<>();
    private static final AtomicInteger index = new AtomicInteger(0);
    
    @Override
    public CreateProtocolResponse createProtocol(CreateProtocolRequest createProtocolRequest) {
        // 构建Lattice
        ILattice lattice = latticeFactory.createLattice(createProtocolRequest.getChainId());

        // 获取address, 创建最新区块latestTBlock
        Address address = new Address(latticeProperties.getAccountAddressStr());
        CurrentTDBlock latestTBlock = lattice.getLatestTDBlockWithCatch(address);

        // 获取Abi, 新增协议: "addProtocol"
        LatticeAbi latticeAbi = new LatticeAbi(latticeProperties.getLedgerAbi());
        String method = "addProtocol";
        LatticeFunction latticeFunction = LatticeAbiKt.getFunction(latticeAbi, method);
        String[] bytes32Array = ByteArrayKt.toBytes32Array(createProtocolRequest.getMessage().getBytes());
        String code = LatticeFunctionKt.encode(latticeFunction,
                new Object[]{createProtocolRequest.getSuite(), bytes32Array});

        // 新建交易tx
        // Transaction tx = new Transaction();
        ExecuteTxBuilder builder = ExecuteTxBuilder.Companion.builder();
        Transaction tx = builder
                .setBlock(latestTBlock)
                .setCode(code)
                .setOwner(address)
                .setLinker(new Address("zltc_QLbz7JHiBTspUvTPzLHy5biDS9mu53mmv"))
                .refreshTimestamp()
                .build();

        signatureDataFactory.setSign(tx, createProtocolRequest.getChainId());

        // 返回交易hash
        CreateProtocolResponse response = new CreateProtocolResponse();
        String hash = lattice.sendRawTBlock(tx);
        response.setHash(hash);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 返回交易uri
        Receipt receipt = lattice.getReceipt(hash);
        List<Type<?>> outputs = LatticeFunctionKt.decodeReturn(latticeFunction,
                receipt.getContractRet());
        response.setUri(String.valueOf(outputs.get(0).getValue()));
        return response;
    }

    @Override
    public UpdateProtocolResponse updateProtocol(UpdateProtocolRequest updateProtocolRequest) {
        // 构建Lattice
        ILattice lattice = latticeFactory.createLattice(updateProtocolRequest.getChainId());

        // 获取address, 创建最新区块latestTBlock
        Address address = new Address(latticeProperties.getAccountAddressStr());
        CurrentTDBlock latestTBlock = lattice.getLatestTDBlockWithCatch(address);

        // 获取Abi, 更新协议: "updateProtocol"
        LatticeAbi latticeAbi = new LatticeAbi(latticeProperties.getLedgerAbi());
        String method = "updateProtocol";
        LatticeFunction latticeFunction = LatticeAbiKt.getFunction(latticeAbi, method);
        String[] bytes32Array = ByteArrayKt.toBytes32Array(updateProtocolRequest.getMessage().getBytes());
        String code = LatticeFunctionKt.encode(latticeFunction,
                new Object[]{updateProtocolRequest.getUri(), bytes32Array});

        ExecuteTxBuilder builder = ExecuteTxBuilder.Companion.builder();
        Transaction tx = builder
                .setBlock(latestTBlock)
                .setCode(code)
                .setOwner(address)
                .setLinker(new Address("zltc_QLbz7JHiBTspUvTPzLHy5biDS9mu53mmv"))
                .refreshTimestamp()
                .build();

        signatureDataFactory.setSign(tx, updateProtocolRequest.getChainId());
        UpdateProtocolResponse response = new UpdateProtocolResponse();
        response.setHash(lattice.sendRawTBlock(tx));
        return response;
    }

    @Override
    public List<Protocol> readProtocol(ReadProtocolRequest readProtocolRequest) {
        // 构建Lattice
        ILattice lattice = latticeFactory.createLattice(readProtocolRequest.getChainId());

        // 获取address, 创建最新区块latestTBlock
        Address address = new Address(latticeProperties.getAccountAddressStr());
        CurrentTDBlock latestTBlock = lattice.getLatestTDBlockWithCatch(address);

        // 获取Abi, 读取协议: "getAddress"
        LatticeAbi latticeAbi = new LatticeAbi(latticeProperties.getLedgerAbi());
        String method = "getAddress";
        LatticeFunction latticeFunction = LatticeAbiKt.getFunction(latticeAbi, method);
        String code = LatticeFunctionKt.encode(latticeFunction,
                new Object[]{readProtocolRequest.getUri()});

        ExecuteTxBuilder builder = ExecuteTxBuilder.Companion.builder();
        Transaction tx = builder
                .setBlock(latestTBlock)
                .setCode(code)
                .setOwner(address)
                .setLinker(new Address("zltc_QLbz7JHiBTspUvTPzLHy5biDS9mu53mmv"))
                .refreshTimestamp()
                .build();
        signatureDataFactory.setSign(tx, readProtocolRequest.getChainId());
        String hash = lattice.sendRawTBlock(tx);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Receipt receipt = lattice.getReceipt(hash);
        String result = receipt.getContractRet();

        TupleType<Tuple> tt = TupleType.parse("((address,bytes32[])[])");
        ByteBuffer buffer = ByteBuffer.wrap(FastHex.decode(result.substring(2)));
        Tuple[] outputs = tt.decode(buffer).get(0);
        List<Protocol> protocols = new ArrayList<>();

        for (int i = 0; i < outputs.length; i++) {
            Protocol protocol = new Protocol();
            EthereumAddress addr = new EthereumAddress((outputs[i].get(0)).toString());
            protocol.setUpdater(AddressKt.toAddress(addr, "01").toString());
            byte[][] bytes = outputs[i].get(1);
            protocol.setData(bytes);
            protocol.setMessage(convert.BytesArrayToString(bytes));
            protocol.setVersion(i);
            protocols.add(protocol);
        }

        return protocols;
    }

    @Override
    public WriteLedgerResponse writeLedger(WriteLedgerRequest writeLedgerRequest) throws Descriptors.DescriptorValidationException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        // 获取协议数据
        ReadProtocolRequest readProtocolRequest = new ReadProtocolRequest();
        readProtocolRequest.setChainId(writeLedgerRequest.getChainId());
        readProtocolRequest.setUri(writeLedgerRequest.getUri());
        List<Protocol> protocolList = readProtocol(readProtocolRequest);
        if (protocolList.size() <= writeLedgerRequest.getVersion())
            throw new RuntimeException("Protocol version does not exist, maybe it's a wrong version");

        // 生成proto文件, marshall
        String messageType = dynamicMsgFactory.generateProtoFile(protocolList.get(writeLedgerRequest.getVersion()).getMessage());
        byte[] data = dynamicMsgFactory.marshallMessageFromProto(messageType, writeLedgerRequest.getJsonData());

        // 生成key
        // byte[] key = SM4Utils.generateKey();
        byte[] key = SM4Util.generateKey();
        // encrypt
        byte[] cipher = CryptoUtils.sm4Encrypt(key, data);

        Address address = new Address(writeLedgerRequest.getBusinessAddress());
        String addr = AddressKt.toEthereumAddress(address);

        ILattice lattice = latticeFactory.createLattice(writeLedgerRequest.getChainId());
        Address accountAddr = new Address(latticeProperties.getAccountAddressStr());
        CurrentTDBlock latestTBlock = lattice.getLatestTDBlockWithCatch(accountAddr);

        // 获取Abi, 数据存证: "writeTraceability"
        LatticeAbi latticeAbi = new LatticeAbi(latticeProperties.getLedgerAbi());
        String method = "writeTraceability";
        LatticeFunction latticeFunction = LatticeAbiKt.getFunction(latticeAbi, method);
        String code = LatticeFunctionKt.encode(latticeFunction,
                new Object[]{writeLedgerRequest.getUri(),
                        writeLedgerRequest.getDataId(),
                        convert.LedgerToBytes32Array(cipher, writeLedgerRequest.getUri(), writeLedgerRequest.getVersion(), key),
                        addr});
        
        // SM2 encrypt payload
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        for (int i = 0; i < 8; i++) {
            byte[] cipherText = CryptoUtils.sm2Encrypt(key, TestCert.getBase64Cert(i));
            byteArray.write(cipherText);
        }
        String payload = ByteArrayKt.toHexString(byteArray.toByteArray());

        // 创建tx
        ExecuteTxBuilder builder = ExecuteTxBuilder.Companion.builder();
        Transaction tx = builder
                .setBlock(latestTBlock)
                .setCode(code)
                .setOwner(accountAddr)
                .setLinker(new Address("zltc_QLbz7JHiBTspUvTPzLHy5biDS9mu53mmv"))
                .refreshTimestamp()
                .setPayload(payload)
                .build();
        
        signatureDataFactory.setSign(tx, writeLedgerRequest.getChainId());

        // tx回执
        String hash = lattice.sendRawTBlock(tx);
        // payload
        int currentIndex = index.getAndIncrement();
        payloadMap.put(currentIndex, hash);
        
        WriteLedgerResponse response = new WriteLedgerResponse();
        response.setHash(hash);
        response.setDataId(writeLedgerRequest.getDataId());
        return response;
    }

    @Override
    public List<LedgerData> readLedger(ReadLedgerRequest readLedgerRequest) throws Descriptors.DescriptorValidationException, IOException {
        // 获取协议数据
        ReadProtocolRequest readProtocolRequest = new ReadProtocolRequest();
        readProtocolRequest.setChainId(readLedgerRequest.getChainId());
        readProtocolRequest.setUri(readLedgerRequest.getUri());
        List<Protocol> protocolList = readProtocol(readProtocolRequest);
        
        Address address = new Address(readLedgerRequest.getBusinessAddress());
        String addr = AddressKt.toEthereumAddress(address);

        // 获取Abi, 读取存证数据: "getTraceability"
        LatticeAbi latticeAbi = new LatticeAbi(latticeProperties.getLedgerAbi());
        String method = "getTraceability";
        LatticeFunction latticeFunction = LatticeAbiKt.getFunction(latticeAbi, method);
        String code = LatticeFunctionKt.encode(latticeFunction,
                new Object[]{readLedgerRequest.getDataId(), addr});
        
        ILattice lattice = latticeFactory.createLattice(readLedgerRequest.getChainId());
        Address accountAddr = new Address(latticeProperties.getAccountAddressStr());
        CurrentTDBlock latestTBlock = lattice.getLatestTDBlockWithCatch(accountAddr);

        ExecuteTxBuilder builder = ExecuteTxBuilder.Companion.builder();
        Transaction tx = builder
                .setBlock(latestTBlock)
                .setCode(code)
                .setOwner(accountAddr)
                .setLinker(new Address("zltc_QLbz7JHiBTspUvTPzLHy5biDS9mu53mmv"))
                .refreshTimestamp()
                .build();

        signatureDataFactory.setSign(tx, readLedgerRequest.getChainId());
        String hash = lattice.sendRawTBlock(tx);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Receipt receipt = lattice.getReceipt(hash);
        String result = receipt.getContractRet();

        TupleType<Tuple> tt = TupleType.parse("((uint64,uint64,address,bytes32[])[])");
        ByteBuffer buffer = ByteBuffer.wrap(FastHex.decode(result.substring(2)));
        Tuple[] outputs = tt.decode(buffer).get(0);
        List<LedgerData> dataLists = new ArrayList<>();
        for (int i = 0; i < outputs.length; i++) {
            LedgerData data = new LedgerData();
            // number
            data.setNumber((outputs[i].get(0)).toString());
            // protocol
            data.setUri(readLedgerRequest.getUri());
            // updater
            EthereumAddress updater = new EthereumAddress((outputs[i].get(2)).toString());
            data.setUpdater(AddressKt.toAddress(updater, "01").toString());
            // data
            byte[][] bytes = outputs[i].get(3);
            data.setData(bytes);
            byte[] bytesData = convert.toByteArray(bytes);
            bytesData = convert.removePaddingZeros(bytesData);
            // isEncrypt
            data.setIsEncrypted(convert.isEncrypted(bytesData));
            // timestamp
            data.setTimestamp(convert.timestamp(bytesData));
            // protocolVersion
            data.setProtocolVersion(convert.protocolVersion(bytesData));
            // jsonData
            String WriteHash = payloadMap.get(i);
            SendTBlock tBlock = lattice.getTBlock(WriteHash);
            String payload = tBlock.getPayload();
            
            byte[] SM2priKey = CryptoUtils.SM2Decrypt(payload);
            // bytesData - 18
            byte[] decodeData = new byte[bytesData.length - 18];
            System.arraycopy(bytesData, 18, decodeData, 0, decodeData.length);
            
            byte[] decryptData = CryptoUtils.sm4Decrypt(SM2priKey, decodeData);
            
            String messageType = dynamicMsgFactory.generateProtoFile(protocolList.get(data.getProtocolVersion()).getMessage());
            
            data.setJsonData(convert.dataToJson(messageType, decryptData));
            dataLists.add(data);
        }
        return dataLists;
    }
}
