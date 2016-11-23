package com.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Result;

public class Resultdal {
	public Resultdal() {
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String DBDRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String DBURL = "jdbc:mysql://localhost:3306/crawlmgl?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";

	public static boolean save(Result result) {
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			
			con = DriverManager.getConnection(DBURL, "root", "ROOT");
			//System.out.println("数据库连接成功！");
		} catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch块结束

		try {
			String word = result.getTheWord();
			int count = result.getTheCount();
			
			
			String sql = "{ call saveResult(?,?) }";
			cs =con.prepareCall(sql);
			cs.setString(1, word);
			cs.setInt(2, count);					
			cs.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			return false;
		} finally {

			if (rs != null) { // 关闭数据集
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (cs != null) { // 关闭语句
				try {
					cs.close();
				} catch (SQLException e) {
				}

			}

			if (con != null) { // 关闭连接
				try {
					con.close();
				} catch (SQLException e) {
				}

			}
		}// try/catch/finally块结束

	}// main方法结束

	public static boolean updateResult(String word) {
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			
			con = DriverManager.getConnection(DBURL, "root", "ROOT");
			//System.out.println("数据库连接成功！");
		} catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch块结束

		try {
			
			String sql = "{ call updateResult(?) }";
			cs =con.prepareCall(sql);
			cs.setString(1, word);					
			cs.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			return false;
		} finally {

			if (rs != null) { // 关闭数据集
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (cs != null) { // 关闭语句
				try {
					cs.close();
				} catch (SQLException e) {
				}

			}

			if (con != null) { // 关闭连接
				try {
					con.close();
				} catch (SQLException e) {
				}

			}
		}// try/catch/finally块结束

	}// main方法结束
	
	public static boolean contain(String word) {
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(DBURL, "root", "ROOT");
			//System.out.println("数据库连接成功！");
		} catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch块结束

		try {
		
			String sql = "{ call getResult(?) }";
			cs =con.prepareCall(sql);
			cs.setString(1, word);						
			rs = cs.executeQuery();
			return displayResult(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {

			if (rs != null) { // 关闭数据集
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (cs != null) { // 关闭语句
				try {
					cs.close();
				} catch (SQLException e) {
				}

			}

			if (con != null) { // 关闭连接
				try {
					con.close();
				} catch (SQLException e) {
				}

			}
		}// try/catch/finally块结束

	}// main方法结束

	public static List<Result> showAllResult() {
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			
			con = DriverManager.getConnection(DBURL, "root", "ROOT");
			//System.out.println("数据库连接成功！");
		} catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch块结束

		try {
			String sql = "{ call showAllResult() }";
			cs =con.prepareCall(sql);
			rs = cs.executeQuery();
			return convertList(rs);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			return null;
			
		} finally {

			if (rs != null) { // 关闭数据集
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (cs != null) { // 关闭语句
				try {
					cs.close();
				} catch (SQLException e) {
				}

			}

			if (con != null) { // 关闭连接
				try {
					con.close();
				} catch (SQLException e) {
				}

			}
		}// try/catch/finally块结束
		

	}// main方法结束
	
	private static boolean displayResult(ResultSet rs) throws SQLException {	
		rs.last();
		int hanghao = rs.getRow();
		if (hanghao > 0) {
		return true;	
		} else {

			return false;
		}
		
	}
	
	//resultset转换成list
	private static List<Result> convertList(ResultSet rs) throws SQLException {
		List<Result> list = new ArrayList();		
		while (rs.next()) {
			Result model = new Result();
			String word = rs.getString("word");
			int count = rs.getInt("count");
			model.setTheCount(count);
			model.setTheWord(word);
			list.add(model);
		}
		return list;
	}
}
