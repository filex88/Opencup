
var svg = null;

AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated',
		'datatype-number',
		function( A ) {
			
			var pie = null;
			var aggregate = null;
			var tipoAggregazione = null;
			
			A.all('.natura-sel-btn').each(
				function() {
					this.on('click', function(){
						var misura = this.getAttribute("data-natura");
						loadPie(misura, this);
					});
				});
			
			var el_div_pie_chart = A.one('#pieChart');

			if(el_div_pie_chart) {
				loadPie("VOLUME", A.one('.natura-sel-btn'));
			}
   			
			function loadPie(pattern, button){
				
				A.one('#pieChart').empty();
				
				A.all('.natura-sel-btn').replaceClass('active', '');

				button.replaceClass('btn-default', 'btn-default active');
				
				tipoAggregazione = pattern;
				
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(namespace);
				resourceURL.setResourceId("aggregati4Pie");
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setCopyCurrentRenderParameters(true);
				
				//console.log("resourceUrl = " + resourceURL.toString());
				
				pie = null;
				
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				aggregate = this.get('responseData');
	           				
	           				//console.log("aggregate = " + aggregate);
	           				//console.log("aggregate.aggregati4Pie = " + aggregate.aggregati4Pie);
	           				
	           			    if(aggregate.aggregati4Pie!=null && aggregate.aggregati4Pie!=""){
	           			    	drawPie();
	           			    }else{
	           			  	        A.one(".pieChartEmpty").setStyles({
	           			    		display: 'block'
	           			    	});
	           			    	A.one(".pieChartToolBar").setStyles({
	           			    		display: 'none'
	           			    	});
	           			    }
	           			   
	           			},
	           			failure: function (e) {
	           				var message = this.get('responseData');
	           				alert("Ajax Error : "+message);	
	           			}
	       			}
	   			});
			}
			
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
			
			function drawPie(){
				
				pie = new d3pie("pieChart", {
					"header": {
						"title": {
							"text": "Distribuzione percentuale",
							"fontSize": 22,
							"font": "helvetica"
						},
						"subtitle": {
							"color": "#999999",
							"fontSize": 12,
							"font": "open sans"
						},
						"titleSubtitlePadding": 5
					},
					"footer": {
						"color": "#999999",
						"fontSize": 10,
						"font": "open sans",
						"location": "bottom-left"
					},

					"data": {
						"sortOrder": "value-desc",
						"content": aggregate.aggregati4Pie
					},
					"labels": {
						"outer": {
							"format": "percentage",
							"pieDistance": 15
						},
						"inner": {
							"format": "none",
							"hideWhenLessThanPercentage": 3
						},
						"mainLabel": {
							"fontSize": 11
						},
						"percentage": {
							"color": "#333333",
							"decimalPlaces": 0
						},
						"value": {
							"color": "#adadad",
							"fontSize": 11
						},
						"lines": {
							"enabled": true,
							"style": "straight"
						}
					},
					"tooltips": {
						"enabled": false,
						"type": "placeholder",
						"string": "{label} Costo:{value}&euro; {percentage}%",
						"styles": {
							"fadeInSpeed": 390,
							"color": "#ffffff",
							"borderRadius": 10,
							"fontSize": 12,
							"padding": 10
						}
					},
					"effects": {
						"pullOutSegmentOnClick": {
							"effect": "linear",
							"speed": 400,
							"size": 8
						}
					},
					"size": {
						"canvasHeight": 450,
						"canvasWidth": 450
					},
					"callbacks": {
						onMouseoverSegment: function(info) {
							//console.log(info);							
							//Update the tooltip position and value
							var tooltip = d3.select("#tooltip-natura-portlet1");

							tooltip.select("#label-tooltip-natura-portlet1").text(info.data.label);
							tooltip.select("#labelvalue-tooltip-natura-portlet1").text(tipoAggregazione);

							if( tipoAggregazione == "VOLUME" ){
								tooltip.select("#value-tooltip-natura-portlet1").text(formattaIntero(info.data.value));
								tooltip.select("#umvalue-tooltip-natura-portlet1").classed("hidden", true);
							}else{
								tooltip.select("#value-tooltip-natura-portlet1").text(formattaImporto(info.data.value));
								tooltip.select("#umvalue-tooltip-natura-portlet1").classed("hidden", false);
							}
							
							//Show the tooltip
							tooltip.classed("hidden", false); 
							tooltip.transition().duration(500).style("opacity", 100);
							
						},
						onMouseoutSegment: function(info) {
							//console.log("mouseout:", info);
							//Hide the tooltip
							var tooltip = d3.select("#tooltip-natura-portlet1");
						    tooltip.transition().duration(500).style("opacity", 0);   
						    tooltip.classed("hidden", true);
						},
						onClickSegment: function(info) {
							//console.log(info);
							var myFormEmpty = A.one(".formEmptyNatura1");
							var linkURL = info.data.linkURL;
							myFormEmpty.setAttribute('action', linkURL);
							myFormEmpty.submit();
						}
					}
					//,
					//"misc": {
					//	"gradient": {
					//		"enabled": true,
					//		"percentage": 100
					//	}
					//}
				});
				
				
				var SVGRoot = document.getElementsByTagName("svg")[0];
				var pt = SVGRoot.createSVGPoint();
				
				SVGRoot.addEventListener('mousemove',
						function(evt){
//					console.log('mousemove');
					var loc = cursorPoint(evt)
					var tooltip = d3.select("#tooltip-natura-portlet1");
					tooltip.style("left", loc.x + "px");
					tooltip.style("top", loc.y + "px");
				},
				false);


				//// Get point in global SVG space
				function cursorPoint(evt){
//					console.log('cursorPoint');
					pt.x = evt.clientX; 
					pt.y = evt.clientY;
					return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
				}
				
				
			}
			
//			d3.select("#pieChart").on("mousemove", function(d) {  
//				var tooltip = d3.select("#tooltip-natura-portlet1");
//				tooltip.style("left", d3.event.pageX + "px");
//				tooltip.style("top", d3.event.pageY + "px");
//			});
   			
		}
	);

//console.log('Leggo l elemento svg');
//console.log(svg);
//console.log('CARICAMENTO');
//svg = document.querySelector('svg');
//console.log('svg: ' + svg);
//var pt   = svg.createSVGPoint();
//
//console.log('pt: ' + pt);
//
//svg.addEventListener('mousemove',
//		function(evt){
//			console.log('mousemove');
//			var loc = cursorPoint(evt);
//			console.log(loc.x + ' / ' + loc.y);
//		},
//		false);
//
//
//// Get point in global SVG space
//function cursorPoint(evt){
//	console.log('cursorPoint');
//	pt.x = evt.clientX; 
//	pt.y = evt.clientY;
//	return pt.matrixTransform(svg.getScreenCTM().inverse());
//}