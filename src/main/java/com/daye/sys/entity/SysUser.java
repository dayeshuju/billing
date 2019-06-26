package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("sysUsers")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 密码（默认123456）
     */
    private String password;
    private String name;
    /**
     * 盐  密码加密时前缀，使加密后的值不同
     */
    private String salt;
    /**
     * 角色
     */
    private Integer roleId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常  默认值 ：1
     */
    private Integer valid;
    /**
     * 创建时间
     */
    @TableField("createdTime")
    private Date createdTime;
    /**
     * 修改时间
     */
    @TableField("modifiedTime")
    private Date modifiedTime;

    /**
     * 备注
     */
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getRoleId() { return roleId;}

    public void setRoleId(Integer roleId) { this.roleId = roleId;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }
    @Override
    public String toString() {
        return "SysUsers{" +
        "id=" + id +
        ", nickname=" + nickname +
        ", password=" + password +
        ", name=" + name +
        ", salt=" + salt +
        ", role="+ roleId +
        ", email=" + email +
        ", mobile=" + mobile +
        ", valid=" + valid +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        ", note=" + note +
        "}";
    }
}
