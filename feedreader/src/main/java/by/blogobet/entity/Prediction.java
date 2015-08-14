package by.blogobet.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the prediction database table.
 * 
 */
@Entity
@Table(name="prediction")
@NamedQueries({
	@NamedQuery(name="Prediction.findAll", query="SELECT p FROM Prediction p"),
	@NamedQuery(name="Prediction.findAllWaitForCalculating", query="SELECT p FROM Prediction p WHERE p.isCalculated=false AND p.startDate < :param")
})
public class Prediction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String bookmaker;

	private String competition;

	private String event;

	@Column(name="external_id")
	private int externalId;

	@Column(name="is_calculated")
	private boolean isCalculated;

	private String link;

	private double odds;

	private String pick;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="posted_date")
	private Date postedDate;

	private double result;

	private String sport;

	private double stake;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Date startDate;

	//bi-directional many-to-one association to Tipster
	@ManyToOne
	private Tipster tipster;

	//bi-directional many-to-one association to Statistic
	@OneToMany(mappedBy="prediction",cascade = CascadeType.ALL)
	private List<Statistic> statistics;

	public Prediction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookmaker() {
		return this.bookmaker;
	}

	public void setBookmaker(String bookmaker) {
		this.bookmaker = bookmaker;
	}

	public String getCompetition() {
		return this.competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getExternalId() {
		return this.externalId;
	}

	public void setExternalId(int externalId) {
		this.externalId = externalId;
	}

	public boolean getIsCalculated() {
		return this.isCalculated;
	}

	public void setIsCalculated(boolean isCalculated) {
		this.isCalculated = isCalculated;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public double getOdds() {
		return this.odds;
	}

	public void setOdds(double odds) {
		this.odds = odds;
	}

	public String getPick() {
		return this.pick;
	}

	public void setPick(String pick) {
		this.pick = pick;
	}

	public Date getPostedDate() {
		return this.postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public double getResult() {
		return this.result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public String getSport() {
		return this.sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public double getStake() {
		return this.stake;
	}

	public void setStake(double stake) {
		this.stake = stake;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Tipster getTipster() {
		return this.tipster;
	}

	public void setTipster(Tipster tipster) {
		this.tipster = tipster;
	}

	public List<Statistic> getStatistics() {
		return this.statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	public Statistic addStatistic(Statistic statistic) {
		if(statistics==null){
			statistics = new ArrayList<Statistic>();
		}
		getStatistics().add(statistic);
		statistic.setPrediction(this);

		return statistic;
	}

	public Statistic removeStatistic(Statistic statistic) {
		getStatistics().remove(statistic);
		statistic.setPrediction(null);

		return statistic;
	}

	@Override
	public String toString() {
		return "Prediction [id=" + id + ", bookmaker=" + bookmaker
				+ ", competition=" + competition + ", event=" + event
				+ ", externalId=" + externalId + ", isCalculated="
				+ isCalculated + ", link=" + link + ", odds=" + odds
				+ ", pick=" + pick + ", postedDate=" + postedDate + ", result="
				+ result + ", sport=" + sport + ", stake=" + stake
				+ ", startDate=" + startDate + ", tipster=" + tipster
				+ ", statistics=" + statistics + "]";
	}
	
	

}