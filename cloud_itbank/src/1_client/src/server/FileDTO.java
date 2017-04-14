package server;

public class FileDTO {
private String id;
private String fileid;
private String uploadday;
private int filesize;
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
public String getUploadday() {
	return uploadday;
}
public void setUploadday(String upload) {
	this.uploadday = upload;
}
public int getFilesize() {
	return filesize;
}
public void setFilesize(int filesize) {
	this.filesize = filesize;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
}
