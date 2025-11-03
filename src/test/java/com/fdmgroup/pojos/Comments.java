package com.fdmgroup.pojos;

public class Comments {

	private int id;
	private String  userid;
	private String body;
	private String postid;
	
	public Comments() {
		super();
	}
	
	public Comments(int id, String userid, String body, String postid) {
		super();
		this.id = id;
		this.userid = userid;
		this.body = body;
		this.postid = postid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", userid=" + userid + ", body=" + body + ", postid=" + postid + "]";
	}
	
	
	
}
