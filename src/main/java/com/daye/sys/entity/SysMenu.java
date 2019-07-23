package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("sysMenus")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 资源名称
     */
    private String name;
    private String htmlid;
    /**
     * 资源URL
     */
    private String url;
    /**
     * 类型     1：菜单   2：按钮
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String note;
    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField("parentId")
    private Integer parentId;
    /**
     * 授权(如：user:create)
     */
    private String permission;
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

    public void setSecondMenus(SysMenu[] secondMenus) {
        this.secondMenus = secondMenus;
    }

    public Integer getNum() {
        return num;
    }

    /**
     * 最大二级菜单数
     */
    @TableField("num")
    private Integer num;

    /**
     * 二级菜单
     */
    @TableField("secondMenus")
    private SysMenu[] secondMenus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlid() {
        return htmlid;
    }

    public void setHtmlid(String htmlid) {
        this.htmlid = htmlid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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
        return "SysMenus{" +
        "id=" + id +
        ", name=" + name +
        ", htmlid=" + htmlid +
        ", url=" + url +
        ", type=" + type +
        ", sort=" + sort +
        ", note=" + note +
        ", parentId=" + parentId +
        ", permission=" + permission +
        ", createdTime=" + createdTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
