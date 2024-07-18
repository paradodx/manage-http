package org.example.managehttp.pojo.ReadProtocol;


import lombok.Data;
import lombok.EqualsAndHashCode;

import org.web3j.abi.datatypes.StaticStruct;



/*
* 协议内容
* */
@Data
public class Protocol {

    private String updater;

    private byte[][] data;

    private String message;

    private Integer version;


}
