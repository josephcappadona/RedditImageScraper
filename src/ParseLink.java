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
		return null;
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
		return null;
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
		return null;
	}
}
