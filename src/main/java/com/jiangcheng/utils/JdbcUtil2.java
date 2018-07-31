package com.jiangcheng.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * ��������
 * @author Kivin.Jiang
 *
 */
public class JdbcUtil2 {
	//�洢����
	private static Properties map = new Properties();
	//�洢����
	private static final ThreadLocal<Connection> tol = new ThreadLocal<Connection>();
	
	static{
		InputStream is = null;
		
		try {
			// ��ȡ�����ļ�����ȡ��Ϣ
			//is = new FileInputStream("d:/workspace/Jdbc/src/conf/conn.properties");
			is = JdbcUtil2.class.getResourceAsStream("/conf/conn.properties");
			//�������Լ������,Class.getResourceAsStream(String Path):path���ԡ�/����ͷʱĬ���ǴӴ������ڵİ���ȡ��Դ,
			//��ͷ�Ǵ�ClassPath���»�ȡ.����ֻ��ͨ��path����һ������·��,���ջ�����ClassLoader��ȡ��Դ.
			//src��confͬ��Jdbc���£�����Ŀ¼̫������ʱ������ѡ����Class.getResourceAsStream('֪��JdbcUtil3.class�����ĸ��ļ���,
			//�����ҳ�conn.Properties�����·��,/�ʹ������·���Ķ���,����ô���');
			map.load(is);
		} catch (Exception e) {
			throw new RuntimeException("�����ļ���ȡ���ִ���");
		} finally {
			if(is != null) try{ is.close(); }catch(Exception e) {}
		}
	}
	
	/**
	 * �õ����Ӷ���
	 * @return 
	 */
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
			throw new RuntimeException("��������,�����޷���ȡ");
		}
	}

	/**
	 * �ر�����
	 * @param rs
	 * @param stmt
	 * @param con
	 */
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
