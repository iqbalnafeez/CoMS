package com.tsnc.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsnc.aop.AopCont;
import com.tsnc.dao.IndexDAO;
import com.tsnc.model.Login;



@Controller

@RequestMapping("/IndexCtrl")
public class IndexCtrl {

	@Autowired
	private IndexDAO indexDAO;

	private static Logger logger = Logger.getLogger(AopCont.class);
	//Login Start  **************
	@RequestMapping(value="/loginSubmit.htm",method = RequestMethod.POST)
	public @ResponseBody Map loginSubmit(@RequestBody Login loginUI,Map<String, Object> model)
	{
		logger.info("Entered the  loginSubmit  method ...");
		String generatedPassword=null;
		/**
		 *  fetching the login info from the table using user id
		 *  
		 */
		/*System.out.println(loginUI.getUsername()+ " "+loginUI.getPassword());*/
		Login loginTB=null;
		try {
			loginTB=indexDAO.fetchLoginUSusername(loginUI.getUsername());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching login detail  for the user "+loginUI.getUsername());
			logger.error(e);
			model.put("success",null );
			model.put("error"," Network error" );
			return model;
		}
		

		if(loginTB!=null){
			/**
			 *  encrypt the password from the UI
			 */
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(loginUI.getPassword().getBytes());
				byte[] bytes = md.digest();
				StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	         /*   System.out.println("generatedPassword"+generatedPassword);*/
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				logger.error("Error in encrypt the password ");
				logger.error(e1);
				e1.printStackTrace();
			}
		/*	System.out.println(loginTB.getPassword());*/
			/**
			 * checking that encrypted ui password is matching with db password or not
			 */
			if(generatedPassword.equals(loginTB.getPassword())){
				/**
				 * Updating   token and date  in the login table with new one
				 */
				UUID uuid = UUID.randomUUID();
				Date date=new Date();
				String token = uuid.toString();
				loginTB.setToken(token);
				loginTB.setDate(date);
				System.out.println(token);
				try {
					indexDAO.updateLoginToken(loginTB);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error in updating the token ");
					logger.error(e);
					model.put("success",null );
					model.put("error"," Network error" );
					return model;
				}
				logger.info("User " +loginUI.getUsername()+ " of  role " +loginTB.getRole()+" logged  successfully with token "+loginTB.getToken());
				loginTB.setPassword(null);
				System.out.println(loginTB.getRole());
				model.put("success",loginTB );
				model.put("error",null );
				return model;
			}else{
				logger.info("User " +loginUI.getUsername()+ " entered the wrong password ");
				model.put("success",null );
				model.put("error"," Invalid Password " );
				return model;
			}
		}else{
			logger.info("User " +loginUI.getUsername()+ " entered the wrong user id ");
			model.put("success",null );
			model.put("error"," Invalid  User name " );
			return model;
		
		}
		

	} 

	//Login end **************
	

	//Forget Password   start **************
	/*@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value="/forgot.htm",method = RequestMethod.GET)
	public @ResponseBody Object forgot(@QueryParam("user_id") String user_id) {
		System.out.println();
		String frgtUser=null;
		try {
			frgtUser = indexDAO.forgotPassword(user_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> res=new HashMap<String,Object>();
		if(frgtUser==null)
		{
			res.put("success",null);
			res.put("error","Invalid User Id");
			return res;
		}
		System.out.println("password recovery");
		// creates a simple e-mail object
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user_id);
		email.setSubject("Recovery Password");
		email.setText("Your password is "+frgtUser);
		// sends the e-mail
		mailSender.send(email);
		res.put("success","successfully password send to"+ user_id);
		res.put("error",null);
		return res;

	}
	//Forget Password   end **************
*/	// logout  start++++++++++

	@RequestMapping(value="/logout.htm",method = RequestMethod.DELETE)
	public @ResponseBody Map logout(@RequestBody String token,Map<String, Object> response)
	{	/**
		 * Updating   token = null in the login table
		 */
		logger.info("Entered the  logout  method ...");
		/*System.out.println("token logout"+token);*/
		try{
			//make the token invalid
			indexDAO.updateUStoken(token);
		}
		catch(Exception e){
		/*	System.out.println("error"+e);*/
			logger.error("Error in updating the token ");
			logger.error(e);
			response.put("success",null );
			response.put("error"," Network error " );
			return response;
		}
		logger.info("User with the token "+ token +" logged out successfully ");
		response.put("success","LogOut successfully");
		response.put("error",null );
		return response;
	}
	//logout  end **************
}
