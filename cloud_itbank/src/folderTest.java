
import java.io.*;
import java.util.*;

class testFile{
	File ft = null; 
	String path = "D:\\raonolje_bigdata"; 

}

public class folderTest {
	public static ArrayList<String> dirList = new ArrayList<String>();
	public static ArrayList<testFile> fileList = new ArrayList<testFile>(); 
	public static String desti = "./mk";
	public static String source = "./root";

	public static void main(String[] args) {
		new folderTest();  

	}

	public folderTest(){
		subDirList(this.source);

		// 디렉토리 생성 
		for(int i = 0 ; i < dirList.size() ; i++){
			File temp = new File(dirList.get(i));
			if(!temp.exists()){
				temp.mkdirs(); 
			}
		}

		BufferedWriter bw = null; 
		BufferedReader br = null; 
		try{
			for(int i = 0 ; i < this.fileList.size() ; i++){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(i).ft)));
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileList.get(i).path)));
				while(true){
					String str = br.readLine(); 
					if(str == null) break; 
					bw.write(str); 
					bw.newLine(); 

				}
				br.close(); 
				bw.close(); 
			}
		}catch(IOException E){}
	}

	public void subDirList(String source){
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					String rep = this.source.replaceAll("\\\\", "/"); 
					String path = this.desti+file.getPath().replaceAll("\\\\", "/").replaceAll(rep, ""); 

					testFile tf = new testFile(); 
					tf.path = path; 
					tf.ft = file; 
					System.out.println(path);
					this.fileList.add(tf); 
				}else if(file.isDirectory()){
					String rep = this.source.replaceAll("\\\\", "/"); 
					String path = this.desti+file.getPath().replaceAll("\\\\", "/").replaceAll(rep, ""); 
					this.dirList.add(path); 
					subDirList(file.getCanonicalPath().toString()); 
				}
			}
		}catch(IOException e){}
	}
}



