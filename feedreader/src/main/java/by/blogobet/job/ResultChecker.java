package by.blogobet.job;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import by.blogobet.entity.Prediction;
import by.blogobet.entity.manager.EntityService;
import by.blogobet.parser.PredictionParser;

public class ResultChecker extends TimerTask{
	
	private static final Logger logger = Logger.getLogger(ResultChecker.class);
	EntityService entityServ = new EntityService();
	
	@Override
	public void run() {
		List<Prediction> predictions = entityServ.getAllPredictionWaitForCalculating();
		logger.debug("Staring find results for " + predictions.size() + " predictions");
		for(Prediction prediction: predictions){
			URL url;
			try {
				logger.debug("Start check result for ID "+ prediction.getId()+" : " + prediction.getLink());
				url = new URL(prediction.getLink());
				Document doc = Jsoup.parse(url, 5000);
				prediction = PredictionParser.parseResultOnly(doc, prediction);
				if(prediction.getIsCalculated()){
					logger.debug("Prediction " + prediction.getId() + " has been calculated: " + prediction.getResult());
					entityServ.update(prediction);
				}else{
					logger.debug("Prediction " + prediction.getId() + " hasn't been calculated yet");
				}
			} catch (IOException e) {
				logger.error("Can't parse prediction result due to exception",e);
			}
		}
		logger.debug("End finding results");
		
	}
}
