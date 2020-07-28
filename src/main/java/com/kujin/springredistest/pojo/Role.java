package com.kujin.springredistest.pojo;

import java.io.Serializable;

/**
 * 角色类对象
 * 对象实例化，重写UID
 */
public class Role implements Serializable {
	
	private static final long serialVersionUID = 6977402643848374753L;

	private long id;
	private String roleName;
	private String note;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", roleName='" + roleName + '\'' +
				", note='" + note + '\'' +
				'}';
	}
}