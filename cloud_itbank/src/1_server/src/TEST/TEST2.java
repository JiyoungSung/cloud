package TEST;

import java.io.File;
import java.io.IOException;

public class TEST2 {
	
	public void MainPanelfile(String source){ //서버의 폴더경로를 쓴다.// 텍스트
		String a = new String();//폴더내의 전체 파일명을 담는다. % 로 구분.
		int c=0;//파일크기 담을값.
		

		File dir = new File(source); 
		
		String[] b=new String[dir.listFiles().length];
		
		File[] fileList = dir.listFiles(); 
		
		try{

			for(int i = 0 ; i < fileList.length ; i++){
				
				File file = fileList[i]; 
				c+=file.length();
	
				b[i]=file.getName();
				
				if(file.isFile()){

    // 파일이 있다면 파일 이름 출력
					System.out.println(file.getCanonicalPath().toString());
					System.out.println("\t 파일 이름 = " + file.getName());
					a+=b[i]+"%";
					
				}
				
				}
			System.out.println(a);
			
			//String a 를가져간다.	 //그다음은 상위폴더의 내용물이다.
			System.out.println(c+"byte");

		}catch(IOException e){

		
		}

	}
	}

