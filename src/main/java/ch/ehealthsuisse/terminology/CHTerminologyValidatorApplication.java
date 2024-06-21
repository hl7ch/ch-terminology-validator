package ch.ehealthsuisse.terminology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.ehealthsuisse.terminology.services.BusinessService;

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
