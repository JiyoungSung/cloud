import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;


class ViewerSub extends JFrame implements TreeWillExpandListener,ActionListener {
	private Container con;
	private JSplitPane sp = new JSplitPane();
	private JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("My 탐색기");
	private JTree tree_jt = new JTree(root);
	private JScrollPane tree_jsp = new JScrollPane(tree_jt);
	private Vector view_vc = new Vector();
	private JList view_li = new JList(view_vc);
	//private JScrollPane view_jsp = new JScrollPane(view_li);
	/*
	private JButton view_bt = new JButton("VIEW");
	private JButton edit_bt = new JButton("EDIT");
	private JButton del_bt = new JButton("DELETE");
	private JButton end_bt = new JButton("EXIT");
	private JTextArea data_ta = new JTextArea();
	private JScrollPane data_jsp = new JScrollPane(data_ta);
	*/
	private Dimension screen;
	
	private Vector data_vc = new Vector();
	private Vector field_vc = new Vector();
	private JTable view_jt;
	private JScrollPane view_jsp;
	private JButton clear_bt = new JButton("CLEAR");
	private JButton end_bt = new JButton("END");
	private JPanel jpjp;
	
	public ViewerSub() {
		super("My 탐색기");
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.init();
		this.start();
		this.setSize(800, 600);
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setVisible(true);
	}

	public void init() {
		con = this.getContentPane();
		//왼쪽 영역
		con.setLayout(new BorderLayout());
		con.add("Center", sp);
		tree_jsp.setPreferredSize(new Dimension(200, (int) this.getHeight()));
		sp.setLeftComponent(tree_jsp);
		
		//오른쪽 영역
		JPanel jp = new JPanel(new BorderLayout());
		/*
		view_jsp.setPreferredSize(new Dimension((int) this.getWidth() - 220, 300));
		jp.add("Center", view_jsp);
		sp1.setTopComponent(jp);
		sp.setRightComponent(sp1);
		*/
		sp1.setTopComponent(jp);
		sp.setRightComponent(sp1);
		
		jpjp = new JPanel(new BorderLayout(10, 10));
		Vector data_vc = new Vector();
		Vector field_vc = new Vector();
		field_vc.add("파일명");
		field_vc.add("파일경로");
		field_vc.add("파일크기");
		field_vc.add("최종수정일");
		field_vc.add("파일종류");
		JPanel jpjp = new JPanel(new BorderLayout(10, 10));
		JTable view_jt = new JTable(data_vc, field_vc);
		view_jsp = new JScrollPane(view_jt);
		jpjp.add("Center", view_jsp);
		jp.add("Center", jpjp);
		
		
		
		File[] file = File.listRoots();
		for (int i = 0; i < file.length; i++) {
			DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(file[i]);
			dmt.add(new DefaultMutableTreeNode("EMPTY"));
			root.add(dmt);
		}
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tree_jt.addTreeWillExpandListener(this);
		/*
		view_bt.addActionListener(this);
		edit_bt.addActionListener(this);
		del_bt.addActionListener(this);
		end_bt.addActionListener(this);
		*/
	}

	
	public void treeWillExpand(TreeExpansionEvent e) {
		if (e.getSource() == tree_jt) {
			tree_jt.setSelectionPath(e.getPath());
			TreePath tp = tree_jt.getSelectionPath();
			// System.out.println("tp = " + tp);
			StringTokenizer stk = new StringTokenizer(tp.toString(), "[,]");
			stk.nextToken();
			if (stk.hasMoreTokens()) {
				String filepath = stk.nextToken().trim();
				while (stk.hasMoreTokens()) {
					filepath += stk.nextToken().trim() + "/";
				}
				// System.out.println("file = " + filepath);
				File dir = new File(filepath);
				File[] data = dir.listFiles();
				if (data == null) {
					return;
				}
				DefaultMutableTreeNode imsi = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				imsi.removeAllChildren();
				view_vc.clear();
				if (data.length == 0) {
					imsi.add(new DefaultMutableTreeNode("EMPTY"));
				} else {
					int count = -1;
					for (int i = 0; i < data.length; i++) {
						if (data[i].isDirectory()) {
							DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(
									data[i].getName());
							dtm.add(new DefaultMutableTreeNode("EMPTY"));
							imsi.add(dtm);
							count++;
						} else {
							/*
							view_vc.add(data[i].getName() + " ("+ data[i].length() + "byte, "
									+ new Date(data[i].lastModified()) + ")");
							*/
							/*
							Vector temp = new Vector();
							temp.add(files[i].getName());
							temp.add(files[i].getParent());
							temp.add(String.valueOf(files[i].length()));
							temp.add(new Date(files[i].lastModified()).toString());
							temp.add(files[i].isDirectory() ? "폴더" : "파일");
							updateTable(temp);
							*/
							Vector temp = new Vector();
							temp.add(data[i].getName());
							temp.add(data[i].getParent());
							temp.add(String.valueOf(data[i].length()));
							temp.add(new Date(data[i].lastModified()).toString());
							temp.add(data[i].isDirectory() ? "폴더" : "파일");
							System.out.println(temp.toString());
							updateTable(temp);
						}
					}
					if (count == -1) {
						imsi.add(new DefaultMutableTreeNode("EMPTY"));
					}
				}
				view_li.setListData(view_vc);
			}
		}
	}
	
	private void updateTable(Vector temp) {
		
		data_vc.add(temp);
		//view_jt.updateUI();
		jpjp.remove(view_jsp);
		view_jt = new JTable(data_vc, field_vc);
		view_jsp = new JScrollPane(view_jt);
		jpjp.add("Center", view_jsp);
		jpjp.validate();
		
	}
	
	public void treeWillCollapse(TreeExpansionEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

public class viewer {
	public static void main(String[] args) {
		/*
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ee) {}
		*/
		ViewerSub ws = new ViewerSub();
	}
}
