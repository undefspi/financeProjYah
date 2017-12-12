package finance.quoteservice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FinanceServiceConnection {
	private String serviceResponse = "{}";
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getHttpResponse(String fsUrl) throws IOException{
		
		final URL url = new URL(fsUrl);
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");
		
		InputStreamReader in = new InputStreamReader(con.getInputStream());
		BufferedReader br = new BufferedReader(in);
		
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = br.readLine()) != null) {
		    content.append(inputLine);
		}
		
		return content.toString();	
	}
	
	public void sendRequest(String url, String[] params){
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		for (int i = 0; i < params.length;i++){
			sb.append(params[i]);
			sb.append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		final String quoteUrl = sb.toString();
		String jsonResponse = "";
		try {
			jsonResponse = getHttpResponse(quoteUrl);
		} catch (IOException e) {
			logger.debug("Failed to retrieve response from provided url",e.getMessage());
		}
		serviceResponse = jsonResponse;	
	}
	
	public String getServiceResponse(){
		return this.serviceResponse;
	}
	
	public void setServiceResponse(String jsonDocString){
		this.serviceResponse = jsonDocString;
	}
	
}