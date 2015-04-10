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

<style type="text/css">
	svg {
		font: 10px sans-serif;
		display: block;
	}

	div.div_RootBody {
		position: relative;	
		border: 2px solid White;
		border-radius: 7px;
		background: WhiteSmoke;
		font: normal 14px Arial;
		font-family: Arial, Helvetica, sans-serif;
		color: Black;
		padding: 0px 1em;
		text-align: left;
	}
</style>


<div>
	
	<!-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
	 -- TORTA --		
	 ---------------------------------------------------------------------------------------------------------------------------------------------------------- -->
	<div class="div_pie_chart_1" style="height: 397px">
	
		<a name="pie-chart-portlet"></a>

		<div>
			<div class="span6 chart-div">
				<div class="div_pie_chart" id="pie_chart_1">
        			<div class="chart" id="pieChart" style="text-align: center"></div>
				</div>
			</div>
			<div class="span6 chart-div">
				<svg class="chart"></svg>
			</div>
		</div>
          
        <div id="tooltip-pie-chart" class="tooltip-pie-chart hidden">
        	<p><span id="label-tooltip-pie-chart"></span></p>
			<p>
				<strong><span>Percentuale</span>:&nbsp;</strong><span id="percentuale-tooltip-pie-chart"></span>
			</p>
			<p>
				<strong><span id="labelvalue-tooltip-pie-chart"></span>:&nbsp;</strong>
				<span id="value-tooltip-pie-chart"></span>
				<span class="hidden" id="umvalue-tooltip-pie-chart">&euro;</span>
			</p>
		</div>
	
		<div class="alert alert-info pieChartEmpty" id="pieChartEmpty" style="display: none"> Nessun dato trovato per la selezione fatta </div>

	</div>
				
</div>

