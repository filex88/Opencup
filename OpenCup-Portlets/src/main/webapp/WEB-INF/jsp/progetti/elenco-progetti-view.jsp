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
	
	valign-top {
		vertical-align: top !important;
		padding-top: 25px;
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
		border-bottom:  solid 5px #1f4e78 !important;
		
	}
	
	#_elencoprogettiportlet_WAR_OpenCupPortletsportlet_progettosSearchContainer_col-aggregato-costo{
		border-bottom:  solid 5px #499652 !important;
	}
	
	#_elencoprogettiportlet_WAR_OpenCupPortletsportlet_progettosSearchContainer_col-aggregato-importo{
		border-bottom:  solid 5px #7ade87 !important;
	}
	
	div.titolo p{padding:1em;font-size:18pt;color:#1f4e78;}
	div.sintesi p{padding-left:1em;padding-right:1em;text-align:justify;color:#1f4e78;}

	div.summary ul li{list-style: none;margin-top:1em;margin-left: -2em;}
	div.summary ul li span{color: #1f4e78;}

	div.summary ul li.sumVolume svg {height: .8em;}
	div.summary ul li.sumVolume rect:first-of-type {fill: #d9d9d9;}
	div.summary ul li.sumVolume rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #f08c00;}

	div.summary ul li.sumCosto svg {height: .8em;}
	div.summary ul li.sumCosto rect:first-of-type {fill: #d9d9d9;}
	div.summary ul li.sumCosto rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #499652;}
		
	div.summary ul li.sumImporto svg {height: .8em;}
	div.summary ul li.sumImporto rect:first-of-type {fill: #d9d9d9;}
	div.summary ul li.sumImporto rect:nth-of-type(2) {color: #fff;stroke: transparent;fill: #7ade87;}
	
	div.my-toggler-affina-ricerca-elenco-progetti,
	div.toggler-content-wrapper,
	div.toggler-content,
	div.table-container {padding: 1em;}
	
	div.stripe{background: #fff; border-top:.5em solid #f0f0f0;}
	
	.aui .table-bordered{
		border: none;
	}
	
	.aui .table-bordered th, .aui .table-bordered td{
		border-bottom: none;
		border-left: none;
		border-right: none;
	}
	
	.aui .table-hover tbody tr:hover>td, .aui .table-hover tbody tr:hover>th{
		background-color: #fff;
	}
	
	.aui .table-striped tbody>tr:nth-child(odd)>td, .aui .table-striped tbody>tr:nth-child(odd)>th{
		background-color:#fff;
	}
	
	.aui .table th{
		color: #1f4e78;
		border-top: 0px solid #ddd !important;
	}
	
	.aui .table td{
		color: #1f4e78;
		border-bottom: 10px solid #f0f0f0 !important;
		border-top: 0px solid #ddd !important;
	}
	
	.aui a{
		color: #1f4e78;
	}
	
	.classificazione-portlet1 .intestazione-tab, .classificazione-portlet1 .table-columns th, .elenco-progetti-portlet .intestazione-tab, .elenco-progetti-portlet .table-columns th, #tabRisultati .intestazione-tab, #tabRisultati .table-columns th{
		background-color: #fff;
	}
	
	div.my-toggler-affina-ricerca-elenco-progetti, div.toggler-content-wrapper, div.toggler-content, div.table-container{
		padding-top: 1em;
		padding-bottom: 1em;
		padding-right: 0px;
		padding-left: 0px;
	}
	
	.w100{
		width: 100%;
	}
	
	.w75{
		width: 75%;
	}
	
	.w50{
		width: 50%;
	}
	
	.w23{
		width: 23%;
	}
	
	.w2{
		width: 2%;
	}
	
	.centra-testo{
		text-align: center !important;
	}
	
	.sumVolume, .sumCosto, .sumImporto{
		text-align: center !important;
	}
	
	.impo{
		font-size: 1.6em;
		font-weight: bold;
	}

	.ricerca-form-elenco-campo{
		/*border-radius: 10px !important;*/
		border-color: #004383 !important;
		border-width: 1px !important;
		border-style: solid !important;
	}
	
	.btn-rimuovi-filtri{
		  height: 3em;
		  background: #ccc !important;
		  border-color: #ccc !important;
		  color: #FFF !important;
		  padding-left: 1em;
		  text-align: center;
		  padding-right: 1em;
		  min-width: 12em;
		  border-radius: 10px !important;
	}
	
	.btn-filtra{
	  height: 3em;
	  background: #004383 !important;
	  color: #FFF;
	  padding-left: 1em;
	  text-align: center;
	  padding-right: 1em;
	  min-width: 12em;
	  border-radius: 10px !important;
	}
	
	.ricerca-form-elenco{
		margin: 0px !important;
	}
	
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
				
			<div class="span4 offset1 summary">
			
				<ul>
					<li class="sumVolume w100">
						<div>
							<span class="left"><small>Progetti censiti</small></span>
							<%--<span class="right"><small><fmt:formatNumber value="${volumeDeiProgetti}" type="number" volumeDeiProgettiProg="0"/></small></span>--%>
							<span class="right"><small id="volumeTotale"></small></span>
							<svg class="w100"></svg>
						</div>
					</li>
					<li class="sumCosto w100">
						<div>
							<span class="left"><small>Costo Previsto</small></span>
							<span class="right"><small id="costoTotale"></small></span>
							<svg class="w100"></svg>
						</div>
					</li>
					<li class="sumImporto w100">
						<div>
							<span class="left"><small>Finanziamento Pubblico Previsto</small></span>
							<span class="right"><small id="importoTotale"></small></span>
							<svg class="w100"></svg>
						</div>
					</li>
				</ul>
				
			</div>
			
			<div class="clear"></div>
		
		</div>	
	</c:if>
	
	<c:if test="${currentAction eq 'elencoProgetti' || currentAction eq 'ricercaAvanzata'}">
		<div id="my-toggler-affina-ricerca-elenco-progetti" class="my-toggler-affina-ricerca-elenco-progetti" style="border-top:.5em solid #f0f0f0">
			
			<c:choose>
				<c:when test="${currentAction eq 'elencoProgetti'}">
					<div class="header-elenco-progetti toggler-header-collapsed" style="float: right; height: 0px;padding-right: 40px;">
						<div id="affina-ricerca" class="affina-ricerca-div affina-ricerca cursor-pointer">
							FILTRI DI RICERCA
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
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${currentAction eq 'elencoProgetti'}">
					<div class="content-elenco-progetti toggler-content-collapsed bordo">
				</c:when>
				<c:otherwise>
					<div class="content-elenco-progetti bordo">
				</c:otherwise>
			</c:choose>
			
				<portlet:actionURL var="affinaRicercaActionVar">
				   	<portlet:param name="action" value="ricerca"></portlet:param>
				</portlet:actionURL>
		
				<aui:form 
					action="${affinaRicercaActionVar}" 
					method="post" 
					name="ricerca-form" 
					id="ricerca-form" 
					cssClass="form-horizontal ricerca-form ricerca-form-elenco">	
				
					<div style="color: #004383; padding: 1em;">
						
						<div style="padding: 1em;">
				           	<span><strong>Inserimento parametri di ricerca <i class='icon-filter'></i></strong></span>
				       	</div>
								
						<aui:input type="hidden" bean="navigaProgetti" name="currentAction" value="${navigaProgetti.currentAction}" />
						<aui:input type="hidden" bean="navigaProgetti" name="naviga" value="${navigaProgetti.naviga}" />
						<aui:input type="hidden" bean="navigaProgetti" name="idNatura" value="${navigaProgetti.idNatura}" />
							
				       	<div>
				       		<div class="span6">
				       		
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Anagrafica CUP</strong>
									<div class="controls">&nbsp;</div>
								</div>

								<div class="control-group no-margin-bottom row-no-wrap" id="cup-div">
									<label class="control-label" for="cup">CUP</label>
									<div class="controls">
										<aui:input type="text" value="${navigaProgetti.cup}" cssClass="ricerca-form-elenco-campo input-xlarge cup" label="" bean="navigaProgetti" name="cup" id="cup"></aui:input>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="descrizione-div">
									<label class="control-label" for="descrizione">Descrizione</label>
									<div class="controls">
										<aui:input type="text" value="${navigaProgetti.descrizione}" cssClass="ricerca-form-elenco-campo input-xlarge descrizione" label="" bean="navigaProgetti" name="descrizione" id="descrizione"></aui:input>
									</div>
								</div>
										
								<div class="control-group no-margin-bottom row-no-wrap" id="statoprogetto-div">
									<label class="control-label" for="statoprogetto">Stato Progetto</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge statoprogetto" label="" bean="navigaProgetti" name="idStatoProgetto" id="statoprogetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idStatoProgetto == -1}"/>
											<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
									            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${navigaProgetti.idStatoProgetto == statoprogetto.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>--%>
									</div>
								</div>

								<div class="control-group no-margin-bottom row-no-wrap" id="anno-div">
									<label class="control-label" for="anno">Anno Decisione</label>
									<div class="controls">
										<aui:select multiple="true" inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge anno" label="" bean="navigaProgetti" name="idAnnoDecisiones" id="anno">
											
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
										<%-- <i class="icon-remove-circle pulisciElementoAnno" style="cursor: pointer;"></i>--%>
									</div>
								</div>
							</div>
							
							<div class="span6">
								
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Classificazione</strong>
									<div class="controls">&nbsp;</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="tipologia-div">
									<label class="control-label" for="tipologia">Tipologia Intervento</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge tipologia" label="" bean="navigaProgetti" name="idTipologiaIntervento" id="tipologia">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idTipologiaIntervento == -1}"/>
											<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
									            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${navigaProgetti.idTipologiaIntervento == tipologiaintervento.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>--%>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="area-intervento-div">
									<label class="control-label" for="area-intervento">Area Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge area-intervento" label="" bean="navigaProgetti" name="idAreaIntervento" id="area-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaIntervento == -1}"/>
											<c:forEach items="${listAreaIntervento}" var="areaIntervento" >
												<aui:option value="${areaIntervento.id}" label="${areaIntervento.descAreaIntervento}" selected="${navigaProgetti.idAreaIntervento == areaIntervento.id}"/>
											</c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoAreaIntervento" style="cursor: pointer;"></i> --%>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="sotto-settore-intervento-div">
									<label class="control-label" for="sotto-settore-intervento">Sotto Settore Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge sotto-settore-intervento" label="" bean="navigaProgetti" name="idSottosettoreIntervento" id="sotto-settore-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idSottosettoreIntervento == -1}"/>
											<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
												<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${navigaProgetti.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
											</c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>--%>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="categoria-intervento-div">
									<label class="control-label" for="categoria-intervento">Categoria Intervento</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge categoria-intervento" label="" bean="navigaProgetti" name="idCategoriaIntervento" id="categoria-intervento">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idCategoriaIntervento == -1}"/>
											<c:forEach items="${listaCategoriaIntervento}" var="categoria" >
												<aui:option value="${categoria.id}" label="${categoria.descCategoriaIntervento}" selected="${navigaProgetti.idCategoriaIntervento == categoria.id}"/>
											</c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoCategoriaIntervento" style="cursor: pointer;"></i>--%>
									</div>
								</div>	
							
							</div>
							
							<div class="clear"></div>
							
						</div>
							
						<div>
						
							<div class="span6">	

								<div class="control-group no-margin-bottom">
									<strong class="control-label">Localizzazione</strong>
									<div class="controls">&nbsp;</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="stato-div">
									<label class="control-label" for="stato">Stato</label>
									<div class="controls">
										<aui:input type="text" value="${navigaProgetti.descStato}" readonly="readonly" cssClass="ricerca-form-elenco-campo input-xlarge stato" label="" bean="navigaProgetti" name="descStato" id="stato"></aui:input>
									</div>
								</div>
										
								<div class="control-group no-margin-bottom row-no-wrap" id="area-geografica-div">
									<label class="control-label" for="areaGeografica">Area Geografica</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge area-geografica" label="" bean="navigaProgetti" name="idAreaGeografica" id="areaGeografica">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaGeografica == -1}"/>
											<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
									            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${navigaProgetti.idAreaGeografica == areaGeografica.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>--%>
									</div>
								</div>
										 
								<div class="control-group no-margin-bottom row-no-wrap" id="regione-div">
									<label class="control-label" for="regione">Regione</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge regione" label="" bean="navigaProgetti" name="idRegione" id="regione">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idRegione == -1}"/>
											<c:forEach items="${listRegione}" var="regione" >
									            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${navigaProgetti.idRegione == regione.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>--%>
									</div>
								</div>
										
								<div class="control-group no-margin-bottom row-no-wrap" id="provincia-div">
									<label class="control-label" for="provincia">Provincia</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge provincia" label="" bean="navigaProgetti" name="idProvincia" id="provincia">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idProvincia == -1}"/>
											<c:forEach items="${listProvincia}" var="provincia" >
									            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${navigaProgetti.idProvincia == provincia.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>--%>
									</div>
								</div>
										
								<div class="control-group no-margin-bottom row-no-wrap" id="comune-div">
									<label class="control-label" for="comune">Comune</label>
									<div class="controls form-inline">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge comune" label="" bean="navigaProgetti" name="idComune" id="comune">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idComune == -1}"/>
											<c:forEach items="${listComune}" var="comune" >
									            <aui:option value="${comune.id}" label="${comune.descComune}" selected="${navigaProgetti.idComune == comune.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoComune" style="cursor: pointer;"></i>--%>
									</div>
								</div>

							</div>
						
							<div class="span6">
								<div class="control-group no-margin-bottom">
									<strong class="control-label">Soggetto</strong>
									<div class="controls">&nbsp;</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="area-soggetto-div">
									<label class="control-label" for="area-soggetto">Area Soggetto</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge area-soggetto" label="" bean="navigaProgetti" name="idAreaSoggetto" id="area-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaSoggetto == -1}"/>
											<c:forEach items="${listAreaSoggetto}" var="areasoggetto" >
									            <aui:option value="${areasoggetto.id}" label="${areasoggetto.descAreaSoggetto}" selected="${navigaProgetti.idAreaSoggetto == areasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoAreaSoggetto" style="cursor: pointer;"></i>--%>
									</div>
								</div>
							
								<div class="control-group no-margin-bottom row-no-wrap" id="categoria-soggetto-div">
									<label class="control-label" for="categoria-soggetto">Categoria Soggetto</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge categoria-soggetto" label="" bean="navigaProgetti" name="idCategoriaSoggetto" id="categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idCategoriaSoggetto == -1}"/>
											<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
									            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${navigaProgetti.idCategoriaSoggetto == categoriasoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>--%>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="sotto-categoria-soggetto-div">
									<label class="control-label" for="sotto-categoria-soggetto">Sotto Categoria Soggetto</label>
									<div class="controls">
										<aui:select inlineField="true" cssClass="ricerca-form-elenco-campo input-xlarge sotto-categoria-soggetto" label="" bean="navigaProgetti" name="idSottoCategoriaSoggetto" id="sotto-categoria-soggetto">
											<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idSottoCategoriaSoggetto == -1}"/>
											<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
									            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottocategSoggetto}" selected="${navigaProgetti.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
									        </c:forEach>
										</aui:select>
										<%-- <i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>--%>
									</div>
								</div>
							
								<div class="control-group no-margin-bottom row-no-wrap" id="soggettoResponsabile-div">
									<label class="control-label" for="soggettoResponsabile">Soggetto Responsabile</label>
									<div class="controls">
										<aui:input type="text" value="${navigaProgetti.soggettoResponsabile}" cssClass="ricerca-form-elenco-campo input-xlarge soggettoResponsabile" label="" bean="navigaProgetti" name="soggettoResponsabile" id="soggettoResponsabile"></aui:input>
									</div>
								</div>
								
								<div class="control-group no-margin-bottom row-no-wrap" id="cfPiSoggettoResponsabile-div">
									<label class="control-label" for="cfPiSoggettoResponsabile">CF/PI Soggetto Resp.</label>
									<div class="controls">
										<aui:input type="text" value="${navigaProgetti.cfPiSoggettoResponsabile}" cssClass="ricerca-form-elenco-campo input-xlarge cfPiSoggettoResponsabile" label="" bean="navigaProgetti" name="cfPiSoggettoResponsabile" id="cfPiSoggettoResponsabile"></aui:input>
									</div>
								</div>
								
							</div>
							
							<div class="clear"></div>
							
						</div>
							
						<div>
							<div class="control-group">
								<div class="pull-right">		
									<aui:button type="submit" id="affina-ricerca-classificazione" cssClass="btn-filtra btn btn-primary btn-filtra" value="Filtra &nbsp;<i class='icon-filter'></i>"></aui:button>
									<aui:button id="affina-ricerca" cssClass="btn btn-rimuovi-filtri" value="Rimuovi Filtri &nbsp;<i class='icon-undo'></i>"></aui:button>
								</div>
							</div>
						</div>
						
					</div>
				</aui:form>
			</div>
		
		</div>
		
	</c:if>	
	
	<c:if test="${valoreRicercaValido eq 'SI'}">
		
		<c:if test="${currentAction ne 'elencoProgetti'}">
			<div class="intestazione">
				<span><strong>Elenco dei progetti <i class="icon-list"></i></strong></span>
			</div>
		</c:if>
		
		<div class="table-container">
			<liferay-ui:search-container searchContainer="${searchContainerElenco}" delta="${searchContainerElenco.delta}" orderByType="${searchContainerElenco.orderByType}" deltaParam="delta">
				
				<liferay-ui:search-container-results results="${searchContainerElenco.results}" total="${searchContainerElenco.total}"/>    
				
				<liferay-ui:search-container-row className="it.dipe.opencup.model.Progetto" keyProperty="id" modelVar="progetti">
			
					<liferay-ui:search-container-column-jsp cssClass="w50" path="/WEB-INF/jsp/progetti/riga-elenco-progetti.jsp" />		
					
					<liferay-ui:search-container-column-text cssClass="w2">
						&nbsp;
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text cssClass="valign-top w23 centra-testo impo" name="aggregato-costo" orderableProperty="impoCostoProgetto" orderable="true">
						<%-- 
						<span class="pull-right colonne-block"><fmt:formatNumber value="${progetti.impoCostoProgetto}" type="currency" minIntegerDigits="1" maxFractionDigits="2" minFractionDigits="2"/></span>
						--%>
						<fmt:formatNumber value="${progetti.impoCostoProgetto}" type="currency" minIntegerDigits="1" maxFractionDigits="0" minFractionDigits="0"/>
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text cssClass="w2">
						&nbsp;
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text cssClass="valign-top w23 centra-testo impo" name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
						<%-- 
						<span class="pull-right colonne-block"><fmt:formatNumber value="${progetti.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  maxFractionDigits="2" minFractionDigits="2"/></span>
						--%>
						<fmt:formatNumber value="${progetti.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  maxFractionDigits="0" minFractionDigits="0"/>
					</liferay-ui:search-container-column-text>
				
				</liferay-ui:search-container-row>
					
				<liferay-ui:search-iterator paginate="${paginate}" searchContainer="${searchContainerElenco}" />
					
			</liferay-ui:search-container>
		</div>
	</c:if>

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
	var filtroExpanded = false;
	
	if(currentAction=='ricercaAvanzata'){
		filtroExpanded = true;
	}

	function nFormatterBar(num){
		if (num >= 1000000000) {
	        return (num / 1000000000).toFixed(1).replace(/\.0$/, '') + ' Mld ';
	     }
	     if (num >= 1000000) {
	        return (num / 1000000).toFixed(1).replace(/\.0$/, '') + ' Mil ';
	     }
	     if (num >= 1000) {
	        return (num / 1).toFixed(0).replace(/\.0$/, '');
	     }
	     return num;

	}
	
	if(currentAction == 'elencoProgetti'){
		
		d3.selectAll("#volumeTotale").text(nFormatterBar("${volumeDeiProgettiProg}"));
		d3.selectAll("#costoTotale").text(nFormatterBar("${costoDeiProgettiProg}")+"\u20ac");
		d3.selectAll("#importoTotale").text(nFormatterBar("${importoFinanziamentiProg}")+"\u20ac");
		
		function drawFinBar(){
			// barra importo finanziato
			var widthLi=d3.selectAll("li.sumImporto")[0][0].clientWidth;
			var range=[${importoFinanziamenti}, ${importoFinanziamentiProg}];
			var x=d3.scale.linear().domain([0, d3.max(range)]).range([0, widthLi]);
			
			d3.selectAll("li.sumImporto")
			.select("div")
			.select("svg")
			.selectAll("rect")
			.data(range)
			.enter()
			.append("rect")
			.attr("width", x)
			.attr("height", ".8em");
		}
		function drawCostoBar(){
			// barra costo previsto
			var widthLi=d3.selectAll("li.sumCosto")[0][0].clientWidth;
			var range=[${costoDeiProgetti}, ${costoDeiProgettiProg}];
			var x=d3.scale.linear().domain([0, d3.max(range)]).range([0, widthLi]);
			
			d3.selectAll("li.sumCosto")
			.select("div")
			.select("svg")
			.selectAll("rect")
			.data(range)
			.enter()
			.append("rect")
			.attr("width", x)
			.attr("height", ".8em");
		}
		function drawVolumeBar(){
			// barra volume
			var widthLi=d3.selectAll("li.sumVolume")[0][0].clientWidth;
			var range=[${volumeDeiProgetti}, ${volumeDeiProgettiProg}];
			var x=d3.scale.linear().domain([0, d3.max(range)]).range([0, widthLi]);
			
			d3.selectAll("li.sumVolume")
			.select("div")
			.select("svg")
			.selectAll("rect")
			.data(range)
			.enter()
			.append("rect")
			.attr("width", x)
			.attr("height", ".8em");
		}
		
		drawVolumeBar();
		drawCostoBar();
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


