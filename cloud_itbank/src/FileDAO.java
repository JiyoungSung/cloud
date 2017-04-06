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

public class FileDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	String url,user,pass;
	public FileDAO(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		url="jdbc:oracle:this:@localhost:1521:xe";
		user="project";
		pass="project";
	}
	public int insertFile(FileDTO dto){
		String sql="insert into member vlaues"+"(?,?,?,?,?)";
		int res=0;
		try{
			con=DriverManager.getConnection(url,user,pass);
			ps=con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getFileid());
			ps.setString(3, dto.getUpload());
			ps.setDouble(4, dto.getFilesize());
			ps.setInt(5, dto.getNum());
			res=ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	public void SaveFile(FileDTO dto) throws IOException{
		File dir1=new File("E:\\jsh_java_workspace\\201704_javaproject\\src\\project");
		File f1=new File(dir1,"memberdb.txt");

		FileOutputStream fos=new FileOutputStream(f1,true);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		DataOutputStream dos=new DataOutputStream(bos);
		dos.writeUTF("아이디:"+dto.getId()+"파일명:"+dto.getFileid()+"업로드일자:"
		+dto.getUpload()+"파일크기:"+dto.getFilesize()+"다운횟수:"+dto.getNum()+"\n");
		dos.flush();
	}
}
