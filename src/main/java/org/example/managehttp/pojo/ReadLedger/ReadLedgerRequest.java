package org.example.managehttp.pojo.ReadLedger;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

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
