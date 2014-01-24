package com.gdx.ergasia_10;

import static org.junit.Assert.*;

import javax.swing.JComboBox;

import org.junit.Test;

public class TestClass {

	Db_connector dbC=new Db_connector();
	
	@Test
	public void testGetDomatiaCount() {
		
		assertEquals(8,dbC.getDomatiaCount());
	}
	
	
	@Test
	public void testCheckTodayFree() {
		
		assertFalse(dbC.checkTodayFree(1));
	}
	
	@Test
	public void testCheckKratisi() {
		
		assertTrue(dbC.checkKratisi("2014/02/14","2014/02/15","Giwrgos","Rakitzis","6973047764"));
	}
	
	@Test(timeout=2000)
	public void openKratisi() {
		Kratisi frame = new Kratisi();
		frame.setVisible(true);
	}
	
	@Test(timeout=2000)
	public void openIpalilos() {
		Ipalilos frame = new Ipalilos();
		frame.setVisible(true);
	}
	
	@Test(timeout=2000)
	public void openPelatis() {
		Pelatis frame = new Pelatis();
		frame.setVisible(true);
	}
	
	@Test(timeout=2000)
	public void openLogin() {
		Login frame = new Login();
		frame.setVisible(true);
	}
	
	@Test(timeout=2000)
	public void openAdmin() {
		Admin frame = new Admin();
		frame.setVisible(true);
	}
	
	@Test(timeout=2000)
	public void openDomatioAdd() {
		DomatioAdd frame = new DomatioAdd();
		frame.setVisible(true);
	}
	
	@Test(timeout=2000)
	public void testSearchTime() {
		dbC.searchDomatia("2014/01/28","2014/01/30");
	}
	
	
	
	@Test(timeout=10000)
	public void testDoKratisiTime() {
		dbC.doKratisi("Giwrgos","Rakitzis","6989524125","2014/02/12","2014/02/14",4);
	}
	
	@Test
	public void testDoKratisi() {
		assertFalse(dbC.doKratisi("Dimitris","Manolis","69895241aa","2014/02/12","2014/02/14",4));
	}
	
	@Test
	public void testDomatioAdd() {
		assertFalse(dbC.domatioAdd("500","4","200"));
		assertFalse(dbC.domatioAdd("5s0","s","200"));
		assertFalse(dbC.domatioAdd("500","4","200"));
		assertFalse(dbC.domatioAdd("500","4","20s0"));
	}
	
	@Test(timeout=2000)
	public void searchClick() {
		Ipalilos ip=new Ipalilos();
		ip.searchClick();
	}
	
	@Test
	public void testLogin() {
		char[] p={'a','b'};
		assertEquals(0,dbC.doLogin(" ",p));
	}
	
	
	
	
	
	

}
