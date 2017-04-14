package client;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

class ThumbnailFrame extends JFrame{

	private JPanel jp = new JPanel();
	private ImageIcon ii;
	ImageRes ic;
	private JLabel jlb[];
	private int num = 1;

	public void init() {
		// 설정
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("Center", jp);
		for (int i = 0; i < ic.count; i++)
			if (i % 4 == 0) {
				num++;
			}
		jp.setLayout(new GridLayout(num, 3));
		jlb = new JLabel[ic.count];
		for (int i = 0; i < ic.count; i++) {
			File file[] = new File("C:\\Users\\Administrator\\Desktop\\photo").listFiles();//서버에서 클라로 보내는경로 클라에저장되는경로.
			String imgpath = String.valueOf(file[i]);// 파일 경로의 값들을 String 값으로 저장
			ii = new ImageIcon(imgpath);
			System.out.println(imgpath);
			jlb[i] = new JLabel(ii);
			jp.add(jlb[i]);
		}

		// ii = new ImageIcon("E:\\jsh_java_workspace\\thumbcli\\test2.jpg");
		// jlb = new JLabel(ii);
		// con.add(jp);
		// jp.setLayout(new FlowLayout());
		// jp.add(jlb);

	}

	public ThumbnailFrame() {
		ic = new ImageRes();
		this.init(); // 모양설정 또는 초기값설정
		this.setSize(500, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) screen.getWidth() / 2 - (int) this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - (int) this.getHeight() / 2;
		this.setLocation(xpos, ypos);
		this.setResizable(true);
		this.setVisible(true);

	}

		

	}

