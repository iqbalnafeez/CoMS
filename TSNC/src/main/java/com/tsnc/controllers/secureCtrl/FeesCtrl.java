package com.tsnc.controllers.secureCtrl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsnc.aop.AopCont;
import com.tsnc.dao.AppSaleDAO;
import com.tsnc.dao.FeesDAO;
import com.tsnc.model.Fee;
import com.tsnc.model.FeeHistorys;
import com.tsnc.model.FeeParticulars;
import com.tsnc.model.FeeStructure;
import com.tsnc.model.Login;
import com.tsnc.model.Base;
import com.tsnc.model.Feereq;
import com.tsnc.model.Paymentreq;
import com.tsnc.model.StudentFormInfo;

@Controller

@RequestMapping("/FeesCtrl")
public class FeesCtrl {
	@Autowired
	private FeesDAO feesDAO;
	private static Logger logger = Logger.getLogger(FeesCtrl.class);
	
	@RequestMapping(value="/PAYMENT.htm",method = RequestMethod.POST)
	public @ResponseBody Map PAYMENT(@RequestBody Feereq feereq,Map<String, Object> model)
	{ 
		
		logger.info("Entered the  PAYMENT  method ...");
		
		/**
		 * fetching basic student info using app or roll number
		 * */
		StudentFormInfo studentInfo=null;
		try {
			if("ano".equalsIgnoreCase(feereq.getType())){

				studentInfo=feesDAO.fetchStudentInfoUSAppNo(feereq.getNum());

			}
			if("rno".equalsIgnoreCase(feereq.getType())){

				studentInfo=feesDAO.fetchStudentInfoUSRolNo(feereq.getNum());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching StudentFormInfo detail  for the app/rol number "+feereq.getNum());
			logger.error(e);
			model.put("success",null );
			model.put("error"," Net work error" );
			return model; 
		}

		/**
		 * checking that the student  was admitted or not
		 * */
		if(studentInfo!=null){
			System.out.println(studentInfo.getName());
			System.out.println(studentInfo.getApplicationNo());
			System.out.println(studentInfo.getRollNo());

		}else{
			logger.info("user entered the  Invalid app/roll number " +feereq.getNum());
			model.put("success",null );
			model.put("error"," Invalid app/roll number" );
			return model;

		}

		/**
		 * checking that fee for the semester is paid for first time or already paid
		 * */
		Fee fee=null;
		try {

			fee=feesDAO.fetchFeeUSAppNo(studentInfo.getApplicationNo(),feereq.getSem());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching fee detail  for the app number "+studentInfo.getApplicationNo()+" for the sem "+feereq.getSem());
			logger.error(e);
			model.put("success",null );
			model.put("error"," Net work error" );
			return model; 
		}  



		Date duedate=null;

		List<FeeStructure> feeStructures=null;


		/**
		 * fee going to pay is for first time so entering the default value 
		 * */
		if(fee==null){

			/**
			 * fetching the  last date to pay the fees
			 * */

			try {
				duedate=feesDAO.fetchDuedate(studentInfo.getBatchStrYr(),feereq.getSem());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("Error occurred while fetching duedate detail  for the values "+studentInfo.getBatchStrYr()+" , "+feereq.getSem());
				logger.error(e1);
				model.put("success",null );
				model.put("error"," Error in getting the due date" );
				return model;
			}

			if( duedate==null){
				logger.info("duedate is  null, admin not enter the fine date for respective semester");
				model.put("success",null );
				model.put("error", " Please add the  fine date in fee structure tab " );
				return model;
			}



			/**
			 * fetching the fee structure
			 * */

			try {
				if(feereq.getSem()==1){
					feeStructures=feesDAO.fetchfeeStructure(feereq.getSem(), studentInfo.getLastAcademicType(), studentInfo.getCourse(), studentInfo.getBatchStrYr());
					System.out.println(" *** sem "+1);	  
				}else{
					feeStructures=feesDAO.fetchfeeStructure(feereq.getSem(), "Others", studentInfo.getCourse(), studentInfo.getBatchStrYr());
					System.out.println(" *** sem "+342);	  
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while fetching feeStructures detail  for the values "+feereq.getSem() +" , "+ studentInfo.getLastAcademicType() +"/Others ,"+ studentInfo.getCourse()+" ,  "+ studentInfo.getBatchStrYr());
				logger.error(e);
				model.put("success",null );
				model.put("error"," Net work error" );
				return model; 
			}

			if(feeStructures==null || feeStructures.size()<1){
				logger.info("feeStructures is  null, admin not enter the feeStructures  for  sem"+feereq.getSem()+ " LastAacademicType, "+ studentInfo.getLastAcademicType()+ " course , "+studentInfo.getCourse()+ " batchstart year, "+studentInfo.getBatchStrYr() );
				model.put("success",null );
				//model.put("error"," No feestructure is avilable for  sem"+feereq.getSem()+ " LastAacademicType, "+ studentInfo.getLastAcademicType()+ " course , "+studentInfo.getCourse()+ " batchstart year, "+studentInfo.getBatchStrYr() );
				model.put("error","No Fee Structure available for the given Semester.");
				return model; 
			}else{

				System.out.println(feeStructures.get(feeStructures.size()-1).getBatchStrYr() +"fee structure is not null");
			}

			fee=new Fee();
			fee.setApplicationNo(studentInfo.getApplicationNo()); 
			fee.setBatchStrYr(studentInfo.getBatchStrYr());
			fee.setCourse(studentInfo.getCourse());
			fee.setName(studentInfo.getName());
			fee.setRollNo(studentInfo.getRollNo());
			fee.setLastAcademicType(studentInfo.getLastAcademicType());
			fee.setSem(feereq.getSem());
			fee.setDueDate(duedate);


			fee.setId(0);
			fee.setFeeNeedTOPay(0);
			fee.setActualFee(0);
			fee.setConcessionPercantage(0);
			fee.setDate(new Date(new java.util.Date().getTime()));

			fee.setFeePaid(0);
			System.out.println(fee.getDate()+" > "+(fee.getDueDate()));
			if( fee.getDate().after(fee.getDueDate())){
				fee.setFine(500);
				fee.setDue(500);
				//System.out.println(fee.getDate()+" grt fine added "+(fee.getDueDate()));
				logger.info(fee.getDate()+" > "+fee.getDueDate() +" so fine 500 added" );
				
			}else{
				//System.out.println(fee.getDate()+" lss no fine added  "+(fee.getDueDate()));
				logger.info(fee.getDate()+" < "+fee.getDueDate() +" so fine 0 " );
				fee.setFine(0);
				fee.setDue(0);
			}
		
			fee.setPaymentnumber(0);

			




		}
		System.out.println("feee ends"+fee);

		/**
		 * fetching the fee particular that is already paid
		 * */ 
		List<FeeParticulars> lsfeeParticulars=null;
		if(fee.getId()>0){

			try {
				lsfeeParticulars=feesDAO.fetchFeeParticularsUSfid(fee.getId());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while fetching feeParticulars detail  for the id "+fee.getId());
				logger.error(e);
				model.put("success",null );
				model.put("error"," Net work error" );
				return model; 
			}

		}


		if(lsfeeParticulars!=null && lsfeeParticulars.size()>0){

			/**
			 * fetching the other fee structure which is not already paid
			 * */ 
			try {

				if(feereq.getSem()==1){
					feeStructures=feesDAO.fetchfeeStructure(fee.getSem(), fee.getLastAcademicType(), fee.getCourse(), fee.getBatchStrYr(),fee.getId());
				}else{
					feeStructures=feesDAO.fetchfeeStructure(fee.getSem(), "Others", fee.getCourse(), fee.getBatchStrYr(),fee.getId());

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while fetching other fee structure which is not already paid detail   ");
				logger.error(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 
			}




		}else{
			lsfeeParticulars=new ArrayList<FeeParticulars>();
		}

		if(feeStructures!=null){
			for(int i=0;i<feeStructures.size();i++){
				FeeStructure feeStructure=feeStructures.get(i);
				FeeParticulars tempfeepart=new FeeParticulars();
				tempfeepart.setId(0);
				tempfeepart.setSid(feeStructure.getId());
				tempfeepart.setParticulars(feeStructure.getParticulars());
				tempfeepart.setAmount(feeStructure.getAmount());
				lsfeeParticulars.add(tempfeepart);
			}  
		}

		for(int i=0;i<lsfeeParticulars.size();i++){

			FeeParticulars tempfeepart=lsfeeParticulars.get(i);
			System.out.println(   tempfeepart.getId()+" , "+tempfeepart.getSid()+ " , "+tempfeepart.getParticulars()+" , "+  tempfeepart.getAmount());

		}  

		Map resp=new HashMap();
		resp.put("fee", fee);
		resp.put("lsfeeParticulars", lsfeeParticulars);
		
		logger.info("Exit the  PAYMENT  method ...");
		
		model.put("success",resp );
		model.put("error",null );
		return model; 
	} 

	@RequestMapping(value="/PaymentSubmit.htm",method = RequestMethod.POST)
	public @ResponseBody Map PaymentSubmit(@RequestBody Paymentreq paymentreq,Map<String, Object> model)
	{ 
		logger.info("Entered the  PaymentSubmit  method ...");
		System.out.println(paymentreq.getFee().getFeePaid());
		System.out.println(paymentreq.getLsfeeParticulars().get(0).getParticulars());
		System.out.println(paymentreq.getLsfeeParticulars().get(0).isIsnewone());
		/**
		 * updating the fees 
		 * */
		int fid=0;
		Fee fee=	paymentreq.getFee();
		System.out.println(fee.getId());
		if(fee.getId()>1){
			try {
				feesDAO.updateFee(fee);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while updating  fee  " );
				logger.error(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 
			}
			fid=fee.getId();

		}else{
			try {
				fid=	feesDAO.saveFee(fee);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while saving  fee  " );
				logger.error(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 
			}

		}
		logger.info("Fee was paid successfully");
		System.out.println("fee added succesfully");
		/**
		 * adding the fee history 
		 * */
		FeeHistorys feeHistorys=new FeeHistorys();
		feeHistorys.setActualFee(fee.getActualFee());
		feeHistorys.setDate(fee.getDate());
		feeHistorys.setDue(fee.getDue());
		feeHistorys.setFeeNeedTOPay(fee.getFeeNeedTOPay());
		feeHistorys.setFeePaid(fee.getFeePaid());
		feeHistorys.setFine(fee.getFine());
		feeHistorys.setPaymentnumber(fee.getPaymentnumber());
		feeHistorys.setPaymenttype(fee.getPaymenttype());
		feeHistorys.setPaymentdated(fee.getPaymentdated());
		feeHistorys.setPaymentdrawnat(fee.getPaymentdrawnat());
		feeHistorys.setConcessionType(fee.getConcessionType());
		feeHistorys.setConcessionPercantage(fee.getConcessionPercantage());
		feeHistorys.setSem(fee.getSem());
		feeHistorys.setFid(fid);
		feeHistorys.setReceiptNo(fee.getReceiptNo());
		feeHistorys.setRemarks(fee.getRemarks());
		
		Integer id=null;
		try {
			id=	feesDAO.addFeeHistorys(feeHistorys);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while adding fee Historys  " );
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		logger.info("Fee history was added successfully");
	//	System.out.println("fee history added succesfully");

		/*	//error is inside 
		 * int id=0;
			 try {
					id=feesDAO.fetchFeeHistorys(feeHistorys);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					model.put("success",null );
					model.put("error","you didn't make any change" );
					return model; 
				}

			 System.out.println("fee id is gettt succesfully");*/

		List<FeeParticulars> lsfeeParticulars=paymentreq.getLsfeeParticulars();

		for(int i=0;i<lsfeeParticulars.size();i++){
			try {	  
				FeeParticulars tempfeepart=lsfeeParticulars.get(i);
				tempfeepart.setFid(fid);



				tempfeepart.setDate(feeHistorys.getDate());

				/*	  error inside it

				  if(tempfeepart.getId()==0&& tempfeepart.isIsnewone()==true){
					  feesDAO.addFeeParticulars(tempfeepart);
				  }
				  if(tempfeepart.getId()!=0&& tempfeepart.isIsnewone()==false){
					  tempfeepart.setOverrided(true);
					  feesDAO.updateFeeParticulars(tempfeepart);
				  }

				  new one down
				 */
				if(tempfeepart.getId()!=0){
					tempfeepart.setOverrided(true);
					feesDAO.updateFeeParticulars(tempfeepart);
					logger.info("updating the  old Fee Particulars with Overrided=true was done successfully");
				} 
				if(tempfeepart.isIsnewone()==true){
					tempfeepart.setHid(id);
					tempfeepart.setId(0); 
					tempfeepart.setOverrided(false);
					feesDAO.addFeeParticulars(tempfeepart);
					logger.info("adding the  new Fee Particulars  was done successfully");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while updating fee Particulars  " );
				logger.error(e);
				model.put("success",null );
				model.put("error","Net work error" );
				return model; 
			}


		}  
		logger.info(" fee particulars are added succesfully");
		model.put("success","Payment was done successfully" );
		model.put("error",null );
		return model; 

	}

	@RequestMapping(value="/HISTORY.htm",method = RequestMethod.POST)
	public @ResponseBody Map HISTORY(@RequestBody Feereq feereq,Map<String, Object> model)
	{ 
		logger.info("Entered the  HISTORY  method ...");
		System.out.println("inside the class"+feereq.getToken());
		/**
		 * fetching basic student info using app or roll number
		 * */
		StudentFormInfo studentInfo=null;
		try {
			if("ano".equalsIgnoreCase(feereq.getType())){

				studentInfo=feesDAO.fetchStudentInfoUSAppNo(feereq.getNum());

			}
			if("rno".equalsIgnoreCase(feereq.getType())){

				studentInfo=feesDAO.fetchStudentInfoUSRolNo(feereq.getNum());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching StudentFormInfo detail  for the app/rol number "+feereq.getNum());
			logger.error(e);
			model.put("success",null );
			model.put("error"," Net work error" );
			return model; 
		}
		/**
		 * checking that student is admitted or not
		 * */
		if(studentInfo!=null){
			System.out.println(studentInfo.getName());
			System.out.println(studentInfo.getApplicationNo());
			System.out.println(studentInfo.getRollNo());

		}else{
			logger.info("user entered the  Invalid app/roll number " +feereq.getNum());
			model.put("success",null );
			model.put("error","Invalid app/roll number" );
			return model;

		}
		/**
		 * checking that fee for the semester is paid for first time or already paid
		 * */
		Fee fee=null;
		try {

			fee=feesDAO.fetchFeeUSAppNo(studentInfo.getApplicationNo(),feereq.getSem());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching fee detail  for the app number "+studentInfo.getApplicationNo()+" , "+feereq.getSem());
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		/**
		 * checking that history is available or not 
		 * */
		if(fee==null){

			model.put("success",null );
			model.put("error","No history is available. Please make payment" );
			return model; 
		}

		List<FeeHistorys> lsfeeHistorys=null;

		/**
		 * fetching the fee history value from the table
		 * */
		try {

			lsfeeHistorys=feesDAO.fetchLsFeeHistorys(fee.getId());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching FeeHistorys detail  for the id "+fee.getId()+"  ");
			logger.error(e);
			model.put("success",null );
			model.put("error"," Net work error" );
			return model; 
		}
		logger.info(" FeeHistorys are added succesfully");
		model.put("success",lsfeeHistorys );
		model.put("error",null );
		return model; 
	}


	@RequestMapping(value="/PARTICULARHISTORY.htm",method = RequestMethod.POST)
	public @ResponseBody Map PARTICULARHISTORY(@RequestBody Base base,@QueryParam("hid") int hid,Map<String, Object> model)
	{ 

		logger.info("Entered the  PARTICULARHISTORY  method ...");

		List<FeeParticulars> lsfeeParticulars=null;

		/**
		 * fetching the fee history value from the table
		 * */
		try {

			lsfeeParticulars=feesDAO.fetchLsFeeParticulars(hid);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching FeeParticulars detail  for the id "+hid+"  ");
			logger.error(e);
			model.put("success",null);
			model.put("error"," Network error" );
			return model; 
		}
		
		model.put("success",lsfeeParticulars);
		model.put("error",null );
		return model; 
	} 


	@RequestMapping(value="/canceltransaction.htm",method = RequestMethod.POST)
	public @ResponseBody Map canceltransaction(@RequestBody Base base,@QueryParam("hid") int hid,@QueryParam("amount") double amount,Map<String, Object> model)
	{ 
		logger.info("Entered the  canceltransaction  method ...");
		DecimalFormat df = new DecimalFormat("###.##");
		System.out.println("kilobytes (DecimalFormat) : " + df.format(amount));
		System.out.println(hid +" "+amount);

		FeeHistorys feeHistorys=null;
	
		try {

			feeHistorys=feesDAO.fetchFeeHistorysUsid(hid);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching FeeHistorys detail  for the id "+hid+"  ");
			logger.error(e);
			model.put("success",null);
			model.put("error"," Net work error" );
			return model; 
		}
		
		Fee fee=null;
		try {

			fee=feesDAO.fetchFeeUSid(feeHistorys.getFid());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching fee detail  for the id "+feeHistorys.getFid()+"  ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		
		fee.setDue(Math.round((fee.getDue()+amount)*100.0)/100.0);
		fee.setFeePaid(Math.round((fee.getFeePaid()-amount)*100.0)/100.0);
		
		
	
		
		feeHistorys.setActualFee(fee.getActualFee());

		feeHistorys.setDate(new Date(new java.util.Date().getTime()));
		feeHistorys.setDue(fee.getDue());
		feeHistorys.setFeeNeedTOPay(fee.getFeeNeedTOPay());
		feeHistorys.setFeePaid(fee.getFeePaid());

		feeHistorys.setFine(fee.getFine());
		feeHistorys.setId(0);
		feeHistorys.setPaymenttype("Cheque_Reverted");
		/*	
		feeHistorys.setConcessionPercantage(concessionPercantage);
		feeHistorys.setConcessionType(concessionType);
		feeHistorys.setFid(fee.getId());
		feeHistorys.setPaymentdated(paymentdated);
		feeHistorys.setPaymentdrawnat(paymentdrawnat);
		feeHistorys.setPaymentnumber(paymentnumber);
		feeHistorys.setPaymenttype(paymenttype);
		feeHistorys.setReceiptNo(receiptNo);
		feeHistorys.setSem(sem);
		*/
		feeHistorys.setRemarks("Revert Trancation Log ");
		/**
		 * 		update the fee and fee history table 
		 */
		try {

			feesDAO.updateFeesaveHistorys(fee,feeHistorys,hid);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while updating  fee and fee history   ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}	
		logger.info("Transaction  reverted successfully ...");
		List<FeeHistorys> lsfeeHistorys=null;

		/**
		 * fetching the fee history with new reverted transaction value from the table
		 * */
		try {

			lsfeeHistorys=feesDAO.fetchLsFeeHistorys(fee.getId());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching feeHistorys detail  for the id "+fee.getId()+"  ");
			logger.error(e);
			model.put("success",null );
			model.put("error","Net work error" );
			return model; 
		}
		logger.info("Leaving the  cancelTransaction  method ...");
		model.put("success",lsfeeHistorys );
		model.put("error",null );
		return model;
		
	
		
	} 



}
