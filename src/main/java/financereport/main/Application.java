package financereport.main;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import finance.forge.ForgeService;
import finance.quotes.Quote;
import finance.quoteservice.FinanceService;

public class Application {
	static final Logger logger = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		
		FinanceService fs = new ForgeService();
		List<Quote> quotes = fs.getFxQuotes("EURUSD");
	}
}
