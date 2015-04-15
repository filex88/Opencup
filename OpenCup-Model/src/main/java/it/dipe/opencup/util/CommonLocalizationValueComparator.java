package it.dipe.opencup.util;

import it.dipe.opencup.dto.LocalizationValueConverter;

import java.util.Comparator;

public class CommonLocalizationValueComparator implements Comparator<LocalizationValueConverter>{

	private String nomeProperty;
	private String ordinamento;
	
	private static final String VOLUME="volumeValue";
	private static final String COSTO="costoValue";
	private static final String IMPORTO="importoValue";
	private static final String ASCENDENTE="asc";
	
	
	public CommonLocalizationValueComparator(String nomeProperty,String ordinamento ) {
		this.nomeProperty=nomeProperty;
		this.ordinamento=ordinamento;
	}

	@Override
	public int compare(LocalizationValueConverter o1, LocalizationValueConverter o2) {
		if (nomeProperty.equalsIgnoreCase(VOLUME)){
			return confrontaInteri(o1.getVolumeValue(), o2.getVolumeValue(), ordinamento);
		}
		else if(nomeProperty.equalsIgnoreCase(COSTO)){
			return confrontaDouble(new Double(o1.getCostoValue()), o2.getCostoValue(), ordinamento);
		}
		else if (nomeProperty.equalsIgnoreCase(IMPORTO)){ //IMPORTO
			return confrontaDouble(new Double(o1.getImportoValue()), o2.getImportoValue(), ordinamento);
		}
		
		return 0;
	}
	
	private int confrontaInteri(Long valore1, Long valore2, String ordinamento){
		int result=0;
		if (valore1!=null && valore2!=null){
			if (valore1.intValue()>valore2.intValue() ){
				result=ordinamento.equalsIgnoreCase(ASCENDENTE)?+1:-1;
			}
			else if (valore1.intValue()<valore2.intValue()){
				result=ordinamento.equalsIgnoreCase(ASCENDENTE)?-1:+1;
			}
	 	}
		return result;
	 }
	
	private int confrontaDouble(Double valore1,Double valore2,String ordinamento){
		int result=0;
		if (valore1!=null && valore2!=null){
			if (valore1.doubleValue()>valore2.doubleValue() ){
				result=ordinamento.equalsIgnoreCase(ASCENDENTE)?+1:-1;
			}
			else if (valore1.doubleValue()<valore2.doubleValue()){
				result=ordinamento.equalsIgnoreCase(ASCENDENTE)?-1:+1;
			}
		}
		return result;
	 }
	
	
}
