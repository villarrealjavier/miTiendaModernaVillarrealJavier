package com.jacaranda.control;

import java.time.LocalDateTime;
import java.util.ArrayList;


import org.hibernate.Session;
import org.hibernate.query.Query;

import com.jacaranda.model.User;


public class UserControl {

	public UserControl() {
		
	}
	
	private static User getUserByUserName(String userName) throws ConnectionDBException {
		User user = null;
		Session session = ConnectionDB.getSession();
		
		try {
			Query<User> query = session.createQuery("SELECT u FROM com.jacaranda.model.User u WHERE u.userName LIKE '" +  userName + "'");
			user = query.getSingleResult();
			
		} catch (Exception e) {
			user = null;
		}
		
		return user;		
	}

	public static User getUser(int id) throws ConnectionDBException {
		User result = null;
		
		Session session = ConnectionDB.getSession();
		result = session.get(User.class, id);
		
		return result;
	}
	
	public static ArrayList<User> getUsers() throws ConnectionDBException {
		ArrayList<User> users = null; 
		
		Session session = ConnectionDB.getSession();
		Query<User> query = session.createQuery("SELECT u FROM com.jacaranda.model.User u");
		users = (ArrayList<User>) query.getResultList();
		
		return users;
	
	}
	
	public static User addUser(String userName, String password, String name, String lastname, LocalDateTime dob, char sex,
			boolean admin) throws UserControlException, ConnectionDBException {
		User result = null;
		Session session = ConnectionDB.getSession();
		
		try {
			User existUser = getUserByUserName(userName);
			
			if(existUser == null) {
				
				User newUser = new User(userName, password, name, lastname, dob, sex, admin);
				session.getTransaction().begin();
				session.save(newUser);
				session.getTransaction().commit();

				result = newUser;
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new UserControlException(e.getMessage());
		}
		
		
		return result;
	}
	
	public static boolean deleteUser(int id) throws UserControlException, ConnectionDBException {
		boolean result = false;
		
		Session session = ConnectionDB.getSession();
		
		try {
			User deleteUser = getUser(id);
			session.getTransaction().begin();
			session.delete(deleteUser);
			session.getTransaction().commit();
			
			result = true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new UserControlException(e.getMessage());
		}
	
		return result;
		
	}
	
	public static User checkUser(String userName, String password) throws UserControlException {
		User result = null;
		
		try {
			User existUser = getUserByUserName(userName);
			
			if (existUser != null && existUser.getPassword().equals(password)) {
				result = existUser;
			}
			
		} catch (Exception e) {
			throw new UserControlException(e.getMessage());
		}
		
		return result;
	}	
}
