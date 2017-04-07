package project;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import project_client.Serverupload;
public class Server extends Thread{
	
	public Server(){
		this.start();
		new Serverupload();
		
	}

	public void run(){
		ServerReceiver sr=new ServerReceiver();
	}
	public static void main(String[] args){
	new Server();	
	}
}
