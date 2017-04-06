package project;

import java.io.*;
import java.sql.*;

public class Server {

	public static void main(String[] args) throws IOException {
	MemberDAO mdao=new MemberDAO();
    MemberDTO mdto=new MemberDTO();
	mdao.SaveMember(mdto);
		
	}
}
