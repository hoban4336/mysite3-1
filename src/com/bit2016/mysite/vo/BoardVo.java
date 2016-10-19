package com.bit2016.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String content;
	private Integer  hit;
	private String regDate;
	private String name;
	private Long users_no;
	private Integer group_no;
	private Integer order_no;
	private Integer depth;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public Integer getGroup_no() {
		return group_no;
	}
	public void setGroup_no(Integer group_no) {
		this.group_no = group_no;
	}
	public Integer getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Integer order_no) {
		this.order_no = order_no;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Long getUsers_no() {
		return users_no;
	}
	public void setUsers_no(Long users_no) {
		this.users_no = users_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content="
				+ content + ", regDate=" + regDate + ", hit=" + hit
				+ ", group_no=" + group_no + ", order_no=" + order_no
				+ ", depth=" + depth + ", users_no=" + users_no + ", name="
				+ name + "]";
	}

	
	
}
