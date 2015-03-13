import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


public class Test {
	public static void main(String[] args) {
		///**
		ArrayList<Map<String,String>> t = RetrieveURLs.getRedditLinks("gifs", "top", 10);
		ArrayList<TypedImage> p = new ArrayList<TypedImage>(); 
		for ( Map<String,String> a : t) {
			try {
				ArrayList<TypedImage> f = SaveImage.getImagesFromURL(a);
				if (f != null)
					p.addAll(f);
			} catch (IOException e) {
				System.out.println("Could not retrieve image at " + a.get("url"));
			}
		}
		for (TypedImage q : p) {
			SaveImage.saveImage(q);
		}
		
		//GetJSON.printJSONAtURL("http://www.reddit.com/r/pics/top.json", 4);
		JSONObject z = (JSONObject) GetJSON.getJSONObjectAtURL("http://www.reddit.com/r/pics/top.json");
		try {
			System.out.println(z.getJSONObject("data").getString("after"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//**/
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
	    String filetype = fileNameMap.getContentTypeFor("http://i.imgur.com/IRvAU6t.jpg");
	    System.out.println(filetype);
	}
}
