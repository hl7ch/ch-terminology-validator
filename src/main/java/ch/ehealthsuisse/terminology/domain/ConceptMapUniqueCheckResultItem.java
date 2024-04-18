/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import ca.uhn.fhir.validation.ResultSeverityEnum;

/**	
 * 
 */
public class ConceptMapUniqueCheckResultItem extends ValueSetUniqueCheckResultItem {

	private String targetSystem;
	private String targetCode;
	private String targetDisplay;

	public String getTargetSystem() {
		return targetSystem;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public String getTargetDisplay() {
		return targetDisplay;
	}

	public ConceptMapUniqueCheckResultItem setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
		return this;
	}

	public ConceptMapUniqueCheckResultItem setTargetCode(String targetCode) {
		this.targetCode = targetCode;
		return this;
	}

	public ConceptMapUniqueCheckResultItem setTargetDisplay(String targetDisplay) {
		this.targetDisplay = targetDisplay;
		return this;
	}

	@Override
	public ConceptMapUniqueCheckResultItem setMessage(String message) {
		super.setMessage(message);
		return this;
	}

	@Override
	public ConceptMapUniqueCheckResultItem setSeverity(ResultSeverityEnum severity) {
		super.setSeverity(severity);
		return this;
	}

	@Override
	public ConceptMapUniqueCheckResultItem setSystem(String system) {
		super.setSystem(system);
		return this;
	}

	@Override
	public ConceptMapUniqueCheckResultItem setCode(String code) {
		super.setCode(code);
		return this;
	}
	
	

	@Override
	public ConceptMapUniqueCheckResultItem setDisplay(String display) {
		super.setDisplay(display);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConceptMapUniqueCheckResultItem [setSeverity()=");
		builder.append(getSeverity());
		builder.append(", sourceSystem=");
		builder.append(getSystem());
		builder.append(", sourceCode=");
		builder.append(getCode());
		builder.append(", targetSystem=");
		builder.append(targetSystem);
		builder.append(", targetCode=");
		builder.append(targetCode);
		builder.append(", targetDisplay=");
		builder.append(targetDisplay);
		builder.append(", message()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}

}
