package com.jacaranda.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class CartItem {
	private int userId;
	private int elementId;
	private int quantity;
	private double price; 
	private LocalDateTime orderedDate;
	

	public CartItem() {
		
	}

	public CartItem(int userId, int elementId, int quantity, double price, LocalDateTime orderedDate) {
		super();
		this.userId = userId;
		this.elementId = elementId;
		this.quantity = quantity;
		this.price = price;
		this.orderedDate = orderedDate;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getElementId() {
		return elementId;
	}


	public void setElementId(int elementId) {
		this.elementId = elementId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public LocalDateTime getOrderedDate() {
		return orderedDate;
	}


	public void setOrderedDate(LocalDateTime orderedDate) {
		this.orderedDate = orderedDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(elementId, orderedDate, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return elementId == other.elementId && Objects.equals(orderedDate, other.orderedDate) && userId == other.userId;
	}

	@Override
	public String toString() {
		return "CartItem [userId=" + userId + ", elementId=" + elementId + ", quantity=" + quantity + ", price=" + price
				+ ", orderedDate=" + orderedDate + "]";
	}
	
	

}
