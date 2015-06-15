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

<div>
	<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center; background: #f0f0f0; padding-top: 3px;">
		<div class="offset3 span2">
			<div class="btn-carica-distribuzione volume-color sel-type-btn" data-distribuzione="VOLUME">
				<aui:a href="#" onClick="return false" cssClass="block">
					Progetti
				</aui:a>
			</div>
			<c:if test='${pattern eq "VOLUME"}'>
				<div class="arrow-down-volume"></div>
			</c:if>
		</div>
		<div class="span2">	
			<div class="btn-carica-distribuzione costo-color sel-type-btn" data-distribuzione="COSTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					Costo
				</aui:a>
			</div>
			<c:if test='${pattern eq "COSTO"}'>
				<div class="arrow-down-costo"></div>
			</c:if>
		</div>
		<div class="span2">	
			<div class="btn-carica-distribuzione importo-color sel-type-btn" data-distribuzione="IMPORTO">
				<aui:a href="#" onClick="return false" cssClass="block">
					Finanziamento
				</aui:a>
			</div>
			<c:if test='${pattern eq "IMPORTO"}'>
				<div class="arrow-down-importo"></div>
			</c:if>
		</div>
		<div class="clear"></div>
	
	</div>	
	
	<div class="stripe">
	
		<div id="container-localizzazione">
			
			<div class="row">
				<div class="titoloLocalizzazione" id="titoloLocalizzazione">
					Localizzazione
				</div>
			</div>
			
			<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
			 -- GRAFICI --		
			 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
			<a id="localizzazione-portlet"></a>
			
			<div class="div_localizzazione_1 div_grafico_padding">
				<div class="row chart-div">
					
					<div class="span4 offset1 div_localizzazione chart localizzazione_1" id="italybymacroareas" >
					</div>
					
					<div class="span6" style="padding-top: 80px">
						<div class="span5" id="chartLegendTerritori"></div>
						<div class="span1" id="histogramChart">
							<svg class="chart-bar-territori"></svg>
						</div>
					</div>
	
					<div class="clear"></div>
					
				</div>
			</div>
			
			<div class="alert alert-info localizzazioneEmpty" id="localizzazioneEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>
						
		</div>
	
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
	
			<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" id="idNatura" />
			<aui:input type="hidden" bean="navigaAggregata" name="idAreaGeografica" value="${navigaAggregata.idAreaGeografica}" id="idAreaGeografica" />
			<aui:input type="hidden" bean="navigaAggregata" name="idRegione" value="${navigaAggregata.idRegione}" id="idRegione" />
			<aui:input type="hidden" bean="navigaAggregata" name="idProvincia" value="${navigaAggregata.idProvincia}" id="idProvincia" />
		
		
	</form>
	
</div>

