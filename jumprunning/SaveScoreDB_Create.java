package jumprunning;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveScoreDB_Create {

	public static void main(String[] args) {
		String driver = "com.mysql.jdbc.Driver"; //����̹� �ε� 
		String url = "jdbc:mysql://localhost:3306/user2?useSSL=false"; //�����ͺ��̽��� �����ϱ� ���� url����
		Connection conn = null; //�����ͺ��̽��� ������ �����Ǵ� ���� ��ü�� �����ȴ�. �������θ� �� �� �ִ� Ŭ������.
		Statement stmt = null; //�����ͺ��̽����� ����� ������ ��ü��.
		try {
			Class.forName(driver); //�̸��� �ش��ϴ� Ŭ������ ��üȭ �ϰڴٴ� ���� 
			conn = DriverManager.getConnection(url, "user2", "user2"); //DriverManagerŬ������ Ŀ�ؼ��� ���� ���� Ŭ������. �����ͺ��̽��� ���������� ���ڴٶ�� ���̴�.
			stmt = conn.createStatement(); //�����ͺ��̽� ������ ������ ��ü�� ���ؼ� ������Ʈ��Ʈ ��ü�� ������ �����̴�.
			String sql = "CREATE TABLE SaveScore("+ "DATE VARCHAR(30) NOT NULL, "
													+ "NAME VARCHAR(10) NOT NULL, "
													+ "SCORE INT(4) NOT NULL)";
			int result = stmt.executeUpdate(sql); //�����ϸ� ���, �����ϸ� ������ ���´�.
			String msg = result > -1 ?  "successful" : " fail";
			System.out.println(msg);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
