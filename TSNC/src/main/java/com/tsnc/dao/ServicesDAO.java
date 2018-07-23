package com.tsnc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.Courses;
import com.tsnc.model.Location;
import com.tsnc.model.Login;

@Repository
public class ServicesDAO extends BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public List<Courses> fetchallCourse()  throws Exception{
		// TODO Auto-generated method stub
		List<Courses> lscourses=currentSession().createQuery("SELECT DISTINCT C.course,courseCategory FROM Courses C").list();
		return lscourses;
	}
	@Transactional
	public List<Location> fetchallLocation() {
		// TODO Auto-generated method stub
		List<Location> locationlst=currentSession().createQuery(" FROM Location L").list();
		return locationlst;
	}

}
