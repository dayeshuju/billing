package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用电用户
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("tbYdyh")
public class TbYdyh implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用电用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户身份证号
     */
    @TableField("idCode")
    private String idCode;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户地址
     */
    private String address;
    /**
     * 电话号码
     */
    @TableField("phoneNum")
    private String phoneNum;
    /**
     * 用户类型
     */
    @TableField("userTypeId")
    private Long userTypeId;
    /**
     * 用户状态(0:禁用,1:启用)
     */
    private Integer valid;
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

    /**
     * 备注
     */
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
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

    public  String getNote(){return note;}

    public void setNote(String note) {this.note = note;}

    @Override
    public String toString() {
        return "TbYdyh{" +
        "id=" + id +
        ", idCode=" + idCode +
        ", name=" + name +
        ", address=" + address +
        ", phoneNum=" + phoneNum +
        ", userTypeId=" + userTypeId +
        ", valid=" + valid +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", note=" + note +
        "}";
    }
}
