/**
 * 
 */
package ch.ehealthsuisse.terminology.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ValueSetCodeCheckResult {
	private List<ValueSetCodeCheckResultItem> resultItems;

	public List<ValueSetCodeCheckResultItem> getResultItems() {
		if (resultItems == null) {
			resultItems = new ArrayList<ValueSetCodeCheckResultItem>();
		}
		return resultItems;
	}

	public void setResultItems(List<ValueSetCodeCheckResultItem> resultItems) {
		this.resultItems = resultItems;
	}

	public void add(ValueSetCodeCheckResultItem sm) {
		getResultItems().add(sm);
	}
}
