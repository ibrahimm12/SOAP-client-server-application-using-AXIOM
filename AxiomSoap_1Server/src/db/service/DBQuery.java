package db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

	String userName, password;

	public DBQuery(String userName, String password) {

		this.userName = userName;
		this.password = password;

	}

	public String executeQuery1(String query) {

		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		String columnValues = "";

		try {


			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://mysql.cc.puv.fi:3306/e1401184_examples";
			
			connection = DriverManager.getConnection(url, userName, password);
			
			statement = connection.createStatement();

			rs = statement.executeQuery(query);
			
			
			while (rs.next()){
				columnValues+=rs.getString(1) + "\t";
               
       
            }

		} catch (Exception ex) {

			return ex.getMessage();

		} finally {

			try {

				// Here we close all open streams

				if (statement != null)

					statement.close();

				if (connection != null)

					connection.close();
           
			} catch (SQLException sqlexc) {

				return sqlexc.getMessage();

			}

		}

		return columnValues;

	}
	

public String executeQuery(String query,String name,String date,String comment) {

		
		Connection connection = null;
		PreparedStatement ps=null;
		int resultSet = 0;

		String columnHeadings = "Data hasn't been saved!";

		try {


			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://mysql.cc.puv.fi:3306/e1401184_examples";
			
			connection = DriverManager.getConnection(url, userName, password);
			
			ps = (PreparedStatement) connection.prepareStatement(query);

			ps.setString(1, name);
			ps.setString(2, date);
			ps.setString(3, comment);
			resultSet+=ps.executeUpdate();
			if(resultSet!=0){
				columnHeadings="Insert data successfully";
			}

		} catch (Exception ex) {

			return ex.getMessage();

		} finally {

			try {

				// Here we close all open streams

				if (ps != null)

					ps.close();

				if (connection != null)

					connection.close();

			} catch (SQLException sqlexc) {

				return sqlexc.getMessage();

			}

		}

		return columnHeadings;

	}


}