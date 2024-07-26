package org.example.managehttp.pojo.CreateProtocol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;


/*
* 创建协议请求数据
* */


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
