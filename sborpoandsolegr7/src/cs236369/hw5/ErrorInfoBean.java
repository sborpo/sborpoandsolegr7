package cs236369.hw5;

public class ErrorInfoBean {
	
	public String getErrorString() {
		return errorString;
	}
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLinkStr() {
		return linkStr;
	}
	public void setLinkStr(String linkStr) {
		this.linkStr = linkStr;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	private String errorString;
	private String reason;
	private String linkStr;
	private String link;

}
