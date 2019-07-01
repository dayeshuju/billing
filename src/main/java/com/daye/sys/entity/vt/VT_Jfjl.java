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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(String lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public Double getArrears() {
        return arrears;
    }

    public void setArrears(Double arrears) {
        this.arrears = arrears;
    }
}
