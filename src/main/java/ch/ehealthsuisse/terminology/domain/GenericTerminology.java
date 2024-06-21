/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ValueSet;

/**
 * 
 */
public class GenericTerminology {

	private List<CodeSystem> codeSystems;
	private List<ValueSet> valueSets;
	private List<ConceptMap> conceptMaps;

	public void setCodeSystems(List<CodeSystem> codeSystems) {
		this.codeSystems = codeSystems;
	}

	public void setValueSets(List<ValueSet> valueSets) {
		this.valueSets = valueSets;
	}

	public void setConceptMaps(List<ConceptMap> conceptMaps) {
		this.conceptMaps = conceptMaps;
	}

	public List<CodeSystem> getCodeSystems() {
		if (codeSystems == null) {
			codeSystems = new ArrayList<>();
		}
		return codeSystems;
	}

	public List<ValueSet> getValueSets() {
		if (valueSets == null) {
			valueSets = new ArrayList<>();
		}
		return valueSets;
	}

	public List<ConceptMap> getConceptMaps() {
		if (conceptMaps == null) {
			conceptMaps = new ArrayList<>();
		}
		return conceptMaps;
	}

	@Override
	public String toString() {
		return "Terminology [codeSystems=" + getCodeSystems().size() + ", valueSets=" + getValueSets().size() + ", conceptMaps="
				+ getConceptMaps().size() + "]";
	}

}
