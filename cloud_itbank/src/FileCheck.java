package project_1;

import java.sql.*;
// ������ �ɱ�??
public class FileCheck {

	private String id, filename;
	private PreparedStatement ps = null;
	private Connection con = null;
	private ResultSet rs =null;
	private String dbid = "project";
	private String pass = "project";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	
	
	FileCheck(String id, String fileid){
		
		this.id = id;
		this.filename = fileid;
		try{
		con = DriverManager.getConnection(url, dbid, pass);
		}catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	public int File(){
		
		String spl = "select * from filedb";
		int check = 0;
		try{
			ps = con.prepareStatement(spl);
			rs = ps.executeQuery();
			while(rs.next()){
				String Cid = rs.getString("id");
				String Cfile = rs.getString("fileid");
				if(id.equals(Cid)){
					if(filename.equals(Cfile)){ check = 1;; }
				else{ System.out.println("���� ���ϸ�"); check = 2;}
				}else { System.out.println("��ġ���� �ʴ� ���̵�"); check = 3;}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	
	
	
	
}
