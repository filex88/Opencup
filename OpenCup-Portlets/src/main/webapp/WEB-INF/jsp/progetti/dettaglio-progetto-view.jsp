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

<style>
	div.padding-dettaglio{padding:1em;}
	div.stripe{
		background: #fff;
		/*
		border-top:.5em solid #f0f0f0;
		*/
	}
</style>



<c:if test="${not empty dettProgetto}">

	<div class="stripe">
	
		<div class="padding-dettaglio" style="padding-bottom: 0px;">
			<span class="titolo"  style="padding-bottom: 0px;">CUP: ${ dettProgetto.anagraficaCup.codiCup  }</span>
		</div>
		
		<div class="padding-dettaglio" style="padding-top: 0px;">
			<span class="titolo">${ dettProgetto.anagraficaCup.descCup }</span>
		</div>
		
		<div>
			<div class="padding-dettaglio span6">
				<p>
					<span class="dett-label">Soggetto titolere:</span>
					<span class="dett-value">${ dettProgetto.soggettoTitolare.descSoggettoTitolare }</span><br/>
					<span class="dett-label">Anno decisione:</span>
					<span class="dett-value">${ dettProgetto.annoDecisione.annoDadeAnnoDecisione }</span><br/>
					<span class="dett-label">Stato:</span>
					<span class="dett-value">${ dettProgetto.statoProgetto.descDecodStatoProgetto }</span>
				</p>
			</div>
			<div class="padding-dettaglio span6">
				<div>	
					<div class="span6">
						<div style="display: block; text-align: center; font-weight: bold; color: #499652; border-bottom: solid 2px #499652 !important; padding-bottom: 10px; font-size: 1.2em">
							Costo Previsto
						</div>
						<div style="display: block; text-align: center; font-weight: bold; color: #1f4e78; padding-top: 10px; padding-bottom: 15px; font-size: 1.6em;">
							<fmt:formatNumber value="${ dettProgetto.impoCostoProgetto }" type="currency" minIntegerDigits="1" maxFractionDigits="0"  minFractionDigits="0"/>
						</div>
					</div>
					
					<div class="span6">
						<div style="display: block; text-align: center; font-weight: bold; color: #7ade87; border-bottom: solid 2px #7ade87 !important; padding-bottom: 10px; font-size: 1.2em">
							Finanziamento Pubblico Previsto
						</div>
						<div style="display: block; text-align: center; font-weight: bold; color: #1f4e78; padding-top: 10px; padding-bottom: 15px; font-size: 1.6em;">
							<fmt:formatNumber value="${ dettProgetto.impoImportoFinanziato }" type="currency" minIntegerDigits="1" maxFractionDigits="0"  minFractionDigits="0"/>
						</div>
					</div>
					<div class="clear"></div>	
				</div>
			</div>
			<div class="clear"></div>	
		</div>
		
		<div>
			<div class="padding-dettaglio span6">
				<div class="row dett-titolo">
					<span>Localizzazione Progetto</span>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Stato:</span>
					</div>
					<div class="span9 dett-value">
						<span>Italia</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Area Geografica:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.areaGeografica }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Regione:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.regioneProgetto }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Provincia:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.provinceProgetto }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Comune:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.comuniProgetto }</span>
					</div>
				</div>
				
				<div class="row dett-titolo">
					<span>Soggetto Responsabile</span>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Area Soggetto:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.soggettoTitolare.sottocategoriaSoggetto.areaSoggetto.descAreaSoggetto }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Sotto Categoria:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.soggettoTitolare.sottocategoriaSoggetto.descSottocategSoggetto }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Categoria:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.soggettoTitolare.categoriaSoggetto.descCategoriaSoggetto }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Soggetto Titolare:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.soggettoTitolare.descSoggettoTitolare }</span>
					</div>
				</div>
			</div>
			<div class="padding-dettaglio span6">
				<div style="text-align: center">
					<div id="map-canvas"></div>
					<c:choose>
						<c:when test="${ 'S' eq multiLocalizzazione }">Progetto Multi Localizzato</c:when>
					</c:choose>
				</div>
			</div>
			<div class="clear"></div>	
		</div>
		
		<div>
			<div class="padding-dettaglio span6">
				<div class="row dett-titolo">
					<span>Classificazione Progetto</span>
				</div>		
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Natura:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.natura.descNatura }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Tipologia:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.tipologiaIntervento.descTipologiaIntervento }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Area d'intervento:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.areaIntervento.descAreaIntervento }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Settore:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.sottosettoreIntervento.settoreIntervento.descSettoreIntervento }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Sottosettore:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.sottosettoreIntervento.descSottosettoreInt }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Categoria:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.categoriaIntervento.descCategoriaIntervento }</span>
					</div>
				</div>
			</div>
			<div class="padding-dettaglio span6">
				<div class="row dett-titolo">
					<span>Dati Aggiuntivi del Progetto</span>
				</div>
				<fmt:formatDate value="${dettProgetto.anagraficaCup.dataGenerazione}" var="dataGenerazione" pattern="dd/MM/yyyy" />
				<div class="row dettaglio">
					<div class="span6 dett-label">
						<span>Data di generazione:</span>
					</div>
					<div class="span6 dett-value">
						<span>${ dataGenerazione }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span6 dett-label">
						<span>Struttura/Infrastruttura Unica:</span>
					</div>
					<div class="span6 dett-value">
						<span>${ dettProgetto.anagraficaCup.descStrutturaInfrast }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span5 dett-label">
						<span>Strumento di Programmazione:</span>
					</div>
					<div class="span7 dett-value">
						<span>${ dettProgetto.anagraficaCup.descStrumento }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span5 dett-label">
						<span>CUP Master:</span>
					</div>
					<div class="span7 dett-value">
						<span>${ dettProgetto.anagraficaCup.anagraficaCup.codiCup }</span>
					</div>
				</div>
			</div>
			<div class="clear"></div>	
		</div>
		
		<div>
			<div class="padding-dettaglio span6">
				<div class="row dett-titolo">
					<span>Dati di Dettaglio</span>
				</div>		
				<div class="row dettaglio">
					<div class="span6 dett-label">
						<span>Oggetto Progettale:</span>
					</div>
					<div class="span6 dett-value">
						<span>${ dettProgetto.anagraficaCup.descStrutturaInfrast }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span6 dett-label">
						<span>Descrizione Intervento:</span>
					</div>
					<div class="span6 dett-value">
						<span>${ dettProgetto.anagraficaCup.descCup }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span6 dett-label">
						<span>Indirizzo Intervento:</span>
					</div>
					<div class="span6 dett-value">
						<span>${ dettProgetto.anagraficaCup.textIndirizzo }</span>
					</div>
				</div>
				<div class="row dett-titolo">
					<span>Dati CIPE</span>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>N&deg; Delibera CIPE:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.anagraficaCup.numeDeliberaCipe }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Anno Delibera:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.anagraficaCup.annoAnnoDelibera }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Legge Obiettivo:</span>
					</div>
					<div class="span9 dett-value">
						<span>
							<c:choose>
								<c:when test="${ 'S' eq dettProgetto.anagraficaCup.flagLeggeObiettivo }">SI</c:when>
								<c:otherwise>NO</c:otherwise>
							</c:choose>
						</span>
					</div>
				</div>
			</div>
				
			</div>
			<div class="padding-dettaglio span6">
				<div class="row dett-titolo">
					<span>Dati Finanziari</span>
				</div>
							
				<div class="row dettaglio">
					<div class="span8 dett-label">
						<span>Atti di concessione o finanza del Progetto :</span>
					</div>
					<div class="span4 dett-value">
						<span>${ dettProgetto.anagraficaCup.descFinanzaProgetto }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span3 dett-label">
						<span>Sponzorizzazione:</span>
					</div>
					<div class="span9 dett-value">
						<span>${ dettProgetto.anagraficaCup.descSponsorizzazioni }</span>
					</div>
				</div>
				<div class="row dettaglio">
					<div class="span6 dett-label">
						<span>Copertura Finanziaria:</span>
					</div>
					<div class="span6 dett-value">
						<span>${ dettProgetto.anagraficaCup.descTipoCopertura }</span>
					</div>
				</div>
			</div>
			<div class="clear"></div>	
			<div style="margin-top:5em; padding-bottom:15px;">
			<span style="color:#1f4e78!important; padding-left:14px;">Per richiedere approfondimenti o effettuare una segnalazione,<aui:a href="${contattaciUrl}"> CONTATTACI</aui:a></span>
			</div>
		</div>
		

	
	<script src="https://maps.googleapis.com/maps/api/js"></script>

	<script type="text/javascript">
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

	//d3.selectAll("li.current-parent.breadcrumb-truncate").selectAll("i").remove();
	d3.selectAll("li.active.last.breadcrumb-truncate").selectAll("i").remove();
