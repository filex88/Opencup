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
<div style="text-align: justify;">
	La sintesi per Natura mette in evidenza i dati aggregati della totalità dei progetti, è possibile proseguire nei dati aggregati navigando nelle ulteriori classificazioni:
	Natura > Settore > Sottosettori > Categoria
</div>
                                                                        
<liferay-portlet:renderURL var="linkURLvedielencoprogetti" portletName="elencoprogettiportlet_WAR_OpenCupPortletsportlet" portletMode="view" windowState="maximized">
	<liferay-portlet:param name="rowIdLiv1" value="1"/>
	<liferay-portlet:param name="rowIdLiv2" value="0"/>
	<liferay-portlet:param name="rowIdLiv3" value="-1"/>
	<liferay-portlet:param name="rowIdLiv4" value="-1"/>
	<liferay-portlet:param name="action" value="PublishEvent"/>
</liferay-portlet:renderURL>

<div style="text-align: right; padding-top: 15px">
	<aui:a href="<%= linkURLvedielencoprogetti %>">
		Vedi Elenco Progetti <i class="icon-list"></i>
	</aui:a>
</div>

<fmt:setLocale value="it_IT"/>

<liferay-ui:search-container searchContainer="${searchContainer}" delta="${searchContainer.delta}" deltaParam="aggregata_delta">
	
	<liferay-ui:search-container-results results="${searchContainer.results}" total="${searchContainer.total}"/>    
	
	<liferay-ui:search-container-row className="it.dipe.opencup.dto.DescrizioneValore" modelVar="descrizioneValore">
		
		<liferay-ui:search-container-column-text name="sintesi-progetti">
			${descrizioneValore.label}
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text>
			<c:choose>
				<c:when test="${'VOLUME DEI PROGETTI' eq descrizioneValore.label}">
					<span class="pull-right"><fmt:formatNumber value="${descrizioneValore.value}" type="number" minIntegerDigits="1"/></span>
				</c:when>
				<c:otherwise>
					<span class="pull-right"><fmt:formatNumber value="${descrizioneValore.value}" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
		
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator searchContainer="${searchContainer}"/>
	
</liferay-ui:search-container>