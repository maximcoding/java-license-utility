package models;

import java.util.*;

public class LicenseInfo {

	public String macAddress;
	public String toolName;
	public String toolType;
	public String license;
	public ArrayList<LicensedProductInfo> products;
	public ArrayList<LicensedFeatureInfo> features;

	public final String getMacAddress() {
		return this.macAddress;
	}
	public final void setMacAddress(String value) {
		this.macAddress = value;
	}

	public final String getToolName() {
		return this.toolName;
	}
	public final void setToolName(String value) {
		this.toolName = value;
	}

	public final String getToolType() {
		return this.toolType;
	}
	public final void setToolType(String value) {
		this.toolType = value;
	}

	public final String getLicense() {
		return this.license;
	}
	public final void setLicense(String value) {
		this.license = value;
	}

	public final ArrayList<LicensedProductInfo> getProducts() {
		return this.products;
	}
	public final void setProducts(ArrayList<LicensedProductInfo> value) {
		this.products = value;
	}
	public final ArrayList<LicensedFeatureInfo> getFeatures() {
		return this.features;
	}
	public final void setFeatures(ArrayList<LicensedFeatureInfo> value) {
		this.features = value;
	}
}