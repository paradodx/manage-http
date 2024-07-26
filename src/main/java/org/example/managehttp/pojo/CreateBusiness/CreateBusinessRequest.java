package org.example.managehttp.pojo.CreateBusiness;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.managehttp.utils.LatticeProperties;



/*
* 创建业务请求数据
* */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateBusinessRequest extends LatticeProperties {

    @NotNull
    // @Pattern(regexp = "^[1-9][0-9]*$")
    @Positive
    private Integer chainId;
}
