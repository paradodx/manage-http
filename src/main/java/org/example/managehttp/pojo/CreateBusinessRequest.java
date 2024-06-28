package org.example.managehttp.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateBusinessRequest extends LatticeProperties {

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]*$")
    private String chainId;
}
