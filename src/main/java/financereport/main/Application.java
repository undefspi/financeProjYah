package financereport.main;


import java.util.List;

import finance.forge.ForgeService;
import finance.quotes.Quote;
import finance.quoteservice.FinanceService;

public class Application {
	public static void main(String[] args) {
		System.out.println("Loading Finance Application");
		FinanceService fs = new ForgeService();
		List<Quote> quotes = fs.getFxQuotes("EURUSD");
	}
}
