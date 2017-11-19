package finance.quoteservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import finance.quotes.ForgeFxQuote;
import finance.quotes.Quote;

public class ForgeService implements FinanceService {
	 static final String FSURL = "https://forex.1forge.com/1.0.2/";
	 private String APIKEY;
	 private final Properties props;
	 private String serviceResponse = "{}";
	 
	 public ForgeService(){
		super();
		this.props = loadProperties(FINANCEPROPSFILENAME);
		this.APIKEY = props.getProperty("forge.apikey");
	 }
	 
	 private Properties loadProperties(String propName){
		 final InputStream is = ClassLoader.getSystemResourceAsStream(propName);
		 final Properties props = new Properties();
		 try{
			 props.load(is);
		 }catch(IOException e){
			 System.out.println("Failed to load properties" + e.getMessage());
		 }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException dontCare) {}
             }
	      }
		  return props;
	 }

	public String issueQuoteServiceRequest(String pairs){
		final String quoteUrl = String.format("%squotes?pairs=%s&api_key=%s",FSURL,pairs,APIKEY);
		String jsonResponse = "";
		try {
			jsonResponse = FinanceServiceConnection.getHttpResponse(quoteUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonResponse;
	}
	
	
	public List<? extends Quote> getFXQuotes(String serviceResponse) {
		ObjectMapper mapper = new ObjectMapper();
		List<? extends Quote> quotesQ = new ArrayList<ForgeFxQuote>();
		try {
			quotesQ  = Arrays.asList(mapper.readValue(serviceResponse, ForgeFxQuote[].class));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  quotesQ;
	}
}
