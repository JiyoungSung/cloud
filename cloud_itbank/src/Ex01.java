<<<<<<< Upstream, based on origin/merge_test2
//UDP �޽��� ������ ����
=======

>>>>>>> 29ad1ed 이클립스 테스트 입니다

import java.net.*;
import java.io.*;

public class Ex01 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket ds = new DatagramSocket();
		while(true){
			System.out.println("�������� = ");
			String msg = br.readLine();
			for(int i=2;i<254;i++){
				InetAddress ia = InetAddress.getByName("192.168.0."+i);
				DatagramPacket dp = new DatagramPacket(
						msg.getBytes(), msg.getBytes().length, ia, 12345);
				ds.send(dp);
			}
			System.out.println("���ۿϷ�");
		}
	}
}
