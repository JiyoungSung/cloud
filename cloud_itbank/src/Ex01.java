//UDP 메시지 보내기 예제

import java.net.*;
import java.io.*;

public class Ex01 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket ds = new DatagramSocket();
		while(true){
			System.out.println("보낼물건 = ");
			String msg = br.readLine();
			for(int i=2;i<254;i++){
				InetAddress ia = InetAddress.getByName("192.168.0."+i);
				DatagramPacket dp = new DatagramPacket(
						msg.getBytes(), msg.getBytes().length, ia, 12345);
				ds.send(dp);
			}
			System.out.println("전송완료");
		}
	}
}
