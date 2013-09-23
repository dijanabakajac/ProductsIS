package services;

import java.util.Collection;

import domain.Product;
import domain.Review;

public interface ProductService {
	Collection<Product> getProducts(String name, String model, String brandName, boolean withImage,
			double minAggregateRating, double minPrice, double maxPrice);

	Collection<Review> getReviewsForProduct(String id);
}
