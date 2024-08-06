package org.example.managehttp.scheduler;


import com.example.lattice.ILattice;
import com.example.model.block.Receipt;
import com.example.model.block.TBlock;
import com.example.model.block.TBlockPages;
import com.google.protobuf.Descriptors;
import com.zkjg.lattice.abi.Function;
import com.zkjg.lattice.abi.Tuple;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.util.encoders.Hex;
import org.example.managehttp.factory.DynamicMsgFactory;
import org.example.managehttp.factory.LatticeFactory;
import org.example.managehttp.mapper.TxDataMapper;
import org.example.managehttp.pojo.ReadProtocol.Protocol;
import org.example.managehttp.pojo.ReadProtocol.ReadProtocolRequest;
import org.example.managehttp.pojo.Tx.TxData;
import org.example.managehttp.service.ProtoService;
import org.example.managehttp.utils.ConvertUtils;
import org.example.managehttp.utils.CryptoUtils;
import org.example.managehttp.utils.FormatUtils;
import org.example.managehttp.utils.LatticeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class WriteTxScheduler {
    @Autowired
    private LatticeProperties latticeProperties;
    @Autowired
    private LatticeFactory latticeFactory;
    @Autowired
    private ConvertUtils convertUtils;
    @Autowired
    private ProtoService protoService;
    @Autowired
    private DynamicMsgFactory dynamicMsgFactory;
    @Autowired
    private TxDataMapper txDataMapper;

    private final Integer index = 100;
    private Long currentHeight;
    private final Lock lock = new ReentrantLock();
    
    @PostConstruct
    public void init() {
        currentHeight = txDataMapper.getMaxHeight() == null ? 1 : txDataMapper.getMaxHeight();
    }
    
    // 每隔5s读取一次TBlockPages, start: height, end: index
    @Scheduled(fixedRate = 5000)
    public void TxSchedule() throws Descriptors.DescriptorValidationException, IOException {
        ILattice lattice = latticeFactory.createLattice(latticeProperties.getChainId());
        // 从db获取currentHeight, 获取失败时当前高度为1, 如果currentHeight >
        TBlockPages pages = lattice.getTBlockPages(currentHeight, index);
        
        lock.lock();
        try {
            // height
            currentHeight = pages.getHeight();
        } finally {
            lock.unlock();
        }
        System.out.println(currentHeight);

        List<TBlock> tBlocks = pages.getTBlocks();
        List<TBlock> filterBlocks = new ArrayList<>();
        for (TBlock block : tBlocks) {
            // 筛选WriteLedgerTx: 0x4131ff53
            String code = block.getCode();
            if (code != null && !code.isEmpty()) {
                if (code.startsWith("4131ff53", 2)) {
                    filterBlocks.add(block);
                }
            }
        }
        // TBlock中get: chain_id, uri, owner, linker, hash, payload, version, timestamp
        for (TBlock block : filterBlocks) {
            String owner = block.getOwner();
            String linker = block.getLinker();
            String code = block.getCode();
            String payload = block.getPayload();
            String hash = block.getHash();
            String prefixCode = code.substring(2, 10);
            // chainId
            int chainId = latticeProperties.getChainId();
            
            byte[] src;
            String uri;
            Long timestamp;
            int version;
            String data;
            src = decode(code);
            // uri
            uri = getUri(src);
            timestamp = convertUtils.timestamp(src);
            // version
            version = convertUtils.protocolVersion(src);
            // data
            data = decodeData(payload, src, uri, version);
            
            // 数据入库
            TxData txData = new TxData();
            txData.setChainId(chainId);
            txData.setUri(uri);
            txData.setHeight(currentHeight);
            txData.setOwner(owner);
            txData.setLinker(linker);
            txData.setPrefixCode(prefixCode);
            txData.setHash(hash);
            txData.setPayload(payload);
            txData.setData(data);
            txData.setVersion(version);
            txData.setTimestamp(timestamp);
            lock.lock();
            try {
                txDataMapper.insertData(txData);
            } finally {
                lock.unlock();
            }
        }
    }

    public byte[] decode(String code) {
        code = FormatUtils.removeHexPrefix(code);
        Function function = new Function("writeTraceability(uint64,string,bytes32[],address)");
        Tuple tuple = function.decodeCall(Hex.decode(code));
        byte[][] data = tuple.get(2);
        return FormatUtils.removePaddingZeros(FormatUtils.toByteArray(data));
    }

    public String getUri(byte[] data) {
        byte[] uriBytes = new byte[8];
        System.arraycopy(data, 2, uriBytes, 0, 8);
        return Long.toString(ByteBuffer.wrap(uriBytes).order(ByteOrder.BIG_ENDIAN).getLong());
    }

    public String decodeData(String payload, byte[] src, String uri, int version) throws Descriptors.DescriptorValidationException, IOException {
        byte[] SM2priKey = CryptoUtils.SM2Decrypt(payload);
        byte[] decodeData = new byte[src.length - 18];
        System.arraycopy(src, 18, decodeData, 0, decodeData.length);
        byte[] decryptData = CryptoUtils.sm4Decrypt(SM2priKey, decodeData);

        ReadProtocolRequest readProtocolRequest = new ReadProtocolRequest();
        readProtocolRequest.setChainId(latticeProperties.getChainId());
        readProtocolRequest.setUri(uri);
        List<Protocol> protocolList = protoService.readProtocol(readProtocolRequest);

        return convertUtils.dataToJson(protocolList.get(version).getMessage(), decryptData);
    }

    
}
