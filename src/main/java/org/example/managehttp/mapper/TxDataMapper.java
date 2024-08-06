package org.example.managehttp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.managehttp.pojo.Tx.TxData;

import java.util.List;

@Mapper
public interface TxDataMapper {

    /**
     * 查询最大高度
     *
     * @return height
     */
    Long getMaxHeight();


    /**
     * 插入交易数据
     * @param 交易数据: txData
     */
    void insertData(TxData txData);

    /**
     * @param 协议号: uri
     * @return List<String> payload
     */
    List<String> getPayloadByUri(String uri);
}
