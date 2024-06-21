/**
 * 
 */
package ch.ehealthsuisse.terminology.validators.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.CodeSystem.ConceptDefinitionComponent;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ValueSet;
import org.hl7.fhir.r4.model.ValueSet.ConceptReferenceComponent;
import org.hl7.fhir.r4.model.ValueSet.ConceptSetComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;
import ch.ehealthsuisse.terminology.domain.CodeSystemUniqueCheckResultItem;
import ch.ehealthsuisse.terminology.domain.ConceptMapCodeCheckResult;
import ch.ehealthsuisse.terminology.domain.ConceptMapCodeCheckResultItem;
import ch.ehealthsuisse.terminology.domain.ConceptMapUniqueCheckResultItem;
import ch.ehealthsuisse.terminology.domain.GenericTerminology;
import ch.ehealthsuisse.terminology.domain.TerminologyValidationReport;
import ch.ehealthsuisse.terminology.domain.UniqueCheckResult;
import ch.ehealthsuisse.terminology.domain.ValueSetCodeCheckResult;
import ch.ehealthsuisse.terminology.domain.ValueSetCodeCheckResultItem;
import ch.ehealthsuisse.terminology.domain.ValueSetUniqueCheckResultItem;
import ch.ehealthsuisse.terminology.validators.TerminologyValidator;

/**
 * 
 */
@Component
public class TerminologyValidatorImpl implements TerminologyValidator {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FhirContext fhirContext;

	@Override
	public void validateCodeSystems(GenericTerminology vacTerm, TerminologyValidationReport report) {
		List<CodeSystem> codeSystems = vacTerm.getCodeSystems();

		codeSystems.forEach(codeSystem -> {

			// do FHIR basic validation
			fhirValidation(codeSystem, report);

			// do unique check
			doUniqueCheck(codeSystem, report);

		});

	}

	@Override
	public void validateValueSets(GenericTerminology vacTerm, TerminologyValidationReport report) {
		List<ValueSet> valueSets = vacTerm.getValueSets();

		valueSets.forEach(valueSet -> {

			// do FHIR basic validation
			fhirValidation(valueSet, report);

			// do unique check
			doUniqueCheck(valueSet, report);

			// do codecheck
			doCodeCheck(valueSet, vacTerm, report);

		});

	}

