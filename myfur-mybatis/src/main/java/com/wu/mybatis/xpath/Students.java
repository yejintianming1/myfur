package com.wu.mybatis.xpath;

public class Students {
	private int id;
	private int socre;
	private String name;
	private String sex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSocre() {
		return socre;
	}
	public void setSocre(int socre) {
		this.socre = socre;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "Students [id=" + id + ", socre=" + socre + ", name=" + name + ", sex=" + sex + "]";
	}	
}