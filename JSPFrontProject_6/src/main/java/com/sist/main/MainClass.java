package com.sist.main;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try
        {
        	Class clsName=Class.forName("");
        	Object obj=clsName.getDeclaredConstructor().newInstance();
        	
        }catch(Exception ex) {}
	}

}