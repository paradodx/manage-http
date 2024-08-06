package org.example.managehttp.pojo.Tx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TxData {
/*    @NotNull
    @Positive*/
    private Integer chainId;
/*    @Pattern(regexp = "^[1-9][0-9]*$")*/
    private String uri;

    private Long height;
/*    @Pattern(regexp = "^zltc_[a-zA-Z0-9]{33}$")*/
    private String owner;
/*    @Pattern(regexp = "^zltc_[a-zA-Z0-9]{33}$")*/
    private String linker;
    
    private String prefixCode;
/*    @NotBlank*/
    private String hash;
/*    @NotBlank*/
    private String payload;
    /* @NotBlank*/
    private String data;
/*    @NotNull*/
    private Integer version;
    /*@NotNull*/
    private Long timestamp;
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")*/
    private LocalDateTime createTime;
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")*/
    private LocalDateTime updateTime;
}
