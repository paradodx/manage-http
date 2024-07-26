package org.example.managehttp.service;

import com.google.protobuf.Descriptors;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

@Service
public interface ProtoService {

    // 创建协议
    CreateProtocolResponse createProtocol(CreateProtocolRequest createProtocolRequest);

    // 更新协议
    UpdateProtocolResponse updateProtocol(UpdateProtocolRequest updateProtocolRequest);

    // 读取协议
    List<Protocol> readProtocol(ReadProtocolRequest readProtocolRequest);

    // 数据存证
    WriteLedgerResponse writeLedger(WriteLedgerRequest writeLedgerRequest) throws IOException, Descriptors.DescriptorValidationException, NoSuchAlgorithmException, NoSuchProviderException;

    // 读取存证数据
    List<LedgerData> readLedger(ReadLedgerRequest readLedgerRequest) throws IOException, Descriptors.DescriptorValidationException;
}
