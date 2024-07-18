package org.example.managehttp.controller.lnm.v1.ledger.business;


import org.example.managehttp.pojo.CreateBusiness.CreateBusinessRequest;
import org.example.managehttp.pojo.CreateBusiness.CreateBusinessResponse;
import org.example.managehttp.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ledger/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("/create")
    public CreateBusinessResponse createBusiness(@RequestBody @Validated CreateBusinessRequest createBusinessRequest){
        System.out.println("business");
        return businessService.createBusiness(createBusinessRequest);
    }

}
