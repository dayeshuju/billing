package com.daye.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 6780580291247551747L;
    /**当前页记录*/
    private List<T> aaData;

    private Integer draw;
    private Integer iTotalDisplayRecords;
    private Integer iTotalRecords;
}