*/	      
	
	/*
	 * MAPPA
	 */
 
 	var lat = 0;
 	var lng = 0;
 	var address = "${addressMap}"; //{zipcode} or {city and state};
 	var zoomMap = ${zoomMap};
	
	function initialize() {
		var element = document.getElementById('map-canvas');
        var mapTypeIds = [];
/*         for(var type in google.maps.MapTypeId) {
            mapTypeIds.push(google.maps.MapTypeId[type]);
        } */
        mapTypeIds.push("OSM");

        var map = new google.maps.Map(element, {
            center: new google.maps.LatLng(lat, lng),
            zoom: 11,
            mapTypeId: "OSM",
            mapTypeControlOptions: {
                mapTypeIds: mapTypeIds
            } 
        });

        map.mapTypes.set("OSM", new google.maps.ImageMapType({
            getTileUrl: function(coord, zoom) {
                return "http://tile.openstreetmap.org/" + zoom + "/" + coord.x + "/" + coord.y + ".png";
            },
            tileSize: new google.maps.Size(256, 256),
            name: "Mappa",
            maxZoom: 18
        }));
		
		/* var mapOptions = {
			center: new google.maps.LatLng(lat, lng),
         	disableDefaultUI: true,
         	disableDoubleClickZoom: false,
         	draggable: true,
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
     			}); */
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
/*
 	AUI().use(
			'aui-base', 
			function( A ) {
				var dataset = [{
	        		data: [{
			            	etichetta: 'Costo Progetto',
			            	valore: '${ impoCostoProgetto }',
			            	valoreformattato: '<fmt:formatNumber value="${ impoCostoProgetto }" type="currency" minIntegerDigits="1" maxFractionDigits="2" minFractionDigits="2"/>'
			        	}, {
			            	etichetta: 'Importo Finanziato',
			            	valore: '${ impoImportoFinanziato }',
			            	valoreformattato: '<fmt:formatNumber value="${ impoImportoFinanziato }" type="currency" minIntegerDigits="1" maxFractionDigits="2" minFractionDigits="2"/>'
			        	}],
			        name: 'Importi'
			    }];
				
				var widthChartContent  = d3.select('.chart').node().getBoundingClientRect().width;
				
				var margins = {
					    top: 5,
					    left: 5,
					    right: 0,
					    bottom: 5
					},
					    
					legendPanel = {
					    width: 0
					},
					    
					width = widthChartContent - margins.left - margins.right - legendPanel.width,
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
									        	
							        			
									    		
									    		//var xPos = parseFloat(d3.select(this).attr('x')) / 2 + width / 2;
									    		//var yPos = parseFloat(d3.select(this).attr('y')) + yScale.rangeBand() / 2;
									    		
									    		
									    		//var mouse = d3.mouse(d3.select(".portlet-body").node()).map( function(d) { return parseInt(d); } );
									    		//
									    		//var xPos = (mouse[0]+10);
									    		//var yPos = (mouse[1]-40);
									    		//
									    		//d3.select('#tooltip')
									    		//	.style('left', xPos + 'px')
									    		//	.style('top', yPos + 'px')
									    		//	.select('#value')
									    		//	.text(d.z)
									    		//	.select('#label')
									    		//	.text(d.x);
							        })
								    .on('mouseover', 
								    		function (d) {
								    			//d3.select('#tooltip').transition().duration(500).style("opacity", 100); 
								    			//d3.select('#tooltip').classed('hidden', false);
									    })
									.on('mouseout', 
											function () {
												//d3.select('#tooltip').transition().duration(500).style("opacity", 0); 
										        //d3.select('#tooltip').classed('hidden', true);
										    });
					
					svg.append('g')
					        .attr('class', 'axis')
					        .attr('transform', 'translate(0,' + height + ')')
					        .call(xAxis);
					
					svg.append('g')
					        .attr('class', 'axis')
					        .call(yAxis);
			});
*/

	</script>
	
</c:if>
