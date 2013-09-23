package domain;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;
import util.Constants;

@Namespace(Constants.SCHEMA)
@RdfType("Product")
public class Product extends CreativeWork {
	
@RdfProperty(Constants.SCHEMA + "model")
private String model;

@RdfProperty(Constants.SCHEMA + "productID")
private String productID;

@RdfProperty(Constants.SCHEMA + "brand")
private Brand brand;

public String getModel() {
	return model;
}

public void setModel(String model) {
	this.model = model;
}

public String getProductID() {
	return productID;
}

public void setProductID(String productID) {
	this.productID = productID;
}

public Brand getBrand() {
	return brand;
}

public void setBrand(Brand brand) {
	this.brand = brand;
}
	
	

}
