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
	static final int PORT = 12345; // ����, Ŭ�� (����,���������);
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
			System.out.println("console> PORT(" + PORT + ") �� ������ �õ��մϴ�.");
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
					System.out.println("Ŭ�� ���� ���� �غ� ");
					socket = serverSocket.accept();
					InputStream stream = socket.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(stream));
					String response = br.readLine();
					System.out.println(response);
					String[] sign = response.split("%");
					System.out.println("Ŭ�� sign="+sign[1]);
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
							JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� Ȯ���ϼ���.", "���̵� ��й�ȣ ����",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("idcheck")) {
						if (sign[1].equals("true")) {
							JOptionPane.showMessageDialog(null, "��� ������ ���̵� �Դϴ�.", "���̵� üũ",
									JOptionPane.DEFAULT_OPTION);
							idcheck = true;

						} else {
							idcheck = false;
							JOptionPane.showMessageDialog(null, "��� �� ������ ���̵� �Դϴ�.", "���̵� üũ",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("emailcheck")) {
						if (sign[1].equals("true")) {
							emailcheck = true;
							JOptionPane.showMessageDialog(null, "��� ������ �̸��� �Դϴ�.", "�̸��� üũ",
									JOptionPane.DEFAULT_OPTION);
						} else {
							emailcheck = false;
							JOptionPane.showMessageDialog(null, "��� �� ������ �̸��� �Դϴ�.", "�̸��� üũ",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("makeid")) {
						if (sign[1].equals("true")) {
							JOptionPane.showMessageDialog(null, "���̵� ���� �Ϸ�.", "���̵� ���� Ȯ��", JOptionPane.DEFAULT_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "���̵� ���� ����.", "���̵� ���� ����", JOptionPane.DEFAULT_OPTION);
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
					} else if (sign[0].equals("findid")) { // ���̵� ã�� ���
						if (sign[1].equals("true")) {
							JOptionPane.showMessageDialog(null, "ã���ô� ���̵�� " + sign[2] + " �Դϴ�.", "���̵� ã�� ���",
									JOptionPane.DEFAULT_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "��ϵ� �̸����� �ƴմϴ�.", "���̵� ã�� ���",
									JOptionPane.DEFAULT_OPTION);
						}
					} else if (sign[0].equals("fileclear")) {
						JOptionPane.showMessageDialog(null, "�������� �Ϸ�.", "��������", JOptionPane.DEFAULT_OPTION);

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
						System.out.println("�ɱ� ?");
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
