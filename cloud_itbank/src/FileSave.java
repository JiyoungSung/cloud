package project_1;
import java.io.IOException;
import java.sql.*;
import java.text.*;
import java.util.Date;

public class FileSave {
	
	private Connection con;
	private PreparedStatement ps;
	private String id, fileid, filesize ;
	private String dbid = "project";
	private String pass = "project";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	
	public FileSave(String id, String fileid, String filesize){
		
		this.id = id;
		this.fileid = fileid;
		this.filesize = filesize;
		try{
		con = DriverManager.getConnection(url, dbid, pass);
		}catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public void File_Save(){
		
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
				System.out.println("파일DB저장 완료");
			}else{
				System.out.println("파일DB저장 실패");
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
