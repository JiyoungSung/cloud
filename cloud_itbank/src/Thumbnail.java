
import java.awt.*;
import java.awt.image.BufferedImage; 
import java.io.File; 
import javax.imageio.ImageIO;
import javax.swing.*; 

class Test{ 
	public Test(){
		try { 
			File originalFileName = new File("D:\\raonolje_bigdata\\photo\\iphone3G.jpg"); 
			File thumbnailFileName = new File("D:\\test.png"); 
			int width = 200; 
			int height = 200; 
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
/*
class MyFrame01 extends JFrame{
	private JLabel jlb = new JLabel();
	private JPanel jp = new JPanel();
	private ImageIcon ii;
	private Test test;
	
	public void init(){
		//설정
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		test = new Test();
		ii = new 
		
		
	}
	public void start(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	public MyFrame01(String title){
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
}
*/
public class Thumbnail {
	public static void main(String[] args){
		Test test = new Test();
	}
}
