package org.example.managehttp.pojo.ReadLedger;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class ReadLedgerRequest {

    @NotNull
    @Positive
    private Integer chainId;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String uri;

    @Pattern(regexp = "^0x[a-zA-Z0-9]{64}$")
    private String dataId;

    @Pattern(regexp = "^zltc_[a-zA-Z0-9]{33}$")
    private String businessAddress;
}
