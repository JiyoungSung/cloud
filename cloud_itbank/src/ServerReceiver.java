package project;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ServerReceiver {

	public ServerReceiver() {
		
		try {
			ServerSocket ss = new ServerSocket(12346);
			System.out.println("서버대기중....");
			
			Socket s = ss.accept();
			System.out.println(s.toString());
			InputStream is = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String fileName = br.readLine(); // 클라이언트 에서 파일명을 보내준것을 받음
			String[] str=fileName.split("%");
			File f = new File("E:"+File.separator+"jsh_java_workspace"+File.separator+"201704_javaproject"
					+File.separator+"src"+File.separator+"project"+File.separator+"serverfile"+File.separator+str[0]);
			if(!f.exists()){//경로가 존재하지않는다면 만든다
				f.mkdirs();
			}
			File f1 = new File(f,str[1]);
			
			FileOutputStream out = new FileOutputStream(f1);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write((char) i);
			}

			br.close(); is.close(); out.close(); s.close(); ss.close();
			br = null; is = null; out = null; s = null;	ss = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}