 package model;

import java.util.Date;

import javafx.scene.control.DatePicker;

public class Task {
	
	private int id;
	private String task;
	private Date date;
	private int days;
	private String status;
	private Category category;
	private User user;

	
	public Task(String task, Date date, int days, String status, Category category) {
		super();
		this.task = task;
		this.date = date;
		this.days = days;
		this.status = status;
		this.category = category;
		
		}

	public Task() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", task=" + task + ", date=" + date + ", days=" + days + ", status=" + status
				+ ", category=" + category + ", user=" + user + "]";
	}
	
}
