package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 抄表记录
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("tbCbjl")
public class TbCbjl implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 表号
     */
    @TableField("meterId")
    private String meterId;
    /**
     * 抄表时间
     */
    @TableField("regisTime")
    private Date regisTime;
    /**
     * 电表示数
     */
    @TableField("meterNum")
    private Double meterNum;
    /**
     * 创建时间
     */
    @TableField("createdTime")
    private Date createdTime;
    /**
     * 操作时间
     */
    @TableField("modifiedTime")
    private Date modifiedTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public Date getRegisTime() {
        return regisTime;
    }

    public void setRegisTime(Date regisTime) {
        this.regisTime = regisTime;
    }

    public Double getMeterNum() {
        return meterNum;
    }

    public void setMeterNum(Double meterNum) {
        this.meterNum = meterNum;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "TbCbjl{" +
        "id=" + id +
        ", meterId=" + meterId +
        ", regisTime=" + regisTime +
        ", meterNum=" + meterNum +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
