package server;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String url, user, pass;

	public FileDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// url="jdbc:oracle:thin:@localhost:1521:xe";
		url = "jdbc:oracle:thin:@192.168.52.189:1521:xe";
		user = "project";
		pass = "project";
	}

	public void SaveFile() throws IOException {
		File dir1 = new File("D:\\project");
		File f1 = new File(dir1, "Filedb.txt");
		String sql = "select * from filedb";
		FileWriter fw = new FileWriter(f1, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("num");
				int filesize = rs.getInt("filesize");
				String fileid = rs.getString("fileid");
				String id = rs.getString("id");
				String upload = rs.getString("upload");

				pw.print("ID:" + id + "Fileid:" + fileid + "UploadDay:" + upload + "filesize:" + filesize + "num:" + num
						+ "\n");
				pw.flush();
			}
		} catch (SQLException e) {
			System.out.println("오라클 연결실패");
			e.printStackTrace();
		}
	}

	public void File_Save(String id, String fileid, String filesize) {

		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql = "insert into filedb values(?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, fileid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ps.setString(3, new String(sdf.format(date)));
			ps.setString(4, filesize);
			ps.setInt(5, 0);
			int res = ps.executeUpdate();
			if (res > 0) {
				System.out.println("파일DB저장 완료");
			} else {
				System.out.println("파일DB저장 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FileDAO fdao = new FileDAO();
		try {
			fdao.SaveFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void FileDelete(String id, String filename) {

		String sql = "select * from filedb";
		String sql2 = "delete from filedb where id=? and fileid = ?";

		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("id");
				if (name.equals(id)) {
					ps = con.prepareStatement(sql2);
					ps.setString(1, name);
					ps.setString(2, filename);
					ps.executeUpdate();
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void FileSize(String id, String fileid) { // 아이디 파일명
		String sql = "select * from filedb where id='" + id + "'";
		MemberDAO mdao = new MemberDAO();
		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String Fsize = rs.getString("filesize");
				String Fid = rs.getString("fileid");
				int size = Integer.parseInt(Fsize);
				if (fileid.equals(Fid)) {
					// mdao.sizeMinus(size, Fid); // 파일사이즈 / 파일명
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<FileDTO> File_Search(String id, String file) {
		String sql = "select * from filedb where id like '" + id + "' and fileid like '%" + file + "%'";
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();

		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				FileDTO dto = new FileDTO();
				dto.setFileid(rs.getString("fileid"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}