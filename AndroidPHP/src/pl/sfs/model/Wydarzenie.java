package pl.sfs.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Wydarzenie implements Comparable {
	 private java.lang.Integer pracownik_ID;
	 
	 private java.lang.Integer grupa_ID;

	 private java.lang.Integer wydarzenia_ID;

	 private java.lang.String wydarzenia_Kolor;

	 private java.lang.String wydarzenia_KoniecDate;

	 private java.lang.String wydarzenia_Notka;

	 public java.lang.Integer getGrupa_ID() {
		return grupa_ID;
	}

	public void setGrupa_ID(java.lang.Integer grupa_ID) {
		this.grupa_ID = grupa_ID;
	}

	private java.lang.String wydarzenia_StartDate;

	 private java.lang.String wydarzenia_Tytul;

	public java.lang.Integer getPracownik_ID() {
		return pracownik_ID;
	}

	public void setPracownik_ID(java.lang.Integer pracownik_ID) {
		this.pracownik_ID = pracownik_ID;
	}

	public java.lang.Integer getWydarzenia_ID() {
		return wydarzenia_ID;
	}

	public void setWydarzenia_ID(java.lang.Integer wydarzenia_ID) {
		this.wydarzenia_ID = wydarzenia_ID;
	}

	public java.lang.String getWydarzenia_Kolor() {
		return wydarzenia_Kolor;
	}

	public void setWydarzenia_Kolor(java.lang.String wydarzenia_Kolor) {
		this.wydarzenia_Kolor = wydarzenia_Kolor;
	}

	public java.lang.String getWydarzenia_KoniecDate() {
		return wydarzenia_KoniecDate;
	}

	public void setWydarzenia_KoniecDate(java.lang.String wydarzenia_KoniecDate) {
		this.wydarzenia_KoniecDate = wydarzenia_KoniecDate;
	}

	public java.lang.String getWydarzenia_Notka() {
		return wydarzenia_Notka;
	}

	public void setWydarzenia_Notka(java.lang.String wydarzenia_Notka) {
		this.wydarzenia_Notka = wydarzenia_Notka;
	}

	public java.lang.String getWydarzenia_StartDate() {
		return wydarzenia_StartDate;
	}

	public void setWydarzenia_StartDate(java.lang.String wydarzenia_StartDate) {
		this.wydarzenia_StartDate = wydarzenia_StartDate;
	}

	public java.lang.String getWydarzenia_Tytul() {
		return wydarzenia_Tytul;
	}

	public void setWydarzenia_Tytul(java.lang.String wydarzenia_Tytul) {
		this.wydarzenia_Tytul = wydarzenia_Tytul;
	}
	
	@SuppressLint("SimpleDateFormat")
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Wydarzenie other = (Wydarzenie) arg0;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa");
		Date startDateOther = null;
		Date startDateThis = null;
		try {
			startDateOther = format.parse(other.getWydarzenia_StartDate());
			startDateThis = format.parse(this.getWydarzenia_StartDate());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(startDateThis.after(startDateOther)){
			return 1;
		}
		else if(startDateThis.equals(startDateOther)){
			return 0;
		}
		else{
			return -1;
		}
	}
	 
}
