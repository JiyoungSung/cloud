package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver {
	FileDAO fdao=new FileDAO();
	public FileReceiver(String id) throws IOException {
		ServerSocket ss = new ServerSocket(20002);

		/* 클라이언트가 접속할때 까지 기다림 */
		/* 접속하게 되면 클라이언트와 데이터를 주고 받을수 있는 유일한 소켓을 열어줌 */
		Socket s = ss.accept();
		System.out.println("소켓 " + s + " 에 연결됨");

		/* 소켓으로부터 스트림 얻어옴 */
		InputStream is = s.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		/* 저장할 파일의 객체 생성함 */
		String fileName = br.readLine();
		File f = new File("D:" + File.separator +"project"+File.separator+id);
		if(!f.exists()){//경로가 존재하지않는다면 만든다
			f.mkdirs();
		}
		File f1=new File(f,fileName);
		/* 기록할 파일 연결함 */
		FileOutputStream out = new FileOutputStream(f1);
		/* 보내온 파일의 끝까지 읽어서 파일로 씀 */
		int i = 0;
		while ((i = is.read()) != -1) {
			out.write((char) i);
		}

		System.out.println("받은파일 C:/fileTransfer/temp 경로에 저장됨!");

		/* 자원정리 */
		br.close();
		is.close();
		out.close();
		s.close();
		ss.close();
		br = null;
		is = null;
		out = null;
		s = null;
		ss = null;
	}
}
