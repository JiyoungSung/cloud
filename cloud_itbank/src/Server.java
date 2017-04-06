package project;

import java.io.*;
import java.sql.*;

public class Server {

	public static void main(String[] args) throws Exception{
	MemberDAO mdao=new MemberDAO();
	FileDAO fdao=new FileDAO();
	mdao.SaveMember(); // 멤버table 값을 파일로 저장
	fdao.SaveFile();	//파일table 값을 파일로 저장
	}
}
