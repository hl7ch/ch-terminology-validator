/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import ca.uhn.fhir.validation.ResultSeverityEnum;

/**
 * 
 */
public class ValueSetCodeCheckResultItem {
	private ResultSeverityEnum severity;
	private String system;
	private String code;
	private String display;
	private String message;

	public ResultSeverityEnum getSeverity() {
		return severity;
	}

	public String getSystem() {
		return system;
	}

	public String getCode() {
		return code;
	}

	public String getDisplay() {
		return display;
	}

	public String getMessage() {
		return message;
	}

	public ValueSetCodeCheckResultItem setSeverity(ResultSeverityEnum severity) {
		this.severity = severity;
		return this;
	}

	public ValueSetCodeCheckResultItem setSystem(String system) {
		this.system = system;
		return this;
	}

	public ValueSetCodeCheckResultItem setCode(String code) {
		this.code = code;
		return this;
	}

	public ValueSetCodeCheckResultItem setDisplay(String display) {
		this.display = display;
		return this;
	}

	public ValueSetCodeCheckResultItem setMessage(String message) {
		this.message = message;
		return this;
	}
}
