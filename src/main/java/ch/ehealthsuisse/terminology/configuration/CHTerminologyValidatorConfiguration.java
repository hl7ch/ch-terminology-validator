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
	
	private FhirContext ctxR4;

	@Bean
	FhirContext fhirContext() {
		if (ctxR4 == null) {
			ctxR4 = FhirContext.forR4();
		}
		return ctxR4;
	}

}
