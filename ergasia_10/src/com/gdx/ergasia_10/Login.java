package com.gdx.ergasia_10;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.junit.Test;

public class Login extends JFrame {

	private Container contentPane;
	private JTextField username;
	private JPasswordField password;
	private Db_connector dbC= new Db_connector();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	public Login() {
		/*
		 * eisagontai username kai password ginetai elegxos an einai swsta kai anoigei thn katallhlh kartela analoga me ton xrhsth pou ekane login
		 */
		setTitle("Είσοδος");
		contentPane=getContentPane();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(dim.width/2-150, dim.height/2-90, 300, 180);
		/*--------------*/
		this.setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		
		
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(38, 25, 70, 14);
		contentPane.add(lblUsername);
		
		username = new JTextField();
		username.setBounds(38, 45, 100, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Κωδικός:");
		lblPassword.setBounds(158, 25, 70, 14);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(158, 45, 100, 20);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*
				 * pairnei ta stoixia kanei elegxo kai emfanizei munhma lathous h anoigei thn katalhlh kartela analoga thn orthothta autwn
				 */
				
				int flag=dbC.doLogin(username.getText(),password.getPassword()); 
				
				if(flag==3){ //idioktitis
					Admin frameAdmin = new Admin();
					frameAdmin.setVisible(true);
					dispose();
				}else if(flag==2){ //ipalilos
					try {
						Ipalilos frameIpalilos = new Ipalilos();
						frameIpalilos.setVisible(true);
						dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(flag==1){ //pelatis
					try {
						Pelatis framePelatis = new Pelatis();
						framePelatis.setVisible(true);
						dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Τα στοιχεία είναι λάθος!");
				}
				
			}
		});
		btnLogin.setBounds(100, 76, 100, 40);
		contentPane.add(btnLogin);
		
		
		
	}
	
	
}
