package com.daye.sys.service.realm;

import com.daye.sys.entity.SysUser;
import com.daye.sys.mapper.SysMenuMapper;
import com.daye.sys.mapper.SysRoleMenuMapper;
import com.daye.sys.mapper.SysUserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ShiroUserRealm extends AuthorizingRealm{

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	/**
	 * 设置凭证匹配器
	 * @param credentialsMatcher
	 */
	@Override
	public void setCredentialsMatcher(
			CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		super.setCredentialsMatcher(cMatcher);
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.基于用户id查找角色id
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		Integer roleId=
		sysUserMapper.findRoleIdById(user.getId());
		//2.基于角色id查找菜单id
		List<Integer>  menuIds = sysRoleMenuMapper.findMenuIdsByRoleId(roleId);
		//3.基于菜单id查找权限标识
		Integer[] array={};
		List<String> permissions=
		sysMenuMapper.findPermissions(menuIds.toArray(array));
		//4.对权限标识进行去重和空的操作
		Set<String> set=new HashSet<String>();
		for(String permission:permissions){
			if(!StringUtils.isEmpty(permission)){
				set.add(permission);
			}
		}
		//5.对权限标识信息进行封装
		SimpleAuthorizationInfo info=
		new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;//返回给授权管理器对象
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取客户端提交的用户信息.获取用户名
		String nickname=(String)token.getPrincipal();
		//2.基于用户名从数据库查询用户信息
		SysUser user=sysUserMapper.findUserByNickname(nickname);
		//3.校验用户信息(用户是否存在,是否被禁用)
		if(user==null)
			throw new AuthenticationException("用户名不正确");
		if(user.getValid()==0)
			throw new AuthenticationException("1");

		//4.对用户信息进行封装
		ByteSource credentialsSalt=
				ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info=
				new SimpleAuthenticationInfo(
						user, //principal(用户身份)
						user.getPassword(),//hashedCredentials(已经加密的密码)
						credentialsSalt, //credentialsSalt(盐)
						this.getName());//realm name
		return info;//将此对象返回给认证管理器
	}

}
