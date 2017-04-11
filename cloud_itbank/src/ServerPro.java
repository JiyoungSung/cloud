
import java.io.*;
import java.net.*;

class FileTransfer{
	private ServerSocket ss;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	private int numPro;
	
	public FileTransfer() throws Exception{
		while(true){
			ss = new ServerSocket(12345);
			System.out.println("Server ready...");
			soc = ss.accept();
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			String filename = br.readLine();
			FileReceiver_s fr = new FileReceiver_s(soc, filename);
			fr.start();
			/*
			switch(numPro){
			case 0: {
				String filename = br.readLine();
				FileReceiver_s fr = new FileReceiver_s(soc, filename);
				fr.start();
				break;
			}
			case 1: break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
			case 7: break;
			case 8: break;
			case 9: break;
			
			}*/
		}
		
		//파일 업로드 : 0
		//파일 다운로드 : 1
		//내 폴더로 이동하기 : 2
		//내 사진 미리보기 : 3
		//중요체크 한 파일들보기 : 4
		//휴지통 보기 : 5
		//용량 체크하기 : 6
		//설정파일 열기 : 7
		//10분 동안 데이터 전송 없으면 연결끊기 : 8
		//파일 검색하기 : 9
		
	}
}

class FileReceiver_s extends Thread{
	private Socket soc;
	private BufferedOutputStream bos;
	private DataInputStream dis;
	private File f;
	private String filename;
	
	public FileReceiver_s(Socket soc, String filename){
		this.soc = soc;
		this.filename = filename;
	}
	public void run(){
		try{
			f = new File(filename);
			dis = new DataInputStream(soc.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			
			int len;
			int size = 4096;
			byte[] data = new byte[size];
			while((len = dis.read(data)) != -1){
				bos.write(data, 0, len);
				//bos.flush();
			}
			bos.close();
			dis.close();
		}catch(Exception e){
			System.out.println("파일 전송의 오류가 있습니다.");
		}
	}
}

class FileSender_s extends Thread {
    private Socket socket;
    private DataOutputStream dos;
    private FileInputStream fis;
    private BufferedInputStream bis;
    private String filename;

    public FileSender_s(Socket socket,String filestr) {
        this.socket = socket;
        this.filename = filestr;
        try {
            // 데이터 전송용 스트림 생성
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    @Override
    public void run() {
        try {
            String fName = filename;
  
            // 파일 내용을 읽으면서 전송
            File f = new File(fName);
            bis = new BufferedInputStream(new FileInputStream(f));
  
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
            System.out.println("완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ServerPro {
	public static void main(String[] args) throws Exception{
		FileTransfer ft = new FileTransfer();
	}
}