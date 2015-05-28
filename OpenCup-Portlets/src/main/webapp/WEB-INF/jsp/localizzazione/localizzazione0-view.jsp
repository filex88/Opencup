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
	<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center; background: #f0f0f0;">
		<div class="offset3 span2">
			<div class="btn-carica-distribuzione volume-color sel-type-btn" data-distribuzione="VOLUME">
				<aui:a href="#" onClick="return false" cssClass="block">
					PROGETTI
				</aui:a>
			</div>
			<c:if test='${pattern eq "VOLUME"}'>
				<div class="arrow-down-volume"></div>
			</c:if>
		</div>
		<div class="span2">	
			<div class="btn-carica-distribuzione costo-color sel-type-btn" data-distribuzione="COSTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					COSTO
				</aui:a>
			</div>
			<c:if test='${pattern eq "COSTO"}'>
				<div class="arrow-down-costo"></div>
			</c:if>
		</div>
		<div class="span2">	
			<div class="btn-carica-distribuzione importo-color sel-type-btn" data-distribuzione="IMPORTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					IMPORTO
				</aui:a>
			</div>
			<c:if test='${pattern eq "IMPORTO"}'>
				<div class="arrow-down-importo"></div>
			</c:if>
		</div>
		<div class="clear"></div>
	
	</div>	
		
	<div id="container-localizzazione" style="height: 400px;">
		
		<div class="row">
			<div class="span6" style="height: 4em;">
				<div class="titoloLocalizzazione" id="titoloLocalizzazione">
					Localizzazione
				</div>
			</div>
			<div class="span3 offset2" style="height: 4em;">
				<div class="row">
					<div class="span6 indicatoreNavigaLocalizzazioneLabel">Naviga per:</div> 
				
						<c:choose>
							<c:when test='${navigaAggregata.indicatoreNavigaLocalizzazione eq "R"}'>
								<div class="span6 indicatoreNavigaLocalizzazione" id="indicatoreNavigaLocalizzazione" data-indicatoreNavigaLocalizzazione="A">Area</div>
							</c:when>
							<c:otherwise>
								<div class="span6 indicatoreNavigaLocalizzazione" id="indicatoreNavigaLocalizzazione" data-indicatoreNavigaLocalizzazione="R">Regione</div>
							</c:otherwise>
						</c:choose>
					
					
				</div>
			</div>
		</div>
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- GRAFICI --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<a id="localizzazione-portlet"></a>
		
		<div class="div_localizzazione_1">
			<div class="row chart-div">
				
				<div class="span4 offset1 div_localizzazione chart localizzazione_1" id="italybymacroareas">
				</div>
				
				<div class="span6">
					<div class="span5" id="chartLegendTerritori"></div>
					<div class="span1" id="histogramChart"></div>
				</div>
				<div class="clear"></div>
				
			</div>
		</div>
		
		<div class="alert alert-info localizzazioneEmpty" id="localizzazioneEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>
					
	</div>
	
		
	<portlet:actionURL var="urlActionVar">
	   	<portlet:param name="action" value="cambiaAggregazione"></portlet:param>
	</portlet:actionURL>
	
	<form 
		action="${urlActionVar}" 
		method="post" 
		name="naviga-form" 
		class="naviga-form"
		id="naviga-form"
		style="display: none;">
	
			<aui:input cssClass="pattern" type="hidden" name="pattern" value="${pattern}" id="pattern" />
			<aui:input type="hidden" bean="navigaAggregata" name="pagAggregata" value="${navigaAggregata.pagAggregata}" id="pagAggregata" />
			
			<aui:input cssClass="indicatoreNavigaLocalizzazione" type="hidden" bean="navigaAggregata" name="indicatoreNavigaLocalizzazione" value="${navigaAggregata.indicatoreNavigaLocalizzazione}" id="indicatoreNavigaLocalizzazione" />
	
			<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" id="idNatura" />
			<aui:input type="hidden" bean="navigaAggregata" name="idAreaGeografica" value="${navigaAggregata.idAreaGeografica}" id="idAreaGeografica" />
			<aui:input type="hidden" bean="navigaAggregata" name="idRegione" value="${navigaAggregata.idRegione}" id="idRegione" />
			<aui:input type="hidden" bean="navigaAggregata" name="idProvincia" value="${navigaAggregata.idProvincia}" id="idProvincia" />
		
		
	</form>
	
