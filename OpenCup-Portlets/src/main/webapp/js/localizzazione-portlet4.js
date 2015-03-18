
var selectedDimension='volume';
d3.select("#volumeLabel").select("input").classed("active",true);

var baseColor1="rgb(209,226,242)";
var baseColor2="rgb(114,178,215)";
var baseColor3="rgb(8,64,131)";

drawAllRegions(selectedDimension,jsonResultLocalizzazione);
d3.select("#volumeLabel")
	.on("click",function(d){
		d3.select("#italybymacroareas").select("svg").remove();
		d3.selectAll("div.selectiontip").remove();
		selectedDimension='volume';
		d3.selectAll("input").attr("class",null);
		d3.select("#volumeLabel").select("input").classed("active",true);
		drawAllRegions(selectedDimension,jsonResultLocalizzazione);
	});
	
d3.select("#costoLabel")
	.on("click",function(d){
		d3.select("#italybymacroareas").select("svg").remove();
		d3.selectAll("div.selectiontip").remove();
		selectedDimension='costo';
		d3.selectAll("input").attr("class",null);
		d3.select("#costoLabel").select("input").classed("active",true);
		drawAllRegions(selectedDimension,jsonResultLocalizzazione);
	});
					
d3.select("#importoLabel")
	.on("click",function(d){
		d3.select("#italybymacroareas").select("svg").remove();
		d3.selectAll("div.selectiontip").remove();
		selectedDimension='importo';
		d3.selectAll("input").attr("class",null);
		d3.select("#importoLabel").select("input").classed("active",true);
		drawAllRegions(selectedDimension,jsonResultLocalizzazione);
	});
            	

function drawAllRegions(dimension,calculated_json){
	

	// elimina paginazione
	d3.selectAll(".taglib-search-iterator-page-iterator-bottom").remove();
	
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
   		.attr("border",border)
   		.style("stroke-width",border);
   	
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
     		return d.properties.TERR;
  	 	})
    	.attr("d",path)
    	.attr ("id",function(d) { return d.properties.TERR+"_"+d.properties.COD_REG; })
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
    		window.location = d.properties.LINK;
   		 })
    	.on("mouseover",function(a){
    		var idSelected=a.properties.COD_REG;
    		svg.selectAll("#"+a.properties.TERR+"_"+idSelected)
    		.style("fill","#FFFFCC");
    		 var mouse = d3.mouse(d3.select(".portlet-body").node()).map( function(d) { return parseInt(d); } );
    		
    		 var labelToShow=null;
    		 var valueToShow=null;
    			if( dimension=='volume'){
    				labelToShow="VOLUME:";
    				valueToShow=a.properties.VALORE_VOLUME;
				}
				else if(dimension=='costo'){
					labelToShow="COSTO:";
    				valueToShow='&euro;&nbsp;'+a.properties.VALORE_COSTO.toFixed(2);
				}
				else{
					labelToShow="IMPORTO FINANZIATO:";
    				valueToShow='&euro;&nbsp;'+a.properties.VALORE_IMPORTO.toFixed(2);
				}
    		 
    		 tooltip.classed("nascosto", false)
        	.attr("style", "left:"+(mouse[0]+10)+"px;top:"+(mouse[1]-40)+"px")
         	.html('<p><strong>REGIONE: </strong>'+a.properties.REGIONE+'</p>'
         	 + '<p><strong>'+labelToShow+' </strong>'+valueToShow+'</p>');
   		 	})
    	.on("mouseout",function(a){
    		var idSelected=a.properties.TERR+"_"+a.properties.COD_REG;
    		svg.selectAll("#"+idSelected)
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
    	 	tooltip.classed("nascosto", true)
    	});
     	
     	var territorio=d3.selectAll("#territorioSel");
     	territorio.style("stroke-width",border);
	});
}