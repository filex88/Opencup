
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />
<fmt:setLocale value="it_IT"/>

<c:if test="${not empty dettProgetto}">
	<fmt:formatDate value="${dettProgetto.anagraficaCup.dataGenerazione}" var="dataGenerazione" pattern="dd/MM/yyyy" />
	<div>
		<div class="span8">
			<div>
				<p>
					<span class="dett-titolo">${ dettProgetto.soggettoTitolare.descSoggettoTitolare }</span>
				</p>
			</div>
			<div>
				<p>
					<span class="dett-label">CUP:</span>
					<span class="dett-value">${  dettProgetto.anagraficaCup.codiCup  }</span>
					<span class="dett-label">generato il:</span>
					<span class="dett-value">${ dataGenerazione }</span>
					<span class="dett-label">Anno decisione:</span>
					<span class="dett-value">${ dettProgetto.annoDecisione.annoDadeAnnoDecisione }</span>
				</p>
			</div>
		</div>
	</div>	
	<div>
		<div class="span6">
		
			<p>
				<span class="dett-titolo">DATI CLASSIFICAZIONE PROGETTO</span>
			</p>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Natura:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.natura.descNatura }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Tipo:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.tipologiaIntervento.descTipologiaIntervento }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Area:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.areaIntervento.descAreaIntervento }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Sotto-settore:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.sottosettoreIntervento.descSottosettoreInt }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Categoria:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.categoriaIntervento.descCategoriaIntervento }</span>
				</div>
			</div>
			
			<p>
				<span class="dett-titolo">DATI LOCALIZZAZIONE DEL PROGETTO</span>
			</p>
						
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Comune:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.comuniProgetto }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Provincia:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.provinceProgetto }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Regione:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.regioneProgetto }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Stato:</span>
				</div>
				<div class="span8">
					<span class="dett-value">Italia</span>
				</div>
			</div>
			
			<p>
				<span class="dett-titolo">DATI SOGGETTO TITOLARE</span>
			</p>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Soggetto Titolare:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.soggettoTitolare.descSoggettoTitolare }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Area:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.soggettoTitolare.categoriaSoggetto.areaSoggetto.descAreaSoggetto }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Categoria:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.soggettoTitolare.categoriaSoggetto.descCategoriaSoggetto }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Sotto-categoria:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.soggettoTitolare.sottocategoriaSoggetto.descSottocategSoggetto }</span>
				</div>
			</div>
			
		</div>
		
		<div class="span5 offset1">
			<p>
				<span class="dett-titolo">COSTI E IMPORTI</span>
			</p>
			
			<div class="row importo">
				<div class="span3 offset1">
					<span class="dett-label">Costo Progetto:</span>
				</div>
				<div class="span3">
					<span class="dett-value pull-right"><fmt:formatNumber value="${ dettProgetto.impoCostoProgetto }" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
				</div>
			</div>
			<div class="row importo">
				<div class="span3 offset1">
					<span class="dett-label">Importo Finanziato:</span>
				</div>
				<div class="span3">
					<span class="dett-value pull-right"><fmt:formatNumber value="${ dettProgetto.impoImportoFinanziato }" type="currency" minIntegerDigits="1" minFractionDigits="3"/></span>
				</div>
			</div>
			
			<div>
				<div class="span5 copertura-finanziamento">
					<p>
						<span class="dett-titolo2">Copertura Finanziamento</span>
					</p>
					<h2><fmt:formatNumber value="${coperturaPercentuale}" type="number" minIntegerDigits="1"/>%</h2>
				</div>
				<div class="span7 chart-fianziamento">
					<div id="tooltip" class="tooltip hidden">
						<p><span id="label"></span></p>
						<p><span id="value"></span></p>
					</div>
					<svg class="chart"></svg>
				</div>
			</div>
			
			<div class="row" style="text-align: center">
				<div id="map-canvas"></div>
				<c:choose>
					<c:when test="${ 'S' eq multiLocalizzazione }">Progetto Multi-localizzato</c:when>
				</c:choose>
			</div>
			
			<fmt:formatDate value="${ maxDataModifica }" var="dataUltimaModifica" pattern="dd/MM/yyyy" />
			<p>
				<span class="dett-titolo">DATI AGGIUDICATIVI DEL PROGETTO</span>
			</p>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Data Ultima Modifica:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dataUltimaModifica }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">CUP Master:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.anagraficaCup.anagraficaCup.codiCup }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Legge Obiettivo:</span>
				</div>
				<div class="span8">
					<span class="dett-value">
						<c:choose>
							<c:when test="${ 'S' eq dettProgetto.anagraficaCup.flagLeggeObiettivo }">SI</c:when>
							<c:otherwise>NO</c:otherwise>
						</c:choose>
					</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Numero e Anno Delibera CIPE:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.anagraficaCup.numeDeliberaCipe } del ${ dettProgetto.anagraficaCup.annoAnnoDelibera }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Soggetto Richiedente:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.anagraficaCup.descSoggettoRichiedente }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Stato Finanza di Progetto:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.anagraficaCup.descFinanzaProgetto }</span>
				</div>
			</div>
			<div class="row dettaglio">
				<div class="span3 offset1">
					<span class="dett-label">Tipo Copertura Finanziaria:</span>
				</div>
				<div class="span8">
					<span class="dett-value">${ dettProgetto.anagraficaCup.descTipoCup }</span>
				</div>
			</div>
			
		</div>
		
	</div>

	<script src="https://maps.googleapis.com/maps/api/js"></script>

	<script type="text/javascript">
	      
