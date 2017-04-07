package project_client;
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

public class LogPro {
	Connection con;
	PreparedStatement ps;

	public boolean start() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ��ġ ����");

		} catch (ClassNotFoundException e) {
			System.out.println("��ġ����");
		}
		con = null;
		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";
		// ����Ŭ url�����ҋ��� �̷��� ��ߵȴ�. jdbc: ~ :thin���� ��ߵ�.
		// localhost�� �ڱ��ڽ�
		// 1521 ��Ʈ��ȣ
		// xe ����Ŭ ����.
		String user = "project";
		String pass = "project";
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("����Ŭ���Ἲ��");
			return true;
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return false;
	}

	public boolean ID_overlap(String id) {// ���̵� �ߺ��Ǵ����� �����ϴ� �޼ҵ��̴�.
		String sql = "select *from member";
		try {
			ps = null;
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

	public String ID_return(String email) {// �̸��Ϸ� ���̵� ã�´�.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				String email1 = rs.getString("email");

				if (email1.equals(email)) {

					return rs.getString("id");// �̸������ִٸ� id����ȯ

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "1";// ���н� 1��ȯ.
	}

	public String IDEamil_pass_return(String id, String email) {// ���̵� �̸��� �޾Ƽ�
																// pass���.
		String sql = "select *from member";
		try {
			ps = null;
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
		String sql = "select *from member where id=?";
		MemberDTO dto = new MemberDTO();
		try {
			ps = null;
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

	public void IDEamil_pass_change(String email, String pass, String pass1){// �̸���
																				// �н��޾Ƽ�
																				// �н�����.
		String sql = "update member  set pass=? where email=?,pass=?";
		try {
			ps = null;
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
		String sql = "select * from member where id=? and pass=?";
		boolean t = false;
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			while (rs.next()) {
				t = true;
			}
		} catch (SQLException e) {
		}
		return t;
	}

	public void member_add(String id, String pass, String email, String rank) {// ȸ�����Թ�ư����������.

		Calendar cal = Calendar.getInstance();
		String sql1 = "insert into member values(?,?,?,?,?,?)";// ���⼭ ����������.
		try {
			ps = con.prepareStatement(sql1); // con �ٸ�����. �����⸸����.
			ps.setString(1, rank); // 1 ���� ,2 ����
			ps.setString(2, id); // 2��° ����ǥ, ���̵�
			ps.setString(3, pass); // 3��° ����ǥ , ��й�ȣ
			ps.setString(4, email); // 4��°����ǥ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(5, new String(sdf.format(date)));
			ps.setInt(6, 0);
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
		MemberDAO mdao=new MemberDAO();
		try {
			mdao.SaveMember();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log_sign(String id) {
		PrintWriter pw;
		String msg = id;
		try {
			InetAddress ia = InetAddress.getByName("localhost");
			Socket soc = new Socket(ia, 1521);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
			pw.println(id);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * byte str[] = msg.getBytes(); try { InetAddress ia =
		 * InetAddress.getByName("192.168.52.140"); DatagramPacket dp = new
		 * DatagramPacket(str, str.length, ia, 12345); DatagramSocket ds = new
		 * DatagramSocket(); ds.send(dp); ds.close(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
	}
}
