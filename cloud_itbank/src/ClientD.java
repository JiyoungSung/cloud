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
		
		file = JOptionPane.showInputDialog("���ϴ� ����"); // ���ϸ��� ��������
		String id = mem.getId();
		try {
			
			System.out.println("Ŭ�� ������?");
			ia = InetAddress.getByName("localhost");
			so = new Socket(ia,20001);//���ϸ��� ������ ��Ʈ��ȣ
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(id+"%"+file+"\n"); bw.flush(); //������ ���ϸ��� �����ش�.
			
			
			ServerSocket ss = new ServerSocket(20000); // Ŭ�󰡹޴���.
			Socket s = ss.accept();
			
			InputStream is = s.getInputStream();
			fd=new FileDialog(this,"",FileDialog.SAVE);
			fd.setVisible(true);
			address = fd.getDirectory() + fd.getFile(); // FileDialog���� ���ϰ�θ� ���´�.
			System.out.println(fd.getDirectory()+fd.getFile());
			File f = new File(address);// ��θ� �������ְ�
			FileOutputStream out = new FileOutputStream(f);
		
			
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
