package com.tsnc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.Concession;
import com.tsnc.model.Courses;
import com.tsnc.model.FeeStructure;
import com.tsnc.model.FineStructure;
import com.tsnc.model.Login;

@Repository
public class StructureDAO extends BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	@Transactional
	public List<Courses> fetchCoursesUSbatchStrYr(int batchStrYr)  throws Exception{
		// TODO Auto-generated method stub

		List<Courses> courseslst=(List<Courses>)currentSession().createQuery("FROM Courses C WHERE C.batchStrYr="+batchStrYr+"").list();
			
		return courseslst;
	}

	@Transactional
	public void updateCourse(Courses course)  throws Exception{
		// TODO Auto-generated method stub
		currentSession().update(course);
	}
	@Transactional
	public void addCourse(Courses course)  throws Exception{
		// TODO Auto-generated method stub
		currentSession().save(course);
	}
	
	@Transactional
	public void deleteCourse(int id)  throws Exception{
		// TODO Auto-generated method stub
		Courses course = (Courses) currentSession().get(Courses.class, id);
		currentSession().delete(course);
	}
	@Transactional
	public List<Concession> fetchConcession() throws Exception
	{
		List<Concession> concessionList = (List<Concession>)currentSession().createQuery("FROM Concession C").list();
	return concessionList;
	}
	
	@Transactional
	public void updateConcession(Concession concession)  throws Exception{
		// TODO Auto-generated method stub
		currentSession().update(concession);
	}
	@Transactional
	public void addConcession(Concession concession)  throws Exception{
		// TODO Auto-generated method stub
		currentSession().save(concession);
	}
	
	@Transactional
	public void deleteConcession(int id)  throws Exception{
		// TODO Auto-generated method stub
		Concession concession = (Concession) currentSession().get(Concession.class, id);
		currentSession().delete(concession);
	}

	@Transactional
	public void addfeeStructure(FeeStructure feeStructure) throws Exception {
		// TODO Auto-generated method stub
		currentSession().save(feeStructure);
		
	}
	@Transactional
	public void updatefeeStructure(FeeStructure feeStructure) throws Exception {
		// TODO Auto-generated method stub
		currentSession().update(feeStructure);
	
	}
	@Transactional
	public void deleteFeeStructure(int id) throws Exception {
		// TODO Auto-generated method stub
		FeeStructure feeStructure = (FeeStructure) currentSession().get(FeeStructure.class, id);
		currentSession().delete(feeStructure);
	}
	@Transactional
	public List<FineStructure> fetchfineStructure(int batchStrYr) throws Exception {
		// TODO Auto-generated method stub

		List<FineStructure> fineStructurelst=(List<FineStructure>)currentSession().createQuery("FROM FineStructure FNS WHERE FNS.batchStrYr="+batchStrYr+"").list();
			
		return fineStructurelst;
	}
	@Transactional
	public void addFineStructure(FineStructure fineStructure) throws Exception  {
		// TODO Auto-generated method stub
		currentSession().save(fineStructure);
	}
	@Transactional
	public void updateFineStructure(FineStructure fineStructure)  throws Exception  {
		// TODO Auto-generated method stub
		currentSession().update(fineStructure);
	}
	
	
}
