package finance.quoteservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class FinanceConfig {
	private static final Logger logger = LoggerFactory.getLogger(FinanceConfig.class);
	private final Properties props;
	
	public FinanceConfig(String propsFilePath){
		props = new Properties();
		setProperties(propsFilePath);
	}
	
	private void setProperties(String propName){
		final InputStream is = ClassLoader.getSystemResourceAsStream(propName);
		 try{
			 props.load(is);
		 }catch(IOException e){
			 logger.error("Failed to load properties" + e.getMessage());
		 }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                	logger.error("Failed to close inputstream",e.getMessage());
                }
             }
	      }
	}
	public String getProperty(String name){
		return this.props.getProperty(name);
	}
}
