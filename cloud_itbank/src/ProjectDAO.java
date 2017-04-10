

import java.io.*; 
import java.sql.*;
import java.util.*;

public class ProjectDAO {


	private ResultSet rs = null;
	private Connection con = null; // 커넥션 열기
	private String url, user, pass;
	private Calendar cal;
	private int filesize;
	private int membersize;
	private ProjectDTO dto;

	public ProjectDAO() {
		dto = new ProjectDTO();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 설치 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 설치 실패");
			e.printStackTrace();
			
		}

		String url = "jdbc:oracle:thin:@192.168.52.189:1521:xe"; // 192.168.52.189

		String user = "project";
		String pass = "project";

		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("오라클 연결에 성공하셨습니다.");
		} catch (SQLException e) {
			System.out.println("오라클 연결 실행!! ");
			e.printStackTrace();

		}
	}

		// 사용자 id 체크, 있으면 true반환, 없으면 false반환 (멤버DB)
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
				System.out.println("사용자 : " + iUserCnt);
			}
		}catch (Exception e) {
		
		}
		
		if(iUserCnt > 0) {
			bResult = true;
		}
		return bResult;
		
	}
	// 정보 받아서 파일 DB에 저장
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
			ps.setInt(3, dto.getFilesize()); // 파일크기
			res = ps.executeUpdate();
			con.commit();
			
			if (res > 0) {
				System.out.println("자료등록 성공!!");
			} else {
				System.out.println("자료등록 실패!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	// 기존의 사용자 용량 + 파일 용량값 구하는 메소드
		public int updatesize(String id, int filesize) {
			this.filesize = filesize;
			this.membersize = filesize + dto.getMembersize();
			int sum = membersize;
			return sum;
		}
	//멤버 DB의 사용자 사이즈 업데이트
	public int updateUserSize(String id) {
		PreparedStatement ps = null;
		String sql = "update member set MEMBERSIZE = ? where id = ?";
		int res = 0;

		int iTSize = dto.getMembersize(); // 총 용량 = 구해놓은 총 용량
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
	
	// 기존 사용자용량 + 파일용량이 1GB를 넘지 않으면 false반환,넘으면 true반환 (멤버 DB)
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
				System.out.println("현재 멤버사이즈 총 : "+ membersize);
			}
		}
		catch (Exception e) {
			
		}

		if (membersize < 1024*1024*1024) { // 1Gbyte 1*1024*1024*1024 byte
			iTotalSize = membersize + filesize; // 사용자 용량+ 파일용량 = 총합
			if (iTotalSize < 1024*1024*1024) {
				checkVal = true; // 데이터 저장 가능
			} else {
				checkVal = false; // 불가능
			}
		}
		
		dto.setMembersize(iTotalSize);
		System.out.println("데이터 저장여부 : "+ checkVal + "총사용용량 :"+ iTotalSize);
		return checkVal; // 
	}

	// 다운로드 횟수 카운드 (파일 DB)
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

	

	// 사용자 id와 파일명을 비교해서 사용자 id와 파일명이 동일한 파일이 존재하면 false반환, 없으면 true반환 (파일DB)
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
					System.out.println("동일한 파일이 존재");
				} else {
					checkVal = true;
					System.out.println("동일한 파일이 미존재");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkVal;
	}

	// 파일 다운로드 카운트 체크
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
		iResult = ps.executeUpdate(); // Update 건수
		con.commit();
		if(iResult > 0 ){
			System.out.println("다운로드 카운드 업데이트 성공");
		} else{
			System.out.println("업데이트 실패");
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
			System.out.println("원하는 기능을 선택하세요: ");
			int select = System.in.read() - 48;
			System.in.skip(5);
			switch (select) {
			case 1:
				System.out.print("ID 입력 :");
				id = in.next();
				
				if(!pdo.UserCheckDB(id)) {
					System.out.print("해당 사용자가 없습니다.");
					break;
				}
				System.out.print("파일용량 입력 :");
				filesize = in.nextInt();

				pdo.checksize(id, filesize);
				
				System.out.print("파일명 입력 :");
				String fileid = in.next();

				pdo.insertfileDB(id, fileid, filesize);
				
				pdo.updatesize(id, filesize);
				if(pdo.updateUserSize(id) < 1) {
					System.out.println("사용자 용량 update FAIL");
				}else {
					System.out.println("사용자 용량 update Success");
				}
				break;

			case 2:
				System.out.print("ID 입력 :");
				id = in.next();
				System.out.print("파일명 입력 :");
				fileid = in.next();
				pdo.existdata(id, fileid);

				pdo.downcount(id, fileid);
				
				pdo.updateDownCount(id, fileid);
				break;

		
				default:
				System.out.println("잘못입력하셨습니다.");

			}
		}
	}
}
