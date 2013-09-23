package services;

import java.util.ArrayList;
import java.util.Collection;

import persistance.DataModelManager;
import persistance.QueryExecutor;
import util.Constants;
import domain.Product;

public class QueryService {
	private QueryExecutor queryExecutor = new QueryExecutor();

	public Collection<Product> getProducts(String name, String model,
			String brandName, boolean withImage, double minAggregateRating,
			double minPrice, double maxPrice) {

		Collection<Product> products = new ArrayList<Product>();

		String where = " ?product a schema:Product. ";
		String filter = "";

		if (!name.isEmpty()) {
			where += "?product schema:name ?name. ";
			filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
		}
		if (!model.isEmpty()) {
			where += "?product schema:model ?model. ";
			filter += "FILTER regex( ?model, \"" + model + "\", \"i\" ) ";
		}
		if (!brandName.isEmpty()) {
			where += "?product schema:brand ?brand. "
					+ "?brand schema:name ?name. ";
			filter += "FILTER regex( ?name, \"" + brandName + "\", \"i\" ) ";
		}
		if (withImage) {
			where += "?product schema:image ?image. ";
		}

		if (minAggregateRating!=0) {
			where += "?product schema:aggregateRating ?aggregateRating. "
					+ "?aggregateRating schema:ratingValue ?ratingValue. ";
			filter += "FILTER ( ?ratingValue >=" + minAggregateRating + " ) ";
		}
		if (minPrice != 0) {
			
			where += "?product schema:offers ?offers. "
					+ "?offers schema:price ?price. ";
			filter += "FILTER( ?price >=" + minPrice + " ) ";
			}
		
		if (maxPrice !=0) {
			where += "?product schema:offers ?offers. "
					+ "?offers schema:price ?price. ";
			filter += "FILTER ( ?price <=" + maxPrice + " ) ";
		}
		
		
		
		String query = "PREFIX products: <" + Constants.NS + "> "
				+ "PREFIX schema: <" + Constants.SCHEMA + "> " +
				 "PREFIX xsd: <" + Constants.XSD + "> " +
				"SELECT ?product " + "WHERE { " + where + filter + " } ";

		Collection<String> productUris = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "product",
						DataModelManager.getInstance().getModel());

		for (String string : productUris) {
			Product product = getProduct(string);
			products.add(product);
		}

		return products;
	}

	public Product getProduct(String uri) {
		Product product = queryExecutor.getProduct(uri);
		return product;
	}
}
