package com.ipvision.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public static SessionFactory factory = null;
	
	private HibernateUtil(){
		
	}
	
	public static synchronized SessionFactory getSessionFactory(){
		if(factory == null){
			try{
				factory = new Configuration().configure().buildSessionFactory();
		      }catch (Exception e) {
		    	  e.printStackTrace();
		      }
		}
		
		return factory;
	}
}
