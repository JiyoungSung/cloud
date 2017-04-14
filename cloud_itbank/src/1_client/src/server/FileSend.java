package server;

import java.io.*;
import java.net.*;
// 클라이언트에서 지정한 파일을 업로드 해준다.
public class FileSend{
	
	private Socket so;
	private ServerSocket ss;
	private InetAddress ia;
	public FileSend() {	
		System.out.println("파일샌드실행");
		/*try{
			ss = new ServerSocket(50000);
			so = ss.accept();
			ia = so.getInetAddress(); // so에 접속자 IP를 가져옴( 필요하구먼... )
			String address = ia.getHostAddress(); // String값으로 변환
			InputStream is = so.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String Name = br.readLine(); // 클라이언트에서 보내준 아이디를 받음 
			ss.close(); so.close();
			System.out.println("접속자:"+Name);// 클라이언트 에서 파일명을 보내준것을 받음
			File f = new File("D:\\project\\server\\"+Name);			
			String filelist[] = f.list();
			String filesu = String.valueOf(filelist.length);			
			ia = InetAddress.getByName(address);
			so = new Socket(ia,10003);
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(filesu); bw.flush(); // 아이디를 서버로 보내준다.
			so.close();
			
		}catch (IOException e){ e.printStackTrace(); }*/		
		try {
			while(true){
				
			ss = new ServerSocket(50000);
			so = ss.accept();
			ServerUTh sut = new ServerUTh(so);
			sut.start(); ss.close();
}
		}catch (IOException e) { e.printStackTrace(); }
	}
}

class ServerUTh extends Thread{
	private Socket so;
	private InetAddress ia;
	ServerUTh(Socket so){
		this.so = so;
	}
	
	public void run(){
		try{
		ia = so.getInetAddress(); // so에 접속자 IP를 가져옴( 필요하구먼... )
		String address = ia.getHostAddress(); // String값으로 변환
		InputStream is = so.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String fileName = br.readLine(); // 클라이언트에서 보내준 파일명을 받음
		System.out.println("파일명:"+fileName);// 클라이언트 에서 파일명을 보내준것을 받음
		
		String[] str=fileName.split("%");
		System.out.println(fileName);
		File f = new File("D:\\project\\"+str[0]);
		File f1 = new File(f, str[1]); // 파일경로, 파명
		
		if (str[2].equals("U")) {
			System.out.println("파일업 실행");
			InetAddress ia = InetAddress.getByName(address);
			so = new Socket(ia, 20000); // 클라로 보내줄 포트번호 지정
			DataInputStream dis = new DataInputStream(new FileInputStream(f1));
			DataOutputStream dos = new DataOutputStream(so.getOutputStream());
			
			int b = 0;
			while ((b = dis.read()) != -1) { // 클라이언트로 파일을 전송
			dos.write(b); dos.flush();
			}
			dis.close(); dos.close();
			
		} 
		else if(str[2].equals("R")) {
			f1.delete();
		}
	}catch (Exception e) { e.printStackTrace();	}
		
	}
}
