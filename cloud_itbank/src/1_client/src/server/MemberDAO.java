package server;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MemberDAO {
	Connection con;
	PreparedStatement ps = null;
	String url;
	String pass;
	String user;
	boolean check = false;

	public MemberDAO() {
		this.start();
	}

	public void start() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ��ġ ����");

		} catch (ClassNotFoundException e) {
			System.out.println("��ġ����");
		}
		con = null;
		url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";
		// ����Ŭ url�����ҋ��� �̷��� ��ߵȴ�. jdbc: ~ :thin���� ��ߵ�.
		// localhost�� �ڱ��ڽ�
		// 1521 ��Ʈ��ȣ
		// xe ����Ŭ ����.
		user = "project";
		pass = "project";
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("����Ŭ���Ἲ��");
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public boolean ID_overlap(String id) {// ���̵� �ߺ��Ǵ����� �����ϴ� �޼ҵ��̴�.
		String sql = "select *from member";
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				String id1 = rs.getString("id");

				if (id1.equals(id)) {
					// ���� ���â�� ����.
					return true;// �ߺ��̶�� true

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;// �ߺ��̾ƴ϶�� false

	}

	public boolean email_check(String mail) {
		String sql = "select *from member where email=?";
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, mail);
			rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;// �ߺ��̾ƴ϶�� false

	}

	public String ID_return(String email) {// �̸��Ϸ� ���̵� ã�´�.
		String sql = "select *from member";
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				String email1 = rs.getString("email");
				if (email1.equals(email)) {
					return true + "%" + rs.getString("id");// �̸������ִٸ� id����ȯ
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false + "";// ���н� 1��ȯ.
	}

	public String IDEamil_pass_return(String id, String email) {// ���̵� �̸��� �޾Ƽ�
																// pass���.
		String sql = "select *from member";
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				String email1 = rs.getString("email");
				String id1 = rs.getString("id");
				if (email1.equals(email) && id1.equals(id)) {
					return rs.getString("pass");// �̸������ִٸ� pass����ȯ
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "1";// ������������ ���.
	}

	public MemberDTO info(String id) {// ���̵� �Է½� ȸ�������� ��ü���ִ´�.
		String sql = "select * from member where id=?";
		MemberDTO dto = new MemberDTO();
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setEmail(rs.getString("email"));
				dto.setDay(rs.getString("signday"));
				dto.setRank(rs.getString("rank"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public void IDEamil_pass_change(String email, String pass, String pass1) {// �̸���
																				// �н��޾Ƽ�
																				// �н�����.
		String sql = "update member  set pass=? where email=? and pass=?";
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, pass1);
			ps.setString(2, email);
			ps.setString(3, pass);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String id, String pass) {
		String sql = "select * from member where id=? ";
		boolean t = false;
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (pass.equals(rs.getString("pass"))) {
					t = true;
					return t;
				}
			}
		} catch (SQLException e) {
		}
		System.out.println("�α����Լ�");
		System.out.println(t);
		return t;
	}

	public void member_add(String id, String pass, String email, String rank) {// ȸ�����Թ�ư����������.
		Calendar cal = Calendar.getInstance();
		String sql1 = "insert into member values(?,?,?,?,?)";// ���⼭ ����������.
		try {
			// con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql1); // con �ٸ�����. �����⸸����.
			ps.setString(1, rank); // 1 ���� ,2 ����
			ps.setString(2, id); // 2��° ����ǥ, ���̵�
			ps.setString(3, pass); // 3��° ����ǥ , ��й�ȣ
			ps.setString(4, email); // 4��°����ǥ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(5, new String(sdf.format(date)));
			int res = ps.executeUpdate(); // ������Ʈ�� �����ϴ°�.
			System.out.println("res=" + res);
			if (res > 0) {
				System.out.println("�ڷ��ϼ���");
			} else {
				System.out.println("�ڷ��� ����");
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
}
