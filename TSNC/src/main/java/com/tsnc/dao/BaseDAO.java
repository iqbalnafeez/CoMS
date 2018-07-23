package com.tsnc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.FeeStructure;
import com.tsnc.model.Login;
import com.tsnc.model.StudentFormInfo;
@Repository
public class BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public Login fetchLoginUStoken(String token)  throws Exception{
		// TODO Auto-generated method stub
		Login login=(Login)currentSession().createQuery("FROM Login L WHERE L.token='"+token+"'").uniqueResult();
		return login;
	}
	
	@Transactional
	public List<FeeStructure> fetchfeeStructure(int sem,
			String lastAcademicType, String course, int batchStrYr) throws Exception {
		// TODO Auto-generated method stub
		List<FeeStructure> feeStructureList = (List<FeeStructure>)currentSession().createQuery("FROM FeeStructure F WHERE F.sem="+sem+" AND F.lastAcademicType='"+lastAcademicType+"' AND F.course='"+course+"' AND F.batchStrYr="+batchStrYr+"").list();
		return feeStructureList;
		
	}

	@Transactional
	public StudentFormInfo fetchStudentInfoUSAppNo(String applicationNo)  throws Exception{
		StudentFormInfo studentInfo=(StudentFormInfo)currentSession().createQuery(" FROM StudentFormInfo S WHERE S.applicationNo='"+applicationNo+"'").uniqueResult();
		return studentInfo;
	
}
	@Transactional
	public StudentFormInfo fetchStudentInfoUSRolNo(String rollNo)throws Exception {
		// TODO Auto-generated method stub
		StudentFormInfo studentInfo=(StudentFormInfo)currentSession().createQuery(" FROM StudentFormInfo S WHERE S.rollNo='"+rollNo+"'").uniqueResult();
		
		return studentInfo;
	}
}
