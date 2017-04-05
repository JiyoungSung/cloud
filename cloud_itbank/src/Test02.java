
//����
import java.net.*;
import java.io.*;

class Server{
	private int i=0;
	private String fileName;
	
	private ServerSocket ss;
	private Socket soc;
	
	public Server() throws IOException{
		ss = new ServerSocket(12345);
		/* Ŭ���̾�Ʈ�� �����Ҷ� ���� ��ٸ� */
		/* �����ϰ� �Ǹ� Ŭ���̾�Ʈ�� �����͸� �ְ� ������ �ִ� ������ ������ ������ */
		soc=ss.accept();
		System.out.println("���� "+soc+" �� �����");
		
		this.start();
	}
	
	public void start() throws IOException{
		/* �������κ��� ��Ʈ�� ���� */
		  InputStream is=soc.getInputStream();
		  BufferedReader br=new BufferedReader(new InputStreamReader(is));
		 
		  /* ������ ������ ��ü ������ */
		  fileName=br.readLine();
		  File f=new File("E:\\test\\new",fileName);
		  System.out.println(f.length());
		 
		  /* ����� ���� ������ */
		  FileOutputStream out=new FileOutputStream(f);
		  /* ������ ������ ������ �о ���Ϸ� �� */
		  while((i=is.read())!=-1){
			  out.write((char)i);
		  }
		  
		  System.out.println("�������� C:/fileTransfer/temp ��ο� �����!");
		 
		  /* �ڿ����� */
		  br.close(); is.close(); out.close(); soc.close(); ss.close();
		  br=null; is=null; out=null; soc=null; ss=null;
	}
}

public class Test02{
	public static void main(String args[]) throws Exception{  
		Server server = new Server();
	}
}