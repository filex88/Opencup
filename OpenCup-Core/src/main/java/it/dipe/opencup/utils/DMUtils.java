package it.dipe.opencup.utils;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component("dmUtils")
public class DMUtils {
	
	public Criteria criteriaBinderOnlyOnePropertyNotNull(Criteria criteria, String propertyNotNull, String[] propertis) {
		
		criteria.add(Restrictions.isNotNull(propertyNotNull));
		
		for (int i = 0; i < propertis.length; i++) {
			if(!(propertis[i].equals(propertyNotNull))){
				criteria.add(Restrictions.isNull(propertis[i]));
			}
		}
		
		return criteria;
	}
	
	
	public Criteria criteriaBinderOneProperty(Criteria criteria, String property, String[] propertis) {
		
		criteria.add(Restrictions.gt(property, 0));
		
		for (int i = 0; i < propertis.length; i++) {
			if(!(propertis[i].equals(property))){
				criteria.add(Restrictions.lt(propertis[i], 0));
			}
		}
		
		return criteria;
	}


	public void criteriaBinder(Criteria criteria, String property1, int property1Value) {
		criteria.add(Restrictions.eq(property1, property1Value));
	}
	
	public void criteriaBinder(Criteria criteria, String property1, int property1Value, String property2, int property2Value) {
		criteriaBinder(criteria, property1, property1Value);
		criteria.add(Restrictions.eq(property2, property2Value));
	}
	
	public void criteriaBinder(Criteria criteria, String property1, int property1Value, String property2, int property2Value, String property3, int property3Value) {
		criteriaBinder(criteria, property1, property1Value, property2, property2Value);
		criteria.add(Restrictions.eq(property3, property3Value));
	}
	
	public void criteriaBinder(Criteria criteria, String property1, int property1Value, String property2, int property2Value, String property3, int property3Value, String property4, int property4Value) {
		criteriaBinder(criteria, property1, property1Value, property2, property2Value, property3, property3Value);
		criteria.add(Restrictions.eq(property4, property4Value));
	}
	

}
