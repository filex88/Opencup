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

<portlet:renderURL var="uriDialogRicerca" windowState="<%=LiferayWindowState.EXCLUSIVE.toString()%>">
	<portlet:param name="action" value="affinaricerca"/>
</portlet:renderURL>

<script type="text/javascript">
	var uriDialogRicerca = "${uriDialogRicerca}";
</script>

<div class="span12">
		
	<div class="span9">
	
		<c:if test="${ modelAttrNaturaRicerca.descNatura != null }">
			<ul class="inline">
				<li>Naviga per</li>
				<li><span class="label label-info riepilogo-filtri">Natura: ${ modelAttrNaturaRicerca.descNatura }
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idNatura" data-parent2="idSettoreIntervento" data-parent3="idSottosettoreIntervento" data-parent4="idCategoriaIntervento" ></i>
				</span></li>				
		</c:if>
		<c:if test="${ modelAttrNaturaRicerca.descSettoreIntervento != null }">																		
			<li><span class="label label-info riepilogo-filtri">Settore Intervento: ${ modelAttrNaturaRicerca.descSettoreIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idSettoreIntervento" data-parent2="idSottosettoreIntervento" data-parent3="idCategoriaIntervento" ></i>
			</span></li>
		</c:if>
		<c:if test="${ modelAttrNaturaRicerca.descSottosettoreIntervento != null }">	
			<li><span class="label label-info riepilogo-filtri">Sottosettore Intervento: ${ modelAttrNaturaRicerca.descSottosettoreIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idSottosettoreIntervento" data-parent2="idCategoriaIntervento"  ></i>
			</span></li>
		</c:if>
		<c:if test="${ modelAttrNaturaRicerca.descCategoriaIntervento != null }">																								
			<li><span class="label label-info riepilogo-filtri">Categoria Intervento: ${ modelAttrNaturaRicerca.descCategoriaIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idCategoriaIntervento" ></i>
			</span></li>
		</c:if>
		<c:if test="${ modelAttrNaturaRicerca.descNatura != null }">
			</ul>
		</c:if>
		<%--
		<c:if test="${ modelAttrNaturaRicerca.descAnnoDecisiones != null }">		
			<ul class="inline">
				<li>Anni</li>
				<li><span class="label label-info riepilogo-filtri">${ modelAttrNaturaRicerca.descAnnoDecisiones } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idAnnoDecisiones" ></i></span></li>
			</ul>
		</c:if>
		
		<c:if test="${ modelAttrNaturaRicerca.descAreaGeografica != null }">
			<ul class="inline">
				<li>Localizzazione</li>
				<li><span class="label label-info riepilogo-filtri">Area Geografica: ${ modelAttrNaturaRicerca.descAreaGeografica } 
					&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idAreaGeografica" data-parent2="idRegione" data-parent3="idProvincia" data-parent4="idComune" ></i>
				</span></li>
		</c:if>
		<c:if test="${ modelAttrNaturaRicerca.descRegione != null }">																		
			<li><span class="label label-info riepilogo-filtri">Regione: ${ modelAttrNaturaRicerca.descRegione } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idRegione" data-parent2="idProvincia" data-parent3="idComune" ></i>
			</span></li>
		</c:if>																	
		<c:if test="${ modelAttrNaturaRicerca.descProvincia != null }">																		
			<li><span class="label label-info riepilogo-filtri">Provincia: ${ modelAttrNaturaRicerca.descProvincia } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idProvincia" data-parent2="idComune" ></i>
			</span></li>
		</c:if>																				
		<c:if test="${ modelAttrNaturaRicerca.descComune != null }">																		
			<li><span class="label label-info riepilogo-filtri">Comune: ${ modelAttrNaturaRicerca.descComune } 
			&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idComune" ></i>
			</span></li>
		</c:if>			
		<c:if test="${ modelAttrNaturaRicerca.descAreaGeografica != null }">
			</ul>
		</c:if>
		
		<c:if test="${ modelAttrNaturaRicerca.descCategoriaSoggetto != null }">
			<ul class="inline">
				<li>Soggetto</li>
				<li><span class="label label-info riepilogo-filtri">Categoria: ${ modelAttrNaturaRicerca.descCategoriaSoggetto } 
					&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idCategoriaSoggetto" data-parent2="idSottoCategoriaSoggetto" ></i>
				</span></li>
		</c:if>
		<c:if test="${ modelAttrNaturaRicerca.descSottoCategoriaSoggetto != null }">																		
			<li><span class="label label-info riepilogo-filtri">Sottocategoria: ${ modelAttrNaturaRicerca.descSottoCategoriaSoggetto } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idSottoCategoriaSoggetto" ></i>
			</span></li>
		</c:if>	
		<c:if test="${ modelAttrNaturaRicerca.descCategoriaSoggetto != null }">
			</ul>
		</c:if>
	
		<c:if test="${ modelAttrNaturaRicerca.descTipologiaIntervento != null }">
			<ul class="inline">
				<li>Tipologia Intervento</li>
				<li><span class="label label-info riepilogo-filtri">${ modelAttrNaturaRicerca.descTipologiaIntervento } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idTipologiaIntervento" ></i>
				</span></li>
			</ul>
		</c:if>
		
		<c:if test="${ modelAttrNaturaRicerca.descStatoProgetto != null }">
			<ul class="inline">
				<li>Stato Progetto</li>
				<li><span class="label label-info riepilogo-filtri">${ modelAttrNaturaRicerca.descStatoProgetto } 
				&nbsp;<i class="icon-remove-circle remove-circle-filtri pulisci icon-2x" data-parent1="idStatoProgetto" ></i>
				</span></li>
			</ul>
		</c:if>

		<portlet:actionURL var="affinaRicercaActionVar">
		   	<portlet:param name="action" value="ricerca"></portlet:param>
		</portlet:actionURL>
		--%>
		<aui:form 
			action="${affinaRicercaActionVar}" 
			method="post" 
			name="filtri-form" 
			id="filtri-form"
			cssClass="filtri-form">
			
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="naviga" value="${modelAttrNaturaRicerca.naviga}" />
				
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idNatura" value="${modelAttrNaturaRicerca.idNatura}" id="idNatura" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idSettoreIntervento" value="${modelAttrNaturaRicerca.idSettoreIntervento}" id="idSettoreIntervento" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idSottosettoreIntervento" value="${modelAttrNaturaRicerca.idSottosettoreIntervento}" id="idSottosettoreIntervento" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idCategoriaIntervento" value="${modelAttrNaturaRicerca.idCategoriaIntervento}" id="idCategoriaIntervento" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idAnnoDecisiones" value="${modelAttrNaturaRicerca.idAnnoDecisiones}" id="idAnnoDecisiones" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idRegione" value="${modelAttrNaturaRicerca.idRegione}" id="idRegione" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idProvincia" value="${modelAttrNaturaRicerca.idProvincia}" id="idProvincia" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idComune" value="${modelAttrNaturaRicerca.idComune}" id="idComune" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idAreaGeografica" value="${modelAttrNaturaRicerca.idAreaGeografica}" id="idAreaGeografica" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idCategoriaSoggetto" value="${modelAttrNaturaRicerca.idCategoriaSoggetto}" id="idCategoriaSoggetto" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idSottoCategoriaSoggetto" value="${modelAttrNaturaRicerca.idSottoCategoriaSoggetto}" id="idSottoCategoriaSoggetto" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idTipologiaIntervento" value="${modelAttrNaturaRicerca.idTipologiaIntervento}" id="idTipologiaIntervento" />
				<aui:input type="hidden" bean="modelAttrNaturaRicerca" name="idStatoProgetto" value="${modelAttrNaturaRicerca.idStatoProgetto}" id="idStatoProgetto" />
			
		</aui:form>
			
	</div>
	
	<div class="span2">
	
			

		<div style="text-align: right;">
		
			<c:choose>
				<c:when test="${modelAttrNaturaRicerca.filtroClassificazione}">
					<aui:button id="affina-ricerca-natura" cssClass="affina-ricerca-natura btn-primary" value="AFFINA LA RICERCA <i class='icon-filter'><span class='icon-stack'><i class='icon-circle icon-stack-base'></i><i class='icon-light'>6</i></span></i>"></aui:button>
				</c:when>
				<c:otherwise>
					<aui:button id="affina-ricerca-natura" cssClass="affina-ricerca-natura" value="AFFINA LA RICERCA <i class='icon-filter'></i>"></aui:button>
				</c:otherwise>
			</c:choose>
		
			<span class="utente">
				<i class="icon-bell-alt"> 
					<span class="icon-stack" id="notifBadge">
						<i class="icon-circle icon-stack-base" ></i>
			          	<i class="icon-light">${modelAttrNaturaRicerca.countAffRicerca4Natura}</i>
					</span> 
				</i>
		 	</span>
		 
		</div>
		
	</div>
	
</div>

<script type="text/javascript">
	
	var namespaceRicerca4js = "<portlet:namespace/>";

	AUI().use(	
		'aui-base',
		function(A) {
			
			
			A.all('.pulisci').each(
					function() {
						this.on('click', function(){
							for (i = 0; i < 5; i++) { 
								var dataParent = this.getAttribute("data-parent"+i);
								if( dataParent ){
									var target = "#"+namespaceRicerca4js+dataParent;
							    	var elemento = A.one(target);
									elemento.val(-1);
								}
							}
					    	submitForm();
						});
					});

			function submitForm(){

				//Rimuovo le parentesi quadre dalla lista degli id degli anni
				var target = "#"+namespaceRicerca4js+"idAnnoDecisiones";
				var elemento = A.one(target);
				var nuovoVal = elemento.val().replace("[","").replace("]","");
				elemento.val(nuovoVal);
				
				// submit
				var form = A.one(".filtri-form");
				form.submit();
			}	
			
	});
	
	
	
</script>
		