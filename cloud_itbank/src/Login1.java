package Login;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import javax.swing.*;
 
public class Login1 extends JFrame implements ActionListener {
	
	Connection con;
	PreparedStatement ps;
	
	int i;
	

	public void Login1() {
	
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ��ġ ����");

		} catch (ClassNotFoundException e) {
			System.out.println("��ġ����");
		}
		con = null;
		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";// url �� ip�ּҸ� �ٲ��ش�.
															// @localhost �κ�.
		// ����Ŭ url�����ҋ��� �̷��� ��ߵȴ�. jdbc: ~ :thin���� ��ߵ�.
		// localhost�� �ڱ��ڽ�
		// 1521 ��Ʈ��ȣ
		// xe ����Ŭ ����.
		String user = "big01";
		String pass = "big01";
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("����Ŭ���Ἲ��");
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void memberSerch() {
		String sql = "select*from member";// ȸ�� DB
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				String email = rs.getString("email");

				if (email.equals(email)) {
					// ���� ���â�� ����.
					String id = rs.getString("id");
					// id���������.
					System.out.println(id);
					break;
				} else {
					System.out.println("ã�� ���̵� �����ϴ�. or ȸ�������̵������ʽ��ϴ�.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void memberlog() {
		System.out.println("���������.");
		System.out.println("�̸��� �Է��Ͻÿ�");
		String name="asd";// �α���â�� ȸ��. ���̵� gettext �� ����.
		String pass="asd";// �α���â�� ��й�ȣ . ��й�ȣ gettext�� �F.
		String name1 = null;
		String pass1 = null;
		ps = null;
		ResultSet rs = null;
		String sql = "select*from member";// ȸ�� DB �� ����.
		try {

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				name1 = rs.getString("ID");
				pass1 = rs.getString("pass");
				if (name.equals(name1) && pass.equals(pass1)) {
					// ���� ���â�� ����.
					return;
				}
			}
			/*
			 * if(������ ���̵�� �н����� ��� . ){ //�ٸ����â�� ���� }
			 */
		} catch (SQLException e) {
			System.err.println("�����ͺ��̽� �ҷ��������.");
		}

	}

	public void membership() {// ȸ�����Թ�ư����������.
		String id="asd";// �ؽ�Ʈ����� �ʵ�(ID) �б�
		Calendar cal = Calendar.getInstance();
		String pass="asd";// �н����� �б�
		String email="asd";// �̸��� �б�

		String sql1 = "insert into member values(?,?,?,?,?,?)";// ���⼭ ����������.
		i = 1;
		ResultSet rs1 = null;
		String sql2 = "select*from member";

		// ȸ���� ���° ȸ������ ��ȣ������.
		try {
			ps = con.prepareStatement(sql2);
			rs1 = ps.executeQuery();
			while (rs1.next()) {
				i++;
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
		System.out.println(i);

		try {
			// ���̵� ������ �ִ����� ã�ƺ��� �Ÿ���.
			String name1 = null;
			ps = null;
			ResultSet rs = null;
			String sql = "select*from member";
			try {

				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
					name1 = rs.getString("name");
					if (id.equals(name1)) {
						System.out.println("�����̸��� �����մϴ�.ó��ȭ������ �ǵ��ư��ϴ�.");
						// ȸ������â�ε� �����̸��� ���������� ����ٰ� �����̸������Ұ�����̺�Ʈ������.
						return;
					}
				}
			} catch (SQLException e) {
				e.getStackTrace();
			}

			// ���⼭ �ʵ� �ؽ�Ʈ���ϵ�������;ߵ�.
			// ID=gettext;
			// pass=gettext;
			// email=gettext;
			ps = con.prepareStatement(sql1); // con �ٸ�����. �����⸸����.
			ps.setInt(1, i);// 1��° ����ǥ,ù��° �ڷ��� ��Ʈ(5)//no
			ps.setString(2, "1"); // 1 ���� ,2 ����
			i++;
			ps.setString(3, id); // 2��° ����ǥ, ���̵�
			ps.setString(4, pass); // 3��° ����ǥ , ��й�ȣ
			ps.setString(5, email); // 4��°����ǥ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(6, new String(sdf.format(date)));
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

	public boolean memberdet(String id1) {
		// ���̵𰡰������ִ���Ȯ���غ���
		String name1;//���̵����κҷ����� ��.
		ps = null;
		ResultSet rs = null;
		String sql = "select*from member";
		try {

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()����Ű�°��� �����͸� �����´�.
				name1 = rs.getString("name");
				if (id1.equals(name1)) {
					
					// ȸ������â�ε� �����̸��� ���������� ����ٰ� �����̸������Ұ�����̺�Ʈ������.
					return true;
				}
			}
		} catch (Exception e) {
			System.err.println("IDȮ�� ����");
		}
		return false;
	}

	public static void main(String[] args) {
		Login1 lo=new Login1();
	}
	
}
