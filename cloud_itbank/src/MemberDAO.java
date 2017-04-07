package project_client;

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
			System.out.println("���dao ����Ŭ���� ����");
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
				String rank=rs.getString("rank");
				String id=rs.getString("id");
				String pass=rs.getString("pass");
				String email=rs.getString("email");
				String signday=rs.getString("signday");
				int membersize=rs.getInt("membersize");
				pw.print("Rank:"+rank
						+"ID:"+id+"PASSWORD:"+pass+"Email:"+email+"Signday:"+signday+"Membersize:"
						+"\n");
				pw.flush();
			}	
		}catch(SQLException e){
			System.out.println("����");
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
