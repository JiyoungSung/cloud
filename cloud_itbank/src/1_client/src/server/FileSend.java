package server;

import java.io.*;
import java.net.*;
// Ŭ���̾�Ʈ���� ������ ������ ���ε� ���ش�.
public class FileSend{
	
	private Socket so;
	private ServerSocket ss;
	private InetAddress ia;
	public FileSend() {	
		System.out.println("���ϻ������");
		/*try{
			ss = new ServerSocket(50000);
			so = ss.accept();
			ia = so.getInetAddress(); // so�� ������ IP�� ������( �ʿ��ϱ���... )
			String address = ia.getHostAddress(); // String������ ��ȯ
			InputStream is = so.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String Name = br.readLine(); // Ŭ���̾�Ʈ���� ������ ���̵� ���� 
			ss.close(); so.close();
			System.out.println("������:"+Name);// Ŭ���̾�Ʈ ���� ���ϸ��� �����ذ��� ����
			File f = new File("D:\\project\\server\\"+Name);			
			String filelist[] = f.list();
			String filesu = String.valueOf(filelist.length);			
			ia = InetAddress.getByName(address);
			so = new Socket(ia,10003);
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(filesu); bw.flush(); // ���̵� ������ �����ش�.
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
		ia = so.getInetAddress(); // so�� ������ IP�� ������( �ʿ��ϱ���... )
		String address = ia.getHostAddress(); // String������ ��ȯ
		InputStream is = so.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String fileName = br.readLine(); // Ŭ���̾�Ʈ���� ������ ���ϸ��� ����
		System.out.println("���ϸ�:"+fileName);// Ŭ���̾�Ʈ ���� ���ϸ��� �����ذ��� ����
		
		String[] str=fileName.split("%");
		System.out.println(fileName);
		File f = new File("D:\\project\\"+str[0]);
		File f1 = new File(f, str[1]); // ���ϰ��, �ĸ�
		
		if (str[2].equals("U")) {
			System.out.println("���Ͼ� ����");
			InetAddress ia = InetAddress.getByName(address);
			so = new Socket(ia, 20000); // Ŭ��� ������ ��Ʈ��ȣ ����
			DataInputStream dis = new DataInputStream(new FileInputStream(f1));
			DataOutputStream dos = new DataOutputStream(so.getOutputStream());
			
			int b = 0;
			while ((b = dis.read()) != -1) { // Ŭ���̾�Ʈ�� ������ ����
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
