package com.daye.sys.entity.vt;


import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class VT_Jlsb implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 表号
     */
    private String meterId;
    /**
     * 表箱号
     */
    private String meterBoxId;
    /**
     * 用电用户id
     */
    private Long ydyhId;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 操作时间
     */
    private Date modifiedTime;

    /**
     * 备注
     */
    private String note;
	
    private String name;
	
    private String idCode;

}
