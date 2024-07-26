package org.example.managehttp.pojo.UpdateProtocol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;


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
