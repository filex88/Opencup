<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="it.dipe.opencup.model.Progetti" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
Progetti progetti = (Progetti)row.getObject();
%>

<div>
		
	<div class="span8">
		
		<div>
			<p>
				<strong>........</strong>
			</p>
		</div>
		
		<div>
			<p>
				<span>Anno decisione: <%= progetti.getAnnoDecisione().getAnnoDadeAnnoDecisione() %></span>
			</p>
		</div>
		
		<div>
			<p>
				<span>Categoria: <%= progetti.getCategoriaIntervento().getDescCategoriaIntervento() %></span>
			</p>
		</div>
		
		<div>
			<p> Comune:
				<c:forEach items="${ progetti.anagraficaCup.cupLocalizzazione }" var="localizzazione"> 
					<span>${ localizzazione.comune.descComune }</span>
				</c:forEach>
				
				
			</p>
		</div>
	</div>
	
	<div class="span4" style="text-align: center;">

		<liferay-portlet:renderURL var="linkURLdettaglioprogetto" portletName="dettaglioprogettoportlet_WAR_OpenCupPortletsportlet" portletMode="view" windowState="maximized">
			<liferay-portlet:param name="idProgettoDettaglio" value="<%= progetti.getId().toString() %>"/>
		</liferay-portlet:renderURL>
		
		<form method="POST" action="<%= linkURLdettaglioprogetto %>">
			<aui:button cssClass="btn-dett-progetto" data-id-progetto="<%= progetti.getId().toString() %>" value="DETTAGLIO PROGETTO" />
		</form>
			
	</div>
	
</div>


<script type="text/javascript">

	AUI().use(
			'aui-base', 
			function(A) {
				A.all('.btn-dett-progetto').each(
						function() {
							this.setAttribute("type","submit");
						});
			}
	);
</script>

