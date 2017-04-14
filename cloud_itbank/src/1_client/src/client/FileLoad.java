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
		System.out.println("���Ϸε� ����");
		file = str; // ���ϸ��� ��������
		this.id=id;
		try {
			ia = InetAddress.getByName("localhost");
			so = new Socket(ia,50000);//���ϸ��� ������ ��Ʈ��ȣ
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(id + "%" + file + "%" + "U" + "\n");
			bw.flush(); // ������ ���ϸ��� �����ش�.
			ServerSocket ss = new ServerSocket(20000); // ������ Ŭ�󰡹޴���.
			Socket s = ss.accept();
			ss.close();
			fd = new FileDialog(this, "", FileDialog.SAVE);
			fd.setVisible(true);
			address = fd.getDirectory() + fd.getFile(); // FileDialog���� ���ϰ�θ�
														// ���´�.
			File f = new File(address);// ��θ� �������ְ�
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

			while ((i = is.read()) != -1) { // �������� �����ִ� ������ �޴´�.
				out.write((char) i);
			}

			JOptionPane.showMessageDialog(null, "���� �ٿ�ε� �Ϸ�");
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}