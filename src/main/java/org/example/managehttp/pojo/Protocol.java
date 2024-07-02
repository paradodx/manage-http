package org.example.managehttp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Protocol {

    private String updater;

    private List<byte[]> data;

    private String message;

    private Integer version;

}
