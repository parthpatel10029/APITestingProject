package com.fdmgroup.pojos;

public class Foods {

	private int id;
	private String Name;
	private String price;
	
	public Foods() {
		super();
	}
	
	public Foods(int id, String name, String price) {
		super();
		this.id = id;
		Name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Foods [id=" + id + ", Name=" + Name + ", price=" + price + "]";
	}
	
	
	
}
