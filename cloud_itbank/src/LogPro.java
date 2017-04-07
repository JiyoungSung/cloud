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
			System.out.println("드라이버 설치 성공");

		} catch (ClassNotFoundException e) {
			System.out.println("설치실패");
		}
		con = null;
		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";
		// 오라클 url시작할떄는 이렇게 써야된다. jdbc: ~ :thin까지 써야됨.
		// localhost는 자기자신
		// 1521 포트번호
		// xe 오라클 버젼.
		String user = "project";
		String pass = "project";
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("오라클연결성공");
			return true;
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return false;
	}

	public boolean ID_overlap(String id) {// 아이디가 중복되는지를 검증하는 메소드이다.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
				String id1 = rs.getString("id");

				if (id1.equals(id)) {
					// 다음 목록창을 띄운다.
					return true;// 중복이라면 true

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;// 중복이아니라면 false

	}

	public String ID_return(String email) {// 이메일로 아이디를 찾는다.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
				String email1 = rs.getString("email");

				if (email1.equals(email)) {

					return rs.getString("id");// 이메일이있다면 id를반환

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "1";// 실패시 1반환.
	}

	public String IDEamil_pass_return(String id, String email) {// 아이디 이메일 받아서
																// pass출력.
		String sql = "select *from member";
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
				String email1 = rs.getString("email");
				String id1 = rs.getString("id");
				if (email1.equals(email) && id1.equals(id)) {
					return rs.getString("pass");// 이메일이있다면 pass를반환
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "1";// 실패했을때의 경우.
	}

	public MemberDTO info(String id) {// 아이디 입력시 회원정보를 전체를넣는다.
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

	public void IDEamil_pass_change(String email, String pass, String pass1){// 이메일
																				// 패스받아서
																				// 패스수정.
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

	public void member_add(String id, String pass, String email, String rank) {// 회원가입버튼을눌렀을때.

		Calendar cal = Calendar.getInstance();
		String sql1 = "insert into member values(?,?,?,?,?,?)";// 여기서 삭제도가능.
		try {
			ps = con.prepareStatement(sql1); // con 다리연결. 보내기만했음.
			ps.setString(1, rank); // 1 무료 ,2 유료
			ps.setString(2, id); // 2번째 물음표, 아이디
			ps.setString(3, pass); // 3번째 물음표 , 비밀번호
			ps.setString(4, email); // 4번째물음표
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(5, new String(sdf.format(date)));
			ps.setInt(6, 0);
			int res = ps.executeUpdate(); // 업데이트를 실행하는것.
			System.out.println("res=" + res);
			if (res > 0) {
				System.out.println("자료등록성공");
			} else {
				System.out.println("자료등록 실패");
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
