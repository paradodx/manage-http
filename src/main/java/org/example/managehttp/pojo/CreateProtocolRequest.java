package org.example.managehttp.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@EqualsAndHashCode(callSuper = true)
@Data
public class CreateProtocolRequest extends LatticeProperties {

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]*$")
    private String chainId;

    @Min(value = 1)
    private long suite;

    @NotBlank
    @Pattern(regexp = "^syntax.*}$")
    private String message;
}
