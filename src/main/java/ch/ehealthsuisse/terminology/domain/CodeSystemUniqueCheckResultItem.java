/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import ca.uhn.fhir.validation.ResultSeverityEnum;

/**
 * 	
 */
public class CodeSystemUniqueCheckResultItem extends UniqueCheckResultItem {

	private String code;
	private String display;

	public String getCode() {
		return code;
	}

	public String getDisplay() {
		return display;
	}

	public CodeSystemUniqueCheckResultItem setCode(String code) {
		this.code = code;
		return this;
	}

	public CodeSystemUniqueCheckResultItem setDisplay(String display) {
		this.display = display;
		return this;
	}

	@Override
	public CodeSystemUniqueCheckResultItem setMessage(String message) {
		super.setMessage(message);
		return this;
	}

	@Override
	public CodeSystemUniqueCheckResultItem setSeverity(ResultSeverityEnum severity) {
		super.setSeverity(severity);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeSystemUniqueCheckResultItem [severity=");
		builder.append(getSeverity());
		builder.append(", message=");
		builder.append(getMessage());
		builder.append(", code=");
		builder.append(code);
		builder.append(", display=");
		builder.append(display);
		builder.append("]");
		return builder.toString();
	}

}
