import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ParseLink {
	
	/**
	 * Takes in any imgur link and returns direct links
	 * to the images on the page.
	 * 
	 * @param url URL of the desired picture. This should
	 * be in the form "imgur.com/*", "i.imgur.com/*",
	 * "imgur.com/a/*", or "imgur.com/gallery/*".
	 * @return ArrayList filled with direct links to the
	 * 			picture(s) that are present at the given URL.
	 */
	public static ArrayList<String> parseImgurLink(String url) {
		if(url.contains("/a/") || url.contains("/gallery/")) {
			return parseImgurAlbum(url);
		} else {
			ArrayList<String> directURL = new ArrayList<String>();
			if(url.contains("i.imgur.com"))
				directURL.add(url);
			else
				directURL.add(url.replace("imgur.com", "i.imgur.com"));
			return directURL;
		}
	}
	
	/**
	 * Takes a link of the form "imgur.com/a/*" or
	 * "imgur.com/gallery/*" and returns an ArrayList of
	 * the URLs for each picture of the album.
	 * 
	 * @param url URL if the desired album.
	 * @return ArrayList of the URLs of each picture on the page
	 */
	private static ArrayList<String> parseImgurAlbum(String url) {
		String htmlText = getHTML(url);
		if(htmlText == null)
			return null;
		
		ArrayList<String> directURLs = new ArrayList<String>();
		String[] splitHTML = htmlText.split("\"<div class=\"image\" id=");
		
		for(int i=1; i < splitHTML.length; i++)
			directURLs.add("i.imgur.com/" + splitHTML[i].substring(0,7));
		
		return directURLs;
	}
	
	/**
	 * Takes in any flickr link and returns a direct link
	 * to the image on the page.
	 * 
	 * @param url URL of the desired picture. This should
	 * be in the form "flickr.com/*" or "*.staticflickr.com/*".
	 * @return String of a direct link to the picture
	 * 			present at the given URL.
	 */
	public static String parseFlickrLink(String url) {
		if(!url.contains("staticflickr.com")) {
			String htmlText = getHTML(url);
			if(htmlText == null)
				return null;
			
			String[] elts = htmlText.split(":");
			String directImageURL = null;
			
			for(int i=0; i<elts.length; i++) {
				if(elts[i].contains("_o."))
					directImageURL = 
							elts[i].substring(4, elts[i].lastIndexOf(".") + 4);
			}
			directImageURL = directImageURL.replace("\\", "");
			
			return directImageURL;
		} else {
			return url;
		}
	}
	
	/**
	 * Gets the raw HTML from the specified URL.
	 * 
	 * @param url URL of the page that you want the HTML of
	 * @return String of the raw HTML source code
	 */
	public static String getHTML(String urlPath) {
		BufferedReader input = null;
		StringBuilder html = new StringBuilder();
		try {
			java.net.URL url = new URL(urlPath);
		
		    input = new BufferedReader(new InputStreamReader(url.openStream()));
		    String htmlLine;
		    
		    while ((htmlLine=input.readLine())!=null) {
		        html.append(htmlLine);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HTML source code could not be retrieved.");
		} finally {
		    try {
				input.close();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("BufferedReader could not be closed.");
			}
		}
		
		return html.toString();
	}
}
