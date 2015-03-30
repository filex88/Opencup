// clear breadcrumb
	var fatherUl=d3.selectAll("li.first").node().parentNode;
		d3.select(fatherUl).insert("li",":first-child")
		.attr("style","padding: 0 5px;color:#fcfcfc;")
		.text("Sei in: ");
		
		d3.selectAll(".divider").each(function(){
			var c=d3.select(this).node().parentNode;
			d3.select(c)
				.style("font-weight","bold")
				.append("i")
				.attr("class","icon-caret-right")
				.attr("style","padding: 0 5px; color:#fcfcfc;");
			d3.select(c).select("span").remove();
		});
		d3.selectAll("li.current-parent.breadcrumb-truncate").selectAll("i").remove();
		d3.selectAll("li.active.last.breadcrumb-truncate").remove();

	// elimina paginazione
	d3.selectAll(".taglib-search-iterator-page-iterator-bottom").remove();

// d3
	
	var selectedDimension='volume';
	d3.select("#volumeLabel").select("input").classed("active",true);
	

	var baseColor1="rgb(209,226,242)";
	var baseColor2="rgb(114,178,215)";
	var baseColor3="rgb(8,64,131)";

	drawRegionsByTerritory(selectedDimension,jsonResultLocalizzazione,territorioSelezionato,areeGeoBack);
	d3.select("#volumeLabel")
		.on("click",function(d){
			d3.select("#italybymacroareas").select("svg").remove();
			d3.selectAll("div.selectiontip").remove();
			selectedDimension='volume';
			d3.selectAll("input").attr("class",null);
			d3.select("#volumeLabel").select("input").classed("active",true);
			drawRegionsByTerritory(selectedDimension,jsonResultLocalizzazione,territorioSelezionato,areeGeoBack);
		});
		
	d3.select("#costoLabel")
		.on("click",function(d){
			d3.select("#italybymacroareas").select("svg").remove();
			d3.selectAll("div.selectiontip").remove();
			selectedDimension='costo';
			d3.selectAll("input").attr("class",null);
			d3.select("#costoLabel").select("input").classed("active",true);
			drawRegionsByTerritory(selectedDimension,jsonResultLocalizzazione,territorioSelezionato,areeGeoBack);
		});
						
	d3.select("#importoLabel")
		.on("click",function(d){
			d3.select("#italybymacroareas").select("svg").remove();
			d3.selectAll("div.selectiontip").remove();
			selectedDimension='importo';
			d3.selectAll("input").attr("class",null);
			d3.select("#importoLabel").select("input").classed("active",true);
			drawRegionsByTerritory(selectedDimension,jsonResultLocalizzazione,territorioSelezionato,areeGeoBack);
		});
	            	

	function drawRegionsByTerritory(dimension,calculated_json,territorioSel,areeGeoLink){
		
		// handling og back links
		d3.select("#eliminaFiltroArea")
			.on("click",function(d){
				window.location = areeGeoLink;
			});
		
		
		
		// min mid, max valori calcolati 
		var minData=d3.min(calculated_json,function(d){
			if( dimension=='volume'){
				return d.volumeValue;
			}
			else if(dimension=='costo'){
				return d.costoValue;
			}
			else{
				return d.importoValue;
			}
		});

		var midData=d3.mean(calculated_json,function(d){
			var result=null;
			if( dimension=='volume'){
				return d.volumeValue;
			}
			else if(dimension=='costo'){
				return d.costoValue;
			}
			else{
				return d.importoValue;
			}
		})

		var maxData=d3.max(calculated_json,function(d){
			var result=null;
			if( dimension=='volume'){
				return d.volumeValue;
			}
			else if(dimension=='costo'){
				return d.costoValue;
			}
			else{
				return d.importoValue;
			}
			
		})

		// scala colori in base a valori calcolati
		var color = d3.scale.linear().domain([minData,midData,maxData])
		.range([baseColor1,baseColor2,baseColor3]);

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
	       	
	  	var tooltip = d3.select("#italybymacroareas").append("div")
	    	.attr("class", "selectiontip nascosto");

		d3.json("/OpenCup-Theme-theme/js/italy_macroareas.json", function(error, it) {
		
			var territory_topojson=it.objects.sub.geometries;
		
			// unisco i dati
			for (var i=0;i < territory_topojson.length;i++){
				var label_toposon=territory_topojson[i].properties.COD_REG;
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
	     	.attr("id","territorioSel");
	        
	     	svg.selectAll("g").selectAll("path").data(topojson.feature(it, it.objects.sub).features)
	     	.enter()
	     	.append("path")
	     	.attr("class",function(d) { 
	    	if (d.properties.TERR==territorioSel){
	     		return d.properties.TERR;}
	     	else {
	     		return "notIncluded";}
	    
	  	 	})
	    	.attr("d",path)
	    	.attr ("id",function(d) { return territorioSel+"_"+d.properties.COD_REG; })
	    	.style("fill",function(d){
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
	    	.on("click", function(d){
	    		if (typeof d.properties.LINK!=="undefined")
	    		{
	    			window.location = d.properties.LINK;
	    		}else{
	    			window.location="#italybymacroareas";
	    		}
	   		 })
	    	.on("mouseover",function(a){
	    		var idSelected=a.properties.COD_REG;
	    		svg.selectAll("#"+territorioSel+"_"+idSelected)
	    		.style("fill","#F08C00");
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
					}
					else if(dimension=='costo'){
						labelToShow="COSTO:";
						if (typeof a.properties.VALORE_COSTO!=="undefined"){
							valueToShow='&euro;&nbsp;'+formatEuro(a.properties.VALORE_COSTO);
						}else{
							valueToShow='&euro;&nbsp;0,00';
						}
	 				
					}
					else{
						labelToShow="IMPORTO FINANZIATO:";
						if (typeof a.properties.VALORE_IMPORTO!=="undefined"){
							valueToShow='&euro;&nbsp;'+formatEuro(a.properties.VALORE_IMPORTO);
						}else{
							valueToShow='&euro;&nbsp;0,00';
						}
	 				
					}
	    		 
	    		 tooltip.classed("nascosto", false)
	        	.attr("style", "left:"+(mouse[0]+10)+"px;top:"+(mouse[1]-40)+"px")
	         	.html('<p><strong>REGIONE: </strong>'+a.properties.REGIONE+'</p>'
	         	 + '<p><strong>'+labelToShow+' </strong>'+valueToShow+'</p>');
	   		 	})
	    	.on("mouseout",function(a){
	    		var idSelected=territorioSel+"_"+a.properties.COD_REG;
	    		svg.selectAll("#"+idSelected)
	    		.style("fill",function(d){
	    			if( dimension=='volume'){
	    				if (typeof d.properties.VALORE_VOLUME!=="undefined"){
	    					return color(d.properties.VALORE_VOLUME);
	    				}else{
	    					return "#fff";
	    				}
						
					}
					else if(dimension=='costo'){
						if (typeof d.properties.VALORE_COSTO!=="undefined"){
							return color(d.properties.VALORE_COSTO);
						}else{
							return "#fff";
						}
						
					}
					else{
						if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
							return color(d.properties.VALORE_IMPORTO);
						}else{
							return "#fff";
						}
					}
	    		})
	    	 	tooltip.classed("nascosto", true)
	    	});
	    
	    	svg.selectAll(".notIncluded").remove();
	    
	    	var selection = d3.select("#territorioSel");
	    
	    	// trovo coordinate quadrato che circonda la selezione  
	    	var currentX=selection[0][0].getBBox().x;
	    	var currentY=selection[0][0].getBBox().y;
	   		var currentW=selection[0][0].getBBox().width;
	    	var currentH=selection[0][0].getBBox().height;
	    
	    	// calcolo spostamenti per portare il riferimento su angolo superiore sx (ossia sottraggo ascissa e ordinata)
	    	var xFirstTranslation=-currentX;
	    	var yFirstTranslation=-currentY;
	    
	   		 var maxScale=2.4;
	    	// dopo aver scalato sposto al centro il g contenitore
	    
	    	var xSecondTranslation=(width/2)-(currentW*(maxScale/2));
	    	var ySecondTranslation=(height/2)-(currentH*(maxScale/2));
	   
	    	// 2.4 =Max fattore di scalea della pag 	
			// sposta all'angolo, poi scalo, poi sposta al centro e tiro su
			selection.attr("transform", "translate("+xSecondTranslation+","+10+")  scale("+maxScale+")  translate("+xFirstTranslation+","+yFirstTranslation+") " );
		
			// porporzione del bordo
			var newBorder=border/maxScale;
			selection.style("stroke-width",newBorder);
			
			d3.selectAll("#dimensions").attr("style","margin-top:"+(-ySecondTranslation*2)+10+"px");
			
			d3.selectAll("#tabRisultati").selectAll("td.table-cell.first").selectAll("a")
			.on("mouseover",function(a){
				
				var valoreClasse=d3.select(this).attr("class").replace("link-url-naviga-selezione","").trim();
				var regione=d3.selectAll("#"+valoreClasse)
					.style("fill","#F08C00");
						
				var labelToShow=null;
				var valueToShow=null;
				
				var xPosition=null;
				var yPosition=null;
				
				var nomeRegione=null;
				var position1=null;
				var position2=null;
				regione.each(function (d){
					// cicla, ma prende solo label e value dell'ultima
					var element=d3.select(this);
					
						
					var rectPositionLeft=element[0][0].getBoundingClientRect().left;
					var rectPositionTop=element[0][0].getBoundingClientRect().top;
					var rectPositionW=element[0][0].getBoundingClientRect().width;
					var rectPositionH=element[0][0].getBoundingClientRect().height;
					
					position1=Math.floor(rectPositionLeft-(rectPositionW/2.0));
					position2=Math.floor(rectPositionTop+(rectPositionH/2.0));
					
					nomeRegione=d.properties.REGIONE;
					if( dimension=='volume' ){
						labelToShow="VOLUME:";
						if (typeof d.properties.VALORE_VOLUME !== "undefined"){
							valueToShow=formatInteger(d.properties.VALORE_VOLUME);
						}else{
							valueToShow='0';
						}
					}
					else if(dimension=='costo'){
						labelToShow="COSTO:";
						if (typeof d.properties.VALORE_COSTO!=="undefined"){
							valueToShow='&euro;&nbsp;'+formatEuro(d.properties.VALORE_COSTO);
						}else{
							valueToShow='&euro;&nbsp;0,00';
						}
						
					}
					else{
						labelToShow="IMPORTO FINANZIATO:";
						if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
							valueToShow='&euro;&nbsp;'+formatEuro(d.properties.VALORE_IMPORTO);
						}else{
							valueToShow='&euro;&nbsp;0,00';
						}
						
					}
					
					
				});
				
				
				 tooltip.classed("nascosto", false)
	        	.attr("style", "left:"+position1+"px;top:"+position2+"px")
	         	.html('<p><strong>REGIONE: </strong>'+nomeRegione+'</p>'
	         	 + '<p><strong>'+labelToShow+' </strong>'+valueToShow+'</p>');
				
				
				})
				.on("mouseout",function(a){
					var valoreClasse=d3.select(this).attr("class").replace("link-url-naviga-selezione","").trim();
					d3.selectAll("#"+valoreClasse)
					.style("fill",function(d){
		    			if( dimension=='volume'){
		    				if (typeof d.properties.VALORE_VOLUME!=="undefined"){
		    					return color(d.properties.VALORE_VOLUME);
		    				}else{
		    					return "#fff";
		    				}
							
						}
						else if(dimension=='costo'){
							if (typeof d.properties.VALORE_COSTO!=="undefined"){
								return color(d.properties.VALORE_COSTO);
							}else{
								return "#fff";
							}
							
						}
						else{
							if (typeof d.properties.VALORE_IMPORTO!=="undefined"){
								return color(d.properties.VALORE_IMPORTO);
							}else{
								return "#fff";
							}
						}
		    		});
					tooltip.classed("nascosto", true)
					
				});
			
	  
		});
	}

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
		