package TEST;

import java.io.File;
import java.io.IOException;

public class TEST2 {
	
	public void MainPanelfile(String source){ //������ ������θ� ����.// �ؽ�Ʈ
		String a = new String();//�������� ��ü ���ϸ��� ��´�. % �� ����.
		int c=0;//����ũ�� ������.
		

		File dir = new File(source); 
		
		String[] b=new String[dir.listFiles().length];
		
		File[] fileList = dir.listFiles(); 
		
		try{

			for(int i = 0 ; i < fileList.length ; i++){
				
				File file = fileList[i]; 
				c+=file.length();
	
				b[i]=file.getName();
				
				if(file.isFile()){

    // ������ �ִٸ� ���� �̸� ���
					System.out.println(file.getCanonicalPath().toString());
					System.out.println("\t ���� �̸� = " + file.getName());
					a+=b[i]+"%";
					
				}
				
				}
			System.out.println(a);
			
			//String a ����������.	 //�״����� ���������� ���빰�̴�.
			System.out.println(c+"byte");

		}catch(IOException e){

		
		}

	}
	}

