package project;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
		url="jdbc:oracle:this:@localhost:1521:xe";
		user="project";
		pass="project";
	}
	public void SaveMember() throws IOException{
		File dir2=new File("E:\\jsh_java_workspace\\201704_javaproject\\src\\project");
		File f2=new File(dir2,"memberdb.txt");
		String sql="select * from member";
		FileOutputStream fos=new FileOutputStream(f2,true);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		DataOutputStream dos=new DataOutputStream(bos);
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
				dos.writeUTF("�ѹ�:"+no+"���:"+rank
						+"���̵�:"+id+"email:"+email+"signday:"+signday+"\n");
				dos.flush();
			}	
		}catch(SQLException e){
			System.out.println("����");
		}
		
		/*dos.writeUTF("��ȣ:"+dto.getNo()+"��ũ:"+dto.getRank()+"���̵�:"+dto.getId()+
				"��й�ȣ:"+dto.getPass()+"�̸���:"+dto.getEmail()+"���Գ�:"+dto.getSignday()+"\n");
		dos.flush();*/
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
