package com.jiangcheng.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

	private static Properties map = new Properties();
	private static final ThreadLocal<Connection> tol = new ThreadLocal<Connection>();
	
	static{
		InputStream is = null;
		
		try {
			is = JdbcUtil.class.getResourceAsStream("/conf/conn.properties");
			map.load(is);
		} catch (Exception e) {
			throw new RuntimeException("读取配置错误");
		} finally {
			if(is != null) try{ is.close(); }catch(Exception e) {}
		}
	}

	public static Connection getConnection(){
		try {
			Connection con = tol.get();
			if(con == null){
				Class.forName(map.getProperty("driver"));
				con = DriverManager.getConnection(map.getProperty("url"), map.getProperty("username"), map.getProperty("passwrod"));
				tol.set(con);
			}
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取连接错误");
		}
	}

	public static void closeAll(ResultSet rs,Statement stmt,Connection con){
		if(rs != null) try { rs.close(); } catch (Exception e) {}
		if(stmt != null) try { stmt.close(); } catch (Exception e) {}
		if(con != null) try { con.close(); } catch (Exception e) {}
	}

	public boolean updateBatchDel(String sql,String[] param){
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pstmt.setString(i, param[i].trim());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
			flag = true;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			closeAll(null,pstmt,con);
		}
		return flag;
	}

}
