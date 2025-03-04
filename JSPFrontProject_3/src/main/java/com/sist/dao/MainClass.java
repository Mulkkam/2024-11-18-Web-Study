package com.sist.dao;
class A
{
	String name="홍길동";
}

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A a=new A();
		a.name="심청이";
		System.out.println(a.name);
		// 서버        
		a=new A();
		System.out.println(a.name);
	}

}
