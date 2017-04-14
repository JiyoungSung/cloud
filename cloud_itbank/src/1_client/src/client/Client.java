package client;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Client {
	static final int PORT = 12345; // 서버, 클라 (연결,스레드실행);
	static final int PORT1 = 12346;
	Socket socket = null;
	Socket socket2 = null;
	boolean check = false;
	boolean idcheck = false;
	boolean emailcheck = false;
	String filelist = "";
	Client cl;
	public Login log;
	public FileUP fup;

	public void setFileup(FileUP fup) {
		this.fup = fup;
	}

	public void logvis(boolean b) {
		log.setVisible(b);
	}

	public void setLogin(Login lo) {
		log = lo;
	}

	public void setclient(Client c) {
		cl = c;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setIdcheck(boolean idcheck) {
		this.idcheck = idcheck;
	}

	public void setEmailcheck(boolean emailcheck) {
		this.emailcheck = emailcheck;
	}

	public boolean isIdcheck() {
		return idcheck;
	}

	public boolean isEmailcheck() {
		return emailcheck;
	}

	public Client() {
		try {
			socket = new Socket("localhost", PORT);
			System.out.println("console> PORT(" + PORT + ") 로 접속을 시도합니다.");
			client_Thread ct = new client_Thread();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void client_Sign(String msg) {
		try {
			OutputStream stream = socket.getOutputStream();
			stream.write(msg.getBytes());
			stream.write('\n');
			stream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class client_Thread extends Thread {
		ServerSocket serverSocket;
		Socket socket = null;
		Socket socket2 = null;
		MemberDTO mdto;

		public client_Thread() {
			this.start();
		}

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(PORT1);
				while (true) {
					System.out.println("클라 사인 받을 준비 ");
					socket = serverSocket.accept();
					InputStream stream = socket.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(stream));
					String response = br.readLine();
					System.out.println(response);
					String[] sign = response.split("%");
					System.out.println("클라 sign="+sign[1]);
					if (sign[0].equals("login")) {
						if (sign[1].equals("true")) {
							mdto = new MemberDTO();
							mdto.setId(sign[2]);
							mdto.setPass(sign[2]);
							mdto.setEmail(sign[2]);
							mdto.setDay(sign[2]);
							mdto.setRank(sign[2]);
							new ClientPro(cl, mdto);
							log.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요.", "아이디 비밀번호 오류",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("idcheck")) {
						if (sign[1].equals("true")) {
							JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다.", "아이디 체크",
									JOptionPane.DEFAULT_OPTION);
							idcheck = true;

						} else {
							idcheck = false;
							JOptionPane.showMessageDialog(null, "사용 불 가능한 아이디 입니다.", "아이디 체크",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("emailcheck")) {
						if (sign[1].equals("true")) {
							emailcheck = true;
							JOptionPane.showMessageDialog(null, "사용 가능한 이메일 입니다.", "이메일 체크",
									JOptionPane.DEFAULT_OPTION);
						} else {
							emailcheck = false;
							JOptionPane.showMessageDialog(null, "사용 불 가능한 이메일 입니다.", "이메일 체크",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("makeid")) {
						if (sign[1].equals("true")) {
							JOptionPane.showMessageDialog(null, "아이디 생성 완료.", "아이디 생성 확인", JOptionPane.DEFAULT_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "아이디 생성 실패.", "아이디 생성 오류", JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("filelist")) {
						for (int i = 1; i < sign.length; i++) {
							if (i == sign.length - 1) {
								filelist += sign[i];
							} else {
								filelist += sign[i] + "%";
							}
						}
						new listview(filelist);
					} else if (sign[0].equals("findid")) { // 아이디 찾음 결과
						if (sign[1].equals("true")) {
							JOptionPane.showMessageDialog(null, "찾으시는 아이디는 " + sign[2] + " 입니다.", "아이디 찾기 결과",
									JOptionPane.DEFAULT_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "등록된 이메일이 아닙니다.", "아이디 찾기 결과",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("fileclear")) {
						JOptionPane.showMessageDialog(null, "파일전송 완료.", "파일전송", JOptionPane.DEFAULT_OPTION);

					} else if (sign[0].equals("flieload")) {
						String imsi = "";
						for (int i = 1; i < sign.length; i++) {
							if (i == sign.length - 1) {
								imsi += sign[i];
							} else
								imsi += sign[i] + "%";
						}
						new FileLoad_gui(mdto.getId(), imsi);

					} else if (sign[0].equals("fileviwe")) {
						String imsi2 = "";
						for (int i = 1; i < sign.length; i++) {
							if (i == sign.length - 1) {
								imsi2 += sign[i];
							} else
								imsi2 += sign[i] + "%";
						}
					} else if (sign[0].equals("photo")) {
						System.out.println("될까 ?");
						new ThumbnailFrame();
						}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void fileview() throws ClassNotFoundException {
			try {
				ServerSocket ss = new ServerSocket(60000);
				socket2 = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(socket2.getInputStream());
				File file = (File) ois.readObject();
				for (String str : file.list()) {

				}
			} catch (IOException e) {
				// TODO: handle exception
			}
		}

	}

	public static void main(String[] args) {
		Client c = new Client();
		c.setclient(c);
		Login log = new Login(c);
		c.setLogin(log);

	}
}
