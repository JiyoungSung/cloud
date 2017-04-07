
import java.io.*;
import java.sql.*;

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
		File dir2=new File("E:\\jsh_java_workspace\\201704_javaproject\\src\\project");
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
				int no=rs.getInt("no");
				String rank=rs.getString("rank");
				String id=rs.getString("id");
				String pass=rs.getString("pass");
				String email=rs.getString("email");
				String signday=rs.getString("signday");
				pw.print("No:"+no+"Rank:"+rank
						+"ID:"+id+"PASSWORD:"+pass+"Email:"+email+"Signday:"+signday+"\n");
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
	
	
}
