<<<<<<< HEAD
<<<<<<< Upstream, based on origin/merge_test2
//UDP �޽��� �ޱ� ����
=======

>>>>>>> 29ad1ed 이클립스 테스트 입니다
=======
//UDP �޽��� �ޱ� ����
>>>>>>> refs/heads/master

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