<script>
	var dataSet = ${jsonResultLocalizzazione};
	var jsonResultLocalizzazione = eval( dataSet );

	//var jsonResultLocalizzazione=eval('('+'${jsonResultLocalizzazione}'+')');
	var namespaceRicerca4js = "<portlet:namespace/>";
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	var dimension = "${pattern}";
	var areaGEOSel="${areaGEO}";
	var regioneSel="${codRegione}";
	
	console.log( regioneSel );
	
	var baseColor1 = "#b2c6ff";
	var baseColor2 = "#4472fb";
	var baseColor3 = "#0932a3";
	
	var textColor = "#1f4e78";
	var fillColor = "Maroon";
	var coloreMisura = "#1f4e78";
	
	if (dimension == "VOLUME"){
		baseColor1 = "#ffdbaa";
		baseColor2 = "#ffb551";
		baseColor3 = "#f08c00";
		fillColor = "#d27900";
		coloreMisura = "#f08c00";
	}else
	if (dimension == "COSTO"){
		baseColor1 = "#69d876";
		baseColor2 = "#58b663";
		baseColor3 = "#499652";
		fillColor = "#3e7d46";
		coloreMisura = "#499652";
	}else
	if (dimension == "IMPORTO"){
		baseColor1 = "#c1ffc1";
		baseColor2 = "#48ff48";
		baseColor3 = "#7ade87";
		fillColor = "#005500";
		coloreMisura = "#7ade87";
	}

	d3.select("#titoloLocalizzazione").style("color", coloreMisura);
	d3.select("#titoloLocalizzazione").style("text-align", "left");
	
	d3.select("#container-localizzazione").style("border-left", "5px solid "+coloreMisura);
	
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
	
	function drawGraphTerritori(dimension, calculated_json){

		var width_div_mappa = d3.select("#italybymacroareas").node().getBoundingClientRect().width - 30;
		
		var width = width_div_mappa,
	    height = width_div_mappa,
	    border=0.5
	    bordercolor='none',
	    smallrectW=50,
	    smallrectH=50;

		var svg = d3.select("#italybymacroareas").append("svg")
	    	.attr("width", width)
	    	.attr("height", height)
	   		.attr("border",border);
   	
   		var borderPath = svg.append("rect")
			.attr("height", height)
	       	.attr("width", width)
	       	.style("stroke", bordercolor)
	       	.style("fill", "none")
	       	.style("stroke-width", border);
	   	
	   	d3.json("/OpenCup-Theme-theme/js/area_"+areaGEOSel+".json", 
	   	function(error, it) {
		
	   		var territory_topojson = it.objects.sub.geometries;
	   		
			// unisco i dati
			for (var i=0;i < territory_topojson.length;i++){
				var label_toposon=territory_topojson[i].properties.COD_PRO;
				for (var j=0;j<calculated_json.length;j++){
					if (label_toposon==calculated_json[j].localizationLabel){
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
		    		var retval = "";
		    		if(	regioneSel == d.properties.COD_REG ){
		    			retval = "link-url-naviga-dettaglio terr-code-" + d.properties.COD_PRO + " " + d.properties.COD_PRO;
		    		}else{	
		    			retval = retval + " notIncluded"
		    		}
		    		return retval; 
		    	})
		    	.attr("d",path)
		    	.attr ("id",function(d) { return d.properties.COD_PRO; })
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
	    				if (d.properties.COD_PRO==a.properties.COD_PRO){
	    					if( dimension=='VOLUME'){
	    						retValColor = "#d27900";
	    					}else if(dimension=='COSTO'){
	    						retValColor = "#3e7d46";
	    					}else{
	    						retValColor = "#005500";
	    					}
	    					
	    					var legend_circle_class = ".legend-circle-Legend1Territori-cod-" + a.properties.COD_PRO;
		    				var circle = d3.select(legend_circle_class);
		    				circle.style("fill", retValColor);
		    				
		    				var legend_class = ".legend-Legend1Territori-cod-" + a.properties.COD_PRO;
		    				var legend = d3.select(legend_class);
		    				legend.style("fill", retValColor);
		    				
		    				var histogram_class = ".histogram-Histogram1Territori-cod-" + a.properties.COD_PRO;
		    				var histogram = d3.select(histogram_class);
		    				histogram.style("fill", retValColor);
		    				
		    				var histogram_legend_class = ".histogram-legend-Histogram1Territori-cod-" + a.properties.COD_PRO;
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
					svg.selectAll(".terr-code-"+a.properties.COD_PRO).style("fill", function(d){
						var retValColor = "#fff";
						if( dimension=='VOLUME' && typeof d.properties.VALORE_VOLUME!=="undefined"){
	    						retValColor = color(d.properties.VALORE_VOLUME);
						}else if(dimension=='COSTO' && typeof d.properties.VALORE_COSTO!=="undefined"){
								retValColor = color(d.properties.VALORE_COSTO);
						}else if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
								retValColor = color(d.properties.VALORE_IMPORTO);
						}	
						var legend_circle_class = ".legend-circle-Legend1Territori-cod-" + a.properties.COD_PRO;
	    				var circle = d3.select(legend_circle_class);
	    				circle.style("fill", retValColor);
	    				
	    				var legend_class = ".legend-Legend1Territori-cod-" + a.properties.COD_PRO;
	    				var legend = d3.select(legend_class);
	    				legend.style("fill", textColor);
						
	    				var histogram_class = ".histogram-Histogram1Territori-cod-" + a.properties.COD_PRO;
	    				var histogram = d3.select(histogram_class);
	    				histogram.style("fill", retValColor);
	    				
	    				var histogram_legend_class = ".histogram-legend-Histogram1Territori-cod-" + a.properties.COD_PRO;
	    				var histogram_legend = d3.select(histogram_legend_class);
	    				histogram_legend.style("fill", textColor);
	    				
						return retValColor;
					});
	    		});
	    	
	    		svg.selectAll(".notIncluded").remove();
	    	
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

	    		//lo shape del veneto risulta piu' lungo e deve essere centrato manualmente veneto =150y x60
	    		if (regioneSel=='05'){
	    			xSecondTranslation+=60;
	    			ySecondTranslation+=150;
	    		}
	    		
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
			retValColor = "#3e7d46";
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
		    .attr("height", gapBetweenGroups + (dataSet.length * heightLegend) );
					
		// Plot the bullet circles...
		/*
		canvas.selectAll("circle")
		.data(dataSet)
		.enter()
		.append("svg:circle") // Append circle elements
		.attr("cx", widthTotal)
		.attr("cy", function(d, i) { 
			return gapBetweenGroups + (heightLegend * i);
		})
		.attr("stroke-width", ".5")
		.attr("r", 5)
		*/
		canvas.selectAll("rect")
		.data(dataSet)
		.enter()
		.append("rect")
		.attr("width", "16")
		.attr("height", "16")
		.attr("x", widthTotal - 10)
		.attr("y", function(d, i) { 
			return gapBetweenGroups + (heightLegend * i) - 10;
		})
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
	
	function drawBarTerritori(chartName, histogramName, dataSet){

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

		// Specify the chart area and dimensions
		var chart = d3.select(chartName)
		    .attr("width", spaceForLabels + chartWidth)
		    .attr("height", chartHeight);

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
					retValColor = "#3e7d46";
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
					retValColor = "#3e7d46";
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
	
	Number.prototype.formattaNumerico = 
		function(c, d, t) {
			var n = this, 
				c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "." : d, 
				t = t == undefined ? "," : t, s = n < 0 ? "-" : "", 
				i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "", 
				j = (j = i.length) > 3 ? j % 3 : 0;
				
		return s + (j ? i.substr(0, j) + t : "")
			+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t)
			+ (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};

	function nFormatter(num) {
		if (num >= 1000000000) {
			return (num / 1000000000).formattaNumerico(0, ',', '.') + ' Mld';
		}
		if (num >= 1000000) {
			return (num / 1000000).formattaNumerico(0, ',', '.') + ' Mil';
		}
		if (num >= 1000) {
			return num.formattaNumerico(0, ',', '.');
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
	
	drawGraphTerritori(dimension, jsonResultLocalizzazione);
	
	drawLegendTerritori("#chartLegendTerritori", "Legend1Territori", jsonResultLocalizzazione);
	
	drawBarTerritori(".chart-bar-territori", "Histogram1Territori", jsonResultLocalizzazione);
	
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
		       					$(".arrow-down-costo").css('border-top','10px solid #2c5831'); 
		       				});
		       				$(".costo-color").mouseout(function() { 
		       					$(".arrow-down-costo").css('border-top','10px solid #499652'); 
		       				});
		       				
		       				$(".importo-color").mouseover(function() { 
		       					$(".arrow-down-importo").css('border-top','10px solid #005500'); 
		       				});
		       				$(".importo-color").mouseout(function() { 
		       					$(".arrow-down-importo").css('border-top','10px solid #7ade87'); 
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
		       				
		      			}
			 		});
		      	}
			 });
		});
	
</script>

