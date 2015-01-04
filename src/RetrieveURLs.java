import java.util.ArrayList;

public class RetrieveURLs {

	/**
	 * Scans the specified subreddit for links according
	 * to the postType and corresponding parameter. These
	 * are as follows: <p>
	 * \/hot --> param = # of posts (type Integer) <p>
	 * \/new --> param = date of earliest post (type Calendar) <p>
	 * \/rising --> param = # of posts (type Integer) <p>
	 * \/controversial --> param = # of posts (type Integer) <p>
	 * \/top --> param = # of posts (type Integer)
	 * 
	 * @param subreddit for example, \/r\/pics,
	 * 					 \/r\/gifs, or \/r\/EarthPorn
	 * @param postType see above
	 * @param param see above
	 * @return ArrayList of URLs
	 * @see java.util.Calendar
	 */
	public static ArrayList<String> getRedditLinks(String subreddit,
			String postType, Object param) {
		//throw illegal argument exception if param doesn't correspond correctly
		return null;
	}
}
