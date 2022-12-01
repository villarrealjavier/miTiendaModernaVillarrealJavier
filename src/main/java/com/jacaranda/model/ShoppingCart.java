package com.jacaranda.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {
	private List<CartItem> requestedItems = new ArrayList<>();
	

	public ShoppingCart() {
	}


	public List<CartItem> getRequestedItems() {
		return requestedItems;
	}


	public void setRequestedItems(List<CartItem> requestedItems) {
		this.requestedItems = requestedItems;
	}
	
	public CartItem getItemByElementId(int eleId) {
		CartItem result = null; 
		
		Iterator<CartItem> iterator = this.requestedItems.iterator();
		
		while(iterator.hasNext() && result == null) {
			CartItem iItem  = iterator.next();
			
			if (iItem.getElementId() == eleId) {
				result = iItem;
			}
			
		}
	
		return result;
	}
	

	@Override
	public String toString() {
		return "ShoppingCart [requestedItems=" + requestedItems + "]";
	}
	
}
