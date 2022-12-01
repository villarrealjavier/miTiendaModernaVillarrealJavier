package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class SaleId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8238599676946189014L;
	private int element; //database ele_id
	private int user; //database us_id
	private LocalDateTime salesDate;

	public SaleId() {
		
	}
	

	public int getElement() {
		return element;
	}

	public void setElement(int element) throws SaleIdException {
		if(element <= 0) {
			throw new SaleIdException("El código del producto no puede ser igual o menor que 0.");
		} else {
			this.element = element;
		}
	}



	public int getUser() {
		return user;
	}



	public void setUser(int user) throws SaleIdException {
		if(user <= 0) {
			throw new SaleIdException("El código del usuario no puede ser igual o menor que 0.");
		} else {
			this.user = user;
		}
	}



	public LocalDateTime getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(LocalDateTime salesDate) throws SaleIdException {
		
		if(salesDate.isAfter(LocalDateTime.now())) {
			throw new SaleIdException("La fecha de venta no puede ser superior a la fecha actual.");
		}
		this.salesDate = salesDate;
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
		SaleId other = (SaleId) obj;
		return element == other.element && Objects.equals(salesDate, other.salesDate) && user == other.user;
	}


	@Override
	public String toString() {
		return "SaleId [element=" + element + ", user=" + user + ", salesDate=" + salesDate + "]";
	}

	
}
