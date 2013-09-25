package main;

import java.io.FileNotFoundException;
import java.util.Collection;

import com.hp.hpl.jena.tdb.TDB;

import domain.Product;

import parser.Parser;
import persistance.DataModelManager;
import rest.ProductRestService;
import services.QueryService;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
	//Parser.parseBestBuy("http://www.bestbuy.com/site/Guitars-Basses/Acoustic-Guitars/pcmcat151600050003.c?id=pcmcat151600050003");
	//DataModelManager.getInstance().write("Products.rdf", "TURTLE");
	//TDB.sync(DataModelManager.getInstance().getDataset());
			
			//QueryService qs = new QueryService();
		//ProductRestService qs=new ProductRestService();
		//String col=qs.getProducts("6", "", "", "", "", "", "");
		//System.out.println(col);
		//for(Product pr:col){
				
				//System.out.println(pr.getName());
			//}
	//	qs.getProducts("", "", "", true, 0, 0, 0);
       //String ss=qs.getProductReveiws("53467111-b8e8-4f1c-803a-d5c63a89d75d");
		//System.out.println(ss);	
	//	System.out.println(s);
		//DataModelManager.getInstance().closeDataModel();
		//System.out.println("Uspesno ste parsirali proizvode!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