	@Override
	public void validateConceptMaps(GenericTerminology vacTerm, TerminologyValidationReport report) {
		List<ConceptMap> conceptMaps = vacTerm.getConceptMaps();

		conceptMaps.forEach(conceptMap -> {

			// do FHIR basic validation
			fhirValidation(conceptMap, report);

			// do unique check
			doUniqueCheck(conceptMap, report);

			// do codecheck
			doCodeCheck(conceptMap, vacTerm, report);
		});
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private Methods
	// //////////////////////////////////////////////////////////////////////////

	private void doCodeCheck(ConceptMap conceptMap, GenericTerminology vacTerm, TerminologyValidationReport report) {

		ConceptMapCodeCheckResult cccr = new ConceptMapCodeCheckResult();

		Optional<ValueSet> sourceVsOpt = getValueSet(conceptMap.getSourceUriType().getValue(), vacTerm);
		Optional<ValueSet> targetVsOpt = getValueSet(conceptMap.getTargetUriType().getValue(), vacTerm);

		conceptMap.getGroup().forEach(group -> {

			if (sourceVsOpt.isPresent()) {
				group.getElement().forEach(element -> {
					if (!checkForCodeInValueSet(group.getSource(), element.getCode(), sourceVsOpt.get())) {
//						logger.error("The source code '" + group.getSource() + "|" + element.getCode() + "|"
//								+ element.getDisplay() + "' is not defined in the corresponding valueset.");

						cccr.add(new ConceptMapCodeCheckResultItem()//
								.setSeverity(ResultSeverityEnum.ERROR)//
								.setSystem(group.getSource())//
								.setCode(element.getCode()) //
								.setDisplay(element.getDisplay()) //
								.setMessage("The source code '" + group.getSource() + "|" + element.getCode() + "|"
										+ element.getDisplay() + "' is not defined in the corresponding valueset."));
					}

					if (targetVsOpt.isPresent()) {
						element.getTarget().forEach(target -> {
							if (!checkForCodeInValueSet(group.getTarget(), target.getCode(), targetVsOpt.get())) {
//								logger.error("The target code '" + group.getTarget() + "|" + target.getCode() + "|"
//										+ target.getDisplay() + "' is not defined in the corresponding valueset.");

								cccr.add(new ConceptMapCodeCheckResultItem()//
										.setSeverity(ResultSeverityEnum.ERROR)//
										.setSystem(group.getTarget())//
										.setCode(target.getCode()) //
										.setDisplay(target.getDisplay()) //
										.setMessage("The target code '" + group.getTarget() + "|" + target.getCode()
												+ "|" + target.getDisplay()
												+ "' is not defined in the corresponding valueset."));
							}

						});
					}
				});
			}
		});

		// check for codes in valueset but not in conceptmap
		if (sourceVsOpt.isPresent()) {
			sourceVsOpt.get().getCompose().getInclude().forEach(include -> {
				include.getConcept().forEach(concept -> {
					boolean notFound = true;
					for (ConceptMap.ConceptMapGroupComponent group : conceptMap.getGroup()) {
						for (ConceptMap.SourceElementComponent element : group.getElement()) {
							if (element.getCode().equals(concept.getCode())) {
								notFound = false;
								break;
							}
						}
					}
					if (notFound) {
//						logger.error("The source code '" + include.getSystem() + "|" + concept.getCode() + "|"
//								+ concept.getDisplay() + "' is defined in the valueset but not in the conceptmap.");

						cccr.add(new ConceptMapCodeCheckResultItem()//
								.setSeverity(ResultSeverityEnum.ERROR)//
								.setSystem(include.getSystem())//
								.setCode(concept.getCode()) //
								.setDisplay(concept.getDisplay()) //
								.setMessage("The source code '" + include.getSystem() + "|" + concept.getCode() + "|"
										+ concept.getDisplay()
										+ "' is defined in the valueset but not in the conceptmap."));
					}
				});
			});
		}

		report.getConceptMapCodeCheckResults().put(conceptMap.getId(), cccr);
	}

	private boolean checkForCodeInValueSet(String system, String code, ValueSet valueSet) {
		List<ConceptReferenceComponent> list = valueSet.getCompose().getInclude().stream()//
				.filter(filter -> filter.getSystem().equals(system))//
				.map(ConceptSetComponent::getConcept)//
				.flatMap(Collection::stream).collect(Collectors.toList());

		boolean retVal = list.stream().filter(filter2 -> filter2.getCode().equals(code))//
				.findAny().isPresent();

		return retVal;

//		return valueSet.getCompose().getInclude().stream()//
//				.filter(filter -> filter.getSystem().equals(system))//
//				.map(ConceptSetComponent::getConcept)//
//				.flatMap(Collection::stream).filter(filter2 -> filter2.getCode().equals(code))//
//				.findAny().isPresent();
	}

	private Optional<ValueSet> getValueSet(String url, GenericTerminology vacTerm) {
		return vacTerm.getValueSets().stream().filter(filter -> filter.getUrl().equals(url)).findFirst();
	}

	private void doUniqueCheck(ConceptMap conceptMap, TerminologyValidationReport report) {
		UniqueCheckResult ucResult = new UniqueCheckResult();
		conceptMap.getGroup().forEach(group -> {

			group.getSource();
			group.getTarget();
			Map<String, String> checkSetElement = new HashMap<>();
			group.getElement().forEach(element -> {
				if (checkSetElement.containsKey(element.getCode())) {
//					SingleValidationMessage sm = new SingleValidationMessage();
//					sm.setSeverity(ResultSeverityEnum.ERROR);
//					sm.setLocationCol(0);
//					sm.setLocationLine(0);
//					sm.setLocationString("Unique Code Test");
//					sm.setMessage("The element code '" + group.getSource() + "|" + element.getCode() + "' "
//							+ element.getDisplay() + " occures more than one time.");
//
//					singleMessages.add(sm);

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
	}

	private void doCodeCheck(ValueSet valueSet, GenericTerminology vacTerm, TerminologyValidationReport report) {
		ValueSetCodeCheckResult vsccr = new ValueSetCodeCheckResult();
		valueSet.getCompose().getInclude().forEach(include -> {
			// get codesystem if available
			Optional<CodeSystem> localCodeSystemOpt = getCodeSystem(include.getSystem(), vacTerm);
			if (localCodeSystemOpt.isPresent()) {
				// check for codes in valueset but not in codesysytem
				include.getConcept().forEach(concept -> {
					if (!checkForCodeInCodeSystem(concept.getCode(), localCodeSystemOpt.get())) {
						ValueSetCodeCheckResultItem sm = new ValueSetCodeCheckResultItem()//
								.setSeverity(ResultSeverityEnum.ERROR)//
								.setSystem(include.getSystem())//
								.setCode(concept.getCode())//
								.setDisplay(concept.getDisplay())//
								.setMessage("The code '" + include.getSystem() + "|" + concept.getCode() + "|"
										+ concept.getDisplay() + "' is not defined in the corresponding codesystem.");
						vsccr.add(sm);
					}

				});

				// check for codes defined in codesystem but not in valueset
				localCodeSystemOpt.get().getConcept().forEach(csconcept -> {
					boolean notFound = true;
					for (ConceptReferenceComponent vsconcept : include.getConcept()) {
						if (vsconcept.getCode().equals(csconcept.getCode())) {
							notFound = false;
							break;
						}
					}
					if (notFound) {
						ValueSetCodeCheckResultItem sm = new ValueSetCodeCheckResultItem()//
								.setSeverity(ResultSeverityEnum.WARNING)//
								.setSystem(include.getSystem())//
								.setCode(csconcept.getCode())//
								.setDisplay(csconcept.getDisplay())//
								.setMessage("The code '" + include.getSystem() + "|" + csconcept.getCode() + "|"
										+ csconcept.getDisplay()
										+ "' is defined in the codesystem but not in the valueset.");
						vsccr.add(sm);
					}
				});

			}
		});
		report.addValueSetCodeCheckResult(valueSet.getId(), vsccr);

	}

	private void doUniqueCheck(CodeSystem codeSystem, TerminologyValidationReport report) {
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
	}

	private void doUniqueCheck(ValueSet valueSet, TerminologyValidationReport report) {
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
	}

	private void fhirValidation(IBaseResource resource, TerminologyValidationReport report) {
		FhirValidator validator = getValidator();
		IIdType idElement = resource.getIdElement();
		ValidationResult result = validator.validateWithResult(resource);
		if (resource instanceof CodeSystem) {
			report.addCodeSystemValidationResult(idElement.getValueAsString(), result);
		} else if (resource instanceof ValueSet) {
			report.addValueSetValidationResult(idElement.getValueAsString(), result);
		} else if (resource instanceof ConceptMap) {
			report.addConceptMapValidationResult(idElement.getValueAsString(), result);
		}
	}

	private boolean checkForCodeInCodeSystem(String code, CodeSystem localCodeSystem) {
		boolean retVal = false;
		for (ConceptDefinitionComponent concept : localCodeSystem.getConcept()) {
			if (concept.getCode().equals(code)) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}

	private FhirValidator getValidator() {
		FhirValidator validator = fhirContext.newValidator();

		IValidatorModule validatorModule = new FhirInstanceValidator(fhirContext);
		validator.registerValidatorModule(validatorModule);
		return validator;
	}

	private Optional<CodeSystem> getCodeSystem(String system, GenericTerminology vacTerm) {
		return vacTerm.getCodeSystems().stream().filter(filter -> filter.getUrl().equals(system)).findAny();
	}

}
