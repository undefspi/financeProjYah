package finance.quoteservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class FinanceServiceConnection {
	
	final static String getHttpResponse(String fsUrl) throws IOException{
		
		final URL url = new URL(fsUrl);
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");
		
		InputStreamReader in = new InputStreamReader(con.getInputStream());
		BufferedReader br = new BufferedReader(in);
		
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
		    content.append(inputLine);
		}
		
		return content.toString();	
	}
}
