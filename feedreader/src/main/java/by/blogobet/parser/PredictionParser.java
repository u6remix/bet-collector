package by.blogobet.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.blogobet.entity.Prediction;
import by.blogobet.parser.util.DateParser;
import by.blogobet.parser.util.NumberParser;

public class PredictionParser {
	
	private static final Logger logger = Logger.getLogger(PredictionParser.class);
	
	private static final Pattern TIME_PATTERN = Pattern.compile("(?i)[0-9]{1,2}:[0-9]{1,2}");
	private static final Pattern ID_FROM_LINK_PATTERN = Pattern.compile("(?<=blogabet.com\\/)(.*?)(?=\\/)");
	

	public static Prediction parse(Element betHeaderDate) {
		String tempText = betHeaderDate.text().trim();
		// ignore combo picks
		if (tempText == null || "".equalsIgnoreCase(tempText)) {
			logger.warn("betHeaderDate text is null. This is probably combo picks or smth else");
			return null;
		}
		Prediction prediction = new Prediction();
		parseBetHeaderdate(betHeaderDate, prediction);

		int trBetHeaderDateIndex = betHeaderDate.parent().elementSiblingIndex();
		// bet header tr
		Element betHeaderTr = betHeaderDate.parent().parent().child(trBetHeaderDateIndex - 1);
		if (!parseBetHeader(betHeaderTr, prediction)) {
			return null;
		}
		// bet body tr
		Element betBody = betHeaderDate.parent().parent().child(trBetHeaderDateIndex + 1);
		parseBetBody(betBody, prediction);
		return prediction;
	}
	
	public static Prediction parseResultOnly(Document doc,Prediction prediction){
		//should be one element only
		Element betHeader = doc.select("span.betHeader").first();
		String fullHeaderText = betHeader.parent().parent().text().trim();
		String eventName = betHeader.text().trim();
		String result = fullHeaderText.replace(eventName, "");
		if (result != null && !"".equalsIgnoreCase(result)) {
			prediction.setResult(NumberParser.parseDouble(result));
			prediction.setIsCalculated(true);
		} else {
			prediction.setIsCalculated(false);
		}
		return prediction;
	}

	// set startDate and competition name
	private static void parseBetHeaderdate(Element betHeaderDate, Prediction prediction) {
		String tempText = betHeaderDate.text().trim();
		String tempHtml = betHeaderDate.html();// contain start date and sport
												// type - name of challenge
		String[] delimited = tempHtml.split("<br />");
		String startDate = delimited[0].trim();
		String competitionLine = tempText.replace(startDate, "");
		prediction.setStartDate(DateParser.parseTimeLine(startDate));
		String[] splitedComp = competitionLine.split(" - ");
		if (splitedComp != null && splitedComp.length == 2) {
			prediction.setSport(splitedComp[0].trim());
			prediction.setCompetition(splitedComp[1].trim());
		} else {
			logger.warn("Unexpected line:" + competitionLine);
		}
	}

	// set event name,link and result if exist
	private static boolean parseBetHeader(Element betHeaderTr, Prediction prediction) {

		Element betHeader = betHeaderTr.select("span.betHeader").first();
		String eventName = betHeader.text().trim();
		if ("Private pick".equalsIgnoreCase(eventName)) {
			logger.debug("Private pick can't be parsed");
			return false;
		}
		String href = betHeader.parent().attr("href");
		if ("".equalsIgnoreCase(href)) {
			logger.debug("Pick title isn't clickable. It can be private pick");
			return false;
		}
		String fullHeaderText = betHeader.parent().parent().text().trim();
		String result = fullHeaderText.replace(eventName, "");
		prediction.setEvent(eventName);
		if (result != null && !"".equalsIgnoreCase(result)) {
			prediction.setResult(NumberParser.parseDouble(result));
			prediction.setIsCalculated(true);
		} else {
			prediction.setIsCalculated(false);
		}
		prediction.setLink(href);
		prediction.setExternalId(getIdFromLink(href));
		return true;
	}

	private static int getIdFromLink(String link) {
		Matcher m = ID_FROM_LINK_PATTERN.matcher(link);
		int id = 0;
		if (m.find()) {
			id = NumberParser.parseInt(m.group());
		} else {
			logger.warn("Can't find id in link: " + link);
		}
		return id;
	}

	private static void parseBetBody(Element betBody, Prediction prediction) {
		String betBodyHtml = betBody.child(0).html();
		List<String> betBodyTiles = new ArrayList<String>();
		String[] betBodyDelimited1 = betBodyHtml.split("<b>");
		for (String betBodyDelimited : betBodyDelimited1) {
			String[] betBodyDelimited2 = betBodyDelimited.split("</b>");
			for (String betBodyDelimetedTemp : betBodyDelimited2) {
				betBodyTiles.add(betBodyDelimetedTemp.replaceAll("&nbsp;", ""));
			}
		}

		for (int i = 0; i < betBodyTiles.size(); i++) {
			String tile = betBodyTiles.get(i);
			if (tile.startsWith("Pick")) {
				prediction.setPick(betBodyTiles.get(i + 1));
			}
			if (tile.startsWith("Stake")) {
				prediction.setStake(NumberParser.parseDouble(betBodyTiles.get(i + 1)));
			}
			if (tile.startsWith("Odds")) {
				prediction.setOdds(NumberParser.parseDouble(betBodyTiles.get(i + 1).split("<img")[0]));
			}
			if (tile.startsWith("Bookmaker")) {
				prediction.setBookmaker(betBodyTiles.get(i + 2));
			}
			if (tile.startsWith("Posted")) {
				String line = betBodyTiles.get(i + 1).split("<a")[0];
				line = getDayTimeLine(line);
				prediction.setPostedDate(DateParser.parseTimeLine(line));
			}

		}
	}

	private static String getDayTimeLine(String str) {
		String time = null;
		String dayTime = null;
		Matcher m = TIME_PATTERN.matcher(str);
		if (m.find()) {
			time = m.group();// 14:32
			int startTimeIndex = str.indexOf(time);
			dayTime = str.substring(1, startTimeIndex + time.length());
		}
		return dayTime;
	}

}
