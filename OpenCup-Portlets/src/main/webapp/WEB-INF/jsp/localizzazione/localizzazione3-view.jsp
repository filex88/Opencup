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

<h2>Per province della regione: ${regionName} </h2>

<div id="italybymacroareas"></div>
<div id="dimensions">
	<ul>
		<li id="volumeLabel">
			<input type="button" value="VOLUME"></input>
		</li>
		<li id="costoLabel">
			<input type="button" value="COSTO"></input>
		</li>
		<li id="importoLabel">
			<input type="button" value="FINANZIATO"></input>
		</li>
	</ul>
</div>
<script>


var regioneSelezionata="${selectedRegion}";
console.log(regioneSelezionata);

var namespace = "<portlet:namespace/>";
namespace = namespace.substring(1,namespace.length - 1);

var selectedRegionValues=null;

var selectedDimension='volume';
d3.select("#volumeLabel").select("input").classed("active",true);

var baseColor1="rgb(209,226,242)";
var baseColor2="rgb(114,178,215)";
var baseColor3="rgb(8,64,131)";

AUI().use('liferay-portlet-url', 'aui-base', 'aui-io-deprecated', function( A ) {
    
    var resourceURL = Liferay.PortletURL.createResourceURL();
    resourceURL.setPortletId(namespace);
    resourceURL.setResourceId("selectedRegionResource");
    resourceURL.setParameter("selectedRegion", regioneSelezionata);
    resourceURL.setCopyCurrentRenderParameters(true);
    console.log(resourceURL.toString());
     
    A.io.request( resourceURL.toString(), {
        dataType: 'json',
        on: {
            	success: function(event, id, obj) 
            	{
            	selectedRegionValues = this.get('responseData');
            	drawProvinceByRegione(selectedDimension,selectedRegionValues.selectedRegionValues,regioneSelezionata);
            	
            	d3.select("#volumeLabel")
					.on("click",function(d){
						d3.select("#italybymacroareas").select("svg").remove();
						d3.selectAll("div.selectiontip").remove();
						selectedDimension='volume';
						d3.selectAll("input").attr("class",null);
						d3.select("#volumeLabel").select("input").classed("active",true);
						drawProvinceByRegione(selectedDimension,selectedRegionValues.selectedRegionValues,regioneSelezionata);
					});
	
					d3.select("#costoLabel")
					.on("click",function(d){
						d3.select("#italybymacroareas").select("svg").remove();
						d3.selectAll("div.selectiontip").remove();
						selectedDimension='costo';
						d3.selectAll("input").attr("class",null);
						d3.select("#costoLabel").select("input").classed("active",true);
						drawProvinceByRegione(selectedDimension,selectedRegionValues.selectedRegionValues,regioneSelezionata);
					});
					
					d3.select("#importoLabel")
					.on("click",function(d){
						d3.select("#italybymacroareas").select("svg").remove();
						d3.selectAll("div.selectiontip").remove();
						selectedDimension='importo';
						d3.selectAll("input").attr("class",null);
						d3.select("#importoLabel").select("input").classed("active",true);
						drawProvinceByRegione(selectedDimension,selectedRegionValues.selectedRegionValues,regioneSelezionata);
					});
            	
            	
           		}
        	}
    	});
	});


function drawProvinceByRegione(dimension,calculated_json,regioneSel){

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
	console.log(minData);

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
	console.log(midData);

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
	console.log(maxData);

	// scala colori in base a valori calcolati
	var color = d3.scale.linear().domain([minData,midData,maxData])
	.range([baseColor1,baseColor2,baseColor3]);


	var width = 500,
   		height = 500,
   		border=1
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

	d3.json("/OpenCup-Theme-theme/js/all_province.json", function(error, it) {
	
		var territory_topojson=it.objects.sub.geometries;
	
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
        
     
        
     	svg.append("g").
     	attr("id","regioneSel");
        
     	svg.selectAll("g").selectAll("path").data(topojson.feature(it, it.objects.sub).features)
     	.enter()
     	.append("path")
     	.attr("class",function(d) { 
     	var selectedRegion=d.properties.COD_REG;
    		if (selectedRegion==regioneSelezionata){
     			return d.properties.TERR;}
     		else {
     			return "notIncluded";}
    
  	 	})
    	.attr("d",path)
    	.attr ("id",function(d) { return d.properties.TR_REG_PRO; })
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
    	.on("mouseover",function(a){
    		svg.selectAll("#"+a.properties.TR_REG_PRO)
    		.style("fill","#FFFFCC");
    		
    		var mouse = d3.mouse(svg.node()).map( function(d) { return parseInt(d); } );
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
    		
    		var nome_prov=a.properties.NOME.toUpperCase();
    		tooltip.classed("nascosto", false)
        	.attr("style", "left:"+(mouse[0]+25)+"px;top:"+(mouse[1]+height-50)+"px")
         	.html('<p><strong>PROVINCIA: </strong>'+nome_prov+'</p>'
         	+'<p><strong>'+labelToShow+' </strong>'+valueToShow+'</p>');
    	})
    	.on("mouseout",function(a){
    		svg.selectAll("#"+a.properties.TR_REG_PRO)
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
    
    	svg.selectAll(".notIncluded").remove();
    
    	var selection = d3.select("#regioneSel");
    
    	// trovo coordinate quadrato che circonda la selezione  
    	var currentX=selection[0][0].getBBox().x;
    	var currentY=selection[0][0].getBBox().y;
    	var currentW=selection[0][0].getBBox().width;
    	var currentH=selection[0][0].getBBox().height;
    
    	// calcolo spostamenti per portare il riferimento su angolo superiore sx (ossia sottraggo ascissa e ordinata)
    	var xFirstTranslation=-currentX;
    	var yFirstTranslation=-currentY;
    
    
    	// dopo aver scalato sposto al centro il g contenitore
    	var maxScale=4;
    
    	var xSecondTranslation=(width/2)-(currentW*(maxScale/2));
   	 	var ySecondTranslation=(height/2)-(currentH*(maxScale/2));
    
    
    	//lo shape del veneto risulta piu' lungo e deve essere centrato manualmente veneto =150y x60
    
    	if (regioneSel=='05'){
    		xSecondTranslation+=60;
    		ySecondTranslation+=150;
    	}
    	
		// sposta all'angolo, poi quintuplica, poi sposta al centro
		selection.attr("transform", "translate("+xSecondTranslation+","+ySecondTranslation+")  scale("+maxScale+")  translate("+xFirstTranslation+","+yFirstTranslation+") " );
  		var newBorder=border/maxScale;
		selection.style("stroke-width",newBorder);
  
		});

	}

</script>