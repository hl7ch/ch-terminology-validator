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
import ch.ehealthsuisse.terminology.services.BusinessService;
import ch.ehealthsuisse.terminology.validators.VaccineTerminologyValidator;

@SpringBootApplication
public class CHTerminologyValidatorApplication implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(getClass());



	@Autowired
	private BusinessService businessService;

	public static void main(String[] args) {
		SpringApplication.run(CHTerminologyValidatorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("start TerminologyValidatorApplication");
		if (args.containsOption(CHTerminologyValidatorConstants.RESOURCE_DIR) && //
				args.getOptionValues(CHTerminologyValidatorConstants.RESOURCE_DIR) != null && //
				args.getOptionValues(CHTerminologyValidatorConstants.RESOURCE_DIR).size() > 0) //
		{
			logger.info(CHTerminologyValidatorConstants.RESOURCE_DIR + " "
					+ args.getOptionValues(CHTerminologyValidatorConstants.RESOURCE_DIR).get(0));

			businessService.runValidations(args);

		}
		logger.info("finish TerminologyValidatorApplication");

	}

}
