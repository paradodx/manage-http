package org.example.managehttp.pojo.ReadProtocol;

import lombok.Data;

import java.util.List;

/*
* 读取协议响应数据
* */

@Data
public class ReadProtocolResponse {

    private List<Protocol> protocol;
}
