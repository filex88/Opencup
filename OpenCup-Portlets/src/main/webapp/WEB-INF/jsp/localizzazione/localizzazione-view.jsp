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

div.stripe{background: #fff; border-top:.5em solid #f0f0f0;}

#italybymacroareas svg path:hover {cursor: default !important;}

</style>

<portlet:defineObjects />
<fmt:setLocale value="it_IT"/>


<div class="stripe">
	
	<div style="padding-top: 30px; padding-bottom: 30px">
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- GRAFICI --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<a id="localizzazione-portlet"></a>
		
		<div class="div_localizzazione_1">
			<div class="row chart-div" style="height: 500px">
				
				<div class="span6 offset1">
					<div class="span5" id=chartLegendTerritori></div>
					<div class="span1 id="histogramChart">
						<svg class="chart-bar-territori"></svg>
					</div>
				</div>
				
				<div class="span3 div_localizzazione chart localizzazione_1" id="localizzazione_1" style="height: 260px">
					<div id="italybymacroareas"></div>
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
	
	
	
	
	var textColor = "#1f4e78";
	var fillColor = "Maroon";
	
	var baseColor1="rgb(209,226,242)";
	var baseColor2="rgb(114,178,215)";
	var baseColor3="rgb(8,64,131)";
	
	var minData = d3.min(jsonResultLocalizzazione, 
			function(d){
				if( dimension == 'volume'){
					return d.volumeValue;
				}
				else if(dimension == 'costo'){
					return d.costoValue;
				}
				else{
					return d.importoValue;
				}
			});
		

	var midData = d3.mean(jsonResultLocalizzazione, 
			function(d){
				var result=null;
				if( dimension == 'volume'){
					return d.volumeValue;
				}
				else if(dimension == 'costo'){
					return d.costoValue;
				}
				else{
					return d.importoValue;
				}
			});
		
	var maxData = d3.max(jsonResultLocalizzazione, 
			function(d){
				var result=null;
				if( dimension == 'volume'){
					return d.volumeValue;
				}
				else if(dimension == 'costo'){
					return d.costoValue;
				}
				else{
					return d.importoValue;
				}
			});

	// scala colori in base a valori calcolati
	var color = d3.scale.linear().domain([minData, midData, maxData]).range([baseColor1, baseColor2, baseColor3]);

	function drawGraphTerritori(dimension, calculated_json){

		var width = 500,
	    height = 500,
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

/*	   	
	 	var tooltip = d3.select("#italybymacroareas").append("div").attr("class", "selectiontip nascosto");
*/
		
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
		    		if( dimension=='volume'){
						return color(d.properties.VALORE_VOLUME);
					}
					else if(dimension=='costo'){
						return color(d.properties.VALORE_COSTO);
					}
					else{
						return color(d.properties.VALORE_IMPORTO);
					}
		    	})  
