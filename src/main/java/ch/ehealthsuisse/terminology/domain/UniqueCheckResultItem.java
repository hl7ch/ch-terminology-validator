/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import ca.uhn.fhir.validation.ResultSeverityEnum;

/**
 * 
 */
public class UniqueCheckResultItem {

	private ResultSeverityEnum severity;

	private String message;


	public String getMessage() {
		return message;
	}

	public ResultSeverityEnum getSeverity() {
		return severity;
	}

	

	public UniqueCheckResultItem setMessage(String message) {
		this.message = message;
		return this;
	}

	public UniqueCheckResultItem setSeverity(ResultSeverityEnum severity) {
		this.severity = severity;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UniqueCheckResultItem [severity=");
		builder.append(severity);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}


}
