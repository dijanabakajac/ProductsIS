package services;

import java.util.Collection;

import persistance.QueryExecutor;

import util.Constants;

import domain.Product;


import domain.Review;

public class ProductServiceImplementation implements ProductService {

	@Override
	public Collection<Product> getProducts(String name, String model,
			String brandName, boolean withImage, double minAggregateRating,
			double minPrice, double maxPrice) {
		
		QueryService queryService = new QueryService();
		Collection<Product> products = queryService.getProducts(name, model, brandName, withImage, minAggregateRating, minPrice, maxPrice);
	return products;
	
	}
		
		
	

	@Override
	public Collection<Review> getReviewsForProduct(String id) {
		QueryService queryService = new QueryService();
		Product product = queryService.getProduct(Constants.NS+id);
		return product.getReviews();
	}

}
