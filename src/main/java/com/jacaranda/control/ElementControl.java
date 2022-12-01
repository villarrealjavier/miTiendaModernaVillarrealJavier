package com.jacaranda.control;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.jacaranda.model.Category;
import com.jacaranda.model.Element;

public class ElementControl {

	public ElementControl() {
		
	}

	public static ArrayList<Element> getElements() throws ConnectionDBException {
		ArrayList<Element> elements = null; 
		
		Session session = ConnectionDB.getSession();
		Query<Element> query = session.createQuery("SELECT e FROM com.jacaranda.model.Element e");
		elements = (ArrayList<Element>) query.getResultList();
		
		return elements;
	}
	
	public static Element getElement(int id) throws ConnectionDBException {
		Element ele = null; 
		
		Session session = ConnectionDB.getSession();
		ele = session.get(Element.class, id);
		
		return ele;
	}
	
	private static Element getElementByName(String name) throws ConnectionDBException {
		Element ele = null;
		Session session = ConnectionDB.getSession();
		
		try {
			Query<Element> query = session.createQuery("SELECT e FROM com.jacaranda.model.Element e WHERE e.name LIKE '" +  name + "'");
			ele = query.getSingleResult();
			
		} catch (Exception e) {
			ele = null;
		}
		
		return ele;		
	}

	
	private static ArrayList<Element> getElementsByCategory(Category category) throws ConnectionDBException{
		ArrayList<Element> elements = null; 
		
		Session session = ConnectionDB.getSession();
		Query<Element> query = session.createQuery("SELECT e FROM com.jacaranda.model.Element WHERE category = '" +  category.getCatId() + "'");
		elements = (ArrayList<Element>) query.getResultList();
		
		return elements;
		
	}
	
	public static Element addElement(String name, String description, double price, int stock, Category category) throws ConnectionDBException, ElementControlException {
		Element result = null;
		Session session = ConnectionDB.getSession();
		
		try {
			Element existElement = getElementByName(name);
			
			if(existElement == null) {
				
				Element newEle = new Element(name, description, price, stock, category);
				session.getTransaction().begin();
				session.save(newEle);
				session.getTransaction().commit();

				result = newEle;
			} else {
				throw new ElementControlException("El producto ya existe en la base de datos.");
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new ElementControlException(e.getMessage());
		}
		
		
		return result;
	}

	
}
