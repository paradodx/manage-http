package org.example.managehttp.mapper;

import org.example.managehttp.pojo.Tx.TxData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TxDataMapperTest {

    @Autowired
    private TxDataMapper txDataMapper;
    @Test
    void getHeightTest() {
        Long height = txDataMapper.getMaxHeight();
        if (height != null) {
            System.out.println(height);
        }else {
            System.out.println("no value");
        }
    }
    
    @Test
    public void insertTest() {
        TxData txData = new TxData();
        txData.setChainId(1);
        txData.setUri("123456");
        txData.setHeight(1L);
        txData.setOwner("zzzz");
        txData.setLinker("xxxx");
        txData.setPrefixCode("1234");
        txData.setHash("1234");
        txData.setData("asdasd");
        txData.setVersion(1);
        txData.setTimestamp(1L);
        txDataMapper.insertData(txData);
    }
    
    @Test
    public void getPayloadTest(){
        List<String> payloads = txDataMapper.getPayloadByUri("42949672961");
        for (String payload : payloads) {
            System.out.println(payload);
        }
    }
}