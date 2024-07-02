package org.example.managehttp.service.Impl;

import com.example.abi.LatticeAbi;
import com.example.abi.LatticeAbiKt;
import com.example.abi.LatticeFunction;
import com.example.abi.LatticeFunctionKt;
import com.example.lattice.*;
import com.example.lattice.model.Transaction;
import com.example.model.Address;
import com.example.model.block.CurrentTDBlock;
import com.example.model.block.Receipt;
import com.example.model.extension.ByteArrayKt;

import org.example.managehttp.factory.LatticeFactory;
import org.example.managehttp.factory.SignatureDataFactory;
import org.example.managehttp.pojo.*;
import org.example.managehttp.utils.LatticeProperties;
import org.example.managehttp.service.ProtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProtoServiceImpl implements ProtoService {

    @Autowired
    private LatticeProperties latticeProperties;
    @Autowired
    private LatticeFactory latticeFactory;
    @Autowired
    private SignatureDataFactory signatureDataFactory;

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
        List<org.web3j.abi.datatypes.Type<?>> outputs = LatticeFunctionKt.decodeReturn(latticeFunction,
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



        /*List<Protocol> contents = new ArrayList<>();
        contents.add();*/
        return null;
    }

}
