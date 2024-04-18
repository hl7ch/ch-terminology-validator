/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

/**
 * 
 */
public class TerminologyValidationReport {

	private Map<String, ValidationResult> codeSystemValidationResults;
	private Map<String, ValidationResult> valueSetValidationResults;
	private Map<String, ValidationResult> conceptMapValidationResults;

	private Map<String, UniqueCheckResult> codeSystemUniqueCheckResults;
	private Map<String, UniqueCheckResult> valueSetUniqueCheckResults;
	private Map<String, UniqueCheckResult> conceptMapUniqueCheckResults;

	private ResultSeverityEnum severityLevel = ResultSeverityEnum.ERROR;

	public Map<String, ValidationResult> getValueSetValidationResults() {
		if (valueSetValidationResults == null) {
			valueSetValidationResults = new HashMap<String, ValidationResult>();
		}
		return valueSetValidationResults;
	}

	public void setValueSetValidationResults(Map<String, ValidationResult> valueSetValidationResults) {
		this.valueSetValidationResults = valueSetValidationResults;
	}

	public void addValueSetValidationResult(String id, ValidationResult result) {
		getValueSetValidationResults().put(id, result);
	}

	public Map<String, ValidationResult> getConceptMapValidationResults() {
		if (conceptMapValidationResults == null) {
			conceptMapValidationResults = new HashMap<String, ValidationResult>();
		}
		return conceptMapValidationResults;
	}

	public void setConceptMapValidationResults(Map<String, ValidationResult> conceptMapValidationResults) {
		this.conceptMapValidationResults = conceptMapValidationResults;
	}

	public void addConceptMapValidationResult(String id, ValidationResult result) {
		getConceptMapValidationResults().put(id, result);
	}

	public Map<String, ValidationResult> getCodeSystemValidationResults() {
		if (codeSystemValidationResults == null) {
			codeSystemValidationResults = new HashMap<String, ValidationResult>();
		}
		return codeSystemValidationResults;
	}

	public void setCodeSystemValidationResults(Map<String, ValidationResult> codeSystemValidationResults) {
		this.codeSystemValidationResults = codeSystemValidationResults;
	}

	public void addCodeSystemValidationResult(String id, ValidationResult result) {
		getCodeSystemValidationResults().put(id, result);
	}

	public Map<String, UniqueCheckResult> getCodeSystemUniqueCheckResults() {
		if (codeSystemUniqueCheckResults == null) {
			codeSystemUniqueCheckResults = new HashMap<String, UniqueCheckResult>();
		}
		return codeSystemUniqueCheckResults;
	}

	public void setCodeSystemUniqueCheckResults(Map<String, UniqueCheckResult> codeSystemUniqueCheckResults) {
		this.codeSystemUniqueCheckResults = codeSystemUniqueCheckResults;
	}

	public void addCodeSystemUniqueCheckResult(String id, UniqueCheckResult ucResult) {
		getCodeSystemUniqueCheckResults().put(id, ucResult);
	}

	public Map<String, UniqueCheckResult> getValueSetUniqueCheckResults() {
		if (valueSetUniqueCheckResults == null) {
			valueSetUniqueCheckResults = new HashMap<String, UniqueCheckResult>();
		}
		return valueSetUniqueCheckResults;
	}

	public void setValueSetUniqueCheckResults(Map<String, UniqueCheckResult> valueSetUniqueCheckResults) {
		this.valueSetUniqueCheckResults = valueSetUniqueCheckResults;
	}

	public void addValueSetUniqueCheckResult(String id, UniqueCheckResult ucResult) {
		getValueSetUniqueCheckResults().put(id, ucResult);
	}

	public Map<String, UniqueCheckResult> getConceptMapUniqueCheckResults() {
		if (conceptMapUniqueCheckResults == null) {
			conceptMapUniqueCheckResults = new HashMap<String, UniqueCheckResult>();
		}
		return conceptMapUniqueCheckResults;
	}

	public void setConceptMapUniqueCheckResults(Map<String, UniqueCheckResult> conceptMapUniqueCheckResults) {
		this.conceptMapUniqueCheckResults = conceptMapUniqueCheckResults;
	}

