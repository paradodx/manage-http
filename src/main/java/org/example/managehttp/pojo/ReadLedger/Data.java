package org.example.managehttp.pojo.ReadLedger;

@lombok.Data
public class Data {

    private String number;

    private String uri;

    private String updater;

    private byte[][] data;

    private String jsonData;

    private String timestamp;

    private Boolean isEncrypted;

    private Integer protocolVersion;
}
