package rest;

import java.util.Collection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import persistance.DataModelManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import domain.Product;
import com.hp.hpl.jena.tdb.TDB;
import domain.Review;

import rest.parser.ProductJSONParser;

import services.ProductServiceImplementation;

@Path("/products")
public class ProductRestService {

	private ProductServiceImplementation productRepository;

	public ProductRestService() {

		productRepository = new ProductServiceImplementation();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getProducts(
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("model") String model,
			@DefaultValue("") @QueryParam("brandName") String brandName,
			@DefaultValue("true") @QueryParam("withImage") String withImage,
			@DefaultValue("") @QueryParam("minAggregateRating") String minAggregateRating,
			@DefaultValue("") @QueryParam("minPrice") String minPrice,
			@DefaultValue("") @QueryParam("maxPrice") String maxPrice) {

		double agg;
		double minpr;
		double maxpr;
		boolean withim;
		try {
			agg = Double.parseDouble(minAggregateRating);
		} catch (Exception e) {
			agg = 0;
		}
		try {
			minpr = Double.parseDouble(minPrice);
		} catch (Exception e) {
			minpr = 0;
		}
		try {
			maxpr = Double.parseDouble(maxPrice);
		} catch (Exception e) {
			maxpr = 0;
		}
		try {
			withim = Boolean.parseBoolean(withImage);
		} catch (Exception e) {
			withim = true;
		}

		Collection<Product> products = productRepository.getProducts(name,
				model, brandName, withim, agg, minpr, maxpr);

		if (products != null && !products.isEmpty()) {
			JsonArray productArray = new JsonArray();

			for (Product p : products) {
				JsonObject productJson = ProductJSONParser.serialize(p);
				productArray.add(productJson);
			}
			TDB.sync(DataModelManager.getInstance().getModel());

			return productArray.toString();
		}
		throw new WebApplicationException(Response.Status.NO_CONTENT);
	}

	@GET
	@Path("{id}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductReveiws(@PathParam("id") String id) {
		Collection<Review> reviews = productRepository.getReviewsForProduct(id);

		if (reviews != null && !reviews.isEmpty()) {
			JsonObject reviewsJson = ProductJSONParser.serializeReview(reviews,
					id);
			return reviewsJson.toString();
		}

		throw new WebApplicationException(Response.Status.NO_CONTENT);
	}

}
