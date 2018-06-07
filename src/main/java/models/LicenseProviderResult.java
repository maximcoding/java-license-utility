package models;

public class LicenseProviderResult {

	/**
	 Code = 0 indicate success
	 */
	public int code;
	public String message;

	public LicenseProviderResult(int code, String message) {
		setCode(code);
		setMessage(message);
	}

	public final int getCode() {
		return code;
	}
	public final void setCode(int value) {
		code = value;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String value) {
		message = value;
	}
}