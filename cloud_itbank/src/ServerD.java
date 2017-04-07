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

			String fileName = br.readLine(); // Ŭ���̾�Ʈ ���� ���ϸ��� �����ذ��� ����
			String[] str=fileName.split("%");
			File f = new File("D:" + File.separator + "no" + File.separator + "server" + File.separator+str[0]);
			File f1 = new File(f, str[1]); // ���ϰ��, ���ϸ�

//			if (f1.canRead()) { // ������ ������ �ִ�.
//
				InetAddress ia = InetAddress.getByName(str[2]);
				so = new Socket(ia, 20000); // Ŭ��� ������ ��Ʈ��ȣ ����
				DataInputStream dis = new DataInputStream(new FileInputStream(f1));
				DataOutputStream dos = new DataOutputStream(so.getOutputStream());
				System.out.println("aa");
				int b = 0;
				while ((b = dis.read()) != -1) { // Ŭ���̾�Ʈ�� ������ ����
					dos.write(b);
					dos.flush();
				}
				dis.close();
				dos.close();
				System.exit(0);
//			} else {
//				System.out.println("���� ������ ����!");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new ServerD();

	}

}
