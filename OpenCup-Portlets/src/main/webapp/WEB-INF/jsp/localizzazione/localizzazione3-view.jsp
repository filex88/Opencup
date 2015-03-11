<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<portlet:defineObjects />

<div class="localizzazioneSubtitle">
<strong>Localizzazione dei progetti: </strong><span>Regione ${regionName}</span>
</div>

<fmt:setLocale value="it_IT"/>

<div class="summaryContainer">
	<div style="text-align: justify;">
	La sintesi per Localizzazione mette in evidenza i dati aggregati della totalità dei progetti, è possibile proseguire nei dati aggregati navigando nelle ulteriori classificazioni:
	Regione > Provincia
	</div>
	<div style="text-align: right; padding-top: 15px">
	<a href="#">
		Vedi Elenco Progetti <i class="icon-list"></i>
	</a>
</div>
	<liferay-ui:search-container searchContainer="${searchContainerSummary}" delta="${searchContainerSummary.delta}"  deltaParam="aggregata_delta">
	
	<liferay-ui:search-container-results results="${searchContainerSummary.results}" total="${searchContainerSummary.total}"/>    
	
	<liferay-ui:search-container-row className="it.dipe.opencup.dto.DescrizioneValore" modelVar="descrizioneValore">
		
		<liferay-ui:search-container-column-text name="SINTESI PROGETTI">
			${descrizioneValore.label}
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text>
			<c:choose>
				<c:when test="${'VOLUME DEI PROGETTI' eq descrizioneValore.label}">
					<span class="pull-right"><fmt:formatNumber value="${descrizioneValore.value}" type="number" minIntegerDigits="1"/></span>
				</c:when>
				<c:otherwise>
					<span class="pull-right"><fmt:formatNumber value="${descrizioneValore.value}" type="currency" minIntegerDigits="1" minFractionDigits="2"/></span>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
		
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator searchContainer="${searchContainerSummary}"/>
	
</liferay-ui:search-container>
</div>

<div class="mapContainer">
<div id="italybymacroareas"></div>
<div id="dimensions">
	<ul>
		<li id="volumeLabel">
			<input type="button" value="VOLUME"></input>
		</li>
		<li id="costoLabel">
			<input type="button" value="COSTO"></input>
		</li>
		<li id="importoLabel">
			<input type="button" value="FINANZIATO"></input>
		</li>
	</ul>
</div>

</div>

<div class="clear"></div>

<div class="detailContainer">

<c:set var="singlequote" value="'"/>
<c:set var="dollar" value="$"/>

<liferay-ui:search-container searchContainer="${searchContainerDistinct}" delta="${searchContainerDistinct.delta}"  orderByType="${searchContainerDistinct.orderByType}"  deltaParam="delta">

	<liferay-ui:search-container-results results="${searchContainerDistinct.results}" total="${searchContainerDistinct.total}"/>    

    <liferay-ui:search-container-row className="it.dipe.opencup.dto.LocalizationValueConverter" keyProperty="localizationLabel" modelVar="localizzazioneValue">
			
		<liferay-ui:search-container-column-text name="PROVINCIA">
			<span  class="pull-right">${fn:replace(localizzazioneValue.fullLabel,dollar,singlequote)}</span>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="VOLUME PROGETTI" orderableProperty="volumeValue" orderable="true">
			<span class="pull-right"><fmt:formatNumber value="${localizzazioneValue.volumeValue}" type="number" minIntegerDigits="1"/></span>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="COSTO" orderableProperty="costoValue" orderable="true">
			<span class="pull-right"><fmt:formatNumber value="${localizzazioneValue.costoValue}" type="currency" minIntegerDigits="1" minFractionDigits="2"/></span>
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text name="IMPORTO FINANZIATO" orderableProperty="importoValue" orderable="true">
			<span class="pull-right"><fmt:formatNumber value="${localizzazioneValue.importoValue}" type="currency" minIntegerDigits="1"  minFractionDigits="2"/></span>
		</liferay-ui:search-container-column-text>
	
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="false" searchContainer="${searchContainerDistinct}"/>

</liferay-ui:search-container>
</div>

<script>
var regioneSelezionata="${selectedRegion}";
var jsonResultLocalizzazione=eval('('+'${jsonResultLocalizzazione}'+')');
var areaGeo="${areaGEO}";
</script>
