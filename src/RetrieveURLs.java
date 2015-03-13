import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RetrieveURLs {

	/**
	 * Scans the specified subreddit for links according
	 * to the postType and corresponding parameter. These
	 * are as follows: <p>
	 * hot --> param = # of posts (type Integer) <p>
	 * new --> param = date of earliest post (type Calendar) <p>
	 * rising --> param = # of posts (type Integer) <p>
	 * controversial --> param = # of posts (type Integer) <p>
	 * top --> param = # of posts (type Integer)
	 * 
	 * @param subreddit for example, /r/pics,
	 * 					 /r/gifs, or /r/EarthPorn
	 * @param postType see above
	 * @param param see above
	 * @return ArrayList<Map<String,String>> of URLs
	 * @see java.util.Calendar
	 */
	public static ArrayList<Map<String,String>> getRedditLinks(String subreddit,
			String postType, Object param) {
		//throw illegal argument exception if param doesn't correspond correctly
		switch(postType) {
		case "hot":
		case "rising":
		case "controversial":
		case "top":
			return getLinksUntilLimit(subreddit, postType, param);
		case "new":
			return getLinksAfterTime(subreddit, postType, param);
		default:
			throw new IllegalArgumentException();
		}
	}
	
	private static Map<String,String> getPostData(JSONObject o) {
		Map<String,String> data = new HashMap<String,String>();
		
		try {
			//get url
			data.put("url", o.get("url").toString());

			//get time
			String timeString = o.get("created_utc").toString();
			long time = (long) (Double.valueOf(timeString.substring(0, timeString.length()-2))*1000000000);
			data.put("time", String.valueOf(time));
			
			//get domain
			data.put("domain", o.get("domain").toString());
			
			//get id
			data.put("id", o.get("id").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private static ArrayList<Map<String,String>> getLinksUntilLimit(
			String subreddit, String postType, Object param) {
		if (!(param instanceof Integer))
			throw new IllegalArgumentException();
		int limit = (int) param;
		
		int count = 0;
		String lastID = "";
		boolean shouldBreak = false;
		ArrayList<Map<String,String>> pics = new ArrayList<Map<String,String>>();
		
		while (true) {
			String url = "http://www.reddit.com/r/" + subreddit + "/" + postType + ".json?limit=100&after=" + lastID;
			JSONObject json = (JSONObject) GetJSON.getJSONObjectAtURL(url);
			try {
				JSONObject data = json.getJSONObject("data");
				JSONArray b = (JSONArray) data.get("children");
				lastID = data.getString("after");
				System.out.println(url);
				System.out.println(data.toString(4));
				
				for(int j=0; j<b.length(); j++) {
					Map<String,String> properties = getPostData(b.getJSONObject(j).getJSONObject("data"));
					count++;
					pics.add(properties);
					
					if(count == limit) {
						shouldBreak = true;
						break;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(shouldBreak)
				break;
		}
		return pics;
	}
	
	private static ArrayList<Map<String,String>> getLinksAfterTime(
			String subreddit, String postType, Object param) {
		if (!(param instanceof Calendar))
			throw new IllegalArgumentException();
		Calendar date = (Calendar) param;
		
		long threshold = date.getTimeInMillis();
		String lastID = "";
		boolean shouldBreak = false;
		ArrayList<Map<String,String>> pics = new ArrayList<Map<String,String>>();
		
		while (true) {
			String url = "http://www.reddit.com/r/" + subreddit + "/" + postType + ".json?limit=100&after=" + lastID;
			JSONObject json = (JSONObject) GetJSON.getJSONObjectAtURL(url);
			try {
				JSONObject data = json.getJSONObject("data");
				JSONArray b = (JSONArray) data.get("children");
				lastID = data.get("after").toString();
				
				for(int j=0; j<b.length(); j++) {
					Map<String,String> properties = getPostData(data);
					
					if(Long.parseLong(properties.get("time")) < threshold) {
						shouldBreak = true;
						break;
					}
					
					pics.add(properties);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(shouldBreak)
				break;
		}
		return pics;
	}
}
