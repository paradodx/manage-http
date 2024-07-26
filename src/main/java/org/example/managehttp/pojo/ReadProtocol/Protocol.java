package org.example.managehttp.pojo.ReadProtocol;


import lombok.Data;



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
