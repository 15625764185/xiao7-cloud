package com.atguigu.composite;

public class Department extends OrganizationComponent {

	//û�м���

	public Department(String name, String des) {
		super(name, des);
		// TODO Auto-generated constructor stub
	}


	//add , remove �Ͳ���д�ˣ���Ϊ����Ҷ�ӽڵ�

	@Override
	protected void print() {
		// TODO Auto-generated method stub
		System.out.println(getName());
	}

}
