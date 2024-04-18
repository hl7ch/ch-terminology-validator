package ch.ehealthsuisse.terminology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ch.ehealthsuisse.terminology.domain.TerminologyValidationReport;
import ch.ehealthsuisse.terminology.domain.VaccineTerminology;
import ch.ehealthsuisse.terminology.loaders.TerminologyLoader;
import ch.ehealthsuisse.terminology.validators.VaccineTerminologyValidator;

@SpringBootApplication
public class CHTerminologyValidatorApplication implements ApplicationRunner {

	private static String RESOURCE_DIR = "dir";
	private static String VACD_TERM = "vacd";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TerminologyLoader resourceLoader;

	@Autowired
	private VaccineTerminologyValidator vaccineTerminologyValidator;

	public static void main(String[] args) {
		SpringApplication.run(CHTerminologyValidatorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("start TerminologyValidatorApplication");
		if (args.containsOption(RESOURCE_DIR) && //
				args.getOptionValues(RESOURCE_DIR) != null && //
				args.getOptionValues(RESOURCE_DIR).size() > 0) //
		{
			logger.info(RESOURCE_DIR + " " + args.getOptionValues(RESOURCE_DIR).get(0));

			if (args.containsOption(VACD_TERM)) {
				logger.info("Check " + VACD_TERM);
				VaccineTerminology vacTerm = resourceLoader
						.loadVaccineTerminologyResources(args.getOptionValues(RESOURCE_DIR).get(0));
				logger.info("Terminology loaded: " + vacTerm);

				TerminologyValidationReport report = new TerminologyValidationReport();
				vaccineTerminologyValidator.validateCodeSystems(vacTerm, report);

				vaccineTerminologyValidator.validateValueSets(vacTerm, report);

				vaccineTerminologyValidator.validateConceptMaps(vacTerm, report);
				
								
				report.setSeverityLevel(ResultSeverityEnum.ERROR);
				
				logger.info("Validation report:\n" + report);
			}

		}
		logger.info("finish TerminologyValidatorApplication");

	}

}
