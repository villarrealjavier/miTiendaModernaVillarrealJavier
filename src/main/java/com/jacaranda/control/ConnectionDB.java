package com.jacaranda.control;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * 
 * @author NadiaC Method which creates a Data base connection
 */
public class ConnectionDB {

	private static Session session;

	/**
	 * Method that gets a data base session
	 * 
	 * @return a session
	 * @throws ConnectionDBException
	 */
	public static Session getSession() throws ConnectionDBException {
		try {
			if (session ==null) {
				StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
				SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
				session = sf.openSession();
			}
		} catch (Exception e) {
			throw new ConnectionDBException("No ha sido posible conectar con la base de datos.");
		}
		
		return session;

	}
}
