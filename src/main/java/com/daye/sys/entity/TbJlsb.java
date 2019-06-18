package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 计量设备
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("tbJlsb")
public class TbJlsb implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 表号
     */
    @TableField("meterId")
    private String meterId;
    /**
     * 表箱号
     */
    @TableField("meterBoxId")
    private String meterBoxId;
    /**
     * 用电用户id
     */
    @TableField("ydyhId")
    private Long ydyhId;
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

    public String getMeterBoxId() {
        return meterBoxId;
    }

    public void setMeterBoxId(String meterBoxId) {
        this.meterBoxId = meterBoxId;
    }

    public Long getYdyhId() {
        return ydyhId;
    }

    public void setYdyhId(Long ydyhId) {
        this.ydyhId = ydyhId;
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
        return "TbJlsb{" +
        "id=" + id +
        ", meterId=" + meterId +
        ", meterBoxId=" + meterBoxId +
        ", ydyhId=" + ydyhId +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
