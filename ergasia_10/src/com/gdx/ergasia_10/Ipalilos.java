package com.gdx.ergasia_10;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class Ipalilos extends JFrame{

	private Container contentPane;
	private Db_connector dbC= new Db_connector();
	private String[] columnNames = {"A/A","Κωδικός","Κλίνες","Κατάσταση","Τιμή"};
	Object[][] data=new Object[dbC.getDomatiaCount()][5];
	Object[][] dataSearch=new Object[dbC.getDomatiaCount()][5];
	Object[][] SavedData=new Object[dbC.getDomatiaCount()][5];
	private ArrayList<HashMap<String, String>> domatia,domatiaSearch;
	private HashMap<String, String> hashMap;
	JComboBox<String> minas,imera,etos,minasE,imeraE,etosE;
	private boolean loadingMonths=false,loadingMonthsE=false;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton cancelButton;
	private DefaultTableModel model;
	
	public Ipalilos() {
		setTitle("Πάνελ Διαχείρισης");
		contentPane=getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(dim.width/2-250, dim.height/2-200, 500, 400);
		/*--------------*/
		this.setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		
		JLabel diathesimaApoLabel = new JLabel("Από:");
		diathesimaApoLabel.setBounds(10, 4, 40, 14);
		contentPane.add(diathesimaApoLabel);
		
		imera = new JComboBox<String>();
		imera.setBounds(50, 2, 40, 20);
		contentPane.add(imera);
		
		minas = new JComboBox<String>();
		minas.setBounds(93, 2, 40, 20);
		contentPane.add(minas);
		
		etos = new JComboBox<String>();
		etos.setBounds(135, 2, 70, 20);
		contentPane.add(etos);
		
		JLabel diathesimaMexriLabel = new JLabel("Μέχρι:");
		diathesimaMexriLabel.setBounds(220, 4, 40, 14);
		contentPane.add(diathesimaMexriLabel);
		
		imeraE = new JComboBox<String>();
		imeraE.setBounds(280, 2, 40, 20);
		contentPane.add(imeraE);
		
		minasE = new JComboBox<String>();
		minasE.setBounds(323, 2, 40, 20);
		contentPane.add(minasE);
		
		etosE = new JComboBox<String>();
		etosE.setBounds(365, 2, 70, 20);
		contentPane.add(etosE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Μενού");
		menuBar.add(mnNewMenu);
		
		JMenuItem menuAddDomatio = new JMenuItem("Προσθήκη Δωματίου");
		menuAddDomatio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent  arg0) {
				try {
					DomatioAdd frameDomatioAdd = new DomatioAdd();
					frameDomatioAdd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnNewMenu.add(menuAddDomatio);
		
		JMenuItem menuKratisi = new JMenuItem("Κράτηση");
		menuKratisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent  arg0) {
				try {
					Kratisi frameKratisi = new Kratisi();
					frameKratisi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnNewMenu.add(menuKratisi);
		
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
		SavedData=data;
		model = new DefaultTableModel(data,columnNames);
		table = new JTable();
		table.setModel(model);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 50, 500, 360);
		contentPane.add(scrollPane);
		
		JButton searchButton = new JButton("Αναζήτηση");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchClick();
			}
		});
		searchButton.setBounds(400, 25, 95, 23);
		contentPane.add(searchButton);
		
		cancelButton = new JButton("Ακύρωση");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				data=SavedData;
				model = new DefaultTableModel(data,columnNames);
				table.setModel(model);
			}
		});
		cancelButton.setBounds(290, 25, 89, 23);
		contentPane.add(cancelButton);
		
		for(int k=1;k<32;k++){
			if(k<10){
				imera.addItem("0"+k);
				imeraE.addItem("0"+k);
			}else{
				imera.addItem(""+k);
				imeraE.addItem(""+k);
			}
			if(k>Calendar.getInstance().get(Calendar.MONTH) && k<13){
				if(k<10){
					minas.addItem("0"+k);
					minasE.addItem("0"+k);
				}else{
					minas.addItem(""+k);
					minasE.addItem(""+k);
				}
			}
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		etos.addItem(year+"");
		etosE.addItem(year+"");
		etos.addItem(++year+"");
		etosE.addItem(year+"");
		

		minas.addActionListener (new ActionListener () {
			
			@Override
		    public void actionPerformed(ActionEvent e) {
				if(!loadingMonths)
					dbC.correctDays(imera,Integer.parseInt(minas.getSelectedItem().toString()));
		    }
			
		});
		
		etos.addActionListener (new ActionListener () {
			
			@Override
		    public void actionPerformed(ActionEvent e) {
				loadingMonths=true;
				dbC.correctMonths(minas);
				loadingMonths=false;
		    }
			
		});
		
		minasE.addActionListener (new ActionListener () {
			
			@Override
		    public void actionPerformed(ActionEvent e) {
				if(!loadingMonthsE)
					dbC.correctDays(imeraE,Integer.parseInt(minasE.getSelectedItem().toString()));
		    }
			
		});
		
		etosE.addActionListener (new ActionListener () {
			
			@Override
		    public void actionPerformed(ActionEvent e) {
				loadingMonthsE=true;
				dbC.correctMonths(minasE);
				loadingMonthsE=false;
		    }
			
		});
		
		
		
		
		
		
		
	}
	
	public void searchClick(){
		String hmera=etos.getSelectedItem().toString()+"/"+minas.getSelectedItem().toString()+"/"+imera.getSelectedItem().toString();
		String hmeraE=etosE.getSelectedItem().toString()+"/"+minasE.getSelectedItem().toString()+"/"+imeraE.getSelectedItem().toString();
		domatiaSearch=dbC.searchDomatia(hmera,hmeraE);
		hashMap = new HashMap<String, String>();
		if(domatiaSearch!=null){
			for(int i=0;i<domatiaSearch.size();i++){
				hashMap=domatiaSearch.get(i);
				dataSearch[i][0]=i+1;
				dataSearch[i][1]=hashMap.get("kodikos");
				dataSearch[i][2]=hashMap.get("atoma");
				dataSearch[i][3]="Ελεύθερο";
				dataSearch[i][4]=hashMap.get("timi");
				
			}
			data=dataSearch;
			model = new DefaultTableModel(data,columnNames);
			table.setModel(model);
		}else{
			JOptionPane.showMessageDialog(null, "Δεν υπάρχουν αποτελέσματα!");
		}
	}
}
