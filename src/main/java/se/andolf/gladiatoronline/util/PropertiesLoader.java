package se.andolf.gladiatoronline.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private String propertiesFile = "gladiatoronline.properties";
	private String[] femaleWeightClasses;
	private String[] maleWeightClasses;
	private String minPools, maxPools, minContender, maxContender;

	public static final String PROPERTY_FEMALE_WIGHT = "femaleWeightClasses";
	public static final String PROPERTY_MALE_WEIGHT = "maleWeightClasses";
	public static final String PROPERTY_MINIMUM_POOLS = "minPools";
	public static final String PROPERTY_MAXIMUM_POOLS = "maxPools";
	public static final String PROPERTY_MINIMUM_CONTENDERS = "minContenders";
	public static final String PROPERTY_MAXIMUM_CONTENDERS = "maxContenders";
	public static final String PROPERTY_SEPARATOR = "separator";

	public void loadPropertiesFromFile() {

		Properties prop = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream(propertiesFile);

		if (in != null) {
			try {
				prop.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {

			prop.load(getClass().getClassLoader().getResourceAsStream(
					propertiesFile));

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Properties file loaded successfully");

		String propertySeperator = prop.getProperty(PROPERTY_SEPARATOR);
		String femaleWeight = prop.getProperty(PROPERTY_FEMALE_WIGHT);
		String maleWeight = prop.getProperty(PROPERTY_MALE_WEIGHT);
		maxPools = prop.getProperty(PROPERTY_MAXIMUM_POOLS);
		minContender = prop.getProperty(PROPERTY_MINIMUM_CONTENDERS);
		maxContender = prop.getProperty(PROPERTY_MAXIMUM_CONTENDERS);

		femaleWeightClasses = femaleWeight.split(propertySeperator);
		maleWeightClasses = maleWeight.split(propertySeperator);
	}

	public String getMinPools() {
		return minPools;
	}

	public void setMinPools(String minPools) {
		this.minPools = minPools;
	}

	public String getMaxPools() {
		return maxPools;
	}

	public void setMaxPools(String maxPools) {
		this.maxPools = maxPools;
	}

	public String getMinContender() {
		return minContender;
	}

	public void setMinContender(String minContender) {
		this.minContender = minContender;
	}

	public String getMaxContender() {
		return maxContender;
	}

	public void setMaxContender(String maxContender) {
		this.maxContender = maxContender;
	}

	public String[] getFemaleWightClasses() {
		return femaleWeightClasses;
	}

	public String[] getMaleWeightClasses() {
		return maleWeightClasses;
	}

	// getter for properties file only for unit test
	protected void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}
}
