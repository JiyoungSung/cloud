package client;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class ImageRes {
        Socket socket = null;
        Socket socket1 = null;
        InputStreamReader inputStreamReader = null;
        FileOutputStream fileOutputStream = null;
        BufferedReader br;
        String msg;
        int count;
 public ImageRes(){
        try {
        	socket1 = new Socket("localhost",1919);//클라쪽소켓이다.
        	br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
           msg=br.readLine();
            count=Integer.parseInt(msg);
            br.close();
            socket1.close();
        	for(int i=0;i<count;i++){
            socket = new Socket("localhost", 1818);
            InputStream inputStream = socket.getInputStream();
            inputStream.toString();
            fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\photo\\"+i+".jpg");
            byte[] dataBuff = new byte[10000];
            int length = inputStream.read(dataBuff);
            System.out.print("다운중 ");
            
            while (length != -1) {// 서버에서 파일을 가져옴
                System.out.print(".");
                fileOutputStream.write(dataBuff, 0, length);
                length = inputStream.read(dataBuff);
 
            }
            System.out.println();
 
            System.out.println("파일 저장 성공");
            inputStream.close(); socket.close();
            }
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
}
}

 
    


