package project_1;

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
	private int sel = 0;
	public ClientD(MemberDTO mem){
		
		file = JOptionPane.showInputDialog("���ϴ� ����"); // ���ϸ��� ��������
		String id = mem.getId();
		FileCheck fc = new FileCheck(id, file);
		sel = fc.File(); // 1 =  �������� / 2. ���� ���ϸ� / 3. ���� ���̵�
		try {
			switch(sel){
			case 1 :
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
			out.close(); ss.close(); break;
			case 2: JOptionPane.showMessageDialog(null, "�������� �ʴ� ���ϸ��Դϴ�."); break;
			case 3: JOptionPane.showMessageDialog(null, "�������� �ʴ� ���̵��Դϴ�."); break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}