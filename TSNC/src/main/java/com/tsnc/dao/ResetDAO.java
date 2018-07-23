package com.tsnc.dao;


import java.util.List;









import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.Login;
import com.tsnc.model.StudentFormInfo;
import com.tsnc.model.SubjectMarks;

@Repository
public class ResetDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	@Transactional
	public List<Login> fetchalluserrole()  throws Exception {
		// TODO Auto-generated method stub
		List<Login> loginls=currentSession().createQuery("FROM Login WHERE role !='2'").list();
		
		
		
		/*for (int i = 0; i < loginls.size(); i++) {
			loginls.get(i).setPassword("0");
			loginls.get(i).setToken(null);
			 
			 login.add(loginls.get(i));
		}
		   */
		return loginls;
	}
	@Transactional
	public void updatepassword(Login login) throws Exception {
		// TODO Auto-generated method stub
		login.setToken(null);
		currentSession().saveOrUpdate(login);
		
	}

}
