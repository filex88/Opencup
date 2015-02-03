<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<portlet:defineObjects />

<a name="natura-portlet2"></a>


Continua la navigazione selezionando la <strong>${navigaPer}</strong> dei progetti

<fmt:setLocale value="it_IT"/>

<liferay-ui:search-container searchContainer="${searchContainer}" delta="${searchContainer.delta}" orderByType="${searchContainer.orderByType}" deltaParam="aggregata_delta">

	<liferay-ui:search-container-results results="${searchContainer.results}" total="${searchContainer.total}"/>    

    <liferay-ui:search-container-row className="it.dipe.opencup.dto.AggregataDTO" keyProperty="id" modelVar="aggregataDTO">
			
		<liferay-ui:search-container-column-text name="aggregato-des">
			<a href="#" onclick="return false;" data-url="${aggregataDTO.linkURL}" class="link-url-naviga-selezione">${aggregataDTO.descURL}</a>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="aggregato-volume" orderableProperty="numeProgetti" orderable="true">
			<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.numeProgetti}" type="number" minIntegerDigits="1"/></span>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="aggregato-costo" orderableProperty="impoCostoProgetti" orderable="true">
			<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.impoCostoProgetti}" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
			<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  minFractionDigits="3"/></span>
		</liferay-ui:search-container-column-text>
	
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator searchContainer="${searchContainer}"/>

</liferay-ui:search-container>

<form class="formEmpty" method="POST" action="#">
</form>