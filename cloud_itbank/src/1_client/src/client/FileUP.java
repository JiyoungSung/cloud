package client;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;
import java.util.Random;
import javax.swing.*;

class FileUP extends JFrame implements ActionListener, PropertyChangeListener, Runnable {

	private JFileChooser jfc = new JFileChooser();
	private JButton b1 = new JButton("파일선택");
	private JButton b2 = new JButton("파일전송");
	private TextField tf = new TextField(25);
	private String file = "";
	private String directory = "";
	private FileDialog fd;
	private JPanel p;

	private JProgressBar progressBar;
	private JButton startButton;
	private JTextArea taskOutput;
	private Task task;
	private int per = 0;
	private int fileSize;
	private InetAddress ia = null;
	private Socket so = null;
	Client client;

	class Task extends SwingWorker<Void, Void> {
		// 메인 Task 백그라운드에서 스레드 실행
		@Override
		public Void doInBackground() {
			int progress = 0;
			setProgress(0);
			while (progress < 100) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ignore) {
				}
				progress = per; // 파일사이즈값을 넣어줌
				setProgress(progress);
			}
			return null;
		}

		// 이벤트 디스패칭 스레드 실행 완료시
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			setCursor(null); // 대기 커서 끄기
		}
	}

	public void setfup(boolean bo) {
		this.setVisible(bo);
	}

	public void init() {
		progressBar = new JProgressBar(0, 100);// 최소 ,최대
		progressBar.setValue(0); // 값
		progressBar.setStringPainted(true);
		p = new JPanel();
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("North", b1);
		con.add("Center", p);
		p.add(tf);
		p.add(progressBar);
		con.add("South", b2);
		con.setVisible(true);
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		b1.addActionListener(this); // 파일 선택
		b2.addActionListener(this); // 파일 전송
	}

	public FileUP(Client c) {
		super("파일 올리기");
		this.init();
		this.start();
		this.client = c;
		this.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		int xpos = (int) screen.getWidth() / 2 - this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - this.getHeight() / 2;
		this.setLocation(xpos, ypos);

		this.setResizable(false);
		this.setVisible(true);
	}

	public String getfilename() {
		return file;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == b1) {
			fd = new FileDialog(this, "", FileDialog.LOAD);
			fd.setVisible(true);
			directory = fd.getDirectory();
			file = fd.getFile();
			tf.setText(directory + file);

			File f = new File(directory + file);
			fileSize = (int) f.length();

		}

		if (e.getSource() == b2) { // 파일 전송 버튼 누를 시
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));// 커서 모양
																		// 변경
			// 변경
			// javax.swing.SwingWorker 인스턴스는 재사용 불가 그러므로 새로운 인터턴스가 필요함
			task = new Task(); // 객체생성
			task.addPropertyChangeListener(this); // 작업의 진행률 속성이 변경되면 호출됩니다.
			task.execute(); // 실행
			Thread th = new Thread(this); // 파일전송 스레드 실행
			th.start();
		}
	}

	// 작업의 진행률 속성이 변경되면 호출됩니다.
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			if(progress==100){
				this.setVisible(false);
			}
		}
	}

	@Override
	public void run() {
		try {
			ia = InetAddress.getByName("localhost");
			so = new Socket(ia, 20002);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(file + "\n");
			bw.flush();

			DataInputStream dis = new DataInputStream(new FileInputStream(new File(tf.getText())));
			DataOutputStream dos = new DataOutputStream(so.getOutputStream());

			int b = 0;

			int size = 0;

			while ((b = dis.read()) != -1) {
				dos.write(b);
				per = (int) (size * 100 / fileSize)+1;
				progressBar.setValue(per);
				size++;
				dos.flush();
			}
			dis.close();
			dos.close();
			so.close();
			dis = null;
			dos = null;
			so = null;

		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}