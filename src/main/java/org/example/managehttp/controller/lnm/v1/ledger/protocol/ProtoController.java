package org.example.managehttp.controller.lnm.v1.ledger.protocol;


import org.example.managehttp.pojo.*;
import org.example.managehttp.service.ProtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/protocol")
public class ProtoController {
    @Autowired
    private ProtoService protoService;

    @PostMapping("/create")
    public CreateProtocolResponse createProtocol(@RequestBody @Validated CreateProtocolRequest createProtocolRequest){
        return protoService.createProtocol(createProtocolRequest);
    }

    @PostMapping("/update")
    public UpdateProtocolResponse updateProtocol(@RequestBody @Validated UpdateProtocolRequest updateProtocolRequest){
        return protoService.updateProtocol(updateProtocolRequest);
    }

    @PostMapping("/read")
    public List<Protocol> readProtocol(@RequestBody @Validated ReadProtocolRequest readProtocolRequest){
        return protoService.readProtocol(readProtocolRequest);
    }
}