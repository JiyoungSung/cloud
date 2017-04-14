
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;



class JTreeFrame extends JFrame implements TreeWillExpandListener{
	private Container con;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Å½»ö±â");
	private JTree tree_jt = new JTree(root);
	private JPanel jp = new JPanel();
	
	private Socket soc;
	private ObjectOutputStream oos;
	private File dir, file;
	private FileInfoReceiver fir;
	
	public JTreeFrame(){
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.init();
		this.start();
		//this.network();
		this.setSize(800, 600);
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setVisible(true);
	}
	public void init(){
		
		try {
			soc = new Socket("localhost", 12345);
			
			fir = new FileInfoReceiver(soc);
			fir.start();
		
			oos = new ObjectOutputStream(soc.getOutputStream());
			dir = new File("C:\\");
			oos.writeObject(dir);
			oos.flush();
		}catch(Exception e){}
	
		
		
		
		
		con = this.getContentPane();
		con.setLayout(new BorderLayout(10, 10));
		con.add("Center", jp);
		jp.add(tree_jt);
		
	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tree_jt.addTreeWillExpandListener(this);
	}
	
	
	@Override
	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		
	}
	@Override
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
		if (e.getSource() == tree_jt) {
			tree_jt.setSelectionPath(e.getPath());
			TreePath tp = tree_jt.getSelectionPath();
			System.out.println(tp);
			StringTokenizer stk = new StringTokenizer(tp.toString(), "[,]");
			stk.nextToken();
			if (stk.hasMoreTokens()) {
				String filepath = stk.nextToken().trim();
				while (stk.hasMoreTokens()) {
					filepath += stk.nextToken().trim() + "/";
				}
				System.out.println("file = " + filepath);
				
				try {
					oos.writeObject(new File("C:\\"+filepath));
					oos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
			}
		}
	}
	class FileInfoReceiver extends Thread{
		private Socket soc;
		private ObjectInputStream ois;
		private File file;
		
		public FileInfoReceiver(Socket soc){
			this.soc = soc;
		}
		public void run(){
			try{
				ois = new ObjectInputStream(soc.getInputStream());
				
				while(true){
					file = (File)ois.readObject();
					
					File[] data = file.listFiles();
					for (int i = 0; i < data.length; i++) {
						if (data[i].isDirectory()) {
							DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(data[i].getName());
							dtm.add(new DefaultMutableTreeNode("EMPTY"));
							root.add(dtm);
						}
						if(data[i].isFile()){
							DefaultMutableTreeNode dtm = new DefaultMutableTreeNode(data[i].getName());
							root.add(dtm);
						}
					}
					
				}
			}catch(Exception e){}
		}
		public File getObject(){
			return file;
		}
	}
}
public class JTreeTest {
	public static void main(String[] args){
		JTreeFrame jf = new JTreeFrame();
	}
}

