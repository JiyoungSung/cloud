
//서버
import java.net.*;
import java.io.*;

class Server{
	private int i=0;
	private String fileName;
	
	private ServerSocket ss;
	private Socket soc;
	
	public Server() throws IOException{
		ss = new ServerSocket(12345);
		/* 클라이언트가 접속할때 까지 기다림 */
		/* 접속하게 되면 클라이언트와 데이터를 주고 받을수 있는 유일한 소켓을 열어줌 */
		soc=ss.accept();
		System.out.println("소켓 "+soc+" 에 연결됨");
		
		this.start();
	}
	
	public void start() throws IOException{
		/* 소켓으로부터 스트림 얻어옴 */
		  InputStream is=soc.getInputStream();
		  BufferedReader br=new BufferedReader(new InputStreamReader(is));
		 
		  /* 저장할 파일의 객체 생성함 */
		  fileName=br.readLine();
		  File f=new File("E:\\test\\new",fileName);
		  System.out.println(f.length());
		 
		  /* 기록할 파일 연결함 */
		  FileOutputStream out=new FileOutputStream(f);
		  /* 보내온 파일의 끝까지 읽어서 파일로 씀 */
		  while((i=is.read())!=-1){
			  out.write((char)i);
		  }
		  
		  System.out.println("받은파일 C:/fileTransfer/temp 경로에 저장됨!");
		 
		  /* 자원정리 */
		  br.close(); is.close(); out.close(); soc.close(); ss.close();
		  br=null; is=null; out=null; soc=null; ss=null;
	}
}

public class Test02{
	public static void main(String args[]) throws Exception{  
		Server server = new Server();
	}
}