package javamysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB2 {
	// �̱��� �������� ��� �ϱ��� �� �ڵ��
	private static ConnectDB2 instance = new ConnectDB2();

	public static ConnectDB2 getInstance() {
		return instance;
	}

	public ConnectDB2() {

	}

	private String jdbcUrl = "jdbc:mysql://192.168.0.78:3306/user2"; // MySQL ���� "jdbc:mysql://localhost:3306/DB�̸�"
	private String dbId = "user2"; // MySQL ���� "������ ��� root"
	private String dbPw = "user2"; // ��й�ȣ "mysql ��ġ �� ������ ��й�ȣ"
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private ResultSet rs = null;
	private String sql = "";
	private String sql2 = "";
	String returns = "";
	String returns2 = "";

	// �����ͺ��̽��� ����ϱ� ���� �ڵ尡 ����ִ� �޼���
	public String joindb(String id, String pwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select id from runmember where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id)) { // �̹� ���̵� �ִ� ���
					returns = "id";
				} 
			} else { // �Է��� ���̵� ���� ���
				sql2 = "insert into runmember (id, pw) values(?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.executeUpdate();

				returns = "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}

	public String logindb(String id, String pwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from runmember where id=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id) && rs.getString("pw").equals(pwd)) {
					returns2 = "true";// �α��� ����
				} else {
					returns2 = "false"; // �α��� ����
				}
			} else {
				returns2 = "noId"; // ���̵� �Ǵ� ��й�ȣ ���� X
			}

		} catch (Exception e) {

		} finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}
		return returns2;
	}
}



