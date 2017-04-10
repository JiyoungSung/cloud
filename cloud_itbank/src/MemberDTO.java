package project_1;
public class MemberDTO {
	private String id;
	private String pass;
	private String email;
	private String signday;
	private String rank;
	private int membersize;
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getSignday() {
		return signday;
	}
	public void setSingday(String signday) {
		this.signday = signday;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getMembersize() {
		return membersize;
	}
	public void setMembersize(int membersize) {
		this.membersize = membersize;
	}
	
	
}
