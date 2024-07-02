package org.example.managehttp.service;

import org.example.managehttp.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProtoService {

    // 创建协议
    CreateProtocolResponse createProtocol(CreateProtocolRequest createProtocolRequest);

    // 更新协议
    UpdateProtocolResponse updateProtocol(UpdateProtocolRequest updateProtocolRequest);

    // 读取协议
    List<Protocol> readProtocol(ReadProtocolRequest readProtocolRequest);
}
