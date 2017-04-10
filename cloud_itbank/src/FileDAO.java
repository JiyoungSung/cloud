package project_1;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			System.out.println("�삤�씪�겢 �뿰寃곗떎�뙣");
			e.printStackTrace();
		}
	}
	
	public void File_Save(String id, String fileid, String filesize){
		
		try{
			con = DriverManager.getConnection(url, user, pass);
		}catch(SQLException e) { e.printStackTrace(); }
		
		String sql = "insert into filedb values(?,?,?,?,?)";
		try{
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, fileid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(3, new String(sdf.format(date)));
			ps.setString(4, filesize);
			ps.setInt(5, 0);
			int res = ps.executeUpdate();
			if(res > 0){
				System.out.println("�뙆�씪DB���옣 �셿猷�");
			}else{
				System.out.println("�뙆�씪DB���옣 �떎�뙣");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		FileDAO fdao = new FileDAO();
		try{
		fdao.SaveFile();
		}catch (IOException e) { e.printStackTrace(); }
	}
	
	
	
}