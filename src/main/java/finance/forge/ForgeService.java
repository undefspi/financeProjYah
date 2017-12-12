package finance.forge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import finance.quotes.ForgeFxQuote;
import finance.quotes.Quote;
import finance.quoteservice.FinanceConfig;
import finance.quoteservice.FinanceService;
import finance.quoteservice.FinanceServiceConnection;

public class ForgeService implements FinanceService {
	 private final Logger logger = LoggerFactory.getLogger(ForgeService.class);
	
	 private static final String FSURL = "https://forex.1forge.com/1.0.2/";
	 private final String apiKey;
	 private final FinanceConfig fc;
	 private final FinanceServiceConnection fsConn;
	 
	 public ForgeService(){
		super();
		this.fc = new FinanceConfig("financeService.properties");
		this.apiKey = fc.getProperty("forge.apikey");
		fsConn = new FinanceServiceConnection();
	 }
	 
	//So the tests can be run with Mockito and the response can be mocked when this part is called
	//this needs to be public.  Would prefer to make this private- answers on a postcard
	public String sendForgeRequest(String[] params){
		 	fsConn.sendRequest(FSURL, params);
		 	return fsConn.getServiceResponse();
	}
	 
	public List<Quote> getFxQuotes(String pairs) {	
		String[] params = {"quotes?","api_key="+ apiKey, "pairs="+ pairs};	
		List<ForgeFxQuote> quotesQ = new ArrayList<ForgeFxQuote>();
		List<Quote> quotes = new ArrayList<Quote>();
			
		ObjectMapper mapper = new ObjectMapper();
		try {
			quotesQ  = Arrays.asList(mapper.readValue(sendForgeRequest(params), ForgeFxQuote[].class));	
		} catch (JsonParseException e) {
			logger.error("Failed to parse json from service request", e.getMessage());
		} catch (JsonMappingException e) {
			logger.error("Failed to map whatever we got back from service request", e.getMessage());
		} catch (IOException e) {
			logger.error("Failed to even call service request", e.getMessage());
		}
		
		//in order to cast the quotes in line with the interface loop it round
		//apparently it is bad practice to make a method return <? extends Quote>
		//which would have solved this.
		for (Iterator iterator = quotesQ.iterator(); iterator.hasNext();) {
			logger.info("running through cast loop");
			Quote forgeFxQuote = (ForgeFxQuote) iterator.next();
			quotes.add(forgeFxQuote);
		}
		
		return quotes;
	}
}
