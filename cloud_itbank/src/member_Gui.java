package project_client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class member_Gui extends JFrame implements ActionListener {
	private JButton up_Button = new JButton("올리기");
	private JButton down_Button = new JButton("내려받기");
	private JButton delete_Button = new JButton("지우기");

	private JPanel jp1 = new JPanel();

	private JRadioButton jrb;
	private MemberDTO mem;

	private String id;
	//private int 
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp1.add(up_Button);
		jp1.add(down_Button);
		jp1.add(delete_Button);
		con.add("North", jp1);
	}

	public void start() {
		up_Button.addActionListener(this);
		down_Button.addActionListener(this);
		delete_Button.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public member_Gui(MemberDTO mem) {
		super("회원");
		this.init();
		this.start();
		this.mem = mem;
		mem.getId();

		super.setSize(700, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
		super.setLocation(xpos, ypos);

		super.setVisible(true);
		super.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == up_Button) {
			new FileUp(mem);
		}
		
		if(e.getSource() == down_Button){
			new ClientD(mem);
		}

	}
}
