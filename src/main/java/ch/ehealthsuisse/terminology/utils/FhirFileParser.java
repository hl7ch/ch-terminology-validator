/**
 * 
 */
package ch.ehealthsuisse.terminology.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;

/**
 * 
 */
public class FhirFileParser<T extends IBaseResource> {

	private FhirContext fhirContext;

	public FhirFileParser(FhirContext fhirContext) {
		this.fhirContext = fhirContext;
	}

	public T parseFile(File file, Class<T> clazz) {
		T retVal = null;
		try {
			retVal = fhirContext.newJsonParser().parseResource(clazz, new FileInputStream(file));
		} catch (DataFormatException | FileNotFoundException e) {
			try {
				retVal = fhirContext.newXmlParser().parseResource(clazz, new FileInputStream(file));
			} catch (DataFormatException | FileNotFoundException e1) {
			}
		}

		return retVal;
	}

}
