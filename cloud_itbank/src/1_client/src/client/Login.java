package client;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Login extends JFrame implements MouseListener {
	private Client client;
	private JLabel title_jlb;
	private JPanel title_p;
	private JTextField id_jtf;
	private JPasswordField pass_jtf;
	private JButton jbt;
	private Checkbox constant_cb;
	private JLabel constant_jlb;
	private JPanel constant_p;
	private JPanel center_p;
	private JLabel find_id_jlb;
	private JLabel make_id_jlb;
	private JPanel bottom_p;
	private String id = "";
	private String pass = "";

	private MemberDTO mdto;

	public Client getClient() {
		return client;
	}

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	private File f = new File("cloud_id");

	public void saveId() {
		if (!f.canRead())
			return;
		String cloudId = "";
		try {
			FileInputStream fis = new FileInputStream(f);
			while (true) {
				int res = fis.read();
				if (res < 0)
					break;
				cloudId += (char) res;
			}
			if (cloudId == null)
				return;
			id_jtf.setText(cloudId);
			pass_jtf.setText("");
			constant_cb.setState(true);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		// 설정
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout(0, 20));
		title_jlb = new JLabel("JCloud", JLabel.CENTER);
		title_jlb.setFont(new Font("", Font.BOLD, 25));
		title_jlb.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		con.add("North", title_jlb);
		id_jtf = new JTextField("Cloud ID", 18);
		pass_jtf = new JPasswordField("비밀번호", 14);
		id_jtf.setFont(new Font("", Font.BOLD, 20));
		pass_jtf.setFont(new Font("", Font.BOLD, 20));
		constant_cb = new Checkbox();
		constant_jlb = new JLabel("아이디 저장");
		center_p = new JPanel();
		con.add("Center", center_p);
		// center_p.setLayout(new GridLayout(4, 1, 10, 10));
		center_p.setLayout(new FlowLayout());
		center_p.add(id_jtf);
		center_p.add(pass_jtf);
		jbt = new JButton("확인");
		center_p.add(jbt);
		constant_p = new JPanel();
		center_p.add(constant_p);
		constant_p.setLayout(new FlowLayout());
		constant_p.add(constant_cb);
		constant_p.add(constant_jlb);
		bottom_p = new JPanel();
		con.add("South", bottom_p);
		bottom_p.setLayout(new GridLayout(2, 1, 10, 10));
		bottom_p.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		find_id_jlb = new JLabel("ID 또는 암호를 잊으셨습니까?", JLabel.CENTER);
		make_id_jlb = new JLabel("ID가 없으십니까? 지금 만드세요.", JLabel.CENTER);
		bottom_p.add(find_id_jlb);
		bottom_p.add(make_id_jlb);
		this.saveId();

	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jbt.addMouseListener(this);
		find_id_jlb.addMouseListener(this);
		make_id_jlb.addMouseListener(this);
		id_jtf.addMouseListener(this);
		pass_jtf.addMouseListener(this);

	}

	public Login(Client cl) {
		super("로그인");
		this.client = cl;
		this.init(); // 모양설정 또는 초기값설정
		this.start(); // event설정
		this.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) screen.getWidth() / 2 - (int) this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - (int) this.getHeight() / 2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if (me.getSource() == find_id_jlb) {
			String email = JOptionPane.showInputDialog(null, "당신의 이메일 주소를 입력하세요", "아이디 찾기", JOptionPane.DEFAULT_OPTION);
			client.client_Sign("findid%"+email);
		}
		if (me.getSource() == make_id_jlb) {
			new Make_id(client);
		}
		if (me.getSource() == jbt) {

			String id = id_jtf.getText();
			char[] pass = pass_jtf.getPassword();
			this.id = id;
			for (int i = 0; i < pass.length; i++) {
				this.pass += pass[i];
			}
			client.client_Sign("login%" + id + "%" + this.pass);
			this.pass="";
		}
		if (me.getSource() == id_jtf) {
			id_jtf.setText("");
			pass_jtf.setText("");
			
			
			
		}
		if (constant_cb.getState()) {
			try {
				FileOutputStream fos = new FileOutputStream(f, false);
				byte by[] = id.getBytes();
				fos.write(by);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!constant_cb.getState()) {
			f.delete();
		}
	}
}
