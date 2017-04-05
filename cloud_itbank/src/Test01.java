
		 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class MyFrame01  extends JFrame implements ActionListener{
	
	private JFileChooser jfc;
	private JButton b1;
	private JButton b2;
	private JTextField jtf;
	private String file;
	private String directory;
	private FileDialog fd;
	
	private InetAddress ia;
	private Socket so;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public void init(){
		jfc = new JFileChooser();
		b1 = new JButton("파일선택");
		b2 = new JButton("파일전송");
		jtf = new JTextField(25);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("North", b1);
		con.add("Center", jtf);
		con.add("South", b2);   
		con.setVisible(true);
	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	public MyFrame01(String title){
		super(title);
		init();
		start();
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		int xpos = (int)screen.getWidth()/2 - this.getWidth()/2;
		int ypos = (int)screen.getHeight()/2 - this.getHeight()/2;
		super.setLocation(xpos, ypos);
		
		super.setResizable(false);
		super.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == b1){
			fd=new FileDialog(this,"",FileDialog.LOAD);
			fd.setVisible(true);
			directory = fd.getDirectory();
			file = fd.getFile();
			jtf.setText(directory+file);
			
		}
		
		if(e.getSource() == b2){
			try{
				ia = InetAddress.getByName("localhost");
				so = new Socket(ia,12345);
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
				bw.write(file+"\n"); 
				bw.flush();
				 
				dis=new DataInputStream(new FileInputStream(new File(jtf.getText())));
				dos=new DataOutputStream(so.getOutputStream());
				 
				int b=0;
				 
				while((b=dis.read()) != -1 ){
					dos.write(b); 
					dos.flush();
				}
							 
				dis.close(); 
				dos.close(); 
				so.close(); 
				
			}catch (IOException e2) {
				e2.printStackTrace();
			}
			
			
			System.exit(0);
		}
	}
}

public class Test01{
	public static void main(String[] args) {
		new MyFrame01("클라이언트");
		
	}
}
