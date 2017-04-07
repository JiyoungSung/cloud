import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
  
public class ClientTest {
    public static void main(String[] args) {
        Socket socket = null;       
        //String[] segments = args[0].split("\\.");
        String serverIp = "localhost";
        int serverPort = 12345;
        String filename = "D:\\Wildlife.wmv";
         
        try {
            // 서버 연결
            socket = new Socket(serverIp, serverPort); // socket(),connect();
            System.out.println("서버에 연결되었습니다.");
  
            FileSender fs = new FileSender(socket,filename);
            fs.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 
class FileSender extends Thread {
    Socket socket;
    DataOutputStream dos;
    FileInputStream fis;
    BufferedInputStream bis;
    String filename;
    int control = 0;
    public FileSender(Socket socket,String filestr) {
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
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
  
            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = bis.read(data)) != -1) {
                control++;
                if(control % 10000 == 0){
                    System.out.println("전송중..." + control/10000);      
                }
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


