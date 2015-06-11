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
		
	<div id="my-toggler-affina-ricerca-classificazione">
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- NAVIGAZIONE --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span10 navigazione-div-container" style="float: left;">
			<ul class="inline barra-navigazione">
				<c:if test="${ navigaAggregata.descNatura != null }">
						<li><span class="label label-info riepilogo-filtri">Natura: ${ navigaAggregata.descNatura }
							&nbsp;<i class="icon-ok-circle vertical-align-middle icon-2x"></i>
						</span></li>			
				</c:if>
				<c:if test="${ navigaAggregata.descAreaIntervento != null }">																		
					<li><span class="label label-info riepilogo-filtri">Area Intervento: ${ navigaAggregata.descAreaIntervento } 
						&nbsp;<i class="icon-remove-circle vertical-align-middle cursor-pointer pulisci icon-2x" data-parent1="idAreaIntervento" data-parent2="idSottosettoreIntervento" data-parent3="idCategoriaIntervento" ></i>
					</span></li>
				</c:if>
				<c:if test="${ navigaAggregata.descSottosettoreIntervento != null }">	
					<li><span class="label label-info riepilogo-filtri">Sottosettore Intervento: ${ navigaAggregata.descSottosettoreIntervento } 
						&nbsp;<i class="icon-remove-circle vertical-align-middle cursor-pointer pulisci icon-2x" data-parent1="idSottosettoreIntervento" data-parent2="idCategoriaIntervento"  ></i>
					</span></li>
				</c:if>
				<c:if test="${ navigaAggregata.descCategoriaIntervento != null }">																								
					<li><span class="label label-info riepilogo-filtri">Categoria Intervento: ${ navigaAggregata.descCategoriaIntervento } 
						&nbsp;<i class="icon-remove-circle vertical-align-middle cursor-pointer pulisci icon-2x" data-parent1="idCategoriaIntervento" ></i>
					</span></li>
				</c:if>
			</ul>

			
		</div>
		
		<div class="header toggler-header-collapsed" style="float: right;">
			<div id="affina-ricerca-classificazione" class="affina-ricerca-div affina-ricerca cursor-pointer">
				Filtri di Ricerca
				<span>
					<i class="icon-filter"> 
						<c:if test="${ navigaAggregata.countAffRicerca4Natura != null }">
							<span class="icon-stack" id="filtriBadge">
				          		<i class="icon-circle icon-stack-base" ></i>
				          		<i class="icon-light">${ navigaAggregata.countAffRicerca4Natura }</i>
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
						<aui:input type="hidden" bean="navigaAggregata" name="idComune" value="${navigaAggregata.idComune}" />
						<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" />
						<div>
							<div class="row">
								<div class="ricerca span5">
									<%-- 
									<div class="control-group no-margin-bottom">
										<strong class="control-label">Classificazione</strong>
										<div class="controls">&nbsp;</div>
									</div>
									
									<div class="control-group no-margin-bottom" id="area-intervento-div">
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
								
									<div class="control-group no-margin-bottom" id="sotto-settore-intervento-div">
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
						
									<div class="control-group no-margin-bottom" id="categoria-intervento-div">
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
									--%>
								
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
										        </c:forEach>
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
										<strong class="control-label">Localizzazione</strong>
										<div class="controls">&nbsp;</div>
									</div>
				
									<div class="control-group no-margin-bottom row-no-wrap" id="stato-div">
										<label class="control-label" for="stato">Stato</label>
										<div class="controls">
											<aui:input type="text" value="${navigaAggregata.descStato}" readonly="readonly" cssClass="input-large stato" label="" bean="navigaAggregata" name="descStato" id="stato"></aui:input>
										</div>
									</div>
								
									<div class="control-group no-margin-bottom row-no-wrap" id="area-geografica-div">
										<label class="control-label" for="areaGeografica">Area Geografica</label>
										<div class="controls form-inline">
											<aui:select inlineField="true" cssClass="input-large area-geografica" label="" bean="navigaAggregata" name="idAreaGeografica" id="areaGeografica">
												<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idAreaGeografica == -1}"/>
												<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
										            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${navigaAggregata.idAreaGeografica == areaGeografica.id}"/>
										        </c:forEach>
											</aui:select>
											<i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>
										</div>
									</div>
					 
									<div class="control-group no-margin-bottom row-no-wrap" id="regione-div">
										<label class="control-label" for="regione">Regione</label>
										<div class="controls form-inline">
											<aui:select inlineField="true" cssClass="input-large regione" label="" bean="navigaAggregata" name="idRegione" id="regione">
												<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idRegione == -1}"/>
												<c:forEach items="${listRegione}" var="regione" >
										            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${navigaAggregata.idRegione == regione.id}"/>
										        </c:forEach>
											</aui:select>
											<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
										</div>
									</div>
								
									<div class="control-group no-margin-bottom row-no-wrap" id="provincia-div">
										<label class="control-label" for="provincia">Provincia</label>
										<div class="controls form-inline">
											<aui:select inlineField="true" cssClass="input-large provincia" label="" bean="navigaAggregata" name="idProvincia" id="provincia">
												<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idProvincia == -1}"/>
												<c:forEach items="${listProvincia}" var="provincia" >
										            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${navigaAggregata.idProvincia == provincia.id}"/>
										        </c:forEach>
											</aui:select>
											<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
										</div>
									</div>
								
									<%-- 
									<div class="control-group no-margin-bottom" id="comune-div">
										<label class="control-label" for="comune">Comune</label>
										<div class="controls form-inline">
											<aui:select inlineField="true" cssClass="input-large comune" label="" bean="navigaAggregata" name="idComune" id="comune">
												<aui:option value="-1" label="ricerca.tutte" selected="${navigaAggregata.idComune == -1}"/>
												<c:forEach items="${listComune}" var="comune" >
										            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${navigaAggregata.idComune == comune.id}"/>
										        </c:forEach>
											</aui:select>
											<i class="icon-remove-circle pulisciElementoComune" style="cursor: pointer;"></i>
										</div>
									</div>
									--%>
								
								</div>
							</div>
						</div>

					</div>
					
					<div class="card-action">
						<div class="control-group">
							<div class="pull-right">
								<aui:button id="affina-ricerca-classificazione" cssClass="btn btn-primary btn-filtra" value="Filtra &nbsp;<i class='icon-filter'></i>"></aui:button>
								<aui:button id="affina-ricerca-classificazione" cssClass="btn btn-rimuovi-filtri" value="Rimuovi Filtri &nbsp;<i class='icon-undo'></i>"></aui:button>
							</div>
						</div>
					</div>

				</div>
			
			</aui:form>
		
		</div>
		
	</div>
	
	<div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- RIEPILOGO --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span6">
			
			<div class="card">
				<div class="card-title">
	            	<span class="title">Naviga Classificazione</span>
	       		</div>
				<div class="card-content">
					<div style="text-align: justify;">
						La sintesi per Classificazione mette in evidenza i dati aggregati della totalità dei progetti, è possibile proseguire nei dati 
						aggregati navigando nelle ulteriori classificazioni:
						Area Intervento > Sottosettori > Categoria
					</div>
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
							<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${costoDeiProgetti}" type="currency" minIntegerDigits="0" maxFractionDigits="0"/></div>
						</div>
						<div class="span4 dati_sitesi dati_sitesi_lilla">
							<div class="celle_dati_sitesi font-size3em"><i class="icon-eur"></i></div>
							<div class="celle_dati_sitesi font-size1em">Importo Finanziato</div>
							<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${importoFinanziamenti}" type="currency" minIntegerDigits="0" maxFractionDigits="0"/></div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="card-action">
					<div class="link_elenco-progetti span4 offset8">
						<aui:a href="${linkURLElencoProgetti}" cssClass="block">
							Vai a Elenco Progetti <i class="icon-list"></i>
						</aui:a>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			
			<%-- 
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
				<liferay-ui:search-iterator paginate="false" searchContainer="${searchContainerRiepilogo}"/>
			</liferay-ui:search-container>
			--%>
		</div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- TORTA --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span6 div_pie_chart_1">
		
			<a name="classificazione-portlet1"></a>
			<div class="card">
				<div class="card-title">
	            	<span class="title">Distribuzione Percentuale</span>
	        	</div>
	        	<div class="card-content" style="height: 397px">

					<div class="div_pie_chart" id="pie_chart_1">
		            	<div class="chart" id="pieChartClassificazione" style="text-align: center"></div>
		            </div>
           
		            <div id="tooltip-classificazione-portlet1" class="tooltip-classificazione-portlet1 hidden">
						<p><span id="label-tooltip-classificazione-portlet1"></span></p>
						<p>
							<strong><span>Percentuale</span>:&nbsp;</strong><span id="percentuale-tooltip-classificazione-portlet1"></span>
						</p>
						<p>
							<strong><span id="labelvalue-tooltip-classificazione-portlet1"></span>:&nbsp;</strong>
							<span id="value-tooltip-classificazione-portlet1"></span>
							<span class="hidden" id="umvalue-tooltip-classificazione-portlet1">&euro;</span>
						</p>
					</div>
		
					<%-- 
					<div id="pieChartClassificazione" style="text-align: center">
					</div>
					--%>

					<div class="loader">Loading...</div>
		
					<div class="alert alert-info pieChartClassificazioneEmpty" id="pieChartClassificazioneEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>

				</div>
				
				<div class="card-action pieChartClassificazioneToolBar" id="pieChartClassificazioneToolBar">
					<div class="btn-carica-distribuzione span4 classificazione-sel-btn block" data-classificazione="VOLUME">
						<aui:a href="#" onClick="return false" cssClass="block">
							VOLUME
						</aui:a>
					</div>
					<div class="btn-carica-distribuzione span4 classificazione-sel-btn" data-classificazione="COSTO">
						<aui:a href="#" onClick="return false" cssClass="block">
							COSTO
						</aui:a>
					</div>
					<div class="btn-carica-distribuzione span4 classificazione-sel-btn" data-classificazione="IMPORTO">
						<aui:a href="#" onClick="return false" cssClass="block">
							IMPORTO
						</aui:a>
					</div>
					<div class="clear"></div>
				</div>	
				<%-- 
				<div class="card-action pieChartClassificazioneToolBar" id="pieChartClassificazioneToolBar">
					<aui:button-row cssClass="btn-group btn-group-justified">
						<aui:button cssClass="classificazione-sel-btn btn btn-default" data-classificazione="VOLUME" value="VOLUME" />
						<aui:button cssClass="classificazione-sel-btn btn btn-default" data-classificazione="COSTO" value="COSTO" />
						<aui:button cssClass="classificazione-sel-btn btn btn-default" data-classificazione="IMPORTO" value="IMPORTO" />
					</aui:button-row>
				</div>	
				 --%>
			</div>

		</div>
		
	</div>
	
	<div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- TABELLA --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div class="span12">
		
			<div class="card">
				<div class="card-title">
	            	<span class="title">Dettaglio Distribuzione</span>
	       		</div>
				<div class="card-content">
					
					<div style="text-align: justify;">
						Continua la navigazione selezionando ${artNavigaPer} <strong>${navigaPer}</strong> dei progetti
					</div>
					
					<fmt:setLocale value="it_IT"/>

					<liferay-ui:search-container searchContainer="${searchContainerDettaglio}" delta="${searchContainerDettaglio.delta}" orderByType="${searchContainerDettaglio.orderByType}" deltaParam="delta">
					
						<liferay-ui:search-container-results results="${searchContainerDettaglio.results}" total="${searchContainerDettaglio.total}"/>    
					
					    <liferay-ui:search-container-row className="it.dipe.opencup.dto.AggregataDTO" keyProperty="id" modelVar="aggregataDTO">
								
							<liferay-ui:search-container-column-text name="aggregato-des">
								<a href="#" data-id="${aggregataDTO.id}" onclick="return false;" data-url="${aggregataDTO.linkURL}" class="link-url-naviga-dettaglio">${aggregataDTO.descURL}</a>
							</liferay-ui:search-container-column-text>
							
							<liferay-ui:search-container-column-text name="aggregato-volume" orderableProperty="numeProgetti" orderable="true">
								<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.numeProgetti}" type="number" minIntegerDigits="1"/></span>
							</liferay-ui:search-container-column-text>
							
							<liferay-ui:search-container-column-text name="aggregato-costo" orderableProperty="impoCostoProgetti" orderable="true">
								<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.impoCostoProgetti}" type="currency" minIntegerDigits="1" maxFractionDigits="2" minFractionDigits="2"/></span>
							</liferay-ui:search-container-column-text>
							
							<liferay-ui:search-container-column-text name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
								<span class="pull-right"><fmt:formatNumber value="${aggregataDTO.impoImportoFinanziato}" type="currency" minIntegerDigits="1" maxFractionDigits="2"  minFractionDigits="2"/></span>
							</liferay-ui:search-container-column-text>
						
						</liferay-ui:search-container-row>
					
						<liferay-ui:search-iterator paginate="false" searchContainer="${searchContainerDettaglio}"/>
					
					</liferay-ui:search-container>
					
					<a name="classificazione-portlet2"></a>
					
				</div>
			</div>

		</div>
	</div>
	
</div>

<portlet:actionURL var="ricercaActionVar">
   	<portlet:param name="action" value="ricerca"></portlet:param>
</portlet:actionURL>

<aui:form 
	action="${ricercaActionVar}" 
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
			
	var namespace = "<portlet:namespace/>";
	namespace = namespace.substring(1, namespace.length - 1);
				
	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
</script>