/**
 * 
 */
package ch.ehealthsuisse.terminology.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ibm.icu.text.SimpleDateFormat;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;
import ch.ehealthsuisse.terminology.CHTerminologyValidatorConstants;
import ch.ehealthsuisse.terminology.domain.TerminologyValidationReport;
import ch.ehealthsuisse.terminology.domain.VaccineTerminology;
import ch.ehealthsuisse.terminology.domain.serializer.CustomValidationResultSerializer;
import ch.ehealthsuisse.terminology.loaders.TerminologyLoader;
import ch.ehealthsuisse.terminology.services.BusinessService;
import ch.ehealthsuisse.terminology.validators.VaccineTerminologyValidator;

/**
 * 
 */
@Service
public class BusinessServiceImpl implements BusinessService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TerminologyLoader resourceLoader;

	@Autowired
	private VaccineTerminologyValidator vaccineTerminologyValidator;

	@Override
	public void runValidations(ApplicationArguments args) {
		if (args.containsOption(CHTerminologyValidatorConstants.VACD_TERM)) {
			logger.info("Check " + CHTerminologyValidatorConstants.VACD_TERM);
			VaccineTerminology vacTerm = resourceLoader.loadVaccineTerminologyResources(
					args.getOptionValues(CHTerminologyValidatorConstants.RESOURCE_DIR).get(0));
			logger.info("Terminology loaded: " + vacTerm);

			TerminologyValidationReport report = new TerminologyValidationReport();
			report.setResourceDirectory(args.getOptionValues(CHTerminologyValidatorConstants.RESOURCE_DIR).get(0));
			report.setResourceStatistics(vacTerm.toString());

			vaccineTerminologyValidator.validateCodeSystems(vacTerm, report);

			vaccineTerminologyValidator.validateValueSets(vacTerm, report);

			vaccineTerminologyValidator.validateConceptMaps(vacTerm, report);

			report.setValidationSeverityLevel(ResultSeverityEnum.ERROR);
			report.setCheckSeverityLevel(ResultSeverityEnum.WARNING);

			logger.info("Validation report:\n" + report);

			if (args.containsOption(CHTerminologyValidatorConstants.OUTPUTDIR)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
				try {

					ObjectMapper objectMapper = new ObjectMapper();
					SimpleModule module = new SimpleModule();
					module.addSerializer(ValidationResult.class, new CustomValidationResultSerializer());
					objectMapper.registerModule(module);

					objectMapper.writerWithDefaultPrettyPrinter().writeValue(
							new File(args.getOptionValues(CHTerminologyValidatorConstants.OUTPUTDIR).get(0),
									report.getClass().getSimpleName() + "_" + sdf.format(new Date()) + ".json"),
							report);
				} catch (IOException e) {
					logger.error("Error writing report output file", e);
				}

				try {
					OutputStream fos = new FileOutputStream(
							new File(args.getOptionValues(CHTerminologyValidatorConstants.OUTPUTDIR).get(0),
									report.getClass().getSimpleName() + "_" + sdf.format(new Date()) + ".txt"));
					fos.write(report.toString().getBytes());
				} catch (IOException e) {
					logger.error("Error writing report output file", e);
				}

			}

		}
	}

}
