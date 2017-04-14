package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver {
	FileDAO fdao=new FileDAO();
	public FileReceiver(String id) throws IOException {
		ServerSocket ss = new ServerSocket(20002);

		/* Ŭ���̾�Ʈ�� �����Ҷ� ���� ��ٸ� */
		/* �����ϰ� �Ǹ� Ŭ���̾�Ʈ�� �����͸� �ְ� ������ �ִ� ������ ������ ������ */
		Socket s = ss.accept();
		System.out.println("���� " + s + " �� �����");

		/* �������κ��� ��Ʈ�� ���� */
		InputStream is = s.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		/* ������ ������ ��ü ������ */
		String fileName = br.readLine();
		File f = new File("D:" + File.separator +"project"+File.separator+id);
		if(!f.exists()){//��ΰ� ���������ʴ´ٸ� �����
			f.mkdirs();
		}
		File f1=new File(f,fileName);
		/* ����� ���� ������ */
		FileOutputStream out = new FileOutputStream(f1);
		/* ������ ������ ������ �о ���Ϸ� �� */
		int i = 0;
		while ((i = is.read()) != -1) {
			out.write((char) i);
		}

		System.out.println("�������� C:/fileTransfer/temp ��ο� �����!");

		/* �ڿ����� */
		br.close();
		is.close();
		out.close();
		s.close();
		ss.close();
		br = null;
		is = null;
		out = null;
		s = null;
		ss = null;
	}
}