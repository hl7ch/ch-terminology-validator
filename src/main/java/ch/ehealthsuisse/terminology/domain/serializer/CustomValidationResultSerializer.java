/**
 * 
 */
package ch.ehealthsuisse.terminology.domain.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.ValidationResult;

/**
 * 
 */
public class CustomValidationResultSerializer extends JsonSerializer<ValidationResult> {

	@Override
	public void serialize(ValidationResult value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeStartArray();
		gen.writeRaw(FhirContext.forR4().newJsonParser().encodeResourceToString(value.toOperationOutcome()));
		gen.writeEndArray();
	}

}
