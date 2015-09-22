package ml;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.functions.SMO;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.weka.WekaAttributeSelection;
import net.sf.javaml.tools.weka.WekaClassifier;

public class Test {

	public static void main(String[] args) throws IOException {
		Dataset data = FileHandler.loadDataset(new File("prediction.data"), 6, ",");
		

		/*Create a Weka AS Evaluation algorithm */
		ASEvaluation eval = new GainRatioAttributeEval();
		/* Create a Weka's AS Search algorithm */
		ASSearch search = new Ranker();
		/* Wrap WEKAs' Algorithms in bridge */
		WekaAttributeSelection wekaattrsel = new WekaAttributeSelection(eval,search);
		/* Apply the algorithm to the data set */
		wekaattrsel.build(data);
		/* Print out the score and rank  of each attribute */
		for (int i = 0; i<wekaattrsel.noAttributes(); i++) 
		    System.out.println("Attribute  " +  i +  "  Ranks  " + wekaattrsel.rank(i) + " and Scores " + wekaattrsel.score(i) );
		
		
		Classifier knn = new KNearestNeighbors(5);
		knn.buildClassifier(data);
		
		
		
		//weka
		SMO smo = new SMO();
		Classifier javamlsmo = new WekaClassifier(smo);
		CrossValidation cv = new CrossValidation(javamlsmo);
		Map<Object, PerformanceMeasure> pm1 = cv.crossValidation(data);
		System.out.println(pm1);
		
		
	    Dataset dataForClassification = FileHandler.loadDataset(new File("prediction.data"), 6, ",");
	    //customn check weka 
	     Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(javamlsmo, dataForClassification);
	     for(Object o:pm.keySet())
	         System.out.println(o+": "+pm.get(o).getAccuracy());
	     /*
	     

	     int correct = 0, wrong = 0;
	     int realWin  = 0, realLose = 0;

	     for (Instance inst : dataForClassification) {
	         Object predictedClassValue = knn.classify(inst);
	         Object realClassValue = inst.classValue();
	         if("Y".equalsIgnoreCase((String) realClassValue)){
	        	 realWin++;
	         }else{
	        	 realLose++;
	         }
	         
	         if (predictedClassValue.equals(realClassValue))
	             correct++;
	         else
	             wrong++;
	     }
	     System.out.println(correct+" " + wrong);
	     System.out.println(realWin+" " + realLose);*/
	}

}
