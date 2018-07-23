package com.tsnc.controllers.secureCtrl;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsnc.aop.AopCont;
import com.tsnc.dao.AppSaleDAO;
import com.tsnc.dao.IndexDAO;
import com.tsnc.model.ApplicationSale;
import com.tsnc.model.Login;
@Controller
@RequestMapping("/AppSaleCtrl")
public class AppSalesCtrl {
	@Autowired
	private AppSaleDAO appSaleDAO;
	private static Logger logger = Logger.getLogger(AppSalesCtrl.class);
	@RequestMapping(value="/appSubmit.htm",method = RequestMethod.POST)
	/**	
	 * This method add application form sale detail to db
	 *	
	 *	*/
	public @ResponseBody Map appSubmit(@RequestBody ApplicationSale applicationSale,Map<String, Object> model)
	{  
		logger.info("Entered the  appSubmit  method ...");
		logger.info("Application Sale detail "+applicationSale.getAmountPaid() +" "+applicationSale.getCourse()+" "+applicationSale.getApplicationNo());
	//	System.out.println(applicationSale.getAmountPaid() +" "+applicationSale.getCourse()+" "+applicationSale.getApplicationNo());
		/**	
		 * checking that App/receipt Num is new or old one
		 *	
		 *	*/
		try {
			List<ApplicationSale> temp=appSaleDAO.fetchApplicationSale(applicationSale.getApplicationNo(),applicationSale.getReciptNo());
		if(temp!=null&&temp.size()>0){
			logger.error("App/Receipt num is  already exist  "+applicationSale.getApplicationNo()+"/"+applicationSale.getReciptNo());
			model.put("success",null );
			model.put("error","Application/Receipt number already exists" );
			return model;
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.put("success",null );
			model.put("error"," Network error" );
			return model;
		}
		
		try {
			appSaleDAO.addApplicationSale(applicationSale);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error in adding the application sale detail ");
			model.put("success",null );
			model.put("error"," Network error" );
			return model;
		}
		logger.info("Application sale  was done succesfully ");
		model.put("success"," Record added succesfully" );
		model.put("error",null );
		return model;
	}
}