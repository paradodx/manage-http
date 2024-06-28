package org.example.managehttp.service.Impl;

import org.example.managehttp.utils.LatticeProperties;
import org.example.managehttp.pojo.CreateProtocolRequest;
import org.example.managehttp.pojo.CreateProtocolResponse;
import org.example.managehttp.pojo.UpdateProtocolRequest;
import org.example.managehttp.pojo.UpdateProtocolResponse;
import org.example.managehttp.service.protoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtoServiceImpl implements protoService {

    @Autowired
    private LatticeProperties latticeConfig;


    @Override
    public CreateProtocolResponse createProtocol(CreateProtocolRequest createProtocolRequest) {

        return null;
    }

    @Override
    public UpdateProtocolResponse updateProtocol(UpdateProtocolRequest updateProtocolRequest) {
        return null;
    }
}
