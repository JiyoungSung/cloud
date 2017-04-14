package server;
import java.awt.Image;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
//http://stellan.tistory.com/entry/Java-Network-Programming-%EC%97%B0%EC%8A%B5-2-%EA%B7%B8%EB%A6%BC%ED%8C%8C%EC%9D%BC%EC%9D%84-%EC%84%9C%EB%B2%84%EB%A1%9C-%EB%B6%80%ED%84%B0-%EC%A0%84%EC%86%A1%EB%B0%9B%EA%B8%B0
public class Imagesend {
	File file;

	ServerSocket serverSocket = null;
	FileInputStream fileInputStream = null;
	OutputStream outputStreamWriter = null;

	Socket socket = null;
	String imgpath;
	Imagecomp tb;
	public Imagesend(File file){ 
		this.file=file;
		try {
			serverSocket = new ServerSocket(1818);
			socket = serverSocket.accept();
			aaa a = new aaa(socket, this.file);
			a.start(); serverSocket.close();
		}catch (IOException e) {
			// TODO: handle exception
		}
	}
}

class aaa extends Thread{
	Socket socket;
	File file;
	FileInputStream fileInputStream = null;
	OutputStream outputStreamWriter = null;

	public aaa(Socket so, File file){
		this.socket = so;
		this.file = file;
	}

	public void run(){
		try{
			OutputStream outputStream = socket.getOutputStream();
			fileInputStream = new FileInputStream(file);
			
			byte[] dataBuff = new byte[10000];
			int length = fileInputStream.read(dataBuff);

			while (length != -1) {
				outputStream.write(dataBuff, 0, length);
				length = fileInputStream.read(dataBuff);
			}
			System.out.println("전송 선공");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
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
