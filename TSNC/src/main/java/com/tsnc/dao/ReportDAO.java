package com.tsnc.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.AdmissionReportReq;
import com.tsnc.model.AppSaleCount;
import com.tsnc.model.ApplicationSale;
import com.tsnc.model.Fee;
import com.tsnc.model.StudentAdmittedReport;
import com.tsnc.model.StudentFormInfo;
import com.tsnc.model.SubjectMarks;

@Repository
public class ReportDAO extends BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public List<Map<String,Object>> fetchappsalecount(String sql)  throws Exception{
		
		List<Map<String,Object>> appsalescount=	currentSession().createSQLQuery(sql)
			.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return appsalescount;
}
	@Transactional
	public List<Fee> fetchfeereport(String sql)throws Exception {
		// TODO Auto-generated method stub
		List<Fee> feels =currentSession().createQuery(sql).list();
		return feels;
	}
	@Transactional
	public List<StudentFormInfo> requestforstdinfo(int batchStrYr,String courseCategory)  throws Exception{
		// TODO Auto-generated method stub
		List<StudentFormInfo> studentFormInfo=currentSession().createQuery("FROM StudentFormInfo WHERE batchStrYr='"+batchStrYr+"' AND courseCategory='"+courseCategory+"'").list();
		return studentFormInfo;
	}
	@Transactional
	public List<SubjectMarks> fetchmarkforstudent(String applicationNo)  throws Exception{
		List<SubjectMarks> subjectMarks=currentSession().createQuery("FROM SubjectMarks WHERE applicationNo='"+applicationNo+"'").list();
		return subjectMarks;
		// TODO Auto-generated method stub
		
	}
	@Transactional
	public List<StudentAdmittedReport> getadmissionreport(AdmissionReportReq admissionReportReq)  throws Exception{
		StudentAdmittedReport studentAdmittedReport=null;
		List<StudentAdmittedReport> lsstd=null;
		Query query =(Query) currentSession()
				.createSQLQuery("CALL admissionreport(:fromdate,:todate,:batchstryr)")
				.setParameter("fromdate", admissionReportReq.getFromdate())
				.setParameter("todate", admissionReportReq.getTodate())
		.setParameter("batchstryr", admissionReportReq.getBatchstartyear());
		System.out.println("List of value in query"+query.list());
		lsstd=currentSession().createQuery("FROM StudentAdmittedReport").list();
		
		
		return lsstd;
		// TODO Auto-generated method stub
		
	}
	
}
