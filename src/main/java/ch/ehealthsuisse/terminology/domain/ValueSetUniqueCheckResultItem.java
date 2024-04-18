/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import ca.uhn.fhir.validation.ResultSeverityEnum;

/**
 * 
 */
public class ValueSetUniqueCheckResultItem extends CodeSystemUniqueCheckResultItem {

	private String system;

	public String getSystem() {
		return system;
	}

	public ValueSetUniqueCheckResultItem setSystem(String system) {
		this.system = system;
		return this;
	}

	@Override
	public ValueSetUniqueCheckResultItem setMessage(String message) {
		super.setMessage(message);
		return this;
	}

	@Override
	public ValueSetUniqueCheckResultItem setSeverity(ResultSeverityEnum severity) {
		super.setSeverity(severity);
		return this;
	}

	@Override
	public ValueSetUniqueCheckResultItem setCode(String code) {
		super.setCode(code);
		return this;
	}

	@Override
	public ValueSetUniqueCheckResultItem setDisplay(String display) {
		super.setDisplay(display);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValueSetUniqueCheckResultItem [severity()=");
		builder.append(getSeverity());
		builder.append(", system=");
		builder.append(system);
		builder.append(", code()=");
		builder.append(getCode());
		builder.append(", display()=");
		builder.append(getDisplay());
		builder.append(", message()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}

}
