package com.tsnc.controllers.secureCtrl;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.validation.Path;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.QueryParam;

import org.hibernate.Query;
import org.joda.time.Days;
import org.joda.time.DateTime;

import javax.ws.rs.core.Context;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.grid.ColumnTitleGroupBuilder;
import net.sf.dynamicreports.report.builder.grid.Grids;
import net.sf.dynamicreports.report.builder.style.PenBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.Style;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.net.URL;
import org.bouncycastle.asn1.cmp.CMPCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import scala.annotation.meta.getter;

import java.math.BigInteger;

import com.tsnc.dao.ReportDAO;
import com.tsnc.dao.StructureDAO;
import com.tsnc.model.AdmissionReportReq;
import com.tsnc.model.AppSaleCount;
import com.tsnc.model.AppSaleReport;
import com.tsnc.model.Base;
import com.tsnc.model.Courses;
import com.tsnc.model.Customer;
import com.tsnc.model.MasterReport;
import com.tsnc.model.Fee;
import com.tsnc.model.FeesReportReq;
import com.tsnc.model.SaleReportReq;
import com.tsnc.model.StudentAdmittedReport;
import com.tsnc.model.StudentFormInfo;
import com.tsnc.model.SubjectMarks;
@Controller

@RequestMapping("/ReportCtrl")
public class ReportCtrl {
	@Autowired
	private ReportDAO reportDAO;
	/*application sale Report Start  +++++++++++++++++++*/

	// file location

	@Value("${r_reportFile}")
	String r_path;
	@Value("${rw_reportFile}")
	String rw_path;

	private static Logger logger = Logger.getLogger(ReportCtrl.class);

	private StyleBuilder boldCenteredStyle = DynamicReports.stl.style(DynamicReports.stl.style().bold())

			.setHorizontalAlignment(HorizontalAlignment.CENTER);

