package org.example.managehttp.controller.lnm.v1.ledger;


import com.google.protobuf.Descriptors;
import org.example.managehttp.pojo.ReadLedger.ReadLedgerRequest;
import org.example.managehttp.pojo.ReadLedger.ReadLedgerResponse;
import org.example.managehttp.pojo.WriteLedger.WriteLedgerRequest;
import org.example.managehttp.pojo.WriteLedger.WriteLedgerResponse;
import org.example.managehttp.service.ProtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private ProtoService protoService;

    @PostMapping("/write")
    public WriteLedgerResponse writeLedger(@RequestBody @Validated WriteLedgerRequest writeLedgerRequest) throws Descriptors.DescriptorValidationException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        return protoService.writeLedger(writeLedgerRequest);
    }

    @PostMapping("/read")
    public ReadLedgerResponse readLedger(@RequestBody @Validated ReadLedgerRequest readLedgerRequest) throws Descriptors.DescriptorValidationException, IOException {
        ReadLedgerResponse response = new ReadLedgerResponse();
        response.setDataList(protoService.readLedger(readLedgerRequest));
        return response;
    }
}
