/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.validation.SingleValidationMessage;

/**
 * 
 */
public class UniqueCheckResult {

	private List<UniqueCheckResultItem> resultItems;

	public List<UniqueCheckResultItem> getResultItems() {
		if (resultItems == null) {
			resultItems = new ArrayList<UniqueCheckResultItem>();
		}
		return resultItems;
	}

	public void setResultItems(List<UniqueCheckResultItem> resultItems) {
		this.resultItems = resultItems;
	}

	public void add(UniqueCheckResultItem sm) {
		getResultItems().add(sm);
	}

}
