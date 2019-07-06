package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户类型
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("tbYhlx")
public class TbYhlx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户类型
     */
    @TableField("userType")
    private String userType;
    /**
     * 电费费率*10000
     */
    private String tate;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTate() {
        return tate;
    }

    public void setTate(String tate) {
        this.tate = tate;
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
        return "TbYhlx{" +
        "id=" + id +
        ", userType=" + userType +
        ", tate=" + tate +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
