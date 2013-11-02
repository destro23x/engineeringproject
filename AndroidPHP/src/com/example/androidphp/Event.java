package com.example.androidphp;

import java.util.Date;

import android.text.format.DateFormat;

public class Event implements Comparable {
	String id;
	String title;
	String note;
	Date startDate;
	Date endDate;
	String color;
	String person;
	
	public Event(String id, String title, String note, Date startDate, Date endDate, String color, String person){
		this.id = id;
		this.title = title;
		this.note = note;
		this.startDate = startDate;
		this.endDate = endDate;
		this.color = color;
		this.person = person;
		
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Event other = (Event) arg0;
		if(this.getStartDate().after(other.getStartDate())){
			return 1;
		}
		else if(this.getStartDate().equals(other.getStartDate())){
			return 0;
		}
		else{
			return -1;
		}
	}
}
