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
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("사용자이름");
	private JTree tree_jt = new JTree(root);
	private JScrollPane tree_jsp = new JScrollPane(tree_jt);
	
	private Dimension screen;
	
	private Vector data_vc = new Vector();
	private Vector field_vc = new Vector();
	private DefaultTableModel dtm;
	private JTable view_jt;
	private JScrollPane view_jsp;
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
		//폴더 루트
		File file = new File("D:\\");
		DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(file);
		dmt.add(new DefaultMutableTreeNode("EMPTY"));
		root.add(dmt);
		
		//jtree영역
		con = this.getContentPane();
		con.setLayout(new BorderLayout(10, 10));
		con.add("Center", sp);
		tree_jsp.setPreferredSize(new Dimension(200, (int) this.getHeight()));
		sp.setLeftComponent(tree_jsp);
		
		//jtable영역
		JPanel jp = new JPanel(new BorderLayout());
		jpjp = new JPanel(new BorderLayout(10, 10));
		Vector data_vc = new Vector();
		Vector field_vc = new Vector();
		field_vc.add("파일명");
		field_vc.add("파일경로");
		field_vc.add("파일크기");
		field_vc.add("최종수정일");
		//field_vc.add("파일종류");
		JPanel jpjp = new JPanel(new BorderLayout(10, 10));
		dtm = new DefaultTableModel(data_vc, field_vc);
		JTable view_jt = new JTable(dtm);
		view_jsp = new JScrollPane(view_jt);
		jpjp.add("Center", view_jsp);
		jp.add("Center", jpjp);
		sp1.setTopComponent(jp);
		sp.setRightComponent(sp1);
		
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tree_jt.addTreeWillExpandListener(this);
	}

	
	public void treeWillExpand(TreeExpansionEvent e) {
		if (e.getSource() == tree_jt) {
			tree_jt.setSelectionPath(e.getPath());
			TreePath tp = tree_jt.getSelectionPath();
			//System.out.println("tp = " + tp);
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
				if (data.length == 0) {
					imsi.add(new DefaultMutableTreeNode("EMPTY"));
				} else {
					int count = -1;
					int co=0;
					dtm.setNumRows(0);
					for (int i = 0; i < data.length; i++) {
						if (data[i].isDirectory()) {
							DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(
									data[i].getName());
							dtm.add(new DefaultMutableTreeNode("EMPTY"));
							imsi.add(dtm);
							count++;
						} else {
							Vector temp = new Vector();
							temp.add(data[i].getName());
							temp.add(data[i].getParent());
							temp.add(String.valueOf(data[i].length()));
							temp.add(new Date(data[i].lastModified()).toString());
							//temp.add(data[i].isDirectory() ? "폴더" : "파일");
							//System.out.println(temp.toString());
							dtm.insertRow(co, temp);
							co++;
						}
					}
					if (count == -1) {
						imsi.add(new DefaultMutableTreeNode("EMPTY"));
					}
				}
			}
		}
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
