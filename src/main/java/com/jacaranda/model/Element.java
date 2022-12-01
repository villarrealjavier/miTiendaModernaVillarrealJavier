package com.jacaranda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "ELEMENTS")
public class Element {
	@Id
	@Column(name = "ele_id")
	private int eleId;
	private String name;
	private String description;
	private double price;
	private int stock;
	@ManyToOne
	@JoinColumn(name="category")
	private Category category;
	@OneToMany(mappedBy = "element", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sale> sales = new ArrayList<>();
	
	/**
	 * Empty constructor
	 */
	public Element() {
		
	}
	
	/**
	 * constructor with all parameters but the id which assigned by the data base 
	 * @param name - name of the product 
	 * @param description - description of the product 
	 * @param price - price of the product 
	 * @param category - category of the product 
	 * @throws ElementException in case the parameters don't meet the specifications
	 */

	public Element(String name, String description, double price, int stock, Category category) throws ElementException {
		super();
		setName(name);
		setDescription(description);
		setPrice(price);
		setStock(stock);
		setCategory(category);
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) throws ElementException {
		if(stock < 0) {
			throw new ElementException("El stock no puede ser menor que 0.");
		} else {
			this.stock = stock;
		}
		
		
	}

	public String getName() {
		return name;
	}

	/**
	 * Method that sets the name of the product
	 * @param name - name of the product
	 * @throws ElementException in case the name is null, blank or has more than 100 characters
	 */
	public void setName(String name) throws ElementException {
		if(name == null || name.length() > 100 || name.isBlank()) {
			throw new ElementException("El nombre del producto no puede estar vacío o tener más de 100 caracteres.");
		} else {
			this.name = name;
		}
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Method that sets the description of the product 
	 * @param description - description of the product 
	 * @throws ElementException in case the description is null, blank or has more than 200 characters  
	 */
	public void setDescription(String description) throws ElementException {
		if(description == null || description.length() > 200 || description.isBlank()) {
			throw new ElementException("La descripción del producto no puede estar vacía o tener más de 200 caracteres.");
		} else {
			this.description = description;
		}
	}

	public double getPrice() {
		return price;
	}

	/**
	 * Method that sets the price of a product 
	 * @param price - price of the product 
	 * @throws ElementException in case the price is lower than 0
	 */
	public void setPrice(double price) throws ElementException {		
		if(price < 0) {
			throw new ElementException("El precio no puede ser menor que 0.");
		}
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getEleId() {
		return eleId;
	}

	public void setEleId(int eleId) {
		this.eleId = eleId;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		return eleId == other.eleId;
	}

	@Override
	public String toString() {
		return "Element [eleId=" + eleId + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", stock=" + stock + ", category=" + category + ", sales=" + sales + "]";
	}

	
}
