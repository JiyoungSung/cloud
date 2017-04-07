package project_client;

import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class login_Gui extends JFrame implements ActionListener {
	private JLabel id_Label = new JLabel("아이디");
	private JLabel Pass_Label = new JLabel("패스워드");
	private JTextField id_Field = new JTextField(25);
	private JTextField pass_Field = new JTextField(25);
	private JLabel sign_Label = new JLabel("환영합니다", JLabel.CENTER);
	private JPanel main_Panel = new JPanel();
	private JPanel south_Panel = new JPanel();
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();

	private JButton login_Button = new JButton("로그인");
	private JButton sign_Button = new JButton("회원가입");
	private JButton cancel_Button = new JButton("취소");
	private Font font = new Font("궁서", Font.BOLD, 30);
	private Font font1 = new Font("궁서", Font.BOLD, 20);

	LogPro log = new LogPro();
	MemberDTO mem;

	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("Center", main_Panel);
		con.add("North", sign_Label);
		sign_Label.setFont(font);
		id_Label.setFont(font1);
		Pass_Label.setFont(font1);
		main_Panel.setLayout(new GridLayout(4, 1));
		main_Panel.add(id_Label);
		// jp1.setLayout(new FlowLayout());
		jp1.add(id_Field);
		main_Panel.add(jp1);
		main_Panel.add(Pass_Label);
		// jp2.setLayout(new FlowLayout());
		jp2.add(pass_Field);
		main_Panel.add(jp2);
		con.add("South", south_Panel);
		south_Panel.setLayout(new FlowLayout());
		south_Panel.add(login_Button);
		south_Panel.add(sign_Button);
		south_Panel.add(cancel_Button);
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cancel_Button.addActionListener(this);
		login_Button.addActionListener(this);
		sign_Button.addActionListener(this);
	}

	public login_Gui() {
		super("클라우드");
		if (log.start()) {
			this.init();
			this.start();

			super.setSize(300, 300);
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

			int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
			int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
			super.setLocation(xpos, ypos);

			super.setVisible(true);
			super.setResizable(false);
		}else{
			JOptionPane.showMessageDialog(null, "서버 연결 실패");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login_Button) {
			if (log.login(id_Field.getText(), pass_Field.getText())) {
				mem=log.info(id_Field.getText());
				log.log_sign(id_Field.getText());
				this.setVisible(false);
				new member_Gui(mem);
			} else {
				System.out.println("실패");
			}
		}
		if (e.getSource() == cancel_Button) {
			System.exit(0);
		}
		if (e.getSource() == sign_Button) {
			this.setVisible(false);
			new sign_up_Gui();
		}

	}

	public static void main(String[] args) {
		new login_Gui();
	}

}
