import java.io.*;
import java.net.*;
import java.util.*;

public class ObjectClient {
	private Socket soc;
	private File dir;
	private ObjectOutputStream oos;
	
	public ObjectClient() {
		try {
			soc = new Socket("localhost", 12345);
			
			FileInfoReceiver fir = new FileInfoReceiver(soc);
			fir.start();
			try{
				oos = new ObjectOutputStream(soc.getOutputStream());
				dir = new File(path);
				oos.writeObject(dir);
				oos.flush();
			}catch(Exception e){}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	class FileInfoReceiver extends Thread{
		private Socket soc;
		private ObjectInputStream ois;
		private File file;
		
		public FileInfoReceiver(Socket soc){
			this.soc = soc;
		}
		public void run(){
			try{
				ois = new ObjectInputStream(soc.getInputStream());
				
				while(true){
					file = (File)ois.readObject();
					
				}
			}catch(Exception e){}
		}
		public File getObject(){
			return file;
		}
	}
	
	public static void main(String[] args){
		 new ObjectClient();
	}
}
