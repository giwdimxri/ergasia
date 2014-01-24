package com.gdx.ergasia_10;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DomatioAdd extends JFrame {

	private Container contentPane;
	private JTextField kodikosTextField,atomaTextField,timiTextField;
	private Db_connector dbC= new Db_connector();

	public DomatioAdd() {
		setTitle("Προσθήκη Δωματίου");
		contentPane=getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(dim.width/2-150, dim.height/2-150, 300, 300);
		/*--------------*/
		this.setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		
		JLabel kodikosLabel = new JLabel("Κωδικός:");
		kodikosLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		kodikosLabel.setBounds(10, 46, 73, 17);
		contentPane.add(kodikosLabel);
		
		kodikosTextField = new JTextField();
		kodikosTextField.setBounds(110, 40, 156, 30);
		contentPane.add(kodikosTextField);
		kodikosTextField.setColumns(10);
		
		JLabel atomaLabel = new JLabel("’τομα:");
		atomaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		atomaLabel.setBounds(10, 85, 73, 17);
		contentPane.add(atomaLabel);
		
		atomaTextField = new JTextField();
		atomaTextField.setBounds(110, 79, 156, 30);
		contentPane.add(atomaTextField);
		atomaTextField.setColumns(10);
		
		JLabel timiLabel = new JLabel("Τιμή:");
		timiLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		timiLabel.setBounds(10, 125, 73, 17);
		contentPane.add(timiLabel);
		
		timiTextField = new JTextField();
		timiTextField.setBounds(110, 119, 156, 30);
		contentPane.add(timiTextField);
		timiTextField.setColumns(10);
		
		JButton addButton = new JButton("Προσθήκη");
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(dbC.domatioAdd(kodikosTextField.getText(),atomaTextField.getText(),timiTextField.getText())){
					JOptionPane.showMessageDialog(null, "Προστέθηκε!");
					kodikosTextField.setText("");
					atomaTextField.setText("");
					timiTextField.setText("");
				}
					
			}
		});
		addButton.setBounds(100, 190, 100, 40);
		contentPane.add(addButton);
	}

}
