package client;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class FileLoad_gui extends JFrame implements ActionListener {

	private int num;
	private JLabel titlelb = new JLabel("���� �ٿ�ε�", JLabel.CENTER);
	private Panel nameP = new Panel();

	private JLabel nullLabel = new JLabel("������ �������� �ʽ��ϴ�.", JLabel.CENTER);
	private Panel p = new Panel();
	private TextField filename_tf = new TextField();
	private JButton fileBt = new JButton("�ٿ�ε�");
	private InetAddress ia = null;
	private Socket so = null;
	private ServerSocket ss;
	private JButton selButton[];
	private String str;
	private String id;

	public void init() {
		try {
			/*
			 * ia = InetAddress.getByName("127.0.0.1"); // ���������� so = new
			 * Socket(ia,10003); // ���̵� ������ ��Ʈ��ȣ BufferedWriter bw=new
			 * BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			 * bw.write(id); bw.flush(); // ���̵� ������ �����ش�. so.close();
			 * 
			 * ss = new ServerSocket(10003); so = ss.accept(); ia =
			 * so.getInetAddress(); // so�� ������ IP�� ������( �ʿ��ϱ���... ) String
			 * address = ia.getHostAddress(); // String������ ��ȯ InputStream is =
			 * so.getInputStream(); BufferedReader br = new BufferedReader(new
			 * InputStreamReader(is)); String filesu = br.readLine(); // Ŭ���̾�Ʈ����
			 * ������ ���̵� ���� ss.close(); so.close(); num =
			 * Integer.parseInt(filesu); // �������� ������ ������ �޾ƿ�
			 */
			String msg[] =str.split("%");
			
			selButton = new JButton[msg.length];

			Container con = this.getContentPane();
			con.setLayout(new BorderLayout());
			con.add("North", titlelb);
			con.add("Center", nameP);
			nameP.setLayout(new GridLayout(num, 1));

			for (int i = 0; i < selButton.length; i++) {
				selButton[i] = new JButton(msg[i]);
				nameP.add(selButton[i]);
			}
			con.add("South", p);
			p.setLayout(new BorderLayout());
			p.add("Center", filename_tf);
			p.add("East", fileBt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for (int i = 0; i < selButton.length; i++) {
			selButton[i].addActionListener(this);
		}
		fileBt.addActionListener(this);
		
	}

	public FileLoad_gui(String id,String str) {
		super("���� LOAD");
		this.str = str;
		this.id=id;
		this.init(); // �ʱⰪ����
		this.start(); // event����
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
		super.setLocation(xpos, ypos);

		super.setResizable(false);
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < selButton.length; i++) {
			if (e.getSource() == selButton[i]) {
				filename_tf.setText("");
				filename_tf.setText(selButton[i].getText());
			}
		}

		if (e.getSource() == fileBt) {
			new FileLoad(id,filename_tf.getText());
			this.setVisible(false);
		}
	}
}
