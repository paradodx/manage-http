package org.example.managehttp.pojo.ReadLedger;

import lombok.Data;

@Data
public class LedgerData {

    private String number;

    private String uri;

    private String updater;

    private byte[][] data;

    private String jsonData;

    private Long timestamp;

    private Boolean isEncrypted;

    private Integer protocolVersion;
}
