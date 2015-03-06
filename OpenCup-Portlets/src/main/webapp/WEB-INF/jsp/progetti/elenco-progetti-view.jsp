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

<%-- 
<aui:a href="<%=themeDisplay.getPortletDisplay().getURLBack()%>">Indietro</aui:a>
--%>

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
		
			<aui:input type="hidden" bean="filtriProgetti" name="naviga" value="${filtriProgetti.naviga}" />
			
			<div class="control-group no-margin-bottom">
				<strong class="control-label">Classificazione</strong>
				<div class="controls">&nbsp;</div>
			</div>

			<div class="control-group no-margin-bottom" id="natura-div">
				<label class="control-label" for="natura">Natura</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large natura" label="" bean="filtriProgetti" name="idNatura" id="natura">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idNatura == -1}"/>
						<c:forEach items="${listaNatura}" var="natura" >
				            <aui:option value="${natura.id}" label="${natura.descNatura}" selected="${filtriProgetti.idNatura == natura.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoNatura" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="settore-intervento-div">
				<label class="control-label" for="settore-intervento">Settore Intervento</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large settore-intervento" label="" bean="filtriProgetti" name="idSettoreIntervento" id="settore-intervento">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idSettoreIntervento == -1}"/>
						<c:forEach items="${listSettoreIntervento}" var="settoreIntervento" >
							<aui:option value="${settoreIntervento.id}" label="${settoreIntervento.descSettoreIntervento}" selected="${filtriProgetti.idSettoreIntervento == settoreIntervento.id}"/>
						</c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoSettoreIntervento" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="sotto-settore-intervento-div">
				<label class="control-label" for="sotto-settore-intervento">Sotto Settore Intervento</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large sotto-settore-intervento" label="" bean="filtriProgetti" name="idSottosettoreIntervento" id="sotto-settore-intervento">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idSottosettoreIntervento == -1}"/>
						<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
							<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${filtriProgetti.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
						</c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="categoria-intervento-div">
				<label class="control-label" for="categoria-intervento">Categoria</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large categoria-intervento" label="" bean="filtriProgetti" name="idCategoriaIntervento" id="categoria-intervento">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idCategoriaIntervento == -1}"/>
						<c:forEach items="${listaCategoriaIntervento}" var="categoria" >
							<aui:option value="${categoria.id}" label="${categoria.descCategoriaIntervento}" selected="${filtriProgetti.idCategoriaIntervento == categoria.id}"/>
						</c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoCategoriaIntervento" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom">
				<strong class="control-label">Gerarchia Soggetto</strong>
				<div class="controls">&nbsp;</div>
			</div>
		
			<div class="control-group no-margin-bottom" id="categoria-soggetto-div">
				<label class="control-label" for="categoria-soggetto">Categoria</label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large categoria-soggetto" label="" bean="filtriProgetti" name="idCategoriaSoggetto" id="categoria-soggetto">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idCategoriaSoggetto == -1}"/>
						<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
				            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${filtriProgetti.idCategoriaSoggetto == categoriasoggetto.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="sotto-categoria-soggetto-div">
				<label class="control-label" for="sotto-categoria-soggetto">Sotto Categoria</label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large sotto-categoria-soggetto" label="" bean="filtriProgetti" name="idSottoCategoriaSoggetto" id="sotto-categoria-soggetto">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idSottoCategoriaSoggetto == -1}"/>
						<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
				            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottoCategoriaSoggetto}" selected="${filtriProgetti.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
				</div>
			</div>
					
			<div class="control-group no-margin-bottom">
				<strong class="control-label">Localizzazione</strong>
				<div class="controls">&nbsp;</div>
			</div>
	
			<div class="control-group no-margin-bottom" id="stato-div">
				<label class="control-label" for="stato">Stato</label>
				<div class="controls">
					<aui:input type="text" value="${filtriProgetti.descStato}" readonly="readonly" cssClass="input-large stato" label="" bean="filtriProgetti" name="descStato" id="stato"></aui:input>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="area-geografica-div">
				<label class="control-label" for="areaGeografica">Area Geografica</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large area-geografica" label="" bean="filtriProgetti" name="idAreaGeografica" id="areaGeografica">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idAreaGeografica == -1}"/>
						<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
				            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${filtriProgetti.idAreaGeografica == areaGeografica.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>
				</div>
			</div>
			 
			<div class="control-group no-margin-bottom" id="regione-div">
				<label class="control-label" for="regione">Regione</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large regione" label="" bean="filtriProgetti" name="idRegione" id="regione">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idRegione == -1}"/>
						<c:forEach items="${listRegione}" var="regione" >
				            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${filtriProgetti.idRegione == regione.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="provincia-div">
				<label class="control-label" for="provincia">Provincia</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large provincia" label="" bean="filtriProgetti" name="idProvincia" id="provincia">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idProvincia == -1}"/>
						<c:forEach items="${listProvincia}" var="provincia" >
				            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${filtriProgetti.idProvincia == provincia.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="comune-div">
				<label class="control-label" for="comune">Comune</label>
				<div class="controls form-inline">
					<aui:select inlineField="true" cssClass="input-large comune" label="" bean="filtriProgetti" name="idComune" id="comune">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idComune == -1}"/>
						<c:forEach items="${listComune}" var="comune" >
				            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${filtriProgetti.idComune == comune.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoComune" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="anno-div">
				<label class="control-label" for="anno"><strong>Anno Decisione</strong></label>
				<div class="controls">
					<aui:select multiple="true" inlineField="true" cssClass="input-large anno" label="" bean="filtriProgetti" name="idAnnoDecisiones" id="anno">
						
						<c:set var="selected" value="false" />
						<c:forEach items="${filtriProgetti.idAnnoDecisiones}" var="annoSel" >
							<c:if test="${annoSel == -1}">
								<c:set var="selected" value="true" />
							</c:if>
						</c:forEach>
						<aui:option value="-1" label="ricerca.tutte" selected="${selected}"/>
						
						<c:forEach items="${listaAnnoDecisione}" var="anno" >
							<c:set var="selected" value="false" />
							<c:forEach items="${filtriProgetti.idAnnoDecisiones}" var="annoSel" >
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
			
			<div class="control-group no-margin-bottom" id="tipologia-div">
				<label class="control-label" for="tipologia"><strong>Tipologia Intervento</strong></label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large tipologia" label="" bean="filtriProgetti" name="idTipologiaIntervento" id="tipologia">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idTipologiaIntervento == -1}"/>
						<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
				            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${filtriProgetti.idTipologiaIntervento == tipologiaintervento.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
				</div>
			</div>

			<div class="control-group no-margin-bottom" id="statoprogetto-div">
				<label class="control-label" for="statoprogetto"><strong>Stato Progetto</strong></label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large statoprogetto" label="" bean="filtriProgetti" name="idStatoProgetto" id="statoprogetto">
						<aui:option value="-1" label="ricerca.tutte" selected="${filtriProgetti.idStatoProgetto == -1}"/>
						<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
				            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${filtriProgetti.idStatoProgetto == statoprogetto.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
				</div>
			</div>

			<div style="text-align: center">
				<aui:button type="submit" value="CERCA" />
			</div>
			
		</aui:form>
		
		
	</div>
	
</div>


<script type="text/javascript">

	var namespaceRicerca4js = "<portlet:namespace/>";
	
	//var namespaceRicerca = "naturaportletricerca_WAR_OpenCupPortletsportlet";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
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
		
		A.one('.natura').on(
			    'change',
			    function(event) {
			    	A.one('.settore-intervento').val(-1);
			    	caricaCombo(namespaceRicerca, "loadSettoreInterventoByNatura", this.val(), namespaceRicerca4js+"settore-intervento");
					});
				
		A.one('.settore-intervento').on(
			    'change',
			    function(event) {
			    	A.one('.sotto-settore-intervento').val(-1);
			    	caricaCombo(namespaceRicerca, "loadSottosettoreInterventoBySettore", this.val(), namespaceRicerca4js+"sotto-settore-intervento");
				});
				
		A.one('.sotto-settore-intervento').on(
			    'change',
			    function(event) {
			    	A.one('.categoria-intervento').val(-1);
			    	caricaCombo(namespaceRicerca, "loadCategoriaInterventoBySettoreSottosettore", this.val(), namespaceRicerca4js+"categoria-intervento");
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
		
		A.one('.pulisciElementoCategoriaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.categoria-soggetto').val(-1);
				});

		A.one('.pulisciElementoSottoCategoriaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.sotto-categoria-soggetto').val(-1);
				});

		A.one('.pulisciElementoAnno').on(
			    'click',
			    function(event) {
			    	A.one('.anno').val(-1);
				});

		A.one('.pulisciElementoTipologia').on(
			    'click',
			    function(event) {
			    	A.one('.tipologia').val(-1);
				});

		A.one('.pulisciElementoStatoprogetto').on(
			    'click',
			    function(event) {
			    	A.one('.statoprogetto').val(-1);
				});
		
		A.one('.pulisciElementoNatura').on(
			    'click',
			    function(event) {

					A.one('.natura').val(-1);
					A.one('.settore-intervento').val(-1);
					A.one('.sotto-settore-intervento').val(-1); 
					A.one('.categoria-intervento').val(-1);
				});

		A.one('.pulisciElementoSettoreIntervento').on(
			    'click',
			    function(event) {

					A.one('.settore-intervento').val(-1);
					A.one('.sotto-settore-intervento').val(-1); 
					A.one('.categoria-intervento').val(-1);
				});

		A.one('.pulisciElementoSottosettoreIntervento').on(
			    'click',
			    function(event) {

					A.one('.sotto-settore-intervento').val(-1); 
					A.one('.categoria-intervento').val(-1);
				});

		A.one('.pulisciElementoCategoriaIntervento').on(
			    'click',
			    function(event) {

					A.one('.categoria-intervento').val(-1);
				});
		
	});
</script>

