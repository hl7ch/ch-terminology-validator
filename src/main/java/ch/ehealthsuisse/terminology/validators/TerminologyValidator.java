/**
 * 
 */
package ch.ehealthsuisse.terminology.validators;

import ch.ehealthsuisse.terminology.domain.TerminologyValidationReport;
import ch.ehealthsuisse.terminology.domain.GenericTerminology;

/**
 * 
 */
public interface TerminologyValidator {

	/**
	 * Methdo to do CodeSystem validation checks.
	 * 
	 * @param vacTerm the Terminology object for vaccination
	 * @param report  the Validation report
	 */
	void validateCodeSystems(GenericTerminology vacTerm, TerminologyValidationReport report);

	/**
	 * Method to do ValueSet validation checks.
	 * 
	 * @param vacTerm the Terminology object for vaccination
	 * @param report  the Validation report
	 */
	void validateValueSets(GenericTerminology vacTerm, TerminologyValidationReport report);

	/**
	 * Method to do ConceptMap validation checks.
	 * 
	 * @param vacTerm the Terminology object for vaccination
	 * @param report  the Validation report
	 */
	void validateConceptMaps(GenericTerminology vacTerm, TerminologyValidationReport report);

}
