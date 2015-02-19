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
	
<portlet:actionURL var="affinaRicercaActionVar"  windowState="<%=LiferayWindowState.NORMAL.toString()%>">
   	<portlet:param name="action" value="ricerca"></portlet:param>
</portlet:actionURL>

<div id="affina-ricerca-natura-modal-content">
	<aui:form 
		action="${affinaRicercaActionVar}" 
		method="post" 
		name="affina-ricerca-natura-modal-content-stato-form" 
		id="affina-ricerca-natura-modal-content-stato-form" 
		cssClass="form-horizontal affina-ricerca-natura-modal-content-stato-form">	
		
		<div>
		
			<div class="row">
		
				<div class="ricerca span5">
				
					<div class="control-group">
						<strong class="control-label">Gerarchia Soggetto</strong>
						<div class="controls">&nbsp;</div>
					</div>
				
					<div class="control-group" id="affina-ricerca-natura-modal-content-categoria-soggetto-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-categoria-soggetto">Categoria</label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-categoria-soggetto" label="" bean="modelAttrNaturaRicerca" name="idCategoriaSoggetto" id="affina-ricerca-natura-modal-content-categoria-soggetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idCategoriaSoggetto == -1}"/>
								<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
						            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${modelAttrNaturaRicerca.idCategoriaSoggetto == categoriasoggetto.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group" id="affina-ricerca-natura-modal-content-sotto-categoria-soggetto-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-sotto-categoria-soggetto">Sotto Categoria</label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-sotto-categoria-soggetto" label="" bean="modelAttrNaturaRicerca" name="idSottoCategoriaSoggetto" id="affina-ricerca-natura-modal-content-sotto-categoria-soggetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idSottoCategoriaSoggetto == -1}"/>
								<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
						            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottoCategoriaSoggetto}" selected="${modelAttrNaturaRicerca.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group" id="affina-ricerca-natura-modal-content-anno-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-anno"><strong>Anno Decisione</strong></label>
						<div class="controls">
							<aui:select multiple="true" inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-anno" label="" bean="modelAttrNaturaRicerca" name="idAnnoDecisiones" id="affina-ricerca-natura-modal-content-anno">
								
								<c:set var="selected" value="false" />
								<c:forEach items="${modelAttrNaturaRicerca.idAnnoDecisiones}" var="annoSel" >
									<c:if test="${annoSel == -1}">
										<c:set var="selected" value="true" />
									</c:if>
								</c:forEach>
								<aui:option value="-1" label="ricerca.tutte" selected="${selected}"/>
								
								<c:forEach items="${listaAnnoDecisione}" var="anno" >
									<c:set var="selected" value="false" />
									<c:forEach items="${modelAttrNaturaRicerca.idAnnoDecisiones}" var="annoSel" >
						           		<c:if test="${annoSel == anno.id}">
											<c:set var="selected" value="true" />
										</c:if>
						        	</c:forEach>
						        	<aui:option value="${anno.id}" label="${anno.annoDadeAnnoDecisione}" selected="${selected}"/>
						        </c:forEach>
						        
							</aui:select>
							<i class="icon-remove-circle pulisciElementoAnno" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group" id="affina-ricerca-natura-modal-content-tipologia-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-tipologia"><strong>Tipologia Intervento</strong></label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-tipologia" label="" bean="modelAttrNaturaRicerca" name="idTipologiaInterventi" id="affina-ricerca-natura-modal-content-tipologia">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idTipologiaInterventi == -1}"/>
								<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
						            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${modelAttrNaturaRicerca.idTipologiaInterventi == tipologiaintervento.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
						</div>
					</div>
		
					<div class="control-group" id="affina-ricerca-natura-modal-content-statoprogetto-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-statoprogetto"><strong>Stato Progetto</strong></label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-statoprogetto" label="" bean="modelAttrNaturaRicerca" name="idStatoProgetto" id="affina-ricerca-natura-modal-content-statoprogetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idStatoProgetto == -1}"/>
								<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
						            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${modelAttrNaturaRicerca.idStatoProgetto == statoprogetto.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
						</div>
					</div>
				</div>
				
				<div class="ricerca span5">
			
					<div class="control-group">
						<strong class="control-label">Localizzazione</strong>
						<div class="controls">&nbsp;</div>
					</div>
	
					<div class="control-group" id="affina-ricerca-natura-modal-content-stato-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-stato">Stato</label>
						<div class="controls">
							<aui:input type="text" value="${modelAttrNaturaRicerca.descStato}" readonly="readonly" cssClass="input-large affina-ricerca-natura-modal-content-stato" label="" bean="modelAttrNaturaRicerca" name="descStato" id="affina-ricerca-natura-modal-content-stato"></aui:input>
						</div>
					</div>
					 
					<div class="control-group" id="affina-ricerca-natura-modal-content-regione-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-regione">Regione</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-regione" label="" bean="modelAttrNaturaRicerca" name="idRegione" id="affina-ricerca-natura-modal-content-regione">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idRegione == -1}"/>
								<c:forEach items="${listRegione}" var="regione" >
						            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${modelAttrNaturaRicerca.idRegione == regione.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group" id="affina-ricerca-natura-modal-content-provincia-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-provincia">Provincia</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-provincia" label="" bean="modelAttrNaturaRicerca" name="idProvincia" id="affina-ricerca-natura-modal-content-provincia">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idProvincia == -1}"/>
								<c:forEach items="${listProvincia}" var="provincia" >
						            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${modelAttrNaturaRicerca.idProvincia == provincia.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
						</div>
					</div>
					<%-- 
					<div class="control-group" id="affina-ricerca-natura-modal-content-comune-div">
						<label class="control-label" for="affina-ricerca-natura-modal-content-comune">Comune</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge affina-ricerca-natura-modal-content-comune" label="" bean="modelAttrNaturaRicerca" name="idComune" id="affina-ricerca-natura-modal-content-comune">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idComune == -1}"/>
								<c:forEach items="${listComune}" var="comune" >
						            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${modelAttrNaturaRicerca.idComune == comune.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoComune" style="cursor: pointer;"></i>
						</div>
					</div>
					--%>
				</div>
			</div>
		</div>		
	</aui:form>
</div>

<script type="text/javascript">

	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
	var globalA;
	
	AUI().use(	
		'aui-base',
		function(A) {
	
			globalA = A;
			
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

					A.io.request( resourceURL.toString(), {
		   				method: 'GET',
		       			dataType: 'json',
		       			on: {
		           			success: function(event, id, obj) {
		           				var responseData = this.get('responseData');
		           				A.Object.each(
		           						responseData.lista, 
		           						function(value, key){
		           							console.log("label = " + value.label);
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
			
			<%--
			A.one('.affina-ricerca-natura-modal-content-provincia').on(
				    'change',
				    function(event) {
				    	caricaCombo(namespaceRicerca, "loadComuniByProvincia", this.val(), namespaceRicerca4js+"affina-ricerca-natura-modal-content-comune");
					});
			--%>
			
			A.one('.affina-ricerca-natura-modal-content-regione').on(
				    'change',
				    function(event) {
				    	caricaCombo(namespaceRicerca, "loadProvinciaByRegione", this.val(), namespaceRicerca4js+"affina-ricerca-natura-modal-content-provincia");
					});

			A.one('.pulisciElementoRegione').on(
				    'click',
				    function(event) {
				    	<%--
				    	A.one('.affina-ricerca-natura-modal-content-comune').val(-1);
				    	--%>
				    	A.one('.affina-ricerca-natura-modal-content-provincia').val(-1);
				    	A.one('.affina-ricerca-natura-modal-content-regione').val(-1);
					});
			
			A.one('.pulisciElementoProvincia').on(
				    'click',
				    function(event) {
				    	<%--
				    	A.one('.affina-ricerca-natura-modal-content-comune').val(-1);
				    	--%>
				    	A.one('.affina-ricerca-natura-modal-content-provincia').val(-1);
					});
			<%--
			A.one('.pulisciElementoComune').on(
				    'click',
				    function(event) {
				    	A.one('.affina-ricerca-natura-modal-content-comune').val(-1);
					});
			--%>
			A.one('.pulisciElementoCategoriaSoggetto').on(
				    'click',
				    function(event) {
				    	A.one('.affina-ricerca-natura-modal-content-categoria-soggetto').val(-1);
					});

			A.one('.pulisciElementoSottoCategoriaSoggetto').on(
				    'click',
				    function(event) {
				    	A.one('.affina-ricerca-natura-modal-content-sotto-categoria-soggetto').val(-1);
					});

			A.one('.pulisciElementoAnno').on(
				    'click',
				    function(event) {
				    	A.one('.affina-ricerca-natura-modal-content-anno').val(-1);
					});

			A.one('.pulisciElementoTipologia').on(
				    'click',
				    function(event) {
				    	A.one('.affina-ricerca-natura-modal-content-tipologia').val(-1);
					});

			A.one('.pulisciElementoStatoprogetto').on(
				    'click',
				    function(event) {
				    	A.one('.affina-ricerca-natura-modal-content-statoprogetto').val(-1);
					});
			
	});
	
	function naturaRicercaContentReset(){
		
		globalA.one('.affina-ricerca-natura-modal-content-categoria-soggetto').val(-1);
		globalA.one('.affina-ricerca-natura-modal-content-sotto-categoria-soggetto').val(-1);
		
		globalA.one('.affina-ricerca-natura-modal-content-anno').val(-1);
		
		globalA.one('.affina-ricerca-natura-modal-content-regione').val(-1);
		globalA.one('.affina-ricerca-natura-modal-content-provincia').val(-1);
		<%--
		globalA.one('.affina-ricerca-natura-modal-content-comune').val(-1);
		--%>
		globalA.one('.affina-ricerca-natura-modal-content-tipologia').val(-1);
		globalA.one('.affina-ricerca-natura-modal-content-statoprogetto').val(-1);
	}
	
	function naturaRicercaContentSubmit(){
		var form = globalA.one(".affina-ricerca-natura-modal-content-stato-form");
		form.submit();
	}
	
</script>
