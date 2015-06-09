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

<fmt:setLocale value="it_IT"/>

<div class="stripe">	

	<a href="${linkallregioni}" >NAVIGA PER REGIONI</a>

	<div id="italybymacroareas"></div>

</div>
<%-- 
<div id="my-toggler-affina-ricerca-localizzazione">
		<div class="span10 navigazione-div-container" style="float:left;">
				<ul class="inline barra-navigazione">
					
					<li><span class="label label-info riepilogo-filtri">Stato: ${ statoSelected }
						&nbsp;<i class="icon-ok-circle vertical-align-middle icon-2x"></i>
					</span></li>			
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
			
		
			<portlet:actionURL var="affinaricercaActionVar">
			   	<portlet:param name="action" value="affinaricerca"></portlet:param>
			</portlet:actionURL>
			
			<aui:form 
				action="${affinaricercaActionVar}" 
				method="post" 
				name="affina-ricerca-form" 
				id="affina-ricerca-form"
				cssClass="affina-ricerca-form form-horizontal form-ricerca-padding">
				
				<div class="card">
					<div class="card-title">
			           	<span class="title">Filtri di ricerca <i class='icon-filter'></i></span>
			       	</div>
			       	<div class="card-content">
				
				
					<aui:input type="hidden" bean="navigaAggregata" name="naviga" value="${navigaAggregata.naviga}" />
					<aui:input type="hidden" bean="navigaAggregata" name="pagElencoProgetti" value="elenco-progetti" />
					<aui:input type="hidden" bean="navigaAggregata" name="pagDettaglioProgetto" value="dettaglio-progetto" />
					<aui:input type="hidden" bean="navigaAggregata" name="idComune" value="${navigaAggregata.idComune}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" />
					<aui:input type="hidden" bean="navigaAggregata" name="idAreaGeografica" value="${navigaAggregata.idAreaGeografica}" />
					
					
					<div>
						<div class="row">
							<div class="ricerca span5">
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Gerarchia Soggetto</strong>
									<div class="controls">&nbsp;</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
									<label class="control-label" for="area-soggetto">Area</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-large area-soggetto" label="" bean="navigaAggregata" name="idAreaSoggetto" id="area-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idAreaSoggetto == -1}"/>
											<c:forEach items="${listAreaSoggetto}" var="areasoggetto" >
									            <aui:option value="${areasoggetto.id}" label="${areasoggetto.descAreaSoggetto}" selected="${navigaAggregata.idAreaSoggetto == areasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoAreaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
							
								<div class="control-group no-margin-bottom row-no-wrap" id="categoria-soggetto-div">
									<label class="control-label" for="categoria-soggetto">Categoria</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-large categoria-soggetto" label="" bean="navigaAggregata" name="idCategoriaSoggetto" id="categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idCategoriaSoggetto == -1}"/>
											<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
									            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${navigaAggregata.idCategoriaSoggetto == categoriasoggetto.id}"/>
									        </c:forEach>(currentY*2)+30)
										</aui:select>
										<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom row-no-wrap" id="sotto-categoria-soggetto-div">
									<label class="control-label" for="sotto-categoria-soggetto">Sotto Categoria</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-large sotto-categoria-soggetto" label="" bean="navigaAggregata" name="idSottoCategoriaSoggetto" id="sotto-categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idSottoCategoriaSoggetto == -1}"/>
											<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
									            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottocategSoggetto}" selected="${navigaAggregata.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="anno-div">
									<label class="control-label" for="anno"><strong>Anno Decisione</strong></label>
									<div class="controls">
										<aui:select multiple="true" inlineField="true" cssClass="input-large anno" label="" bean="navigaAggregata" name="idAnnoAggregatos" id="anno">
											
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
					
								<div class="control-group no-margin-bottom row-no-wrap" id="tipologia-div">
									<label class="control-label" for="tipologia"><strong>Tipologia Intervento</strong></label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-large tipologia" label="" bean="navigaAggregata" name="idTipologiaIntervento" id="tipologia">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idTipologiaIntervento == -1}"/>
											<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
									            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${navigaAggregata.idTipologiaIntervento == tipologiaintervento.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom row-no-wrap" id="statoprogetto-div">
									<label class="control-label" for="statoprogetto"><strong>Stato Progetto</strong></label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-large statoprogetto" label="" bean="navigaAggregata" name="idStatoProgetto" id="statoprogetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idStatoProgetto == -1}"/>
											<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
									            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${navigaAggregata.idStatoProgetto == statoprogetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
							</div>
							<div class="ricerca span5">
										
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Classificazione</strong>
									<div class="controls">&nbsp;</div>
								</div>
			
								<div class="control-group no-margin-bottom row-no-wrap" id="natura-div">
									<label class="control-label" for="stato">Natura</label>
									<div class="controls">
										<aui:input type="text"  value="${navigaAggregata.descNatura}" readonly="readonly" cssClass="input-large stato" label="" bean="navigaAggregata" name="descNatura" id="natura"></aui:input>
									</div>
								</div>
								
									<div class="control-group no-margin-bottom row-no-wrap" id="area-intervento-div">
									<label class="control-label" for="area-intervento">Area Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="input-large area-intervento" label="" bean="navigaAggregata" name="idAreaIntervento" id="area-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idAreaIntervento == -1}"/>
											<c:forEach items="${listAreaIntervento}" var="areaIntervento" >
												<aui:option value="${areaIntervento.id}" label="${areaIntervento.descAreaIntervento}" selected="${navigaAggregata.idAreaIntervento == areaIntervento.id}"/>
											</c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoAreaIntervento" style="cursor: pointer;"></i>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="sotto-settore-intervento-div">
									<label class="control-label" for="sotto-settore-intervento">Sotto Settore Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="input-large sotto-settore-intervento" label="" bean="navigaAggregata" name="idSottosettoreIntervento" id="sotto-settore-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idSottosettoreIntervento == -1}"/>
											<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
												<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${navigaAggregata.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
											</c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom row-no-wrap" id="categoria-intervento-div">
									<label class="control-label" for="categoria-intervento">Categoria</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="input-large categoria-intervento" label="" bean="navigaAggregata" name="idCategoriaIntervento" id="categoria-intervento">
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
							
					</div>
					<div class="card-action">		
					<div class="control-group">
						<div class="pull-right">
							<aui:button id="affina-ricerca-classificazione" type="submit" cssClass="btn btn-primary btn-filtra" value="Filtra &nbsp;<i class='icon-filter'></i>"></aui:button>
							<aui:button id="affina-ricerca-classificazione"  cssClass="btn btn-rimuovi-filtri" value="Rimuovi Filtri &nbsp;<i class='icon-undo'></i>"></aui:button>
						</div>
					</div>
				</div>
			</div>
			</aui:form>
		</div>
		
</div>

<div class="summaryContainer">
	
	<div class="card">
		<div class="card-title">
		       <span class="title">Naviga per localizzazione</span>
		</div>
	
		<div style="text-align: justify;" class="card-content">
			La sintesi per Localizzazione mette in evidenza i dati aggregati della totalità dei progetti, è possibile proseguire nei dati aggregati navigando nelle ulteriori classificazioni:
			Regione > Provincia
		</div>
	</div>
	
	<div class="card">
		<div class="card-title">
          	<span class="title">Dati di sintesi</span>
      	</div>
       	<div class="card-content">
			<div>
				<fmt:setLocale value="it_IT"/>
				<div class="span4 dati_sitesi dati_sitesi_verde">
					<div class="celle_dati_sitesi font-size3em"><i class="icon-bar-chart"></i></div>
					<div class="celle_dati_sitesi font-size1em">Volume</div>
					<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${volumeDeiProgetti}" type="number" minIntegerDigits="0"/></div>
				</div>
				<div class="span4 dati_sitesi dati_sitesi_arancio">
					<div class="celle_dati_sitesi font-size3em"><i class="icon-tags"></i></div>
					<div class="celle_dati_sitesi font-size1em">Costo</div>
					<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${costoDeiProgetti}" type="currency" minIntegerDigits="0" minFractionDigits="0"/></div>
				</div>
				<div class="span4 dati_sitesi dati_sitesi_lilla">
					<div class="celle_dati_sitesi font-size3em"><i class="icon-eur"></i></div>
					<div class="celle_dati_sitesi font-size1em">Importo Finanziato</div>
					<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${importoFinanziamenti}" type="currency" minIntegerDigits="0" minFractionDigits="0"/></div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="card-action">
			<div class="link_elenco-progetti span4 offset8">
				<aui:a href="${linkElencoProgetti}" cssClass="block">
					Vai a Elenco Progetti <i class="icon-list"></i>
				</aui:a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
			
</div>

<div class="mapContainer ">
	<div class="card">
		<div class="card-title">
			<span class="title">Distribuzione</span>
			<span class="right"> <a href="${linkallregioni}" >NAVIGA PER REGIONI</a></span>
		</div>
		
		<div id="italybymacroareas"></div>
		
		<div id="dimensions" class="card-action">
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
</div>

<div class="clear"></div>

<div class="detailContainer" id="tabRisultati">

<div class="card">
<div class="card-title"> <span class="title">Dettaglio distribuzione</span></div>
<div class="card-content">
<div style="text-align: justify;">
	Continua la navigazione selezionando <strong>l'area geografica</strong> dei progetti
</div>

<liferay-ui:search-container searchContainer="${searchContainerDistinct}" delta="${searchContainerDistinct.delta}" orderByType="${searchContainerDistinct.orderByType}" deltaParam="delta" >

	<liferay-ui:search-container-results results="${searchContainerDistinct.results}" total="${searchContainerDistinct.total}"/>    

    <liferay-ui:search-container-row className="it.dipe.opencup.dto.LocalizationValueConverter" keyProperty="localizationLabel" modelVar="localizzazioneValue" >
			
		<liferay-ui:search-container-column-text name="AREA GEOGRAFICA">
			<a id="${localizzazioneValue.localizationLabel}" href="${localizzazioneValue.detailUrl}"  class="link-url-naviga-selezione ">${localizzazioneValue.fullLabel}</a>
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

	<liferay-ui:search-iterator searchContainer="${searchContainerDistinct}"/>

</liferay-ui:search-container>
</div>
</div>

</div>
--%>

<script>
	var jsonResultLocalizzazione=eval('('+'${jsonResultLocalizzazione}'+')');
	
	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
	var globalA;
	
	AUI().use(	
		'aui-base',
		'aui-toggler',
		function(A) {
	
			globalA = A;
			
		<%--	
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
		--%>	
	});
	
	
</script>

