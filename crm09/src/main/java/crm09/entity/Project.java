package crm09.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Project {
	int id;
	String name;
	LocalDate startDay;
	LocalDate endDay;
	StatusProject status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getStartDay() {
		return startDay;
	}
	public void setStartDay(LocalDate date) {
		this.startDay = date;
	}
	public LocalDate getEndDay() {
		return endDay;
	}
	public void setEndDay(LocalDate endDay) {
		this.endDay = endDay;
	}
	public StatusProject getStatus() {
		return status;
	}
	public void setStatus(StatusProject status) {
		this.status = status;
	}
	
}
