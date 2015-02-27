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

<div id="modal-content">
	<aui:form 
		action="${affinaRicercaActionVar}" 
		method="post" 
		name="modal-content-stato-form" 
		id="modal-content-stato-form" 
		cssClass="form-horizontal modal-content-stato-form">	
		
		<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idComune" value="-1" />
		<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="naviga" value="${modelAttrNaturaRicerca.naviga}" />
					
		<div>
		
			<div class="row">
		
				<div class="ricerca span5">

					<div class="control-group no-margin-bottom">
						<strong class="control-label">Classificazione</strong>
						<div class="controls">&nbsp;</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-natura-div">
						<label class="control-label" for="modal-content-natura">Natura</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-natura" label="" bean="modelAttrNaturaRicerca" name="idNatura" id="modal-content-natura">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idNatura == -1}"/>
								<c:forEach items="${listaNatura}" var="natura" >
						            <aui:option value="${natura.id}" label="${natura.descNatura}" selected="${modelAttrNaturaRicerca.idNatura == natura.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoNatura" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-settore-intervento-div">
						<label class="control-label" for="modal-content-settore-intervento">Settore Intervento</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-settore-intervento" label="" bean="modelAttrNaturaRicerca" name="idSettoreIntervento" id="modal-content-settore-intervento">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idSettoreIntervento == -1}"/>
								<c:forEach items="${listSettoreIntervento}" var="settoreIntervento" >
									<aui:option value="${settoreIntervento.id}" label="${settoreIntervento.descSettoreIntervento}" selected="${modelAttrNaturaRicerca.idSettoreIntervento == settoreIntervento.id}"/>
								</c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoSettoreIntervento" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-sotto-settore-intervento-div">
						<label class="control-label" for="modal-content-sotto-settore-intervento">Sotto Settore Intervento</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-sotto-settore-intervento" label="" bean="modelAttrNaturaRicerca" name="idSottosettoreIntervento" id="modal-content-sotto-settore-intervento">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idSottosettoreIntervento == -1}"/>
								<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
									<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${modelAttrNaturaRicerca.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
								</c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-categoria-intervento-div">
						<label class="control-label" for="modal-content-categoria-intervento">Categoria</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-categoria-intervento" label="" bean="modelAttrNaturaRicerca" name="idCategoriaIntervento" id="modal-content-categoria-intervento">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idCategoriaIntervento == -1}"/>
								<c:forEach items="${listaCategoriaIntervento}" var="categoria" >
									<aui:option value="${categoria.id}" label="${categoria.descCategoriaIntervento}" selected="${modelAttrNaturaRicerca.idCategoriaIntervento == categoria.id}"/>
								</c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoCategoriaIntervento" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom">
						<strong class="control-label">Gerarchia Soggetto</strong>
						<div class="controls">&nbsp;</div>
					</div>
				
					<div class="control-group no-margin-bottom" id="modal-content-categoria-soggetto-div">
						<label class="control-label" for="modal-content-categoria-soggetto">Categoria</label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-categoria-soggetto" label="" bean="modelAttrNaturaRicerca" name="idCategoriaSoggetto" id="modal-content-categoria-soggetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idCategoriaSoggetto == -1}"/>
								<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
						            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${modelAttrNaturaRicerca.idCategoriaSoggetto == categoriasoggetto.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-sotto-categoria-soggetto-div">
						<label class="control-label" for="modal-content-sotto-categoria-soggetto">Sotto Categoria</label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-sotto-categoria-soggetto" label="" bean="modelAttrNaturaRicerca" name="idSottoCategoriaSoggetto" id="modal-content-sotto-categoria-soggetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idSottoCategoriaSoggetto == -1}"/>
								<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
						            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottoCategoriaSoggetto}" selected="${modelAttrNaturaRicerca.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
						</div>
					</div>
					
				</div>
				
				<div class="ricerca span5">
			
					<div class="control-group no-margin-bottom">
						<strong class="control-label">Localizzazione</strong>
						<div class="controls">&nbsp;</div>
					</div>
	
					<div class="control-group no-margin-bottom" id="modal-content-stato-div">
						<label class="control-label" for="modal-content-stato">Stato</label>
						<div class="controls">
							<aui:input type="text" value="${modelAttrNaturaRicerca.descStato}" readonly="readonly" cssClass="input-large modal-content-stato" label="" bean="modelAttrNaturaRicerca" name="descStato" id="modal-content-stato"></aui:input>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-area-geografica-div">
						<label class="control-label" for="modal-content-area-geografica">Area Geografica</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-area-geografica" label="" bean="modelAttrNaturaRicerca" name="idAreaGeografica" id="modal-content-area-geografica">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idAreaGeografica == -1}"/>
								<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
						            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${modelAttrNaturaRicerca.idAreaGeografica == areaGeografica.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>
						</div>
					</div>
					 
					<div class="control-group no-margin-bottom" id="modal-content-regione-div">
						<label class="control-label" for="modal-content-regione">Regione</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-regione" label="" bean="modelAttrNaturaRicerca" name="idRegione" id="modal-content-regione">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idRegione == -1}"/>
								<c:forEach items="${listRegione}" var="regione" >
						            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${modelAttrNaturaRicerca.idRegione == regione.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-provincia-div">
						<label class="control-label" for="modal-content-provincia">Provincia</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-provincia" label="" bean="modelAttrNaturaRicerca" name="idProvincia" id="modal-content-provincia">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idProvincia == -1}"/>
								<c:forEach items="${listProvincia}" var="provincia" >
						            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${modelAttrNaturaRicerca.idProvincia == provincia.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="modal-content-anno-div">
						<label class="control-label" for="modal-content-anno"><strong>Anno Decisione</strong></label>
						<div class="controls">
							<aui:select multiple="true" inlineField="true" cssClass="input-xlarge modal-content-anno" label="" bean="modelAttrNaturaRicerca" name="idAnnoDecisiones" id="modal-content-anno">
								
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
					
					<div class="control-group no-margin-bottom" id="modal-content-tipologia-div">
						<label class="control-label" for="modal-content-tipologia"><strong>Tipologia Intervento</strong></label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-tipologia" label="" bean="modelAttrNaturaRicerca" name="idTipologiaIntervento" id="modal-content-tipologia">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idTipologiaIntervento == -1}"/>
								<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
						            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${modelAttrNaturaRicerca.idTipologiaIntervento == tipologiaintervento.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
						</div>
					</div>
		
					<div class="control-group no-margin-bottom" id="modal-content-statoprogetto-div">
						<label class="control-label" for="modal-content-statoprogetto"><strong>Stato Progetto</strong></label>
						<div class="controls">
							<aui:select inlineField="true" cssClass="input-xlarge modal-content-statoprogetto" label="" bean="modelAttrNaturaRicerca" name="idStatoProgetto" id="modal-content-statoprogetto">
								<aui:option value="-1" label="ricerca.tutte" selected="${modelAttrNaturaRicerca.idStatoProgetto == -1}"/>
								<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
						            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${modelAttrNaturaRicerca.idStatoProgetto == statoprogetto.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
						</div>
					</div>
					
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
			
			A.one('.modal-content-area-geografica').on(
				    'change',
				    function(event) {
				    	A.one('.modal-content-regione').val(-1);
				    	A.one('.modal-content-provincia').val(-1);
				    	caricaCombo(namespaceRicerca, "loadRegioneByAreaGeografica", this.val(), namespaceRicerca4js+"modal-content-regione");
					});
			
			A.one('.modal-content-regione').on(
				    'change',
				    function(event) {
				    	A.one('.modal-content-provincia').val(-1);
				    	caricaCombo(namespaceRicerca, "loadProvinciaByRegione", this.val(), namespaceRicerca4js+"modal-content-provincia");
					});
			
			A.one('.modal-content-natura').on(
				    'change',
				    function(event) {
				    	A.one('.modal-content-settore-intervento').val(-1);
				    	caricaCombo(namespaceRicerca, "loadSettoreInterventoByNatura", this.val(), namespaceRicerca4js+"modal-content-settore-intervento");
 					});
					
			A.one('.modal-content-settore-intervento').on(
				    'change',
				    function(event) {
				    	A.one('.modal-content-sotto-settore-intervento').val(-1);
				    	caricaCombo(namespaceRicerca, "loadSottosettoreInterventoBySettore", this.val(), namespaceRicerca4js+"modal-content-sotto-settore-intervento");
					});
					
			A.one('.modal-content-sotto-settore-intervento').on(
				    'change',
				    function(event) {
				    	A.one('.modal-content-categoria-intervento').val(-1);
				    	caricaCombo(namespaceRicerca, "loadCategoriaInterventoBySettoreSottosettore", this.val(), namespaceRicerca4js+"modal-content-categoria-intervento");
					});
			
			A.one('.pulisciElementoAreaGeografica').on(
				    'click',
				    function(event) {
				    	<%--
				    	A.one('.modal-content-comune').val(-1);
				    	--%>
				    	A.one('.modal-content-provincia').val(-1);
				    	A.one('.modal-content-regione').val(-1);
				    	A.one('.modal-content-area-geografica').val(-1);
					});

			A.one('.pulisciElementoRegione').on(
				    'click',
				    function(event) {
				    	<%--
				    	A.one('.modal-content-comune').val(-1);
				    	--%>
				    	A.one('.modal-content-provincia').val(-1);
				    	A.one('.modal-content-regione').val(-1);
					});
			
			A.one('.pulisciElementoProvincia').on(
				    'click',
				    function(event) {
				    	A.one('.modal-content-provincia').val(-1);
					});
			
			A.one('.pulisciElementoCategoriaSoggetto').on(
				    'click',
				    function(event) {
				    	A.one('.modal-content-categoria-soggetto').val(-1);
					});

			A.one('.pulisciElementoSottoCategoriaSoggetto').on(
				    'click',
				    function(event) {
				    	A.one('.modal-content-sotto-categoria-soggetto').val(-1);
					});

			A.one('.pulisciElementoAnno').on(
				    'click',
				    function(event) {
				    	A.one('.modal-content-anno').val(-1);
					});

			A.one('.pulisciElementoTipologia').on(
				    'click',
				    function(event) {
				    	A.one('.modal-content-tipologia').val(-1);
					});

			A.one('.pulisciElementoStatoprogetto').on(
				    'click',
				    function(event) {
				    	A.one('.modal-content-statoprogetto').val(-1);
					});
			
			A.one('.pulisciElementoNatura').on(
				    'click',
				    function(event) {

						A.one('.modal-content-natura').val(-1);
						A.one('.modal-content-settore-intervento').val(-1);
						A.one('.modal-content-sotto-settore-intervento').val(-1); 
						A.one('.modal-content-categoria-intervento').val(-1);
					});

			A.one('.pulisciElementoSettoreIntervento').on(
				    'click',
				    function(event) {

						A.one('.modal-content-settore-intervento').val(-1);
						A.one('.modal-content-sotto-settore-intervento').val(-1); 
						A.one('.modal-content-categoria-intervento').val(-1);
					});

			A.one('.pulisciElementoSottosettoreIntervento').on(
				    'click',
				    function(event) {

						A.one('.modal-content-sotto-settore-intervento').val(-1); 
						A.one('.modal-content-categoria-intervento').val(-1);
					});

			A.one('.pulisciElementoCategoriaIntervento').on(
				    'click',
				    function(event) {

						A.one('.modal-content-categoria-intervento').val(-1);
					});
			
	});
	
	function naturaRicercaContentReset(){
		
		globalA.one('.modal-content-categoria-soggetto').val(-1);
		globalA.one('.modal-content-sotto-categoria-soggetto').val(-1);
		
		globalA.one('.modal-content-anno').val(-1);
		
		globalA.one('.modal-content-area-geografica').val(-1);
		globalA.one('.modal-content-regione').val(-1);
		globalA.one('.modal-content-provincia').val(-1);
		globalA.one('.modal-content-tipologia').val(-1);
		globalA.one('.modal-content-statoprogetto').val(-1);
		
		globalA.one('.modal-content-natura').val(-1);
		globalA.one('.modal-content-settore-intervento').val(-1);
		globalA.one('.modal-content-sotto-settore-intervento').val(-1); 
		globalA.one('.modal-content-categoria-intervento').val(-1);
		
	}
	
	function naturaRicercaContentSubmit(){
		var form = globalA.one(".modal-content-stato-form");
		form.submit();
	}
	
</script>
