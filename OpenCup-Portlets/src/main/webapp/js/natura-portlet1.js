AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated',
		'datatype-number',
		function( A ) {
			
			var pie = null;
			var aggregate = null;
			var tipoAggregazione = null;
			
			A.all('#VOLUME').on('click', function(){
				loadPie("VOLUME");
			});
			
			
			A.all('#COSTO').on('click', function(){
				loadPie("COSTO");
			});
			
			
			A.all('#IMPORTO').on('click', function(){
				loadPie("IMPORTO");
			});
			
   			loadPie("VOLUME");
   			
			function loadPie(pattern){

				A.all('#IMPORTO').replaceClass('active', '');
				A.all('#COSTO').replaceClass('active', '');
				A.all('#VOLUME').replaceClass('active', '');
				
				A.all("#"+pattern).replaceClass('btn-default', 'btn-default active');
				
				tipoAggregazione = pattern;
				
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId("naturaportlet1_WAR_OpenCupPortletsportlet");
				resourceURL.setResourceId("naturaPortlet1");
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
	           				//console.log("aggregate = " + aggregate.naturaPortlet1);
	           				
	           				// Select the node(s) using a css selector string
	           			    A.one('#pieChart').empty();
	           				drawPie();
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
						"content": aggregate.naturaPortlet1
					},
					"labels": {
						"outer": {
							"format": "percentage",
							"pieDistance": 5
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
							//console.log("mouseover:", info);							
							//Update the tooltip position and value
							var tooltip = d3.select("#tooltip");

							tooltip.select("#label").text(info.data.label);
							tooltip.select("#labelvalue").text(tipoAggregazione);

							if( tipoAggregazione == "VOLUME" ){
								tooltip.select("#value").text(formattaIntero(info.data.value));
								tooltip.select("#umvalue").classed("hidden", true);
							}else{
								tooltip.select("#value").text(formattaImporto(info.data.value));
								tooltip.select("#umvalue").classed("hidden", false);
							}
							
							//Show the tooltip
							tooltip.classed("hidden", false); 
							tooltip.transition().duration(500).style("opacity", 100);

							
						},
						onMouseoutSegment: function(info) {
							//console.log("mouseout:", info);
							//Hide the tooltip
							var tooltip = d3.select("#tooltip");
						    tooltip.transition().duration(500).style("opacity", 0);   
						    tooltip.classed("hidden", true);
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
			}
			
			var tooltip = d3.select("#tooltip");
			d3.select("#pieChart").on("mouseover", function(d) {  
				tooltip.style("left", d3.event.pageX + "px");
				tooltip.style("top", d3.event.pageY + "px");
			});
   			
		}
	);