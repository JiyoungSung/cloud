<<<<<<< Upstream, based on origin/merge_test2
//UDP 메시지 받기 예제
=======

>>>>>>> 29ad1ed �씠�겢由쎌뒪 �뀒�뒪�듃 �엯�땲�떎

import java.net.*;
import java.io.*;

public class Ex02 {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket(12345);
		while(true){
			byte[] data = new byte[65508];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			ds.receive(dp);
			System.out.println(dp.getAddress().getHostAddress()
					+ " >> "+new String(dp.getData()).trim());
		}
	}
}
