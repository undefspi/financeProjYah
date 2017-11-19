package financereport.main;


import finance.quoteservice.FinanceService;
import finance.quoteservice.ForgeService;

public class Application {
	public static void main(String[] args) {
		System.out.println("Loading Finance Application");
		FinanceService fs = new ForgeService();
		fs.getFXQuotes("EURUSD");
	}
}
