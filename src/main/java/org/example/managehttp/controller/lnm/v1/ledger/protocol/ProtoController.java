package org.example.managehttp.controller.lnm.v1.ledger.protocol;


import org.example.managehttp.pojo.CreateProtocol.CreateProtocolRequest;
import org.example.managehttp.pojo.CreateProtocol.CreateProtocolResponse;
import org.example.managehttp.pojo.ReadProtocol.ReadProtocolRequest;
import org.example.managehttp.pojo.ReadProtocol.ReadProtocolResponse;
import org.example.managehttp.pojo.UpdateProtocol.UpdateProtocolRequest;
import org.example.managehttp.pojo.UpdateProtocol.UpdateProtocolResponse;
import org.example.managehttp.service.ProtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger/protocol")
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
    public ReadProtocolResponse readProtocol(@RequestBody @Validated ReadProtocolRequest readProtocolRequest){
        ReadProtocolResponse response = new ReadProtocolResponse();
        response.setProtocol(protoService.readProtocol(readProtocolRequest));
        return response;
    }
}