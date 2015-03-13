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

<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
 -- RIEPILOGO --		
 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
<div>

	<div class="span6">
	
		<fmt:setLocale value="it_IT"/>
		
		<liferay-ui:search-container searchContainer="${searchContainerRiepilogo}" delta="${searchContainerRiepilogo.delta}" deltaParam="delta">
			
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
	
	</div>
	
</div>

<div>
		
	<div class="span8">

		<liferay-ui:search-container searchContainer="${searchContainerElenco}" delta="${searchContainerElenco.delta}" orderByType="${searchContainerElenco.orderByType}" deltaParam="delta">
		
			<liferay-ui:search-container-results results="${searchContainerElenco.results}" total="${searchContainerElenco.total}"/>    
			
			<liferay-ui:search-container-row className="it.dipe.opencup.model.Progetti" keyProperty="id" modelVar="progetti">
				
				<liferay-ui:search-container-column-jsp name="aggregato-des" align="right" path="/WEB-INF/jsp/progetti/riga-elenco-progetti.jsp" />		
				
				<liferay-ui:search-container-column-text name="aggregato-costo" orderableProperty="impoCostoProgetto" orderable="true">
					<span class="pull-right"><fmt:formatNumber value="${progetti.impoCostoProgetto}" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
				</liferay-ui:search-container-column-text>
				
				<liferay-ui:search-container-column-text name="aggregato-importo" orderableProperty="impoImportoFinanziato" orderable="true">
					<span class="pull-right"><fmt:formatNumber value="${progetti.impoImportoFinanziato}" type="currency" minIntegerDigits="1"  minFractionDigits="3"/></span>
				</liferay-ui:search-container-column-text>
		
			</liferay-ui:search-container-row>
		
			<liferay-ui:search-iterator searchContainer="${searchContainerElenco}" />
		
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
			cssClass="form-horizontal ricerca-form">	
		
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
					
					<div class="control-group no-margin-bottom" id="area-intervento-div">
						<label class="control-label" for="area-intervento">Area Intervento</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large area-intervento" label="" bean="navigaProgetti" name="idAreaIntervento" id="area-intervento">
								<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaIntervento == -1}"/>
								<c:forEach items="${listAreaIntervento}" var="areaIntervento" >
									<aui:option value="${areaIntervento.id}" label="${areaIntervento.descAreaIntervento}" selected="${navigaProgetti.idAreaIntervento == areaIntervento.id}"/>
								</c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoAreaIntervento" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="sotto-settore-intervento-div">
						<label class="control-label" for="sotto-settore-intervento">Sotto Settore Intervento</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large sotto-settore-intervento" label="" bean="navigaProgetti" name="idSottosettoreIntervento" id="sotto-settore-intervento">
								<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idSottosettoreIntervento == -1}"/>
								<c:forEach items="${listSottosettoreIntervento}" var="sottosettoreIntervento" >
									<aui:option value="${sottosettoreIntervento.id}" label="${sottosettoreIntervento.descSottosettoreInt}" selected="${navigaProgetti.idSottosettoreIntervento == sottosettoreIntervento.id}"/>
								</c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoSottosettoreIntervento" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="categoria-intervento-div">
						<label class="control-label" for="categoria-intervento">Categoria</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large categoria-intervento" label="" bean="navigaProgetti" name="idCategoriaIntervento" id="categoria-intervento">
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
		
			<div class="control-group no-margin-bottom" id="categoria-soggetto-div">
				<label class="control-label" for="categoria-soggetto">Categoria</label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large categoria-soggetto" label="" bean="navigaProgetti" name="idCategoriaSoggetto" id="categoria-soggetto">
						<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idCategoriaSoggetto == -1}"/>
						<c:forEach items="${listCategoriaSoggetto}" var="categoriasoggetto" >
				            <aui:option value="${categoriasoggetto.id}" label="${categoriasoggetto.descCategoriaSoggetto}" selected="${navigaProgetti.idCategoriaSoggetto == categoriasoggetto.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoCategoriaSoggetto" style="cursor: pointer;"></i>
				</div>
			</div>
			
			<div class="control-group no-margin-bottom" id="sotto-categoria-soggetto-div">
				<label class="control-label" for="sotto-categoria-soggetto">Sotto Categoria</label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large sotto-categoria-soggetto" label="" bean="navigaProgetti" name="idSottoCategoriaSoggetto" id="sotto-categoria-soggetto">
						<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idSottoCategoriaSoggetto == -1}"/>
						<c:forEach items="${listSottoCategoriaSoggetto}" var="sottoCategoriaSoggetto" >
				            <aui:option value="${sottoCategoriaSoggetto.id}" label="${sottoCategoriaSoggetto.descSottocategSoggetto}" selected="${navigaProgetti.idSottoCategoriaSoggetto == sottoCategoriaSoggetto.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoSottoCategoriaSoggetto" style="cursor: pointer;"></i>
				</div>
			</div>
			
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
			
					<div class="control-group no-margin-bottom" id="stato-div">
						<label class="control-label" for="stato">Stato</label>
						<div class="controls">
							<aui:input type="text" value="${navigaProgetti.descStato}" readonly="readonly" cssClass="input-large stato" label="" bean="navigaProgetti" name="descStato" id="stato"></aui:input>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="area-geografica-div">
						<label class="control-label" for="areaGeografica">Area Geografica</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large area-geografica" label="" bean="navigaProgetti" name="idAreaGeografica" id="areaGeografica">
								<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idAreaGeografica == -1}"/>
								<c:forEach items="${listAreaGeografica}" var="areaGeografica" >
						            <aui:option value="${areaGeografica.id}" label="${areaGeografica.descAreaGeografica}" selected="${navigaProgetti.idAreaGeografica == areaGeografica.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoAreaGeografica" style="cursor: pointer;"></i>
						</div>
					</div>
					 
					<div class="control-group no-margin-bottom" id="regione-div">
						<label class="control-label" for="regione">Regione</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large regione" label="" bean="navigaProgetti" name="idRegione" id="regione">
								<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idRegione == -1}"/>
								<c:forEach items="${listRegione}" var="regione" >
						            <aui:option value="${regione.id}" label="${regione.descRegione}" selected="${navigaProgetti.idRegione == regione.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoRegione" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="provincia-div">
						<label class="control-label" for="provincia">Provincia</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large provincia" label="" bean="navigaProgetti" name="idProvincia" id="provincia">
								<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idProvincia == -1}"/>
								<c:forEach items="${listProvincia}" var="provincia" >
						            <aui:option value="${provincia.id}" label="${provincia.descProvincia}" selected="${navigaProgetti.idProvincia == provincia.id}"/>
						        </c:forEach>
							</aui:select>
							<i class="icon-remove-circle pulisciElementoProvincia" style="cursor: pointer;"></i>
						</div>
					</div>
					
					<div class="control-group no-margin-bottom" id="comune-div">
						<label class="control-label" for="comune">Comune</label>
						<div class="controls form-inline">
							<aui:select inlineField="true" cssClass="input-large comune" label="" bean="navigaProgetti" name="idComune" id="comune">
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
			
			<div class="control-group no-margin-bottom" id="anno-div">
				<label class="control-label" for="anno"><strong>Anno Decisione</strong></label>
				<div class="controls">
					<aui:select multiple="true" inlineField="true" cssClass="input-large anno" label="" bean="navigaProgetti" name="idAnnoDecisiones" id="anno">
						
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
			
			<div class="control-group no-margin-bottom" id="tipologia-div">
				<label class="control-label" for="tipologia"><strong>Tipologia Intervento</strong></label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large tipologia" label="" bean="navigaProgetti" name="idTipologiaIntervento" id="tipologia">
						<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idTipologiaIntervento == -1}"/>
						<c:forEach items="${listaTipologiaIntervento}" var="tipologiaintervento" >
				            <aui:option value="${tipologiaintervento.id}" label="${tipologiaintervento.descTipologiaIntervento}" selected="${navigaProgetti.idTipologiaIntervento == tipologiaintervento.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoTipologia" style="cursor: pointer;"></i>
				</div>
			</div>

			<div class="control-group no-margin-bottom" id="statoprogetto-div">
				<label class="control-label" for="statoprogetto"><strong>Stato Progetto</strong></label>
				<div class="controls">
					<aui:select inlineField="true" cssClass="input-large statoprogetto" label="" bean="navigaProgetti" name="idStatoProgetto" id="statoprogetto">
						<aui:option value="-1" label="ricerca.tutte" selected="${navigaProgetti.idStatoProgetto == -1}"/>
						<c:forEach items="${listaStatoProgetto}" var="statoprogetto" >
				            <aui:option value="${statoprogetto.id}" label="${statoprogetto.descStatoProgetto}" selected="${navigaProgetti.idStatoProgetto == statoprogetto.id}"/>
				        </c:forEach>
					</aui:select>
					<i class="icon-remove-circle pulisciElementoStatoprogetto" style="cursor: pointer;"></i>
				</div>
			</div>

			<div style="text-align: center">			
				<aui:button type="submit" id="affina-ricerca-classificazione" cssClass="btn btn-primary btn-filtra" value="Filtra &nbsp;<i class='icon-filter'></i>"></aui:button>
				<aui:button id="affina-ricerca-classificazione" cssClass="btn btn-rimuovi-filtri" value="Rimuovi Filtri &nbsp;<i class='icon-undo'></i>"></aui:button>
			</div>
			
		</aui:form>
		
		
	</div>
	
</div>


<script type="text/javascript">

	var namespaceRicerca4js = "<portlet:namespace/>";
	
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
</script>


