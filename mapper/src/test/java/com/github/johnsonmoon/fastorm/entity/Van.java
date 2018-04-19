package com.github.johnsonmoon.fastorm.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/4/19 14:31.
 */
public class Van {
	private String id;
	private int price;
	private Date createDate;
	private List<String> sequenceNumbers;
	private Map<String, Object> properties;
	private boolean isImported;
	private int seatCount;
	private String origin;

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

	public boolean isImported() {
		return isImported;
	}

	public void setImported(boolean imported) {
		isImported = imported;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "Van{" +
				"id='" + id + '\'' +
				", price=" + price +
				", createDate=" + createDate +
				", sequenceNumbers=" + sequenceNumbers +
				", properties=" + properties +
				", isImported=" + isImported +
				", seatCount=" + seatCount +
				", origin='" + origin + '\'' +
				'}';
	}
}
