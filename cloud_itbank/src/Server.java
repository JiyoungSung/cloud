package project;

import java.io.*;
import java.sql.*;

public class Server {

	public static void main(String[] args) throws Exception{
	MemberDAO mdao=new MemberDAO();
	FileDAO fdao=new FileDAO();
	mdao.SaveMember(); // ���table ���� ���Ϸ� ����
	fdao.SaveFile();	//����table ���� ���Ϸ� ����
	}
}
