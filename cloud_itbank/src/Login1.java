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
			System.out.println("드라이버 설치 성공");

		} catch (ClassNotFoundException e) {
			System.out.println("설치실패");
		}
		con = null;
		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";// url 의 ip주소를 바꿔준다.
															// @localhost 부분.
		// 오라클 url시작할떄는 이렇게 써야된다. jdbc: ~ :thin까지 써야됨.
		// localhost는 자기자신
		// 1521 포트번호
		// xe 오라클 버젼.
		String user = "big01";
		String pass = "big01";
		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("오라클연결성공");
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void memberSerch() {
		String sql = "select*from member";// 회원 DB
		try {
			ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
				String email = rs.getString("email");

				if (email.equals(email)) {
					// 다음 목록창을 띄운다.
					String id = rs.getString("id");
					// id를보여줘라.
					System.out.println(id);
					break;
				} else {
					System.out.println("찾는 아이디가 없습니다. or 회원가입이되있지않습니다.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void memberlog() {
		System.out.println("쿼리등록중.");
		System.out.println("이름을 입력하시오");
		String name="asd";// 로그인창의 회원. 아이디 gettext 를 하자.
		String pass="asd";// 로그인창의 비밀번호 . 비밀번호 gettext를 핮.
		String name1 = null;
		String pass1 = null;
		ps = null;
		ResultSet rs = null;
		String sql = "select*from member";// 회원 DB 를 넣자.
		try {

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
				name1 = rs.getString("ID");
				pass1 = rs.getString("pass");
				if (name.equals(name1) && pass.equals(pass1)) {
					// 다음 목록창을 띄운다.
					return;
				}
			}
			/*
			 * if(관리자 아이디와 패스워드 라면 . ){ //다른목록창을 띄운다 }
			 */
		} catch (SQLException e) {
			System.err.println("데이터베이스 불러오기실패.");
		}

	}

	public void membership() {// 회원가입버튼을눌렀을때.
		String id="asd";// 텍스트에리어나 필드(ID) 읽기
		Calendar cal = Calendar.getInstance();
		String pass="asd";// 패스워드 읽기
		String email="asd";// 이메일 읽기

		String sql1 = "insert into member values(?,?,?,?,?,?)";// 여기서 삭제도가능.
		i = 1;
		ResultSet rs1 = null;
		String sql2 = "select*from member";

		// 회원에 몇번째 회원인지 번호지정함.
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
			// 아이디가 같은게 있는지를 찾아보고 거른다.
			String name1 = null;
			ps = null;
			ResultSet rs = null;
			String sql = "select*from member";
			try {

				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
					name1 = rs.getString("name");
					if (id.equals(name1)) {
						System.out.println("같은이름이 존재합니다.처음화면으로 되돌아갑니다.");
						// 회원가입창인데 같은이름이 존재했을때 여기다가 같은이름존재할경우의이벤트를넣자.
						return;
					}
				}
			} catch (SQLException e) {
				e.getStackTrace();
			}

			// 여기서 필드 텍스트파일들을갖고와야됨.
			// ID=gettext;
			// pass=gettext;
			// email=gettext;
			ps = con.prepareStatement(sql1); // con 다리연결. 보내기만했음.
			ps.setInt(1, i);// 1번째 물음표,첫번째 자료형 인트(5)//no
			ps.setString(2, "1"); // 1 무료 ,2 유료
			i++;
			ps.setString(3, id); // 2번째 물음표, 아이디
			ps.setString(4, pass); // 3번째 물음표 , 비밀번호
			ps.setString(5, email); // 4번째물음표
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(6, new String(sdf.format(date)));
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
	}

	public boolean memberdet(String id1) {
		// 아이디가같은게있는지확인해보자
		String name1;//아이디를전부불러오는 것.
		ps = null;
		ResultSet rs = null;
		String sql = "select*from member";
		try {

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext()+next()가리키는곳에 데이터를 꺼내온다.
				name1 = rs.getString("name");
				if (id1.equals(name1)) {
					
					// 회원가입창인데 같은이름이 존재했을때 여기다가 같은이름존재할경우의이벤트를넣자.
					return true;
				}
			}
		} catch (Exception e) {
			System.err.println("ID확인 실패");
		}
		return false;
	}

	public static void main(String[] args) {
		Login1 lo=new Login1();
	}
	
}
