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
    //身份证号
    private String idCode;
    //表号
    private String meterId;
    //缴费时间
    private String payTime;
    //欠费金额
    private Double arrears;
    //字符缴费状态
    private String payStatus;
    //数字缴费状态
    private Integer payStatu;
    //用电量
    private Long periodElecNum;
    //应缴电费
    private String amountDue;
    //实缴电费
    private String actualAmount;
    //是否打印
    private Integer receiptStatus;
    //抄表时间
    private String regisTime;
    //创建时间
    private String createdTime;
    //操作时间
    private String modifiedTime;
    //备注
    private String note;

    //用户地址
    private String address;
    //用户电话
    private String phoneNum;
    //电费费率
    private String tate;
    //用户状态(0:禁用,1:启用)
    private Integer valid;
    //用户创建时间
    private String createdUserTime;
    //操作时间
    private String modifiedUserTime;
    //用户备注
    private String userNote;


}
