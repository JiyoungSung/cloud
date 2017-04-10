package project_1;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class FileUp extends JFrame implements ActionListener {
	private JFileChooser jfc = new JFileChooser();
	private JButton b1 = new JButton("파일선택");
	private JButton b2 = new JButton("파일전송");
	private TextField tf = new TextField(25);
	private String file = "";
	private String directory = "";
	private FileDialog fd;
	private InetAddress ia = null;
	private Socket so = null;
	MemberDTO mem=new MemberDTO();
	MemberDAO mdao = new MemberDAO();
	FileDAO fdao = new FileDAO();
	private String id;
	private String filesize;
	private File f = null;
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("North", b1);
		con.add("Center", tf);
		con.add("South", b2);
		con.setVisible(true);
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}

	public FileUp(MemberDTO mem) {
		super("파일 올리기");
		this.mem=mem;
		id=mem.getId();
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
			f = new File(tf.getText());
			long a = f.length();
			filesize = String.valueOf(a);
		}

		if (e.getSource() == b2) {
			try {
				ia = InetAddress.getByName("localhost");
				so = new Socket(ia, 12346);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
				bw.write(id+"%"+file+"\n");				
				bw.flush();

				DataInputStream dis = new DataInputStream(new FileInputStream(new File(tf.getText())));
				DataOutputStream dos = new DataOutputStream(so.getOutputStream());

				int b = 0;
				while ((b = dis.read()) != -1) {
					dos.write(b);
					dos.flush();
				}
				fdao.File_Save(id, file, filesize); // 파일DB에 파일저장
				mdao.SizePluse(Integer.parseInt(filesize), id); // 멤버DB에 파일사이즈 ++
				
//				FileSave fs = new FileSave(id,file,filesize);
//				fs.File_Save();
				
				JOptionPane.showMessageDialog(null, "파일업로드 완료");
				dis.close(); dos.close(); so.close();
				dis = null; dos = null;	so = null;
				this.setVisible(false);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

	}
}
