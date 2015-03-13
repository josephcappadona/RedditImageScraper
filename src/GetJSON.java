import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetJSON {

	public static Object getJSONObjectAtURL (String urlPath) {
		URL url;
		HttpURLConnection connection = null;
		InputStream is = null;
		JSONParser parser = new JSONParser();

		try {
			System.setProperty("http.agent", "Test by user EulersPhi");
			url = new URL(urlPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "tests by EulersPhi");
			connection.setRequestMethod("GET");
			connection.connect();
			is = connection.getInputStream();
			BufferedReader theReader =
				new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String reply;
			while ((reply = theReader.readLine()) != null) {
				Object obj = parser.parse(reply);
				try {
					JSONObject jsonObject =
							new JSONObject((org.json.simple.JSONObject) obj);
					return jsonObject;
				} catch (ClassCastException e) {
					try {
						JSONArray jsonArray =
								new JSONArray((org.json.simple.JSONArray) obj);
							return jsonArray;
					} catch (ClassCastException ex) {
						System.out.println("Invalid object returned.");
						return null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void printJSONAtURL(String url, int n) {
		try {
			Object obj = GetJSON.getJSONObjectAtURL(url);
			if(obj instanceof JSONObject) {
				System.out.println(((JSONObject) obj).toString(n));
			} else if (obj instanceof JSONArray) {
				System.out.println(((JSONArray) obj).toString(n));
			} else {
				System.out.println("JSON returned null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Date d = new Date();
		System.out.println(d.getTime());
		String subreddit = "gifs";
		String postType = "new";
		String lastID = "";
		boolean shouldBreak = false;
		long thresholdDate = 1420339483;
		ArrayList<Map<String,String>> pics = new ArrayList<Map<String,String>>();
		
		
		while (true) {
			String url = "http://www.reddit.com/r/" + subreddit + "/" + postType + ".json?limit=100&after=" + lastID;
			System.out.println(url);
			JSONObject json = (JSONObject) GetJSON.getJSONObjectAtURL(url);
			try {
				JSONArray b = (JSONArray) json.getJSONObject("data").get("children");
				lastID = json.getJSONObject("data").get("after").toString();
				
				for(int i=0; i<b.length(); i++) {
					Map<String,String> prop = new HashMap<String,String>();
					//get url
					prop.put("url", b.getJSONObject(i).getJSONObject("data").get("url").toString());
					//get time
					String timeString = b.getJSONObject(i).getJSONObject("data").get("created_utc").toString();
					long time = (long) (Double.valueOf(timeString.substring(0, timeString.length()-2))*1000000000);
					prop.put("time", String.valueOf(time));
					//get domain
					prop.put("domain", b.getJSONObject(i).getJSONObject("data").get("domain").toString());
					//get id
					prop.put("id", b.getJSONObject(i).getJSONObject("data").get("id").toString());
					
					if(time < thresholdDate) {
						System.out.println(prop.get("time") + " quit");
						shouldBreak = true;
						break;
					}
					System.out.println(prop.get("time") + "  " + prop.get("url"));
					
					pics.add(prop);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(shouldBreak)
				break;
		}
		System.out.println("-----------------");
		InputStream in = null;
		ByteArrayOutputStream out = null;
		for(int i=0; i<pics.size(); i++) {
			//System.out.println(pics.get(i).get("url"));
			System.out.println(pics.get(i));
			URL url;
			try {
				String urlString = pics.get(i).get("url");
				if(pics.get(i).get("domain").equals("imgur.com")
						&& !pics.get(i).get("url").contains("/a/")
						&& !pics.get(i).get("url").contains("/gallery/")) {
					urlString += ".png";
				}
				url = new URL(urlString);
				System.out.println(urlString);
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
			    String type = fileNameMap.getContentTypeFor(urlString);
			    System.out.println(type);
				in = new BufferedInputStream(url.openStream());
				out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1!=(n=in.read(buf)))
				{
				   out.write(buf, 0, n);
				}

				byte[] response = out.toByteArray();
				//And you may then want to save the image so do:

				String filename =  pics.get(i).get("id");
				FileOutputStream fos = new FileOutputStream(filename + ".png");
				fos.write(response);
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					out.close();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
