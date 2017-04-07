<<<<<<< HEAD
<<<<<<< Upstream, based on origin/merge_test2
//UDP ¸Þ½ÃÁö ¹Þ±â ¿¹Á¦
=======

>>>>>>> 29ad1ed ì´í´ë¦½ìŠ¤ í…ŒìŠ¤íŠ¸ ìž…ë‹ˆë‹¤
=======
//UDP ¸Þ½ÃÁö ¹Þ±â ¿¹Á¦
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
