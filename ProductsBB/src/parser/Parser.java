package parser;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hp.hpl.jena.sparql.function.library.substring;


import persistance.DataModelManager;
import util.URIGenerator;

import domain.AggregateRating;
import domain.Brand;

import domain.Offer;
import domain.Organization;
import domain.Product;
import domain.Rating;

import domain.Review;

public class Parser {

	private static Product getBasicProductInformation(Element product)
			throws Exception {
		Product prod = new Product();
		System.out.println("Podaci o proizvodu:");
		String url = product.select("meta[itemprop=url]").attr("content");
		if (!url.isEmpty()) {
			prod.setUrl(new URI(url));
		}
		String name = product.select("meta[itemprop=name]").attr("content");
		prod.setName(name);
		System.out.println("Ime proizvoda: " + name);
		String image = product.select("img[itemprop=image]").attr("src");
		if (!image.isEmpty()) {
			prod.setImage(new URI(image));

		}
		String description = product.select("div[itemprop=description]").text();
		prod.setDescription(description);
		System.out.println("Opis proizvoda: " + description);

		String productID = product.select("span[itemprop=productID]").text();
		prod.setProductID(productID);
		System.out.println("ID proizvoda: " + productID);

		String model = product.select("span[itemprop=model]").text();
		prod.setModel(model);
		System.out.println("Model proizvoda: " + model);
System.out.println("Proba");
		return prod;

	}

	private static AggregateRating parseAggregateRating(Element aggregateRating)
			throws Exception {
		AggregateRating aggRat = new AggregateRating();

		String ratingValue = aggregateRating.select(
				"span[itemprop=ratingValue]").text();
		double vrednost = 0;
		try {
			vrednost = Double.parseDouble(ratingValue);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}

		aggRat.setRatingValue(vrednost);

		System.out.println("Rating value je: " + ratingValue);

		int vrednost2 = 0;
		String reviewCount = aggregateRating.select(
				"meta[itemprop=reviewCount]").attr("content");
		try {
			vrednost2 = Integer.parseInt(reviewCount);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}
		aggRat.setReviewCount(vrednost2);

		System.out.println("Review count: " + reviewCount);

		int vrednost3 = 0;
		String bestRating = aggregateRating.select("meta[itemprop=bestRating]")
				.attr("content");
		try {
			vrednost3 = Integer.parseInt(bestRating);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}

		aggRat.setBestRating(vrednost3);

		System.out.println("Best rating kod agregata je: " + bestRating);

		int vrednost4 = 0;
		String worstRating = aggregateRating.select(
				"meta[itemprop=worstRating]").attr("content");
		try {
			vrednost4 = Integer.parseInt(worstRating);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}

		aggRat.setWorstRating(vrednost4);

		System.out.println("Worst rating je: " + worstRating);

		aggRat.setUri(URIGenerator.generate(aggRat));

		 DataModelManager.getInstance().save(aggRat);

		return aggRat;
	}

	private static Rating parseRating(Element reviewRating) throws Exception {
		Rating rat = new Rating();

		int br = 0;
		String bestRating = reviewRating.select("meta[itemprop=bestRating]")
				.attr("content");
		try {
			br = Integer.parseInt(bestRating);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}

		rat.setBestRating(br);

		System.out.println("Best rating kod ratinga je: " + bestRating);

		int br2 = 0;
		String worstRating = reviewRating.select("meta[itemprop=worstRating]")
				.attr("content");
		try {
			br2 = Integer.parseInt(worstRating);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}
		rat.setWorstRating(br2);

		System.out.println("Worst rating kod ratinga je: " + worstRating);

		double br3 = 0;
		String ratingValue = reviewRating.select("span[itemprop=ratingValue]")
				.text();
		try {
			br3 = Double.parseDouble(ratingValue);
		} catch (Exception e) {
			System.out.println("Nije definisana vrednost za ovaj proizvod.");
		}

		rat.setRatingValue(br3);

		System.out.println("Rating value kod ratinga je: " + ratingValue);
		
       rat.setUri(URIGenerator.generate(rat));
		
		DataModelManager.getInstance().save(rat);
		return rat;
	}

	private static Collection<Review> parseReviews(Elements productReviews)
			throws Exception {
		Collection<Review> reviews = new ArrayList<Review>();

		for (Element r : productReviews) {
			Review rev = new Review();

			String name = r.select("span[itemprop=name]").text();
			rev.setName(name);

			System.out.println("Ime review-a je: " + name);

			String author = r.select("span[itemprop=author]").text();
			rev.setAuthor(author);
			System.out.println("Autor je " + author);

			String datePublished = r.select("meta[itemprop=datePublished]")
					.attr("content");
			System.out.println("Datum parsiran " + datePublished);
			rev.setDatePublished(new SimpleDateFormat("yyyy-MM-dd")
					.parse(datePublished));

			String description = r.select("span[itemprop=description]").text();
			rev.setDescription(description);

			System.out.println("Opis reviewa  je: " + description);

			Element reviewRating = r
					.select("div[itemprop=reviewRating][itemscope][itemtype=http://schema.org/Rating]")
					.first();
			if (reviewRating != null) {
				Rating rat = parseRating(reviewRating);
				rev.setReviewRating(rat);
			}

			reviews.add(rev);
			rev.setUri(URIGenerator.generate(rev));

		DataModelManager.getInstance().save(rev);

		}

		return reviews;
	}

