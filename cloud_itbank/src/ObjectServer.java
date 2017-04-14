import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.tree.TreePath;

public class ObjectServer {
	private ServerSocket ss;
	private Socket soc;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public ObjectServer(){
		try {
			ss = new ServerSocket(12345);
			System.out.println("서버 대기중...");
			soc = ss.accept();
			FileInfoTransfer fit = new FileInfoTransfer(soc);
			fit.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	class FileInfoTransfer extends Thread{
		private Socket soc;
		public FileInfoTransfer(Socket soc){
			this.soc = soc;
		}
		public void run(){
			try {
				ois = new ObjectInputStream(soc.getInputStream());
				oos = new ObjectOutputStream(soc.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
			try {
				while(true){
					
					File file = new File((String)ois.readObject());
					
					oos.writeObject(file);
					oos.flush();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					oos.close();
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
	
	public static void main(String[] args){
		new ObjectServer();
	}
}

