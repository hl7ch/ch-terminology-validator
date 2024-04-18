/**
 * 
 */
package ch.ehealthsuisse.terminology.validators.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ValueSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import ch.ehealthsuisse.terminology.domain.CodeSystemUniqueCheckResultItem;
import ch.ehealthsuisse.terminology.domain.ConceptMapUniqueCheckResultItem;
import ch.ehealthsuisse.terminology.domain.TerminologyValidationReport;
import ch.ehealthsuisse.terminology.domain.UniqueCheckResult;
import ch.ehealthsuisse.terminology.domain.UniqueCheckResultItem;
import ch.ehealthsuisse.terminology.domain.VaccineTerminology;
import ch.ehealthsuisse.terminology.domain.ValueSetUniqueCheckResultItem;
import ch.ehealthsuisse.terminology.validators.VaccineTerminologyValidator;

/**
 * 
 */
@Component
public class VaccineTerminologyValidatorImpl implements VaccineTerminologyValidator {

	@Autowired
	private FhirContext fhirContext;

	@Override
	public void validateCodeSystems(VaccineTerminology vacTerm, TerminologyValidationReport report) {
		List<CodeSystem> codeSystems = vacTerm.getCodeSystems();

		FhirValidator validator = getValidator();

		codeSystems.forEach(codeSystem -> {
			ValidationResult result = validator.validateWithResult(codeSystem);
			report.addCodeSystemValidationResult(codeSystem.getId(), result);

			UniqueCheckResult ucResult = new UniqueCheckResult();
			Map<String, String> checkSet = new HashMap<>();
			codeSystem.getConcept().forEach(concept -> {
				if (checkSet.containsKey(concept.getCode())) {
					CodeSystemUniqueCheckResultItem sm = new CodeSystemUniqueCheckResultItem()//
							.setSeverity(ResultSeverityEnum.ERROR)//
							.setCode(concept.getCode())//
							.setDisplay(concept.getDisplay())//
							.setMessage("The code '" + concept.getCode() + "|" + concept.getDisplay()
									+ "' occures more than one time.");
					ucResult.add(sm);

				} else {
					checkSet.put(concept.getCode(), concept.getDisplay());
				}

			});
			report.addCodeSystemUniqueCheckResult(codeSystem.getId(), ucResult);

		});

	}

	@Override
	public void validateValueSets(VaccineTerminology vacTerm, TerminologyValidationReport report) {
		List<ValueSet> valueSets = vacTerm.getValueSets();

		FhirValidator validator = getValidator();

		valueSets.forEach(valueSet -> {
			ValidationResult result = validator.validateWithResult(valueSet);
			report.addValueSetValidationResult(valueSet.getId(), result);

			UniqueCheckResult ucResult = new UniqueCheckResult();
			valueSet.getCompose().getInclude().forEach(include -> {
				Map<String, String> checkSet = new HashMap<>();

				include.getConcept().forEach(concept -> {
					if (checkSet.containsKey(concept.getCode())) {
						ValueSetUniqueCheckResultItem sm = new ValueSetUniqueCheckResultItem()//
								.setSeverity(ResultSeverityEnum.ERROR)//
								.setSystem(include.getSystem())//
								.setCode(concept.getCode())//
								.setDisplay(concept.getDisplay())//
								.setMessage("The code '" + include.getSystem() + "|" + concept.getCode() + "|"
										+ concept.getDisplay() + "' occures more than one time.");
						ucResult.add(sm);
					} else {
						checkSet.put(concept.getCode(), concept.getDisplay());
					}
				});
			});
			report.addValueSetUniqueCheckResult(valueSet.getId(), ucResult);

		});

	}

	@Override
	public void validateConceptMaps(VaccineTerminology vacTerm, TerminologyValidationReport report) {
		List<ConceptMap> conceptMaps = vacTerm.getConceptMaps();

		FhirValidator validator = getValidator();

		conceptMaps.forEach(conceptMap -> {
			ValidationResult result = validator.validateWithResult(conceptMap);
			report.addConceptMapValidationResult(conceptMap.getId(), result);

			UniqueCheckResult ucResult = new UniqueCheckResult();
			conceptMap.getGroup().forEach(group -> {

				group.getSource();
				group.getTarget();
				Map<String, String> checkSetElement = new HashMap<>();
				group.getElement().forEach(element -> {
					if (checkSetElement.containsKey(element.getCode())) {
//						SingleValidationMessage sm = new SingleValidationMessage();
//						sm.setSeverity(ResultSeverityEnum.ERROR);
//						sm.setLocationCol(0);
//						sm.setLocationLine(0);
//						sm.setLocationString("Unique Code Test");
//						sm.setMessage("The element code '" + group.getSource() + "|" + element.getCode() + "' "
//								+ element.getDisplay() + " occures more than one time.");
//
//						singleMessages.add(sm);

						ConceptMapUniqueCheckResultItem sm = new ConceptMapUniqueCheckResultItem()//
								.setSeverity(ResultSeverityEnum.ERROR)//
								.setSystem(group.getSource())//
								.setCode(element.getCode()) //
								.setDisplay(element.getDisplay()) //
								.setMessage("The element code '" + group.getSource() + "|" + element.getCode() + "|"
										+ element.getDisplay() + "' occures more than one time.");
						ucResult.add(sm);
					} else {
						checkSetElement.put(element.getCode(), element.getDisplay());
					}

					Map<String, String> checkSetTarget = new HashMap<>();
					element.getTarget().forEach(target -> {
						if (checkSetTarget.containsKey(target.getCode())) {

							ConceptMapUniqueCheckResultItem sm = new ConceptMapUniqueCheckResultItem()//
									.setSeverity(ResultSeverityEnum.ERROR)//
									.setSystem(group.getSource())//
									.setCode(element.getCode()) //
									.setDisplay(element.getDisplay())//
									.setTargetSystem(group.getTarget()) //
									.setTargetCode(target.getCode()) //
									.setMessage("The element code '" + group.getTarget() + "|" + target.getCode() + "|"
											+ target.getDisplay() + "' occures more than one time as target.");
							ucResult.add(sm);

						} else {
							checkSetTarget.put(target.getCode(), target.getDisplay());
						}

					});
				});

			});

			report.addConceptMapUniqueCheckResult(conceptMap.getId(), ucResult);
		});
	}

	private FhirValidator getValidator() {
		FhirValidator validator = fhirContext.newValidator();

		IValidatorModule validatorModule = new FhirInstanceValidator(fhirContext);
		validator.registerValidatorModule(validatorModule);
		return validator;
	}

}
