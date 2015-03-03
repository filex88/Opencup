
var svg = null;

AUI().use(
		'liferay-portlet-url', 
		'aui-base', 
		'aui-io-deprecated',
		'datatype-number',
		function( A ) {
			
			
			A.all('.pulisci').each(
				function() {
					this.on('click', function(){
						for (i = 0; i < 5; i++) { 
							var dataParent = this.getAttribute("data-parent"+i);
							if( dataParent ){
								var target = "#"+namespaceRicerca4js+dataParent;
						    	var elemento = A.one(target);
								if( i==1 ){
									elemento.val(0);
								}else{
									elemento.val(-1);
								}
							}
						}
				    	submitForm();
					});
				});

			function submitForm(){

				//Rimuovo le parentesi quadre dalla lista degli id degli anni
				var target = "#"+namespaceRicerca4js+"idAnnoAggregatos";
				var elemento = A.one(target);
				var nuovoVal = elemento.val().replace("[","").replace("]","");
				elemento.val(nuovoVal);
				
				// submit
				var form = A.one(".filtri-form");
				form.submit();
			}	
			
			
			
			var myFormEmpty = A.one(".formEmptyClassificazione1");
			
			A.all('.link-url-naviga-dettaglio').each(
					function() {
						this.on('click', function(){
							var linkURL = this.getAttribute("data-url");
							myFormEmpty.setAttribute('action', linkURL);
							myFormEmpty.submit();
						});
					});
			
			
			var pieClassificazione = null;
			var aggregate = null;
			var tipoAggregazione = null;
			
			A.all('.classificazione-sel-btn').each(
				function() {
					this.on('click', function(){
						var misura = this.getAttribute("data-classificazione");
						loadPie(misura, this);
					});
				});
			
			var el_div_pie_chart = A.one('#pieChartClassificazione');

			if(el_div_pie_chart) {
				loadPie("VOLUME", A.one('.classificazione-sel-btn'));
			}
   			
			function loadPie(pattern, button){
				
				A.one('#pieChartClassificazione').empty();
				
				A.all('.classificazione-sel-btn').replaceClass('active', '');

				button.replaceClass('btn-default', 'btn-default active');
				
				tipoAggregazione = pattern;
				
				var resourceURL = Liferay.PortletURL.createResourceURL();
				resourceURL.setPortletId(namespace);
				resourceURL.setResourceId("aggregati4Pie");
				resourceURL.setParameter("pattern", pattern);
				resourceURL.setCopyCurrentRenderParameters(true);

				pieClassificazione = null;
				
				A.io.request( resourceURL.toString(), {
	   				method: 'GET',
	       			dataType: 'json',
	       			on: {
	           			success: function(event, id, obj) {
	           				aggregate = this.get('responseData');
	           				
	           			    if(aggregate.aggregati4Pie!=null && aggregate.aggregati4Pie!=""){
	           			    	drawPie();
	           			    }else{
	           			  	        A.one(".pieChartClassificazioneEmpty").setStyles({
	           			    		display: 'block'
	           			    	});
	           			    	A.one(".pieChartClassificazioneToolBar").setStyles({
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
				
				pieClassificazione = new d3pie("pieChartClassificazione", {
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
							//Update the tooltip position and value
							var tooltip = d3.select("#tooltip-classificazione-portlet1");

							tooltip.select("#label-tooltip-classificazione-portlet1").text(info.data.label);
							tooltip.select("#labelvalue-tooltip-classificazione-portlet1").text(tipoAggregazione);

							if( tipoAggregazione == "VOLUME" ){
								tooltip.select("#value-tooltip-classificazione-portlet1").text(formattaIntero(info.data.value));
								tooltip.select("#umvalue-tooltip-classificazione-portlet1").classed("hidden", true);
							}else{
								tooltip.select("#value-tooltip-classificazione-portlet1").text(formattaImporto(info.data.value));
								tooltip.select("#umvalue-tooltip-classificazione-portlet1").classed("hidden", false);
							}
							
							//Show the tooltip
							tooltip.classed("hidden", false); 
							tooltip.transition().duration(500).style("opacity", 100);
							
						},
						onMouseoutSegment: function(info) {
							//Hide the tooltip
							var tooltip = d3.select("#tooltip-classificazione-portlet1");
						    tooltip.transition().duration(500).style("opacity", 0);   
						    tooltip.classed("hidden", true);
						},
						onClickSegment: function(info) {
							var linkURL = info.data.linkURL;
							myFormEmpty.setAttribute('action', linkURL);
							myFormEmpty.submit();
						}
					}
				});
				
				
				var SVGRoot = document.getElementsByTagName("svg")[0];
				var pt = SVGRoot.createSVGPoint();
				
				SVGRoot.addEventListener('mousemove',
						function(evt){
					var loc = cursorPoint(evt)
					var tooltip = d3.select("#tooltip-classificazione-portlet1");
					tooltip.style("left", loc.x + "px");
					tooltip.style("top", loc.y + "px");
				},
				false);


				//// Get point in global SVG space
				function cursorPoint(evt){
					pt.x = evt.clientX; 
					pt.y = evt.clientY;
					return pt.matrixTransform(SVGRoot.getScreenCTM().inverse());
				}
				
				
			}
   			
		}
	);