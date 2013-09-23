package domain;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

@Namespace(Constants.SCHEMA)
@RdfType("Offer")
public class Offer extends Thing {
	
	@RdfProperty(Constants.SCHEMA + "availability")
	private String availability;
	
	@RdfProperty(Constants.SCHEMA + "itemCondition")
	private String itemCondition;
	
	@RdfProperty(Constants.SCHEMA + "priceCurrency")
	private String priceCurrency;
	
	@RdfProperty(Constants.SCHEMA + "price")
	private double price;
	
	@RdfProperty(Constants.SCHEMA + "seller")
	private Organization seller;

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public String getPriceCurrency() {
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency) {
		this.priceCurrency = priceCurrency;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Organization getSeller() {
		return seller;
	}

	public void setSeller(Organization seller) {
		this.seller = seller;
	}
	
	

}
