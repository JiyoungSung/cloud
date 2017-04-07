<<<<<<< Upstream, based on origin/merge_test2
//UDP ¸Ş½ÃÁö º¸³»±â ¿¹Á¦
=======

>>>>>>> 29ad1ed ì´í´ë¦½ìŠ¤ í…ŒìŠ¤íŠ¸ ì…ë‹ˆë‹¤

import java.net.*;
import java.io.*;

public class Ex01 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket ds = new DatagramSocket();
		while(true){
			System.out.println("º¸³¾¹°°Ç = ");
			String msg = br.readLine();
			for(int i=2;i<254;i++){
				InetAddress ia = InetAddress.getByName("192.168.0."+i);
				DatagramPacket dp = new DatagramPacket(
						msg.getBytes(), msg.getBytes().length, ia, 12345);
				ds.send(dp);
			}
			System.out.println("Àü¼Û¿Ï·á");
		}
	}
}
