package com.gdx.ergasia_10;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JComboBox;

import org.junit.Test;

public class Kratisi extends JFrame {

	private Container contentPane;
	private JTextField onomaTextField,eponimoTextField,phoneTextField;
	private JComboBox<String> minas,imera,etos,minasE,imeraE,etosE,atomaBox;
	private boolean loadingMonths=false,loadingMonthsE=false;
	private Db_connector dbC= new Db_connector();

	public Kratisi() {
		setTitle("Κράτηση");
		contentPane=getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(dim.width/2-150, dim.height/2-190, 300, 380);
		/*--------------*/
		this.setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		
		JLabel onomaLabel = new JLabel("Όνομα:");
		onomaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		onomaLabel.setBounds(10, 46, 73, 17);
		contentPane.add(onomaLabel);
		
		onomaTextField = new JTextField();
		onomaTextField.setBounds(110, 40, 156, 30);
		contentPane.add(onomaTextField);
		onomaTextField.setColumns(10);
		
		JLabel eponimoLabel = new JLabel("Επώνυμο:");
		eponimoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		eponimoLabel.setBounds(10, 85, 73, 17);
		contentPane.add(eponimoLabel);
		
		eponimoTextField = new JTextField();
		eponimoTextField.setBounds(110, 79, 156, 30);
		contentPane.add(eponimoTextField);
		eponimoTextField.setColumns(10);
		
		JLabel phoneLabel = new JLabel("Τηλέφωνο:");
		phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		phoneLabel.setBounds(10, 125, 83, 17);
		contentPane.add(phoneLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(110, 118, 156, 30);
		contentPane.add(phoneTextField);
		phoneTextField.setColumns(10);
		
		JLabel dateLabel = new JLabel("Ημ. Εισόδου:");
		dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		dateLabel.setBounds(10, 165, 95, 17);
		contentPane.add(dateLabel);
		
		JLabel meresLabel = new JLabel("Ημ. Εξόδου:");
		meresLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		meresLabel.setBounds(10, 205, 90, 17);
		contentPane.add(meresLabel);
		
		JLabel atomaLabel = new JLabel("’τομα:");
		atomaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		atomaLabel.setBounds(10, 245, 90, 17);
		contentPane.add(atomaLabel);
		
		/**/
		
		JButton kratisiButton = new JButton("Κράτηση");
		kratisiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String hmera=etos.getSelectedItem().toString()+"/"+minas.getSelectedItem().toString()+"/"+imera.getSelectedItem().toString();
				String hmeraE=etosE.getSelectedItem().toString()+"/"+minasE.getSelectedItem().toString()+"/"+imeraE.getSelectedItem().toString();
				if(dbC.checkKratisi(hmera,hmeraE,onomaTextField.getText(),eponimoTextField.getText(),phoneTextField.getText())){
					if(dbC.doKratisi(onomaTextField.getText(),eponimoTextField.getText(),phoneTextField.getText(),hmera,hmeraE,Integer.parseInt(atomaBox.getSelectedItem().toString()))){
						JOptionPane.showMessageDialog(null, "Η κράτηση πραγματοποιήθηκε!");
						onomaTextField.setText("");
						eponimoTextField.setText("");
						phoneTextField.setText("");
					}				
				}
			}
		});
		kratisiButton.setBounds(100, 290, 100, 40);
		contentPane.add(kratisiButton);
		
		imera = new JComboBox<String>();
		imera.setBounds(110, 165, 40, 20);
		contentPane.add(imera);
		
		minas = new JComboBox<String>();
		minas.setBounds(153, 165, 40, 20);
		contentPane.add(minas);
		
		etos = new JComboBox<String>();
		etos.setBounds(196, 165, 70, 20);
		contentPane.add(etos);
		
		imeraE = new JComboBox<String>();
		imeraE.setBounds(110, 205, 40, 20);
		contentPane.add(imeraE);
		
		minasE = new JComboBox<String>();
		minasE.setBounds(153, 205, 40, 20);
		contentPane.add(minasE);
		
		etosE = new JComboBox<String>();
		etosE.setBounds(196, 205, 70, 20);
		contentPane.add(etosE);
		
		atomaBox = new JComboBox<String>();
		atomaBox.setBounds(110, 245, 40, 20);
		contentPane.add(atomaBox);
		

		for(int k=1;k<32;k++){
			if(k<10){
				imera.addItem("0"+k);
				imeraE.addItem("0"+k);
			}else{
				imera.addItem(""+k);
				imeraE.addItem(""+k);
			}
			if(k<5)
				atomaBox.addItem(k+"");
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
	
	
	
	
	
	
	
	
}
