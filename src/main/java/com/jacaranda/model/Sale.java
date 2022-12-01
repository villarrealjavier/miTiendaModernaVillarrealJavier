package com.jacaranda.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "SALES")
@IdClass (SaleId.class)
public class Sale {
	@Id
	@ManyToOne
	@JoinColumn (name = "ele_id", insertable = false, updatable = false)
	private Element element;
	@Id
	@ManyToOne
	@JoinColumn (name = "us_id", insertable = false, updatable = false)
	private User user;
	@Id
	@Column (name = "salesDate", insertable = false, updatable = false)
	private LocalDateTime salesDate;
	private int quantity;
	private double price;
	
	public Sale() {
		
	}

	public Sale(Element element, User user, LocalDateTime salesDate, int quantity, double price) throws SaleException {
		super();
		setElement(element);
		setUser(user);
		setSalesDate(salesDate);
		setQuantity(quantity);
		setPrice(price);
	}
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) throws SaleException {
		if(element != null) {
			this.element = element;
		} else {
			throw new SaleException("El producto no puede ser nulo.");
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) throws SaleException {
		if (user == null) {
			throw new SaleException("El usuario no puede ser nulo.");
		} else {
			this.user = user;
		}
		
	}
	
	public LocalDateTime getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(LocalDateTime salesDate) throws SaleException {
		if(salesDate == null) {
			throw new SaleException("La fecha no puede ser nula.");
		} else {
			this.salesDate = salesDate;
		}	
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) throws SaleException {
		if(quantity < 0) {
			throw new SaleException("La cantidad no puede ser menor de 0.");
		} else {
			this.quantity = quantity;
		}
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) throws SaleException {
		if(price < 0) {
			throw new SaleException("El precio no puede ser menor que 0.");
		} else {
			this.price = price;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(element, salesDate, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(element, other.element) && Objects.equals(salesDate, other.salesDate)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Sale [quantity=" + quantity + ", price=" + price + ", element=" + element + ", user=" + user
				+ ", salesDate=" + salesDate + "]";
	}

}
