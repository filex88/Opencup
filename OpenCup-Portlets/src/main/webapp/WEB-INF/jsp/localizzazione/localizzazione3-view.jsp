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
<div id="my-toggler-affina-ricerca-localizzazione">
		<div class="span10">
				<ul class="inline">
					
					<li>
						<span class="label label-info riepilogo-filtri">Stato: ${ statoSelected }
						&nbsp;<i class="icon-ok-circle vertical-align-middle icon-2x"></i>
						</span>
					</li>
					<c:choose>
						<c:when test="${isDirect}">
							<li>
								<span class="label label-info riepilogo-filtri">
									Regione: ${regionName} 
									&nbsp;<i id="eliminaFiltroRegione" class="icon-remove-circle vertical-align-middle cursor-pointer pulisci icon-2x " title="Rimuovi filtro regione"></i>
								</span>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<span class="label label-info riepilogo-filtri">
									Area geografica: ${ selectedTerritoryName } 
									&nbsp;<i id="eliminaFiltroArea" class="icon-remove-circle vertical-align-middle cursor-pointer pulisci icon-2x " title="Rimuovi filtro area geografica"></i>
								</span>
							</li>
							<li>
								<span class="label label-info riepilogo-filtri">
									Regione: ${ regionName } 
									&nbsp;<i id="eliminaFiltroRegione" class="icon-remove-circle vertical-align-middle cursor-pointer pulisci icon-2x " title="Rimuovi filtro regione"></i>
								</span>
							</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<div class="header toggler-header-collapsed" style="float: right;">
			<div id="affina-ricerca-localizzazione" class="affina-ricerca-div affina-ricerca cursor-pointer">
				AFFINA LA RICERCA
				<span>
					<i class="icon-filter"> 
						<c:if test="${ navigaAggregata.countAffRicercaLocalizzazione != null }">
							<span class="icon-stack" id="filtriBadge">
				          		<i class="icon-circle icon-stack-base" ></i>
				          		<i class="icon-light">${ navigaAggregata.countAffRicercaLocalizzazione }</i>
							</span>
						</c:if> 
					</i>
				</span>
			</div>
		</div>
		
		<div class="clear"></div>
		
		<div class="content toggler-content-collapsed">
			
			<h4 >Filtri di ricerca <i class='icon-filter'></i></h4>
			
			<portlet:actionURL var="affinaricercaActionVar">
			   	<portlet:param name="action" value="affinaricerca"></portlet:param>
			</portlet:actionURL>
			
			<aui:form 
				action="${affinaricercaActionVar}" 
				method="post" 
				name="affina-ricerca-form" 
				id="affina-ricerca-form"
				cssClass="affina-ricerca-form form-horizontal">
					<aui:input type="hidden" bean="navigaAggregata" name="naviga" value="${navigaAggregata.naviga}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idComune" value="${navigaAggregata.idComune}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idAreaGeografica" value="${navigaAggregata.idAreaGeografica}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idRegione" value="${navigaAggregata.idRegione}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idProvincia" value="${navigaAggregata.idProvincia}" />
					<aui:input type="hidden" bean="isDirect" name="isDirect" value="${isDirect}" />
		
					<div>
						<div class="row">
							<div class="ricerca span5" style="width:48%">
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Gerarchia Soggetto</strong>
									<div class="controls">&nbsp;</div>
								</div>
								
								<div class="control-group no-margin-bottom" id="area-soggetto-div">
									<label class="control-label" for="area-soggetto">Area</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge area-soggetto" label="" bean="navigaAggregata" name="idAreaSoggetto" id="area-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idAreaSoggetto == -1}"/>
											<c:forEach items="${listAreaSoggetto}" var="areasoggetto" >
									            <aui:option value="${areasoggetto.id}" label="${areasoggetto.descAreaSoggetto}" selected="${navigaAggregata.idAreaSoggetto == areasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoAreaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
							
								<div class="control-group no-margin-bottom" id="categoria-soggetto-div">
									<label class="control-label" for="categoria-soggetto">Categoria</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge categoria-soggetto" label="" bean="navigaAggregata" name="idCategoriaSoggetto" id="categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idCategoriaSoggetto == -1}"/>
											<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
									            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${navigaAggregata.idCategoriaSoggetto == categoriasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom" id="sotto-categoria-soggetto-div">
									<label class="control-label" for="sotto-categoria-soggetto">Sotto Categoria</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge sotto-categoria-soggetto" label="" bean="navigaAggregata" name="idSottoCategoriaSoggetto" id="sotto-categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idSottoCategoriaSoggetto == -1}"/>
											<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
									            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottocategSoggetto}" selected="${navigaAggregata.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom" id="anno-div">
									<label class="control-label" for="anno"><strong>Anno Decisione</strong></label>
									<div class="controls">
										<aui:select multiple="true" inlineField="true" cssClass="input-xlarge anno" label="" bean="navigaAggregata" name="idAnnoAggregatos" id="anno">
											
											<c:set var="selected" value="false" />
											<c:forEach items="${navigaAggregata.idAnnoAggregatos}" var="annoSel" >
												<c:if test="${annoSel == -1}">
													<c:set var="selected" value="true" />
												</c:if>
											</c:forEach>
											<aui:option value="-1" label="ricerca.tutte" selected="${selected}"/>
											
											<c:forEach items="${listaAnnoAggregato}" var="anno" >
												<c:set var="selected" value="false" />
												<c:forEach items="${navigaAggregata.idAnnoAggregatos}" var="annoSel" >
									           		<c:if test="${annoSel == anno.id}">
														<c:set var="selected" value="true" />
													</c:if>
									        	</c:forEach>
									        	<aui:option value="${anno.id}" label="${anno.annoAggregato}" selected="${selected}"/>
									        </c:forEach>
									        
										</aui:select>
										<i class="icon-remove-circle pulisciElementoAnno" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom" id="tipologia-div">
									<label class="control-label" for="tipologia"><strong>Tipologia Intervento</strong></label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge tipologia" label="" bean="navigaAggregata" name="idTipologiaIntervento" id="tipologia">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idTipologiaIntervento == -1}"/>
											<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
									            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${navigaAggregata.idTipologiaIntervento == tipologiaintervento.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom" id="statoprogetto-div">
									<label class="control-label" for="statoprogetto"><strong>Stato Progetto</strong></label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge statoprogetto" label="" bean="navigaAggregata" name="idStatoProgetto" id="statoprogetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idStatoProgetto == -1}"/>
											<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
									            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${navigaAggregata.idStatoProgetto == statoprogetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
							</div>
							<div class="ricerca span5" style="width:48%">
										
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Classificazione</strong>
									<div class="controls">&nbsp;</div>
								</div>
			
								<div class="control-group no-margin-bottom" id="natura-div">
									<label class="control-label" for="stato">Natura</label>
									<div class="controls">
										<aui:input type="text"  value="${navigaAggregata.descNatura}" readonly="readonly" cssClass="input-large stato" label="" bean="navigaAggregata" name="descNatura" id="natura"></aui:input>
									</div>
								</div>
								
									<div class="control-group no-margin-bottom" id="area-intervento-div">
									<label class="control-label" for="area-intervento">Area Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="input-xlarge area-intervento" label="" bean="navigaAggregata" name="idAreaIntervento" id="area-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idAreaIntervento == -1}"/>
											<c:forEach items="${listAreaIntervento}" var="areaIntervento" >
												<aui:option value="${areaIntervento.id}" label="${areaIntervento.descAreaIntervento}" selected="${navigaAggregata.idAreaIntervento == areaIntervento.id}"/>
											</c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoAreaIntervento" style="cursor: pointer;"></i>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom" id="sotto-settore-intervento-div">
									<label class="control-label" for="sotto-settore-intervento">Sotto Settore Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="input-xlarge sotto-settore-intervento" label="" bean="navigaAggregata" name="idSottosettoreIntervento" id="sotto-settore-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idSottosettoreIntervento == -1}"/>
											<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
												<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${navigaAggregata.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
											</c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom" id="categoria-intervento-div">
									<label class="control-label" for="categoria-intervento">Categoria</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="input-xlarge categoria-intervento" label="" bean="navigaAggregata" name="idCategoriaIntervento" id="categoria-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idCategoriaIntervento == -1}"/>
											<c:forEach items="${listaCategoriaIntervento}" var="categoria" >
												<aui:option value="${categoria.id}" label="${categoria.descCategoriaIntervento}" selected="${navigaAggregata.idCategoriaIntervento == categoria.id}"/>
											</c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoCategoriaIntervento" style="cursor: pointer;"></i>
									</div>
								</div>
							</div>
						</div>
					</div>
							
					<div class="control-group">
						<div class="pull-right">
							<aui:button id="affina-ricerca-classificazione" type="submit" cssClass="btn btn-primary btn-filtra" value="Filtra &nbsp;<i class='icon-filter'></i>"></aui:button>
							<aui:button id="affina-ricerca-classificazione"  cssClass="btn btn-rimuovi-filtri" value="Rimuovi Filtri &nbsp;<i class='icon-undo'></i>"></aui:button>
						</div>
					</div>
				
			</aui:form>
		</div>
</div>

<div class="clear"></div>



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
var regioniBack="${regioniBackLink}"
var areeGeoBack="${areeGeoBackLink}";

var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
	var globalA;
	
	AUI().use(	
		'aui-base',
		'aui-toggler',
		function(A) {
	
			globalA = A;
			
			
		function caricaCombo2(pNamespaceRicerca, resourceId, pattern, pattern2, target){	
			var select = document.getElementById(target);
			select.options.length = 0;
			select.options[select.options.length] = new Option("Tutte", -1);
			
			if( pattern != -1 ){
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(pNamespaceRicerca);
				resourceURL.setResourceId(resourceId);
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setParameter("pattern2", pattern2);
				resourceURL.setCopyCurrentRenderParameters(true);
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				var responseData = this.get('responseData');
	           				//console.log(responseData);
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
	           				//console.log(responseData);
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
		
		///////////// LOCALIZZAZIONE /////////////
		
				
		///////////// CLASSIFICAZIONE /////////////
		A.one('.area-intervento').on(
			    'change',
			    function(event) {
			    	A.one('.sotto-settore-intervento').val(-1);
			    	caricaCombo(namespaceRicerca, "loadSottosettoreInterventoByArea", this.val(), namespaceRicerca4js+"sotto-settore-intervento");
				});
			
		A.one('.sotto-settore-intervento').on(
			    'change',
		    function(event) {
			    	A.one('.categoria-intervento').val(-1);
			    	var area = A.one('.area-intervento').val();
			    	caricaCombo2(namespaceRicerca, "loadCategoriaInterventoByAreaSottosettore", area, this.val(), namespaceRicerca4js+"categoria-intervento");
			});
		
		///////////// GERARCHIA SOGGETTO /////////////
		A.one('.area-soggetto').on(
			    'change',
			    function(event) {
			    	A.one('.categoria-soggetto').val(-1);
			    	A.one('.sotto-categoria-soggetto').val(-1);
			    	caricaCombo(namespaceRicerca, "loadCategoriaSoggettoByAreaSoggetto", this.val(), namespaceRicerca4js+"categoria-soggetto");
				});
		
		A.one('.categoria-soggetto').on(
			    'change',
			    function(event) {
			    	A.one('.sotto-categoria-soggetto').val(-1);
			    	caricaCombo(namespaceRicerca, "loadSottoCategoriaSoggettoByCategoriaSoggetto", this.val(), namespaceRicerca4js+"sotto-categoria-soggetto");
				});
		
		
		
		
		
		
		
//		A.one('.pulisciElementoComune').on(
//			    'click',
//			    function(event) {
//			    	A.one('.comune').val(-1);
//				});
		
		A.one('.pulisciElementoAreaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.area-soggetto').val(-1);
			    	A.one('.categoria-soggetto').val(-1);
			    	A.one('.sotto-categoria-soggetto').val(-1);
				});
		
		A.one('.pulisciElementoCategoriaSoggetto').on(
			    'click',
			    function(event) {
			    	A.one('.categoria-soggetto').val(-1);
			    	A.one('.sotto-categoria-soggetto').val(-1);
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

		A.one('.pulisciElementoAreaIntervento').on(
			    'click',
			    function(event) {
					A.one('.area-intervento').val(-1);
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
		
		var myFormAffinaRicerca = A.one(".affina-ricerca-form");
	
		A.one('.btn-rimuovi-filtri').on(
				    'click',
				    function(event) {
				    	// pulisco i campi
				    	A.one('.area-intervento').val(-1);
				    	A.one('.sotto-settore-intervento').val(-1);
				    	A.one('.categoria-intervento').val(-1);
				    	A.one('.area-soggetto').val(-1);
				    	A.one('.categoria-soggetto').val(-1);
				    	A.one('.sotto-categoria-soggetto').val(-1);
				    	A.one('.anno').val(-1);
				    	A.one('.tipologia').val(-1);
				    	A.one('.statoprogetto').val(-1);

				    	
				    	// submit
				    	myFormAffinaRicerca.submit();
					});
		
		
				
			
		///////////////////////////////////////////
		
		var toggler = new A.TogglerDelegate({
				        animated: true,
				        closeAllOnExpand: true,
				        container: '#my-toggler-affina-ricerca-localizzazione',
				        content: '.content',
				        expanded: false,
				        header: '.header',
				        transition: {
				          duration: 0.2,
				          easing: 'cubic-bezier(0, 0.1, 0, 1)'
				        }
				      });
			
	});		
		
</script>
