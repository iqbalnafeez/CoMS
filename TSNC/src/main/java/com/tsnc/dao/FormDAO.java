package com.tsnc.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;












import com.tsnc.model.Courses;
import com.tsnc.model.ReferralInfo;
import com.tsnc.model.StudentFormInfo;
import com.tsnc.model.PgForm;
import com.tsnc.model.ApplicationSale;
import com.tsnc.model.SubjectMarks;
import com.tsnc.model.UgForm;
@Repository
public class FormDAO extends BaseDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	@Transactional
	public void savemandinfo (StudentFormInfo studentFormInfo) throws Exception {
		
		currentSession().saveOrUpdate(studentFormInfo);

	}
	@Transactional
	public void pgformsave(PgForm pgform)  throws Exception{
		currentSession().saveOrUpdate(pgform);
	
	}
	@Transactional
	public UgForm ugformsave(UgForm ugform)  throws Exception{
		
		currentSession().saveOrUpdate(ugform);
		return ugform;
	}
	@Transactional
	public void updatefeename(StudentFormInfo stdfrminfo)throws Exception {
		currentSession().createQuery("UPDATE Fee SET name='"+stdfrminfo.getName()+"' WHERE applicationNo='"+stdfrminfo.getApplicationNo()+"'  ");
	
			
	}
	@Transactional
	public ReferralInfo fetchReferralInfoUsappNo(String applicationNo) throws Exception{
		// TODO Auto-generated method stub
		
		ReferralInfo referalInfo=(ReferralInfo)currentSession().createQuery(" FROM ReferralInfo S WHERE S.applicationNo='"+applicationNo+"'").uniqueResult();
		return referalInfo;
		
	}
	@Transactional
	public PgForm fetchpginfo(StudentFormInfo stdfrminfo)throws Exception {
		// TODO Auto-generated method stub

		PgForm pgForm=(PgForm)currentSession().createQuery(" FROM PgForm S WHERE S.applicationNo='"+stdfrminfo.getApplicationNo()+"'").uniqueResult();
		System.out.println("fetch");
		return pgForm;
	}
	@Transactional
	public void savereferalinfo(ReferralInfo referalInfo)throws Exception {
		// TODO Auto-generated method stub
		currentSession().saveOrUpdate(referalInfo);
		
	}
	@Transactional
	public List<SubjectMarks> fetchUgFormUsappNo(String applicationNo) throws Exception  {
		// TODO Auto-generated method stub
		List<SubjectMarks> subjectMarkslst=currentSession().createQuery(" FROM SubjectMarks SM WHERE SM.applicationNo='"+applicationNo+"'").list();
		return subjectMarkslst;
	}
	@Transactional
	public void updateStudentFormInfo (StudentFormInfo studentFormInfo)  throws Exception {
		// TODO Auto-generated method stub
		currentSession().update(studentFormInfo);
		
	}
	@Transactional
	public void ListSaveOrUpdate(
			List<SubjectMarks> lssubjectMarks)  throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<lssubjectMarks.size();i++){
			
			currentSession().saveOrUpdate(lssubjectMarks.get(i));
		}
	}
	@Transactional
	public Courses fetchCourses(String course, int batchStrYr) throws Exception {
		// TODO Auto-generated method stub
		Courses courses=(Courses) currentSession().createQuery(" FROM Courses C WHERE C.course='"+course+"' AND C.batchStrYr="+batchStrYr+" ").uniqueResult();
		return courses;
	}
	@Transactional
	public BigInteger fetchlastRollnum(String course, int batchStrYr)throws Exception  {
		// TODO Auto-generated method stub
		String sql=" SELECT count(*) FROM studentform_info S "+
					" WHERE S.batchStrYr="+batchStrYr+" AND S.course='"+course+"' ";
		BigInteger lastrollnum= (BigInteger) currentSession().createSQLQuery(sql).uniqueResult();
		return lastrollnum;
	}
	@Transactional
	public void updatefeesavemandinfo(StudentFormInfo studentFormInfo)throws Exception   {
		// TODO Auto-generated method stub
		currentSession().saveOrUpdate(studentFormInfo);
		currentSession().createQuery("UPDATE Fee SET name='"+studentFormInfo.getName()+"' , rollNo='"+studentFormInfo.getRollNo()+"'"
				+ " WHERE applicationNo='"+studentFormInfo.getApplicationNo()+"'  ");
		
	}
		

	
	
}
