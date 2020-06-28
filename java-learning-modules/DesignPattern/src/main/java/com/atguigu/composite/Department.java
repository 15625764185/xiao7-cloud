package com.atguigu.composite;

public class Department extends OrganizationComponent {

	//没有集合

	public Department(String name, String des) {
		super(name, des);
		// TODO Auto-generated constructor stub
	}


	//add , remove 就不用写了，因为他是叶子节点

	@Override
	protected void print() {
		// TODO Auto-generated method stub
		System.out.println(getName());
	}

}
