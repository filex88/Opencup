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

<div class="portlet-body">
	<div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- NAVIGAZIONE --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span12">
			
			<c:if test="${ navigaAggregata.descNatura != null }">
				<ul class="inline">
					<li>Naviga per</li>
					<li><span class="label label-info riepilogo-filtri">Natura: ${ navigaAggregata.descNatura }
					</span></li>				
			</c:if>
			<c:if test="${ navigaAggregata.descAreaIntervento != null }">																		
			<li><span class="label label-info riepilogo-filtri">Area Intervento: ${ navigaAggregata.descAreaIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idAreaIntervento" data-parent2="idSottosettoreIntervento" data-parent3="idCategoriaIntervento" ></i>
			</span></li>
		</c:if>
		<c:if test="${ navigaAggregata.descSottosettoreIntervento != null }">	
			<li><span class="label label-info riepilogo-filtri">Sottosettore Intervento: ${ navigaAggregata.descSottosettoreIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idSottosettoreIntervento" data-parent2="idCategoriaIntervento"  ></i>
			</span></li>
		</c:if>
		<c:if test="${ navigaAggregata.descCategoriaIntervento != null }">																								
			<li><span class="label label-info riepilogo-filtri">Categoria Intervento: ${ navigaAggregata.descCategoriaIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idCategoriaIntervento" ></i>
			</span></li>
		</c:if>
		<c:if test="${ navigaAggregata.descNatura != null }">
			</ul>
		</c:if>
			
		</div>
	
	</div>
	
	<div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- RIEPILOGO --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span6">
		
			<div style="text-align: justify;">
				La sintesi per Natura mette in evidenza i dati aggregati della totalità dei progetti, è possibile proseguire nei dati 
				aggregati navigando nelle ulteriori classificazioni:
				Natura > Settore > Sottosettori > Categoria
			</div>

			<div style="text-align: right; padding-top: 15px">
				<aui:a href="${linkURLElencoProgetti}">
					Vedi Elenco Progetti <i class="icon-list"></i>
				</aui:a>
			</div>
			
			<fmt:setLocale value="it_IT"/>
			
			<liferay-ui:search-container searchContainer="${searchContainerRiepilogo}" delta="${searchContainerRiepilogo.delta}" deltaParam="aggregata_delta">
				
				<liferay-ui:search-container-results results="${searchContainerRiepilogo.results}" total="${searchContainerRiepilogo.total}"/>    
				
				<liferay-ui:search-container-row className="it.dipe.opencup.dto.DescrizioneValore" modelVar="descrizioneValore">
					
					<liferay-ui:search-container-column-text name="sintesi-progetti">
						${descrizioneValore.label}
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text>
						<c:choose>
							<c:when test="${'VOLUME DEI PROGETTI' eq descrizioneValore.label}">
								<span class="pull-right"><fmt:formatNumber value="${descrizioneValore.value}" type="number" minIntegerDigits="1"/></span>
							</c:when>
							<c:otherwise>
								<span class="pull-right"><fmt:formatNumber value="${descrizioneValore.value}" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
					
				</liferay-ui:search-container-row>
				
				<liferay-ui:search-iterator searchContainer="${searchContainerRiepilogo}"/>
				
			</liferay-ui:search-container>
		
		</div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- TORTA --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span6">
		
			<a name="classificazione-portlet1"></a>

			<div id="pieChartClassificazione" style="text-align: center">
			</div>
			
			<div class="alert alert-info pieChartClassificazioneEmpty" id="pieChartClassificazioneEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>
			
			<div style="text-align: center" class="pieChartClassificazioneToolBar" id="pieChartClassificazioneToolBar">
				<aui:button-row cssClass="btn-group btn-group-justified">
					<aui:button cssClass="classificazione-sel-btn btn btn-default" data-classificazione="VOLUME" value="VOLUME" />
					<aui:button cssClass="classificazione-sel-btn btn btn-default" data-classificazione="COSTO" value="COSTO" />
					<aui:button cssClass="classificazione-sel-btn btn btn-default" data-classificazione="IMPORTO" value="IMPORTO" />
				</aui:button-row>
			</div>
			
			<script type="text/javascript">
			
				var namespace = "<portlet:namespace/>";
				namespace = namespace.substring(1, namespace.length - 1);
				
			</script>

		</div>
		
	</div>
	
	<div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- TORTA --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span12">
		
			<div style="text-align: justify;">
				Continua la navigazione selezionando la <strong>${navigaPer}</strong> dei progetti
			</div>
			
			<fmt:setLocale value="it_IT"/>

			<liferay-ui:search-container searchContainer="${searchContainerDettaglio}" delta="${searchContainerDettaglio.delta}" orderByType="${searchContainerDettaglio.orderByType}" deltaParam="delta">
			
				<liferay-ui:search-container-results results="${searchContainerDettaglio.results}" total="${searchContainerDettaglio.total}"/>    
			
			    <liferay-ui:search-container-row className="it.dipe.opencup.dto.AggregataDTO" keyProperty="id" modelVar="aggregataDTO">
						
					<liferay-ui:search-container-column-text name="aggregato-des">
						<a href="#" onclick="return false;" data-url="${aggregataDTO.linkURL}" class="link-url-naviga-dettaglio">${aggregataDTO.descURL}</a>
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text name="aggregato-volume" orderableProperty="numeProgetti" orderable="true">
						<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.numeProgetti}" type="number" minIntegerDigits="1"/></span>
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text name="aggregato-costo" orderableProperty="impoCostoProgetti" orderable="true">
						<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.impoCostoProgetti}" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
						<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  minFractionDigits="3"/></span>
					</liferay-ui:search-container-column-text>
				
				</liferay-ui:search-container-row>
			
				<liferay-ui:search-iterator searchContainer="${searchContainerDettaglio}"/>
			
			</liferay-ui:search-container>
			
			<a name="natura-portlet2"></a>
		
		</div>
	</div>
	
</div>

<form class="formEmptyClassificazione1" method="POST" action="#">
</form>

<portlet:actionURL var="affinaRicercaActionVar">
   	<portlet:param name="action" value="ricerca"></portlet:param>
</portlet:actionURL>

<aui:form 
	action="${affinaRicercaActionVar}" 
	method="post" 
	name="filtri-form" 
	id="filtri-form"
	cssClass="filtri-form">
	
		<aui:input type="hidden" bean="navigaAggregata" name="naviga" value="${navigaAggregata.naviga}" />
		
		<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" id="idNatura" />
		<aui:input type="hidden" bean="navigaAggregata" name="idAreaIntervento" value="${navigaAggregata.idAreaIntervento}" id="idAreaIntervento" />
		<aui:input type="hidden" bean="navigaAggregata" name="idSottosettoreIntervento" value="${navigaAggregata.idSottosettoreIntervento}" id="idSottosettoreIntervento" />
		<aui:input type="hidden" bean="navigaAggregata" name="idCategoriaIntervento" value="${navigaAggregata.idCategoriaIntervento}" id="idCategoriaIntervento" />
		<aui:input type="hidden" bean="navigaAggregata" name="idAnnoAggregatos" value="${navigaAggregata.idAnnoAggregatos}" id="idAnnoAggregatos" />
		<aui:input type="hidden" bean="navigaAggregata" name="idRegione" value="${navigaAggregata.idRegione}" id="idRegione" />
		<aui:input type="hidden" bean="navigaAggregata" name="idProvincia" value="${navigaAggregata.idProvincia}" id="idProvincia" />
		<aui:input type="hidden" bean="navigaAggregata" name="idComune" value="${navigaAggregata.idComune}" id="idComune" />
		<aui:input type="hidden" bean="navigaAggregata" name="idAreaGeografica" value="${navigaAggregata.idAreaGeografica}" id="idAreaGeografica" />
		<aui:input type="hidden" bean="navigaAggregata" name="idCategoriaSoggetto" value="${navigaAggregata.idCategoriaSoggetto}" id="idCategoriaSoggetto" />
		<aui:input type="hidden" bean="navigaAggregata" name="idSottoCategoriaSoggetto" value="${navigaAggregata.idSottoCategoriaSoggetto}" id="idSottoCategoriaSoggetto" />
		<aui:input type="hidden" bean="navigaAggregata" name="idTipologiaIntervento" value="${navigaAggregata.idTipologiaIntervento}" id="idTipologiaIntervento" />
		<aui:input type="hidden" bean="navigaAggregata" name="idStatoProgetto" value="${navigaAggregata.idStatoProgetto}" id="idStatoProgetto" />
	
</aui:form>

<script type="text/javascript">
	var namespaceRicerca4js = "<portlet:namespace/>";
</script>