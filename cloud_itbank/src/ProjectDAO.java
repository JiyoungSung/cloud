

import java.io.*; 
import java.sql.*;
import java.util.*;

public class ProjectDAO {


	private ResultSet rs = null;
	private Connection con = null; // Ŀ�ؼ� ����
	private String url, user, pass;
	private Calendar cal;
	private int filesize;
	private int membersize;
	private ProjectDTO dto;

	public ProjectDAO() {
		dto = new ProjectDTO();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ��ġ ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� ��ġ ����");
			e.printStackTrace();
			
		}

		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe"; // 192.168.52.189

		String user = "project";
		String pass = "project";

		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("����Ŭ ���ῡ �����ϼ̽��ϴ�.");
		} catch (SQLException e) {
			System.out.println("����Ŭ ���� ����!! ");
			e.printStackTrace();

		}
	}

		// ����� id üũ, ������ true��ȯ, ������ false��ȯ (���DB)
	public boolean UserCheckDB(String id) {
		boolean bResult = false;
		System.out.println("user_id : " + id);
		int iUserCnt = 0;
		PreparedStatement ps = null;
		String sql = "select count(*) as R_cnt from member where id = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				iUserCnt = rs.getInt(1);
				System.out.println("����� : " + iUserCnt);
			}
		}catch (Exception e) {
		
		}
		
		if(iUserCnt > 0) {
			bResult = true;
		}
		return bResult;
		
	}
	// ���� �޾Ƽ� ���� DB�� ����
	public void insertfileDB(String id, String fileid, int filesize) {
		dto.setId(id);
		dto.setFileid(fileid);
		dto.setFilesize(filesize);

		String sql = "insert into filedb values(?,?,sysdate,?,0)";
		int res = 0;

		try {

			PreparedStatement ps = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getFileid());
			ps.setInt(3, dto.getFilesize()); // ����ũ��
			res = ps.executeUpdate();
			con.commit();
			
			if (res > 0) {
				System.out.println("�ڷ��� ����!!");
			} else {
				System.out.println("�ڷ��� ����!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	// ������ ����� �뷮 + ���� �뷮�� ���ϴ� �޼ҵ�
		public int updatesize(String id, int filesize) {
			this.filesize = filesize;
			this.membersize = filesize + dto.getMembersize();
			int sum = membersize;
			return sum;
		}
	//��� DB�� ����� ������ ������Ʈ
	public int updateUserSize(String id) {
		PreparedStatement ps = null;
		String sql = "update member set MEMBERSIZE = ? where id = ?";
		int res = 0;

		int iTSize = dto.getMembersize(); // �� �뷮 = ���س��� �� �뷮
		try {
		ps = con.prepareStatement(sql);
		ps.setInt(1,iTSize);
		ps.setString(2,id);
		res = ps.executeUpdate();
		con.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	// ���� ����ڿ뷮 + ���Ͽ뷮�� 1GB�� ���� ������ false��ȯ,������ true��ȯ (��� DB)
	public boolean checksize(String id, int filesize) {

		boolean checkVal = false;
		int membersize = 0;
		int iTotalSize = 0;
		String sql = "select membersize from member where id = ?";
		try {
			PreparedStatement ps = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				membersize = rs.getInt(1);
				System.out.println("���� ��������� �� : "+ membersize);
			}
		}
		catch (Exception e) {
			
		}

		if (membersize < 1024*1024*1024) { // 1Gbyte 1*1024*1024*1024 byte
			iTotalSize = membersize + filesize; // ����� �뷮+ ���Ͽ뷮 = ����
			if (iTotalSize < 1024*1024*1024) {
				checkVal = true; // ������ ���� ����
			} else {
				checkVal = false; // �Ұ���
			}
		}
		
		dto.setMembersize(iTotalSize);
		System.out.println("������ ���忩�� : "+ checkVal + "�ѻ��뷮 :"+ iTotalSize);
		return checkVal; // 
	}

	// �ٿ�ε� Ƚ�� ī��� (���� DB)
	public int downcount(String id, String fileid) {
		int iCnt = 0;
		String sql = "select num from filedb where id =? and fileid= ?" ;
		try {
			PreparedStatement ps = null;
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, fileid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int iNum = rs.getInt(1);
				iCnt = iNum;
				System.out.println("num : " + iNum);
			}
		}catch (Exception e) {
		}
		
		dto.setNum(iCnt);
		return iCnt;
	}

	

	// ����� id�� ���ϸ��� ���ؼ� ����� id�� ���ϸ��� ������ ������ �����ϸ� false��ȯ, ������ true��ȯ (����DB)
	public boolean existdata(String id, String fileid) {
		boolean checkVal = false;
		PreparedStatement ps = null;
		String sql = "SELECT COUNT(*) AS R_CNT "
				+ "FROM FILEDB "
				+ "WHERE   ID = ? "
				+ "AND FILEID = ?";
		
		try {
			
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, fileid);
			rs = ps.executeQuery();
			while (rs.next()) {// hasNext() +next()
				int iRowCnt = rs.getInt(1);
				
				if(iRowCnt > 0) {
					checkVal = false;
					System.out.println("������ ������ ����");
				} else {
					checkVal = true;
					System.out.println("������ ������ ������");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkVal;
	}

	// ���� �ٿ�ε� ī��Ʈ üũ
	public int updateDownCount(String id, String fileid) {
		int iResult = 0;
		int iDwCnt = dto.getNum();
		System.out.println("iDwCnt : " + iDwCnt); 
		iDwCnt++;
		PreparedStatement ps = null;
		try{
			
		String sql = "update filedb set num = ? where id = ? and fileid =?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, iDwCnt);
		ps.setString(2, id);
		ps.setString(3, fileid);
		iResult = ps.executeUpdate(); // Update �Ǽ�
		con.commit();
		if(iResult > 0 ){
			System.out.println("�ٿ�ε� ī��� ������Ʈ ����");
		} else{
			System.out.println("������Ʈ ����");
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return iResult;
	}
	
	public static void main(String[] args) throws IOException {

		ProjectDAO pdo = new ProjectDAO();
		String id;
		int filesize;
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("1.insertfileDB  2.downcount ");
			System.out.println("���ϴ� ����� �����ϼ���: ");
			int select = System.in.read() - 48;
			System.in.skip(5);
			switch (select) {
			case 1:
				System.out.print("ID �Է� :");
				id = in.next();
				
				if(!pdo.UserCheckDB(id)) {
					System.out.print("�ش� ����ڰ� �����ϴ�.");
					break;
				}
				System.out.print("���Ͽ뷮 �Է� :");
				filesize = in.nextInt();

				pdo.checksize(id, filesize);
				
				System.out.print("���ϸ� �Է� :");
				String fileid = in.next();

				pdo.insertfileDB(id, fileid, filesize);
				
				pdo.updatesize(id, filesize);
				if(pdo.updateUserSize(id) < 1) {
					System.out.println("����� �뷮 update FAIL");
				}else {
					System.out.println("����� �뷮 update Success");
				}
				break;

			case 2:
				System.out.print("ID �Է� :");
				id = in.next();
				System.out.print("���ϸ� �Է� :");
				fileid = in.next();
				pdo.existdata(id, fileid);

				pdo.downcount(id, fileid);
				
				pdo.updateDownCount(id, fileid);
				break;

		
				default:
				System.out.println("�߸��Է��ϼ̽��ϴ�.");

			}
		}
	}
}
