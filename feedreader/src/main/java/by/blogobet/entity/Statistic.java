package by.blogobet.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the statistics database table.
 * 
 */
@Entity
@Table(name="statistics")
@NamedQuery(name="Statistic.findAll", query="SELECT s FROM Statistic s")
public class Statistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="avg_odds")
	private double avgOdds;

	@Column(name="avg_stake")
	private double avgStake;

	private int lose;

	private int picks;

	private int profit;

	private double returned;

	private double staked;

	private String type;

	@Column(name="void")
	private int void_;

	private int win;

	private int yield;

	//bi-directional many-to-one association to Prediction
	@ManyToOne
	private Prediction prediction;

	public Statistic() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAvgOdds() {
		return this.avgOdds;
	}

	public void setAvgOdds(double avgOdds) {
		this.avgOdds = avgOdds;
	}

	public double getAvgStake() {
		return this.avgStake;
	}

	public void setAvgStake(double avgStake) {
		this.avgStake = avgStake;
	}

	public int getLose() {
		return this.lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getPicks() {
		return this.picks;
	}

	public void setPicks(int picks) {
		this.picks = picks;
	}

	public int getProfit() {
		return this.profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public double getReturned() {
		return this.returned;
	}

	public void setReturned(double returned) {
		this.returned = returned;
	}

	public double getStaked() {
		return this.staked;
	}

	public void setStaked(double staked) {
		this.staked = staked;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVoid_() {
		return this.void_;
	}

	public void setVoid_(int void_) {
		this.void_ = void_;
	}

	public int getWin() {
		return this.win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getYield() {
		return this.yield;
	}

	public void setYield(int yield) {
		this.yield = yield;
	}

	public Prediction getPrediction() {
		return this.prediction;
	}

	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avgOdds);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(avgStake);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + lose;
		result = prime * result + picks;
		result = prime * result
				+ ((prediction == null) ? 0 : prediction.hashCode());
		result = prime * result + profit;
		temp = Double.doubleToLongBits(returned);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(staked);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + void_;
		result = prime * result + win;
		result = prime * result + yield;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistic other = (Statistic) obj;
		if (Double.doubleToLongBits(avgOdds) != Double
				.doubleToLongBits(other.avgOdds))
			return false;
		if (Double.doubleToLongBits(avgStake) != Double
				.doubleToLongBits(other.avgStake))
			return false;
		if (id != other.id)
			return false;
		if (lose != other.lose)
			return false;
		if (picks != other.picks)
			return false;
		if (prediction == null) {
			if (other.prediction != null)
				return false;
		} else if (!prediction.equals(other.prediction))
			return false;
		if (profit != other.profit)
			return false;
		if (Double.doubleToLongBits(returned) != Double
				.doubleToLongBits(other.returned))
			return false;
		if (Double.doubleToLongBits(staked) != Double
				.doubleToLongBits(other.staked))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (void_ != other.void_)
			return false;
		if (win != other.win)
			return false;
		if (yield != other.yield)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Statistic [id=" + id + ", avgOdds=" + avgOdds + ", avgStake="
				+ avgStake + ", lose=" + lose + ", picks=" + picks
				+ ", profit=" + profit + ", returned=" + returned + ", staked="
				+ staked + ", type=" + type + ", void_=" + void_ + ", win="
				+ win + ", yield=" + yield + ", prediction=" + prediction + "]";
	}
	
	

}