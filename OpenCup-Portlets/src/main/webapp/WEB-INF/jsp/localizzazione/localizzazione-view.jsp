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

#italybymacroareas svg path:hover {cursor: default !important;}

</style>

<portlet:defineObjects />
<fmt:setLocale value="it_IT"/>


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
		
		<div class="div_localizzazione_1">
			<div class="row chart-div">
				
				<div class="span3 offset1" id=chartLegendTerritori></div>
					
				<div class="span4" id="histogramChart">
					<svg class="chart-bar-territori"></svg>
				</div>

				<div class="span4 chart" id="italybymacroareas">
				</div>
				
				<div class="clear"></div>
				
			</div>
		</div>
		
		<div class="alert alert-info localizzazioneEmpty" id="localizzazioneEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>
					
	</div>
	
</div>

<script>
	var dataSet = ${jsonResultLocalizzazione};
	var jsonResultLocalizzazione = eval( dataSet );

	//var jsonResultLocalizzazione=eval('('+'${jsonResultLocalizzazione}'+')');
	var namespaceRicerca4js = "<portlet:namespace/>";
	var namespaceRicerca = "<portlet:namespace/>";
	namespaceRicerca = namespaceRicerca.substring(1, namespaceRicerca.length - 1);
	var dimension = "${dimension}";
	
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
	
	d3.select("#container-localizzazione").style("border-left","10px solid "+fillColor);
	
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

		width_div_mappa = d3.select("#italybymacroareas").node().getBoundingClientRect().width - 30;
		
		var width = width_div_mappa,
	    height = width_div_mappa,
	    border=0.5
	    bordercolor='none',
	    smallrectW=50,
	    smallrectH=50;

		var svg = d3.select("#italybymacroareas")
			.append("svg")
	   	 	.attr("width", width)
	    	.attr("height", height)
	   		.attr("border",border);
	   	
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
				var label_toposon=territory_topojson[i].properties.TERR;
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
	    
		    svg.append("g").attr("id","territorioSel");
	     
		    svg.selectAll("g")
		    	.selectAll("path")
		    	.data(topojson.feature(it, it.objects.sub).features)
		    	.enter()
		    	.append("path")
		    	.attr("class",function(d) { return d.properties.TERR; })
		    	.attr("d",path)
		    	.attr ("id",function(d) { return d.properties.ID_REG_TER; })
		    	.style("fill", function(d){
		    		//console.log(d.properties.TERR);
		    		if( dimension=='VOLUME'){
						return color(d.properties.VALORE_VOLUME);
					}
					else if(dimension=='COSTO'){
						return color(d.properties.VALORE_COSTO);
					}
					else{
						return color(d.properties.VALORE_IMPORTO);
					}
		    	});
   		});
		
	}

	function drawLegendTerritori (divLegend, legendName, dataSet){
		
		var width_LegendTerritori = d3.select(divLegend).node().getBoundingClientRect().width;
		
		var widthTotal = 50;
		var heightLegend = 25; 
		var gapBetweenGroups = 25;
		var legendBulletOffset = 30;
		var legendTextOffset = 20;
		var textVerticalSpace = 20;
		
		var canvas = d3.select(divLegend).append("svg:svg")
		    .attr("width", width_LegendTerritori)
		    .attr("height", gapBetweenGroups + (dataSet.length * heightLegend) );
					
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
		.attr("class", function(d, i) { return "link-url-naviga-dettaglio legend-" + legendName + "-legendBullet-index-" + i; });
		
		
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
			
        .attr("class", function(d, i) { return "link-url-naviga-dettaglio label legend-" + legendName + "-legendText-index-" + i; })
		
        .style("fill", textColor)
        .style("font-size", "1.8em")
        .append("title")
        .text(function(d) { return d.fullLabel; });
		
	};
	
	function drawBarTerritori(chartName, histogramName, dataSet){

		var width_chart = d3.select(chartName).node().getBoundingClientRect().width;
		
		var chartWidth       = (width_chart / 100 * 70);
		    barHeight        = 25, //220 / dataSet.length, //310
		    gapBetweenGroups = 30,
		    spaceForLabels   = spaceForLabels   = width_chart - chartWidth;

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
			.attr("class", function(d, i) { return "bar histogram-" + histogramName + "-arc-index-" + i; })
		    .attr("width", x)
		    .attr("height", barHeight - 10);
		
		// Draw labels
		bar.append("text")
		   .attr("class", function(d, i) { return "label histogram-" + histogramName + "-label-index-" + i; })
		   .attr("x", function(d) { return - 10; })
		   .attr("y", (barHeight-10) / 2)
		   .attr("dy", ".25em")
		   .style("fill", textColor)
		   .style("font-size", "1.8em")
		   .text(function(d, i) { 
				return nFormatter(d);
			});
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
	
	drawGraphTerritori(dimension, jsonResultLocalizzazione);
	
	drawLegendTerritori("#chartLegendTerritori", "Legend1Territori", jsonResultLocalizzazione);
	
	drawBarTerritori(".chart-bar-territori", "Histogram1Territori", jsonResultLocalizzazione);
</script>
