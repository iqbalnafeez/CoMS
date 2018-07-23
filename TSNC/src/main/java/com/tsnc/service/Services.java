package com.tsnc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tsnc.dao.ServicesDAO;
import com.tsnc.dao.StructureDAO;
import com.tsnc.model.Base;
import com.tsnc.model.Courses;
import com.tsnc.model.Location;
import com.tsnc.model.Login;

@Controller

@RequestMapping("/Services")
public class Services {
	@Autowired
	private ServicesDAO servicesDAO;
	@Value("${r_photoFile}")
	String r_path;
	@Value("${rw_photoFile}")
	String rw_path;
	
	@RequestMapping(value="/Courses.htm",method = RequestMethod.POST)
	public @ResponseBody Map courses(@RequestBody Base base,Map<String, Object> model)
	{ System.out.println("getting courses");
	String token=base.getToken();
	System.out.println(base.getToken());
	Login login=null;
			
	try {
		
		login=(Login)servicesDAO.fetchLoginUStoken(token);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		model.put("success",null );
		model.put("error","n/w error " );
		return model;
	}
	
		
		if(login==null){
			model.put("success",null );
			model.put("error","Invalid token" );
			return model; 
		}
		
			System.out.println("Role "+login.getRole()+" user "+login.getUsername()); 

			/*access privelage goes here*/ 
	List<Courses> courseslst=null;
		  try {
			  courseslst=servicesDAO.fetchallCourse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 model.put("success",null);
				model.put("error","Network error" );
				return model; 
		}
		  for(int i=0;i<courseslst.size();i++){
			  System.out.println(courseslst.get(i));
		  }
		    model.put("success",courseslst);
			model.put("error",null );
			return model; 
	} 
	
	
	@RequestMapping(value="/Locations.htm",method = RequestMethod.POST)
	public @ResponseBody Map locations(@RequestBody Base base,Map<String, Object> model)
	{ 
		String token=base.getToken();
		System.out.println("getting Locations");
		System.out.println(token);
		Login login=null;
				
		try {
			
			login=(Login)servicesDAO.fetchLoginUStoken(token);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.put("success",null );
			model.put("error","n/w error " );
			return model;
		}
		
			
			if(login==null){
				model.put("success",null );
				model.put("error","Invalid token" );
				return model; 
			}
			
				System.out.println("Role "+login.getRole()+" user "+login.getUsername()); 

				/*access privelage goes here*/ 
	List<Location> locationlst=null;
		  try {
			  locationlst=servicesDAO.fetchallLocation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 model.put("success",null);
				model.put("error","Network error" );
				return model; 
		}
		  for(int i=0;i<locationlst.size();i++){
			  System.out.println(locationlst.get(i));
		  }
		    model.put("success",locationlst);
			model.put("error",null );
			return model; 
	} 
	
	
	@RequestMapping(value="/uploadphoto.htm",method = RequestMethod.POST)
	public @ResponseBody  Map<String,Object>  uploadBulkQuestion(@FormParam("file")MultipartHttpServletRequest request, @FormParam("token") String token, Map<String,Object> model) 
	{
		System.out.println(token);
		Login login=null;
				
		try {
			
			login=(Login)servicesDAO.fetchLoginUStoken(token);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.put("success",null );
			model.put("error","n/w error " );
			return model;
		}
		
			
			if(login==null){
				model.put("success",null );
				model.put("error","Invalid token" );
				return model; 
			}
			
				System.out.println("Role "+login.getRole()+" user "+login.getUsername()); 

				/*access privelage goes here*/
				

			
			Iterator<String> itr =  request.getFileNames();
			MultipartFile files = request.getFile(itr.next());
			System.out.println(files);

			String fullpath=null;
			String filename=null;
			File theDir = new File(rw_path);

			// if the directory does not exist, create it
			if (!theDir.exists()) {
				System.out.println(" sss DIR created");  
				boolean result =  theDir.mkdirs();

				if(result) {    
					System.out.println("DIR created");  
				}
			}

			try {
			if(null != files && files.getSize() > 0) {
				System.out.println(files.getName());
				InputStream inputStream=null;

			
					inputStream = files.getInputStream();
				
				// && and new Date() is used to avoid overwriting of file
				java.util.Date dd=new java.util.Date();
				String s = "_$_"+String.valueOf(dd.getTime());
				filename=files.getOriginalFilename().replace(".",s+".");
				System.out.println(filename);
				
				//System.out.println(filename.replace(".",s+"."));
				fullpath = rw_path+filename;
				OutputStream out = new FileOutputStream(new File(
						fullpath));
				int read = 0;
				byte[] bytes = new byte[1024];


				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
				

			}
			 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					model.put("success",null );
					model.put("error","file handling error " );
					return model;
				}
			System.out.println(r_path+filename);
			model.put("success",r_path+filename);
			model.put("error",null );
			//model.put("success", "D:/TSNC/photos/tiger11403619304006.jpg");
			return model;
		
	
		
		
	}
	
}
