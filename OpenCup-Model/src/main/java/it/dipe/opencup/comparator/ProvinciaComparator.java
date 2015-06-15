package it.dipe.opencup.comparator;

import java.util.Comparator;

import it.dipe.opencup.model.Aggregata;

public class ProvinciaComparator implements Comparator<Aggregata> {
	@Override
	public int compare(Aggregata o1, Aggregata o2) {
		return o1.getLocalizzazione().getDescProvincia().compareTo( o2.getLocalizzazione().getDescProvincia() ); 
	}
}
