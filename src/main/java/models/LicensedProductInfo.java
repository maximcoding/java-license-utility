package models;

import java.util.Date;

public class LicensedProductInfo {

	public String name;
	/**
	 Expiration date of the product – the first expiration date from all features related to this product.
	 Null for permanent license.
	*/
	public Date expirationDate;

	@Override
	public String toString() {
		return String.format("%1$s expires on %2$s", name, expirationDate);
	}
}