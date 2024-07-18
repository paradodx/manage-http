package org.example.managehttp.pojo.ReadLedger;

import lombok.Data;

import java.util.List;

@Data
public class ReadLedgerResponse {

    private List<org.example.managehttp.pojo.ReadLedger.Data> dataList;
}
