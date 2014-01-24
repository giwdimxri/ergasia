package com.gdx.ergasia_10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Db_connector {
	Connection connection;
	public Db_connector(){
		/*
		 * me thn dimiourgia tou antikeimenou ginetai kai sundesh sthn vash
		 */
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/ergasia_10?useUnicode=true&characterEncoding=UTF-8&";
			connection = DriverManager
			          .getConnection(url
			              + "user=gdx&password=gdx2014");
			Statement s=connection.createStatement();
			s.execute("SET NAMES UTF8");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Αποτυχία σύνδεσης!");
			e.printStackTrace();
		}
	}
	
	public int doLogin(String u,char[] p){
		int flag=0;
		String pass="",who=null;
		for(int i=0;i<p.length;i++)
			pass+=p[i];
		
		if(u.length()==0 || pass.length()==0)
			return 0;
		
		try{
			PreparedStatement statment = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
			statment.setString(1, u);
			statment.setString(2, pass);
			ResultSet r=statment.executeQuery();
			if(r.next())
				flag=r.getInt("who");
		} catch (SQLException e) {
			e.printStackTrace();
			flag=0;
		} catch (Exception e) {
			e.printStackTrace();
			flag=0;
		}
		return flag;
	}
	
	public int getDomatiaCount(){
		try {
			PreparedStatement statment = connection.prepareStatement("SELECT count(*) as num FROM domatia");
			ResultSet rs = statment.executeQuery();
			if(rs.next())
				return rs.getInt("num");
			else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public ArrayList<HashMap<String, String>> getDomatia(){
		ArrayList<HashMap<String, String>> domatia=new ArrayList<HashMap<String, String>>();
		try {
			PreparedStatement statment = connection.prepareStatement("SELECT * FROM domatia");
			ResultSet rs = statment.executeQuery();
			while(rs.next()){
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("id", rs.getString("id"));
				hashMap.put("kodikos", rs.getString("kodikos"));
				hashMap.put("atoma", rs.getInt("atoma")+"");
				hashMap.put("timi", rs.getInt("timi")+"");
				domatia.add(hashMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return domatia;
	}
	
	public boolean doKratisi(String onoma,String eponimo,String phone,String imera_eisodou,String imera_exodou,int atoma){
		String queryDomatia="SELECT * FROM domatia WHERE atoma=?";
		PreparedStatement sDomatia;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT domatia.id FROM domatia INNER JOIN kratiseis" +
					" ON domatia.id=domatio_id WHERE atoma=? AND imera_eisodou>=? AND imera_eisodou<=? AND imera_exodou>=? AND" +
					" imera_exodou>=?");
			s.setInt(1, atoma);
			s.setString(2, imera_eisodou+" 00:00:00");
			s.setString(3, imera_exodou+" 00:00:00");
			s.setString(4, imera_eisodou+" 00:00:00");
			s.setString(5, imera_exodou+" 00:00:00");
			ResultSet r=s.executeQuery();
			if(r.next()){
				queryDomatia+=" AND id<>"+r.getInt("id");
				while(r.next()){
					queryDomatia+=" AND id<>"+r.getInt("id");
				}
				sDomatia= connection.prepareStatement(queryDomatia+" LIMIT 1");
				sDomatia.setInt(1, atoma);
			}else{
				sDomatia= connection.prepareStatement(queryDomatia+" LIMIT 1");
				sDomatia.setInt(1, atoma);
			}
			 r=sDomatia.executeQuery();
			 if(!r.next()){
				 JOptionPane.showMessageDialog(null, "Δεν υπάρχουν διαθέσιμα δωμάτια την συγκεκριμένη ημερομηνία!");
				 return false;
			 }else{
				
				PreparedStatement statment = connection.prepareStatement("INSERT INTO kratiseis(onoma,eponimo,phone,imera_eisodou,imera_exodou,domatio_id) VALUES (?,?,?,?,?,?)");
				statment.setString(1, onoma);
				statment.setString(2, eponimo);
				statment.setString(3, phone);
				statment.setString(4, imera_eisodou);
				statment.setString(5, imera_exodou);
				statment.setInt(6, r.getInt("id"));
				if(statment.executeUpdate()==0){
					JOptionPane.showMessageDialog(null, "Κάτι πήγε στραβά παρακαλώ δοκιμάστε αργότερα!");
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Κάτι πήγε στραβά παρακαλώ δοκιμάστε αργότερα!");
			return false;
		}
		
		return true;
	}
	
	public boolean checkTodayFree(int id){
		PreparedStatement s;
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
			Date date = new Date();
			
			s = connection.prepareStatement("SELECT * FROM domatia INNER JOIN kratiseis" +
					" ON domatia.id=domatio_id WHERE domatia.id=? AND imera_eisodou<=? AND imera_exodou>=?");
			s.setInt(1, id);
			s.setString(2, dateFormat.format(date)+" 00:00:00");
			s.setString(3, dateFormat.format(date)+" 00:00:00");
			ResultSet r=s.executeQuery();
			if(r.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void correctMonths(JComboBox<String> c){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int etosV=Integer.parseInt(c.getSelectedItem().toString());
		int k=1;
		c.removeAllItems();
		if(etosV==year)
			k=Calendar.getInstance().get(Calendar.MONTH)+1;
		for(int i=k;i<13;i++){
			c.addItem(i+"");
		}
	}
	
	public void correctDays(JComboBox<String> c,int minasV){
		int count=c.getItemCount();
		if(minasV==2){
			
			while(count>28){
				c.removeItemAt((count--)-1);
			}
		}

		if(minasV==4 || minasV==6  || minasV==9  || minasV==11){
			while(count>30)
				c.removeItemAt((count--)-1);
			if(count<30){
				while(count!=30)
					c.addItem((++count)+"");
			}
		}else{
			if(count<31 && minasV!=2){
				while(count!=31)
					c.addItem((++count)+"");
			}
		}
	}
	
	public boolean checkKratisi(String hmera,String hmeraE,String onoma,String eponimo,String phone){
		if(onoma.length()<3){
			JOptionPane.showMessageDialog(null, "Δώστε έγκυρο όνομα!");
			return false;
		}
		if(eponimo.length()<3){
			JOptionPane.showMessageDialog(null, "Δώστε έγκυρο επώνυμο!");
			return false;
		}
		try{
			Double.parseDouble(phone);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Το τηλέφωνο δεν είναι έγκυρο!");
			return false;
		}
		if(phone.length()!=10){
			JOptionPane.showMessageDialog(null, "Το τηλέφωνο δεν είναι έγκυρο!");
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
			Date dateS =sdf.parse(hmera+" 00:00:00");
			Date dateE =sdf.parse(hmeraE+" 00:00:00");
			if(!dateS.before(dateE)){
				JOptionPane.showMessageDialog(null, "Η ημερομηνία εξόδου πρέπει να είναι μετά την ημερομηνία εισόδου!");
				return false;
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean domatioAdd(String k,String a,String t){
		int kodikos,atoma,timi;
		try{
			kodikos=Integer.parseInt(k);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Ο κωδικός δωματίου δεν είναι έγκυρος!");
			return false;
		}
		try{
			atoma=Integer.parseInt(a);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Ο αριθμός ατόμων δεν είναι έγκυρος!");
			return false;
		}
		try{
			timi=Integer.parseInt(t);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Η τιμή δωματίου δεν είναι έγκυρη!");
			return false;
		}
		try {
			PreparedStatement s = connection.prepareStatement("INSERT INTO domatia(kodikos,atoma,timi) VALUES(?,?,?)");
			s.setInt(1, kodikos);
			s.setInt(2, atoma);
			s.setInt(3, timi);
			if(s.executeUpdate()==0){
				JOptionPane.showMessageDialog(null, "Ο κωδικός δωματίου υπάρχει ήδη!");
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Κάτι πήγε στραβά. Παρακαλώ δοκιμάστε αργότερα!");
			return false;
		}
		
		return true;
	}
	
	public ArrayList<HashMap<String,String>> searchDomatia(String imera_eisodou,String imera_exodou){
		ArrayList<HashMap<String, String>> domatia=new ArrayList<HashMap<String, String>>();
		String queryDomatia="SELECT * FROM domatia WHERE";
		PreparedStatement sDomatia;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT domatia.id FROM domatia INNER JOIN kratiseis" +
					" ON domatia.id=domatio_id WHERE imera_eisodou>=? AND imera_eisodou<=? AND imera_exodou>=? AND" +
					" imera_exodou>=?");
			s.setString(1, imera_eisodou+" 00:00:00");
			s.setString(2, imera_exodou+" 00:00:00");
			s.setString(3, imera_eisodou+" 00:00:00");
			s.setString(4, imera_exodou+" 00:00:00");
			ResultSet r=s.executeQuery();
			if(r.next()){
				queryDomatia+=" id<>"+r.getInt("id");
				while(r.next()){
					queryDomatia+=" AND id<>"+r.getInt("id");
				}
				sDomatia= connection.prepareStatement(queryDomatia);
			}else{
				sDomatia= connection.prepareStatement(queryDomatia);
			}
			 r=sDomatia.executeQuery();
			 while(r.next()){
				 HashMap<String, String> hashMap = new HashMap<String, String>();
				 hashMap.put("id", r.getInt("id")+"");
				 hashMap.put("kodikos", r.getString("kodikos"));
				 hashMap.put("atoma", r.getInt("atoma")+"");
				 hashMap.put("timi", r.getInt("timi")+"");
				 domatia.add(hashMap);
			 }
		}catch(SQLException e){
			return null;
		}catch(Exception e){
			return null;
		}
		return domatia;
	}
	
	public int getUsersSum(){
		int sum=0;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT COUNT(*) as num FROM users");
			ResultSet r=s.executeQuery();
			r.next();
			sum=r.getInt("num")-2;
		} catch (SQLException e) {
			
		}
		
		return sum;
	}
	
	public int getAllKratiseisSum(){
		int sum=0;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT COUNT(*) as num FROM kratiseis");
			ResultSet r=s.executeQuery();
			r.next();
			sum=r.getInt("num");
		} catch (SQLException e) {
		
		}
		
		return sum;
	}
	
	public int esodaSum(){
		int sum=0;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT SUM(timi) as num FROM kratiseis INNER JOIN domatia ON domatia.id=domatio_id");
			ResultSet r=s.executeQuery();
			r.next();
			sum=r.getInt("num");
		} catch (SQLException e) {
		
		}
		
		return sum;
	}
	
	public int esodaMonthSum(){
		int sum=0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/");
		Date date = new Date();
		String today=dateFormat.format(date);
		System.out.println(today);
		try {
			PreparedStatement s = connection.prepareStatement("SELECT SUM(timi) as num FROM kratiseis INNER JOIN domatia ON domatia.id=domatio_id WHERE imera_eisodou>=? AND imera_eisodou<=?");
			s.setString(1, today+"01 00:00:00");
			s.setString(2, today+"30 00:00:00");
			ResultSet r=s.executeQuery();
			r.next();
			sum=r.getInt("num");
		} catch (SQLException e) {
			
		}
		return sum;
	}
	
	public int getMonthKratiseisSum(){
		int sum=0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/");
		Date date = new Date();
		String today=dateFormat.format(date);
		System.out.println(today);
		try {
			PreparedStatement s = connection.prepareStatement("SELECT COUNT(*) as num FROM kratiseis WHERE imera_eisodou>=? AND imera_eisodou<=?");
			s.setString(1, today+"01 00:00:00");
			s.setString(2, today+"30 00:00:00");
			ResultSet r=s.executeQuery();
			r.next();
			sum=r.getInt("num");
		} catch (SQLException e) {
			
		}
		return sum;
	}
	
}
