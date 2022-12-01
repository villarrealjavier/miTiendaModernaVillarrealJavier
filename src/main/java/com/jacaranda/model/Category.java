package com.jacaranda.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "CATEGORIES")
public class Category {
	@Id
	@Column (name = "cat_id")
	private int catId;
	private String name;
	private String description;
	@OneToMany(mappedBy ="category", cascade = CascadeType.ALL, orphanRemoval = true) 
	private List<Element> elements;
	
	/**
	 * Empty constructor
	 */
	public Category() {
		
	}

	/**
	 * Constructor with all parameters but the id that is assigned by the data base 
	 * @param name - name of the category
	 * @param description - description of the category
	 * @throws CategoryException in case any of the parameters don't meet the specifications
	 */
	public Category(String name, String description) throws CategoryException {
		super();
		setName(name);
		setDescription(description);
		List<Element> elements = null;
	}

	public String getName() {
		return name;
	}

	/**
	 * Method that sets the category's name
	 * @param name - name of the category
	 * @throws CategoryException in case the category's name is null, empty or has more than 50 characters
	 */
	public void setName(String name) throws CategoryException {
		if(name == null || name.length() > 50 || name.isBlank()) {
			throw new CategoryException("El nombre de la categoría no puede estar vacía o tener más de 50 caracteres.");
		} else {
			this.name = name;
		}
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Method that sets the category's description
	 * @param description - description of the category
	 * @throws CategoryException in case the category's description is null, empty or has more than 200 characters
	 */
	public void setDescription(String description) throws CategoryException {		
		if(description == null || description.length() > 200 || description.isBlank()) {
			throw new CategoryException("La descripción no puede estar vacía o tener más de 200 caracteres.");
		} else {
			this.description = description;
		}
		
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	
}
