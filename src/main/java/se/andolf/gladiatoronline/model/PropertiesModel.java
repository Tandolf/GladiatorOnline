package se.andolf.gladiatoronline.model;

public class PropertiesModel {

	private String[] femaleWeightClasses;
	private String[] maleWeightClasses;
	private int maxPools, minContender, maxContender;

	public String[] getFemaleWeightClasses() {
		return femaleWeightClasses;
	}

	public void setFemaleWeightClasses(String[] femaleWeightClasses) {
		this.femaleWeightClasses = femaleWeightClasses;
	}

	public String[] getMaleWeightClasses() {
		return maleWeightClasses;
	}

	public void setMaleWeightClasses(String[] maleWeightClasses) {
		this.maleWeightClasses = maleWeightClasses;
	}

	public int getMaxPools() {
		return maxPools;
	}

	public void setMaxPools(int maxPools) {
		this.maxPools = maxPools;
	}

	public int getMinContender() {
		return minContender;
	}

	public void setMinContender(int minContender) {
		this.minContender = minContender;
	}

	public int getMaxContender() {
		return maxContender;
	}

	public void setMaxContender(int maxContender) {
		this.maxContender = maxContender;
	}

}
