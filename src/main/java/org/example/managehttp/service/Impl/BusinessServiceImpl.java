package org.example.managehttp.service.Impl;


import com.example.lattice.ExecuteTxBuilder;
import com.example.lattice.ILattice;
import com.example.lattice.model.Transaction;
import com.example.model.*;
import com.example.model.block.CurrentTDBlock;
import com.example.model.extension.ByteArrayKt;
import org.example.managehttp.factory.LatticeFactory;
import org.example.managehttp.factory.SignatureDataFactory;
import org.example.managehttp.pojo.CreateBusiness.CreateBusinessRequest;
import org.example.managehttp.pojo.CreateBusiness.CreateBusinessResponse;
import org.example.managehttp.service.BusinessService;
import org.example.managehttp.utils.LatticeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private LatticeProperties latticeProperties;
    @Autowired
    private LatticeFactory latticeFactory;
    @Autowired
    private SignatureDataFactory signatureDataFactory;

    @Override
    public CreateBusinessResponse createBusiness(CreateBusinessRequest createBusinessRequest) {
        // 构建Lattice
        ILattice lattice = latticeFactory.createLattice(createBusinessRequest.getChainId());

        // 获取address, 创建最新区块latestTBlock
        Address address = new Address(latticeProperties.getAccountAddressStr());
        CurrentTDBlock latestTBlock = lattice.getLatestTDBlockWithCatch(address);
        String code = ByteArrayKt.toHexString(new byte[]{49});
        // 新建tx
        ExecuteTxBuilder builder = ExecuteTxBuilder.Companion.builder();
        Transaction tx = builder
                .setBlock(latestTBlock)
                .setCode(code)
                .setOwner(address)
                .setLinker(new Address("zltc_QLbz7JHiBTspS9WTWJUrbNsB5wbENMweQ"))
                .refreshTimestamp()
                .build();

        signatureDataFactory.setSign(tx, createBusinessRequest.getChainId());
        // 创建业务
        CreateBusinessResponse response = new CreateBusinessResponse();
        // 业务hash
        String hash = lattice.sendRawTBlock(tx);
        response.setHash(hash);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 业务地址
        EthereumAddress ethAddress = new EthereumAddress(lattice.getReceipt(hash).getContractRet());
        String addr = AddressKt.toAddress(ethAddress, "01").getHex();
        response.setAddress(addr);
        return response;
    }
}
