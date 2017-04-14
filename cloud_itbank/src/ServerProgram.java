import java.io.*;
//import java.sql.*;

public class ServerProgram {
	public static void main(String[] args) throws IOException {
	MemberDAO mdao=new MemberDAO();
    MemberDTO mdto=new MemberDTO();
	mdao.SaveMember();
		
	}
}