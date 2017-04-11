import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import java.awt.dnd.*;
import java.io.*;
import java.net.*;

class ClientFrame extends JFrame implements DropTargetListener, TreeWillExpandListener{
	private Dimension screen;
	//ù��° ��
	private JPanel title_p;
	private JLabel title_lb;
	private JTextField search_tf;
	private JButton search_bt;
	private JButton view_bt;
	private JButton setup_bt;
	private JPanel setup_p;
	
	//���� ��
	private JPanel info_p;
	private JButton folder_bt;
	private JButton photo_bt;
	private JButton important_bt;
	private JButton trash_bt;
	private JLabel usage_lb;
	private JButton upgrade_bt;
	private JLabel empty_lb;
	private JLabel name_lb;
	private JLabel date_lb;
	private JLabel size_lb;
	
	//������ġ
	private JPanel center_p;
	private JPanel folder_p;
	private JPanel folder_lb_p;
	private TextArea file_ta;
	
	//�������� �����
	private JSplitPane sp = new JSplitPane();
	private JScrollPane info_jsp = new JScrollPane();
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ŭ����");
	private JTree tree_jt = new JTree(root);
	private JScrollPane tree_jsp = new JScrollPane(tree_jt);
	private Vector view_vc = new Vector();
	
	
	
	//�巡�׾� ���
	private DropTarget dt;
	
	public void init(){
		//����
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		//ù��° �� ���̾ƿ� ����
		title_p = new JPanel();
		con.add("North", title_p);
		title_p.setLayout(new BorderLayout());
		title_lb = new JLabel("Cloud");
		title_lb.setFont(new Font("", Font.BOLD, 20));
		title_p.add("West", title_lb);
		search_tf = new JTextField("Ŭ���� �˻�");
		title_p.add("Center", search_tf);
		setup_p = new JPanel();
		title_p.add("East", setup_p);
		setup_p.setLayout(new GridLayout(1, 4));
		search_bt = new JButton("�˻�");
		setup_p.add(search_bt);
		view_bt = new JButton("view");
		setup_p.add("East", view_bt);
		setup_bt = new JButton("setup");
		setup_p.add("East", setup_bt);
		
		//������ ���̾ƿ� ����
		center_p = new JPanel();
		con.add("Center", center_p);
		center_p.setLayout(new BorderLayout());
		center_p.add("Center", sp);
		info_jsp.setPreferredSize(new Dimension(200, (int) screen.getHeight()));
		sp.setLeftComponent(info_jsp);
		info_p = new JPanel();
		sp.setLeftComponent(info_p);
		info_p.setLayout(new GridLayout(10, 1));
		folder_bt = new JButton("�� ����");
		photo_bt = new JButton("�� ����");
		important_bt = new JButton("�߿�");
		trash_bt = new JButton("������");
		usage_lb = new JLabel("500M/1GB ����", JLabel.CENTER);
		upgrade_bt = new JButton("�뷮 ���׷��̵�");
		info_p.add(folder_bt);
		info_p.add(photo_bt);
		info_p.add(important_bt);
		info_p.add(trash_bt);
		info_p.add(usage_lb);
		info_p.add(upgrade_bt);
		
		//���� ���� ����		
		sp.setRightComponent(tree_jsp);
		
		File[] file = File.listRoots();
		for (int i = 0; i < file.length; i++) {
			DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(file[i]);
			dmt.add(new DefaultMutableTreeNode("EMPTY"));
			root.add(dmt);
		}
		
		//�߾� ����
		/*
		folder_lb_p = new JPanel();
		folder_p.add("North", folder_lb_p);
		folder_lb_p.setLayout(new GridLayout(1, 4));
		empty_lb = new JLabel();
		name_lb = new JLabel("�̸�");
		date_lb = new JLabel("���� ���� ��¥", JLabel.CENTER);
		size_lb = new JLabel("���� ũ��", JLabel.CENTER);
		folder_lb_p.add(empty_lb);
		folder_lb_p.add(name_lb);
		folder_lb_p.add(date_lb);
		folder_lb_p.add(size_lb);
		file_ta = new TextArea();
		//folder_p.add("Center", file_ta);
		*/
		
		//�巡�׾� ���
		dt = new DropTarget(tree_jt, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		
	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tree_jt.addTreeWillExpandListener(this);
	}
		
	public ClientFrame(String title){
		super(title);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.init(); //��缳�� �Ǵ� �ʱⰪ����
		this.start(); //event����
		this.setSize(800, 600);
		int xpos = (int)screen.getWidth()/2-(int)this.getWidth()/2;
		int ypos = (int)screen.getHeight()/2-(int)this.getHeight()/2;
		this.setLocation(xpos, ypos);
		this.setResizable(true);
		this.setVisible(true);
	}
	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void drop(DropTargetDropEvent dtde) {
		//System.out.println("dragDrop");
        if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0){
            dtde.acceptDrop(dtde.getDropAction());
            Transferable tr = dtde.getTransferable();
            try{
                //���ϸ� ������
                List list = (List) tr.getTransferData(DataFlavor.javaFileListFlavor);
                //���ϸ� ���
                for(int i=0;i < list.size();i++){
                    //System.out.println(list.size() + "-" + list.get(i));
                	File file = (File)list.get(i);
                    System.out.println(file.getName());
                    String filename = list.get(i).toString();
                    try{
                    	InetAddress ia = InetAddress.getByName("localhost");
                    	Socket soc = new Socket(ia, 12345);
                    	PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
                    	pw.println(file.getName());
                    	FileSender_c fs = new FileSender_c(soc, filename);
                    	fs.start();
                    }catch(IOException e){}
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
	}
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void treeWillCollapse(TreeExpansionEvent arg0) throws ExpandVetoException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
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
				DefaultMutableTreeNode imsi = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
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
							view_vc.add(data[i].getName() + " ("
									+ data[i].length() + "byte, "
									+ new Date(data[i].lastModified()) + ")");
						}
					}
					if (count == -1) {
						imsi.add(new DefaultMutableTreeNode("EMPTY"));
					}
				}
				//view_li.setListData(view_vc);
				 
			}
		}
	}
}

class FileSender_c extends Thread {
    Socket socket;
    DataOutputStream dos;
    FileInputStream fis;
    BufferedInputStream bis;
    String filename;
    int control = 0;
    public FileSender_c(Socket socket,String filestr) {
        this.socket = socket;
        this.filename = filestr;
        try {
            // ������ ���ۿ� ��Ʈ�� ����
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    @Override
    public void run() {
        try {
  
            String fName = filename;
  
            // ���� ������ �����鼭 ����
            File f = new File(fName);
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
  
            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = bis.read(data)) != -1) {
                dos.write(data, 0, len);
                dos.flush();
            }
            
            dos.close();
            bis.close();
            fis.close();
            System.out.println("�Ϸ�");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ClientPro {
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ee) {}
		
		ClientFrame cf = new ClientFrame("Ŭ���̾�Ʈ");
		
		
	}
}
