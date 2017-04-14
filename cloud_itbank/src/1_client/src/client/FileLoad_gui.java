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
	private JLabel titlelb = new JLabel("파일 다운로드", JLabel.CENTER);
	private Panel nameP = new Panel();

	private JLabel nullLabel = new JLabel("파일이 존재하지 않습니다.", JLabel.CENTER);
	private Panel p = new Panel();
	private TextField filename_tf = new TextField();
	private JButton fileBt = new JButton("다운로드");
	private InetAddress ia = null;
	private Socket so = null;
	private ServerSocket ss;
	private JButton selButton[];
	private String str;
	private String id;

	public void init() {
		try {
			/*
			 * ia = InetAddress.getByName("127.0.0.1"); // 서버아이피 so = new
			 * Socket(ia,10003); // 아이디를 보내는 포트번호 BufferedWriter bw=new
			 * BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			 * bw.write(id); bw.flush(); // 아이디를 서버로 보내준다. so.close();
			 * 
			 * ss = new ServerSocket(10003); so = ss.accept(); ia =
			 * so.getInetAddress(); // so에 접속자 IP를 가져옴( 필요하구먼... ) String
			 * address = ia.getHostAddress(); // String값으로 변환 InputStream is =
			 * so.getInputStream(); BufferedReader br = new BufferedReader(new
			 * InputStreamReader(is)); String filesu = br.readLine(); // 클라이언트에서
			 * 보내준 아이디를 받음 ss.close(); so.close(); num =
			 * Integer.parseInt(filesu); // 서버에서 파일의 갯수를 받아옴
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
		super("파일 LOAD");
		this.str = str;
		this.id=id;
		this.init(); // 초기값설정
		this.start(); // event설정
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
