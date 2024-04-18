/**
 * 
 */
package ch.ehealthsuisse.terminology.loaders;

import ch.ehealthsuisse.terminology.domain.VaccineTerminology;

/**
 * 
 */
public interface TerminologyLoader {

	VaccineTerminology loadVaccineTerminologyResources(String string);

}
