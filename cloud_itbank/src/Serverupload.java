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
			ia = so.getInetAddress(); // so�� ������ IP�� ������( �ʿ��ϱ���... )
			String address = ia.getHostAddress(); // String������ ��ȯ
			InputStream is = so.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String fileName = br.readLine(); // Ŭ���̾�Ʈ���� ������ ���ϸ��� ����
			System.out.println("���ϸ�:"+fileName);// Ŭ���̾�Ʈ ���� ���ϸ��� �����ذ��� ����
			
			String[] str=fileName.split("%");
			System.out.println(fileName);
			File f = new File("E:"+File.separator+"jsh_java_workspace"+File.separator+"201704_javaproject"+File.separator+
					"src"+File.separator+"project"+File.separator+"serverfile"+File.separator+str[0]);
			File f1 = new File(f, str[1]); // ���ϰ��, �ĸ�
			
//			if (ch == 1) { // ������ ������ �ִ�. ( ����üũ )
			
				InetAddress ia = InetAddress.getByName(address);
				so = new Socket(ia, 20000); // Ŭ��� ������ ��Ʈ��ȣ ����
				DataInputStream dis = new DataInputStream(new FileInputStream(f1));
				DataOutputStream dos = new DataOutputStream(so.getOutputStream());
				
				int b = 0;
				while ((b = dis.read()) != -1) { // Ŭ���̾�Ʈ�� ������ ����
				dos.write(b); dos.flush();
				}
				dis.close(); dos.close(); ss.close();
				
//			} else if(ch == 2) {
//				System.out.println("���� ������ ����!");
//			}
		} 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
