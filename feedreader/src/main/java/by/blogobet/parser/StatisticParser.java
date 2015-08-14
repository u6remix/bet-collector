package by.blogobet.parser;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.blogobet.entity.Statistic;
import by.blogobet.parser.util.NumberParser;

public class StatisticParser {
	private static final Logger logger = Logger.getLogger(StatisticParser.class);
	private String linkToCompStat;
	private String linkToSportStat;

	public Statistic parse(Document doc, String sport, String competition) {
		Statistic stat = parse(doc);

		Elements sportStatsLinks = doc.select("a[href*=sport_name=" + sport + "]");
		for (Element el : sportStatsLinks) {
			if (el.parent().hasClass("infoPageText2") && competition.equalsIgnoreCase(el.text())) {
				// String competitionName = el.text();
				linkToCompStat = el.attr("href").replaceAll("\u00A0", "").replaceAll(" ", "");
				// String value =
				// el.parent().parent().select("td").get(1).text();
			} else if (el.parent().hasClass("infoPageTitle2")) {
				linkToSportStat = el.attr("href").replaceAll("\u00A0", "");
			}
		}
		logger.debug(stat);
		return stat;
	}

	public static Statistic parse(Document doc) {
		Statistic stat = new Statistic();
		parseStatHeader(doc, stat);
		parseStatHeaderDetails(doc, stat);
		logger.debug(stat);
		return stat;
	}

	private static void parseStatHeader(Document doc, Statistic stat) {
		Elements mainStatsTds = doc.select("div#div_ppy").select("tr").select("td");
		String picks = mainStatsTds.get(0).text().replace("picks", "").trim();
		String profit = mainStatsTds.get(1).text().replace("profit", "").trim();
		String yield = mainStatsTds.get(2).text().replace("yield", "").trim();
		stat.setPicks(NumberParser.parseInt(picks));
		stat.setProfit(NumberParser.parseInt(profit));
		stat.setYield(NumberParser.parseInt(yield));
	}

	private static void parseStatHeaderDetails(Document doc, Statistic stat) {
		Elements statsTrs = doc.select("table#id_stats").select("tr");
		for (Element tr : statsTrs) {
			Elements tds = tr.select("td");
			String name = tds.get(0).text().trim();
			String value = tds.get(1).text().trim();
			if (name != null) {
				if (name.startsWith("Win-Loss-Void")) {
					String[] values = value.split("-");
					if (values != null && values.length == 3) {
						stat.setWin(NumberParser.parseInt(values[0]));
						stat.setLose(NumberParser.parseInt(values[1]));
						stat.setVoid_(NumberParser.parseInt(values[2]));
					} else {
						logger.warn("Win-Loss-Void section doesn't contain 3 elements as expected");
					}
				} else if (name.startsWith("Stake avg")) {
					stat.setAvgStake(NumberParser.parseDouble(value));
				} else if (name.startsWith("Odd avg")) {
					stat.setAvgOdds(NumberParser.parseDouble(value));
				} else if (name.startsWith("Staked")) {
					stat.setStaked(NumberParser.parseDouble(value));
				} else if (name.startsWith("Returned")) {
					stat.setReturned(NumberParser.parseDouble(value));
				}
			}
		}
	}

	public String getLinkToCompStat() {
		return linkToCompStat;
	}

	public String getLinkToSportStat() {
		return linkToSportStat;
	}

}
