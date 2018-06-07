package interfaces;

import models.LicenseInfo;
import models.LicenseProviderResult;
import models.LicensedFeatureInfo;
import models.LicensedProductInfo;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface ILicenseProvider {
	/** 
	 Returns license path
	 @return license path
	*/
	Iterable<String> getLicenseFilesFullNames();
	/**
	 Adds new license file. The original file might be deleted afterwards, so it should be copied to the right location.
	 @param fullFilename The full path to the newly added license file
	*/
	LicenseProviderResult addLicenseFile(String fullFilename) throws IOException;
	/**
	 Returns a list of licensed feature keys, with their data
	*/
	Iterable<LicensedFeatureInfo> getLicensedFeatures();
	/**
	 Returns a list of licensed products, with their data
	*/
	Iterable<LicensedProductInfo> getLicensedProducts();
	/**
	 Get the MAC Address of current machine
	 @return
	*/
	String getCurrentMacAddress();
	/**
	 Get licenses for all MAC addresses from all files
	 @return
	*/
	Iterable<LicenseInfo> getAllLicenses();
	/**
	 Path to the directory which contains licenses
	*/
	void setLicenseFolder(String value);
	/**
	 Key for license decryption
	*/
	void setCryptoKey(String value);
	/**
	 Key for license decryption
	*/
	void setCryptoIV(String value);
}