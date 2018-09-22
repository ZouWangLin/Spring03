package cn.itheima.entity;

import org.springframework.stereotype.Component;

@Component
public class Student {
	private String id;
	private String username;
	private String sex;
	private Integer age;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", username=" + username + ", sex=" + sex + ", age=" + age + "]";
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String id, String username, String sex, Integer age) {
		super();
		this.id = id;
		this.username = username;
		this.sex = sex;
		this.age = age;
	}
	
	

}
