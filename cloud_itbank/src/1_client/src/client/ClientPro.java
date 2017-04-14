package client;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

class ClientFrame extends JFrame implements ActionListener {
	private Dimension screen;
	// 첫번째 줄
	private JPanel title_p;
	private JLabel title_lb;

	// 정보 바
	private JPanel info_p;
	private JButton myinfo_bt;
	private JButton fileup_bt;
	private JButton fileload_bt;
	private JButton photo_bt;
	private JLabel usage_lb;
	private JButton upgrade_bt;
	private JLabel empty_lb;
	private JLabel name_lb;
	private JLabel date_lb;
	private JLabel size_lb;

	// 파일리스트 띄우기
	private Panel file_p = new Panel();
	private TextArea file_texta = new TextArea(20, 20);

	// 폴더위치
	private JPanel center_p;
	private JPanel folder_p;
	private JPanel folder_lb_p;
	private TextArea file_ta;

	// 10분 로그아웃
	// jtree
	private Container con;
	private JSplitPane sp = new JSplitPane();
	private JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("사용자이름");
	private JTree tree_jt = new JTree(root);
	private JScrollPane tree_jsp = new JScrollPane(tree_jt);

	private MemberDTO mdto;
	private FileDTO fdto;
	private Client client;
	private FileUP fu;
	private boolean time = true;

	public void init() {
		// 설정
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		// 첫번째 줄 레이아웃 설정
		title_p = new JPanel();
		con.add("North", title_p);// 타이틀 바
		title_p.setLayout(new BorderLayout());// 타이틀바 보더.
		title_lb = new JLabel("JCloud", JLabel.CENTER);
		title_lb.setFont(new Font("", Font.BOLD, 20));
		title_p.add("Center", title_lb);

		// 정보바 레이아웃 설정
		center_p = new JPanel();
		con.add("Center", center_p);// 센터 패널.
		center_p.setLayout(new BorderLayout());

		info_p = new JPanel();
		con.add("West", info_p);
		center_p.add("North", new JLabel("자신의 폴더리스트", JLabel.CENTER));
		center_p.add("Center", file_texta);
		// sp.setLeftComponent(info_p);

		info_p.setLayout(new GridLayout(10, 1));
		myinfo_bt = new JButton("내 정보");
		fileup_bt = new JButton("파일 UP");
		fileload_bt = new JButton("파일 LOAD");
		photo_bt = new JButton("사진 보기");
		usage_lb = new JLabel("500M/1GB 사용됨", JLabel.CENTER);
		upgrade_bt = new JButton("용량 업그레이드");
		info_p.add(myinfo_bt);
		info_p.add(fileup_bt);
		info_p.add(fileload_bt);
		info_p.add(photo_bt);
		info_p.add(usage_lb);
		info_p.add(upgrade_bt);

	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fileup_bt.addActionListener(this);
		myinfo_bt.addActionListener(this);
		fileload_bt.addActionListener(this);
		photo_bt.addActionListener(this);
	}

	public void file(String str) {

		String reset = "";
		System.out.println(str);
		file_texta.setText(reset);
		client.client_Sign("fileviwe%" + mdto.getId());
		System.out.println("정보보기 사인");
		String msg[] = str.split("%");

		for (int i = 0; i < msg.length; i++) {
			file_texta.append(msg[i]);
		}

	}

	public ClientFrame(Client cl, MemberDTO dto) {
		super("J클라우드");
		client = cl;
		this.mdto = dto;
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.init(); // 모양설정 또는 초기값설정
		this.start(); // event설정
		this.file("");
		this.setSize(1000, 600);
		int xpos = (int) screen.getWidth() / 2 - (int) this.getWidth() / 2;
		int ypos = (int) screen.getHeight() / 2 - (int) this.getHeight() / 2;
		this.setLocation(xpos, ypos);
		this.setResizable(true);
		this.setVisible(true);
		// tt = new Th_time();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileup_bt) {
			client.client_Sign("fileup%" + mdto.getId());
			fu = new FileUP(client);
			client.setFileup(fu);
		} else if (e.getSource() == myinfo_bt) {
			System.out.println("정보버튼 누름");
			new Myinfo(mdto);
		} else if (e.getSource() == fileload_bt) {
			client.client_Sign("fileload%" + mdto.getId());
			System.out.println("클라사인 보냄");
		} else if (e.getSource() == photo_bt) {
			client.client_Sign("photo%"+ mdto.getId());
			System.out.println("사진보기 보냄");
			
		
		}

	}
}

public class ClientPro {
	public ClientPro(Client client, MemberDTO mto) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ee) {
		}
		ClientFrame cf = new ClientFrame(client, mto);
	}

	public static void main(String[] args) {
		ClientFrame cc = new ClientFrame(null, null);
	}
}