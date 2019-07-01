package com.daye.sys.entity.vt;

import lombok.Data;

import java.io.Serializable;

@Data
public class VT_Jfjl implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long sort;

    //id
    private Integer id;
    //姓名
    private String name;
    //户号
    private String idCode;
    //表号
    private String meterId;
    //上次缴费时间
    private String lastPayTime;
    //欠费金额
    private Double arrears;

}
