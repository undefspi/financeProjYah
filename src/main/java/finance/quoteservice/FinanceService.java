package finance.quoteservice;

import java.util.List;

import finance.quotes.ForgeFxQuote;
import finance.quotes.Quote;

public interface FinanceService {
	
	
	public List<Quote> getFxQuotes(String pairs);
}
