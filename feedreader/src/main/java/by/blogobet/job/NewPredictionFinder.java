package by.blogobet.job;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import by.blogobet.entity.Prediction;
import by.blogobet.entity.Statistic;
import by.blogobet.entity.Tipster;
import by.blogobet.entity.manager.EntityService;
import by.blogobet.parser.PredictionListPageParser;
import by.blogobet.parser.StatisticParser;

public class NewPredictionFinder extends TimerTask {

	private static final Logger logger = Logger.getLogger(NewPredictionFinder.class);

	EntityService entityServ = new EntityService();

	@Override
	public void run() {
		List<Tipster> tipsterList = entityServ.getAllTipsters();
		logger.info("Starting find for new predictions for " + tipsterList.size() + " tipsters");
		for (Tipster tipster : tipsterList) {
			int tempLastExternalId = tipster.getLastExternalId();
			if (tipster.getIsActive()) {
				URL url;
				try {
					logger.debug("Starting load " + tipster.getLink());
					url = new URL(tipster.getLink());
					Document doc = Jsoup.parse(url, 5000);
					List<Prediction> predictionList = PredictionListPageParser.parsePrediction(doc);
					for (Prediction prediction : predictionList) {
						if (!prediction.getIsCalculated() && prediction.getStartDate().after(new Date())
								&& prediction.getExternalId() > tipster.getLastExternalId()) {
							logger.info("Prediction is new: " + prediction.getLink());
							StatisticParser statParser = new StatisticParser();
							logger.debug("Getting common statistic");
							Statistic commonStat = statParser.parse(doc, prediction.getSport(), prediction.getCompetition());
							// TODO analyze common stat
							// save common stat
							commonStat.setType("common");
							prediction.addStatistic(commonStat);

							if (statParser.getLinkToSportStat() != null) {
								logger.debug("Getting sport statistics");
								Statistic sportStat = getAdditionalStat(statParser.getLinkToSportStat());
								// TODO analyze sport stat
								// save sport stat
								sportStat.setType("sport");
								prediction.addStatistic(sportStat);
							} else {
								logger.warn("Sport statistics is not available");
							}

							// Get competition stat
							if (statParser.getLinkToCompStat() != null) {
								logger.debug("Getting competition statistics");
								Statistic compStat = getAdditionalStat(statParser.getLinkToCompStat());
								// TODO analyze competition stat
								// save competition stat
								compStat.setType("competition");
								prediction.addStatistic(compStat);
							} else {
								logger.warn("Competition statistic is not available");
							}
							prediction.setTipster(tipster);
							entityServ.save(prediction);
							tempLastExternalId = tempLastExternalId < prediction.getExternalId() ? prediction.getExternalId() : tempLastExternalId;
						}
					}
				} catch (IOException e) {
					logger.error("Not so expected exception occured: ",e);
				}

			}
			tipster.setLastExternalId(tempLastExternalId);
			entityServ.update(tipster);
		}
	}

	private Statistic getAdditionalStat(String link) throws IOException {
		logger.debug("Statistic link: " + link);
		URL url = new URL(link);
		Document sportStatDoc = Jsoup.parse(url, 5000);
		Statistic stat = StatisticParser.parse(sportStatDoc);
		return stat;
	}

}
