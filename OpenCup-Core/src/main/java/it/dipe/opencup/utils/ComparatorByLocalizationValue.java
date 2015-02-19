package it.dipe.opencup.utils;

import it.dipe.opencup.dto.LocalizationValueConverter;

import java.util.Comparator;

public class ComparatorByLocalizationValue implements Comparator<LocalizationValueConverter>{

	@Override
	public int compare(LocalizationValueConverter arg0,LocalizationValueConverter arg1) {
		return Double.compare(arg0.getLocalizationValue(), arg1.getLocalizationValue());
	}

	
	
	

}
