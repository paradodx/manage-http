package org.example.managehttp.controller;


import org.example.managehttp.service.protoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protocol")
public class ProtoController {
    @Autowired
    private protoService protoService;

}