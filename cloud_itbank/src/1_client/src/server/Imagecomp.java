package server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*; 

class Imagecomp extends Thread{
	int count;
	String imgpath;
	File thumbnailFileName;
	Socket soc;
	ServerSocket ss;
	PrintWriter pw;
	String msg;
	public  Imagecomp(String imgpath){
		this.imgpath=imgpath;
		this.start();
	}
	public void run(){
		try { 
			System.out.println("�̰� ����Ǵ�???????????");
			File[] originalFileName = new File(imgpath).listFiles();
			count=originalFileName.length;
			ss = new ServerSocket(1919);
			System.out.println("ī��Ʈ�����¼��� ���");
			soc = ss.accept();
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
			msg=String.valueOf(count);
			pw.println(msg);
			pw.flush();
			for(int i=0;i<originalFileName.length;i++){
				String name=originalFileName[i].getName();
				thumbnailFileName = new File(imgpath+File.separator+i);// �������� ����� ������ ����Ǵ� ���
				
				int width = 100; 
				int height = 100; 
				//	����� �̹��� ���� 
				BufferedImage originalImg = ImageIO.read(originalFileName[i]); 
				BufferedImage thumbnailImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); 
				// ����� �׸��� 
				Graphics2D g = thumbnailImg.createGraphics(); 
				g.drawImage(originalImg, 0, 0, width, height, null); 
				// ���ϻ��� 
				ImageIO.write(thumbnailImg, "jpg", thumbnailFileName);
				Imagesend ts1=new Imagesend(thumbnailFileName);
			}

		} catch (Exception e) { 
			e.printStackTrace(); 
		}	

	}
}

/*
class MyFrame01 extends JFrame{
	private JLabel jlb = new JLabel();
	private JPanel jp = new JPanel();
	private ImageIcon ii;
	private Test test;

	public void init(){
		//����
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		test = new Test();
		ii = new 


	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public MyFrame01(String title){
		super(title);
		this.init(); //��缳�� �Ǵ� �ʱⰪ����
		this.start(); //event����
		this.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)screen.getWidth()/2-(int)this.getWidth()/2;
		int ypos = (int)screen.getHeight()/2-(int)this.getHeight()/2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
}
 */
