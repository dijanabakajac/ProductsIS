package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

@Namespace(Constants.SCHEMA)
@RdfType("CreativeWork")
public class CreativeWork extends Thing{
	@RdfProperty(Constants.SCHEMA + "author")
	private String author;
	
	@RdfProperty(Constants.SCHEMA + "datePublished")
	private Date datePublished;
	
	@RdfProperty(Constants.SCHEMA + "aggregateRating")
	private AggregateRating aggregateRating;
	
	@RdfProperty(Constants.SCHEMA + "offers")
	private Offer offers;
	
	public Offer getOffers() {
		return offers;
	}

	public void setOffers(Offer offers) {
		this.offers = offers;
	}

	@RdfProperty(Constants.SCHEMA + "reviews")
	private Collection<Review> reviews;
	
	public CreativeWork() {
		reviews = new ArrayList<Review>();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public AggregateRating getAggregateRating() {
		return aggregateRating;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		this.aggregateRating = aggregateRating;
	}

	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
}
