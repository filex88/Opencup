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
<!--
div.stripe{background: #fff;border-top:.5em solid #f0f0f0;}
-->
</style>

<portlet:defineObjects />

<div class="stripe">

	<c:if test="${ config.mostraPulsanti }">
		<div class="distribuzioneToolBar" id="distribuzioneToolBar" style="text-align: center">
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
	</c:if>
	
	<div style="padding-top: 30px; padding-bottom: 30px">
		
		<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 -- GRAFICI --		
		 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
		<a id="pie-chart-portlet"></a>
		
		<div class="div_pie_chart_1">
			<div class="row chart-div">
				<div class="span3 offset1 div_pie_chart chart pie_chart_1" id="pie_chart_1" style="height: 260px">
				</div>
				<div style="overflow-y: auto; height: 260px ">
					<div>
						<div class="span5" id="chartLegend"></div>
						<div class="span3" id="histogramChart">
							<svg class="chart-bar"></svg>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="tooltip-pie-chart" class="tooltip-pie-chart hidden">
	    	<p><span id="label-tooltip-pie-chart"></span></p>
			<p><strong><span>Percentuale</span>:&nbsp;</strong><span id="percentuale-tooltip-pie-chart"></span></p>
			<p>
				<strong><span id="labelvalue-tooltip-pie-chart"></span>:&nbsp;</strong>
				<span id="value-tooltip-pie-chart"></span>
				<span class="hidden" id="umvalue-tooltip-pie-chart">&euro;</span>
			</p>
		</div>
		
		<div class="alert alert-info pieChartEmpty" id="pieChartEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>
					
	</div>
	
	<portlet:actionURL var="urlActionVar">
	   	<portlet:param name="action" value="cambiaAggregazione"></portlet:param>
	</portlet:actionURL>
	
	<form 
		action="${urlActionVar}" 
		method="post" 
		name="naviga-form" 
		class="naviga-form"
		id="naviga-form">
	
			<aui:input cssClass="pattern" type="hidden" name="pattern" value="${pattern}" id="pattern" />
	
			<aui:input type="hidden" bean="navigaAggregata" name="idNatura" value="${navigaAggregata.idNatura}" id="idNatura" />
			<aui:input type="hidden" bean="navigaAggregata" name="idAreaIntervento" value="${navigaAggregata.idAreaIntervento}" id="idAreaIntervento" />
			<aui:input type="hidden" bean="navigaAggregata" name="idSottosettoreIntervento" value="${navigaAggregata.idSottosettoreIntervento}" id="idSottosettoreIntervento" />
			<aui:input type="hidden" bean="navigaAggregata" name="idCategoriaIntervento" value="${navigaAggregata.idCategoriaIntervento}" id="idCategoriaIntervento" />
		
	</form>

</div>

<script type="text/javascript">

	var dataSet = ${aggregati4Pie};
	var dataSet1 = eval( dataSet );
	
	var tipoAggregazione = '${pattern}';
	var selezionabile = ! ${config.selezionabile};
	
	//var segments = [ "#b2c6ff", "#9eb5fc", "#90abfb", "#81a0fa", "#7597fb", "#678dfb", "#5a84fa", "#507cfb", "#4472fb", "#3869f9", "#2f62f2", "#275aea", "#2254e2", "#1b4bd8", "#1745ce", "#1240c3", "#0d39b8", "#0932a3" ];
	
	var baseColor1 = "#b2c6ff";
	var baseColor2 = "#4472fb";
	var baseColor3 = "#0932a3";
	
	var textColor = "#1f4e78";
	var fillColor = "Maroon";
	
	var minData = 0;
	var maxData = ${ recordCount };
	var midData = maxData / 2;
	
	
	if (tipoAggregazione == "VOLUME"){
		baseColor1 = "#ffdbaa";
		baseColor2 = "#ffb551";
		baseColor3 = "#f08c00";
		fillColor = "#d27900";
	}else
	if (tipoAggregazione == "COSTO"){
		baseColor1 = "#ffc6e1";
		baseColor2 = "#ff55a6";
		baseColor3 = "#c90061";
		fillColor = "#950047";
	}else
	if (tipoAggregazione == "IMPORTO"){
		baseColor1 = "#c1ffc1";
		baseColor2 = "#48ff48";
		baseColor3 = "#009600";
		fillColor = "#005500";
	}
	//var colorScale = d3.scale.ordinal().range(segments);

	
	var colorScale = d3.scale.linear().domain([minData,midData,maxData]).range([baseColor1,baseColor2,baseColor3]);
	
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
	
	String.prototype.trunc =
	     function(n,useWordBoundary){
	         var toLong = this.length>n,
	         s_ = toLong ? this.substr(0,n-1) : this;
	         s_ = useWordBoundary && toLong ? s_.substr(0,s_.lastIndexOf(' ')) : s_;
	         //return  toLong ? s_ + '&hellip;' : s_;
	         return  toLong ? s_ + '...' : s_;
	      };

	var synchronizedMouseOver = function(info) {
		
		var arc = d3.select(this);
		
		if(selezionabile){
			arc.style('cursor','default');
		}
		
		var indexValue = arc.attr("index_value");
		var label = arc.attr("data_label");
		var percentage = arc.attr("data_percentage");
		var value = arc.attr("data_value");
	
		var pieArcSelector = "." + "pie-" + "Pie1" + "-arc-" + indexValue;
		var pieSelectedArc = d3.selectAll(pieArcSelector);
		pieSelectedArc.style("fill", fillColor);
		
		var histogramArcSelector = "." + "histogram-" + "Histogram1" + "-arc-" + indexValue;
		var histogramSelectedArc = d3.selectAll(histogramArcSelector);
		histogramSelectedArc.style("fill", fillColor);
		
		var histogramLabelSelector = "." + "histogram-" + "Histogram1" + "-label-" + indexValue;
		var histogramSelectedLabel = d3.selectAll(histogramLabelSelector);
		histogramSelectedLabel.style("fill", fillColor);
		
		var legendBulletSelector = "." + "legend-" + "Legend1" + "-legendBullet-" + indexValue;
		var legendBulletSelected = d3.selectAll(legendBulletSelector);
		legendBulletSelected.style("fill", fillColor);
		
		var legendTextSelector = "." + "legend-" + "Legend1" + "-legendText-" + indexValue;
		var legendTextSelected = d3.selectAll(legendTextSelector);
		legendTextSelected.style("fill", fillColor);

//		//Update the tooltip
//		var tooltip = d3.select("#tooltip-pie-chart");
//			
//		d3.select("#label-tooltip-pie-chart").text(label);
//		d3.select("#labelvalue-tooltip-pie-chart").text(tipoAggregazione);
//		d3.select("#percentuale-tooltip-pie-chart").text((percentage) + "%");
//		if( tipoAggregazione == "VOLUME" ){
//			d3.select("#value-tooltip-pie-chart").text(nFormatter(value));
//			d3.select("#umvalue-tooltip-pie-chart").classed("hidden", true);
//		}else{
//			d3.select("#value-tooltip-pie-chart").text(nFormatter(value));
//			d3.select("#umvalue-tooltip-pie-chart").classed("hidden", false);
//		}
//		
//		//Show the tooltip
//		tooltip.classed("hidden", false); 
//		tooltip.transition().duration(500).style("opacity", 100);
		
	};
	
	var synchronizedMouseOut = function() {
		var arc = d3.select(this);
		var indexValue = arc.attr("index_value");
		
		var colorValue = arc.attr("color_value");
		
		var pieArcSelector = "." + "pie-" + "Pie1" + "-arc-" + indexValue;
		var pieSelectedArc = d3.selectAll(pieArcSelector);
		pieSelectedArc.style("fill", colorValue);
		
		var histogramArcSelector = "." + "histogram-" + "Histogram1" + "-arc-" + indexValue;
		var histogramSelectedArc = d3.selectAll(histogramArcSelector);
		histogramSelectedArc.style("fill", colorValue);
		
		var histogramLabelSelector = "." + "histogram-" + "Histogram1" + "-label-" + indexValue;
		var histogramSelectedLabel = d3.selectAll(histogramLabelSelector);
		histogramSelectedLabel.style("fill", textColor);
		
		var legendBulletSelector = "." + "legend-" + "Legend1" + "-legendBullet-" + indexValue;
		var legendBulletSelected = d3.selectAll(legendBulletSelector);
		legendBulletSelected.style("fill", colorValue);
		
		var legendTextSelector = "." + "legend-" + "Legend1" + "-legendText-" + indexValue;
		var legendTextSelected = d3.selectAll(legendTextSelector);
		legendTextSelected.style("fill", textColor);

		//Hide the tooltip
//		var tooltip = d3.select("#tooltip-pie-chart");
//		tooltip.transition().duration(500).style("opacity", 0);   
//		tooltip.classed("hidden", true);

	};
	
	function drawPie( pieName, dataSet, selectString, colors, margin, outerRadius, innerRadius, sortArcs ) {
		
		// pieName => A unique drawing identifier that has no spaces, no "." and no "#" characters.
		// dataSet => Input Data for the chart, itself.
		// selectString => String that allows you to pass in
		//           a D3 select string.
		// colors => String to set color scale.  Values can be...
		//           => "colorScale10"
		//           => "colorScale20"
		//           => "colorScale20b"
		//           => "colorScale20c"
		// margin => Integer margin offset value.
		// outerRadius => Integer outer radius value.
		// innerRadius => Integer inner radius value.
		// sortArcs => Controls sorting of Arcs by value.
		//              0 = No Sort.  Maintain original order.
		//              1 = Sort by arc value size.
		
		// Color Scale Handling...
//		var colorScale = d3.scale.category20c();
//		
//		switch (colors) {
//			case "colorScale10":
//				colorScale = d3.scale.category10();
//				break;
//			case "colorScale20":
//				colorScale = d3.scale.category20();
//				break;
//			case "colorScale20b":
//				colorScale = d3.scale.category20b();
//				break;
//			case "colorScale20c":
//				colorScale = d3.scale.category20c();
//				break;
//			case "segments":
//				colorScale = d3.scale.ordinal().range(segments);
//				break;
//			default:
//				colorScale = d3.scale.category20c();
//		};
	
		var pieWidthTotal = outerRadius * 2;
			
		var pieCenterX = outerRadius + margin / 2;
		var pieCenterY = outerRadius + margin / 2;
		var legendBulletOffset = 30;
		var legendVerticalOffset = outerRadius - margin;
		var legendTextOffset = 20;
		var textVerticalSpace = 20;
		
		var canvasWidth = outerRadius * 2 + margin * 2;//wigthDivPieChart;
		var canvasHeight = 0;
		
		var pieDrivenHeight = outerRadius * 2 + margin * 2;
			
		// Autoadjust Canvas Height
		//if (pieDrivenHeight >= legendTextDrivenHeight) {
			canvasHeight = pieDrivenHeight;
		//} else {
		//	canvasHeight = legendTextDrivenHeight;
		//}

		var x = d3.scale.linear().domain([ 0, d3.max(dataSet, function(d) {	return d.value; }) ]).rangeRound([ 0, pieWidthTotal ]);
		var y = d3.scale.linear().domain([ 0, dataSet.length ]).range([ 0, (dataSet.length * 20) ]);
	
		var tweenPie = function(b) {
			b.innerRadius = 0;
			var i = d3.interpolate({
				startAngle : 0,
				endAngle : 0
			}, b);
			return function(t) {
				return arc(i(t));
			};
		}
	
		// Create a drawing canvas...
		var canvas = d3.select(selectString).append("svg:svg") //create the SVG element
		.data([ dataSet ]) //associate our data with the document
		.attr("width", canvasWidth) //set the width of the canvas
		.attr("height", canvasHeight) //set the height of the canvas
		.append("svg:g") //make a group to hold our pie chart
		.attr("transform", "translate(" + pieCenterX + "," + pieCenterY + ")") // Set center of pie

		// Define an arc generator. This will create <path> elements for using arc data.
		var arc = d3.svg.arc().innerRadius(innerRadius) // Causes center of pie to be hollow
		.outerRadius(outerRadius);
	
		// Define a pie layout: the pie angle encodes the value of dataSet.
		// Since our data is in the form of a post-parsed CSV string, the
		// values are Strings which we coerce to Numbers.
		var pie = d3.layout.pie().value(function(d) {
			return d.value;
		}).sort(function(a, b) {
			if (sortArcs == 1) {
				return b.value - a.value;
			} else {
				return null;
			}
		});
	
		// Select all <g> elements with class slice (there aren't any yet)
		var arcs = canvas.selectAll("g.slice")
		// Associate the generated pie data (an array of arcs, each having startAngle,
		// endAngle and value properties) 
		.data(pie)
		// This will create <g> elements for every "extra" data element that should be associated
		// with a selection. The result is creating a <g> for every object in the data array
		// Create a group to hold each slice (we will have a <path> and a <text>      // element associated with each slice)
		.enter()
		.append("svg:a")
		.attr("xlink:href", function(d) {
			return "#"; //d.data.linkURL;
		}).on('onclick',  function() {return false;})
		.append("svg:g").attr("class", "slice") //allow us to style things in the slices (like text)
		// Set the color for each slice to be chosen from the color function defined above
		// This creates the actual SVG path using the associated data (pie) with the arc drawing function
		.style("stroke", "White").attr("d", arc);
	
		arcs.append("svg:path")
		// Set the color for each slice to be chosen from the color function defined above
		// This creates the actual SVG path using the associated data (pie) with the arc drawing function
		.attr("fill", function(d, i) { return colorScale(i); })
		.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-" + i; })
		
		.attr("data_id", function(d, i) { return dataSet[i].id })
		.attr("data_label", function(d, i) { return dataSet[i].label })
		.attr("data_value", function(d, i) { return dataSet[i].value })
		.attr("data_percentage", function(d, i) { return dataSet[i].percentage })
		.attr("data_linkURL", function(d, i) { return dataSet[i].linkURL })
		
		.attr("class", function(d, i) { return "link-url-naviga-dettaglio pie-" + pieName + "-arc-index-" + i; })
		.style("stroke", "White")
		.attr("d", arc)
		.on('mouseover', synchronizedMouseOver)
		.on("mouseout", synchronizedMouseOut)
		.transition()
		.ease("linear")
		//.ease("bounce")
		.duration(500)
		.delay(function(d, i) { return i * 50; })
		.attrTween("d", tweenPie);
	
		// Add a value to the larger arcs, translated to the arc centroid and rotated.
		arcs.filter(function(d) { return d.endAngle - d.startAngle > .2; })
		.append("svg:text")
		.attr("dy", ".35em")
		.attr("text-anchor", "middle")
		//.attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")rotate(" + angle(d) + ")"; })
		.attr("transform", function(d) { //set the label's origin to the center of the arc
					//we have to make sure to set these before calling arc.centroid
					d.outerRadius = outerRadius; // Set Outer Coordinate
					d.innerRadius = innerRadius; // Set Inner Coordinate
					return "translate(" + arc.centroid(d) + ")";
					//return "translate(" + arc.centroid(d) + ")rotate("	+ angle(d) + ")";
				})
		
		.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-" + i; })
		
		.attr("data_id", function(d, i) { return dataSet[i].id })
		.attr("data_label", function(d, i) { return dataSet[i].label })
		.attr("data_value", function(d, i) { return dataSet[i].value })
		.attr("data_percentage", function(d, i) { return dataSet[i].percentage })
		.attr("data_linkURL", function(d, i) { return dataSet[i].linkURL })
		.attr("class", function(d, i) { return "link-url-naviga-dettaglio pie-" + pieName + "-perc-index-" + i; })
		
		.style("fill", "White")
		.style("font", "normal 18px Arial")
		//.style("cursor", function(){
		//		if(selezionabile){
		//			return "default";
		//		}else{
		//			return "pointer";
		//		}
		//	})
		.text(function(d) { return (d.data.percentage) + "%"; })
		.on('mouseover', synchronizedMouseOver)
		.on("mouseout", synchronizedMouseOut);
	
		// Computes the angle of an arc, converting from radians to degrees.
		function angle(d) {
			var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
			return a > 90 ? a - 180 : a;
		}
		
	};
	
	function drawBar(chartName, histogramName, dataSet) {

		var chartWidth       = 200,
		    barHeight        = 25, //220 / dataSet.length, //310
		    gapBetweenGroups = 30,
		    spaceForLabels   = 150;

		// Zip the series data together (first values, second values, etc.)
		var zippedData = [];
		for (var i=0; i<dataSet.length; i++) {
		   zippedData.push(dataSet[i].value); 
		}

		// Color scale
		//var color = d3.scale.category20();
		//var color = d3.scale.ordinal().range(segments);
	
		var chartHeight = barHeight * zippedData.length + (gapBetweenGroups * 2);

		var x = d3.scale.linear()
		    .domain([0, d3.max(zippedData)])
		    .range([0, chartWidth]);

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
			.append("svg:a")
			.attr("xlink:href", function(d) {
				return "#"; //d.data.linkURL;
			}).on('onclick',  function() {return false;})
			.append("g")
		    .attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
			.attr("index_value", function(d, i) { return "index-" + i; })
			
			.attr("data_id", function(d, i) { return dataSet[i].id })
			.attr("data_label", function(d, i) { return dataSet[i].label })
			.attr("data_value", function(d, i) { return dataSet[i].value })
			.attr("data_percentage", function(d, i) { return dataSet[i].percentage })
			.attr("data_linkURL", function(d, i) { return dataSet[i].linkURL })
			
			.attr("class", "link-url-naviga-dettaglio")
			
			.on('mouseover', synchronizedMouseOver)
			.on("mouseout", synchronizedMouseOut)
			
			.attr("transform", function(d, i) {
		      return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/dataSet.length))) + ")";
		    });

		// Create rectangles of the correct width
		bar.append("rect")
		    .attr("fill", function(d,i) { return colorScale(i % dataSet.length); })
			.attr("class", function(d, i) { return "bar histogram-" + histogramName + "-arc-index-" + i; })
		    .attr("width", x)
		    .attr("height", barHeight - 10);
		
		/*
		// Add text label in bar
		bar.append("text")
		   .attr("x", function(d) { return x(d) - 3; })
		   .attr("y", barHeight / 2)
		   .attr("dy", ".35em")
		   .text(function(d, i) { return nFormatter(dataSet[i].value); });
		*/
		
		// Draw labels
		bar.append("text")
		   .attr("class", function(d, i) { return "label histogram-" + histogramName + "-label-index-" + i; })
		   .attr("x", function(d) { return - 10; })
		   .attr("y", (barHeight-10) / 2)
		   .attr("dy", ".25em")
		   .style("fill", textColor)
		   .style("font-size", "1.8em")
		   .text(function(d, i) { 
		        //return dataSet[i].label; 
				return nFormatter(dataSet[i].value);
			});
	};
	
	function drawLegend(divLegend, legendName, dataSet){
		
		var widthTotal = 50;
		var heightLegend = 25; 
		var gapBetweenGroups = 25;
		var legendBulletOffset = 30;
		var legendTextOffset = 20;
		var textVerticalSpace = 20;
		
		var canvas = d3.select(divLegend).append("svg:svg")
		    .attr("width", 800)
		    .attr("height", gapBetweenGroups + (dataSet.length * heightLegend) );
			
		
//		var colorScale = d3.scale.ordinal().range(segments);
		
		// Plot the bullet circles...
		canvas.selectAll("circle")
		.data(dataSet)
		.enter()
		.append("svg:a")
		.attr("xlink:href", function(d) {
			return "#"; //d.data.linkURL;
		}).on('onclick',  function() {return false;})
		.append("svg:circle") // Append circle elements
		
		.attr("cx", widthTotal)
		.attr("cy", function(d, i) { 
			//return ( gapBetweenGroups + (i * (heightTotal / dataSet.length)) + ((heightTotal / dataSet.length)/2) ) ; 
			return gapBetweenGroups + (heightLegend * i);
		})
		
		.attr("data_id", function(d, i) { return dataSet[i].id })
		.attr("data_label", function(d, i) { return dataSet[i].label })
		.attr("data_value", function(d, i) { return dataSet[i].value })
		.attr("data_percentage", function(d, i) { return dataSet[i].percentage })
		.attr("data_linkURL", function(d, i) { return dataSet[i].linkURL })
			
		.attr("stroke-width", ".5").style("fill", function(d, i) { return colorScale(i); }) // Bullet fill color
		.attr("r", 5).attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-" + i; })
		.attr("class", function(d, i) { return "link-url-naviga-dettaglio legend-" + legendName + "-legendBullet-index-" + i; })
		.on('mouseover', synchronizedMouseOver)
		.on("mouseout", synchronizedMouseOut);
		
		
		// Create hyper linked text at right that acts as label key...
        canvas.selectAll("a.legend_link")
		.data(dataSet) // Instruct to bind dataSet to text elements
		.enter().append("svg:a") // Append legend elements
		.attr("xlink:href", function(d) { 
			//return d.linkURL; 
			return "#";
		})
		.on('mouseclick', function(){ return false;})
		.append("text")
		.attr("text-anchor", "center")
		.attr("x", widthTotal + 20)
		.attr("y", function(d, i) { 
			//return ( gapBetweenGroups + (i * (heightTotal / dataSet.length)) + ((heightTotal / dataSet.length)/2) ) ; 
			return gapBetweenGroups + (heightLegend*i);
		})
		.attr("dx", 0)
        .attr("dy", "5px") // Controls padding to place text in alignment with bullets
        .text(function(d) { return (d.label).trunc(36, true); })
        .attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
        .attr("index_value", function(d, i) { return "index-" + i; })
        
        .attr("data_id", function(d, i) { return dataSet[i].id })
		.attr("data_label", function(d, i) { return dataSet[i].label })
		.attr("data_value", function(d, i) { return dataSet[i].value })
		.attr("data_percentage", function(d, i) { return dataSet[i].percentage })
		.attr("data_linkURL", function(d, i) { return dataSet[i].linkURL })
			
        .attr("class", function(d, i) { return "link-url-naviga-dettaglio label legend-" + legendName + "-legendText-index-" + i; })
		
        .style("fill", textColor)
        .style("font-size", "1.8em")
        .on('mouseover', synchronizedMouseOver)
        .on("mouseout", synchronizedMouseOut)
        .append("title")
        .text(function(d) { return d.label; });
		
	};
	
	function createEventMouseOver(divElementRef){
		
		var SVGRoot = d3.select( divElementRef ).select('svg')[0][0];
		var pt = SVGRoot.createSVGPoint();
		SVGRoot.addEventListener('mousemove',
					function(evt){
						var loc = cursorPointHistogram(evt)
						var tooltip = d3.select("#tooltip-pie-chart");
						tooltip.style("left", (evt.pageX - 100) + "px");
						tooltip.style("top", (loc.y) + "px");
					}, false);
		/// Get point in global SVG space
		function cursorPointHistogram(evt){
			pt.x = evt.clientX; 
			pt.y = evt.clientY;
			return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
		}
	};
	
	var margin = 10;
	var outerRadius = 130;
	
	pie = drawPie("Pie1", dataSet1, ".pie_chart_1", "segments", margin, outerRadius, 0, 0);
	
	legend = drawLegend("#chartLegend", "Legend1", dataSet1);
	
	bar = drawBar(".chart-bar", "Histogram1", dataSet1);
	
//	createEventMouseOver('#pie_chart_1');
//	createEventMouseOver('#chartLegend');
//	createEventMouseOver('#histogramChart');

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
	       				
	       				$( ".link-url-naviga-dettaglio" ).click(function() {
	       					if(!selezionabile){
	       						var arc = d3.select(this);
		       					var data_linkURL = arc.attr("data_linkURL");
		       					$( ".naviga-form" ).attr("action", data_linkURL);
		       					$( ".naviga-form" ).submit();
	       					}
	       				});
	       				
	      			}
		 		});
	      	}
		 });
	});
	
</script>
	

