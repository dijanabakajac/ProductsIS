package rest.parser;

import java.util.Collection;

import util.Constants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import domain.AggregateRating;
import domain.Brand;
import domain.Offer;
import domain.Organization;
import domain.Product;
import domain.Rating;
import domain.Review;

public class ProductJSONParser {
	public static JsonObject serialize(Product p) {
		JsonObject productJson = new JsonObject();

		productJson.addProperty("uri", p.getUri().toString());
		productJson.addProperty("name", p.getName());
		System.out.println("proba ovde " + p.getName());
		if (p.getUrl() != null) {
			productJson.addProperty("url", p.getUrl().toString());
		}
		if (p.getImage() != null)
			productJson.addProperty("image", p.getImage().toString());
		productJson.addProperty("model", p.getModel());
		productJson.addProperty("description", p.getDescription());
		productJson.addProperty("productID", p.getProductID());
		if (!p.getReviews().isEmpty())
			productJson.addProperty("reviews", p.getUri().toString()
					+ "/reviews");
		if (p.getAggregateRating() != null) {
			productJson.add("aggregateRating",
					serializeAggregateRating(p.getAggregateRating()));
		}
		if (p.getBrand() != null) {
			productJson.add("brand", serializeBrand(p.getBrand()));
		}
		if (p.getOffers() != null) {
			productJson.add("offers", serializeOffers(p.getOffers()));
		}
		return productJson;
	}

	private static JsonElement serializeOffers(Offer of) {
		JsonObject json = new JsonObject();
		json.addProperty("price", of.getPrice());
		json.addProperty("availability", of.getAvailability());
		json.addProperty("priceCurrency", of.getPriceCurrency());
		json.addProperty("itemCondition", of.getItemCondition());
		// System.out.println("proba2 "+ of.getItemCondition());
		if (of.getSeller() != null) {
			json.add("seller", serializeSeller(of.getSeller()));
		}
		return json;
	}

	private static JsonElement serializeSeller(Organization or) {
		JsonObject json = new JsonObject();
		if (or.getName() != null) {
			json.addProperty("name", or.getName());
		}
		return json;
	}

	private static JsonElement serializeBrand(Brand b) {
		JsonObject json = new JsonObject();
		if (b.getName() != null) {
			json.addProperty("name", b.getName());
		}
		return json;
	}

	public static JsonObject serializeReview(Collection<Review> rev, String id) {
		JsonObject reviewsJson = new JsonObject();

		reviewsJson.addProperty("uri", Constants.NS + id + "/reviews");
		reviewsJson.addProperty("product", Constants.NS + id);
		JsonArray reviews = new JsonArray();
		for (Review r : rev) {
			JsonObject json = new JsonObject();
			json.addProperty("name", r.getName());
			json.addProperty("author", r.getAuthor());
			json.addProperty("datePublished", r.getDatePublished().toString());
			json.addProperty("description", r.getDescription());
			if (r.getReviewRating() != null)
				json.add("reviewRating",
						serializeReviewRating(r.getReviewRating()));
			reviews.add(json);

		}
		reviewsJson.add("reviews", reviews);

		return reviewsJson;
	}

	public static JsonObject serializeReviewRating(Rating r) {
		JsonObject jsons = new JsonObject();

		jsons.addProperty("bestRating", r.getBestRating());
		jsons.addProperty("ratingValue", r.getRatingValue());
		jsons.addProperty("worstRating", r.getWorstRating());
		return jsons;
	}

	public static JsonObject serializeAggregateRating(AggregateRating ar) {
		JsonObject json = new JsonObject();

		json.addProperty("bestRating", ar.getBestRating());
		json.addProperty("ratingValue", ar.getRatingValue());
		json.addProperty("worstRating", ar.getWorstRating());
		json.addProperty("reviewCount", ar.getReviewCount());

		return json;
	}

}
