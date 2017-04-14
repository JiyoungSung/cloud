package client;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class listview extends JFrame {
	JTextArea jta = new JTextArea();
	Client client;
	String[] msg;

	public void init() {
		Container con = this.getContentPane();
		con.add(jta);

	}

	public void listset(String str) {
		msg = str.split("%");
		if (msg.length == 0) {
			jta.append("저장된 자료가 없습니다.");
		} else {
			for (int i = 0; i < msg.length; i++) {
				jta.append(msg[i] + "\n");
			}
		}
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public listview(String str) {
		this.listset(str);
		this.init();
		this.start();
		this.setSize(300, 300);
		Dimension screen_jdlg = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) screen_jdlg.getWidth() / 2 - (int) this.getWidth() / 2;
		int ypos = (int) screen_jdlg.getHeight() / 2 - (int) this.getHeight() / 2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
}
