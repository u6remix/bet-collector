package by.blogobet.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.blogobet.entity.Prediction;

public class PredictionListPageParser {
	private static final Logger logger = Logger.getLogger(PredictionListPageParser.class);

	public static List<Prediction> parsePrediction(Document doc) {
		// contains start date and sport type - name of challenge
		Elements betHeaderDates = doc.select("td.betHeaderDate");
		List<Prediction> pickList = new ArrayList<Prediction>();
		for (Element betHeaderDate : betHeaderDates) {
			Prediction prediction = PredictionParser.parse(betHeaderDate);
			if (prediction != null) {
				logger.debug(prediction);
				pickList.add(prediction);
			}
		}
		return pickList;

	}
}
