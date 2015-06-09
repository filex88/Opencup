<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="it.dipe.opencup.model.Progetto" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
Progetto progetti = (Progetto)row.getObject();
%>

<portlet:actionURL var="linkURLdettaglioprogetto" name="">
   	<portlet:param name="action" value="dettaglio"></portlet:param>
   	<portlet:param name="idProgettoDettaglio" value="<%= progetti.getId().toString() %>"></portlet:param>
   	<portlet:param name="currentAction" value="${currentAction}"></portlet:param>
   	<c:if test="${cercaPerKeyword!=null}">
   		<portlet:param name="cercaPerKeyword" value="${cercaPerKeyword}"></portlet:param>
   	</c:if>
</portlet:actionURL>
	
<div>

	<div>
		<p style="font-size: 1.2em;">
			<a href="${linkURLdettaglioprogetto}" class="link-url-dettaglio">
			<strong><%= progetti.getAnagraficaCup().getDescCup() %></strong></a>
		</p>
	</div>
	
	<div>
		<p style="font-size: 1.2em;">
			<span>CUP: <strong><%= progetti.getAnagraficaCup().getCodiCup() %></strong></span>
		</p>
	</div>
	
	<hr style="background-color: #1f4e78; width: 75%; height:2px">
	
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
		<p>
			<span>Comune: <%= progetti.getComuniProgetto() %></span>
		</p>
	</div>
	
	<%-- 
	<div>
	
		<div class="span8">
			<span>Comune: <%= progetti.getComuniProgetto() %></span>
		</div>
		
		<div class="span4" style="text-align: center;">
			<portlet:actionURL var="linkURLdettaglioprogetto" name="">
			   	<portlet:param name="action" value="dettaglio"></portlet:param>
			   	<portlet:param name="idProgettoDettaglio" value="<%= progetti.getId().toString() %>"></portlet:param>
			</portlet:actionURL>
			
			<aui:form 
				action="${linkURLdettaglioprogetto}" 
				method="post">
				<aui:button cssClass="btn-dett-progetto" data-id-progetto="<%= progetti.getId().toString() %>" value="DETTAGLIO PROGETTO" />
			</aui:form>
			
		</div>
		
		<div class="clear"></div>
		
	</div>
	--%>
	
	
</div>

<script type="text/javascript">

	AUI().use(
			'aui-base', 
			function(A) {
				/*
				A.all('.btn-dett-progetto').each(
						function() {
							this.setAttribute("type","submit");
						});
				*/
			}
			
	);
</script>

