package com.daye.common.vo;

import java.io.Serializable;

/**
 * Node :Value Object 
 * 借助此对象封装zTree对应的树节点信息
 */
public class Node implements Serializable {
	private static final long serialVersionUID = 4351174414771192644L;
	/**菜单id*/
	private Integer id;
	/**上级菜单id*/
	private Integer parentId;
	/**菜单名称*/
	private String name;
	public Integer getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", parentId=" + parentId + ", name=" + name + "]";
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	 
}