	private StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle)

			.setBorder(DynamicReports.stl.pen1Point())

			.setBackgroundColor(Color.LIGHT_GRAY);
	private StyleBuilder columnStyle= DynamicReports.stl.style(boldCenteredStyle)

			.setBorder(DynamicReports.stl.pen1Point());

	@RequestMapping(value="/appsalereport/GO.htm",method = RequestMethod.POST)
	public @ResponseBody Map appsalereportGo(@RequestBody SaleReportReq saleReportReq,Map<String, Object> model)
	{ 
		
		logger.info("Entered the  appsalereportGo  method ...");
		
		String sql="SELECT course,salesLocation,date, count(*) as count FROM application_sales ";
		sql=sql+"WHERE 	(date BETWEEN '"+saleReportReq.getFrmdate()+"' AND '"+saleReportReq.getTodate()+"') ";
		if("ALL".equalsIgnoreCase(saleReportReq.getCourse())){
			sql=sql+" ";
		}else{
			sql=sql+" AND course='"+saleReportReq.getCourse()+"' ";
		}
		if("ALL".equalsIgnoreCase(saleReportReq.getSalesLocation())){
			sql=sql+" ";
		}else{
			sql=sql+" AND salesLocation='"+saleReportReq.getSalesLocation()+"' ";
		}

		sql=sql+" GROUP BY course,salesLocation,date WITH  ROLLUP";
		System.out.println(sql);
		logger.info("SQL to get the count "+sql);
		
		List<Map<String,Object>> countdb=null;
		try {
			 countdb=reportDAO.fetchappsalecount(sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("Error occurred while fetching appsalecount detail   ");
			logger.error(e1);
			model.put("success",null );
			model.put("error"," Network error" );
			return model;
		}
	
		
		if(countdb==null||countdb.size()<1){
			logger.info("No Applciation was sold for given the inputs(course/location/date) ");
			model.put("success",null );
			model.put("error","No Applciation was sold for given the inputs(course/location/date)" );
			return model;
		}
	/*
	 * 	for(int i=0;i<countdb.size();i++){
			System.out.println(countdb.get(i));
			AppSaleCount	appSaleCount =(AppSaleCount) countdb.get(i);
			System.out.println(appSaleCount.getCourse()+"  "+appSaleCount.getSalecount());
			
		}
		*/
		
		int dffdays =(int)( (  saleReportReq.getTodate().getTime()-saleReportReq.getFrmdate().getTime()) / (1000 * 60 * 60 * 24));
		System.out.println(dffdays +" number of day gap");
		
		List<AppSaleReport> lssalereport = new ArrayList<AppSaleReport>();
		AppSaleReport appSaleReport=new AppSaleReport();
		appSaleReport.setS_no(1);
	
		for (Map<String, Object> obj : countdb) {
		   System.out.println(obj.get("course")+" "+obj.get("salesLocation")+" "+obj.get("date")+" "+obj.get("count"));
		   if(dffdays<8 && obj.get("date")!=null){
				int saleday =(int)( (  saleReportReq.getTodate().getTime()-((Date) obj.get("date")).getTime()) / (1000 * 60 * 60 * 24));
			   String chk1 =   (String)obj.get("salesLocation");
				if("COLLEGE".equalsIgnoreCase(chk1)){
					switch(saleday){
					case 0:
						appSaleReport.setCol_sale_7((BigInteger) obj.get("count"));
						break;
					case 1:
						appSaleReport.setCol_sale_6((BigInteger) obj.get("count"));
						break;
					case 2:
						appSaleReport.setCol_sale_5((BigInteger) obj.get("count"));
						break;
					case 3:
						appSaleReport.setCol_sale_4((BigInteger) obj.get("count"));
						break;
					case 4:
						appSaleReport.setCol_sale_3((BigInteger) obj.get("count"));
						break;
					case 5:
						appSaleReport.setCol_sale_2((BigInteger) obj.get("count"));
						break;
					case 6:
						appSaleReport.setCol_sale_1((BigInteger) obj.get("count"));
						break;
					}
					
				}else if("Dhun".equalsIgnoreCase(chk1)){
					
					switch(saleday){
					case 0:
						appSaleReport.setDhun_sale_7((BigInteger) obj.get("count"));
						break;
					case 1:
						appSaleReport.setDhun_sale_6((BigInteger) obj.get("count"));
						break;
					case 2:
						appSaleReport.setDhun_sale_5((BigInteger) obj.get("count"));
						break;
					case 3:
						appSaleReport.setDhun_sale_4((BigInteger) obj.get("count"));
						break;
					case 4:
						appSaleReport.setDhun_sale_3((BigInteger) obj.get("count"));
						break;
					case 5:
						appSaleReport.setDhun_sale_2((BigInteger) obj.get("count"));
						break;
					case 6:
						appSaleReport.setDhun_sale_1((BigInteger) obj.get("count"));
						break;
					}
				}else if("ICL(H.O)".equalsIgnoreCase(chk1)){
			
					switch(saleday){
					case 0:
						appSaleReport.setIcl_sale_7((BigInteger) obj.get("count"));
						break;
					case 1:
						appSaleReport.setIcl_sale_6((BigInteger) obj.get("count"));
						break;
					case 2:
						appSaleReport.setIcl_sale_5((BigInteger) obj.get("count"));
						break;
					case 3:
						appSaleReport.setIcl_sale_4((BigInteger) obj.get("count"));
						break;
					case 4:
						appSaleReport.setIcl_sale_3((BigInteger) obj.get("count"));
						break;
					case 5:
						appSaleReport.setIcl_sale_2((BigInteger) obj.get("count"));
						break;
					case 6:
						appSaleReport.setIcl_sale_1((BigInteger) obj.get("count"));
						break;
					}
				}else{
					System.err.println("Error unknown location contact admin");
					logger.info("Error unknown location contact admin ");
				}
		
		 
	
		   
		   }
		   if(obj.get("salesLocation")==null && obj.get("course")!=null){
			   appSaleReport.setCourses((String) obj.get("course"));
				appSaleReport.setAll_total((BigInteger) obj.get("count"));
				System.out.println(" ** "+appSaleReport.getS_no() +" "+appSaleReport.getCourses());
			   lssalereport.add(appSaleReport);
			   int nsnum=appSaleReport.getS_no();
			   appSaleReport=new AppSaleReport();
	    	   appSaleReport.setS_no(nsnum+1);
			
		   }
		   if(obj.get("date")==null && obj.get("salesLocation")!=null){
			String chk =   (String)obj.get("salesLocation");
			if("COLLEGE".equalsIgnoreCase(chk)){
				appSaleReport.setCol_total((BigInteger) obj.get("count"));
			}else if("Dhun".equalsIgnoreCase(chk)){
				appSaleReport.setDhun_total((BigInteger) obj.get("count"));
			}else if("ICL(H.O)".equalsIgnoreCase(chk)){
				appSaleReport.setIcl_total((BigInteger) obj.get("count"));
			}else{
				System.err.println("Error unknown location contact admin");
				logger.info("Error unknown location contact admin "+chk);
			}
			  
			   
		   }
		     
		}

		
	
		JasperReportBuilder report = DynamicReports.report();//a new report
	
		TextColumnBuilder<Integer>     snoColumn       = Columns.column("S.No", "s_no",   DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(30);

		TextColumnBuilder<String>     coursesColumn       = Columns.column("Courses", "courses",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setFixedWidth(115);
		
		TextColumnBuilder<BigInteger>    collegeTlColumn   = Columns.column("Total", "col_total", DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<BigInteger> dhunTlColumn  = Columns.column("Total", "dhun_total", DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<BigInteger> iclTlColumn  = Columns.column("Total", "icl_total", DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<BigInteger> allTlColumn  = Columns.column("Application Total", "all_total", DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		ColumnTitleGroupBuilder appsales =null;
		 DateFormat dateFormat = new SimpleDateFormat("dd-MM");
    /*     Date myDate = new Date(System.currentTimeMillis());
         System.out.println("result is "+ dateFormat.format(myDate));*/
         Calendar cal = Calendar.getInstance();
         cal.setTime(saleReportReq.getTodate());
         cal.add(Calendar.DATE, -6);
         System.out.println(dateFormat.format(cal.getTime()));
          String no1=dateFormat.format(cal.getTime());
          cal.setTime(saleReportReq.getTodate());
          cal.add(Calendar.DATE, -5);
          System.out.println(dateFormat.format(cal.getTime()));
         String no2=dateFormat.format(cal.getTime());
         cal.setTime(saleReportReq.getTodate());
         cal.add(Calendar.DATE, -4);
         System.out.println(dateFormat.format(cal.getTime()));
         String no3=dateFormat.format(cal.getTime());
         cal.setTime(saleReportReq.getTodate());
         cal.add(Calendar.DATE, -3);
         System.out.println(dateFormat.format(cal.getTime()));
         String no4=dateFormat.format(cal.getTime());
         cal.setTime(saleReportReq.getTodate());
         cal.add(Calendar.DATE, -2);
         System.out.println(dateFormat.format(cal.getTime()));
         String no5=dateFormat.format(cal.getTime());
         cal.setTime(saleReportReq.getTodate());
         cal.add(Calendar.DATE, -1);
         System.out.println(dateFormat.format(cal.getTime()));
         String no6=dateFormat.format(cal.getTime());
         cal.setTime(saleReportReq.getTodate());
    /*     cal.add(Calendar.DATE, -10);*/
         System.out.println(dateFormat.format(cal.getTime()));
         String no7=dateFormat.format(cal.getTime());
         
		if(dffdays<8){
			report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		TextColumnBuilder<BigInteger>     col_sale_1Column       = Columns.column(no1, "col_sale_1",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     col_sale_2Column       = Columns.column(no2, "col_sale_2",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     col_sale_3Column       = Columns.column(no3, "col_sale_3",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     col_sale_4Column       = Columns.column(no4, "col_sale_4",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     col_sale_5Column       = Columns.column(no5, "col_sale_5",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     col_sale_6Column       = Columns.column(no6, "col_sale_6",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     col_sale_7Column       = Columns.column(no7, "col_sale_7",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		
		TextColumnBuilder<BigInteger>     dhun_sale_1Column       = Columns.column(no1, "dhun_sale_1",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     dhun_sale_2Column       = Columns.column(no2, "dhun_sale_2",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     dhun_sale_3Column       = Columns.column(no3, "dhun_sale_3",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     dhun_sale_4Column       = Columns.column(no4, "dhun_sale_4",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     dhun_sale_5Column       = Columns.column(no5, "dhun_sale_5",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     dhun_sale_6Column       = Columns.column(no6, "dhun_sale_6",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     dhun_sale_7Column       = Columns.column(no7, "dhun_sale_7",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);

		TextColumnBuilder<BigInteger>     icl_sale_1Column       = Columns.column(no1, "icl_sale_1",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     icl_sale_2Column       = Columns.column(no2, "icl_sale_2",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     icl_sale_3Column       = Columns.column(no3, "icl_sale_3",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     icl_sale_4Column       = Columns.column(no4, "icl_sale_4",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     icl_sale_5Column       = Columns.column(no5, "icl_sale_5",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     icl_sale_6Column       = Columns.column(no6, "icl_sale_6",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<BigInteger>     icl_sale_7Column       = Columns.column(no7, "icl_sale_7",   DataTypes.bigIntegerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(30);
		
		if("Dhun".equalsIgnoreCase(saleReportReq.getSalesLocation())){
			report.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
			ColumnTitleGroupBuilder d = Grids.titleGroup("Dhun",dhun_sale_1Column,
					dhun_sale_2Column,dhun_sale_3Column,dhun_sale_4Column,dhun_sale_5Column,dhun_sale_6Column,dhun_sale_7Column, dhunTlColumn);
		     appsales = Grids.titleGroup("Application Sales", d);
		}else if("COLLEGE".equalsIgnoreCase(saleReportReq.getSalesLocation())){
			report.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
			ColumnTitleGroupBuilder c = Grids.titleGroup("College",col_sale_1Column,
					col_sale_2Column,col_sale_3Column,col_sale_4Column,col_sale_5Column,col_sale_6Column,col_sale_7Column, collegeTlColumn);
			 appsales = Grids.titleGroup("Application Sales", c);
		}else if("ICL(H.O)".equalsIgnoreCase(saleReportReq.getSalesLocation())){
			report.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
			ColumnTitleGroupBuilder i = Grids.titleGroup("ICL(H.O)",icl_sale_1Column,
					icl_sale_2Column,icl_sale_3Column,icl_sale_4Column,icl_sale_5Column,icl_sale_6Column,icl_sale_7Column, iclTlColumn);
			 appsales = Grids.titleGroup("Application Sales", i);
		}else{
		
			ColumnTitleGroupBuilder c = Grids.titleGroup("College",col_sale_1Column,
					col_sale_2Column,col_sale_3Column,col_sale_4Column,col_sale_5Column,col_sale_6Column,col_sale_7Column, collegeTlColumn);
			ColumnTitleGroupBuilder d = Grids.titleGroup("Dhun",dhun_sale_1Column,
					dhun_sale_2Column,dhun_sale_3Column,dhun_sale_4Column,dhun_sale_5Column,dhun_sale_6Column,dhun_sale_7Column, dhunTlColumn);
			ColumnTitleGroupBuilder i = Grids.titleGroup("ICL(H.O)",icl_sale_1Column,
					icl_sale_2Column,icl_sale_3Column,icl_sale_4Column,icl_sale_5Column,icl_sale_6Column,icl_sale_7Column, iclTlColumn);
			 appsales = Grids.titleGroup("Application Sales", c,d,i);
		}
		
	
			report.columns(
					snoColumn,
					coursesColumn,
					
					col_sale_1Column,col_sale_2Column,col_sale_3Column,col_sale_4Column,col_sale_5Column,col_sale_6Column,col_sale_7Column,
					collegeTlColumn,
					dhun_sale_1Column,dhun_sale_2Column,dhun_sale_3Column,dhun_sale_4Column,dhun_sale_5Column,dhun_sale_6Column,dhun_sale_7Column,
					dhunTlColumn,
					icl_sale_1Column,icl_sale_2Column,icl_sale_3Column,icl_sale_4Column,icl_sale_5Column,icl_sale_6Column,icl_sale_7Column,
					iclTlColumn,
					
					allTlColumn
					);
		}	else{
			if("Dhun".equalsIgnoreCase(saleReportReq.getSalesLocation())){
				ColumnTitleGroupBuilder d = Grids.titleGroup("Dhun", dhunTlColumn);     appsales = Grids.titleGroup("Application Sales", d);
			}else if("COLLEGE".equalsIgnoreCase(saleReportReq.getSalesLocation())){
				ColumnTitleGroupBuilder c = Grids.titleGroup("College", collegeTlColumn);
				appsales = Grids.titleGroup("Application Sales", c);
			}else if("ICL(H.O)".equalsIgnoreCase(saleReportReq.getSalesLocation())){
				ColumnTitleGroupBuilder i = Grids.titleGroup("ICL(H.O)", iclTlColumn); appsales = Grids.titleGroup("Application Sales", i);
			}else{
			
				ColumnTitleGroupBuilder c = Grids.titleGroup("College", collegeTlColumn);
				ColumnTitleGroupBuilder d = Grids.titleGroup("Dhun", dhunTlColumn);
				ColumnTitleGroupBuilder i = Grids.titleGroup("ICL(H.O)", iclTlColumn);	 appsales = Grids.titleGroup("Application Sales", c,d,i);

				appsales= Grids.titleGroup("Application Sales", c,d,i);
			}
			
		
		
			report.columns(
					snoColumn,
					coursesColumn,
				
					collegeTlColumn,
				
					dhunTlColumn,
					
					iclTlColumn,
					
					allTlColumn
					);
		}
		 DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		 columnTitleStyle.setFontSize(10);
		report
		.setColumnTitleStyle(columnTitleStyle) 
		.setColumnStyle(columnStyle)
		.highlightDetailEvenRows() ;
		
			report	.columnGrid(



						snoColumn,coursesColumn,appsales,allTlColumn

						) 


						.title(//title of the report
								Components.text("Application Sales Report From "+ dateFormat2.format(saleReportReq.getFrmdate())+" to "+  dateFormat2.format(saleReportReq.getTodate())+" ").setStyle(boldCenteredStyle)
							
								.setHorizontalAlignment(HorizontalAlignment.CENTER))
								//.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))//show page number on the page footer
								// .setDataSource("SELECT id, first_name, last_name, date FROM customers", connection);
								.setDataSource(new JRBeanCollectionDataSource(lssalereport));



			File down=null;
			File prev=null;
		try {
			//	report.show();//show the report

			prev=new File(rw_path+"ApplciationSaleReport.pdf");
			System.out.println(prev.getAbsolutePath() +" 111 "+prev.getPath());
			
			report.toPdf(new FileOutputStream(prev));//export the report to a pdf file
			
			if("odt".equalsIgnoreCase(saleReportReq.getFormat())){
				down=new File(rw_path+"ApplciationSaleReport.odt");
				report.toOdt(new FileOutputStream(down));
			}else if ("ods".equalsIgnoreCase(saleReportReq.getFormat())){
				down=new File(rw_path+"ApplciationSaleReport.ods");
				report.toOds(new FileOutputStream(down));
				
			}
			else if ("xls".equalsIgnoreCase(saleReportReq.getFormat())){
				down=new File(rw_path+"ApplciationSaleReport.xls");
				report.toXls(new FileOutputStream(down));
				
			}else{
				down=new File(rw_path+"ApplciationSaleReport.pdf");
			}
			
			
			
		/*	File f4=new File(rw_path+"ApplciationSaleReport.docs");
			report.toDocx(new FileOutputStream(f4));
			File f6=new File(rw_path+"ApplciationSaleReport.xml");
			report.toXml(new FileOutputStream(f6));
*/
	
		} /*catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while generating  the report file");
			logger.error(e);
			model.put("success",null );
			model.put("error"," Report file generation error" );
			return model;

		}
		
		Map<String,String> res=new HashMap<String,String>();
		res.put("downloadpath",r_path+down.getName());
		res.put("previewpath",r_path+prev.getName());
				model.put("success",res );
				model.put("error",null );
				return model;

		
	} 

	/*application sale Report end  +++++++++++++++++++*/
	
	
	
	/*Fee Report start  +++++++++++++++++++*/
	@RequestMapping(value="/feereport/GO.htm",method = RequestMethod.POST)
	public @ResponseBody Map feereportGo(@RequestBody FeesReportReq feesReportReq,Map<String, Object> model)
	{ 
		logger.info("Entered the  feereportGo  method ...");
		
		String sql="FROM Fee F  ";
		sql=sql+" WHERE 	(F.date BETWEEN '"+feesReportReq.getFrmdate()+"' AND '"+feesReportReq.getTodate()+"') ";
		if(!"ALL".equalsIgnoreCase(feesReportReq.getCourse())){
			sql=sql+" AND F.course='"+feesReportReq.getCourse()+"' ";
		}
		if(0!=feesReportReq.getSem()){
			sql=sql+"  AND  F.sem="+feesReportReq.getSem()+" ";
		}
		if(!"BOTH".equalsIgnoreCase(feesReportReq.getStatus())){
			if("UNPAID".equalsIgnoreCase(feesReportReq.getStatus())){
				sql=sql+"  AND  F.due > 0 ";
			}
			if("PAID".equalsIgnoreCase(feesReportReq.getStatus())){
				sql=sql+"  AND  F.due <= 0 ";
			}
			
			
		}
	/*	System.out.println( sql);*/
		
		logger.info("SQL to get the fee "+sql);
		List<Fee> feels=null;
		try {
			feels=reportDAO.fetchfeereport(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error occurred while fetching  the fee details");
			logger.error(e);
			model.put("success",null );
			model.put("error"," Network error" );
			return model;
			
		}
		
		
		System.out.println(feesReportReq.getCourse() +"  "+feesReportReq.getFormat()+" "+feesReportReq.getStatus());
		

		
		if(feels==null||feels.size()<1){
			model.put("success",null );
			model.put("error"," No fee  was paid  for the given  inputs" );
			return model;
		}
	
		for(int i=0;i<feels.size();i++){
			Fee fee=feels.get(i);
			if(fee.getDue()>=0){
				fee.setFeeowe(0);
			}else{
				fee.setFeeowe(fee.getDue()*(-1));
				fee.setDue(0);
			}
			feels.set(i, fee);
		}
		
		
		StyleBuilder borderedStyle = DynamicReports.stl.style( DynamicReports.stl.pen1Point());

	
		JasperReportBuilder report = DynamicReports.report();//a new report
		
		TextColumnBuilder<String>     nameColumn       = Columns.column("Name", "name",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT
				).setFixedWidth(90);

		TextColumnBuilder<String>     rollNoColumn       = Columns.column("RollNo", "rollNo",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setFixedWidth(60);
		
		TextColumnBuilder<String>    applicationNoColumn   = Columns.column("ApplicationNo", "applicationNo", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setFixedWidth(60);
		TextColumnBuilder<String> courseColumn  = Columns.column("Course", "course", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT)
				.setFixedWidth(120);
	
		TextColumnBuilder<Integer> semColumn  = Columns.column("Sem", "sem", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setFixedWidth(25);
		TextColumnBuilder<Double> actualFeeColumn  = Columns.column("ActualFee", "actualFee", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setFixedWidth(50);
		TextColumnBuilder<Double> feeNeedTOPayColumn  = Columns.column("Fees After Concession", "feeNeedTOPay", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setFixedWidth(50);
		TextColumnBuilder<Double> dueColumn  = Columns.column("Fee Due", "due", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setFixedWidth(50);
		TextColumnBuilder<Double> feeoweColumn  = Columns.column("Fee Owe", "feeowe", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.RIGHT)
				.setFixedWidth(50);

				
		
		
			report.columns(
					nameColumn,
					rollNoColumn,
					applicationNoColumn,
					courseColumn,
					semColumn,
					actualFeeColumn,
					feeNeedTOPayColumn,
					dueColumn,
					
					feeoweColumn
					
					);
	
		
		report
		.setColumnTitleStyle(columnTitleStyle) 
		.setColumnStyle(columnStyle)
		.highlightDetailEvenRows() ;
		 DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
			report	


						.title(//title of the report
								Components.text("Fees Report From "+ dateFormat2.format(feesReportReq.getFrmdate())+" to "+  dateFormat2.format(feesReportReq.getTodate())+" ").setStyle(boldCenteredStyle)
							
								.setHorizontalAlignment(HorizontalAlignment.CENTER))
								//.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))//show page number on the page footer
								// .setDataSource("SELECT id, first_name, last_name, date FROM customers", connection);
								.setDataSource(new JRBeanCollectionDataSource(feels));



			File down=null;
			File prev=null;
		try {
			//	report.show();//show the report

			prev=new File(rw_path+"FeeReport.pdf");
			System.out.println(prev.getAbsolutePath() +" 111 "+prev.getPath());
			
			report.toPdf(new FileOutputStream(prev));//export the report to a pdf file
			
			if("odt".equalsIgnoreCase(feesReportReq.getFormat())){
				down=new File(rw_path+"FeeReport.odt");
				report.toOdt(new FileOutputStream(down));
				
			}else if ("ods".equalsIgnoreCase(feesReportReq.getFormat())){
				down=new File(rw_path+"FeeReport.ods");
				report.toOds(new FileOutputStream(down));
				
			}else if ("xls".equalsIgnoreCase(feesReportReq.getFormat())){
				down=new File(rw_path+"FeeReport.xls");
				report.toXls(new FileOutputStream(down));
			}		
			else{
				down=new File(rw_path+"FeeReport.pdf");
			}
			
			ClassLoader classloader =
					   org.apache.poi.poifs.filesystem.POIFSFileSystem.class.getClassLoader();
					java.net.URL res = classloader.getResource(
					         "org/apache/poi/poifs/filesystem/POIFSFileSystem.class");
					String path = res.getPath();
					System.out.println("Core POI came from " + path);
			
			/*File f4=new File(rw_path+"FeeReport.txt");
			report.toText(new FileOutputStream(f4));*/
			
			/*File f6=new File(rw_path+"FeeReport.xml");
			report.toXml(new FileOutputStream(f6));*/

	
		} /*catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while generating  the report file");
			logger.error(e);
			model.put("success",null );
			model.put("error","Report file generation error" );
			return model;

		}
		
		
		Map<String,String> res=new HashMap<String,String>();
		res.put("downloadpath",r_path+down.getName());
		res.put("previewpath",r_path+prev.getName());
				model.put("success",res );
				model.put("error",null );
				return model;

		
	} 

	/*Fee Report end  +++++++++++++++++++*/
	/*Student Master Report Start*********************/
	
	@RequestMapping(value="/StudentMasterReport/GO.htm",method = RequestMethod.POST)
	public @ResponseBody Map studentmasterReportGo(@RequestBody Base base,@QueryParam("batchStrYr") int batchStrYr,@QueryParam("reportFormat") String reportFormat,Map<String, Object> model)
	{/*@QueryParam("batchStryr") int batchStrYr,
		int batchStrYr=0;*/
		System.out.println(batchStrYr+reportFormat);
		StyleBuilder columnStyle1= DynamicReports.stl.style(boldCenteredStyle)

				.setBorder(DynamicReports.stl.pen1Point());
		logger.info("Entered the  studentmasterReportGo  method ...");
		List<StudentFormInfo> studentFormInfo=null;
		List<MasterReport> masterReport=new ArrayList<MasterReport>();
		try {
			studentFormInfo=reportDAO.requestforstdinfo(batchStrYr,"UG");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			logger.error("Error occurred while fetching  the studentFormInfo details");
			logger.error(e1);
			model.put("success",null );
			model.put("error","Network error" );
			return model;
		}
		
		if(studentFormInfo==null||studentFormInfo.size()<1){
			model.put("success",null );
			model.put("error"," No Student  was admitted for the batch  "+batchStrYr+" " );
			return model;
		}

		 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		JasperReportBuilder report = DynamicReports.report();//a new report
		report.setPageFormat(PageType.A3, PageOrientation.PORTRAIT);
		
		System.out.println(studentFormInfo.size());
		for(int i=0;i<studentFormInfo.size();i++){
			List<SubjectMarks> subjectMarks=null;
			
			System.out.println(studentFormInfo.get(i).getApplicationNo());
			MasterReport masterReport1=new MasterReport();
			masterReport1.setReligion(studentFormInfo.get(i).getReligion());
			masterReport1.setAnnincome(studentFormInfo.get(i).getAnnincome());
			masterReport1.setApplicationNo(studentFormInfo.get(i).getApplicationNo());
			masterReport1.setBatchStrYr(studentFormInfo.get(i).getBatchStrYr());
			masterReport1.setCommunity(studentFormInfo.get(i).getCommunity());
			masterReport1.setNationality(studentFormInfo.get(i).getNationality());
			masterReport1.setCommAddress(studentFormInfo.get(i).getCommAddress());
			masterReport1.setGender(studentFormInfo.get(i).getGender());
			if(studentFormInfo.get(i).getDob()!=null){
				masterReport1.setDob(dateFormat.format(studentFormInfo.get(i).getDob()));
			}
		
			masterReport1.setName(studentFormInfo.get(i).getName());
			masterReport1.setParentname(studentFormInfo.get(i).getParentname());
			masterReport1.setContactphone(studentFormInfo.get(i).getContactphone());
			masterReport1.setOccupation(studentFormInfo.get(i).getOccupation());
			masterReport1.setCourse(studentFormInfo.get(i).getCourse());
			//masterReport1.setCourseCategory(studentFormInfo.get(i).getCourseCategory());
			//masterReport1.setHandicapped(studentFormInfo.get(i).getHandicapped());
			masterReport1.setLastAcademicType(studentFormInfo.get(i).getLastAcademicType());
			masterReport1.setLastschoolname(studentFormInfo.get(i).getLastschoolname());
			masterReport1.setTotalmarkobtained(studentFormInfo.get(i).getTotalmarkobtained());
			masterReport1.setPercentage(studentFormInfo.get(i).getPercentage());
			masterReport1.setRollNo(studentFormInfo.get(i).getRollNo());
			if(studentFormInfo.get(i).getDate()!=null){
				masterReport1.setDate(dateFormat.format(studentFormInfo.get(i).getDate()));	
			}
			
			if(studentFormInfo.get(i).getHandicapped()==null){
				masterReport1.setHandicapped("N");
				System.out.println("NNNNNNNNN");
			}else{
				System.out.println(studentFormInfo.get(i).getHandicapped());
				masterReport1.setHandicapped("Y");
				System.out.println("YYYYYYYYYYYY");
			}
			
			
				try {
					subjectMarks=reportDAO.fetchmarkforstudent(masterReport1.getApplicationNo());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int j=0;j<subjectMarks.size();j++){
				if(j==0){
					masterReport1.setRegno(subjectMarks.get(j).getRegno());
					masterReport1.setYearofpass(subjectMarks.get(j).getYearofpass());
					masterReport1.setSub1(subjectMarks.get(j).getSubject());
					masterReport1.setMark1(subjectMarks.get(j).getMarkobtain());
				}
				if(j==1){
					masterReport1.setSub2(subjectMarks.get(j).getSubject());
					masterReport1.setMark2(subjectMarks.get(j).getMarkobtain());
				}
				if(j==2){
					masterReport1.setSub3(subjectMarks.get(j).getSubject());
					masterReport1.setMark3(subjectMarks.get(j).getMarkobtain());
				}
				if(j==3){
					masterReport1.setSub4(subjectMarks.get(j).getSubject());
					masterReport1.setMark4(subjectMarks.get(j).getMarkobtain());
				}
				if(j==4){
					masterReport1.setSub5(subjectMarks.get(j).getSubject());
					masterReport1.setMark5(subjectMarks.get(j).getMarkobtain());
				}
				if(j==5){
					masterReport1.setSub6(subjectMarks.get(j).getSubject());
					masterReport1.setMark6(subjectMarks.get(j).getMarkobtain());
				}
				
				}
				
			
				
		
			
			
			
			masterReport.add(masterReport1);
		}
		System.out.println(masterReport.size());
	
		TextColumnBuilder<String>     nameColumn       = Columns.column("Name", "name",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(25);
		TextColumnBuilder<String>    AppNoColumn   = Columns.column("Application No", "applicationNo", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(25);
		TextColumnBuilder<String>    RollNoColumn   = Columns.column("Roll No", "rollNo", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(25);
		
		TextColumnBuilder<String>     admissiondateColumn       = Columns.column("Admission Date", "date",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(30);
		TextColumnBuilder<String>     dobColumn       = Columns.column("DOB", "dob",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(30);
		TextColumnBuilder<String>    genderColumn   = Columns.column("Sex", "gender", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		/*
		 * TextColumnBuilder<Integer> batchStrYrColumn  = Columns.column("Batch Start Year", "batchStrYr", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(30);
		*/
		TextColumnBuilder<String>    courseColumn   = Columns.column("Course", "course", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>   phyhandiColumn   = Columns.column("Phy.Handi", "handicapped", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(25);
		TextColumnBuilder<String>    nationalityColumn   = Columns.column("Nationality", "nationality", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    religionColumn   = Columns.column("Religion", "religion", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    communityColumn   = Columns.column("Community", "community", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    boardColumn   = Columns.column("Board", "lastAcademicType", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(40);
		TextColumnBuilder<String>    institutionColumn   = Columns.column("Institution Name", "lastschoolname", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(40);
		TextColumnBuilder<String> yearofpassColumn  = Columns.column("Year of pass", "yearofpass", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(30);
		TextColumnBuilder<String>    regNoColumn   = Columns.column("Register No", "regno", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(35);
		TextColumnBuilder<String>    fathernameColumn   = Columns.column("Father Name", "parentname", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    contactphoneColumn   = Columns.column("Phone No", "contactphone", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    occupationColumn   = Columns.column("Occupation", "occupation", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<Double>    annincomeColumn   = Columns.column("Annual Income", "annincome", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    addressColumn   = Columns.column("Address", "commAddress", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(30);
		TextColumnBuilder<String>    sub1Column   = Columns.column("Sub1", "sub1", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String>    sub2Column   = Columns.column("Sub2", "sub2", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String>    sub3Column   = Columns.column("Sub3", "sub3", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String>    sub4Column   = Columns.column("Sub4", "sub4", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String>    sub5Column   = Columns.column("Sub5", "sub5", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String>    sub6Column   = Columns.column("Sub6", "sub6", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<Integer> mark1Column  = Columns.column("Mark", "mark1", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer> mark2Column  = Columns.column("Mark", "mark2", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer> mark3Column  = Columns.column("Mark", "mark3", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer> mark4Column  = Columns.column("Mark", "mark4", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer> mark5Column  = Columns.column("Mark", "mark5", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer> mark6Column  = Columns.column("Mark", "mark6", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Double>    totalColumn   = Columns.column("Total", "totalmarkobtained", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(28);
		TextColumnBuilder<Double>    percentageColumn   = Columns.column("Percentage(%)", "percentage", DataTypes.doubleType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFixedWidth(28);
		report.columns(
				nameColumn,
				AppNoColumn,RollNoColumn,admissiondateColumn,courseColumn,dobColumn,genderColumn,
				phyhandiColumn,nationalityColumn,religionColumn,communityColumn,boardColumn,
				institutionColumn,yearofpassColumn,regNoColumn,sub1Column,mark1Column,sub2Column,mark2Column,sub3Column,mark3Column,
				sub4Column,mark4Column,sub5Column,mark5Column,sub6Column,mark6Column,totalColumn,percentageColumn,fathernameColumn,
				contactphoneColumn,occupationColumn,annincomeColumn,addressColumn
				
				);
	
		
		
		columnTitleStyle.setFontSize(9);
		columnStyle1.setFontSize(8);
		report
		.setColumnTitleStyle(columnTitleStyle) 
			.setColumnStyle(columnStyle1)
		.highlightDetailEvenRows() ;
							report.title(//title of the report
								Components.text("Student Master Report for "+ studentFormInfo.get(0).getBatchStrYr()+" batch  ").setStyle(boldCenteredStyle)
								.setHorizontalAlignment(HorizontalAlignment.CENTER))
								//.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))//show page number on the page footer
								// .setDataSource("SELECT id, first_name, last_name, date FROM customers", connection);
								.setDataSource(new JRBeanCollectionDataSource(masterReport));



			File down=null;
			File prev=null;
			try {
				//	report.show();//show the report

				prev=new File(rw_path+"StudentMasterReport.pdf");
				System.out.println(prev.getAbsolutePath() +" 111 "+prev.getPath());
				
				report.toPdf(new FileOutputStream(prev));//export the report to a pdf file
				
				if("odt".equalsIgnoreCase(reportFormat)){
					down=new File(rw_path+"StudentMasterReport.odt");
					report.toOdt(new FileOutputStream(down));
				}else if ("ods".equalsIgnoreCase(reportFormat)){
					down=new File(rw_path+"StudentMasterReport.ods");
					report.toOds(new FileOutputStream(down));
				}else if ("xls".equalsIgnoreCase(reportFormat)){
					down=new File(rw_path+"StudentMasterReport.xls");
					report.toXls(new FileOutputStream(down));
				}else{
					down=new File(rw_path+"StudentMasterReport.pdf");
				}
				
				

		
			} /*catch (DRException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/catch (Exception e) {
				e.printStackTrace();
				logger.error("Error occurred while generating  the report file");
				logger.error(e);
				model.put("success",null );
				model.put("error","Report file generation error" );
				return model;

			}
			Map<String,String> res=new HashMap<String,String>();
			res.put("downloadpath",r_path+down.getName());
			res.put("previewpath",r_path+prev.getName());
					model.put("success",res );
					model.put("error",null );
					return model;
		
		
		
	}
	
	
	/*Student Master Report End*/
	
	
	
	/* Student Admission Report Start    */
	@RequestMapping(value="/StudentAdmissionReport/GO.htm",method = RequestMethod.POST)
	public @ResponseBody Map studentAdmissionReportGo(@RequestBody AdmissionReportReq admissionReportReq,Map<String, Object> model)
	{logger.info("Entered the  studentAdmissionReportGo  method ...");
		List<StudentAdmittedReport> lsstd=null;
       
        	try {
				lsstd= reportDAO.getadmissionreport(admissionReportReq);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("Error occurred while fetching  the admission info details");
				logger.error(e1);
				model.put("success",null );
				model.put("error","Network error" );
				return model;
			}
        	if(lsstd==null||lsstd.size()<1){
        		
    			model.put("success",null );
    			model.put("error"," No Student  was admitted  " );
    			return model;
    		}
		
        StyleBuilder borderedStyle = DynamicReports.stl.style( DynamicReports.stl.pen1Point());
		JasperReportBuilder report = DynamicReports.report();//a new report
		report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		ColumnTitleGroupBuilder admissionmale =null;
		ColumnTitleGroupBuilder admissionfemale =null;
		System.out.println(lsstd.size());
		for(int i=0;i<lsstd.size();i++){
		System.out.println(lsstd.get(i).getCourse());
		}
		TextColumnBuilder<String>     courseColumn       = Columns.column("Course", "course",   DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.LEFT).setFixedWidth(125);
		TextColumnBuilder<Integer>    sanctionColumn     = Columns.column("Sanctioned Strength", "sactionedstrength", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    adhocColumn        = Columns.column("Adhoc Increase", "adhocincrease", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    totalColumn        = Columns.column("Total", "total", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		TextColumnBuilder<Integer>    mocColumn          = Columns.column("OC", "moc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setTitleHeight(10);
		TextColumnBuilder<Integer>    mbcColumn          = Columns.column("BC", "mbc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    mbcmColumn         = Columns.column("BCM", "mbcm", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    mmbcColumn         = Columns.column("MBC", "mmbc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    mscColumn          = Columns.column("SC", "msc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    mscaColumn         = Columns.column("SCA", "msca", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    mstColumn          = Columns.column("ST", "mst", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    mtotalColumn       = Columns.column("TOTAL", "mtotal", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);

		TextColumnBuilder<Integer>    focColumn          = Columns.column("OC", "foc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER).setTitleHeight(10);
		TextColumnBuilder<Integer>    fbcColumn          = Columns.column("BC", "fbc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    fbcmColumn         = Columns.column("BCM", "fbcm", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    fmbcColumn         = Columns.column("MBC", "fmbc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    fscColumn          = Columns.column("SC", "fsc", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    fscaColumn         = Columns.column("SCA", "fsca", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    fstColumn          = Columns.column("ST", "fst", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);
		TextColumnBuilder<Integer>    ftotalColumn       = Columns.column("TOTAL", "ftotal", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);

		TextColumnBuilder<Integer>    gtotalColumn       = Columns.column("No.of Admitted Students", "noOfAddmittedStudent", DataTypes.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER);

		
		/*
		ColumnTitleGroupBuilder  moc= Grids.titleGroup("moc", mocColumn);
		ColumnTitleGroupBuilder  mbc= Grids.titleGroup("mbc", mbcColumn);
		ColumnTitleGroupBuilder  mbcm= Grids.titleGroup("mbcm", mbcmColumn);
		ColumnTitleGroupBuilder  mmbc= Grids.titleGroup("mmbc", mmbcColumn);
		ColumnTitleGroupBuilder  msc= Grids.titleGroup("msc", mscColumn);
		ColumnTitleGroupBuilder  msca= Grids.titleGroup("msca", mscaColumn);
		ColumnTitleGroupBuilder  mst= Grids.titleGroup("mst", mstColumn);
		ColumnTitleGroupBuilder  mtotal= Grids.titleGroup("mtotal", mtotalColumn);
	
		ColumnTitleGroupBuilder  foc= Grids.titleGroup("foc", focColumn);
		ColumnTitleGroupBuilder  fbc= Grids.titleGroup("fbc", fbcColumn);
		ColumnTitleGroupBuilder  fbcm= Grids.titleGroup("fbcm",fbcmColumn);
		ColumnTitleGroupBuilder  fmbc= Grids.titleGroup("fmbc",fmbcColumn);
		ColumnTitleGroupBuilder  fsc= Grids.titleGroup("fsc", fscColumn);
		ColumnTitleGroupBuilder  fsca= Grids.titleGroup("fsca",fscaColumn);
		ColumnTitleGroupBuilder  fst= Grids.titleGroup("fst", fstColumn);
		ColumnTitleGroupBuilder  ftotal= Grids.titleGroup("ftotal",ftotalColumn);
		*/
		admissionmale= Grids.titleGroup("MALE", mocColumn,mbcColumn,mbcmColumn,mmbcColumn,mscColumn,mscaColumn,mstColumn,mtotalColumn).setTitleHeight(30);
		admissionfemale= Grids.titleGroup("FEMALE", focColumn,fbcColumn,fbcmColumn,fmbcColumn,fscColumn,fscaColumn,fstColumn,ftotalColumn).setTitleHeight(30);
		
		report.columns(
				courseColumn,sanctionColumn,adhocColumn,totalColumn,mocColumn,mbcColumn,mbcmColumn,mmbcColumn,mscColumn,mscaColumn,mstColumn,mtotalColumn
				,focColumn,fbcColumn,fbcmColumn,fmbcColumn,fscColumn,fscaColumn,fstColumn,ftotalColumn,gtotalColumn
				);
		
		report	.columnGrid(
courseColumn,sanctionColumn,adhocColumn,totalColumn,admissionmale,admissionfemale,gtotalColumn
				
				);
		 DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		report
		.setColumnTitleStyle(columnTitleStyle) 
		.setColumnStyle(columnStyle)
		.highlightDetailEvenRows() ;
							report.title(//title of the report
								Components.text("Student Admission Report From "+dateFormat2.format(admissionReportReq.getFromdate())+" to "+dateFormat2.format(admissionReportReq.getTodate()) ).setStyle(boldCenteredStyle)
								.setHorizontalAlignment(HorizontalAlignment.CENTER))
						  	//	.pageFooter(Components.pageXofY().setStyle(boldCenteredStyle))//show page number on the page footer
								// .setDataSource("SELECT id, first_name, last_name, date FROM customers", connection);
								.setDataSource(new JRBeanCollectionDataSource(lsstd));

			File down=null;
			File prev=null;
			try {
				//	report.show();//show the report

				prev=new File(rw_path+"StudentAdmissionReport.pdf");
				System.out.println(prev.getAbsolutePath() +" 111 "+prev.getPath());
				
				report.toPdf(new FileOutputStream(prev));//export the report to a pdf file
				
				if("odt".equalsIgnoreCase(admissionReportReq.getReportFormat())){
					down=new File(rw_path+"StudentAdmissionReport.odt");
					report.toOdt(new FileOutputStream(down));
				}else if ("ods".equalsIgnoreCase(admissionReportReq.getReportFormat())){
					down=new File(rw_path+"StudentAdmissionReport.ods");
					report.toOds(new FileOutputStream(down));
					
				}else if ("xls".equalsIgnoreCase(admissionReportReq.getReportFormat())){
					down=new File(rw_path+"StudentAdmissionReport.xls");
					report.toXls(new FileOutputStream(down));
					
				}
				
				else{
					down=new File(rw_path+"StudentAdmissionReport.pdf");
				}
				
				
				
				/*File f4=new File(rw_path+"StudentAdmissionReport.docs");
				report.toDocx(new FileOutputStream(f4));
				File f6=new File(rw_path+"StudentAdmissionReport.xml");
				report.toXml(new FileOutputStream(f6));*/

		
			} /*catch (DRException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/catch (Exception e) {
				e.printStackTrace();
				logger.error("Error occurred while generating  the report file");
				logger.error(e);
				model.put("success",null );
				model.put("error","Report file generation error" );
				return model;

			}
			Map<String,String> res=new HashMap<String,String>();
			res.put("downloadpath",r_path+down.getName());
			res.put("previewpath",r_path+prev.getName());
					model.put("success",res );
					model.put("error",null );
					return model;
			
		
	}
	
	
	
	
	
	
	
	
	/*Student Admission Report End*/
	
	
}


