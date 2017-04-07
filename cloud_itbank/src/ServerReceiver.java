package project;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ServerReceiver {

	public ServerReceiver() {
		
		try {
			ServerSocket ss = new ServerSocket(12346);
			System.out.println("���������....");
			
			Socket s = ss.accept();
			System.out.println(s.toString());
			InputStream is = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String fileName = br.readLine(); // Ŭ���̾�Ʈ ���� ���ϸ��� �����ذ��� ����
			String[] str=fileName.split("%");
			File f = new File("E:"+File.separator+"jsh_java_workspace"+File.separator+"201704_javaproject"
					+File.separator+"src"+File.separator+"project"+File.separator+"serverfile"+File.separator+str[0]);
			if(!f.exists()){//��ΰ� ���������ʴ´ٸ� �����
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