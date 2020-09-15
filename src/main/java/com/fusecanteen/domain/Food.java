package com.fusecanteen.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="food")
public class Food {

	@Id
	private Long id;
	private String name;
	private Float price;
	private Integer frequency;
	private Boolean isPreparedToday;
	private String category;
	private List<Review> review;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public Integer getFrequency() {
		return frequency;
	}


	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}


	public Boolean getIsPreparedToday() {
		return isPreparedToday;
	}


	public void setIsPreparedToday(Boolean isPreparedToday) {
		this.isPreparedToday = isPreparedToday;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public List<Review> getReview() {
		return review;
	}


	public void setReview(List<Review> review) {
		this.review = review;
	}


	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", price=" + price + ", frequency=" + frequency
				+ ", isPreparedToday=" + isPreparedToday + ", category=" + category + ", review=" + review + "]";
	}
	
	
}
