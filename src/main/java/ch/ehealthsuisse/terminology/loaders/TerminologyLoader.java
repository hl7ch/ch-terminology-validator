/**
 * 
 */
package ch.ehealthsuisse.terminology.loaders;

import ch.ehealthsuisse.terminology.domain.GenericTerminology;

/**
 * 
 */
public interface TerminologyLoader {

	/**
	 * Method to load all resources for validaton.
	 * 
	 * @param directory the directory to look up the resources
	 * @return the instance of GenericTerminology
	 */
	GenericTerminology loadTerminologyResources(String string);

}
