<%@page import="it.dipe.opencup.model.Regione"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>

<portlet:renderURL var="uriDialogRicerca" windowState="<%=LiferayWindowState.EXCLUSIVE.toString()%>">
	<portlet:param name="action" value="affinaricerca"/>
</portlet:renderURL>

<script type="text/javascript">
	var uriDialogRicerca = "${uriDialogRicerca}";
</script>

<div style="text-align: right;">

	<c:choose>
		<c:when test="${modelAttrNaturaRicerca.filtroClassificazione}">
			<aui:button id="affina-ricerca-natura" cssClass="affina-ricerca-natura btn-primary" value="AFFINA LA RICERCA <i class='icon-filter'></i>"></aui:button>
		</c:when>
		<c:otherwise>
			<aui:button id="affina-ricerca-natura" cssClass="affina-ricerca-natura" value="AFFINA LA RICERCA <i class='icon-filter'></i>"></aui:button>
		</c:otherwise>
	</c:choose>

</div>