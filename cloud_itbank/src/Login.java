
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

import javax.swing.*;

class Login extends JFrame implements MouseListener{
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
	
	private Member member;
	
	private String id;
	private String pass;
	
	public String getId(){
		return id;
	}
	public String getPass(){
		return pass;
	}
	//아이디 저장
	private File f = new File("cloud_id");
	
	public void saveId(){
		if(!f.canRead()) return;
		String cloudId="";
		try {
			FileInputStream fis = new FileInputStream(f);
			while(true){
				int res = fis.read();
				if(res < 0) break;
				cloudId += (char)res;
			}
			if(cloudId == null) return;
			id_jtf.setText(cloudId);
			pass_jtf.setText("");
			constant_cb.setState(true);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		//설정
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout(0, 20));
		title_jlb = new JLabel("Cloud", JLabel.CENTER);
		title_jlb.setFont(new Font("", Font.BOLD, 25));
		title_jlb.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		con.add("North", title_jlb);
		id_jtf = new JTextField("Cloud ID", 18);
		pass_jtf = new JPasswordField("비밀번호", 14);
		
		id_jtf.setFont(new Font("", Font.BOLD, 20));
		pass_jtf.setFont(new Font("", Font.BOLD, 20));
		constant_cb = new Checkbox();
		constant_jlb = new JLabel("로그인 상태 유지");
		center_p = new JPanel();
		con.add("Center", center_p);
		//center_p.setLayout(new GridLayout(4, 1, 10, 10));
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
		
		//아이디 저장 불러오는 메소드
		this.saveId();
		
	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jbt.addMouseListener(this);
		find_id_jlb.addMouseListener(this);
		make_id_jlb.addMouseListener(this);
		id_jtf.addMouseListener(this);
		pass_jtf.addMouseListener(this);
		
	}
		
	public Login(String title){
		super(title);
		this.init(); //모양설정 또는 초기값설정
		this.start(); //event설정
		this.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)screen.getWidth()/2-(int)this.getWidth()/2;
		int ypos = (int)screen.getHeight()/2-(int)this.getHeight()/2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource()==find_id_jlb && me.getButton()==1){
			//System.out.println("아이디 찾기 클릭");
		}
		if(me.getSource()==make_id_jlb && me.getButton()==1){
			//System.out.println("만들기 클릭");
		}
		
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
		if(me.getSource() == find_id_jlb){
			String email= JOptionPane.showInputDialog("당신의 이메일 주소를 입력하세요");
			if(email == null || email.equals("")){
				JOptionPane.showMessageDialog(null, "다시 입력해주세요");
				return;
			}
			String message = email+" 로 이메일을 보냈습니다.\n 잠시후 확인해주세요.";
			JOptionPane.showMessageDialog(null, message);
		}
		if(me.getSource() == make_id_jlb){
			JDialog jdlg = new JDialog();
			jdlg.setSize(300, 300);
			Dimension screen_jdlg = Toolkit.getDefaultToolkit().getScreenSize();
			int xpos = (int)screen_jdlg.getWidth()/2-(int)jdlg.getWidth()/2;
			int ypos = (int)screen_jdlg.getHeight()/2-(int)jdlg.getHeight()/2;
			jdlg.setLocation(xpos, ypos);
			jdlg.setResizable(false);
			
			Container con_jdlg = jdlg.getContentPane();
			con_jdlg.setLayout(new GridLayout(5, 1, 10, 10));
			JPanel[] jp = new JPanel[5];
			JLabel[] jlb = new JLabel[4];
			JTextField[] jtf = new JTextField[4];
			String[] str = new String[]{"아이디", "이메일", "비밀번호", "비밀번호확인"};
			JButton jbt_1 = new JButton("확인");
			JButton jbt_2 = new JButton("취소");
			for(int i=0;i<jp.length;i++){
				if(i==jp.length-1){
					jp[i] = new JPanel();
					con_jdlg.add(jp[i]);
					jp[i].setLayout(new FlowLayout());
					jp[i].add(jbt_1);jp[i].add(jbt_2);
					break;
				}
				jp[i] = new JPanel();
				con_jdlg.add(jp[i]);
				jp[i].setLayout(new FlowLayout());
				jlb[i] = new JLabel(str[i], JLabel.RIGHT);
				jtf[i] = new JTextField(15);
				jp[i].add(jlb[i]);jp[i].add(jtf[i]);
			}
			
			jdlg.setVisible(true);
		}
		if(me.getSource()==jbt){
			String id = id_jtf.getText();
			char[] pass = pass_jtf.getPassword();
			this.id = id;
			
			//체크되면 아이디 파일에 저장
			if(constant_cb.getState()){
				try {
					FileOutputStream fos = new FileOutputStream(f, false);
					byte by[] = id.getBytes();
					fos.write(by);
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!constant_cb.getState()){
				f.delete();
			}
			
			for(int i=0;i<pass.length;i++){
				this.pass+=pass[i];
			}
			this.setVisible(false);
			
		}
		if(me.getSource()==id_jtf){
			id_jtf.setText("");
			pass_jtf.setText("");
		}
	}
	public static void main(String[] args){
		new Login("로그인");
	}
}

class Member{
	private String id;
	private char pass[];
	private String email;
	
	public Member(String id, char[] pass){
		this.id= id;
		this.pass = pass;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public char[] getPass() {
		return pass;
	}
	public void setPass(char[] pass) {
		this.pass = pass;
	}
}


