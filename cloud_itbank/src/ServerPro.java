
import java.io.*;
import java.net.*;

class FileTransfer{
	private ServerSocket ss;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	private CloudSocket cs;
	
	public FileTransfer() throws Exception{
		ss = new ServerSocket(12345);
		System.out.println("Server ready...");
		while(true){
			soc = ss.accept();
			FileClient fc = new FileClient(cs);
			cs.
		}
	}
	
	class FileClient extends Thread{
		Socket soc;
		
		FileClient(Socket soc){
			this.soc = soc;
		}
		public void run(){
			while(true){
				try{
					br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				}
			}
		}
	}
}

public class ServerPro {
	public static void main(String[] args) throws Exception{
		
	}
}