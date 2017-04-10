package project_1;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



public class sign_up_Gui extends JFrame implements ActionListener {
	private JLabel id_Label = new JLabel("아이디");
	private JLabel Pass_Label = new JLabel("패스워드");
	private JLabel Pass_Label1 = new JLabel("패스워드 확인");
	private JLabel email_Label = new JLabel("email");
	private JTextField id_Field = new JTextField(20);
	private JTextField pass_Field = new JTextField(25);
	private JTextField pass_Field1 = new JTextField(25);
	private JTextField email_Field = new JTextField(25);

	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();
	private JPanel jp6 = new JPanel();

	private JToggleButton jtbt1 = new JToggleButton("무료");
	private JToggleButton jtbt2 = new JToggleButton("유료");
	private ButtonGroup bg = new ButtonGroup();

	private JButton check_Button = new JButton("확인");
	private JButton sign_Button = new JButton("회원가입");
	private JButton cancel_Button = new JButton("취소");
	private Font font = new Font("궁서", Font.BOLD, 30);
	private Font font1 = new Font("궁서", Font.BOLD, 20);

	private String rank;
	LogPro log=new LogPro();

	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(10, 1));
		/*
		 * jp1.setLayout(new FlowLayout()); jp2.setLayout(new FlowLayout());
		 * jp3.setLayout(new FlowLayout()); jp4.setLayout(new FlowLayout());
		 * jp5.setLayout(new FlowLayout()); jp6.setLayout(new FlowLayout());
		 */
		jp1.add(id_Field);
		jp1.add(check_Button);
		jp2.add(pass_Field);
		jp3.add(pass_Field1);
		jp4.add(email_Field);
		jp5.add(jtbt1);
		bg.add(jtbt1);
		jp5.add(jtbt2);
		bg.add(jtbt2);
		jp6.add(sign_Button);
		jp6.add(cancel_Button);
		sign_Button.setEnabled(false);

		id_Label.setFont(font1);
		Pass_Label.setFont(font1);
		Pass_Label1.setFont(font1);
		email_Label.setFont(font1);

		con.add(id_Label);
		con.add(jp1);
		con.add(Pass_Label);
		con.add(jp2);
		con.add(Pass_Label1);
		con.add(jp3);
		con.add(email_Label);
		con.add(jp4);
		con.add(jp5);
		con.add(jp6);

	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		check_Button.addActionListener(this);
		sign_Button.addActionListener(this);
		cancel_Button.addActionListener(this);
		jtbt1.addActionListener(this);
		jtbt2.addActionListener(this);
	}

	public sign_up_Gui() {
		super("회원가입");
		log.start();
		this.init();
		this.start();
		

		super.setSize(300, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
		super.setLocation(xpos, ypos);

		super.setVisible(true);
		super.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == check_Button) {
			if(log.ID_overlap(id_Field.getText())){
				JOptionPane.showMessageDialog(null, "사용중인 ID입니다.");
			}else{			
				sign_Button.setEnabled(true);
				JOptionPane.showMessageDialog(null, "사용가능한 ID입니다.");
			}
		}
		if (e.getSource() == sign_Button) {
			log.member_add(id_Field.getText(), pass_Field.getText(), email_Field.getText(), rank);
			this.setVisible(false);
			new ClientMain();
		}
		if (e.getSource() == cancel_Button) {
			this.setVisible(false);
			new ClientMain();
		}
		if (e.getSource() == jtbt1) {
			rank="free";
		}
		if (e.getSource() == jtbt2) {
			rank="vip";
		}
		

	}

	public static void main(String[] args) {
		new sign_up_Gui();
	}

}
