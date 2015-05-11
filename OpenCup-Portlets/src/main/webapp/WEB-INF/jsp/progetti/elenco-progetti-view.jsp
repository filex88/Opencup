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

<style>
	.valign-middle {
		vertical-align: middle !important;
	}
	.bordo {
		border: solid 2px #1f4e78;
	}
	.intestazione{
		padding-top: 15px;
		padding-left: 5px;
	}
	
	#_elencoprogettiportlet_WAR_OpenCupPortletsportlet_progettosSearchContainer_col-1,
	#_elencoprogettiportlet_WAR_OpenCupPortletsportlet_documentoDTOsSearchContainer_col-1
	{
		border-bottom:  solid 2px #1f4e78 !important;
		
	}
	
	#_elencoprogettiportlet_WAR_OpenCupPortletsportlet_progettosSearchContainer_col-aggregato-costo{
		border-bottom:  solid 2px #f08c00 !important;
	}
	
	#_elencoprogettiportlet_WAR_OpenCupPortletsportlet_progettosSearchContainer_col-aggregato-importo{
		border-bottom:  solid 2px #c90061 !important;
	}
	
	div.titolo p{padding:1em;font-size:18pt;color:#1f4e78;}
	div.sintesi p{padding-left:1em;padding-right:1em;text-align:justify;color:#1f4e78;}

	div.summary ul li{list-style: none;margin-top:1em;margin-left: -2em;}
	div.summary ul li span{color: #1f4e78;}
	div.summary ul li.sumVolume svg {height: .8em; background-color: #009600;}
	div.summary ul li.sumCosto svg {height: .8em; background-color: #f08c00;}
	div.summary ul li.sumImporto svg {height: .8em;}
	div.summary ul li.sumImporto rect:first-of-type {fill: #d9d9d9;}
	div.summary ul li.sumImporto rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #c90061;}
	
	div.my-toggler-affina-ricerca-elenco-progetti,
	div.toggler-content-wrapper,
	div.toggler-content,
	div.table-container {padding: 1em;}
	
	div.stripe{background: #fff;border-top:.5em solid #f0f0f0;}
	
</style>

<fmt:setLocale value="it_IT"/>

<portlet:defineObjects />

<liferay-theme:defineObjects/>

<div class="stripe">

	<liferay-ui:success key="valore-ricerca-non-valido" message="valore-ricerca-non-valido-1" />

	<c:if test="${currentAction eq 'elencoProgetti'}">
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- RIEPILOGO --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<div>
		
			<div class="span6">
		        <div class="titolo"><p>Elenco Progetti</p></div>
				<div class="sintesi"><p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. 
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet.
				</p></div>
			</div>
				
			<div class="span6 summary">
			
				<ul>
					<li class="sumVolume w100">
						<div>
							<span class="left"><small>Progetti censiti</small></span>
							<span class="right"><small><fmt:formatNumber value="${volumeDeiProgetti}" type="number" minIntegerDigits="0"/></small></span>
							<svg class="w100"></svg>
						</div>
					</li>
					<li class="sumCosto w100">
						<div>
							<span class="left"><small>Costo</small></span>
							<span class="right"><small id="costoTotale"></small></span>
							<svg class="w100"></svg>
						</div>
					</li>
					<li class="sumImporto w100">
					<div>
						<span class="left"><small>Finanziamento pubblico</small></span>
						<span class="right"><small id="importoTotale"></small></span>
						<svg class="w100"></svg>
					</div>
					</li>
				</ul>
			
				<%-- 
			 	<div class="card">
					<div class="card-title">
			           	<span class="title">Dati di sintesi</span>
			       	</div>
			       	<div class="card-content">
						<div>
							<div class="span4 dati_sitesi dati_sitesi_verde">
								<div class="celle_dati_sitesi font-size3em"><i class="icon-bar-chart"></i></div>
								<div class="celle_dati_sitesi font-size1em">Volume</div>
								<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${volumeDeiProgetti}" type="number" maxFractionDigits="0" minIntegerDigits="0"/></div>
							</div>
							<div class="span4 dati_sitesi dati_sitesi_arancio">
								<div class="celle_dati_sitesi font-size3em"><i class="icon-tags"></i></div>
								<div class="celle_dati_sitesi font-size1em">Costo</div>
								<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${costoDeiProgetti}" type="currency" minIntegerDigits="0" maxFractionDigits="0" minFractionDigits="0"/></div>
							</div>
							<div class="span4 dati_sitesi dati_sitesi_lilla">
								<div class="celle_dati_sitesi font-size3em"><i class="icon-eur"></i></div>
								<div class="celle_dati_sitesi font-size1em">Importo Finanziato</div>
								<div class="celle_dati_sitesi font-size2em"><fmt:formatNumber value="${importoFinanziamenti}" type="currency" minIntegerDigits="0" maxFractionDigits="0" minFractionDigits="0"/></div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			--%>
		</div>
		
		<div id="my-toggler-affina-ricerca-elenco-progetti" class="my-toggler-affina-ricerca-elenco-progetti">
			
			<div class="header toggler-header-collapsed" style="float: right; height: 0px">
				<div id="affina-ricerca" class="affina-ricerca-div affina-ricerca cursor-pointer">
					AFFINA LA RICERCA
					<span>
						<i class="icon-filter"> 
							<c:if test="${ navigaProgetti.countAffRicerca != null }">
								<span class="icon-stack" id="filtriBadge">
					          		<i class="icon-circle icon-stack-base" ></i>
					          		<i class="icon-light">${ navigaProgetti.countAffRicerca }</i>
								</span>
							</c:if> 
						</i>
					</span>
				</div>
			</div>
			
			<div class="content toggler-content-collapsed bordo">
				<portlet:actionURL var="affinaRicercaActionVar">
				   	<portlet:param name="action" value="ricerca"></portlet:param>
				</portlet:actionURL>
		
				<aui:form 
					action="${affinaRicercaActionVar}" 
					method="post" 
					name="ricerca-form" 
					id="ricerca-form" 
					cssClass="form-horizontal ricerca-form">	
				
					<div>
						<div>
				           	<span><strong>Filtri di ricerca <i class='icon-filter'></i></strong></span>
				       	</div>
				       	<div>
				       		<div class="span6">
				       		
								<aui:input type="hidden" bean="navigaProgetti" name="naviga" value="${navigaProgetti.naviga}" />
								<aui:input type="hidden" bean="navigaProgetti" name="idNatura" value="${navigaProgetti.idNatura}" />
								
								<c:choose>
									<c:when test=' ${navigaProgetti.naviga eq "classificazione"} '>
									
										<aui:input type="hidden" bean="navigaProgetti" name="idAreaIntervento" value="${navigaProgetti.idAreaIntervento}" />
										<aui:input type="hidden" bean="navigaProgetti" name="idSottosettoreIntervento" value="${navigaProgetti.idSottosettoreIntervento}" />
										<aui:input type="hidden" bean="navigaProgetti" name="idCategoriaIntervento" value="${navigaProgetti.idCategoriaIntervento}" />
									
									</c:when>
									<c:otherwise>
									
										<div class="control-group no-margin-bottom">
											<strong class="control-label">Classificazione</strong>
											<div class="controls">&nbsp;</div>
										</div>
										
										<div class="control-group no-margin-bottom row-no-wrap" id="area-intervento-div">
											<label class="control-label" for="area-intervento">Area Intervento</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge area-intervento" label="" bean="navigaProgetti" name="idAreaIntervento" id="area-intervento">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaIntervento == -1}"/>
													<c:forEach items="${listAreaIntervento}" var="areaIntervento" >
														<aui:option value="${areaIntervento.id}" label="${areaIntervento.descAreaIntervento}" selected="${navigaProgetti.idAreaIntervento == areaIntervento.id}"/>
													</c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoAreaIntervento" style="cursor: pointer;"></i>
											</div>
										</div>
										
										<div class="control-group no-margin-bottom row-no-wrap" id="sotto-settore-intervento-div">
											<label class="control-label" for="sotto-settore-intervento">Sotto Settore Intervento</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge sotto-settore-intervento" label="" bean="navigaProgetti" name="idSottosettoreIntervento" id="sotto-settore-intervento">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idSottosettoreIntervento == -1}"/>
													<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
														<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${navigaProgetti.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
													</c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>
											</div>
										</div>
										
										<div class="control-group no-margin-bottom row-no-wrap" id="categoria-intervento-div">
											<label class="control-label" for="categoria-intervento">Categoria</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge categoria-intervento" label="" bean="navigaProgetti" name="idCategoriaIntervento" id="categoria-intervento">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idCategoriaIntervento == -1}"/>
													<c:forEach items="${listaCategoriaIntervento}" var="categoria" >
														<aui:option value="${categoria.id}" label="${categoria.descCategoriaIntervento}" selected="${navigaProgetti.idCategoriaIntervento == categoria.id}"/>
													</c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoCategoriaIntervento" style="cursor: pointer;"></i>
											</div>
										</div>	
									
									</c:otherwise>
								</c:choose>
								
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Gerarchia Soggetto</strong>
									<div class="controls">&nbsp;</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
									<label class="control-label" for="area-soggetto">Area</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge area-soggetto" label="" bean="navigaProgetti" name="idAreaSoggetto" id="area-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaSoggetto == -1}"/>
											<c:forEach items="${listAreaSoggetto}" var="areasoggetto" >
									            <aui:option value="${areasoggetto.id}" label="${areasoggetto.descAreaSoggetto}" selected="${navigaProgetti.idAreaSoggetto == areasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoAreaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
							
								<div class="control-group no-margin-bottom row-no-wrap" id="categoria-soggetto-div">
									<label class="control-label" for="categoria-soggetto">Categoria</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge categoria-soggetto" label="" bean="navigaProgetti" name="idCategoriaSoggetto" id="categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idCategoriaSoggetto == -1}"/>
											<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
									            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${navigaProgetti.idCategoriaSoggetto == categoriasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="sotto-categoria-soggetto-div">
									<label class="control-label" for="sotto-categoria-soggetto">Sotto Categoria</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge sotto-categoria-soggetto" label="" bean="navigaProgetti" name="idSottoCategoriaSoggetto" id="sotto-categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idSottoCategoriaSoggetto == -1}"/>
											<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
									            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottocategSoggetto}" selected="${navigaProgetti.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="tipologia-div">
									<label class="control-label" for="tipologia"><strong>Tipologia Intervento</strong></label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge tipologia" label="" bean="navigaProgetti" name="idTipologiaIntervento" id="tipologia">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idTipologiaIntervento == -1}"/>
											<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
									            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${navigaProgetti.idTipologiaIntervento == tipologiaintervento.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
									</div>
								</div>
					
								<div class="control-group no-margin-bottom row-no-wrap" id="statoprogetto-div">
									<label class="control-label" for="statoprogetto"><strong>Stato Progetto</strong></label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="input-xlarge statoprogetto" label="" bean="navigaProgetti" name="idStatoProgetto" id="statoprogetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idStatoProgetto == -1}"/>
											<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
									            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${navigaProgetti.idStatoProgetto == statoprogetto.id}"/>
									        </c:forEach>
										</aui:select>
										<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
									</div>
								</div>
								
							</div>
							
							<div class="span6">	
								<c:choose>
									<c:when test=' ${navigaProgetti.naviga eq "localizzazione"} '>
									
										<aui:input type="hidden" bean="navigaProgetti" name="descStato" value="${navigaProgetti.descStato}" />
										<aui:input type="hidden" bean="navigaProgetti" name="idAreaGeografica" value="${navigaProgetti.idAreaGeografica}" />
										<aui:input type="hidden" bean="navigaProgetti" name="idRegione" value="${navigaProgetti.idRegione}" />
										<aui:input type="hidden" bean="navigaProgetti" name="idProvincia" value="${navigaProgetti.idProvincia}" />
										<aui:input type="hidden" bean="navigaProgetti" name="idComune" value="${navigaProgetti.idComune}" />
									
									</c:when>
									<c:otherwise>
									
										<div class="control-group no-margin-bottom">
											<strong class="control-label">Localizzazione</strong>
											<div class="controls">&nbsp;</div>
										</div>
								
										<div class="control-group no-margin-bottom row-no-wrap" id="stato-div">
											<label class="control-label" for="stato">Stato</label>
											<div class="controls">
												<aui:input type="text" value="${navigaProgetti.descStato}" readonly="readonly" cssClass="input-xlarge stato" label="" bean="navigaProgetti" name="descStato" id="stato"></aui:input>
											</div>
										</div>
										
										<div class="control-group no-margin-bottom row-no-wrap" id="area-geografica-div">
											<label class="control-label" for="areaGeografica">Area Geografica</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge area-geografica" label="" bean="navigaProgetti" name="idAreaGeografica" id="areaGeografica">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaGeografica == -1}"/>
													<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
											            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${navigaProgetti.idAreaGeografica == areaGeografica.id}"/>
											        </c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>
											</div>
										</div>
										 
										<div class="control-group no-margin-bottom row-no-wrap" id="regione-div">
											<label class="control-label" for="regione">Regione</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge regione" label="" bean="navigaProgetti" name="idRegione" id="regione">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idRegione == -1}"/>
													<c:forEach items="${listRegione}" var="regione" >
											            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${navigaProgetti.idRegione == regione.id}"/>
											        </c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
											</div>
										</div>
										
										<div class="control-group no-margin-bottom row-no-wrap" id="provincia-div">
											<label class="control-label" for="provincia">Provincia</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge provincia" label="" bean="navigaProgetti" name="idProvincia" id="provincia">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idProvincia == -1}"/>
													<c:forEach items="${listProvincia}" var="provincia" >
											            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${navigaProgetti.idProvincia == provincia.id}"/>
											        </c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
											</div>
										</div>
										
										<div class="control-group no-margin-bottom row-no-wrap" id="comune-div">
											<label class="control-label" for="comune">Comune</label>
											<div class="controls form-inline">
												<aui:select inlineField="true" cssClass="input-xlarge comune" label="" bean="navigaProgetti" name="idComune" id="comune">
													<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idComune == -1}"/>
													<c:forEach items="${listComune}" var="comune" >
											            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${navigaProgetti.idComune == comune.id}"/>
											        </c:forEach>
												</aui:select>
												<i class="icon-remove-circle pulisciElementoComune" style="cursor: pointer;"></i>
											</div>
										</div>
									
									</c:otherwise>
								</c:choose>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="anno-div">
									<label class="control-label" for="anno"><strong>Anno Decisione</strong></label>
									<div class="controls">
										<aui:select multiple="true" inlineField="true" cssClass="input-xlarge anno" label="" bean="navigaProgetti" name="idAnnoDecisiones" id="anno">
											
											<c:set var="selected" value="false" />
											<c:forEach items="${navigaProgetti.idAnnoDecisiones}" var="annoSel" >
												<c:if test="${annoSel == -1}">
													<c:set var="selected" value="true" />
												</c:if>
											</c:forEach>
											<aui:option value="-1" label="ricerca.tutte" selected="${selected}"/>
											
											<c:forEach items="${listaAnnoDecisione}" var="anno" >
												<c:set var="selected" value="false" />
												<c:forEach items="${navigaProgetti.idAnnoDecisiones}" var="annoSel" >
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
								
							</div>
							<div class="clear"></div>
						</div>
							
						<div>
							<div class="control-group">
								<div class="pull-right">		
									<aui:button type="submit" id="affina-ricerca-classificazione" cssClass="btn btn-primary btn-filtra" value="Filtra &nbsp;<i class='icon-filter'></i>"></aui:button>
									<aui:button id="affina-ricerca" cssClass="btn btn-rimuovi-filtri" value="Rimuovi Filtri &nbsp;<i class='icon-undo'></i>"></aui:button>
								</div>
							</div>
						</div>
						
					</div>
				</aui:form>
			</div>
		
		</div>
	</c:if>
	
	<div class="intestazione">
		<span><strong>Elenco dei progetti <i class="icon-list"></i></strong></span>
	</div>
	<div class="table-container">
		<liferay-ui:search-container searchContainer="${searchContainerElenco}" delta="${searchContainerElenco.delta}" orderByType="${searchContainerElenco.orderByType}" deltaParam="delta">
			
			<liferay-ui:search-container-results results="${searchContainerElenco.results}" total="${searchContainerElenco.total}"/>    
			
			<liferay-ui:search-container-row className="it.dipe.opencup.model.Progetto" keyProperty="id" modelVar="progetti">
		
				<liferay-ui:search-container-column-jsp path="/WEB-INF/jsp/progetti/riga-elenco-progetti.jsp" />		
				
				<liferay-ui:search-container-column-text cssClass="valign-middle" name="aggregato-costo" orderableProperty="impoCostoProgetto" orderable="true">
					<span class="pull-right colonne-block"><fmt:formatNumber value="${progetti.impoCostoProgetto}" type="currency" minIntegerDigits="1" maxFractionDigits="2" minFractionDigits="2"/></span>
				</liferay-ui:search-container-column-text>
				
				<liferay-ui:search-container-column-text cssClass="valign-middle" name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
					<span class="pull-right colonne-block"><fmt:formatNumber value="${progetti.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  maxFractionDigits="2" minFractionDigits="2"/></span>
				</liferay-ui:search-container-column-text>
			
			</liferay-ui:search-container-row>
				
			<liferay-ui:search-iterator paginate="${paginate}" searchContainer="${searchContainerElenco}" />
				
		</liferay-ui:search-container>
	</div>
	
	<c:if test="${action eq 'ricercaLibera'}">
		<div class="intestazione">
			<span><strong>Elenco dei documenti <i class="icon-list"></i></strong></span>
		</div>
		<div class="table-container">
			<liferay-ui:search-container searchContainer="${searchContainerElencoDoc}" delta="${searchContainerElencoDoc.delta}" orderByType="${searchContainerElencoDoc.orderByType}" deltaParam="deltaDoc">
			
				<liferay-ui:search-container-results results="${searchContainerElencoDoc.results}" total="${searchContainerElencoDoc.total}"/>    
				
				<liferay-ui:search-container-row className="it.dipe.opencup.dto.DocumentoDTO" keyProperty="id" modelVar="documento">
				
					<liferay-ui:search-container-column-text>
						<div>
							<p>
								<a href="${documento.url}" class="link-url-dettaglio">
								<strong>${documento.titolo}</strong></a>
							</p>
						</div>
						
						<div>
							<p>
								<span>${documento.testo}</span>
							</p>
						</div>
					</liferay-ui:search-container-column-text>
					
				</liferay-ui:search-container-row>
					
				<liferay-ui:search-iterator paginate="false" searchContainer="${searchContainerElencoDoc}" />
					
			</liferay-ui:search-container>
		</div>
	</c:if>
</div>
<script type="text/javascript">

	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	
	var currentAction = "${currentAction}";

	function nFormatterBar(num){
		if (num >= 1000000000) {
	        return (num / 1000000000).toFixed(1).replace(/\.0$/, '') + ' Mld ';
	     }
	     if (num >= 1000000) {
	        return (num / 1000000).toFixed(1).replace(/\.0$/, '') + ' Mil ';
	     }
	     if (num >= 1000) {
	        return (num / 1000).toFixed(0).replace(/\.0$/, '') + ' K ';
	     }
	     return num;

	}
	
	if(currentAction == 'elencoProgetti'){
		d3.selectAll("#costoTotale").text(nFormatterBar("${costoDeiProgetti}")+"\u20ac");
		
		d3.selectAll("#importoTotale").text(nFormatterBar("${importoFinanziamenti}")+"\u20ac");
		
		function drawFinBar(){
			// barra importo finanziato
			var widthLiFinanziato=d3.selectAll("li.sumImporto")[0][0].clientWidth;
			var rangeFinanziato=["${costoDeiProgetti}","${importoFinanziamenti}"];
			var xFinanziato=d3.scale.linear().domain([0, d3.max(rangeFinanziato)]).range([0, widthLiFinanziato]);

			d3.selectAll("li.sumImporto").select("div").select("svg").selectAll("rect").data(rangeFinanziato)
			.enter().append("rect")
			.attr("width", xFinanziato)
			.attr("height", ".8em");
		}
		drawFinBar();
	}
	

	/*
	// clear breadcrumb
	
	var fatherUl=d3.selectAll("li.first").node().parentNode;

	d3.select(fatherUl).insert("li",":first-child")
	.attr("style","padding: 0 5px;color:#fcfcfc")
	.text("Sei in: ");

	d3.selectAll(".divider").each(
			function(){
			var c=d3.select(this).node().parentNode;
			d3.select(c)
				.style("font-weight","bold")
				.append("i")
				.attr("class","icon-caret-right")
				.attr("style","padding: 0 5px;color:#fcfcfc");
		
				d3.select(c).select("span").remove();
		});
//


//d3.selectAll("li.current-parent.breadcrumb-truncate").selectAll("i").remove();
d3.selectAll("li.active.last.breadcrumb-truncate").selectAll("i").remove();
*/	
	
</script>


