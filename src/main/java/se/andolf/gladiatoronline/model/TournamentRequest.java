package se.andolf.gladiatoronline.model;


public class TournamentRequest {

	private String tournamentName;
	private String gender;
	private int weight;
	private int pools;
	private int totalContenders;
	private String[][] contenders;

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPools() {
		return pools;
	}

	public void setPools(int pools) {
		this.pools = pools;
	}

	public int getTotalContenders() {
		return totalContenders;
	}

	public void setTotalContenders(int totalContenders) {
		this.totalContenders = totalContenders;
	}

	public String[][] getContenders() {
		return contenders;
	}

	public void setContenders(String[][] contenders) {
		this.contenders = contenders;
	}
}
