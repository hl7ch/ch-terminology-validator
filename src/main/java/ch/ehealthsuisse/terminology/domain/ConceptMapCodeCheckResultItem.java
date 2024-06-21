/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import ca.uhn.fhir.validation.ResultSeverityEnum;

/**
 * 
 */
public class ConceptMapCodeCheckResultItem {
	private ResultSeverityEnum severity;
	private String system;
	private String code;
	private String display;
	private String message;

	public ResultSeverityEnum getSeverity() {
		return severity;
	}

	public ConceptMapCodeCheckResultItem setSeverity(ResultSeverityEnum severity) {
		this.severity = severity;
		return this;
	}

	public String getSystem() {
		return system;
	}

	public ConceptMapCodeCheckResultItem setSystem(String system) {
		this.system = system;
		return this;
	}

	public String getCode() {
		return code;
	}

	public ConceptMapCodeCheckResultItem setCode(String code) {
		this.code = code;
		return this;
	}

	public String getDisplay() {
		return display;
	}

	public ConceptMapCodeCheckResultItem setDisplay(String display) {
		this.display = display;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ConceptMapCodeCheckResultItem setMessage(String message) {
		this.message = message;
		return this;
	}

}
