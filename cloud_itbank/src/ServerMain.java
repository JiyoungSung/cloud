package project_1;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class ServerMain extends Thread{
	
	public ServerMain(){
		this.start();
		new Serverupload();
		
	}

	public void run(){
		ServerReceiver sr=new ServerReceiver();
	}
	public static void main(String[] args){
	new ServerMain();	
	}
}
