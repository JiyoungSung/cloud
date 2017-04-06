package project;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
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
	String url,user,pass;
	public MemberDAO(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		url="jdbc:oracle:this:@localhost:1521:xe";
		user="project";
		pass="project";
	}
	public int insertMember(MemberDTO dto){
		String sql="insert into member vlaues"+"(?,?,?,?,?,?)";
		int res=0;
		try{
			con=DriverManager.getConnection(url,user,pass);
			ps=con.prepareStatement(sql);
			ps.setInt(1, dto.getNo());
			ps.setString(2, dto.getRank());
			ps.setString(3, dto.getId());
			ps.setString(4, dto.getPass());
			ps.setString(5, dto.getEmail());
			ps.setString(6, dto.getSignday());
			res=ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	public void SaveMember(MemberDTO dto) throws IOException{
		File dir2=new File("E:\\jsh_java_workspace\\201704_javaproject\\src\\project");
		File f2=new File(dir2,"memberdb.txt");
		String sql="select * from member";
		FileOutputStream fos=new FileOutputStream(f2,true);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		DataOutputStream dos=new DataOutputStream(bos);
		try{
			con=DriverManager.getConnection(url,user,pass);
			rs=ps.executeQuery();
			while(rs.next()){//rs.next():hasNext()+next() 의 기능을가지고있다.
				int no=rs.getInt("no");
				String rank=rs.getString("rank");
				String id=rs.getString("id");
				String email=rs.getString("email");
				String signday=rs.getString("signday");
				dos.writeUTF("넘버:"+no+"등급:"+rank
						+"아이디:"+id+"email:"+email+"signday:"+signday+"\n");
			}
			
		}catch(SQLException e){
			
		}
		
		/*dos.writeUTF("번호:"+dto.getNo()+"랭크:"+dto.getRank()+"아이디:"+dto.getId()+
				"비밀번호:"+dto.getPass()+"이메일:"+dto.getEmail()+"가입날:"+dto.getSignday()+"\n");
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
