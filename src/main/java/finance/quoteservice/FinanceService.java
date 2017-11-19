package finance.quoteservice;

import java.util.List;

import finance.quotes.ForgeFxQuote;
import finance.quotes.Quote;

public interface FinanceService {
	static final String FINANCEPROPSFILENAME = "financeService.properties";
	
	public String issueQuoteServiceRequest(String pairs);
	public List<? extends Quote> getFXQuotes(String serviceResponse);
}