<script type="text/javascript">
	
	var dataSet1 = ${aggregati4Pie};
	var tipoAggregazione = '${pattern}';
	
	var A = null;
	AUI().use(
		'aui-base', 
		function( Y ) {
			A = Y;
	});
	
	function formattaImporto(valore){
		return A.Number.format(valore,{
			prefix: "",
	        thousandsSeparator: ".",
	        decimalSeparator: ",",
	        decimalPlaces: 3,
	        suffix: ""
		});
	}
				
	function formattaIntero(valore){
		return A.Number.format(valore,{
			prefix: "",
	        thousandsSeparator: ".",
	        decimalSeparator: ",",
	        decimalPlaces: 0,
	        suffix: ""
		});
	}
	
	function drawPie( pieName, dataSet, selectString, colors, margin, outerRadius, divPieChart, innerRadius, sortArcs ) {
		
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
	
		var segments = [ "#b2c6ff", "#9eb5fc", "#90abfb", "#81a0fa", "#7597fb", "#678dfb", "#5a84fa", "#507cfb", "#4472fb", "#3869f9", "#2f62f2", "#275aea", "#2254e2", "#1b4bd8", "#1745ce", "#1240c3", "#0d39b8", "#0932a3" ];
			
		// Color Scale Handling...
		var colorScale = d3.scale.category20c();
		
		switch (colors) {
			case "colorScale10":
				colorScale = d3.scale.category10();
				break;
			case "colorScale20":
				colorScale = d3.scale.category20();
				break;
			case "colorScale20b":
				colorScale = d3.scale.category20b();
				break;
			case "colorScale20c":
				colorScale = d3.scale.category20c();
				break;
			case "segments":
				colorScale = d3.scale.ordinal().range(segments);
				break;
			default:
				colorScale = d3.scale.category20c();
		};
	
		var wigthDivPieChart = d3.select(divPieChart).node().getBoundingClientRect().width			
		var pieWidthTotal = outerRadius * 2;
		
		var marginX = margin;
		if( wigthDivPieChart > pieWidthTotal ){
			marginX = (wigthDivPieChart - pieWidthTotal);
		}
			
		var pieCenterX = outerRadius + margin / 2;
		var pieCenterY = outerRadius + margin / 2;
		var legendBulletOffset = 30;
		var legendVerticalOffset = outerRadius - margin;
		var legendTextOffset = 20;
		var textVerticalSpace = 20;
		
		var canvasWidth = wigthDivPieChart;
		var canvasHeight = 0;
		
		var pieDrivenHeight = outerRadius * 2 + margin * 2;
		var legendTextDrivenHeight = (dataSet.length * textVerticalSpace) + margin * 2;
			
		// Autoadjust Canvas Height
		if (pieDrivenHeight >= legendTextDrivenHeight) {
			canvasHeight = pieDrivenHeight;
		} else {
			canvasHeight = legendTextDrivenHeight;
		}

		var x = d3.scale.linear().domain([ 0, d3.max(dataSet, function(d) {	return d.value; }) ]).rangeRound([ 0, pieWidthTotal ]);
		var y = d3.scale.linear().domain([ 0, dataSet.length ]).range([ 0, (dataSet.length * 20) ]);

		var synchronizedMouseOver = 
			function(info) {
				var arc = d3.select(this);
				
				console.log(info);
				
				var indexValue = arc.attr("index_value");

				var arcSelector = "." + "pie-" + pieName + "-arc-" + indexValue;
				var selectedArc = d3.selectAll(arcSelector);
				selectedArc.style("fill", "Maroon");
				
				var bulletSelector = "." + "pie-" + pieName + "-legendBullet-" + indexValue;
				var selectedLegendBullet = d3.selectAll(bulletSelector);
				selectedLegendBullet.style("fill", "Maroon");

				var textSelector = "." + "pie-" + pieName + "-legendText-" + indexValue;
				var selectedLegendText = d3.selectAll(textSelector);
				selectedLegendText.style("fill", "Maroon");
				
			
				var tooltip = d3.select("#tooltip-pie-chart");
				//Update the tooltip position and value
				
				if( info.data ){
					tooltip.select("#label-tooltip-pie-chart").text(info.data.label);
					tooltip.select("#labelvalue-tooltip-pie-chart").text(tipoAggregazione);
					tooltip.select("#percentuale-tooltip-pie-chart").text((info.data.percentage) + "%");
					if( tipoAggregazione == "VOLUME" ){
						tooltip.select("#value-tooltip-pie-chart").text(formattaIntero(info.data.value));
						tooltip.select("#umvalue-tooltip-pie-chart").classed("hidden", true);
					}else{
						tooltip.select("#value-tooltip-pie-chart").text(formattaImporto(info.data.value));
						tooltip.select("#umvalue-tooltip-pie-chart").classed("hidden", false);
					}
				}else{
					tooltip.select("#label-tooltip-pie-chart").text(info.label);
					tooltip.select("#labelvalue-tooltip-pie-chart").text(tipoAggregazione);
					tooltip.select("#percentuale-tooltip-pie-chart").text((info.percentage) + "%");
					if( tipoAggregazione == "VOLUME" ){
						tooltip.select("#value-tooltip-pie-chart").text(formattaIntero(info.value));
						tooltip.select("#umvalue-tooltip-pie-chart").classed("hidden", true);
					}else{
						tooltip.select("#value-tooltip-pie-chart").text(formattaImporto(info.value));
						tooltip.select("#umvalue-tooltip-pie-chart").classed("hidden", false);
					}
				}

				//Show the tooltip
				tooltip.classed("hidden", false); 
				tooltip.transition().duration(500).style("opacity", 100);
				
		};
	
		var synchronizedMouseOut = function() {
			var arc = d3.select(this);
			var indexValue = arc.attr("index_value");

			var arcSelector = "." + "pie-" + pieName + "-arc-" + indexValue;
			var selectedArc = d3.selectAll(arcSelector);
			var colorValue = selectedArc.attr("color_value");
			selectedArc.style("fill", colorValue);

			var bulletSelector = "." + "pie-" + pieName + "-legendBullet-"
					+ indexValue;
			var selectedLegendBullet = d3.selectAll(bulletSelector);
			var colorValue = selectedLegendBullet.attr("color_value");
			selectedLegendBullet.style("fill", colorValue);

			var textSelector = "." + "pie-" + pieName + "-legendText-" + indexValue;
			var selectedLegendText = d3.selectAll(textSelector);
			selectedLegendText.style("fill", "Blue");
			
			//Hide the tooltip
			var tooltip = d3.select("#tooltip-pie-chart");
		    tooltip.transition().duration(500).style("opacity", 0);   
		    tooltip.classed("hidden", true);
		};
	
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
		var canvas = d3.select(selectString).append("svg:svg") //create the SVG element inside the <body>
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
		.enter().append("svg:a").attr("xlink:href", function(d) {
			return d.data.linkURL;
		}).append("svg:g").attr("class", "slice") //allow us to style things in the slices (like text)
		// Set the color for each slice to be chosen from the color function defined above
		// This creates the actual SVG path using the associated data (pie) with the arc drawing function
		.style("stroke", "White").attr("d", arc);
	
		arcs.append("svg:path")
		// Set the color for each slice to be chosen from the color function defined above
		// This creates the actual SVG path using the associated data (pie) with the arc drawing function
		.attr("fill", function(d, i) { return colorScale(i); })
		.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-" + i; })
		.attr("data_value", function(d) { return d.data.value })
		.attr("data_label", function(d) { return d.data.label })
		.attr("data_percentage", function(d) { return d.data.percentage })
		.attr("data-pos", function(d, i) { return i })
		.attr("class", function(d, i) { return "pie-" + pieName + "-arc-index-" + i; })
		.style("stroke", "White")
		.attr("d", arc)
		.on('mouseover', synchronizedMouseOver)
		.on("mouseout", synchronizedMouseOut)
		.transition()
		.ease("linear")
		//.ease("bounce")
		.duration(2000)
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
		.style("fill", "White")
		.style("font", "normal 18px Arial")
		.text(function(d) { return (d.data.percentage) + "%"; });
	
		// Computes the angle of an arc, converting from radians to degrees.
		function angle(d) {
			var a = (d.startAngle + d.endAngle) * 90 / Math.PI - 90;
			return a > 90 ? a - 180 : a;
		}
	
		// Plot the bullet circles...
		canvas.selectAll("circle")
		.data(dataSet)
		.enter()
		.append("svg:circle") // Append circle elements
		.attr("cx", pieWidthTotal + legendBulletOffset).attr("cy", function(d, i) {	return i * textVerticalSpace - legendVerticalOffset; })
		.attr("stroke-width", ".5").style("fill", function(d, i) { return colorScale(i); }) // Bullet fill color
		.attr("r", 5).attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-" + i; })
		.attr("class", function(d, i) { return "pie-" + pieName + "-legendBullet-index-" + i; })
		.on('mouseover', synchronizedMouseOver)
		.on("mouseout", synchronizedMouseOut);
	
		// Create hyper linked text at right that acts as label key...
		canvas.selectAll("a.legend_link").data(dataSet) // Instruct to bind dataSet to text elements
		.enter().append("svg:a") // Append legend elements
		.attr("xlink:href", function(d) { return d.linkURL; })
		.append("text").attr("text-anchor", "center")
		.attr("x", pieWidthTotal + legendBulletOffset + legendTextOffset)
		//.attr("y", function(d, i) { return legendOffset + i*20 - 10; })
		//.attr("cy", function(d, i) {    return i*textVerticalSpace - legendVerticalOffset; } )
		.attr("y", function(d, i) { return i * textVerticalSpace - legendVerticalOffset; })
		.attr("dx", 0).attr("dy", "5px") // Controls padding to place text in alignment with bullets
		.text(function(d) { return d.label; })
		.attr("color_value", function(d, i) { return colorScale(i); }) // Bar fill color...
		.attr("index_value", function(d, i) { return "index-" + i; })
		.attr("class", function(d, i) { return "pie-" + pieName + "-legendText-index-" + i; })
		.style("fill", "Blue")
		.style("font", "normal 1.5em Arial")
		.on('mouseover', synchronizedMouseOver)
		.on("mouseout", synchronizedMouseOut);
		
		var SVGRoot = d3.select('#pieChart').select('svg')[0][0];
		var pt = SVGRoot.createSVGPoint();
		SVGRoot.addEventListener('mousemove',
				function(evt){
					var loc = cursorPoint(evt)
					var tooltip = d3.select("#tooltip-pie-chart");
					tooltip.style("left", (evt.pageX - 100) + "px");
					tooltip.style("top", (loc.y) + "px");
				}, false);
		
		//// Get point in global SVG space
		function cursorPoint(evt){
			pt.x = evt.clientX; 
			pt.y = evt.clientY;
			return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
		}
		
	};
	
	function drawBar(){
		
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
							        	
					        			
							    		/*
							    		var xPos = parseFloat(d3.select(this).attr('x')) / 2 + width / 2;
							    		var yPos = parseFloat(d3.select(this).attr('y')) + yScale.rangeBand() / 2;
							    		*/
							    		
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
		
	};
	
	
	drawPie("Pie1", dataSet1, "#pie_chart_1 .chart", "segments", 10, 155, ".div_pie_chart_1", 0, 0);
	drawBar();
</script>

