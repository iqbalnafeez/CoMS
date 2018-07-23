package com.tsnc.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.Login;


@Repository
public class IndexDAO extends BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private Session currentSession(){
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public Login fetchLoginUSusername(String username)  throws Exception{
		// TODO Auto-generated method stub
		Login login=(Login)currentSession().get(Login.class, username);
		return login;
	}
	@Transactional
	public String forgotPassword(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	public void updateLoginToken(Login login) throws Exception {
		// TODO Auto-generated method stub
		currentSession().update(login);
		
	}
	@Transactional
	public void updateUStoken(String token)  throws Exception{
		// TODO Auto-generated method stub

				Login login=(Login)currentSession().createQuery("FROM Login L WHERE L.token='"+token+"'").uniqueResult();
				login.setToken(null);
		  currentSession().update(login); 
	}

	 

}
