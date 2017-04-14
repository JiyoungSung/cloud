package client;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Myinfo extends JFrame implements ActionListener{
	private JLabel[] lb = new JLabel[4];
	private JButton bt = new JButton("Ȯ��");
	private JLabel title=new JLabel("ȸ������",JLabel.CENTER);
	private Panel p = new Panel();
	private MemberDTO mto;
	private Font font=new Font("���", Font.BOLD, 40);

	public void init() {
		this.setLayout(new BorderLayout());
		this.add("North",title);
		title.setFont(font);
		for (int i = 0; i < lb.length; i++) {
			lb[i] = new JLabel("", JLabel.CENTER);
			p.add(lb[i]);
		}
		lb[0].setText("���̵� : " + mto.getId());
		lb[1].setText("�̸��� : " + mto.getEmail());
		lb[2].setText("������ : " + mto.getDay());
		if (mto.getRank().equals("vip")) {
			lb[3].setText("����ȸ��");
		} else {
			lb[3].setText("����ȸ��");
		}
		this.add("Center", p);
		this.add("South", bt);
		p.setLayout(new GridLayout(4, 1));
	}

	public Myinfo(MemberDTO mto) {
		this.mto = mto;
		this.setTitle(mto.getId() + " ��");
		this.setSize(250, 300);
		this.init();
		bt.addActionListener(this);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
		super.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt){
			this.setVisible(false);
		}
		
	}
}
