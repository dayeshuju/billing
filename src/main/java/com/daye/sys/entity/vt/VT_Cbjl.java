package com.daye.sys.entity.vt;

import lombok.Data;

import java.io.Serializable;

@Data
public class VT_Cbjl implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long sort;
    private Integer id;
    private String name;
    private String idCode;
    private String meterId;
    private String regisTime;
    private Double meterNum;
}
