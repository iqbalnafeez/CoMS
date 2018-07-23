package com.tsnc.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ResponseBody;





import com.tsnc.dao.BaseDAO;
import com.tsnc.dao.IndexDAO;
import com.tsnc.model.Base;
import com.tsnc.model.Login;

import org.apache.log4j.Logger;

@Aspect
public class AopCont  {
	@Autowired
	private BaseDAO baseDAO;

	private static Logger logger = Logger.getLogger(AopCont.class);



	@Around("execution(* com.tsnc.controllers.secureCtrl.*.*(..))")
/**	
 *  This method was called automatically  before and after of call of  any method
 *	inside  the package com.tsnc.controllers.secureCtrl
 *
 *	Authentication and Authorization was done inside this method
 *	
 *	*/
	public Map aroundAdvice(JoinPoint pjp) throws Throwable{
		
		logger.info("Entered the  aroundAdvice  method ...");
		
		logger.info(" user reuested for the controller  !!"+pjp.getSignature()); 
		
		String s=pjp.getSignature().getName();
		/*
		 * System.out.println(s);
		System.out.println("Before running Advice on method="+pjp.toString());
		System.out.println("Before running Advice on target = "+pjp.getTarget());
		System.out.println("Before running Advice on signature = "+pjp.getSignature());
		System.out.println("Token from the client "+token);
		*/
		Object[] obj=pjp.getArgs();
		Base base=(Base) obj[0];
		String token=base.getToken();

		logger.info(" user with the token "+ token +" requested for the controller  !!"+pjp.getSignature()); 
		Map<String,Object> model=new HashMap<String,Object>();
		Login login=baseDAO.fetchLoginUStoken(token);
		if(login!=null){
			/*System.out.println("Role "+login.getRole()+" user "+login.getUsername()); */
			if(s.equalsIgnoreCase("resetPassRequest")){

				if(login.getRole()!=2){
					logger.info(" User " +login.getUsername()+ " of  role " +login.getRole()+" is not access to entered into the method "+pjp.getSignature()); 
				/*	System.out.println("Donot have a permission to view this page");*/
				model.put("success",null );
				
				model.put("error"," You don't have access for this screen " );

				return model; 

				}
				else{
					logger.info(" User " +login.getUsername()+ " of  role " +login.getRole()+"is allowed to entered into the method "+pjp.getSignature()); 
				System.out.println("granted permission to view the change password for Admission role user");
				return (Map) ((ProceedingJoinPoint) pjp).proceed();
				}

				}


			
			
			return (Map) ((ProceedingJoinPoint) pjp).proceed();

		}else{
			model.put("success",null );
			model.put("error","Invalid token" );
			return model; 
		}



	}





}










/*   @Pointcut("execution(* com.tsnc.controllers.secureCtrl.*.*(..))")
private void tokenvalidation(){}

@Around("tokenvalidation()")*/