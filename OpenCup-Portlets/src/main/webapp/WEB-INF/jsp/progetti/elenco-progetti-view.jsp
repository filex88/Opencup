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

<liferay-theme:defineObjects/>

<aui:a href="<%=themeDisplay.getPortletDisplay().getURLBack()%>">Indietro</aui:a>

<div class="span12">
		
	<div class="span8">

		<fmt:setLocale value="it_IT" />

		<liferay-ui:search-container searchContainer="${searchContainer}" delta="${searchContainer.delta}" orderByType="${searchContainer.orderByType}" deltaParam="delta">
		
			<liferay-ui:search-container-results results="${searchContainer.results}" total="${searchContainer.total}"/>    
			
			<liferay-ui:search-container-row className="it.dipe.opencup.model.Progetti" keyProperty="id" modelVar="progetti">
				
				<liferay-ui:search-container-column-jsp name="aggregato-des" align="right" path="/WEB-INF/jsp/progetti/riga-elenco-progetti.jsp" />		
				
				<liferay-ui:search-container-column-text name="aggregato-costo" orderableProperty="impoCostoProgetto" orderable="true">
					<span class="pull-right"><fmt:formatNumber value="${progetti.impoCostoProgetto}" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
				</liferay-ui:search-container-column-text>
				
				<liferay-ui:search-container-column-text name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
					<span class="pull-right"><fmt:formatNumber value="${progetti.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  minFractionDigits="3"/></span>
				</liferay-ui:search-container-column-text>
		
			</liferay-ui:search-container-row>
		
			<liferay-ui:search-iterator searchContainer="${searchContainer}" />
		
		</liferay-ui:search-container>
	</div>
	
	<div class="span4">
	
		<h4>Filtri di ricerca <i class='icon-filter'></i></h4>
		
		<portlet:actionURL var="affinaRicercaActionVar">
		   	<portlet:param name="action" value="ricerca"></portlet:param>
		</portlet:actionURL>

		<aui:form 
			action="${affinaRicercaActionVar}" 
			method="post" 
			name="ricerca-form" 
			id="ricerca-form" 
			cssClass="form-horizontal">	
		
			<div class="control-group">
				<strong class="control-label">Localizzazione</strong>
				<div class="controls">&nbsp;</div>
			</div>
	
			<div class="control-group" id="stato-div">
				<label class="control-label" for="stato">Stato</label>
				<div class="controls">
					<aui:input type="text" value="${modelAttrFiltriRicercaElePj.descStato}" readonly="readonly" cssClass="input-large stato" label="" bean="modelAttrFiltriRicercaElePj" name="descStato" id="stato"></aui:input>
				</div>
			</div>
			
			<div class="control-group" id="area-geografica-div">
				<label class="control-label" for="areaGeografica">Area Geografica</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large area-geografica" label="" bean="modelAttrFiltriRicercaElePj" name="idAreaGeografica" id="areaGeografica">
						<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrFiltriRicercaElePj.idAreaGeografica == -1}"/>
						<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
				            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${modelAttrFiltriRicercaElePj.idAreaGeografica == areaGeografica.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>
				</div>
			</div>
			 
			<div class="control-group" id="regione-div">
				<label class="control-label" for="regione">Regione</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large regione" label="" bean="modelAttrFiltriRicercaElePj" name="idRegione" id="regione">
						<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrFiltriRicercaElePj.idRegione == -1}"/>
						<c:forEach items="${listRegione}" var="regione" >
				            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${modelAttrFiltriRicercaElePj.idRegione == regione.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group" id="provincia-div">
				<label class="control-label" for="provincia">Provincia</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large provincia" label="" bean="modelAttrFiltriRicercaElePj" name="idProvincia" id="provincia">
						<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrFiltriRicercaElePj.idProvincia == -1}"/>
						<c:forEach items="${listProvincia}" var="provincia" >
				            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${modelAttrFiltriRicercaElePj.idProvincia == provincia.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group" id="comune-div">
				<label class="control-label" for="comune">Comune</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large comune" label="" bean="modelAttrFiltriRicercaElePj" name="idComune" id="comune">
						<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrFiltriRicercaElePj.idComune == -1}"/>
						<c:forEach items="${listComune}" var="comune" >
				            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${modelAttrFiltriRicercaElePj.idComune == comune.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoComune" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<aui:button type="submit" value="CERCA" />
		</aui:form>
		
		
	</div>
	
</div>


<script type="text/javascript">

	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "naturaportletricerca_WAR_OpenCupPortletsportlet";
	
	AUI().use('aui-base', function(A) {
		
		function caricaCombo(pNamespaceRicerca, resourceId, pattern, target){
			
			var select = document.getElementById(target);
				select.options.length = 0;
				select.options[select.options.length] = new Option("Tutte", -1);
				
			if( pattern != -1 ){
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(pNamespaceRicerca);
				resourceURL.setResourceId(resourceId);
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setCopyCurrentRenderParameters(true);
				
				console.log(resourceURL.toString());
				
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				var responseData = this.get('responseData');
	           				A.Object.each(
	           						responseData.lista, 
	           						function(value, key){
	           							//console.log("label = " + value.label);
	           							select.options[select.options.length] = new Option(value.label, value.id);
	           				});
	           			},
	           			failure: function (e) {
	           				var message = this.get('responseData');
	           				alert("Ajax Error : "+message);	
	           			}
	       			}
	   			});
			}
		}
		
		A.one('.area-geografica').on(
			    'change',
			    function(event) {
			    	A.one('.regione').val(-1);
			    	A.one('.provincia').val(-1);
			    	A.one('.comune').val(-1);
			    	caricaCombo(namespaceRicerca, "loadRegioneByAreaGeografica", this.val(), namespaceRicerca4js+"regione");
				});
		
		A.one('.regione').on(
			    'change',
			    function(event) {
			    	A.one('.provincia').val(-1);
			    	A.one('.comune').val(-1);
			    	caricaCombo(namespaceRicerca, "loadProvinciaByRegione", this.val(), namespaceRicerca4js+"provincia");
				});
		
		A.one('.provincia').on(
			    'change',
			    function(event) {
			    	A.one('.comune').val(-1);
			    	caricaCombo(namespaceRicerca, "loadComuniByProvincia", this.val(), namespaceRicerca4js+"comune");
				});
		
		
		A.one('.pulisciElementoAreaGeografica').on(
			    'click',
			    function(event) {
			    	A.one('.area-geografica').val(-1);
			    	A.one('.regione').val(-1);
			    	A.one('.provincia').val(-1);
			    	A.one('.comune').val(-1);
				});
		
		A.one('.pulisciElementoRegione').on(
			    'click',
			    function(event) {
			    	A.one('.regione').val(-1);
			    	A.one('.provincia').val(-1);
			    	A.one('.comune').val(-1);
				});
		
		A.one('.pulisciElementoProvincia').on(
			    'click',
			    function(event) {
			    	A.one('.provincia').val(-1);
			    	A.one('.comune').val(-1);
				});
		
		A.one('.pulisciElementoComune').on(
			    'click',
			    function(event) {
			    	A.one('.comune').val(-1);
				});
		
		
	});
</script>

