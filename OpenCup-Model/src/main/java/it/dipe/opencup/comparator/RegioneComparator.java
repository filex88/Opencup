package it.dipe.opencup.comparator;

import it.dipe.opencup.model.Aggregata;

import java.util.Comparator;

public class RegioneComparator implements Comparator<Aggregata> {
	@Override
	public int compare(Aggregata o1, Aggregata o2) {
		return o1.getLocalizzazione().getDescRegione().compareTo( o2.getLocalizzazione().getDescRegione() ); 
	}
}
