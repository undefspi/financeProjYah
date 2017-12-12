package finance.testforgefx;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import finance.quotes.ForgeFxQuote;
import finance.quotes.Quote;
import finance.quoteservice.FinanceConfig;
import finance.quoteservice.FinanceServiceConnection;
import finance.forge.ForgeService;


public class TestForgeServices {
	private String quoteResponse = "{}";
	private static final String FSURL = "https://forex.1forge.com/1.0.2/";
	private static final String PAIRS = "USDGBP";
	private FinanceConfig fc;
	private String apiKey; 
	
	
	@Before
	public void makeFxCalls(){
		this.fc = new FinanceConfig("financeService.properties");
		this.apiKey = fc.getProperty("forge.apikey");
		
		FinanceServiceConnection fsConn = new FinanceServiceConnection();
		String[] params = {"quotes?","api_key="+ apiKey, "pairs=" + PAIRS};
		fsConn.sendRequest(FSURL, params);
		quoteResponse = fsConn.getServiceResponse();
	}
	
	@Test
	public void TestMappingToPojoFromQuoteResposne(){
		String pairs = "GBPUSD";
		String response = "[{\"symbol\":\"GBPUDD\",\"price\":1.32372,\"bid\":1.32371,\"ask\":1.32372,\"timestamp\":1511207001}]";
		ForgeService fs = Mockito.spy(new ForgeService());
		Mockito.doReturn(response).when(fs).sendForgeRequest(Mockito.any(String[].class));
		List<Quote> quotes = fs.getFxQuotes(Mockito.anyString());
		ForgeFxQuote fq = (ForgeFxQuote) quotes.get(0);
		assertThat(fq.getAsk(), equalTo(1.32372f));
	}
	
	@Test
	public void TestExternalMappingCallWorks(){
		ForgeService fs = Mockito.spy(new ForgeService());
		Mockito.doReturn(quoteResponse).when(fs).sendForgeRequest(Mockito.any(String[].class));
		List<Quote> quotes = fs.getFxQuotes(Mockito.anyString());
		assertThat(quotes.isEmpty(), is(false));
	}
}