package com.tsnc.controllers.secureCtrl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsnc.dao.StructureDAO;
import com.tsnc.model.Base;
import com.tsnc.model.Concession;
import com.tsnc.model.Courses;
import com.tsnc.model.FeeStructure;
import com.tsnc.model.FineStructure;
import com.tsnc.model.Lsconcession;
import com.tsnc.model.Lscourse;
import com.tsnc.model.LsfeeStructure;
import com.tsnc.model.LsfineStructure;



@Controller

@RequestMapping("/StructureCtrl")
public class StructureCtrl {
	@Autowired
	private StructureDAO structureDAO;
	
	private static Logger logger = Logger.getLogger(StructureCtrl.class);
	
/*courses start  +++*/
	@RequestMapping(value="/Courses/GO.htm",method = RequestMethod.POST)
	public @ResponseBody Map coursesGO(@RequestBody Base base,@QueryParam("batchStrYr") int batchStrYr,Map<String, Object> model)
	{ logger.info("Entered the  coursesGO  method ...");
		/*  System.out.println("Token  "+base.getToken()+" batchStrYr "+batchStrYr);*/
		  List<Courses> courseslst=null;
		  try {
			  courseslst=structureDAO.fetchCoursesUSbatchStrYr(batchStrYr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching course detail  for batchstartyear "+batchStrYr);
			logger.error(e);
			 model.put("success",null);
				model.put("error","Network error" );
				return model; 
		}
		    model.put("success",courseslst);
			model.put("error",null );
			return model; 
	} 

	@RequestMapping(value="/Courses/SAVE.htm",method = RequestMethod.POST)
	public @ResponseBody Map coursesSAVE(@RequestBody Lscourse lscourse,Map<String, Object> model)
	{  
		logger.info("Entered the  coursesSAVE  method ...");
	//	System.out.println(lscourse.getToken());
		  List<Courses> courseslst=lscourse.getLscourse();
			
		  try {
			  
			  for(int i=0;i<courseslst.size();i++){
					Courses courses=courseslst.get(i);
					System.out.println("i "+i+" id "+courses.getId());
					if(courses.getId()==0){
						/*courses.setId(id);*/
						structureDAO.addCourse(courses);
					}else{
						 structureDAO.updateCourse(courses);
					}
					
				}
			 /* structureDAO.updateCourses(courseslst);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while updating courses  ");
			logger.error(e);
				model.put("success",null);
				model.put("error"," Network error" );
				return model; 
		}
		    model.put("success","  Course added/modified successfully");
			model.put("error",null );
			return model; 
	} 

	@RequestMapping(value="/Courses/DELETE.htm",method = RequestMethod.POST)
	public @ResponseBody Map coursesDELETE(@RequestBody Base base,@QueryParam("did") int[] did,Map<String, Object> model)
	{ 
		
		logger.info("Entered the  coursesDELETE  method ...");
			
		  try {
			  
			  for(int i=0;i<did.length;i++){
				  structureDAO.deleteCourse(did[i]);
					
				}
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while  courses are deleted  ");
			logger.error(e);
			  model.put("success",null);
				model.put("error","Network Error" );
				return model; 
		}
		    model.put("success"," Course deleted successfully");
			model.put("error",null );
			return model; 
	} 
	/*courses END  +++*/
	
/*	concession start ***
*/		@RequestMapping(value="/Concession/GO.htm",method = RequestMethod.POST)
		public @ResponseBody Map concessionGO(@RequestBody Base base,Map<String, Object> model)
		{  
	      logger.info("Entered the  concessionGO  method ...");
			//  System.out.println("Token  "+base.getToken());
			  List<Concession> concessionList=null;
			  try {
				  concessionList=structureDAO.fetchConcession();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while fetching courses   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error","Network error" );
					return model; 
			}
			    model.put("success",concessionList);
				model.put("error",null );
				return model; 
		} 
		@RequestMapping(value="/Concession/SAVE.htm",method = RequestMethod.POST)
		public @ResponseBody Map concessionSAVE(@RequestBody Lsconcession lsconcession,Map<String, Object> model)
		{  logger.info("Entered the  concessionSAVE  method ...");
			//System.out.println(lsconcession.getToken());
			  List<Concession> concessionlst=lsconcession.getLsconcession();
				
			  try {
				  
				  for(int i=0;i<concessionlst.size();i++){
						Concession concession=concessionlst.get(i);
						System.out.println("i "+i+" id "+concession.getId());
						if(concession.getId()==0){
						
							structureDAO.addConcession(concession);;
						}else{
							 structureDAO.updateConcession(concession);
						}
						
					}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while updating concession   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error"," Network error " );
					return model; 
			}
			    model.put("success","  Concession added/modified successfully");
				model.put("error",null );
				return model; 
		} 

		@RequestMapping(value="/Concession/DELETE.htm",method = RequestMethod.POST)
		public @ResponseBody Map concessionDELETE(@RequestBody Base base,@QueryParam("did") int[] did,Map<String, Object> model)
		{ 
			 logger.info("Entered the  concessionDELETE  method ...");
			
				
			  try {
				  
				  for(int i=0;i<did.length;i++){
					  structureDAO.deleteConcession(did[i]);
						
					}
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while deleting the concession   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error"," Network error" );
					return model; 
			}
			    model.put("success"," Concession deleted successfully");
				model.put("error",null );
				return model; 
		} 
		
		/*	concession end ***
		*/	
		/*courses start  +++*/
		@RequestMapping(value="/feeStructure/GO.htm",method = RequestMethod.POST)
		public @ResponseBody Map feeStructureGO(@RequestBody FeeStructure feeStructure,Map<String, Object> model)
		{  logger.info("Entered the  feeStructureGO  method ...");
			  /*System.out.println(feeStructure);
			 System.out.println(feeStructure.getCourse());*/
			  List<FeeStructure> feeStructurelst=null;
			  try {
			 feeStructurelst=structureDAO.fetchfeeStructure(feeStructure.getSem(),feeStructure.getLastAcademicType(),feeStructure.getCourse(),feeStructure.getBatchStrYr());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while fetching  the fee Structure   ");
				logger.error(e);
				    model.put("success",null);
					model.put("error","Network error" );
					return model; 
			}
			    model.put("success",feeStructurelst);
				model.put("error",null );
				return model; 
		} 

		private Object key(Map feestr) {
			// TODO Auto-generated method stub
			return null;
		}

		@RequestMapping(value="/feeStructure/SAVE.htm",method = RequestMethod.POST)
		public @ResponseBody Map feeStructureSAVE(@RequestBody LsfeeStructure lsfeeStructure,Map<String, Object> model)
		{ logger.info("Entered the  feeStructureSAVE  method ...");
			System.out.println(lsfeeStructure.getToken());
			  List<FeeStructure> feeStructurelst=lsfeeStructure.getLsfeeStructure();
				
			  try {
				  
				  for(int i=0;i<feeStructurelst.size();i++){
					  FeeStructure feeStructure=feeStructurelst.get(i);
						System.out.println("i "+i+" id "+feeStructure.getId());
						if(feeStructure.getId()==0){
							/*courses.setId(id);*/
							structureDAO.addfeeStructure(feeStructure);
						}else{
							 structureDAO.updatefeeStructure(feeStructure);
						}
						
					}
				 /* structureDAO.updateCourses(courseslst);*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while updating  the fee Structure   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error","courses update Fails" );
					return model; 
			}
			    model.put("success","Fee particulars added/modified successfully.");
				model.put("error",null );
				return model; 
		} 

		@RequestMapping(value="/feeStructure/DELETE.htm",method = RequestMethod.POST)
		public @ResponseBody Map feeStructureDELETE(@RequestBody Base base,@QueryParam("did") int[] did,Map<String, Object> model)
		{ 
			 logger.info("Entered the  feeStructureDELETE  method ...");
			
				
			  try {
				  
				  for(int i=0;i<did.length;i++){
					  structureDAO.deleteFeeStructure(did[i]);
						
					}
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while deleting  the fee Structure   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error"," Network error " );
					return model; 
			}
			    model.put("success"," FeeStructure deleted successfully");
				model.put("error",null );
				return model; 
		} 
		/*courses END  +++*/
		
		
		
/*		fine start  ************ */
		@RequestMapping(value="/fine/GO.htm",method = RequestMethod.POST)
		public @ResponseBody Map fineGO(@RequestBody Base base,@QueryParam("batchStrYr") int batchStrYr,Map<String, Object> model)
		{  logger.info("Entered the  fineGO  method ...");
			  System.out.println("Token  "+base.getToken());
			  List<FineStructure> fineStructureList=null;
			  try {
				  fineStructureList=structureDAO.fetchfineStructure(batchStrYr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while fetching  the fine Structure   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error","Network error" );
					return model; 
			}
			  
			  
			    model.put("success",fineStructureList);
				model.put("error",null );
				return model; 
		} 
		@RequestMapping(value="/fine/SAVE.htm",method = RequestMethod.POST)
		public @ResponseBody Map fineSAVE(@RequestBody LsfineStructure lsfineStructure,Map<String, Object> model)
		{  logger.info("Entered the  fineSAVE  method ...");
			System.out.println(lsfineStructure.getToken());
			  List<FineStructure> fineStructurelst=lsfineStructure.getLsfineStructure();
				
			  try {
				  
				  for(int i=0;i<fineStructurelst.size();i++){
					  FineStructure fineStructure=fineStructurelst.get(i);
						System.out.println("i "+i+" id "+fineStructure.getId());
						if(fineStructure.getId()==0){
						
							structureDAO.addFineStructure(fineStructure);;
						}else{
							 structureDAO.updateFineStructure(fineStructure);
						}
						
					}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error occurred while updateing  the fine Structure   ");
				logger.error(e);
				 model.put("success",null);
					model.put("error"," Network error" );
					return model; 
			}
			    model.put("success","Fine date added/modified successfully");
				model.put("error",null );
				return model; 
		} 

/*		fine end+++++++++*/
		
}
