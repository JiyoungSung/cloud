package project;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		url="jdbc:oracle:thin:@localhost:1521:xe";
		user="project";
		pass="project";
	}
	public void SaveFile() throws IOException{
		File dir1=new File("E:\\jsh_java_workspace\\201704_javaproject\\src\\project");
		File f1=new File(dir1,"Filedb.txt");
		String sql="select * from filedb";
		FileWriter fw=new FileWriter(f1,true);
		BufferedWriter bw=new BufferedWriter(fw);
		PrintWriter pw=new PrintWriter(bw);
		try{
			con=DriverManager.getConnection(url,user,pass);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				int num=rs.getInt("num");
				int filesize=rs.getInt("filesize");
				String fileid=rs.getString("fileid");
				String id=rs.getString("id");
				String upload=rs.getString("upload");
				
				pw.print("ID:"+id+"Fileid:"+fileid+"UploadDay:"+upload+"filesize:"+filesize
						+"num:"+num+"\n");
				pw.flush();
			}	
		}catch(SQLException e){
			System.out.println("¿À·ù");
			e.printStackTrace();
		}
	}
}
