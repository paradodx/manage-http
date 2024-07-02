package org.example.managehttp.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "lattice")
public class LatticeProperties {

    private String accountAddressStr;

    private String linkerAddressStr;

    private String privateKeyHex;

    private Boolean isGm;

    // private Integer chainId;

    private String httpUrl;

    private String ledgerAbi;
}
