import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;


public class SaveImage {
	
	/**
	 * Retrieves an image from a specified URL, retrieves
	 * the file type of that image, and saves both pieces
	 * of information, in addition to the image's post
	 * info from reddit in a TypedImage that is returned.
	 * 
	 * @param urlString URL of the image that is to be retrieved
	 * @return a TypedImage object that contains the byte
	 * 			information of the image and the three
	 * 			character file type of the image, as well as the
	 * 			properties of the image as per its post on reddit.
	 * @throws IOException 
	 */
	public static ArrayList<TypedImage> getImagesFromURL(Map<String,String> properties) throws IOException {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		String urlString = properties.get("url");
		String domain = properties.get("domain");
		ArrayList<String> urls;
		
		
		try {
			urls = ParseLink.parseLink(urlString, domain);
		} catch (UnsupportedDomainException e1) {
			e1.printStackTrace();
			return null;
		}
		if (urls == null) {
			return null;
		}
		System.out.println(urls.toString());
		
		ArrayList<TypedImage> returnImages = new ArrayList<TypedImage>();
		for (int i=0; i<urls.size(); i++) {
			try {
				urlString = urls.get(i);
				if (urlString.substring(urlString.length()-4).equals("gifv")) {
					urlString = urlString.substring(0, urlString.length()-1);
				}
				URL url = new URL(urlString);
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
			    String filetype = fileNameMap.getContentTypeFor(urlString);
			    System.out.println(filetype + "!!!");
			    
				in = new BufferedInputStream(url.openStream());
				out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1 != (n=in.read(buf)))
				{
				   out.write(buf, 0, n);
				}
	
				byte[] response = out.toByteArray();
				
				in.close();
				out.close();
				
				returnImages.add(new TypedImage(response, filetype, properties));
			} catch (IOException e) {
				out.close();
				in.close();
				throw new IOException();
			} catch (NullPointerException e) {
				out.close();
				in.close();
			}
		}
		return returnImages;
	}
	
	/**
	 * Saves an image in the same directory in which 
	 * the program is run. Hopefully, I can modify this
	 * to save the image in a custom directory.
	 * 
	 * @param img TypedImage that contains the image that
	 * 			   you want to be saved.
	 * @return returns true if the image was successfully saved,
	 * 			false if there was an issue with saving.
	 */
	public static boolean saveImage(TypedImage img) {
		String filename =  img.getID();
		FileOutputStream fos = null;
		
		if (img.getFiletype().equals(null))
			return false;
		
		try {
			fos = new FileOutputStream(filename + img.getFiletype());
			fos.write(img.getImageData());
			fos.close();
		} catch (IOException e) {
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
