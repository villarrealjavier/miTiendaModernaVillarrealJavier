package com.jacaranda.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "USERS")
public class User {
	@Id
	@Column (name = "us_id")
	private int id; //is assigned by the data base by auto increment
	private String userName;
	private String password;
	private String name;
	private String lastname;
	private LocalDateTime dob;
	private char sex;
	private boolean admin;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sale> sales = new ArrayList<>();
	
	/**
	 * Empty constructor 
	 */
	public User() {
		super();
		
	}

	/**
	 * Constructor with all parameters but the id which is set by the data base 
	 * @param userName - user's name
	 * @param password - user's password 
	 * @param name - user's first name 
	 * @param lastname - user's last name
	 * @param dob - user's date of birth
	 * @param sex - user's sex (female 'M', male 'H')
	 * @param admin - user's role (true:admin false:user)
	 * @throws UserException in case the parameters don't meet the specification
	 */
	public User(String userName, String password, String name, String lastname, LocalDateTime dob, char sex,
			boolean admin) throws UserException {
		super();
		setUserName(userName);
		setPassword(password);
		setName(name);
		setLastname(lastname);
		setDob(dob);
		setSex(sex);
		setAdmin(admin);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}

	/**
	 * Method that sets the userName 
	 * @param userName - user's nickname 
	 * throws an exception if the parameter is null, has more than 20 characters or is blank 
	 */
	public void setUserName(String userName) throws UserException {
		if(userName == null || userName.length() > 20 || userName.isBlank()) {
			throw new UserException("El nombre de usuario no puede estar vacío o tener más de 20 caracteres.");
		} else {
			this.userName = userName;
		}
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) throws UserException {
		
		if(password ==null || password.isBlank()) {
			throw new UserException("La contraseña no puede estar vacío ni dejarse en blanco.");
		} else {
			this.password = password;
		}
	}


	public String getName() {
		return name;
	}


	/**
	 * Method which sets the user's first name
	 * @param name - user's first name
	 * @throws UserException - In case the name is null or blank
	 */
	public void setName(String name) throws UserException {
		if(name == null || name.isBlank()) {
			throw new UserException("El nombre no puede estar vacío.");
		} else {
			this.name = name;
		}
	}


	public String getLastname() {
		return lastname;
	}


	/**
	 * Method which sets the user's last name
	 * @param lastname - user's last name
	 * @throws UserException - In case the last name is null or blank
	 */
	public void setLastname(String lastname) throws UserException {
		if(lastname == null || lastname.isBlank()) {
			throw new UserException("El apellido no puede estar vacío.");
		} else {
			this.lastname = lastname;
		}
	}

	public LocalDateTime getDob() {
		return dob;
	}


	/**
	 * Method that sets the date of birth of the user
	 * @param dob - date of birth 
	 * @throws UserException in case the date is after today
	 */
	public void setDob(LocalDateTime dob) throws UserException {
		if(dob.isAfter(LocalDateTime.now())){
			throw new UserException("La fecha de nacimiento no puede ser mayor a la fecha actual.");
		} else {
			this.dob = dob;
		}
	}


	public char getSex() {
		return sex;
	}

	/**
	 * Method that sets the user's sex
	 * @param sex - user's sex
	 * @throws UserException in case the character differs from 'M' female of 'H' male
	 */
	public void setSex(char sex) throws UserException {
		if(Character.toUpperCase(sex) != 'M' && Character.toUpperCase(sex)!= 'H') {
			throw new UserException("El valor del sexo introducido no es válido.");
		} else {
			this.sex = sex;
		}
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", name=" + name + ", lastname="
				+ lastname + ", dob=" + dob + ", sex=" + sex + ", admin=" + admin + ", sales=" + sales + "]";
	}

}
