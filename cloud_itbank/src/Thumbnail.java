
import java.awt.*;
import java.awt.image.BufferedImage; 
import java.io.File; 
import javax.imageio.ImageIO;
import javax.swing.*; 
import java.awt.event.*;

class Test{ 
	public Test(){
		try { 
			File originalFileName = new File("D:\\raonolje_bigdata\\photo\\iphone3G.jpg"); 
			File thumbnailFileName = new File("D:\\test.png"); 
			int width = 150; 
			int height = 150; 
			//	썸네일 이미지 생성 
			BufferedImage originalImg = ImageIO.read(originalFileName); 
			BufferedImage thumbnailImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); 
			// 썸네일 그리기 
			Graphics2D g = thumbnailImg.createGraphics(); 
			g.drawImage(originalImg, 0, 0, width, height, null); 
			// 파일생성 
			ImageIO.write(thumbnailImg, "png", thumbnailFileName);	
		} catch (Exception e) { 
			e.printStackTrace(); 
		}	
	}
}

class ThumbnailFrame extends JFrame implements MouseListener{
	private JLabel jlb = new JLabel();
	private JPanel jp = new JPanel();
	private ImageIcon ii;
	private Test test;
	
	public void init(){
		//설정
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		test = new Test();
		ii = new ImageIcon("D:\\test.png");
		jlb = new JLabel(ii);
		con.add(jp);
		jp.setLayout(new FlowLayout());
		jp.add(jlb);
		
		
	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jlb.addMouseListener(this);
	}
		
	public ThumbnailFrame(){
		this.init(); //모양설정 또는 초기값설정
		this.start(); //event설정
		this.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)screen.getWidth()/2-(int)this.getWidth()/2;
		int ypos = (int)screen.getHeight()/2-(int)this.getHeight()/2;
		this.setLocation(xpos, ypos);
		this.setResizable(true);
		this.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
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
		if(me.getSource() == jlb){
			//System.out.println("라벨 클릭됨");
			ii = new ImageIcon("D:\\raonolje_bigdata\\photo\\iphone3G.jpg");
			
			JDialog jdlg = new JDialog();
			jdlg.setSize(ii.getIconWidth(), ii.getIconWidth());
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			int xpos = (int)screen.getWidth()/2-(int)jdlg.getWidth()/2;
			int ypos = (int)screen.getHeight()/2-(int)jdlg.getHeight()/2;
			jdlg.setLocation(xpos, ypos);
			jdlg.setVisible(true);
			
			Container con = jdlg.getContentPane();
			jdlg.setLayout(new BorderLayout());
			JPanel jp1 = new JPanel();
			JLabel jlb1 = new JLabel(ii);
			jdlg.add(jp1);	
			jdlg.add(jlb1);
			
		}
		
	}
	
}

public class Thumbnail {
	public static void main(String[] args){
		ThumbnailFrame tf = new ThumbnailFrame();
	}
}
