package org.example.managehttp.pojo.ReadProtocol;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/*
* 读取协议请求数据
* */

@Data
public class ReadProtocolRequest {
    @NotNull
    // @Pattern(regexp = "^[1-9][0-9]*$")
    @Positive
    private Integer chainId;

    @Pattern(regexp = "^[1-9][0-9]*$")
    private String uri;
}
