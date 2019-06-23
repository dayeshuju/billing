package com.daye.common.aspect;

import com.daye.common.annotation.RequiredLog;
import com.daye.common.util.IPUtils;
import com.daye.common.util.ShiroUtils;
import com.daye.sys.entity.SysLog;
import com.daye.sys.entity.SysUser;
import com.daye.sys.mapper.SysLogMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
/***
 * 借助@Aspect注解描述此类为一个切面对象,
 * 我们要在此类中实现日志记录操作.例如
 * 用户添加,修改等业务操作执行我们要进行
 * 日志记录.
 */
@Order(1) //使用order属性，设置该类在spring容器中的加载顺序
@Aspect
@Service
public class SysLogAspect {
	@Autowired
	private SysLogMapper sysLogMapper;
	
	/**
	 * 切入点的定义(借助@Pointcut注解进行描述)
	 */
	@Pointcut("bean(*ServiceImpl)")
	public void doPointCut(){}
	/**
	 * @Around 修饰的的方法为一个环绕通知
	 * 可以在目标方法执行之前和之后添加扩展业务
	 * @param jp 连接点(封装了要执行的具体方法对象信息)
	 * @return
	 */
	@Around("doPointCut()")
	//@Around("bean(sysUserServiceImpl)")
	public Object aroundMethod(ProceedingJoinPoint jp)
	    throws Throwable{
		//1.添加扩展功能
		long startTime=System.currentTimeMillis();
		//System.out.println("method.start:"+startTime);
		//2.执行目标方法(可以基于业务不执行目标方法)
		//result为目标方法的返回值
		//System.out.println("invoke target method");
		Object result=jp.proceed();//通过反射机制调用目标方法
		//3.添加扩展功能
		long endTime=System.currentTimeMillis();
		//System.out.println("method.end:"+endTime);
		saveObject(jp,endTime-startTime);
		return result;
	}
	/**将用户行为日志存储到数据库中
	 * @throws JsonProcessingException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException */
	private void saveObject(ProceedingJoinPoint jp,long time) throws JsonProcessingException, NoSuchMethodException, SecurityException
	{
		//1.获取要记录的用户行为数据
		//1.1获取ip地址
		String ip= IPUtils.getIpAddr();
		//1.2获取登录用户名
		SysUser user = ShiroUtils.getPrincipal();
		if(user != null) {
			Integer userId=user.getId();
			//1.3获取用户操作
			//1.3.1获取目标方法对象(先获取目标类,再获取目标方法对象)
			Class<?> targetCls=jp.getTarget().getClass();
			
			Method targetMethod = getTargetMethod(targetCls,jp);
			//1.3.2获取目标方法对象上的注解
			RequiredLog rLog=
			targetMethod.getDeclaredAnnotation(RequiredLog.class);
			//1.3.3获取注解中定义的操作名
			String operation=rLog.operation();
			//1.4 获取方法名(目标类的名字+方法名)
			String method = targetCls.getSimpleName() + "." + targetMethod.getName();
			//1.5 获取用户传递的实际参数(将参数转换为字符串)
			String params = null;
			if(rLog.value() == 1) {
				params=new ObjectMapper().writeValueAsString(jp.getArgs());
			}
			//2.封装用户行为数据
			SysLog log=new SysLog();
			log.setIp(ip);
			log.setUserId(userId);
			log.setOperation(operation);
			log.setMethod(method);
			log.setParams(params);
			log.setTime(time);
			//3.将用户行为数据写入到数据库
			sysLogMapper.insert(log);
		}
	}
	/***
	 * 获取目标方法对象(获得此对象后就可以获取方法上的注解了)
	 * @param targetCls
	 * @param jp
	 * @return
	 * @throws NoSuchMethodException
	 */
	//快速提取方法快捷键:alt+shift+m
	private Method getTargetMethod(Class<?> targetCls,ProceedingJoinPoint jp) throws NoSuchMethodException {
		//获取方法签名对象
		MethodSignature ms=
		(MethodSignature)jp.getSignature();
		//Method method=ms.getMethod();接口方法,不是我们需要的
		//ms.getMethod();接口方法对象
		//获取方法参数类型
		Class<?>[] paramTypes=ms.getMethod().getParameterTypes();
		//获取目标方法(先获取目标对象,再获取目标方法)
		Method targetMethod=
				targetCls.getDeclaredMethod(
				ms.getName(),paramTypes);
		return targetMethod;
	}
}







