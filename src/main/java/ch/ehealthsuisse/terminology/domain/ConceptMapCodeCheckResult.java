/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ConceptMapCodeCheckResult {

	private List<ConceptMapCodeCheckResultItem> resultItems;

	public List<ConceptMapCodeCheckResultItem> getResultItems() {
		if (resultItems == null) {
			resultItems = new ArrayList<ConceptMapCodeCheckResultItem>();
		}
		return resultItems;
	}

	public void setResultItems(List<ConceptMapCodeCheckResultItem> resultItems) {
		this.resultItems = resultItems;
	}

	public void add(ConceptMapCodeCheckResultItem sm) {
		getResultItems().add(sm);
	}

}
