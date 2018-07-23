package com.tsnc.controllers.secureCtrl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsnc.dao.ResetDAO;
import com.tsnc.model.Base;
import com.tsnc.model.Login;
import com.tsnc.model.StudentFormInfo;
import com.tsnc.model.SubjectMarks;




@Controller
@RequestMapping("/ResetPasswordCtrl")
public class ResetPasswordCtrl {
	@Autowired
	private ResetDAO resetdao;
	private static Logger logger = Logger.getLogger(ResetPasswordCtrl.class);
	
	
	@RequestMapping(value="/RESETPASSWORDREQUEST.htm",method = RequestMethod.POST)
	/**
	 * fetch all the Login  where role other than 2(ADMIN) 
	 * and set password =null and token =null 
	 * before send
	 */
	public @ResponseBody Map resetPassRequest(@RequestBody Base base,Map<String, Object> model) {
		logger.info("Entered the  resetPassRequest  method ...");
		List<Login> login=null;
		try {
			login = (List<Login>) resetdao.fetchalluserrole();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching All Login detail  ");
			logger.error(e);
			model.put("success", null);
			model.put("error", "Network error");
		}
		
		ListIterator<Login> it=login.listIterator();
		while(it.hasNext()){
			it.next().setPassword(null);
				}
	ListIterator<Login> it1=login.listIterator();
		while(it1.hasNext()){
			
			
			it1.next().setToken(null);
			}
		
		model.put("success", login);
		model.put("error", null);
	
	
		return model;
	}
	
	@RequestMapping(value="/RESETPASSWORD.htm",method = RequestMethod.POST)
	public @ResponseBody Map resetPass(@RequestBody Login login,Map<String, Object> model) {
		logger.info("Entered the  resetPass  method ...");
		
		/**
		 * encrypt the password before save
		 * */
		String generatedPassword=null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(login.getPassword().getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
         
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("Error in encrypt the password ");
			logger.error(e1);
		}
		login.setPassword(generatedPassword);
		/**
		 *update login with new encrypted password 
		 * */
		try {
			resetdao.updatepassword(login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error in updating the password ");
			logger.error(e);
		}
		logger.info("Password changed successfully for the user "+login.getUsername());
		model.put("success", "Password changed successfully");
		model.put("error", null);
		return model;
	}
}
