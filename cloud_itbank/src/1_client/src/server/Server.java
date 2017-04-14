package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.filechooser.FileView;
import javax.swing.plaf.SliderUI;

public class Server {

	static final int PORT = 12345;
	static final int PORT1 = 12346;
	Socket socket = null;
	Socket socket2 = null;
	InetAddress ia = null;
	Server_Thread st;

	public Server() {
		try {
			System.out.println("���������");
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				socket = serverSocket.accept();
				System.out.println("console> ���� " + socket.getInetAddress() + " Ŭ���̾�Ʈ�� " + socket.getLocalPort()
						+ " ��Ʈ�� ����Ǿ����ϴ�." + socket.getPort());
				st = new Server_Thread(socket.getInetAddress(), socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Server_Thread extends Thread {
		Socket socket = null;
		InetAddress ia = null;
		MemberDAO mdao;
		MemberDTO mto;

		public Server_Thread(InetAddress ia, Socket soc) {
			mdao = new MemberDAO();
			this.socket = soc;
			this.ia = ia;
			this.start();
		}

		public void server_Sign(String msg) {
			try {
				socket2 = new Socket(ia, PORT1);
				OutputStream stream = socket2.getOutputStream();
				stream.write(msg.getBytes());
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				InputStream stream = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				while (true) {
					System.out.println("���� ��ȣ ���� �غ���");
					String response = br.readLine();
					System.out.println(response);
					String[] sign = response.split("%");
					System.out.println("sign="+sign[0]);
					if (sign[0].equals("login")) {
						if (mdao.login(sign[1], sign[2])) {
							mto = mdao.info(sign[1]);
							server_Sign("login%true%" + mto.getId() + "%" + mto.getPass() + "%" + mto.getEmail() + "%"
									+ mto.getDay() + "%" + mto.getRank());
							createfolder(mto.getId());
						} else {
							server_Sign("login%false");
						}
					} else if (sign[0].equals("idcheck")) {
						if (mdao.ID_overlap(sign[1])) {
							server_Sign("idcheck%false");
						} else {
							server_Sign("idcheck%true");
						}
					} else if (sign[0].equals("emailcheck")) {
						if (mdao.email_check(sign[1])) {
							server_Sign("emailcheck%false");
						} else {
							server_Sign("emailcheck%true");
						}
					} else if (sign[0].equals("makeid")) {
						if (!mdao.ID_overlap(sign[1]) && !mdao.email_check(sign[3])) {
							mdao.member_add(sign[1], sign[2], sign[3], sign[4]);
							server_Sign("makeid%true");
						} else {
							server_Sign("makeid%false");
						}
					} else if (sign[0].equals("filelist")) {
						String msg = filelist(sign[1]);
						server_Sign("filelist%" + msg);
					} else if (sign[0].equals("findid")) {
						if (mdao.email_check(sign[1])) {
							String email = mdao.ID_return(sign[1]);
							server_Sign("findid%" + email);
						} else {
							server_Sign("findid%false%");
						}
					} else if (sign[0].equals("fileup")) {
						new FileReceiver(sign[1]);
					} else if (sign[0].equals("flieupclear")) {
						server_Sign("flieupclear");

					} else if (sign[0].equals("fileload")) {
						String str = filelist(sign[1]);
						server_Sign("flieload%" + str); // ���ϸ���Ʈ�� �������ش�.
						new FileSend();

					} else if (sign[0].equals("fileviwe")) {
						String str = filelist(sign[1]);
						server_Sign("fileviwe%" + str); // ���ϸ���Ʈ�� �������ش�.
						//new FileSend(); //�̰Ͷ����ȵ�..
					} else if (sign[0].equals("photo")) {
						String str = filelist(sign[1]);
						server_Sign("photo%" + str); // ���ϸ���Ʈ�� �������ش�. 
						System.out.println("�����ص� �ɱ mto="+mto.getId());
					new Imagecomp("D:" + File.separator + "project" + File.separator + mto.getId());
					 
						
					}
				}
			} catch (IOException e) {
				// TODO: handle exception
			}
		}

		public String filelist(String id) {
			File dir2 = new File("D:" + File.separator + "project" + File.separator + id);
			String list[] = dir2.list(); // ���丮�ȿ� �ִ� ���ϵ��� ����Ʈ�� ��´�.
			String msg = "";
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					if (i == (list.length - 1)) {
						msg += list[i];
					} else {
						msg += list[i] + "%";
					}
				}
				return msg;
			} else {
				return msg;
			}
		}

		public void createfolder(String id) {
			File f = new File("D:" + File.separator + "project" + File.separator + id);
			if (!f.exists()) {// ��ΰ� ���������ʴ´ٸ� �����
				f.mkdirs();
			}
		}

		public void fileview(String id) {
			try {
				Socket s = new Socket(ia, 60000);
				File file = new File("D:\\project\\" + id + "\\");
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(file);
				oos.flush();
				oos.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
	}
}
