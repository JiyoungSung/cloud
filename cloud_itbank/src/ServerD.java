package test01;

import java.io.*;
import java.net.*;

public class ServerD {

	public ServerD() {

		IdCheck ttd = new IdCheck();

		try {

			ServerSocket ss = new ServerSocket(12345);

			Socket so = ss.accept();

			InputStream is = so.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String fileName = br.readLine(); // 클라이언트 에서 파일명을 보내준것을 받음
			String[] str=fileName.split("%");
			File f = new File("D:" + File.separator + "no" + File.separator + "server" + File.separator+str[0]);
			File f1 = new File(f, str[1]); // 파일경로, 파일명

//			if (f1.canRead()) { // 파일을 읽을수 있다.
//
				InetAddress ia = InetAddress.getByName(str[2]);
				so = new Socket(ia, 20000); // 클라로 보내줄 포트번호 지정
				DataInputStream dis = new DataInputStream(new FileInputStream(f1));
				DataOutputStream dos = new DataOutputStream(so.getOutputStream());
				System.out.println("aa");
				int b = 0;
				while ((b = dis.read()) != -1) { // 클라이언트로 파일을 전송
					dos.write(b);
					dos.flush();
				}
				dis.close();
				dos.close();
				System.exit(0);
//			} else {
//				System.out.println("없는 파일을 선택!");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new ServerD();

	}

}
