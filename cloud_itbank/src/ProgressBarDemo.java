
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;
import java.util.Random;
import javax.swing.*;

class MyFrame08 extends JFrame implements ActionListener, PropertyChangeListener, Runnable {

	private JFileChooser jfc = new JFileChooser();
	private JButton b1 = new JButton("���ϼ���");
	private JButton b2 = new JButton("��������");
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

	class Task extends SwingWorker<Void, Void> {
		// ���� Task ��׶��忡�� ������ ����
		@Override
		public Void doInBackground() {

			int progress = 0;
			// progress �ʱ�ȭ
			setProgress(0);

			while (progress < 100) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
				}
				progress = per; // ���ϻ������ �־���
				setProgress(progress);
			}

			return null;
		}

		// �̺�Ʈ ����Ī ������ ���� �Ϸ��
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			setCursor(null); // ��� Ŀ�� ����
		}
	}

	public void init() {
		progressBar = new JProgressBar(0, 100);// �ּ� ,�ִ�
		progressBar.setValue(0); // ��
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b1.addActionListener(this); // ���� ����
		b2.addActionListener(this); // ���� ����
	}

	public MyFrame08(String title) {
		super(title);
		init();
		start();
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

		if (e.getSource() == b1) {
			fd = new FileDialog(this, "", FileDialog.LOAD);
			fd.setVisible(true);
			directory = fd.getDirectory();
			file = fd.getFile();
			tf.setText(directory + file);

			File f = new File(directory + file);
			System.out.println((int) f.length());
			fileSize = (int) f.length();

		}

		if (e.getSource() == b2) { // ���� ���� ��ư ���� ��
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));// Ŀ�� ��� ����															// ����

			// javax.swing.SwingWorker �ν��Ͻ��� ���� �Ұ� �׷��Ƿ� ���ο� �����Ͻ��� �ʿ���
			task = new Task(); // ��ü����
			task.addPropertyChangeListener(this); // �۾��� ����� �Ӽ��� ����Ǹ� ȣ��˴ϴ�.
			task.execute(); // ����
			Thread th = new Thread(this); // �������� ������ ����
			th.start();
		}
	}

	// �۾��� ����� �Ӽ��� ����Ǹ� ȣ��˴ϴ�.
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			}

	}

	@Override
	public void run() {

		try {

			ia = InetAddress.getByName("localhost");
			so = new Socket(ia, 12345);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
			bw.write(file + "\n");
			bw.flush();

			DataInputStream dis = new DataInputStream(new FileInputStream(new File(tf.getText())));
			DataOutputStream dos = new DataOutputStream(so.getOutputStream());

			int b = 0;

			int size = 0;

			while ((b = dis.read()) != -1) {
				dos.write(b);
				per = (int) (size * 100 / fileSize);
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
			System.exit(0);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	}
}

public class ProgressBarDemo {

	public static void main(String[] args) {
		MyFrame08 mf = new MyFrame08(" ProgressBar");

	}

}