/*
 * MAPPA
 */
 
 	var lat = 0;
 	var lng = 0;
 	var address = '${addressMap}';//{zipcode} or {city and state};
 	var zoomMap = ${zoomMap};
	
	function initialize() {
		var mapCanvas = document.getElementById('map-canvas');
		var mapOptions = {
			center: new google.maps.LatLng(lat, lng),
         	disableDefaultUI: true,
         	disableDoubleClickZoom: true,
         	draggable: false,
         	keyboardShortcuts: false,
         	zoom: zoomMap,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		
		var map = new google.maps.Map(mapCanvas, mapOptions);
     	
		var marker = new google.maps.Marker({
        										map: map,
                                   				position: map.getCenter()
                                       		});
     	var infowindow = new google.maps.InfoWindow();
     	infowindow.setContent('<b>'+address+'</b>');
     	google.maps.event.addListener(marker, 'click', 
     			function() {
         			infowindow.open(map, marker);
     			});
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
	var geocoder = new google.maps.Geocoder();
	
	geocoder.geocode({'address': address}, 
				    function(results, status) {
				        if (status == google.maps.GeocoderStatus.OK) {
						    lat = results[0].geometry.location.lat();
						    lng = results[0].geometry.location.lng();
							initialize();
                     	} else {
							alert("Geocode was not successful for the following reason: " + status);
						}
					});
 
/*
 * FINE MAPPA
 */
		AUI().use(
			'aui-base', 
			function( A ) {
				var dataset = [{
	        		data: [{
			            	etichetta: 'Costo Progetto',
			            	valore: '${ impoCostoProgetto }',
			            	valoreformattato: '<fmt:formatNumber value="${ impoCostoProgetto }" type="currency" minIntegerDigits="1" minFractionDigits="3"/>'
			        	}, {
			            	etichetta: 'Importo Finanziato',
			            	valore: '${ impoImportoFinanziato }',
			            	valoreformattato: '<fmt:formatNumber value="${ impoImportoFinanziato }" type="currency" minIntegerDigits="1" minFractionDigits="3"/>'
			        	}],
			        name: 'Importi'
			    }];
				
				var margins = {
					    top: 5,
					    left: 120,
					    right: 5,
					    bottom: 5
					},
					    
					legendPanel = {
					    width: 0
					},
					    
					width = 500 - margins.left - margins.right - legendPanel.width,
					height = 100 - margins.top - margins.bottom,
					
					series = dataset.map(
								function (d) {
									return d.name;
								}),
					
					dataset = dataset.map(
								function (d) {
									return d.data.map(
										function (o, i) {
											// Structure it so that your numeric
											// axis (the stacked amount) is y
											return {
								                y: o.valore,
								                x: o.etichetta,
								                z: o.valoreformattato
											};
										});
								}),
					
					stack = d3.layout.stack();

					stack(dataset);

					var dataset = dataset.map(
								function (group) {
									return group.map(
										function (d) {
											// Invert the x and y values, and y0 becomes x0
											return {
												x: d.y,
								                y: d.x,
								                x0: d.y0,
								                z: d.z
											};
										});
								}),
						
						svg = d3.select('.chart')
								        .append('svg')
								        .attr('width', width + margins.left + margins.right + legendPanel.width)
								        .attr('height', height + margins.top + margins.bottom)
								        .append('g')
								        .attr('transform', 'translate(' + margins.left + ',' + margins.top + ')'),
					    
						xMax = d3.max(dataset, 
									function (group) {
					        			return d3.max(group, 
					        				function (d) {
					        					return d.x + d.x0;
					        				});
					    			}),
					    
					    xScale = d3.scale.linear()
							        .domain([0, xMax])
							        .range([0, width]),
					    
					    importi = dataset[0].map(
					    			function (d) {
					    				return d.y;
					    			}),
					    
					    yScale = d3.scale.ordinal()
							        .domain(importi)
							        .rangeRoundBands([0, height], .1),
					    
					    xAxis = d3.svg.axis()
							        .scale(xScale)
							        .orient('bottom'),
					    
					    yAxis = d3.svg.axis()
							        .scale(yScale)
							        .orient('left'),
					    
					    colours = d3.scale.category10(),
					    
					    groups = svg.selectAll('g')
							        .data(dataset)
							        .enter()
							        .append('g')
							        .style('fill', 
						        		function (d, i) {
						        			return colours(i);
						        		}),
					    
					    rects = groups.selectAll('rect')
							        .data(
						        		function (d) {
						        			return d;
						        		})
							        .enter()
							        .append('rect')
							        .attr('x', 
							        	function (d) {
							            	return xScale(d.x0);
							        	})
							        .attr('y', 
						        		function (d, i) {
						            		return yScale(d.y);
						        		})
							        .attr('height', 
							        	function (d) {
							            	return yScale.rangeBand();
							        	})
							        .attr('width', 
							        	function (d) {
							        		return xScale(d.x);
							        	})
							        .on('mousemove',
							        		function (d){
									        	var mouse = d3.mouse(d3.select(".portlet-body").node()).map( function(d) { return parseInt(d); } );
									    		
									    		/*
									    		var xPos = parseFloat(d3.select(this).attr('x')) / 2 + width / 2;
									    		var yPos = parseFloat(d3.select(this).attr('y')) + yScale.rangeBand() / 2;
									    		*/
									    		var xPos = (mouse[0]+10);
									    		var yPos = (mouse[1]-40);
									    		
									    		d3.select('#tooltip')
									    			.style('left', xPos + 'px')
									    			.style('top', yPos + 'px')
									    			.select('#value')
									    			.text(d.z)
									    			.select('#label')
									    			.text(d.x);
							        })
								    .on('mouseover', 
								    		function (d) {
										        d3.select('#tooltip').classed('hidden', false);
									    })
									.on('mouseout', 
											function () {
										        d3.select('#tooltip').classed('hidden', true);
										    });
					
					svg.append('g')
					        .attr('class', 'axis')
					        .attr('transform', 'translate(0,' + height + ')')
					        .call(xAxis);
					
					svg.append('g')
					        .attr('class', 'axis')
					        .call(yAxis);
			});
		
	</script>
	
</c:if>
