package by.blogobet.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.blogobet.entity.Prediction;
import by.blogobet.entity.Statistic;
import by.blogobet.parser.util.NumberParser;

public class MainPageFeedParser {
	
	private static final Logger logger = Logger.getLogger(MainPageFeedParser.class);
	private static long LAST_EXTERNAL_ID = 0;
	private static URL URL = null;
	static{
		try {
			URL = new URL("http://www.blogabet.com/");
		} catch (MalformedURLException e) {
			logger.error("Network problems",e);
		}
	}
	
	public void parse() throws IOException{
		Document doc = Jsoup.parse(URL, 5000);
		Elements feedItems = doc.select("div.feed_item_clearfix");
		for(Element feedItem: feedItems){
			String betby_id = feedItem.select("input").first().attr("id");
			String betId = betby_id.replace("betby_", "");
			Element tipsterA = feedItem.select("div.comment a").first();
			String tipsterName = tipsterA.text();
			String tipsterLink = tipsterA.attr("href");
			
			int yeild = 0;
			int betsCount = 0;
			// something like -2%(90) or +20%(26)
			String rawStatLine = feedItem.select("div.avatar").text().trim();
			String[] rawStatParts = rawStatLine.split("%(");
			if(rawStatParts.length==2){
				yeild = NumberParser.parseInt(rawStatParts[0]);
				betsCount = NumberParser.parseInt(rawStatParts[1].replaceAll(")",""));
			}
			
			//TODO implement a high level check common yeild and bets count
			//TODO Check existing tipster in the db by name and link
			
		}
		
	}
	
	public Statistic getCommonStatistic(String link) throws IOException{
		URL url = new URL(link);
		Document doc = Jsoup.parse(url, 5000);
		return StatisticParser.parse(doc);
	}

}
