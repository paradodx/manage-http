package org.example.managehttp.service;

import org.example.managehttp.pojo.CreateProtocolRequest;
import org.example.managehttp.pojo.CreateProtocolResponse;
import org.example.managehttp.pojo.UpdateProtocolRequest;
import org.example.managehttp.pojo.UpdateProtocolResponse;
import org.springframework.stereotype.Service;

@Service
public interface protoService {

    // 创建协议
    CreateProtocolResponse createProtocol(CreateProtocolRequest createProtocolRequest);

    // 更新协议
    UpdateProtocolResponse updateProtocol(UpdateProtocolRequest updateProtocolRequest);
}
