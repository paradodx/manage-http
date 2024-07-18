package org.example.managehttp.pojo.WriteLedger;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class WriteLedgerRequest {

    @NotNull
    @Positive
    private Integer chainId;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String uri;

    @NotNull
    private Integer version;

    @Pattern(regexp = "^0x[a-zA-Z0-9]{64}$")
    private String dataId;

    @Pattern(regexp = "^zltc_[a-zA-Z0-9]{33}$")
    private String businessAddress;

    @NotBlank(message = "Message must not be blank")
    private String jsonData;

    private String encryptKey;
}
