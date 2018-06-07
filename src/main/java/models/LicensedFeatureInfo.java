package models;


import java.util.Date;

public class LicensedFeatureInfo {

	public String key;
    /**
     Limit for feature usage.
     -1 for unlimited usage
     */
	public int countLimit;

	/**
	 Expiration date of the feature.
	 Null for permanent license.
	 */
	public Date expirationDate;


	@Override
	public String toString() {
		return String.format("%1$s expires on %2$s, count limit=%3$s", key, expirationDate, countLimit);
	}
}