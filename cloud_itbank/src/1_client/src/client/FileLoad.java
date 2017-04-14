package client;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class FileLoad extends Frame {

	private String file = "";
	private InetAddress ia = null;
	private Socket so = null;
	private FileDialog fd;
	private String address = "";
	private MemberDTO mem ;
	private int sel = 0;
	private String id="";

	public FileLoad(String id,String str) {
		System.out.println("파일로드 실행");
		file = str; // 파일명을 지정해줌
		this.id=id;
		try {
			ia = InetAddress.getByName("localhost");
			so = new Socket(ia,50000);//파일명을 보내는 포트번호
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(id + "%" + file + "%" + "U" + "\n");
			bw.flush(); // 서버로 파일명을 보내준다.
			ServerSocket ss = new ServerSocket(20000); // 파일을 클라가받는쪽.
			Socket s = ss.accept();
			ss.close();
			fd = new FileDialog(this, "", FileDialog.SAVE);
			fd.setVisible(true);
			address = fd.getDirectory() + fd.getFile(); // FileDialog에서 파일경로를
														// 빼온다.
			File f = new File(address);// 경로를 지정해주고
			ClientDTh cdt = new ClientDTh(s, f);
			cdt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ClientDTh extends Thread {
	Socket s;
	File add = null;

	ClientDTh(Socket s, File add) {
		this.s = s;
		this.add = add;
	}

	public void run() {
		try {

			InputStream is = s.getInputStream();
			FileOutputStream out = new FileOutputStream(add);
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