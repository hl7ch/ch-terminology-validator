/**
 * 
 */
package ch.ehealthsuisse.terminology.loaders.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ValueSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ch.ehealthsuisse.terminology.domain.GenericTerminology;
import ch.ehealthsuisse.terminology.loaders.TerminologyLoader;
import ch.ehealthsuisse.terminology.utils.FhirFileParser;

/**
 * 
 */
@Component
public class TerminologyLoaderImpl implements TerminologyLoader {

	@Autowired
	private FhirContext fhirContext;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public GenericTerminology loadTerminologyResources(String path) {
		GenericTerminology retVal = new GenericTerminology();
		File[] files = new File(path).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File basePath, String filename) {
				if (filename != null && (filename.endsWith(".json") || filename.endsWith(".xml"))) {
					log.info("" + basePath + ", " + filename);
					return true;
				}
				return false;
			}
		});

		if (files != null) {

			List<CodeSystem> codeSystems = getCodeSystems(files);
			retVal.setCodeSystems(codeSystems);

			List<ValueSet> valueSets = getValueSets(files);
			retVal.setValueSets(valueSets);

			List<ConceptMap> conceptMaps = getConceptMaps(files);
			retVal.setConceptMaps(conceptMaps);
		}

		return retVal;
	}

	private List<CodeSystem> getCodeSystems(File[] files) {
		List<CodeSystem> retVal = new ArrayList<>();
		for (File file : files) {
			if (file.getName().contains("CodeSystem")) {
				CodeSystem cs = new FhirFileParser<CodeSystem>(fhirContext).parseFile(file, CodeSystem.class);
				if (cs != null) {
					retVal.add(cs);
				}
			}
		}
		return retVal;
	}

	private List<ValueSet> getValueSets(File[] files) {
		List<ValueSet> retVal = new ArrayList<>();
		for (File file : files) {
			if (file.getName().contains("ValueSet")) {
				ValueSet cs = new FhirFileParser<ValueSet>(fhirContext).parseFile(file, ValueSet.class);
				if (cs != null) {
					retVal.add(cs);
				}
			}
		}
		return retVal;
	}

	private List<ConceptMap> getConceptMaps(File[] files) {
		List<ConceptMap> retVal = new ArrayList<>();
		for (File file : files) {
			if (file.getName().contains("ConceptMap")) {
				ConceptMap cs = new FhirFileParser<ConceptMap>(fhirContext).parseFile(file, ConceptMap.class);
				if (cs != null) {
					retVal.add(cs);
				}
			}
		}
		return retVal;
	}

}
