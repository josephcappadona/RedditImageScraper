import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class GetHTML {
	public static void main(String[] args) {
		BufferedReader input = null;
		StringBuilder html = new StringBuilder();
		try {
		java.net.URL url = new URL("https://www.flickr.com/photos/billynewmanphotos/15970710248/in/photostream/");
		
		    input = new BufferedReader(
		        new InputStreamReader(url.openStream()));

		    String htmlLine;
		    while ((htmlLine=input.readLine())!=null) {
		        html.append(htmlLine);
		    }
		} catch (Exception e) {
			
		} finally {
		    try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String htmlText = html.toString();
		///
		System.out.println(htmlText);
		String[] elts = htmlText.split(":");
		String t = null;
		for(int i=0; i<elts.length; i++) {
			if(elts[i].contains("_o.")) {
				t = elts[i].substring(4, elts[i].lastIndexOf(".")+4);
			}
		}
		System.out.println(t.replace("\\", ""));
	}//8 to length-1
}
