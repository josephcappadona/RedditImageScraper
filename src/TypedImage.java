import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;


public class TypedImage {
	private byte[] imgData;
	private String filetype;
	private Map<String,String> properties;
	
	/**
	 * @param imgData saves the image returned as the
	 * 					response of the URL stream
	 * @param filetype takes the content type of the URL
	 * 					file map and parses it into a three
	 * 					letter file type
	 */
	public TypedImage(byte[] imgData, String filetype, Map<String,String> properties) {
		this.imgData = imgData;
		try {
			this.filetype = parseContentType(filetype);
		} catch (UnsupportedTypeException e) {
			e.printStackTrace();
			this.filetype = null;
		}
		this.properties = properties;
	}
	
	/**
	 * Converts the content type given by a URL file map into
	 * a type that is usable to save the image file
	 * @param type type information as returned from a URL
	 * 				file map in the form "image/*", where
	 * 				* is the file type. The expected inputs
	 * 				include "jpeg", "png", and "gif".
	 * @return the string of the image type that will be
	 * 			used to save the image file
	 * @throws UnsupportedTypeException 
	 */
	private String parseContentType(String filetype) throws UnsupportedTypeException {
		System.out.println(filetype);
		switch(filetype) {
		case "image/gif":
			return "gif";
		case "image/jpeg":
			return "jpeg";
		case "image/png":
			return "png";
		case "image/tiff":
			return "tiff";
		default:
			throw new UnsupportedTypeException();
		}
	}
	
	public String getURL() {
		return properties.get("url");
	}
	
	public String getTime() {
		return properties.get("time");
	}
	
	public String getID() {
		return properties.get("id");
	}
	
	public String getDomain() {
		return properties.get("domain");
	}
	
	public byte[] getImageData() {
		return imgData;
	}
	
	public String getFiletype() {
		return filetype;
	}
}
