package project_1;
import java.io.*;
import java.net.*;

import project_1.FileCheck;

public class Serverupload{
	
	private InetAddress ia = null;
	
	public Serverupload() {
		try {
			while(true){
			ServerSocket ss = new ServerSocket(20001);
			Socket so = ss.accept();
			ia = so.getInetAddress(); // so에 접속자 IP를 가져옴( 필요하구먼... )
			String address = ia.getHostAddress(); // String값으로 변환
			InputStream is = so.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String fileName = br.readLine(); // 클라이언트에서 보내준 파일명을 받음
			System.out.println("파일명:"+fileName);// 클라이언트 에서 파일명을 보내준것을 받음
			
			String[] str=fileName.split("%");
			System.out.println(fileName);
			File f = new File("E:"+File.separator+"jsh_java_workspace"+File.separator+"201704_javaproject"+File.separator+
					"src"+File.separator+"project"+File.separator+"serverfile"+File.separator+str[0]);
			File f1 = new File(f, str[1]); // 파일경로, 파명
			
//			if (ch == 1) { // 파일을 읽을수 있다. ( 파일체크 )
			
				InetAddress ia = InetAddress.getByName(address);
				so = new Socket(ia, 20000); // 클라로 보내줄 포트번호 지정
				DataInputStream dis = new DataInputStream(new FileInputStream(f1));
				DataOutputStream dos = new DataOutputStream(so.getOutputStream());
				
				int b = 0;
				while ((b = dis.read()) != -1) { // 클라이언트로 파일을 전송
				dos.write(b); dos.flush();
				}
				dis.close(); dos.close(); ss.close();
				
//			} else if(ch == 2) {
//				System.out.println("없는 파일을 선택!");
//			}
		} 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