/*
		    	.on("click", function(d){
		    		if (typeof d.properties.LINK!=="undefined")
		    		{
		    			window.location = d.properties.LINK;
		    		}else{
		    			window.location="#italybymacroareas";
		    		}
		    		
		   		})
		    	.on("mouseover",function(a){
		    		svg.selectAll("path").style("fill",function (d){
		    			if (d.properties.TERR==a.properties.TERR){
		    				return "#F08C00";
		    			}else{
		    				if( dimension=='volume'){
		    					if (typeof d.properties.VALORE_VOLUME!=="undefined"){
		    						return color(d.properties.VALORE_VOLUME);
		    					}else{
		    						return "#fff";
		    					}
		    				}else if(dimension=='costo'){
								if (typeof d.properties.VALORE_COSTO!=="undefined"){
									return color(d.properties.VALORE_COSTO);
								}else{
									return "#fff";
								}
		    				}else{
								if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
									return color(d.properties.VALORE_IMPORTO);
								}else{
									return "#fff";
								}
		    				}
		    			}
		    		});
		    		
		    		var mouse = d3.mouse(d3.select("#content").node()).map( function(d) { return parseInt(d); } );
		    		var labelToShow=null;
		    		var valueToShow=null;
	    			if( dimension=='volume' ){
	    				labelToShow="VOLUME:";
	    				if (typeof a.properties.VALORE_VOLUME !== "undefined"){
	    					valueToShow=formatInteger(a.properties.VALORE_VOLUME);
	    				}else{
	    					valueToShow='0';
	    				}
					}else if(dimension=='costo'){
						labelToShow="COSTO:";
						if (typeof a.properties.VALORE_COSTO!=="undefined"){
							valueToShow='&euro;&nbsp;'+formatEuro(a.properties.VALORE_COSTO);
						}else{
							valueToShow='&euro;&nbsp;0,00';
						}
	    				
					}else{
						labelToShow="IMPORTO FINANZIATO:";
						if (typeof a.properties.VALORE_IMPORTO!=="undefined"){
							valueToShow='&euro;&nbsp;'+formatEuro(a.properties.VALORE_IMPORTO);
						}else{
							valueToShow='&euro;&nbsp;0,00';
						}
	    				
					}
					
		    		tooltip.classed("nascosto", false)
		        		.attr("style", "left:"+(mouse[0]+10)+"px;top:"+(mouse[1]-40)+"px")
		        		.html('<p><strong>AREA GEOGRAFICA: </strong>'+a.properties.TERR_DESC+'</p>'+'<p><strong>'+labelToShow+' </strong>'+valueToShow+'</p>');
		    	})
		    	.on("mouseout",function(a){
		    		svg.selectAll("."+a.properties.TERR)
		    			.style("fill",function(d){
		    				if( dimension=='volume'){
		    					if (typeof d.properties.VALORE_VOLUME!=="undefined"){
		    						return color(d.properties.VALORE_VOLUME);
			    				}else{
			    					return "#fff";
			    				}
						
		    				}else if(dimension=='costo'){
								if (typeof d.properties.VALORE_COSTO!=="undefined"){
									return color(d.properties.VALORE_COSTO);
								}else{
									return "#fff";
								}
		    				}else{
								if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
									return color(d.properties.VALORE_IMPORTO);
								}else{
									return "#fff";
								}
		    				}
		    			});
		    		tooltip.classed("nascosto", true)
		    	});
*/	     

/*		    	
		    var territorio=d3.selectAll("#territorioSel");
			territorio.style("stroke-width",border);
			
			var currentY=territorio[0][0].getBBox().y;
			var ytransl=-currentY+10;
 	
			territorio.attr("transform","translate(0,"+ytransl+")");
	
			var skewDown=(ytransl*2)+10+16;
	 
			d3.selectAll("#dimensions").attr("style","margin-top:"+skewDown+"px");
*/
		    	
   		});
		
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
					if( dimension=='volume'){
						return color(d.volumeValue);
					}
					else if(dimension=='costo'){
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
	
	function drawBarTerritori(chartName, histogramName, dataSet) {

		var chartWidth       = 200,
		    barHeight        = 25, //220 / dataSet.length, //310
		    gapBetweenGroups = 30,
		    spaceForLabels   = 150;

		// Zip the series data together (first values, second values, etc.)
		var zippedData = [];
		
		for (var i=0; i<dataSet.length; i++) {
			if( dimension=='volume'){
				zippedData.push(dataSet[i].volumeValue);
			}
			else if(dimension=='costo'){
				zippedData.push(dataSet[i].costoValue);
			}
			else{
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
		   .attr("x", function(d) { return - 60; })
		   .attr("y", (barHeight-10) / 2)
		   .attr("dy", ".25em")
		   .style("fill", textColor)
		   .style("font-size", "1.8em")
		   .text(function(d, i) { 
				return nFormatter(d);
			});
	};

	function formatEuro(number)
	{
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
	
	function nFormatter(num) {
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
