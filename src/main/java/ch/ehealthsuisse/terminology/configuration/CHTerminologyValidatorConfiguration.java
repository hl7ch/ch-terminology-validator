/**
 * 
 */
package ch.ehealthsuisse.terminology.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;

/**
 * 
 */
@Configuration
public class CHTerminologyValidatorConfiguration {

	@Bean
	public FhirContext fhirContext() {
		return FhirContext.forR4();
	}

}
