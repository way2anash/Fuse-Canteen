package com.fusecanteen.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusecanteen.domain.Food;
import com.fusecanteen.domain.Review;

@Service
public class ReviewService {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final Logger LOG = LoggerFactory.getLogger(ReviewService.class);
	
	@Autowired
	private FoodService foodService;

	public Food addReview(Review review, Long foodId) {
		
		LOG.info("Adding Food Review to food id : ", foodId);
		Optional<Food> food = foodService.findFoodById(foodId);
		
		if(food.isPresent()) {
			
			List<Review> tempReview = 
					(food.get().getReview()==null ? new ArrayList<Review>() : food.get().getReview() );
			review.setCreatedDate(dateFormat.format(new Date()));
			tempReview.add(review);
			food.get().setReview(tempReview);
			foodService.saveFood(food.get());
			return food.get(); 
			
		}
		    return food.get(); 
	}

	public Food upateReviewById(Review review, Long foodId, Long reviewId) {
		
		LOG.info("updating Food Review to food id : ", foodId,", review id : ",reviewId);
		Optional<Food> food = foodService.findFoodById(foodId);
		
		if(food.isPresent()) {
			List<Review> tempReview = food.get().getReview();
			
			if(tempReview !=null) {
				for(Iterator<Review> it = tempReview.iterator(); it.hasNext();) {
					Review r = it.next();
					if(r.getId() == reviewId) {
						 it.remove();	
					}
				}
				review.setCreatedDate(dateFormat.format(new Date()));
				tempReview.add(review);
				food.get().setReview(tempReview);
				foodService.saveFood(food.get());
				return food.get(); 
				
			}
			else {
				tempReview = new ArrayList<Review>();
				review.setCreatedDate(dateFormat.format(new Date()));
				tempReview.add(review);
				food.get().setReview(tempReview);
				foodService.saveFood(food.get());
				return food.get(); 
			}
		}
		return food.get();
	}

	public void deleteReviewById(Review review, Long foodId, Long reviewId) {
		
		LOG.info("Deleting Food Review to food id : ", foodId,", review id : ",reviewId);
		Optional<Food> food = foodService.findFoodById(foodId);
		
		if(food.isPresent()) {
			List<Review> tempReview = food.get().getReview();
			
			if(tempReview !=null) {			
				for(Iterator<Review> it = tempReview.iterator(); it.hasNext();) {
					Review r = it.next();
					if(r.getId() == reviewId) {
						 it.remove();	
					}
				}
				food.get().setReview(tempReview);
				foodService.saveFood(food.get());
			}
		}
	}
	
}
