package Register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;


public class Callapi {

	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	static final String URL = "https://aip.baidubce.com/rpc/2.0/nlp/v1/lexer?access_token=24.d3088c0c37c743560864a1efe8304967.2592000.1513517470.282335-10385098";

	
	public static void post(String completeUrl, String JSONbody) {
		try {
			//JSONbody = new String("{\"text\":\"" + java.net.URLEncoder.encode(JSONbody, "GBK") + "\"}");
			JSONbody = java.net.URLEncoder.encode("{\"text\":\"" + JSONbody + "\"}", "GBK");
			System.out.println(JSONbody);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    HttpClient httpClient = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(completeUrl);
	    httpPost.setHeader("Content-type", "application/json");
	    try {
	        StringEntity stringEntity = new StringEntity(JSONbody);
	        //stringEntity.setContentEncoding("GBK");
	        httpPost.getRequestLine();
	        httpPost.setEntity(stringEntity);
	        

	        HttpResponse response = httpClient.execute(httpPost);
	        
	        HttpEntity entity = response.getEntity();
	 
	        
			String result = "";
			if (entity != null) {
			    InputStream instream;
				instream = entity.getContent();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(instream));
				
	            String line;
	            if ((line = in.readLine()) != null) {
	            	System.out.println(line);
	            	Pattern pattern = Pattern.compile("\"text\": \"([\\d\\w%]*)\"");
	 	            Matcher m = pattern.matcher(line);
	 	            if(m.find()){
	 	            	String t = m.group(1);
	 	            	System.out.println(t);
	 	            	System.out.println(java.net.URLDecoder.decode(t, "GBK"));
	 	            }else{
	 	            	System.out.println("Not Found");
	 	            }
	 	            
	 	            
	 	            
	 	            
	            }
	           
	            
	            //result = java.net.URLDecoder.decode(result, "utf-8");
			}else{
				System.out.println("empty entity!");
			}
	        
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		String text = "���Ǻ��ˡ�";
		
		//try {
		//	text = java.net.URLEncoder.encode(text, "GBK");  
		//} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		String access_token = "24.8920f1f26f329afb3bc174e507355b72.2592000.1513583398.282335-10385098";
		String JSON = "";
		
		//JSON = new String("{\"text\":\"" + text + "\"}");
		//try {
			//System.out.println(java.net.URLDecoder.decode(JSON, "GBK"));
			//System.out.println(java.net.URLDecoder.decode("%CE%D2%CA%C7%BA%C3%C8%CB%A1%A3", "GBK"));
		//} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		String url = "https://aip.baidubce.com/rpc/2.0/nlp/v2/dnnlm_cn?access_token=";
		
		Callapi.post(url + access_token, text);
	}

}
