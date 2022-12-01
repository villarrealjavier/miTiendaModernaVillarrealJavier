package com.jacaranda.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.jacaranda.model.CartItem;
import com.jacaranda.model.Element;
import com.jacaranda.model.ElementException;
import com.jacaranda.model.Sale;
import com.jacaranda.model.SaleException;
import com.jacaranda.model.User;

public class SaleControl {

	public SaleControl() {
		
	}
	
	public static ArrayList<Sale> getSales() throws ConnectionDBException{
		ArrayList<Sale> sales = null; 
		
		Session session = ConnectionDB.getSession();
		Query<Sale> query = session.createQuery("SELECT s FROM com.jacaranda.model.Sale s");
		sales = (ArrayList<Sale>)query.getResultList();
		
		return sales;
	}
	
	public static ArrayList<Sale> getSalesByUser(User user) throws ConnectionDBException{
		ArrayList<Sale> result = null; 
		
		Session session = ConnectionDB.getSession();
		Query<Sale> query = session.createQuery("SELECT s FROM com.jacaranda.model.Sale s WHERE us_id = '" +  user.getId() + "' ORDER BY salesDate DESC", Sale.class);
		result  = (ArrayList<Sale>)query.getResultList();
		
		return result ;
	}

	
	public static boolean addSale(List<CartItem> cartList) throws ConnectionDBException, SaleControlException {
		boolean result = false; 
		Session session = ConnectionDB.getSession();
		
		try {
			
			if(cartList.size() > 0) {
				
				session.getTransaction().begin();

				Iterator<CartItem> iterator = cartList.iterator();
				
				while(iterator.hasNext()) {
					CartItem iItem = iterator.next();
					
					//checking if the user and element exist in DB
					Element ele = ElementControl.getElement(iItem.getElementId());
					User user = UserControl.getUser(iItem.getUserId());
					
					//checking that stock if greater than quantity
					if (ele.getStock() >= iItem.getQuantity()) {
						int newStock = ele.getStock() - iItem.getQuantity();
						Sale newSale = new Sale(ele, user, iItem.getOrderedDate(), iItem.getQuantity(), iItem.getPrice()) ;
						session.save(newSale);
						ele.setStock(newStock);
						session.save(ele);
						
					}else {
						throw new SaleControlException("La cantidad no puede ser mayor que el stock disponible.");
					}
					
				}
				
				session.getTransaction().commit();
				result = true;
				
			} else {
				throw new SaleControlException("La lista de productos está vacía.");
			}
			
		} catch (ConnectionDBException | SaleException | SaleControlException | ElementException e) {
			session.getTransaction().rollback();
			throw new SaleControlException(e.getMessage());
		}
	
		return result;
	}
			
	

}
