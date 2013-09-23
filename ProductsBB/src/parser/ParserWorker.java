package parser;

public class ParserWorker extends Thread {
	
	private String url;

	public ParserWorker(String url) {
		this.url = url;
	}
	
	@Override
	public void run() {
		try {
			Parser.parseProduct(url);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
