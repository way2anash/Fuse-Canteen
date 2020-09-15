package com.fusecanteen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fusecanteen.domain.Food;
import com.fusecanteen.domain.Review;
import com.fusecanteen.services.ReviewService;

@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/reviews/{foodId}")
	public ResponseEntity<Food> addReview(@RequestBody Review review, @PathVariable Long foodId) {

		Food food = reviewService.addReview(review, foodId);
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}

	@PutMapping("/reviews/{foodId}/{reviewId}")
	public ResponseEntity<Food> upateReview(@RequestBody Review review, @PathVariable Long foodId, @PathVariable Long reviewId) {

		Food food =  reviewService.upateReviewById(review, foodId,reviewId);
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/reviews/{foodId}/{reviewId}")
	public ResponseEntity<HttpStatus> DeleteReview(@RequestBody Review review, @PathVariable Long foodId, @PathVariable Long reviewId) {

		 reviewService.deleteReviewById(review, foodId,reviewId);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
