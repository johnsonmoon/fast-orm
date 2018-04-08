package com.github.johnsonmoon.fastorm.entity;

import com.github.johnsonmoon.fastorm.core.annotation.Column;
import com.github.johnsonmoon.fastorm.core.annotation.Id;
import com.github.johnsonmoon.fastorm.core.annotation.PrimaryKey;
import com.github.johnsonmoon.fastorm.core.annotation.Table;

/**
 * Created by johnsonmoon at 2018/4/8 17:45.
 */
@Table(name = "student", settings = "ENGINE=InnoDB CHARSET=UTF8")
public class Student {
	@Id
	@PrimaryKey
	@Column(name = "id", type = "varchar(20)", notNull = true)
	private String id;
	@Column(name = "name", notNull = true)
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "phone", type = "varchar(15)")
	private String phone;
	@Column(name = "address", type = "varchar(100)")
	private String address;

	public Student() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
