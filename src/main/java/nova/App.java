package nova;

import managers.LicenseProvider;
import models.LicenseInfo;
import models.LicensedFeatureInfo;
import models.LicensedProductInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class App {

    public static String getMacAddress() {
        return macAddress;
    }

    public static void setMacAddress(String macAddress) {
        App.macAddress = macAddress;
    }

    public static List<String> getFiles() {
        return files;
    }

    public static void setFiles(List<String> files) {
        App.files = files;
    }

    public static List<LicensedProductInfo> getProducts() {
        return products;
    }

    public static void setProducts(List<LicensedProductInfo> products) {
        App.products = products;
    }

    public static List<LicensedFeatureInfo> getFeatures() {
        return features;
    }

    public static void setFeatures(List<LicensedFeatureInfo> features) {
        App.features = features;
    }

    public static List<LicenseInfo> getLicenses() {
        return licenses;
    }

    public static void setLicenses(List<LicenseInfo> licenses) {
        App.licenses = licenses;
    }

    private static String macAddress;
    private static List<String> files;
    private static List<LicensedProductInfo> products;
    private static List<LicensedFeatureInfo> features;
    private static List<LicenseInfo> licenses;

    public static void main(String[] args) {
        Properties config = getConfig();
        LicenseProvider licenseProvider = new LicenseProvider();
        licenseProvider.setLicenseFolder(config.getProperty("licenseDirPath"));
        licenseProvider.setCryptoIV(config.getProperty("cryptoIV"));
        licenseProvider.setCryptoKey(config.getProperty("cryptoKey"));
        setMacAddress(licenseProvider.getCurrentMacAddress());
        setLicenses(licenseProvider.getAllLicenses());
        setFiles(licenseProvider.getLicenseFilesFullNames());
        setProducts(licenseProvider.getLicensedProducts());
        setFeatures(licenseProvider.getLicensedFeatures());
        System.out.println("MAC " + macAddress);
        System.out.println("licenses " + licenses);
        System.out.println("files " + files);
        System.out.println("products " + products);
        System.out.println("features " + features);
    }

    private static Properties getConfig() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}