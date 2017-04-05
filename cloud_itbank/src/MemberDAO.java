

import java.sql.*;
import java.util.*;

public class MemberDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	String url, user, pass;
	
	public MemberDAO(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		user = "big01";
		pass = "big01";
	}
	public int insertMember(MemberDTO dto){
		String sql = "insert into member values(java_member.nextval, ?, ?, ?";
		int res = 0;
		try{
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getHp());
			ps.setString(3, dto.getEmail());
			res = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	public ArrayList<MemberDTO> listMember(){
		String sql = "select * from member";
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		try{
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt("no"));
				dto.setName(rs.getString("name"));
				dto.setHp(rs.getString("hp"));
				dto.setEmail(rs.getString("email"));
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	public int deleteMember(String name){
		String sql = "delete from member where name = ?";
		int res=0;
		try{
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			res = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	
	
}
