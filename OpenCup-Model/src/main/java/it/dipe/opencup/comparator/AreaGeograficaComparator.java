package it.dipe.opencup.comparator;

import it.dipe.opencup.model.Aggregata;

import java.util.Comparator;

public class AreaGeograficaComparator implements Comparator<Aggregata> {
	@Override
	public int compare(Aggregata o1, Aggregata o2) {
		return AreaGeografica.valueOf(o1.getLocalizzazione().getDescAreaGeografica().replace("-", "")).compareTo( AreaGeografica.valueOf(o2.getLocalizzazione().getDescAreaGeografica().replace("-", "")) ); 
	}
	
	enum AreaGeografica {
		NORDOVEST(0), NORDEST(1), CENTRO(2), SUD(3), ISOLE(4);
		int position;
		AreaGeografica(int p) {
			position = p;
		}
		int getPosition(){
			return position;
		}
	}

}
