

public class ProjectDTO {
	// memberDB
	private String rank;
	private String id;
	private String pass;
	private String email;
	private String signday;
	private int membersize;
	
	//file DB
	private String fileid;
	private String upload;
	private int num =0;
	private int filesize;
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSignday() {
		return signday;
	}
	public void setSignday(String signday) {
		this.signday = signday;
	}
	public int getMembersize() {
		return membersize;
	}
	public void setMembersize(int membersize) {
		this.membersize = membersize;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	
	


}