	public void addConceptMapUniqueCheckResult(String id, UniqueCheckResult ucResult) {
		getConceptMapUniqueCheckResults().put(id, ucResult);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TerminologyValidationReport [\nCodeSystem ValidationResults=");
		codeSystemValidationResults.forEach((id, cs) -> {
			List<SingleValidationMessage> errors = cs.getMessages().stream()
					.filter(filter -> severityLevel.equals(filter.getSeverity())).collect(Collectors.toList());
			if (!errors.isEmpty()) {
				builder.append("\n\t").append(id).append(":\n");
				errors.forEach(message -> {
					builder.append("\n\t\t")//
							.append(message.getSeverity())//
							.append(" ")//
							.append(message.getLocationLine() + "/" + message.getLocationCol()) //
							.append(" ")//
							.append(message.getLocationString()) //
							.append(" ")//
							.append(message.getMessage());
				});
			}
		});
		builder.append("\n\nCodeSystem UniqueCheck=");
		codeSystemUniqueCheckResults.forEach((id, cs) -> {
			List<UniqueCheckResultItem> errors = cs.getResultItems().stream()
					.filter(filter -> severityLevel.equals(filter.getSeverity())).collect(Collectors.toList());
			errors.forEach(message -> {
				builder.append("\n\t\t")//
						.append(message.getSeverity())//
						.append(", ")//
						.append(((CodeSystemUniqueCheckResultItem) message).getCode())//
						.append("|'")//
						.append(((CodeSystemUniqueCheckResultItem) message).getDisplay())//
						.append("': ")//
						.append(message.getMessage());
			});
		});

		builder.append("\n\nValueSet ValidationResults=");
		valueSetValidationResults.forEach((id, cs) -> {

			List<SingleValidationMessage> errors = cs.getMessages().stream()
					.filter(filter -> severityLevel.equals(filter.getSeverity())).collect(Collectors.toList());
			if (!errors.isEmpty()) {
				builder.append("\n\t").append(id).append(":\n");
				errors.forEach(message -> {
					if (severityLevel.equals(message.getSeverity())) {
						builder.append("\n\t\t")//
								.append(message.getSeverity())//
								.append(" ")//
								.append(message.getLocationLine() + "/" + message.getLocationCol()) //
								.append(" ")//
								.append(message.getLocationString()) //
								.append(" ")//
								.append(message.getMessage());
					}
				});
			}
		});
		builder.append("\n\nValueSet UniqueCheck=");
		valueSetUniqueCheckResults.forEach((id, vs) -> {
			List<UniqueCheckResultItem> errors = vs.getResultItems().stream()
					.filter(filter -> severityLevel.equals(filter.getSeverity())).collect(Collectors.toList());
			errors.forEach(message -> {
				builder.append("\n\t\t")//
						.append(message.getSeverity())//
						.append(", ")//
						.append(((ValueSetUniqueCheckResultItem) message).getSystem())//
						.append("|")//
						.append(((ValueSetUniqueCheckResultItem) message).getCode())//
						.append("|'")//
						.append(((ValueSetUniqueCheckResultItem) message).getDisplay())//
						.append("': ")//
						.append(message.getMessage());
			});
		});

		builder.append("\n\nConceptMap ValidationResults=");
		conceptMapValidationResults.forEach((id, cs) -> {
			List<SingleValidationMessage> errors = cs.getMessages().stream()
					.filter(filter -> severityLevel.equals(filter.getSeverity())).collect(Collectors.toList());
			if (!errors.isEmpty()) {
				builder.append("\n\t").append(id).append(":\n");
				errors.forEach(message -> {
					if (severityLevel.equals(message.getSeverity())) {
						builder.append("\n\t\t")//
								.append(message.getSeverity())//
								.append(" ")//
								.append(message.getLocationLine() + "/" + message.getLocationCol()) //
								.append(" ")//
								.append(message.getLocationString()) //
								.append(" ")//
								.append(message.getMessage());
					}
				});
			}
		});

		builder.append("\n\nConceptMap UniqueCheck=");
		conceptMapUniqueCheckResults.forEach((id, cm) -> {
			List<UniqueCheckResultItem> errors = cm.getResultItems().stream()
					.filter(filter -> severityLevel.equals(filter.getSeverity())).collect(Collectors.toList());
			errors.forEach(message -> {
				builder.append("\n\t\t")//
						.append(message.getSeverity())//
						.append(", source ")//
						.append(((ConceptMapUniqueCheckResultItem) message).getSystem())//
						.append("|")//
						.append(((ConceptMapUniqueCheckResultItem) message).getCode())//
						.append("|'")//
						.append(((ConceptMapUniqueCheckResultItem) message).getDisplay());
				if (StringUtils.isNoneEmpty(((ConceptMapUniqueCheckResultItem) message).getTargetSystem())) {
					builder.append("', target ")//
							.append(((ConceptMapUniqueCheckResultItem) message).getTargetSystem())//
							.append("|")//
							.append(((ConceptMapUniqueCheckResultItem) message).getTargetCode())//
							.append("|'")//
							.append(((ConceptMapUniqueCheckResultItem) message).getTargetDisplay());
				} else {
					builder.append("'");
				}
				builder.append(": ")//
						.append(message.getMessage());
			});
		});

		builder.append("\n]");
		return builder.toString();
	}

	public ResultSeverityEnum getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(ResultSeverityEnum severityLevel) {
		this.severityLevel = severityLevel;
	}

}
