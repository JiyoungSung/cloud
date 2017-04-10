package project_1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MemberDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	String url,user,pass,rank,id,email,signday;
	public MemberDAO(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("멤버dao 오라클접속 오류");
		}
		url="jdbc:oracle:thin:@localhost:1521:xe";
		user="project";
		pass="project";
	}
	public void SaveMember() throws IOException{
		File dir2=new File("E:"+File.separator+"jsh_java_workspace"+File.separator+"201704_javaproject"+File.separator+
				"src"+File.separator+"project");
		File f2=new File(dir2,"memberdb.txt");
		String sql="select * from member";
		FileWriter fw=new FileWriter(f2,true);
		BufferedWriter bw=new BufferedWriter(fw);
		PrintWriter pw=new PrintWriter(bw);
		try{
			con=DriverManager.getConnection(url,user,pass);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				String rank=rs.getString("rank");
				String id=rs.getString("id");
				String pass=rs.getString("pass");
				String email=rs.getString("email");
				String signday=rs.getString("signday");
				int membersize=rs.getInt("membersize");
				pw.print("Rank:"+rank
						+"ID:"+id+"PASSWORD:"+pass+"Email:"+email+"Signday:"+signday+"Membersize:"
						+membersize+"\n");
				pw.flush();
			}	
		}catch(SQLException e){
			System.out.println("오류");
			e.printStackTrace();
		}
	}
	public int deleteMember(String name){
		String sql="delete from member where name =?";
		int res=0;
		try{
			con=DriverManager.getConnection(url,user,pass);
		ps=con.prepareStatement(sql);
		ps.setString(1, name);
		res=ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	
	public void SizePluse(int a, String id){
		
		String sql = "select * from member";
		
		try{
			con = DriverManager.getConnection(url,user,pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MemberDTO mem = new MemberDTO();
				int size = rs.getInt("membersize");
				System.out.println("size =>" + size);
				System.out.println("a =>" + a);
				size += a;
				String sql2 = "update member set membersize='"+ size + "' where id='" + id +"'";
				ps = con.prepareStatement(sql2);
				ps.executeUpdate();
			}
			
		}catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	
	
}