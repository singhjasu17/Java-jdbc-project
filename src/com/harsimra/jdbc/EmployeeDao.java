package com.harsimra.jdbc;
import java.sql.*;
import java.math.BigDecimal;
import java.util.*;
import java.io.*;
import com.harsimra.emp.Employee;

public class EmployeeDao {
   
	private Connection myConn=null;
	public EmployeeDao() throws Exception
	{
		Properties props=new Properties();
		props.load(new FileInputStream("demo.properties"));
		String user=props.getProperty("user");
		String pass=props.getProperty("password");
		String dburl=props.getProperty("dburl");
		myConn=DriverManager.getConnection(dburl,user,pass);
		System.out.println("User Connection successful to dburl - "+dburl);
	}
	public void deleteEmployee(String first_name,String last_name) throws Exception
	{
		PreparedStatement myStat=null;
		try
		{
			String f_name=first_name;
			String l_name=last_name;
		    myStat=myConn.prepareStatement("delete from employees where ( first_name= ? and last_name= ? )");
		    myStat.setString(1, f_name);
		    myStat.setString(2, l_name);
		    myStat.executeUpdate();
		}
		finally
		{
			close(myStat,null);
		}
	}
	public void updateEmployee(Employee emp) throws Exception
	{
		PreparedStatement myStat=null;
		try
		{
			String first_name=emp.getFirst_name();
			String last_name=emp.getLast_name();
			BigDecimal salary=emp.getSalary();
			String email=emp.getEmail();
			myStat=myConn.prepareStatement("update employees set salary= ? , email = ? where (first_name= ? and last_name=?)");
			myStat.setBigDecimal(1, salary);
            myStat.setString(2, email);
            myStat.setString(3, first_name);
            myStat.setString(4, last_name);
			myStat.executeUpdate();
			
			
			
		}
		finally
		{
			close(myStat,null);
		}
	}
	private Employee convertRowToEmployee(ResultSet rs) throws Exception
	{
		int id=rs.getInt("id");
		String first_name=rs.getString("first_name");
		String last_name=rs.getString("last_name");
		String email=rs.getString("email");
		BigDecimal salary=rs.getBigDecimal("salary");
		
		Employee emp=new Employee(id,last_name,first_name,email,salary);
		return emp;
		
	}
	public List<Employee> searchEmployees(String name)throws Exception
	{
		List<Employee> list = new ArrayList<>();
		PreparedStatement myStat=null;
		ResultSet myRs=null;
		try
		{
			name+="%";
			myStat=myConn.prepareStatement("select * from employees where last_name like ?");
			myStat.setString(1, name);
			myRs=myStat.executeQuery();
			while(myRs.next())
			{
				Employee temp=convertRowToEmployee(myRs);
				list.add(temp);
			}
			return list;
		}
finally
		{
			close(myStat,myRs);
		}
		
	}
	
	
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}
	
	
	
	public void addEmployee(Employee emp) throws Exception
	{
		PreparedStatement myStat=null;
		try {
		myStat=myConn.prepareStatement("insert into employees(last_name,first_name,email,department,salary) values(?, ? , ? ,'New dept', ?)");
        myStat.setString(1, emp.getLast_name());
        myStat.setString(2, emp.getFirst_name());
        myStat.setString(3, emp.getEmail());
        myStat.setBigDecimal(4, emp.getSalary());
        myStat.executeUpdate();
		}
		finally
        {
        	close(myStat,null);
        }
	}
	public List<Employee> getAllEmployees() throws Exception
	{
		List <Employee> list = new ArrayList<>();
		Statement myStat=null;
		ResultSet myRs=null;
		try
		{
			myStat=myConn.createStatement();
			myRs=myStat.executeQuery("select * from employees");
			while(myRs.next())
			{
                 Employee tempEmp=convertRowToEmployee(myRs);
                 list.add(tempEmp);
			}
			
		return list;
			
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
			close(myStat,myRs);
		}
		return list;
	}
	public static void main(String[] args) throws Exception {

		EmployeeDao dao = new EmployeeDao();
		System.out.println(dao.searchEmployees("thom"));

		System.out.println(dao.getAllEmployees());
		BigDecimal bg=new BigDecimal(12);
		
		
		
		// TODO Auto-generated method stub

	}

}
