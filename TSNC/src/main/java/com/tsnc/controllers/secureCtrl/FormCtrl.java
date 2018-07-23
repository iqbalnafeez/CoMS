package com.tsnc.controllers.secureCtrl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsnc.dao.FormDAO;
import com.tsnc.model.Courses;
import com.tsnc.model.ReferralInfo;
import com.tsnc.model.StudentFormInfo;
import com.tsnc.model.PgForm;
import com.tsnc.model.Feereq;
import com.tsnc.model.SubjectMarks;
import com.tsnc.model.UgForm;
@Controller
@RequestMapping("/Formctrl")
public class FormCtrl {
	@Autowired
	private FormDAO formdao;
	private static Logger logger = Logger.getLogger(FormCtrl.class);
	
	@RequestMapping(value="/AppFormReq.htm",method = RequestMethod.POST)
	/**
	 * check the student is already admitted or not 
	 * if admitted send the student info 
	 * else
	 * send only application number
	 * */
	public @ResponseBody Map appFormrequest(@RequestBody Feereq feereq,Map<String, Object> model) {
	
		logger.info("Entered the  appFormrequest  method ...");
		
		/**
		 * fetching basic student info using app or roll number
		 * */
		StudentFormInfo studentFormInfo=null;
		try {
			if("ano".equalsIgnoreCase(feereq.getType())){

				studentFormInfo=(StudentFormInfo)formdao.fetchStudentInfoUSAppNo(feereq.getNum());
				if(studentFormInfo==null){
					studentFormInfo=new StudentFormInfo();
					studentFormInfo.setApplicationNo(feereq.getNum()); 
				}
			}
			if("rno".equalsIgnoreCase(feereq.getType())){

				studentFormInfo=formdao.fetchStudentInfoUSRolNo(feereq.getNum());

				if(studentFormInfo==null){
					logger.info(" Invalid roll number "+feereq.getNum());
					model.put("success",null );
					model.put("error"," Invalid roll number " );
					return model; 
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching StudentFormInfo detail  for the app/roll number "+feereq.getNum());
			logger.error(e);
			model.put("success",null );
			model.put("error"," Net work error" );
			return model; 
		}


		model.put("success",studentFormInfo );
		model.put("error",null );
		return model; 

	}

	@RequestMapping(value="/SAVEMANDINFO.htm",method = RequestMethod.POST)
	/**
	 * 
	 *update the student form info
	 * update the fee table
	 * */
	public @ResponseBody Map SavemandatoryInfo(@RequestBody StudentFormInfo studentFormInfo,Map<String, Object> model) {
		logger.info("Entered the  SavemandatoryInfo  method ...");
		//future location for community validation for admission
		if(studentFormInfo.getRollNo()==null||studentFormInfo.getRollNo().length()<1){
			
	
		Courses courses=null;
		//fetch the roll code from course table
		try {
			courses=formdao.fetchCourses(studentFormInfo.getCourse(),studentFormInfo.getBatchStrYr());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("Error occurred while fetching courses detail  for the "+studentFormInfo.getCourse()+" , "+studentFormInfo.getBatchStrYr());
			logger.error(e1);
			model.put("success",null );
			model.put("error","Net work error ");
			return model; 
		}

		if(courses==null || courses.getRollcode()==null || courses.getRollcode().length()<1){
			logger.info("courses is null  for particular batch -"+studentFormInfo.getBatchStrYr());
			model.put("success",null );
			model.put("error","Please contact admin to add course/rollcode for particular batch -"+studentFormInfo.getBatchStrYr() );
			return model;
		}
		//till 2099 no issue
		String rollnumber=(studentFormInfo.getBatchStrYr()-2000)+"/"+ courses.getRollcode()+"/";
		
		int roll=0;
		//fetch the last  roll number from student info table
		try{
			BigInteger	temp=	formdao.fetchlastRollnum(studentFormInfo.getCourse(),studentFormInfo.getBatchStrYr());
			roll=temp.intValue();
		}catch(Exception e){
			System.out.println(e);
			logger.error("Error occurred while fetching lastRollnum detail  for  "+studentFormInfo.getCourse()+" , "+studentFormInfo.getBatchStrYr());
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		roll=roll+1;
		if(roll<10){
			rollnumber=rollnumber+"0"+roll;
		}else{
			rollnumber=rollnumber+roll;
		}
		logger.info("New Roll number "+rollnumber+" for application number "+studentFormInfo.getApplicationNo());
		studentFormInfo.setRollNo(rollnumber);
		}
	/*	
		try{
			formdao.savemandinfo(studentFormInfo);

		}catch(Exception e){
			System.out.println(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		*/
		
		try{
			/* issue
			 *only  update the name,roll number  in the fees table ,not update the batch start year ,
			 * course,last academic type in fees table because of some issues in future
			 */		
			formdao.updatefeesavemandinfo(studentFormInfo);
		}catch(Exception e){
			System.out.println(e);
			logger.error("Error occurred while updating fee  ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		logger.info(" studentFormInfo updated successfully");
		
		model.put("success", studentFormInfo);
		model.put("error",null );
		return model;
	}

	@RequestMapping(value="/STDFRMINFO.htm",method = RequestMethod.POST)
	/**
	 * 
	 *update the student form info
	 * based on the course UG/PG fetch respective details from the respective table
	 * if fetched details is null attach the  application number and send it .
	 * */
	public @ResponseBody Map SandardForMInfo(@RequestBody StudentFormInfo studentFormInfo,Map<String, Object> model) {
		logger.info("Entered the  SandardForMInfo  method ...");
		
		try{
			formdao.savemandinfo(studentFormInfo);
		}catch(Exception e){
			System.out.println(e);
			logger.error("Error occurred while  save studentFormInfo detail  ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		logger.info(" studentFormInfo saved successfully  ");
		
		if("UG".equalsIgnoreCase(studentFormInfo.getCourseCategory())){
			logger.info(" student course category is UG  ");
			UgForm ugForm=new UgForm();
			List<SubjectMarks> subjectMarkslst=null;
		/*	not required 
		 * try{

				//model.put("ugform",stdfrminfo.getApplicationNo() );
				System.out.println("Get the ug form info here");
				//formdao.fetchuginfoinstdinfo(studentFormInfo.getApplicationNo());
				studentFormInfo=(StudentFormInfo)formdao.fetchStudentInfoUSAppNo(studentFormInfo.getApplicationNo());



			}
			catch(Exception e){
				System.out.println(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 

			}*/
			
			try{

				subjectMarkslst=formdao.fetchUgFormUsappNo(studentFormInfo.getApplicationNo());

			}
			catch(Exception e){
				System.out.println(e);
				logger.error("Error occurred while  fetching  UG form(student 12th mark ) for app num  "+studentFormInfo.getApplicationNo());
				logger.error(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 

			}	
			ugForm.setApplicationNo(studentFormInfo.getApplicationNo());
			if(subjectMarkslst!=null){
			
				
				
				ugForm.setBreakstudy(studentFormInfo.getBreakstudy());
				ugForm.setLastschoolname(studentFormInfo.getLastschoolname());
				ugForm.setPercentage(studentFormInfo.getPercentage());
				ugForm.setTotalmarkobtained(studentFormInfo.getTotalmarkobtained());
				ugForm.setTotalmaxmark(studentFormInfo.getTotalmaxmark());
				ugForm.setLssubjectMarks(subjectMarkslst);
			
			}
			
			model.put("success", ugForm);
			model.put("error", null);
			return model;


		}
		else if("PG".equalsIgnoreCase(studentFormInfo.getCourseCategory())){
			logger.info(" student course category is PG  ");
			PgForm pgForm=null;
			try{
				pgForm=(PgForm)formdao.fetchpginfo(studentFormInfo);
				if(pgForm==null){
					pgForm=new PgForm();
					pgForm.setApplicationNo(studentFormInfo.getApplicationNo());
				}
				System.out.println(pgForm.getApplicationNo());
				model.put("success",pgForm );
				model.put("error",null );
				return model;
			}
			catch(Exception e){
				System.out.println(e);
				logger.error("Error occurred while  fetching  PG for app num  "+studentFormInfo.getApplicationNo());
				logger.error(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 

			}

		}else{
			logger.error("Invaild course Category for app num "+studentFormInfo.getApplicationNo());
			model.put("success",null );
			model.put("error"," Invaild course Category" );
			return model; 
		}



	}

	@RequestMapping(value="/PGFRMSAVE.htm",method = RequestMethod.POST)
	public @ResponseBody Map PGForm(@RequestBody PgForm pgform,Map<String, Object> model) {
		logger.info("Entered the  PGForm  method ...");
		/*System.out.println("Enter into PGFRMSAVE");
		System.out.println(pgform.getPassout());
		System.out.println(pgform.getApplicationNo());*/
		ReferralInfo referalInfo=null;
		try{
			formdao.pgformsave(pgform);
		}catch(Exception e){
			logger.error("Error occurred while  saving  PG form   ");
			logger.error(e);
			model.put("success",null );
			model.put("error"," Net work error" );
			return model; 
		}
		logger.info("Pg form saved successfully");
		try{

			referalInfo=(ReferralInfo)formdao.fetchReferralInfoUsappNo(pgform.getApplicationNo());
			if(referalInfo==null){
				referalInfo=new ReferralInfo();
				System.out.println(pgform.getApplicationNo());
				referalInfo.setApplicationNo(pgform.getApplicationNo());

			}
			
			model.put("success",referalInfo);
			model.put("error",null );
			return model;
		}catch(Exception e){
			logger.error("Error occurred while  fetch ReferralInfo  for app num  "+pgform.getApplicationNo());
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}

	}


	@RequestMapping(value="/UGFRMSAVE.htm",method = RequestMethod.POST)
	public @ResponseBody Map UgForm(@RequestBody UgForm ugForm,Map<String, Object> model) {
		logger.info("Entered the  UgForm  method ...");
		
		 ReferralInfo referralInfo=null;

		 StudentFormInfo studentFormInfo=null;
		 try {
			 studentFormInfo	=(StudentFormInfo)formdao.fetchStudentInfoUSAppNo(ugForm.getApplicationNo());
			 studentFormInfo.setBreakstudy(ugForm.getBreakstudy());
			 studentFormInfo.setLastschoolname(ugForm.getLastschoolname());
			 studentFormInfo.setPercentage(ugForm.getPercentage());
			 studentFormInfo.setTotalmarkobtained(ugForm.getTotalmarkobtained());
			 studentFormInfo.setTotalmaxmark(ugForm.getTotalmaxmark());
			 formdao.updateStudentFormInfo(studentFormInfo);
			 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("Error occurred while  fetch StudentInfo  for app num  "+ugForm.getApplicationNo());
			logger.error(e1);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		 
		
		 
		try{
			formdao.ListSaveOrUpdate(ugForm.getLssubjectMarks());
			
		}catch(Exception e){
			
			logger.error("Error occurred while  save/update ugform  ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		try{

			referralInfo=formdao.fetchReferralInfoUsappNo(ugForm.getApplicationNo());

			if(referralInfo==null){
				referralInfo=new ReferralInfo();
				referralInfo.setApplicationNo(ugForm.getApplicationNo());
			}
		

		}catch(Exception e){
			System.out.println(e);
			logger.error("Error occurred while fetching the refferralInfo ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}

		
		model.put("success", referralInfo);
		model.put("error",null );
		return model;
	}


	@RequestMapping(value="/REFERRALINFO.htm",method = RequestMethod.POST)
	public @ResponseBody Map RefInfo(@RequestBody ReferralInfo referralInfo,Map<String, Object> model) {
		
		logger.info("Entered the  RefInfo  method ...");
	
		try{

			formdao.savereferalinfo(referralInfo);
			logger.info("Application form saved successfully");
			model.put("success", "Application form saved successfully ");
			model.put("error",null );
			return model;
			//update the name in the fees table ,not update the batch start year ,
			//course,last acatemic type in fees table because of some issues in future

		}catch(Exception e){
			System.out.println(e);
			logger.error("Error occurred while saving the refferralInfo ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}

	}



}









