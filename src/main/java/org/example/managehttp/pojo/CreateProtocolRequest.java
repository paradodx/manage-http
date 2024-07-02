package org.example.managehttp.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;

import javax.validation.constraints.*;


@EqualsAndHashCode(callSuper = true)
@Data
public class CreateProtocolRequest extends LatticeProperties {

    @NotNull
    // @Pattern(regexp = "^[1-9][0-9]*$")
    @Positive
    private Integer chainId;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String suite;

    @NotBlank(message = "Message must not be blank")
    @Pattern(regexp = "^syntax.*}$", message = "Message must match the pattern '^syntax.*}$'")
    private String message;
}
