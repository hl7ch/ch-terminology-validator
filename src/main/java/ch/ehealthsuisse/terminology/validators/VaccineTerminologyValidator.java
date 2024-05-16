/**
 * 
 */
package ch.ehealthsuisse.terminology.validators;

import ch.ehealthsuisse.terminology.domain.TerminologyValidationReport;
import ch.ehealthsuisse.terminology.domain.VaccineTerminology;

/**
 * 
 */
public interface VaccineTerminologyValidator {

	/**
	 * Methdo to do CodeSystem validation checks.
	 * 
	 * @param vacTerm the Terminology object for vaccination
	 * @param report  the Validation report
	 */
	void validateCodeSystems(VaccineTerminology vacTerm, TerminologyValidationReport report);

	/**
	 * Method to do ValueSet validation checks.
	 * 
	 * @param vacTerm the Terminology object for vaccination
	 * @param report  the Validation report
	 */
	void validateValueSets(VaccineTerminology vacTerm, TerminologyValidationReport report);

	/**
	 * Method to do ConceptMap validation checks.
	 * 
	 * @param vacTerm the Terminology object for vaccination
	 * @param report  the Validation report
	 */
	void validateConceptMaps(VaccineTerminology vacTerm, TerminologyValidationReport report);

}
