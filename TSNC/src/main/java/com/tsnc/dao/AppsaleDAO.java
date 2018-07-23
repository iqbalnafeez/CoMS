package com.tsnc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tsnc.model.ApplicationSale;



	@Repository
	public class AppSaleDAO extends BaseDAO {
		@Autowired
		private SessionFactory sessionFactory;
		private Session currentSession(){
			return sessionFactory.getCurrentSession();
		}

		@Transactional
		public String addApplicationSale(ApplicationSale applicationSale)  throws Exception{
			currentSession().save(applicationSale);
			return "success";
}
		@Transactional
		public 	List<ApplicationSale> fetchApplicationSale(String applicationNo, String reciptNo) throws Exception{
			// TODO Auto-generated method stub
			List<ApplicationSale> applicationSale=	(List<ApplicationSale>)
					currentSession().createQuery("FROM ApplicationSale  ApS WHERE ApS.applicationNo='"+applicationNo+"'"
							+ " OR ApS.reciptNo='"+reciptNo+"' ").list();
			return applicationSale;
		}
}