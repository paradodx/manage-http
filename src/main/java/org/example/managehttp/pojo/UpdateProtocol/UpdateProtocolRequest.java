package org.example.managehttp.pojo.UpdateProtocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;

import javax.validation.constraints.*;


/*
* 更新协议请求数据
* */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateProtocolRequest extends LatticeProperties {
    @NotNull
    // @Pattern(regexp = "^[1-9][0-9]*$")
    @Positive
    private Integer chainId;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String uri;

    @NotBlank
    @Pattern(regexp = "^syntax.*}$")
    private String message;
}