	private static Brand parseBrand(Element brand) throws Exception {
		Brand b = new Brand();

		String name = brand.select("meta[itemprop=name]").attr("content");
		b.setName(name);
		System.out.println("Ime brenda je: " + name);
		b.setUri(URIGenerator.generate(b));

		DataModelManager.getInstance().save(b);

		return b;
	}

	private static Organization parseOrganization(Element seller)
			throws Exception {
		Organization o = new Organization();
		String name = seller.select("meta[itemprop=name]").attr("content");
		o.setName(name);
		System.out.println("Ime sellera je: " + name);
		o.setUri(URIGenerator.generate(o));

		DataModelManager.getInstance().save(o);

		return o;
	}

	private static Offer parseOffer(Element offers) throws Exception {
		Offer of = new Offer();
		String price = offers.select("meta[itemprop=price]").attr("content");
		if(!price.isEmpty()){
			 String dd = price.replace("$", " ");
	            double pr = Double.parseDouble(dd.trim());
		of.setPrice(pr);
		System.out.println("Cena je: " + pr);
		}

		

		String availability = offers.select("meta[itemprop=availability]")
				.attr("content");
		of.setAvailability(availability);

		System.out.println("Dostupnost: " + availability);

		String itemCondition = offers.select("meta[itemprop=itemCondition]")
				.attr("content");
		of.setItemCondition(itemCondition);

		System.out.println("Stanje proizvoda je: " + itemCondition);

		String priceCurrency = offers.select("meta[itemprop=priceCurrency]")
				.attr("content");
		of.setPriceCurrency(priceCurrency);

		System.out.println("Valuta: " + priceCurrency);

		Element seller = offers
				.select("div[itemscope][itemtype=http://schema.org/Organization][itemprop=seller]")
				.first();
		if (seller != null) {
			Organization rat = parseOrganization(seller);
			of.setSeller(rat);
		}
		of.setUri(URIGenerator.generate(of));

		DataModelManager.getInstance().save(of);

		return of;
	}

	public static Product parseProduct(String productUrl) throws Exception {
		Document doc = Jsoup.connect(productUrl).get();

		Element product = doc.select(
				"div[itemscope][itemtype=http://schema.org/Product]").first();
		
		
			Product prod = getBasicProductInformation(product);

			Element aggregateRating = product
					.select("li[itemprop=aggregateRating][itemscope][itemtype=http://schema.org/AggregateRating]")
					.first();
			if (aggregateRating != null) {
				AggregateRating aggRat = parseAggregateRating(aggregateRating);
				prod.setAggregateRating(aggRat);
			}

			Elements reviewsProduct = product
					.select("div[itemprop=reviews][itemscope][itemtype=http://schema.org/Review]");
			if (reviewsProduct != null) {
				Collection<Review> reviews = parseReviews(reviewsProduct);
				prod.setReviews(reviews);

			}

			Element brand = product
					.select("div[itemprop=brand][itemscope][itemtype=http://schema.org/Brand]")
					.first();
			if (brand != null) {
				Brand br = parseBrand(brand);
				prod.setBrand(br);
			}

			Element offers = product
					.select("div[itemprop=offers][itemscope][itemtype=http://schema.org/Offer]")
					.first();
			if (offers != null) {
				Offer off = parseOffer(offers);
				prod.setOffers(off);
			}
		prod.setUri(URIGenerator.generate(prod));

		DataModelManager.getInstance().save(prod);
		return prod;
		
		

	}

	public static void parseBestBuy(String productsurl)
			throws Exception {
		
		Document doc = Jsoup.connect(productsurl).userAgent("Mozilla").get();
		Collection<Product> products = new LinkedList<Product>();
		//ThreadPoolExecutorProducts tpep=new ThreadPoolExecutorProducts();

		Element div = doc.select("div#listView").first();

		Elements el = div.select("div.hproduct");
		for (Element element : el) {
			String url = element.select("a").attr("href");
			String str = "http://www.bestbuy.com" + url;
			
			System.out.println("URL Proizvoda " + str);
		
		//	ParserWorker pw = new ParserWorker(str);
		//	tpep.runTask(pw);
			Product pp=parseProduct(str);
			products.add(pp);
		}
		//tpep.shutDown();
		//return products;
	}
}