package com.hztianque.exceltool.test;

import java.util.ArrayList;
import java.util.List;

import ognl.Ognl;
import ognl.OgnlException;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PeopleInfo p = new PeopleInfo();
		//List<PeopleEmContact>  l  = new ArrayList<PeopleEmContact>();
		//l.add( new PeopleEmContact());
	//	p.setPeopleEmContactList(l);
		System.out.println(p.getPeopleEmContactList().size());
		
		try {
			Ognl.setValue("peopleEmContactList[0].name", p, "22");
			System.out.println(p.getPeopleEmContactList().get(0).getName());
		} catch (OgnlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
