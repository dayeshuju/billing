package com.daye.common.util;

import com.daye.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {

	 public static SysUser getPrincipal(){
		 return (SysUser)SecurityUtils
		.getSubject().getPrincipal();
	 }
}
