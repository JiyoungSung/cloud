

import java.io.File;
import java.util.*;

public class MemberPro2 {
	Scanner in;
	MemberDAO dao;
	public MemberPro2(){
		in = new Scanner(System.in);
		dao = new MemberDAO();
	}
	public void insert(){
		MemberDTO insert = new MemberDTO();
		System.out.print("이름을 입력 : ");
		insert.setName(in.next());
		System.out.print("전화번호를 입력 : ");
		insert.setHp(in.next());
		System.out.print("이메일을 입력 : ");
		insert.setEmail(in.next());
		dao.insertMember(insert);
	}
	public void view(){
		ArrayList<MemberDTO> list = dao.listMember();
		for(MemberDTO dto : list){
			System.out.print(dto.getNo() + "\t");
			System.out.print(dto.getName() + "\t");
			System.out.print(dto.getHp() + "\t");
			System.out.println(dto.getEmail());
		}
	}
	public void delete(){
		System.out.print("삭제할 회원의 이름 : ");
		String name = in.next();
		int res = dao.deleteMember(name);
		if (res>0){
			System.out.println(name+"님을 삭제하였습니다.");
		} else {
			System.out.println(name+"님은 저희 회원이 아닙니다.");
		}
	}
	public void exit(){
		System.out.println("프로그램을 종료합니다.");
		System.exit(0);
	}
}
