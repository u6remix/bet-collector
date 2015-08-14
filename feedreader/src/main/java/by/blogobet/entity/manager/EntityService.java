package by.blogobet.entity.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import by.blogobet.entity.Prediction;
import by.blogobet.entity.Tipster;
import by.blogobet.util.DateUtil;


public class EntityService {
	EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
	
	public Prediction save(Prediction prediction){
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(prediction);
		tr.commit();
		return prediction;
	}
	
	public void update(Object emp){
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.merge(emp);
		tr.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tipster> getAllTipsters(){
		List<Tipster> results = (List<Tipster>)em.createNamedQuery("Tipster.findAll").getResultList();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prediction> getAllPrediction(){
		List<Prediction> results = (List<Prediction>)em.createNamedQuery("Prediction.findAll").getResultList();
		return results;
	}
	
	public List<Prediction> getAllPredictionWaitForCalculating(){
		List<Prediction> results = (List<Prediction> ) em.createNamedQuery("Prediction.findAllWaitForCalculating").setParameter("param", DateUtil.getDate2HoursBefore()).getResultList();
		return results;
	}
}
