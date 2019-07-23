package com.daye.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 缴费记录
 * </p>
 *
 * @author 唐东东
 * @since 2019-06-18
 */
@TableName("tbJfjl")
public class TbJfjl implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 表号
     */
    @TableField("meterId")
    private String meterId;
    /**
     * 用户id
     */
    @TableField("ydyhId")
    private Long ydyhId;
    /**
     * 本期电量
     */
    @TableField("periodElecNum")
    private Double periodElecNum;
    /**
     * 应缴电费
     */
    @TableField("amountDue")
    private Double amountDue;
    /**
     * 实缴电费
     */
    @TableField("actualAmount")
    private Double actualAmount;
    /**
     * 缴费状态(0:未缴费,1:欠费,2:已缴费)
     */
    @TableField("payStatus")
    private Integer payStatus;
    /**
     * 缴费时间
     */
    @TableField("payTime")
    private Date payTime;
    /**
     * 是否打印收据(1:打印,0:未打印)
     */
    @TableField("receiptStatus")
    private Integer receiptStatus;
    /**
     *  抄表时间
     */
    @TableField("regisTime")
    private Date regisTime;
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

    public void setCashier(int cashier) {
        this.cashier = cashier;
    }
//收银员ID
    private int cashier;

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

    public Long getYdyhId() {
        return ydyhId;
    }

    public void setYdyhId(Long ydyhId) {
        this.ydyhId = ydyhId;
    }

    public Double getPeriodElecNum() {
        return periodElecNum;
    }

    public void setPeriodElecNum(Double periodElecNum) {
        this.periodElecNum = periodElecNum;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
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
        return "TbJfjl{" +
        "id=" + id +
        ", meterId=" + meterId +
        ", ydyhId=" + ydyhId +
        ", periodElecNum=" + periodElecNum +
        ", amountDue=" + amountDue +
        ", actualAmount=" + actualAmount +
        ", payStatus=" + payStatus +
        ", payTime=" + payTime +
        ", receiptStatus=" + receiptStatus +
        ", createdTime=" + createdTime +
        ", regisTime=" + regisTime +
        ", modifiedTime=" + modifiedTime +
        ", note=" + note +
        "}";
    }

    public Date getRegisTime() {
        return regisTime;
    }

    public void setRegisTime(Date regisTime) {
        this.regisTime = regisTime;
    }
}
