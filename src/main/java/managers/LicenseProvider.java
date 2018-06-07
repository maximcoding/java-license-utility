package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.ILicenseProvider;
import models.LicenseInfo;
import models.LicenseProviderResult;
import models.LicensedFeatureInfo;
import models.LicensedProductInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class LicenseProvider implements ILicenseProvider {

    private String licenseFolder;
    private String cryptoKey;
    private String cryptoIV;

    String getLicenseFolder() {
        return this.licenseFolder;
    }

    public void setLicenseFolder(String value) {
        this.licenseFolder = value;
    }

    String getCryptoKey() {
        return this.cryptoKey;
    }

    public void setCryptoKey(String value) {
        this.cryptoKey = value;
    }

    String getCryptoIV() {
        return this.cryptoIV;
    }

    public void setCryptoIV(String value) {
        this.cryptoIV = value;
    }

    @Override
    public List<String> getLicenseFilesFullNames() {
        if (this.getLicenseFolder() == null) {
            throw new IllegalArgumentException("License directory is not set");
        }
        if (!(new File(this.getLicenseFolder())).isDirectory()) {
            throw new IllegalArgumentException(String.format("License directory does not exist: %1$s", this.getLicenseFolder()));
        }
        List<String> results = new ArrayList<String>();
        File[] files = new File(this.getLicenseFolder()).listFiles();
        Arrays.stream(files).filter(File::isFile).forEach(file -> results.add(file.getName()));
        return results;
    }

    @Override
    public LicenseProviderResult addLicenseFile(String fullFilename) throws IOException {
        if (!(new File(fullFilename)).isFile()) {
            return new LicenseProviderResult(-1, String.format("File does not exist: %1$s", fullFilename));
        }
        if (this.getLicenseFolder() == null) {
            throw new IllegalArgumentException("License directory is not set");
        }
        if (!(new File(this.getLicenseFolder())).isDirectory()) {
            (new File(this.getLicenseFolder())).mkdirs();
        }
        File fi = new File(fullFilename);
        Files.copy(Paths.get(fullFilename), Paths.get(Paths.get(this.getLicenseFolder()).resolve(fi.getName()).toString()), StandardCopyOption.COPY_ATTRIBUTES);
        return new LicenseProviderResult(0, "OK");
    }

    @Override
    public List<LicensedFeatureInfo> getLicensedFeatures() {
        List<LicensedFeatureInfo> features = new ArrayList<LicensedFeatureInfo>();
        try {
            this.getRelevantLicenses().stream().forEach(licenseInfo -> features.addAll(licenseInfo.getFeatures()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return features;
    }

    @Override
    public List<LicensedProductInfo> getLicensedProducts() {
        List<LicensedProductInfo> prods = new ArrayList<LicensedProductInfo>();
        try {
            this.getRelevantLicenses().stream().forEach(licenseInfo -> prods.addAll(licenseInfo.getProducts()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prods;
    }

    @Override
    public String getCurrentMacAddress() {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);
            byte[] macAddressBytes = networkInterface.getHardwareAddress();
            StringBuilder macAddressBuilder = new StringBuilder();
            for (int macAddressByteIndex = 0; macAddressByteIndex < macAddressBytes.length; macAddressByteIndex++) {
                String macAddressHexByte = String.format("%02X", macAddressBytes[macAddressByteIndex]);
                macAddressBuilder.append(macAddressHexByte);
            }
            return macAddressBuilder.toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LicenseInfo> getAllLicenses() {
        if (this.cryptoKey == null) {
            throw new IllegalArgumentException(String.format("Encryption key is not set"));
        }
        if (this.cryptoIV == null) {
            throw new IllegalArgumentException(String.format("Encryption IV is not set"));
        }
        ArrayList<LicenseInfo> licenses = new ArrayList<LicenseInfo>();
        List<String> fileNames = this.getLicenseFilesFullNames();
        for (String fileName : fileNames) {
            try {
                LicenseInfo convertedLicense = convertJsonTextFileToLicenseInfo(this.getLicenseFolder() + "/" + fileName);
                String jsonLicense = CryptoHelper.decrypt(convertedLicense.getLicense(), this.getCryptoKey(), this.getCryptoIV());
                licenses.add(new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(jsonLicense, LicenseInfo.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return licenses;
    }

    private List<LicenseInfo> getRelevantLicenses() {
        return getAllLicenses().stream().filter(licenseInfo -> licenseInfo.getMacAddress().equals(getCurrentMacAddress())).collect(Collectors.toList());
    }

    private LicenseInfo convertJsonTextFileToLicenseInfo(String fileName) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(fileName)));
            return new Gson().fromJson(jsonString, LicenseInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}