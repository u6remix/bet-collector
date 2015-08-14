package by.blogobet.parser;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.blogobet.job.NewPredictionFinder;
import by.blogobet.job.ResultChecker;

import com.sun.syndication.io.FeedException;


public class App {
	private static final Logger logger = Logger.getLogger(App.class);
	private static final int GLOBAL_PREDICTION_FINDER_PERIOD = 600; // in seconds
	private static final int RESULT_FINDER_PERIOD = 60;//in minutes
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, FeedException {
		logger.debug("Starting application at "+ new Date());
		logger.debug("GLOBAL_PREDICTION_FINDER_PERIOD = " + GLOBAL_PREDICTION_FINDER_PERIOD + " seconds");
		logger.debug("RESULT_FINDER_PERIOD = " + RESULT_FINDER_PERIOD + " minutes");
		
		Timer time = new Timer(); 
		NewPredictionFinder st = new NewPredictionFinder();
		ResultChecker rc = new ResultChecker();
		time.schedule(st, 0, GLOBAL_PREDICTION_FINDER_PERIOD * 1000);
		time.schedule(rc, 0, RESULT_FINDER_PERIOD *60*1000);

	}
	
	
	@Deprecated
	private static void parseRssDescription(String description){
		Document doc = Jsoup.parse(description);
		Element table = doc.select("table").get(0); //select the first table.
		Elements rows = table.select("tr");
		for (int i = 0; i < rows.size(); i++) { 
		    Element row = rows.get(i);
		    Elements cols = row.select("td");
		    if(cols.size()>1){
		    System.out.println(cols.get(0).text().trim()+"     -    "+cols.get(1).text());
		    }
		}
	}

}
