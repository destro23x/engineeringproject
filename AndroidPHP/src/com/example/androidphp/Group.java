package com.example.androidphp;

public class Group {
	String idGroup;
	String idCourse;
	String groupName;
	String groupActive;
	String groupLevel;
	
	public Group(String idGroup, String idCourse, String groupName, String groupActive, String groupLevel){
		this.idGroup = idGroup;
		this.idCourse = idCourse;
		this.groupName = groupName;
		this.groupActive = groupActive;
		this.groupLevel = groupLevel;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public String getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(String idCourse) {
		this.idCourse = idCourse;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupActive() {
		return groupActive;
	}

	public void setGroupActive(String groupActive) {
		this.groupActive = groupActive;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}
	
	
	
}
