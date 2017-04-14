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
	private JButton bt = new JButton("확인");
	private JLabel title=new JLabel("회원정보",JLabel.CENTER);
	private Panel p = new Panel();
	private MemberDTO mto;
	private Font font=new Font("고딕", Font.BOLD, 40);

	public void init() {
		this.setLayout(new BorderLayout());
		this.add("North",title);
		title.setFont(font);
		for (int i = 0; i < lb.length; i++) {
			lb[i] = new JLabel("", JLabel.CENTER);
			p.add(lb[i]);
		}
		lb[0].setText("아이디 : " + mto.getId());
		lb[1].setText("이메일 : " + mto.getEmail());
		lb[2].setText("가입일 : " + mto.getDay());
		if (mto.getRank().equals("vip")) {
			lb[3].setText("유료회원");
		} else {
			lb[3].setText("무료회원");
		}
		this.add("Center", p);
		this.add("South", bt);
		p.setLayout(new GridLayout(4, 1));
	}

	public Myinfo(MemberDTO mto) {
		this.mto = mto;
		this.setTitle(mto.getId() + " 님");
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
