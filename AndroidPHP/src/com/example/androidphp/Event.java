package com.example.androidphp;

public class Event {
	String id;
	String title;
	String note;
	String startDate;
	String endDate;
	String color;
	String person;
	
	public Event(String id, String title, String note, String startDate, String endDate, String color, String person){
		this.id = id;
		this.title = title;
		this.note = note;
		this.startDate = startDate;
		this.endDate = endDate;
		this.color = color;
		this.person = person;
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString(){
		return null; //nie ma zwracac nula
	}
}
