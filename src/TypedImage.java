
public class TypedImage {
	byte[] imgData;
	String filetype;
	
	/**
	 * @param response saves the image returned as the
	 * 					response of the URL stream
	 * @param filetype takes the content type of the URL
	 * 					file map and parses it into a three
	 * 					letter file type
	 */
	public TypedImage(byte[] response, String filetype) {
		this.imgData = response;
		this.filetype = parseImageType(filetype);
	}
	
	/**
	 * Converts the content type given by a URL file map into
	 * a type that is usable to save the image file
	 * @param type type information as returned from a URL
	 * 				file map in the form "image/*", where
	 * 				* is the file type. The expected inputs
	 * 				include "jpeg", "png", and "gif".
	 * @return the three character image type that will be
	 * 			used to save the image file
	 */
	private static String parseImageType(String type) {
		return null;
	}
}
