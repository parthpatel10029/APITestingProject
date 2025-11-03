package com.fdmgroup.pojos;

import java.util.List;

public class Managers {

	private String id;
    private double salary;
    private int age;
    private String name;
    private List<Staffs> staffs;
    
    public Managers() {
    			super();
    }
    
    	public Managers(String id, double salary, int age, String name, List<Staffs> staffs) {
    				super();
    				this.id = id;
    				this.salary = salary;
    					this.age = age;
    					this.name = name;
    					this.staffs = staffs;
    	}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public double getSalary() {
			return salary;
		}

		public void setSalary(double salary) {
			this.salary = salary;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Staffs> getStaffs() {
			return staffs;
		}

		public void setStaffs(List<Staffs> staffs) {
			this.staffs = staffs;
		}

		@Override
		public String toString() {
			return "Managers [id=" + id + ", salary=" + salary + ", age=" + age + ", name=" + name + ", staffs="
					+ staffs + "]";
		}
    	
    	
    	
    	
	
}
