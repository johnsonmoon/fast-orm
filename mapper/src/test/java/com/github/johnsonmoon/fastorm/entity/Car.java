package com.github.johnsonmoon.fastorm.entity;

import com.github.johnsonmoon.fastorm.core.annotation.Column;
import com.github.johnsonmoon.fastorm.core.annotation.Id;
import com.github.johnsonmoon.fastorm.core.annotation.PrimaryKey;
import com.github.johnsonmoon.fastorm.core.annotation.Table;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/4/12 16:35.
 */
@Table(name = "car"/*, settings = "ENGINE=InnoDB CHARSET=UTF8"*/)
public class Car {
	@Id
	@PrimaryKey
	@Column(name = "_id", type = "varchar(20)", notNull = true)
	private String id;

	@Column(name = "price", type = "integer")
	private int price;

	@Column(name = "create_date", type = "datetime")
	private Date createDate;

	@Column(name = "sequence_numbers", type = "varchar(1000)")
	private List<String> sequenceNumbers;

	@Column(name = "properties", type = "varchar(1000)")
	private Map<String, Object> properties;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<String> getSequenceNumbers() {
		return sequenceNumbers;
	}

	public void setSequenceNumbers(List<String> sequenceNumbers) {
		this.sequenceNumbers = sequenceNumbers;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "Car{" +
				"id='" + id + '\'' +
				", price=" + price +
				", createDate=" + createDate +
				", sequenceNumbers=" + sequenceNumbers +
				", properties=" + properties +
				'}';
	}
}
