package com.example.androidphp;

public class Student {
	String id;
	String name;
	String surname;
	String pesel;
	String phone;
	String mail;
	String street;
	String city;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	String houseNumber;
	String cityCode;
	String active;
	
	public Student(String id, String name, String surname, String pesel, String phone, String mail, String street, String city, String houseNumber, String cityCode, String active){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
		this.phone = phone;
		this.mail = mail;
		this.street = street;
		this.city = city;
		this.houseNumber = houseNumber;
		this.cityCode = cityCode;
		this.active = active;
	}
	
	public String toString(){
		return(id +" "+ name +" "+ surname +" "+ pesel +" "+ phone +" "+ mail +" "+ street +" "+ city +" "+ houseNumber +" "+ cityCode +" "+ active );
	}


}
