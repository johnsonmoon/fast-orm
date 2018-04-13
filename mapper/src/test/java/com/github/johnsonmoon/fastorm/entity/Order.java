package com.github.johnsonmoon.fastorm.entity;

import com.github.johnsonmoon.fastorm.core.annotation.*;

/**
 * Created by johnsonmoon at 2018/4/8 17:45.
 */
@Table(name = "order"/*, settings = "ENGINE=InnoDB CHARSET=UTF8"*/)
public class Order {
	@Id
	@PrimaryKey
	@Column(name = "id", type = "varchar(20)", notNull = true)
	private String id;
	@Indexed
	@Column(name = "phone", type = "varchar(15)", defaultValue = "1323260332")
	private String phone;
	@Indexed
	@Column(name = "receiver", notNull = true)
	private String receiver;
	@Column(name = "address", type = "varchar(100)", notNull = true)
	private String address;

	public Order() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id='" + id + '\'' +
				", phone='" + phone + '\'' +
				", receiver='" + receiver + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
