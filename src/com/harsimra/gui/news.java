package com.harsimra.gui;

import java.awt.EventQueue;
import com.harsimra.emp.*;
import com.harsimra.jdbc.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class news {

	private JFrame frame;
	private JTextField textField;
	private JTable table;
	private	EmployeeDao Employeedao;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					news window = new news();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public news() {
		initialize();
	    try
	    {
	    Employeedao=new EmployeeDao();
	    }catch(Exception exc)
	    {
	    	System.out.println(exc.getMessage());
	    }
	}
	public void refreshEmployeeView()
	{
		try {
		List <Employee> employees = Employeedao.getAllEmployees();
		EmployeeTableModel model=new EmployeeTableModel(employees);
		table.setModel(model);}
		catch(Exception exc)
		{
			System.out.println("in Refresh Employee view");
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton_3 = new JButton("Update Employee");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateDialog dialo=new UpdateDialog(news.this,Employeedao);
				dialo.setVisible(true);
				
			}
		});
		panel.add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("Enter Last Name");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JButton Search = new JButton("Search");
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String last_name=textField.getText();
					List<Employee> employees=null;
					if(last_name!=null && last_name.trim().length()>0)
					{
						employees=Employeedao.searchEmployees(last_name);
					}
					else
					{
						employees=Employeedao.getAllEmployees();
					}
					
//					for(Employee emp:employees)
//					{
//						System.out.println(emp);
//					}
					EmployeeTableModel model=new EmployeeTableModel(employees);
				table.setModel(model);	
				}
				catch(Exception exc)
				{
					System.out.println(exc.getMessage());
				}
				
			}
		});
		panel.add(Search);
		
		JButton btnNewButton = new JButton("Add Employee");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog dialo=new dialog(news.this,Employeedao);
				dialo.setVisible(true);
			}
			
		});
		
		JButton btnNewButton_4 = new JButton("Delete Employee");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteDialog dialo=new DeleteDialog(news.this,Employeedao);
				dialo.setVisible(true);
			}
			
		});
		
		panel.add(btnNewButton_4);
		panel.add(btnNewButton);
		
//		JButton button = new JButton("New button");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		//panel.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
//		
//		JButton btnNewButton_1 = new JButton("New button");
//		scrollPane.setColumnHeaderView(btnNewButton_1);
//		
//		JButton btnNewButton_2 = new JButton("New button");
//		scrollPane.setColumnHeaderView(btnNewButton_2);
	}

}
