package project_1;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

public class LogPro {
	Connection con;
	PreparedStatement ps;

	public boolean start() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("�뱶�씪�씠踰� �꽕移� �꽦怨�");

		} catch (ClassNotFoundException e) {
			System.out.println("�꽕移섏떎�뙣");
		}
		con = null;
//		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		// �삤�씪�겢 url�떆�옉�븷혢혳�뒗 �씠�젃寃� �뜥�빞�맂�떎. jdbc: ~ :thin源뚯� �뜥�빞�맖.
		// localhost�뒗 �옄湲곗옄�떊
		// 1521 �룷�듃踰덊샇
		// xe �삤�씪�겢 踰꾩졏.
		String user = "project";
		String pass = "project";
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("�삤�씪�겢�뿰寃곗꽦怨�");
			return true;
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return false;
	}

	public boolean ID_overlap(String id) {// �븘�씠�뵒媛� 以묐났�릺�뒗吏�瑜� 寃�利앺븯�뒗 硫붿냼�뱶�씠�떎.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {// hasNext()+next()媛�由ы궎�뒗怨녹뿉 �뜲�씠�꽣瑜� 爰쇰궡�삩�떎.
				String id1 = rs.getString("id");

				if (id1.equals(id)) {
					// �떎�쓬 紐⑸줉李쎌쓣 �쓣�슫�떎.
					return true;// 以묐났�씠�씪硫� true

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;// 以묐났�씠�븘�땲�씪硫� false

	}

	public String ID_return(String email) {// �씠硫붿씪濡� �븘�씠�뵒瑜� 李얜뒗�떎.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()媛�由ы궎�뒗怨녹뿉 �뜲�씠�꽣瑜� 爰쇰궡�삩�떎.
				String email1 = rs.getString("email");

				if (email1.equals(email)) {

					return rs.getString("id");// �씠硫붿씪�씠�엳�떎硫� id瑜쇰컲�솚

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "1";// �떎�뙣�떆 1諛섑솚.
	}

	public String IDEamil_pass_return(String id, String email) {// �븘�씠�뵒 �씠硫붿씪 諛쏆븘�꽌
																// pass異쒕젰.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()媛�由ы궎�뒗怨녹뿉 �뜲�씠�꽣瑜� 爰쇰궡�삩�떎.
				String email1 = rs.getString("email");
				String id1 = rs.getString("id");
				if (email1.equals(email) && id1.equals(id)) {
					return rs.getString("pass");// �씠硫붿씪�씠�엳�떎硫� pass瑜쇰컲�솚
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "1";// �떎�뙣�뻽�쓣�븣�쓽 寃쎌슦.
	}

	public MemberDTO info(String id) {// �븘�씠�뵒 �엯�젰�떆 �쉶�썝�젙蹂대�� �쟾泥대�쇰꽔�뒗�떎.
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
				dto.setSingday(rs.getString("signday"));
				dto.setRank(rs.getString("rank"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public void IDEamil_pass_change(String email, String pass, String pass1){// �씠硫붿씪
																				// �뙣�뒪諛쏆븘�꽌
																				// �뙣�뒪�닔�젙.
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

	public void member_add(String id, String pass, String email, String rank) {// �쉶�썝媛��엯踰꾪듉�쓣�닃���쓣�븣.
		
		Calendar cal = Calendar.getInstance();
		String sql1 = "insert into member values(?,?,?,?,?,?)";// �뿬湲곗꽌 �궘�젣�룄媛��뒫.
		try {
			ps = con.prepareStatement(sql1); // con �떎由ъ뿰寃�. 蹂대궡湲곕쭔�뻽�쓬.
			ps.setString(1, rank); // 1 臾대즺 ,2 �쑀猷�
			ps.setString(2, id); // 2踰덉㎏ 臾쇱쓬�몴, �븘�씠�뵒
			ps.setString(3, pass); // 3踰덉㎏ 臾쇱쓬�몴 , 鍮꾨�踰덊샇
			ps.setString(4, email); // 4踰덉㎏臾쇱쓬�몴
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(5, new String(sdf.format(date)));
			ps.setInt(6, 0);
			int res = ps.executeUpdate(); // �뾽�뜲�씠�듃瑜� �떎�뻾�븯�뒗寃�.
			System.out.println("res=" + res);
			if (res > 0) {
				System.out.println("�옄猷뚮벑濡앹꽦怨�");
			} else {
				System.out.println("�옄猷뚮벑濡� �떎�뙣");
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
