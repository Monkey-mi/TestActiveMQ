package com.tgb.SpringActivemq.model;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = -5268898209820709788L;
	
	private int u_id;
	private String login_id;
	private String pwd;
	private String u_type;
	private String login_type;
	private String login_def;
	private String name;
	private String sex;
	private Date birthday;
	private String tel;
	private String email;
	private String post;
	private String address;
	private Date create_dt;
	private Date modify_dt;
	private Date last_login;
	private String nickname;
	private int nick_portrait;
	private String is_valid;
	
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int uid) {
		this.u_id = uid;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getU_type() {
		return u_type;
	}
	public void setU_type(String u_type) {
		this.u_type = u_type;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public String getLogin_def() {
		return login_def;
	}
	public void setLogin_def(String login_def) {
		this.login_def = login_def;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreate_dt() {
		return create_dt;
	}
	public void setCreate_dt(Date create_dt) {
		this.create_dt = create_dt;
	}
	public Date getModify_dt() {
		return modify_dt;
	}
	public void setModify_dt(Date modify_dt) {
		this.modify_dt = modify_dt;
	}
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getNick_portrait() {
		return nick_portrait;
	}
	public void setNick_portrait(int nick_portrait) {
		this.nick_portrait = nick_portrait;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getIs_valid() {
		return is_valid;
	}
	@Override
	public String toString() {
		return "UserInfo [u_id=" + u_id + ", login_id=" + login_id + ", pwd="
				+ pwd + ", u_type=" + u_type + ", login_type=" + login_type
				+ ", login_def=" + login_def + ", name=" + name + ", sex="
				+ sex + ", birthday=" + birthday + ", tel=" + tel + ", email="
				+ email + ", post=" + post + ", address=" + address
				+ ", create_dt=" + create_dt + ", modify_dt=" + modify_dt
				+ ", last_login=" + last_login + ", nickname=" + nickname
				+ ", nick_portrait=" + nick_portrait + ", is_valid=" + is_valid
				+ "]";
	}
    	
}
