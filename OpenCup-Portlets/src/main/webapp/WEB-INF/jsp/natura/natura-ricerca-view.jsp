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

//	var namespaceRicerca4js = "<portlet:namespace/>";
//	
//	var namespaceRicerca = "<portlet:namespace/>";
//	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
	var uriDialogRicerca = "${uriDialogRicerca}";

</script>

<div style="text-align: right;">

	<c:choose>
		<c:when test="${sessionAttrNaturaRicerca.filtroClassificazione}">
			<aui:button id="affina-ricerca-natura" cssClass="affina-ricerca-natura btn btn-primary" value="AFFINA LA RICERCA <i class='icon-filter'></i>"></aui:button>
		</c:when>
		<c:otherwise>
			<aui:button id="affina-ricerca-natura" cssClass="affina-ricerca-natura btn" value="AFFINA LA RICERCA <i class='icon-filter'></i>"></aui:button>
		</c:otherwise>
	</c:choose>

</div>

<%--

<div id="affina-ricerca-natura-modal"></div>
<div id="affina-ricerca-natura-modal-content" style="display: none">
	
	<portlet:actionURL var="myActionVar">
    	<portlet:param name="action" value="ricerca"></portlet:param>
	</portlet:actionURL>

	<aui:form action="${myActionVar}" method="post" name="affina-ricerca-natura-modal-content-stato-form" id="affina-ricerca-natura-modal-content-stato-form" cssClass="form-horizontal">	
		<div class="row">
			<div class="span10 offset1 ricerca">
				
				Localizzazione
				
				<div class="control-group" id="affina-ricerca-natura-modal-content-stato-div">
					<label class="control-label" for="affina-ricerca-natura-modal-content-stato">Stato</label>
					<div class="controls">
						<span>Italia</span>
					</div>
				</div>
				 
				<div class="control-group" id="affina-ricerca-natura-modal-content-regione-div">
					<label class="control-label" for="affina-ricerca-natura-modal-content-regione">Regione</label>
					<div class="controls">
						<span>
							<aui:select cssClass="input-xlarge affina-ricerca-natura-modal-content-regione" label="" bean="sessionAttrNaturaRicerca" name="idRegione" id="affina-ricerca-natura-modal-content-regione">
								<aui:option value="-1" label="ricerca.tutte" selected="${sessionAttrNaturaRicerca.idRegione == -1}"/>
								<c:forEach items="${listRegione}" var="regione" >
						            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${sessionAttrNaturaRicerca.idRegione == regione.id}"/>
						        </c:forEach>
							</aui:select>
						</span>
					</div>
				</div>
				
				<div class="control-group" id="affina-ricerca-natura-modal-content-provincia-div">
					<label class="control-label" for="affina-ricerca-natura-modal-content-provincia">Provincia</label>
					<div class="controls">
						<span>
							<aui:select cssClass="input-xlarge affina-ricerca-natura-modal-content-provincia" label="" bean="sessionAttrNaturaRicerca" name="idProvincia" id="affina-ricerca-natura-modal-content-provincia">
								<aui:option value="-1" label="ricerca.tutte" selected="${sessionAttrNaturaRicerca.idProvincia == -1}"/>
								<c:forEach items="${listProvincia}" var="provincia" >
						            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${sessionAttrNaturaRicerca.idProvincia == provincia.id}"/>
						        </c:forEach>
							</aui:select>
						</span>
					</div>
				</div>
				
				<div class="control-group" id="affina-ricerca-natura-modal-content-comune-div">
					<label class="control-label" for="affina-ricerca-natura-modal-content-comune">Comune</label>
					<div class="controls">
						<span>
							<aui:select cssClass="input-xlarge affina-ricerca-natura-modal-content-comune" label="" bean="sessionAttrNaturaRicerca" name="idComune" id="affina-ricerca-natura-modal-content-comune">
								<aui:option value="-1" label="ricerca.tutte" selected="${sessionAttrNaturaRicerca.idComune == -1}"/>
								<c:forEach items="${listComune}" var="comune" >
						            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${sessionAttrNaturaRicerca.idComune == comune.id}"/>
						        </c:forEach>
							</aui:select>
						</span>
					</div>
				</div>
				
			</div>
		</div>
		
		
		<div class="row">
			<div class="span10 offset1 ricerca">
				
				Gerarchia Soggetto
				 
				<div class="control-group" id="affina-ricerca-natura-modal-content-categoria-soggetto-div">
					<label class="control-label" for="affina-ricerca-natura-modal-content-categoria-soggetto">Categoria Soggetto</label>
					<div class="controls">
						<span>
							<aui:select cssClass="input-xlarge affina-ricerca-natura-modal-content-categoria-soggetto" label="" bean="sessionAttrNaturaRicerca" name="idCategoriaSoggetto" id="affina-ricerca-natura-modal-content-categoria-soggetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${sessionAttrNaturaRicerca.idCategoriaSoggetto == -1}"/>
								<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
						            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${sessionAttrNaturaRicerca.idCategoriaSoggetto == categoriasoggetto.id}"/>
						        </c:forEach>
							</aui:select>
						</span>
					</div>
				</div>
				
				<div class="control-group" id="affina-ricerca-natura-modal-content-sotto-categoria-soggetto-div">
					<label class="control-label" for="affina-ricerca-natura-modal-content-sotto-categoria-soggetto">Sotto Categoria Soggetto</label>
					<div class="controls">
						<span>
							<aui:select cssClass="input-xlarge affina-ricerca-natura-modal-content-sotto-categoria-soggetto" label="" bean="sessionAttrNaturaRicerca" name="idSottoCategoriaSoggetto" id="affina-ricerca-natura-modal-content-sotto-categoria-soggetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${sessionAttrNaturaRicerca.idSottoCategoriaSoggetto == -1}"/>
								<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
						            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottoCategoriaSoggetto}" selected="${sessionAttrNaturaRicerca.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
						        </c:forEach>
							</aui:select>
						</span>
					</div>
				</div>
			
				
			</div>
		</div>
		
		<div class="row">
			<div class="span10 offset1 ricerca">
				
			</div>
		</div>
		
		<div class="form-actions">
			<aui:button cssClass="btn btn-primary" type="submit" value="Filtra"></aui:button>
		</div>
		
	</aui:form>
	
</div>

--%>