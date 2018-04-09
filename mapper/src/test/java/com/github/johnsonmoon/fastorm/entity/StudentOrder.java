package com.github.johnsonmoon.fastorm.entity;

import com.github.johnsonmoon.fastorm.core.annotation.*;

/**
 * Created by johnsonmoon at 2018/4/8 17:45.
 */
@Table(name = "student_order", settings = "ENGINE=InnoDB CHARSET=UTF8")
public class StudentOrder {
	@Id
	@PrimaryKey
	@Column(name = "id", type = "varchar(20)", notNull = true)
	private String id;
	@Indexed
	@ForeignKey(references = "student(id)")
	@Column(name = "student_id", type = "varchar(20)", notNull = true)
	private String studentId;
	@Indexed
	@ForeignKey(references = "order(id)")
	@Column(name = "order_id", type = "varchar(20)", notNull = true)
	private String orderId;

	public StudentOrder() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
