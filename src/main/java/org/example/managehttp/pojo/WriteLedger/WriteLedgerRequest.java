package org.example.managehttp.pojo.WriteLedger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

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
}
