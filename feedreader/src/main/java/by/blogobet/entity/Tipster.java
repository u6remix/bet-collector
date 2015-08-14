package by.blogobet.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipster database table.
 * 
 */
@Entity
@NamedQuery(name="Tipster.findAll", query="SELECT t FROM Tipster t")
public class Tipster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="is_active")
	private boolean isActive;

	@Column(name="last_external_id")
	private int lastExternalId;

	private String link;

	private String name;

	//bi-directional many-to-one association to Prediction
	@OneToMany(mappedBy="tipster")
	private List<Prediction> predictions;

	public Tipster() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getLastExternalId() {
		return this.lastExternalId;
	}

	public void setLastExternalId(int lastExternalId) {
		this.lastExternalId = lastExternalId;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Prediction> getPredictions() {
		return this.predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public Prediction addPrediction(Prediction prediction) {
		getPredictions().add(prediction);
		prediction.setTipster(this);

		return prediction;
	}

	public Prediction removePrediction(Prediction prediction) {
		getPredictions().remove(prediction);
		prediction.setTipster(null);

		return prediction;
	}

	@Override
	public String toString() {
		return "Tipster [id=" + id + ", isActive=" + isActive
				+ ", lastExternalId=" + lastExternalId + ", link=" + link
				+ ", name=" + name + ", predictions=" + predictions + "]";
	}
	
	

}