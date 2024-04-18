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

	void validateCodeSystems(VaccineTerminology vacTerm, TerminologyValidationReport report);

	void validateValueSets(VaccineTerminology vacTerm, TerminologyValidationReport report);

	void validateConceptMaps(VaccineTerminology vacTerm, TerminologyValidationReport report);

}
