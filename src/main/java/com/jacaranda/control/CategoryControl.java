package com.jacaranda.control;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.jacaranda.model.Category;



public class CategoryControl {

	public CategoryControl() {
	
	}
	
	public static ArrayList<Category> getCategories() throws ConnectionDBException {
		ArrayList<Category> categories = null; 
		
		Session session = ConnectionDB.getSession();
		Query<Category> query = session.createQuery("SELECT c FROM com.jacaranda.model.Category c");
		 categories  = (ArrayList<Category>) query.getResultList();
		
		return categories;
	}
	
	public static Category getCategory(int id) throws ConnectionDBException {
		Category cat = null; 
		
		Session session = ConnectionDB.getSession();
		cat = session.get(Category.class, id);
		
		return cat;
	}
	

	
	
}
