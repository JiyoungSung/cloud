
//����
import java.net.*;
import java.io.*;

public class Server1{
 public static void main(String args[]) throws Exception{  
 
  /* 3333��Ʈ�� ���������� ���� */
  ServerSocket ss=new ServerSocket(12345);
 
  /* Ŭ���̾�Ʈ�� �����Ҷ� ���� ��ٸ� */
  /* �����ϰ� �Ǹ� Ŭ���̾�Ʈ�� �����͸� �ְ� ������ �ִ� ������ ������ ������ */
  Socket s=ss.accept();
  System.out.println("���� "+s+" �� �����");
 
  /* �������κ��� ��Ʈ�� ���� */
  InputStream is=s.getInputStream();
  BufferedReader br=new BufferedReader(new InputStreamReader(is));
 
  /* ������ ������ ��ü ������ */
  String fileName=br.readLine();
  File f=new File("E:\\test\\new",fileName);
  System.out.println(f.length());
   long filesize = f.length();
  /* ����� ���� ������ */
  FileOutputStream out=new FileOutputStream(f);
  /* ������ ������ ������ �о ���Ϸ� �� */
  int i=0;
  while((i=is.read())!=-1){
   out.write((char)i);
  }
  
  System.out.println("�������� C:/fileTransfer/temp ��ο� �����!");
 
  /* �ڿ����� */
  br.close(); is.close(); out.close(); s.close(); ss.close();
  br=null; is=null; out=null; s=null; ss=null;
 }
}