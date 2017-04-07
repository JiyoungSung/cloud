package project_client;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class ClientD extends Frame {

	private String file = "";
	private InetAddress ia = null;
	private Socket so = null;
	private FileDialog fd = null;
	private String address = "";
	MemberDTO mem = new MemberDTO();
	public ClientD(MemberDTO mem){
		
		file = JOptionPane.showInputDialog("원하는 파일"); // 파일명을 지정해줌
		String id = mem.getId();
		try {
			
			System.out.println("클라 실행중?");
			ia = InetAddress.getByName("localhost");
			so = new Socket(ia,20001);//파일명을 보내는 포트번호
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(id+"%"+file+"\n"); bw.flush(); //서버로 파일명을 보내준다.
			
			
			ServerSocket ss = new ServerSocket(20000); // 클라가받는쪽.
			Socket s = ss.accept();
			
			InputStream is = s.getInputStream();
			fd=new FileDialog(this,"",FileDialog.SAVE);
			fd.setVisible(true);
			address = fd.getDirectory() + fd.getFile(); // FileDialog에서 파일경로를 빼온다.
			System.out.println(fd.getDirectory()+fd.getFile());
			File f = new File(address);// 경로를 지정해주고
			FileOutputStream out = new FileOutputStream(f);
		
			
			int i = 0;
			
			
			while ((i = is.read()) != -1) { // 서버에서 보내주는 파일을 받는다.
				
				out.write((char) i);	
			}
			
			JOptionPane.showMessageDialog(null, "파일 다운로드 완료");
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
