package org.example.managehttp.service;


import org.example.managehttp.pojo.CreateBusiness.CreateBusinessRequest;
import org.example.managehttp.pojo.CreateBusiness.CreateBusinessResponse;
import org.springframework.stereotype.Service;

@Service
public interface BusinessService {

    // 创建业务
    CreateBusinessResponse createBusiness(CreateBusinessRequest createBusinessRequest);
}
