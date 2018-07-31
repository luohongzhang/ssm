package com.jiangcheng.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��������
 * @author Kivin.Jiang
 *
 */
public class JcbcUtil {

	public Connection con = null;
	
	public PreparedStatement pstmt = null;
	
	/**
	 * �õ����Ӷ���
	 */
	public void getConnection(){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/yanyan?user=yanyan&password=yanyan&useUnicode=true&characterEncoding=GB2312";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "yanyan", "yanyan");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeAll(ResultSet rs,Statement stmt,Connection con){
		if(rs != null) try { rs.close(); } catch (Exception e) {}
		if(stmt != null) try { stmt.close(); } catch (Exception e) {}
		if(con != null) try { con.close(); } catch (Exception e) {}
	}
	
	
	
	/**
	 * ����ɾ����Ϣ���е���Ϣ
	 * @param sql
	 * @param param
	 * @return
	 */
	public boolean updateBatchDel(String sql,String[] param){
		boolean flag = false;
		getConnection();
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pstmt.setString(1, param[i].trim());
				pstmt.addBatch();
			}
			pstmt.executeBatch();//����ִ��
			con.commit();//�ύ����
			flag = true;
		} catch (SQLException e) {
			try {
				con.rollback();//��������ع�
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} finally {
			closeAll(null,pstmt,con);
		}
		return flag;
	}

}
