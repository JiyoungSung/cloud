package client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Make_id extends JFrame implements ActionListener {
	private JPanel[] jp = new JPanel[6];
	private JLabel[] jlb = new JLabel[4];
	private JTextField[] jtf = new JTextField[4];
	private String[] str = new String[] { "아이디", "이메일", "비밀번호        ", "비밀번호확인" };
	private JButton jbt_1 = new JButton("확인");
	private JButton jbt_2 = new JButton("취소");
	private Container con_jdlg;
	private JPanel jpp = new JPanel();
	private JToggleButton jtbt1 = new JToggleButton("무료");
	private JToggleButton jtbt2 = new JToggleButton("유료");
	private ButtonGroup bg = new ButtonGroup();
	private String rank;
	private JButton jbt_3 = new JButton("확인");
	private JButton jbt_4 = new JButton("확인");
	private Client client;
	make_idThread mit;

	public Make_id(Client cl) {
		this.init();
		this.client = cl;
		this.start();
		client.setIdcheck(false);
		client.setEmailcheck(false);
		mit = new make_idThread();
		mit.start();
		this.setSize(300, 300);
		Dimension screen_jdlg = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) screen_jdlg.getWidth() / 2 - (int) this.getWidth() / 2;
		int ypos = (int) screen_jdlg.getHeight() / 2 - (int) this.getHeight() / 2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void init() {
		con_jdlg = this.getContentPane();
		con_jdlg.setLayout(new GridLayout(6, 1, 10, 10));
		for (int i = 0; i < jp.length; i++) {
			if (i == 4) {
				jp[i] = new JPanel();
				jp[i].setLayout(new FlowLayout(FlowLayout.CENTER));
				jp[i].add(jtbt1);
				bg.add(jtbt1);
				jp[i].add(jtbt2);
				bg.add(jtbt2);
				con_jdlg.add(jp[i]);
			} else if (i == jp.length - 1) {
				jp[i] = new JPanel();
				con_jdlg.add(jp[i]);
				jp[i].setLayout(new FlowLayout());
				jp[i].add(jbt_1);
				jp[i].add(jbt_2);
				break;
			} else if (i < 4) {
				jp[i] = new JPanel();
				con_jdlg.add(jp[i]);
				jp[i].setLayout(new FlowLayout(FlowLayout.LEFT));
				jlb[i] = new JLabel(str[i], JLabel.RIGHT);
				jlb[i].setSize(5, 10);
				jtf[i] = new JTextField(15);
				jp[i].add(jlb[i]);
				jp[i].add(jtf[i]);
			}
		}
		jp[0].add(jbt_3);
		jp[1].add(jbt_4);

	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jbt_1.addActionListener(this);
		jbt_2.addActionListener(this);
		jbt_3.addActionListener(this);
		jbt_4.addActionListener(this);
		jtbt1.addActionListener(this);
		jtbt2.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jtbt1) {
			rank = "free";
		} else if (e.getSource() == jtbt2) {
			rank = "vip";
		} else if (e.getSource() == jbt_3) {
			client.client_Sign("idcheck%" + jtf[0].getText());
		} else if (e.getSource() == jbt_4) {
			client.client_Sign("emailcheck%" + jtf[1].getText());
			System.out.println(client.isIdcheck() + "," + client.isEmailcheck());
		} else if (e.getSource() == jbt_1) {
			mit.setTf(false);
			if (!jtf[2].getText().equals(jtf[3].getText())) {
				JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.", "비밀번호 확인 오류", JOptionPane.DEFAULT_OPTION);
			} else {
				client.client_Sign(
						"makeid%" + jtf[0].getText() + "%" + jtf[2].getText() + "%" + jtf[1].getText() + "%" + rank);
				this.setVisible(false);
			}

		} else if (e.getSource() == jbt_2) {
			this.setVisible(false);
			mit.setTf(false);
			client.logvis(true);
		}
	}

	class make_idThread extends Thread {
		boolean tf = true;

		@Override
		public void run() {
			while (tf) {
				try {
					System.out.println(client.isEmailcheck() +","+ client.isIdcheck());
					if (client.isEmailcheck() && client.isIdcheck()) {
						jbt_1.setEnabled(true);
					} else {
						jbt_1.setEnabled(false);
					}
					sleep(500);
				} catch (InterruptedException e) {
				}
			}
		}

		public void setTf(boolean tf) {
			this.tf = tf;
		}
	}
}
