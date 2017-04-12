import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;


public class TreeEx extends JFrame implements TreeWillExpandListener, ActionListener{
	//���� ���� �⺻ gui
	private Container con;
	private JPanel jp;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ž����");
	private JTree tree_jt = new JTree(root);
	private JScrollPane tree_jsp = new JScrollPane(tree_jt);
	
	//�׽�Ʈ ��ư �߰�
	private String[] str = new String[]{"�����߰�", "���ϻ���", "�����߰�", "��������", "����", "����"};
	private JButton[] jbt = new JButton[str.length];
	private JPanel jbt_p = new JPanel();
	
	private Dimension screen;
	
	public TreeEx() {
		super("My Ž����");
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.init();
		this.start();
		this.setSize(600, 600);
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setVisible(true);
	}

	public void init() {
		con = this.getContentPane();
		con.setLayout(new BorderLayout());
		jp = new JPanel(new BorderLayout());
		con.add("Center", jp);
		tree_jsp.setPreferredSize(new Dimension(550, 500));
		jp.add(tree_jsp);
		jp.add("South", jbt_p);
		jbt_p.setLayout(new GridLayout(1, jbt.length+1));
		for(int i=0;i<jbt.length;i++){
			jbt[i] = new JButton(str[i]);
			jbt_p.add(jbt[i]);
		}
		
		//���������� �ֻ��������� ���� 
		File file = new File("D:\\raonolje_bigdata\\");
		DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(file);
		dmt.add(new DefaultMutableTreeNode("EMPTY"));
		root.add(dmt);

	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Ʈ�� ������
		tree_jt.addTreeWillExpandListener(this);
		for(int i=0;i<jbt.length;i++){
			jbt[i].addActionListener(this);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == jbt[0]){
			JFileChooser jfc = new JFileChooser();
			if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				System.out.println(jfc.getSelectedFile().toString());
				String outFileName = "D:\\"+jfc.getSelectedFile().getName();
				
				fileCopy(jfc.getSelectedFile().toString(), outFileName);
				
			}
		}
		if(ae.getSource() == jbt[1]){
			
		}
		if(ae.getSource() == jbt[2]){
	
		}
		if(ae.getSource() == jbt[3]){
			
		}
		if(ae.getSource() == jbt[4]){
			
		}
		if(ae.getSource() == jbt[5]){
			
		}
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
		//Ʈ�� �˻� �����ϵ��� ����
		if (e.getSource() == tree_jt) {
			TreeExpansion(e);
		}
		
	}
	public void TreeExpansion(TreeExpansionEvent e){
		tree_jt.setSelectionPath(e.getPath());
		TreePath tp = tree_jt.getSelectionPath();
		System.out.println("tp = " + tp);
		StringTokenizer stk = new StringTokenizer(tp.toString(), "[,]");
		stk.nextToken();
		if (stk.hasMoreTokens()) {
			String filepath = stk.nextToken().trim()+"/";
			while (stk.hasMoreTokens()) {
				filepath += stk.nextToken().trim() + "/";
			}
			//System.out.println("file = " + filepath);
			File dir = new File(filepath);
			File[] data = dir.listFiles();
			if (data == null) return;
			DefaultMutableTreeNode imsi = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
			imsi.removeAllChildren();
			
			if (data.length == 0) {
				imsi.add(new DefaultMutableTreeNode("EMPTY"));
			} else {
				for (int i = 0; i < data.length; i++) {
					if (data[i].isDirectory()) {
						DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(data[i].getName());
						dtm.add(new DefaultMutableTreeNode("EMPTY"));
						imsi.add(dtm);
					} 
				}
				for(int i=0;i<data.length;i++){
					if(data[i].isFile()){
						DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(data[i].getName());
						imsi.add(dtm);
					}
				}
			}
		}
	}
	public void fileCopy(String inFileName, String outFileName){
		 try {
			 FileInputStream fis = new FileInputStream(inFileName);
			 FileOutputStream fos = new FileOutputStream(outFileName);
			   
			 int data = 0;
			 while((data=fis.read())!=-1) {
			 fos.write(data);
			 }
			 fis.close();
			 fos.close();
			   
			 } catch (Exception e) {
			   e.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		new TreeEx();
	}
}
