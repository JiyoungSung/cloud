
import java.net.*;

public class CloudSocket extends Socket{
	private String id;
	private String fileid;
	private String upload;
	private Double filesize;
	private int num;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public Double getFilesize() {
		return filesize;
	}
	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}