</div>

<script>
	var dataSet = ${jsonResultLocalizzazione};
	var jsonResultLocalizzazione = eval( dataSet );

	var indicatoreNavigaLocalizzazione = "${indicatoreNavigaLocalizzazione}";
	
	//var jsonResultLocalizzazione=eval('('+'${jsonResultLocalizzazione}'+')');
	var namespaceRicerca4js = "<portlet:namespace/>";
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	var dimension = "${pattern}";
	
	var baseColor1 = "#b2c6ff";
	var baseColor2 = "#4472fb";
	var baseColor3 = "#0932a3";
	
	var textColor = "#1f4e78";
	var fillColor = "Maroon";
	
	if (dimension == "VOLUME"){
		baseColor1 = "#ffdbaa";
		baseColor2 = "#ffb551";
		baseColor3 = "#f08c00";
		fillColor = "#d27900";
	}else
	if (dimension == "COSTO"){
		baseColor1 = "#ffc6e1";
		baseColor2 = "#ff55a6";
		baseColor3 = "#c90061";
		fillColor = "#950047";
	}else
	if (dimension == "IMPORTO"){
		baseColor1 = "#c1ffc1";
		baseColor2 = "#48ff48";
		baseColor3 = "#009600";
		fillColor = "#005500";
	}

	d3.select("#titoloLocalizzazione").style("background", fillColor);
	
	d3.select("#container-localizzazione").style("border-left","5px solid "+fillColor);
	
	var minData = d3.min(jsonResultLocalizzazione, 
			function(d){
				if( dimension == 'VOLUME'){
					return d.volumeValue;
				}
				else if(dimension == 'COSTO'){
					return d.costoValue;
				}
				else{
					return d.importoValue;
				}
			});
		

	var midData = d3.mean(jsonResultLocalizzazione, 
			function(d){
				var result=null;
				if( dimension == 'VOLUME'){
					return d.volumeValue;
				}
				else if(dimension == 'COSTO'){
					return d.costoValue;
				}
				else{
					return d.importoValue;
				}
			});
		
	var maxData = d3.max(jsonResultLocalizzazione, 
			function(d){
				var result=null;
				if( dimension == 'VOLUME'){
					return d.volumeValue;
				}
				else if(dimension == 'COSTO'){
					return d.costoValue;
				}
				else{
					return d.importoValue;
				}
			});


	// scala colori in base a valori calcolati
	var color = d3.scale.linear().domain([minData, midData, maxData]).range([baseColor1, baseColor2, baseColor3]);

	var classCharTerr = "chart-bar-territori";
	var classItalySvg = "italybymacroareassvg";
	var classLegendTerr = "legend-territori";
	
	function drawGraphTerritori(identificativo, dimension, calculated_json){

		var width_div_mappa = d3.select(identificativo).node().getBoundingClientRect().width - 30;
		
		var width = width_div_mappa,
	    height = width_div_mappa,
	    border=0.5
	    bordercolor='none',
	    smallrectW=50,
	    smallrectH=50;

		var svg = d3.select(identificativo).append("svg")
	   	 	.attr("width", width)
	    	.attr("height", height)
	   		.attr("border",border)
			.attr("class", classItalySvg);
	   	
	   	var borderPath = svg.append("rect")
			.attr("height", height)
	       	.attr("width", width)
	       	.style("stroke", bordercolor)
	       	.style("fill", "none")
	       	.style("stroke-width", border);

	   	d3.json("/OpenCup-Theme-theme/js/italy_macroareas.json", 
	   	function(error, it) {
		
	   		var territory_topojson=it.objects.sub.geometries;
		
			// unisco i dati
			for (var i=0;i < territory_topojson.length;i++){
				
				var label_toposon = territory_topojson[i].properties.TERR;
				if( indicatoreNavigaLocalizzazione == 'R' ){
					label_toposon = territory_topojson[i].properties.COD_REG;
				}

				for (var j=0;j<calculated_json.length;j++){
					if (label_toposon == calculated_json[j].localizationLabel){
						var valore_volume=calculated_json[j].volumeValue;
						var valore_costo=calculated_json[j].costoValue;
						var valore_importo=calculated_json[j].importoValue;
						var link=calculated_json[j].detailUrl;
						territory_topojson[i].properties.VALORE_VOLUME=valore_volume;
						territory_topojson[i].properties.VALORE_COSTO=valore_costo;
						territory_topojson[i].properties.VALORE_IMPORTO=valore_importo;
						territory_topojson[i].properties.LINK=link;
						break;
					}
				}
			}

			var projection = d3.geo.albers()
		        .center([0, 41])
		        .rotate([347, 0])
		        .parallels([35, 45])
		        .scale(2000)
		        .translate([width / 2, height / 2]);
	 
		    var path = d3.geo.path()
		        .projection(projection);
		    
	    	svg.append("g")
    		.attr("id","regioneSel")
    		.attr("class","elementoCartina");
	     
		    svg.selectAll("g")
		    	.selectAll("path")
		    	.data(topojson.feature(it, it.objects.sub).features)
		    	.enter()
		    	.append("path")
		    	.attr("data_linkURL", function (d){ return d.properties.LINK })
		    	.attr("class",function(d) { 
		    		var code = d.properties.TERR;
					if( indicatoreNavigaLocalizzazione == 'R' ){
						code = d.properties.COD_REG;
					}
		    		return "link-url-naviga-dettaglio terr-code-" + code + " " + code; 
		    	})
		    	.attr("d",path)
		    	.attr ("id",function(d) { return d.properties.ID_REG_TER; })
				.on("click", function(d){
				
					return null;
				
				}).style("fill", function(d){
					
					if( dimension=='VOLUME'){
						return color(d.properties.VALORE_VOLUME);
					}
					else if(dimension=='COSTO'){
						return color(d.properties.VALORE_COSTO);
					}
					else{
						return color(d.properties.VALORE_IMPORTO);
					}
		    	})
				.on("mouseover", function(a){
					
					var ele = d3.select(this);
					ele.style('cursor','pointer');
					
					svg.selectAll("path").style("fill", function (d){
	    				var retValColor = "#fff";
	    				
	    				var codeA = a.properties.TERR;
						if( indicatoreNavigaLocalizzazione == 'R' ){
							codeA = a.properties.COD_REG;
						}
						var codeD = d.properties.TERR;
						if( indicatoreNavigaLocalizzazione == 'R' ){
							codeD = d.properties.COD_REG;
						}

	    				if (codeA == codeD){
	    					if( dimension=='VOLUME'){
	    						retValColor = "#d27900";
	    					}else if(dimension=='COSTO'){
	    						retValColor = "#950047";
	    					}else{
	    						retValColor = "#005500";
	    					}
	    					
	    					var legend_circle_class = ".legend-circle-Legend1Territori-cod-" + codeA;
		    				var circle = d3.select(legend_circle_class);
		    				circle.style("fill", retValColor);
		    				
		    				var legend_class = ".legend-Legend1Territori-cod-" + codeA;
		    				var legend = d3.select(legend_class);
		    				legend.style("fill", retValColor);
		    				
		    				var histogram_class = ".histogram-Histogram1Territori-cod-" + codeA;
		    				var histogram = d3.select(histogram_class);
		    				histogram.style("fill", retValColor);
		    				
		    				var histogram_legend_class = ".histogram-legend-Histogram1Territori-cod-" + codeA;
		    				var histogram_legend = d3.select(histogram_legend_class);
		    				histogram_legend.style("fill", retValColor);
		    				
	    				}else{
	    					if( dimension=='VOLUME' && typeof d.properties.VALORE_VOLUME!=="undefined"){
	    						retValColor = color(d.properties.VALORE_VOLUME);
							}else if(dimension=='COSTO' && typeof d.properties.VALORE_COSTO!=="undefined"){
								retValColor = color(d.properties.VALORE_COSTO);
							}else if(typeof d.properties.VALORE_IMPORTO!=="undefined"){
								retValColor = color(d.properties.VALORE_IMPORTO);
							}
	    		  		}
						return retValColor;
	    			});
				})
				.on("mouseout", function(a){
					
					var ele = d3.select(this);
					ele.style('cursor','default');
					
					var code = a.properties.TERR;
					if( indicatoreNavigaLocalizzazione == 'R' ){
						code = a.properties.COD_REG;
					}
					
					svg
					.selectAll(".terr-code-"+code)
					.style("fill", function(d){
						var retValColor = "#fff";
						if( dimension=='VOLUME' && typeof d.properties.VALORE_VOLUME!=="undefined"){
	    						retValColor = color(d.properties.VALORE_VOLUME);
						}else if(dimension=='COSTO' && typeof d.properties.VALORE_COSTO!=="undefined"){
								retValColor = color(d.properties.VALORE_COSTO);
						}else if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
								retValColor = color(d.properties.VALORE_IMPORTO);
						}
						
						var legend_circle_class = ".legend-circle-Legend1Territori-cod-" + code;
	    				var circle = d3.select(legend_circle_class);
	    				circle.style("fill", retValColor);
	    				
	    				var legend_class = ".legend-Legend1Territori-cod-" + code;
	    				var legend = d3.select(legend_class);
	    				legend.style("fill", textColor);
						
	    				var histogram_class = ".histogram-Histogram1Territori-cod-" + code;
	    				var histogram = d3.select(histogram_class);
	    				histogram.style("fill", retValColor);
	    				
	    				var histogram_legend_class = ".histogram-legend-Histogram1Territori-cod-" + code;
	    				var histogram_legend = d3.select(histogram_legend_class);
	    				histogram_legend.style("fill", textColor);
	    				
						return retValColor;
					});
	    		});
		    
			    var selection = d3.select('.elementoCartina');
	    	   	
	    		// trovo coordinate quadrato che circonda la selezione  
	    		var currentX=selection[0][0].getBBox().x;
	    		var currentY=selection[0][0].getBBox().y;
	    		var currentW=selection[0][0].getBBox().width;
	    		var currentH=selection[0][0].getBBox().height;
	
	    		// calcolo spostamenti per portare il riferimento su angolo superiore sx (ossia sottraggo ascissa e ordinata)
	    		var xFirstTranslation=-currentX;
	    		var yFirstTranslation=-currentY;
	
	    		var maxCurrentWH = (currentW>currentH)?currentW:currentH;

	    		// dopo aver scalato sposto al centro il g contenitore
	    		var maxScale=width_div_mappa/maxCurrentWH;
	
	    		var xSecondTranslation=(width/2)-(currentW*(maxScale/2));
	    		 var ySecondTranslation=(height/2)-(currentH*(maxScale/2));
	    		
	    		// sposta all'angolo, poi quintuplica, poi sposta al centro
	    		selection.attr("transform", "translate("+xSecondTranslation+","+10+")  scale("+maxScale+")  translate("+xFirstTranslation+","+yFirstTranslation+") " );
	    		var newBorder=border/maxScale;
	    		
	    		selection.style("stroke-width", newBorder);
		    	
   			});
		
	}
	
	function switchColor(identificativo, coloreGrafico, coloreTesto){
		
		var legend_circle_class = ".legend-circle-Legend1Territori-cod-" + identificativo;
		var circle = d3.select(legend_circle_class);
		circle.style("fill", coloreGrafico);
		
		var legend_class = ".legend-Legend1Territori-cod-" + identificativo;
		var legend = d3.select(legend_class);
		legend.style("fill", coloreTesto);
		
		var histogram_class = ".histogram-Histogram1Territori-cod-" + identificativo;
		var histogram = d3.select(histogram_class);
		histogram.style("fill", coloreGrafico);
		
		var histogram_legend_class = ".histogram-legend-Histogram1Territori-cod-" + identificativo;
		var histogram_legend = d3.select(histogram_legend_class);
		histogram_legend.style("fill", coloreTesto);
		
		var terr_class = ".terr-code-" + identificativo;
		var terr = d3.selectAll(terr_class);
		terr.style("fill", function(a){
			return coloreGrafico;
		});
		
	}
	
	syncMouseOver = function(a){
		
		var ele = d3.select(this);
		ele.style('cursor','pointer');
		
		if( dimension=='VOLUME'){
			retValColor = "#d27900";
		}else if(dimension=='COSTO'){
			retValColor = "#950047";
		}else{
			retValColor = "#005500";
		}
		switchColor(a.localizationLabel, retValColor, retValColor);
	}
	
	syncMouseOut = function(a){
		var ele = d3.select(this);
		ele.style('cursor','default');
		
		var retValColor = "#fff";
		if( dimension=='VOLUME'){
				retValColor = color(a.volumeValue);
		}else if(dimension=='COSTO'){
				retValColor = color(a.costoValue);
		}else{
				retValColor = color(a.importoValue);
		}
		switchColor(a.localizationLabel, retValColor, textColor);
	}
	
	function drawLegendTerritori (divLegend, legendName, dataSet){
		
		var widthTotal = 50;
		var heightLegend = 25; 
		var gapBetweenGroups = 25;
		var legendBulletOffset = 30;
		var legendTextOffset = 20;
		var textVerticalSpace = 20;
		
		var canvas = d3.select(divLegend).append("svg:svg")
		    .attr("width", 800)
		    .attr("height", gapBetweenGroups + (dataSet.length * heightLegend) )
		    .attr("class", classLegendTerr );
					
		// Plot the bullet circles...
		canvas.selectAll("circle")
		.data(dataSet)
		.enter()
		.append("svg:circle") // Append circle elements
		.attr("cx", widthTotal)
		.attr("cy", function(d, i) { 
			return gapBetweenGroups + (heightLegend * i);
		})
		.attr("stroke-width", ".5")
		.style("fill", 
				function(d, i) {
					if( dimension=='VOLUME'){
						return color(d.volumeValue);
					}
					else if(dimension=='COSTO'){
						return color(d.costoValue);
					}
					else{
						return color(d.importoValue);
					}
				}) // Bullet fill color
		.attr("r", 5)
		.attr("index_value", function(d, i) { return "index-" + i; })
		.attr("class", function(d, i) {
			return "link-url-naviga-dettaglio legend-circle-" + legendName + "-cod-" + d.localizationLabel; })
		.attr("data_linkURL", function (d){ return d.detailUrl })
		.on("mouseover", syncMouseOver )
		.on("mouseout", syncMouseOut );
			
		// Create hyper linked text at right that acts as label key...
        canvas.selectAll("a.legend_link")
		.data(dataSet) // Instruct to bind dataSet to text elements
		.enter()
		.append("text")
		.attr("text-anchor", "center")
		.attr("x", widthTotal + 20)
		.attr("y", function(d, i) { 
			return gapBetweenGroups + (heightLegend*i);
		})
		.attr("dx", 0)
        .attr("dy", "5px") // Controls padding to place text in alignment with bullets
        .text(function(d) { return (d.fullLabel).trunc(36, true); })
        .attr("index_value", function(d, i) { return "index-" + i; })
        .attr("class", function(d, i) { return "link-url-naviga-dettaglio label legend-" + legendName + "-cod-" + d.localizationLabel; })
        .style("fill", textColor)
        .style("font-size", "1.8em")
        .attr("data_linkURL", function (d){ return d.detailUrl })
        .on("mouseover", syncMouseOver )
		.on("mouseout", syncMouseOut )
        .append("title")
        .text(function(d) { return d.fullLabel; });
		
	};
	
	function drawBarTerritori(divChart, histogramName, dataSet){

		var chartWidth       = 200,
		    barHeight        = 25, //220 / dataSet.length, //310
		    gapBetweenGroups = 30,
		    spaceForLabels   = 150;

		// Zip the series data together (first values, second values, etc.)
		var zippedData = [];
		
		for (var i=0; i<dataSet.length; i++) {
			if( dimension=='VOLUME'){
				zippedData.push(dataSet[i].volumeValue);
			}else if(dimension=='COSTO'){
				zippedData.push(dataSet[i].costoValue);
			}else{
				zippedData.push(dataSet[i].importoValue);
			}
		}

		var chartHeight = barHeight * zippedData.length + (gapBetweenGroups * 2);
		
		var x = d3.scale.linear().domain([0, d3.max(zippedData)]).range([0, chartWidth]);

		var y = d3.scale.linear().range([chartHeight + gapBetweenGroups, 0]);

		var yAxis = d3.svg.axis()
		    .scale(y)
		    .tickFormat('')
		    .tickSize(0)
		    .orient("left");
		
		var chart = d3.select(divChart).append("svg")
	    .attr("width", spaceForLabels + chartWidth)
		.attr("height", chartHeight)
	    .attr("class", classCharTerr);

		// Create bars
		var bar = chart.selectAll("g")
		    .data(zippedData)
		    .enter()
			.append("g")
			.attr("index_value", function(d, i) { return "index-" + i; })
			.attr("transform", 
					function(d, i) {
		     	 		return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/dataSet.length))) + ")";
		    });

		// Create rectangles of the correct width
		bar.append("rect")
		    .attr("fill", 
   				function(d, i) {
   					return color(d);
   				}) // Bullet fill color
			.attr("class", function(d, i) { return "link-url-naviga-dettaglio bar histogram-" + histogramName + "-cod-" + dataSet[i].localizationLabel; })
		    .attr("width", x)
		    .attr("height", barHeight - 10)
		    .attr("data_linkURL", function (d, i){ return dataSet[i].detailUrl })
		    .on("mouseover", function(d, i){
				
				var ele = d3.select(this);
				ele.style('cursor','pointer');
				
				if( dimension=='VOLUME'){
					retValColor = "#d27900";
				}else if(dimension=='COSTO'){
					retValColor = "#950047";
				}else{
					retValColor = "#005500";
				}
				switchColor(jsonResultLocalizzazione[i].localizationLabel, retValColor, retValColor);
			} )
			.on("mouseout", function(d, i){
				var ele = d3.select(this);
				ele.style('cursor','default');
				
				var retValColor = color(d);
				switchColor(jsonResultLocalizzazione[i].localizationLabel, retValColor, textColor);
			} );
		
		// Draw labels
		bar.append("text")
			.attr("class", function(d, i) { return "link-url-naviga-dettaglio label histogram-legend-" + histogramName + "-cod-" + dataSet[i].localizationLabel; })
		   	.attr("x", function(d) { return - 80; })
		   	.attr("y", (barHeight-10) / 2)
		   	.attr("dy", ".25em")
		   	.style("fill", textColor)
		   	.style("font-size", "1.8em")
		   	.text(function(d, i) { 
				return nFormatter(d);
			})
			.attr("data_linkURL", function (d, i){ return dataSet[i].detailUrl })
		   	.on("mouseover", function(d, i){
				
				var ele = d3.select(this);
				ele.style('cursor','pointer');
				
				if( dimension=='VOLUME'){
					retValColor = "#d27900";
				}else if(dimension=='COSTO'){
					retValColor = "#950047";
				}else{
					retValColor = "#005500";
				}
				switchColor(jsonResultLocalizzazione[i].localizationLabel, retValColor, retValColor);
			} )
			.on("mouseout", function(d, i){
				var ele = d3.select(this);
				ele.style('cursor','default');
				
				var retValColor = color(d);
				switchColor(jsonResultLocalizzazione[i].localizationLabel, retValColor, textColor);
			} );
	};

	function formatEuro(number){
		var numberStr = parseFloat(number).toFixed(2).toString();
		var numFormatDec = numberStr.slice(-2); /*decimal 00*/
		numberStr = numberStr.substring(0, numberStr.length-3); /*cut last 3 strings*/
		var numFormat = new Array;
		while (numberStr.length > 3) {
			numFormat.unshift(numberStr.slice(-3));
			numberStr = numberStr.substring(0, numberStr.length-3);
		}
		numFormat.unshift(numberStr);
		return numFormat.join('.')+','+numFormatDec; /*format 000.000.000,00 */
	}

	function formatInteger(importoNumerico){
		  
		  var importo = importoNumerico.toString();
		  
		  if(importo.length>3){
		    importo = importo.split('',importo.length).reverse().join('').replace(/([0-9]{3})/g,'$1.');
		    importo = importo.split('',importo.length).reverse().join('');
		  }
		  return importo.replace(/^\./, "");  // elimina il primo punto se presente
	}
	
	function nFormatter(num){
	    if (num >= 1000000000) {
	       return (num / 1000000000).toFixed(0).replace(/\.0$/, '') + ' Mld';
	    }
	    if (num >= 1000000) {
	       return (num / 1000000).toFixed(0).replace(/\.0$/, '') + ' Mil';
	    }
	    if (num >= 1000) {
	       return (num / 1000).toFixed(0).replace(/\.0$/, '') + ' K';
	    }
	    return num;
	}
	
	String.prototype.trunc =
	     function(n,useWordBoundary){
	         var toLong = this.length>n,
	         s_ = toLong ? this.substr(0,n-1) : this;
	         s_ = useWordBoundary && toLong ? s_.substr(0,s_.lastIndexOf(' ')) : s_;
	         //return  toLong ? s_ + '&hellip;' : s_;
	         return  toLong ? s_ + '...' : s_;
	      };
	
	drawGraphTerritori("#italybymacroareas", dimension, jsonResultLocalizzazione);
	
	drawLegendTerritori("#chartLegendTerritori", "Legend1Territori", jsonResultLocalizzazione);
	
	drawBarTerritori("#histogramChart", "Histogram1Territori", jsonResultLocalizzazione);
	
	
	/*
	var indicatoreNavigaLocalizzazione = d3.select("#indicatoreNavigaLocalizzazione");
	
	indicatoreNavigaLocalizzazione.text('Regione');
	
	indicatoreNavigaLocalizzazione.on('click', function(){

		d3.select("#italybymacroareas").selectAll( "."+classItalySvg ).remove();
		d3.select("#chartLegendTerritori").selectAll( "."+classLegendTerr ).remove();
		d3.select("#histogramChart").selectAll( "."+classCharTerr ).remove();
		
		if( indicatoreNavigaLocalizzazione.text() == 'Regione' ){
			indicatoreNavigaLocalizzazione.text('Area Geografica');

			drawGraphTerritori("#italybymacroareas", dimension, jsonResultLocalizzazione);
			drawLegendTerritori("#chartLegendTerritori", "Legend1Territori", jsonResultLocalizzazione);
			drawBarTerritori("#histogramChart", "Histogram1Territori", jsonResultLocalizzazione);
		}else{
			indicatoreNavigaLocalizzazione.text('Regione');
			
			drawGraphTerritori("#italybymacroareas", dimension, jsonResultLocalizzazione);
			drawLegendTerritori("#chartLegendTerritori", "Legend1Territori", jsonResultLocalizzazione);
			drawBarTerritori("#histogramChart", "Histogram1Territori", jsonResultLocalizzazione);
		}		
	});	
	*/
	
	AUI().use('get', function(A){
		   A.Get.script('${jsFolder}/jquery-1.11.0.min.js', {
		       	onSuccess: function(){
		       		A.Get.script('${jsFolder}/bootstrap.min.js', {
		       			onSuccess: function(){	
		       			
		       				$(".volume-color").mouseover(function() { 
		       					$(".arrow-down-volume").css('border-top','10px solid #d27900'); 
		       				});
		       				$(".volume-color").mouseout(function() { 
		       					$(".arrow-down-volume").css('border-top','10px solid #f08c00'); 
		       				});
		       				
		       				$(".costo-color").mouseover(function() { 
		       					$(".arrow-down-costo").css('border-top','10px solid #950047'); 
		       				});
		       				$(".costo-color").mouseout(function() { 
		       					$(".arrow-down-costo").css('border-top','10px solid #c90061'); 
		       				});
		       				
		       				$(".importo-color").mouseover(function() { 
		       					$(".arrow-down-importo").css('border-top','10px solid #005500'); 
		       				});
		       				$(".importo-color").mouseout(function() { 
		       					$(".arrow-down-importo").css('border-top','10px solid #009600'); 
		       				});
		       				
		       				$( ".sel-type-btn" ).click(function() {
	       						var arc = d3.select(this);
		       					var distribuzione = arc.attr("data-distribuzione");
		       					$( ".pattern" ).val(distribuzione);
		       					$( ".naviga-form" ).submit();
		       				});
		       				
		       				$(".link-url-naviga-dettaglio" ).click(function() {
		       					var arc = d3.select(this);
		       					var data_linkURL = arc.attr("data_linkURL");
		       					$( ".naviga-form" ).attr("action", data_linkURL);
		       					$( ".naviga-form" ).submit();
		       				});
		       				
		       				$("#indicatoreNavigaLocalizzazione" ).click(function() {
		       					var arc = d3.select(this);
		       					var dataIndicatoreNavigaLocalizzazione = arc.attr("data-indicatoreNavigaLocalizzazione");
		       					$( ".indicatoreNavigaLocalizzazione" ).val(dataIndicatoreNavigaLocalizzazione);
		       					$( ".naviga-form" ).submit();
		       				});
		       				
		       				
		       				
		      			}
			 		});
		      	}
			 });
		});
	
</script>

