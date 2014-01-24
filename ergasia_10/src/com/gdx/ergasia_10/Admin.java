package com.gdx.ergasia_10;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class Admin extends JFrame {

	private Container contentPane;
	private Db_connector dbC= new Db_connector();
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private String[] columnNames = {"A/A","Κωδικός","Κλίνες","Κατάσταση","Τιμή"};
	Object[][] data=new Object[dbC.getDomatiaCount()][5];
	private ArrayList<HashMap<String, String>> domatia;
	private HashMap<String, String> hashMap;

	
	public Admin() {
		setTitle("Πάνελ Διαχείρισης");
		contentPane=getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(dim.width/2-250, dim.height/2-200, 500, 400);
		/*--------------*/
		this.setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		
		JLabel sumResLabel = new JLabel("Σύνολο Κρατήσεων:");
		sumResLabel.setBounds(10, 10, 114, 14);
		contentPane.add(sumResLabel);
		
		JLabel sumResLabelVal = new JLabel(dbC.getAllKratiseisSum()+"");
		sumResLabelVal.setBounds(134, 10, 104, 14);
		contentPane.add(sumResLabelVal);
		
		JLabel sumResMonthLabel = new JLabel("Κρατήσεις Μήνα:");
		sumResMonthLabel.setBounds(255, 10, 104, 14);
		contentPane.add(sumResMonthLabel);
		
		JLabel sumResMonthLabelVal = new JLabel(dbC.getMonthKratiseisSum()+"");
		sumResMonthLabelVal.setBounds(355, 10, 104, 14);
		contentPane.add(sumResMonthLabelVal);
		
		JLabel xristesLabel = new JLabel("Χρήστες:");
		xristesLabel.setBounds(172, 10, 50, 14);
		contentPane.add(xristesLabel);
		
		JLabel xristesLabelVal = new JLabel(dbC.getUsersSum()+"");
		xristesLabelVal.setBounds(230, 10, 104, 14);
		contentPane.add(xristesLabelVal);
		
		JLabel sumEsodaLabel = new JLabel("Σύνολο Εσόδων:");
		sumEsodaLabel.setBounds(10, 30, 114, 14);
		contentPane.add(sumEsodaLabel);
		
		JLabel sumEsodaLabelVal = new JLabel(dbC.esodaSum()+"");
		sumEsodaLabelVal.setBounds(134, 30, 104, 14);
		contentPane.add(sumEsodaLabelVal);
		
		JLabel  sumEsodaMonthLabel = new JLabel("Έσοδα Μήνα:");
		sumEsodaMonthLabel.setBounds(172, 30, 80, 14);
		contentPane.add(sumEsodaMonthLabel);
		
		JLabel sumEsodaMonthLabelVal = new JLabel(dbC.esodaMonthSum()+"");
		sumEsodaMonthLabelVal.setBounds(260, 30, 104, 14);
		contentPane.add(sumEsodaMonthLabelVal);
		
		domatia=dbC.getDomatia();
		hashMap = new HashMap<String, String>();
		for(int i=0;i<domatia.size();i++){
			hashMap=domatia.get(i);
			data[i][0]=i+1;
			data[i][1]=hashMap.get("kodikos");
			data[i][2]=hashMap.get("atoma");
			try{
				if(dbC.checkTodayFree(Integer.parseInt(hashMap.get("id"))))
					data[i][3]="Ελεύθερο";
				else
					data[i][3]="Μη διαθέσιμο";
			}catch(NumberFormatException e){
				data[i][3]="";
			}
			data[i][4]=hashMap.get("timi");
			
		}
		model = new DefaultTableModel(data,columnNames);
		table = new JTable();
		table.setModel(model);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 50, 500, 360);
		contentPane.add(scrollPane);
	}
}
