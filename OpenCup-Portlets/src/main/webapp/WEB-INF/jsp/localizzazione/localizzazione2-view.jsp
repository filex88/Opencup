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

<h2>Per regioni dell'area geografica: ${selectedTerritoryName} </h2>

<div id="italybymacroareas" style="text-align: left"></div>

<script>


var territorioSelezionato="${selectedTerritory}";

var namespace = "<portlet:namespace/>";
namespace = namespace.substring(1,namespace.length - 1);

var selectedTerritoryValues=null;

var baseColor1="rgb(209,226,242)";
var baseColor2="rgb(114,178,215)";
var baseColor3="rgb(8,64,131)";



AUI().use('liferay-portlet-url', 'aui-base', 'aui-io-deprecated', function( A ) {
    
    var resourceURL = Liferay.PortletURL.createResourceURL();
    resourceURL.setPortletId(namespace);
    resourceURL.setResourceId("selectedTerritoryResource");
    resourceURL.setParameter("selectedTerritory", territorioSelezionato);
    resourceURL.setCopyCurrentRenderParameters(true);
    console.log(resourceURL.toString());
     
    A.io.request( resourceURL.toString(), {
        dataType: 'json',
        on: {
            	success: function(event, id, obj) 
            	{
            	selectedTerritoryValues = this.get('responseData');
            	drawRegionsByTerritory();
           		}
        	}
    	});
	});

function drawRegionsByTerritory(){

	var calculated_json=selectedTerritoryValues.selectedTerritoryValues;
	
	// min mid, max valori calcolati 
	var minData=d3.min(calculated_json,function(d){
		return d.localizationValue.toFixed(2);
	})
	
	var midData=d3.mean(calculated_json,function(d){
		return d.localizationValue.toFixed(2);
	})

	var maxData=d3.max(calculated_json,function(d){
		return d.localizationValue.toFixed(2);
	})

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
    	.attr("class", "selectiontip");

	d3.json("/OpenCup-Theme-theme/js/italy_macroareas.json", function(error, it) {
	
		var territory_topojson=it.objects.sub.geometries;
	
		// unisco i dati
		for (var i=0;i < territory_topojson.length;i++){
			var label_toposon=territory_topojson[i].properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
			for (var j=0;j<calculated_json.length;j++){
				if (label_toposon==calculated_json[j].localizationLabel){
					var valore=calculated_json[j].localizationValue.toFixed(2);
					var link=calculated_json[j].detailUrl;
					territory_topojson[i].properties.VALORE=valore;
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
    	if (d.properties.TERR==territorioSelezionato){
     		return d.properties.TERR;}
     	else {
     		return "notIncluded";}
    
  	 	})
    	.attr("d",path)
    	.attr ("id",function(d) { return d.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-"); })
    	.style("fill",function(d){
    		return color(d.properties.VALORE);
    	})
    	.on("click", function(d){
    		window.location = d.properties.LINK;
   		 })
    	.on("mouseover",function(a){
    		var idSelected=a.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    		svg.selectAll("#"+idSelected)
    		.style("fill","#FFFFCC");
    		 var mouse = d3.mouse(svg.node()).map( function(d) { return parseInt(d); } );
    		 tooltip.classed("nascosto", false)
        	.attr("style", "left:"+(mouse[0]+25)+"px;top:"+(mouse[1]+height-50)+"px")
         	.html('<p><strong>REGIONE: </strong>'+a.properties.REGIONE+'</p>'
         	  +'<p><strong>VALORE: </strong>&euro;&nbsp;'+a.properties.VALORE+'</p>');
   		 	})
    	.on("mouseout",function(a){
    		var idSelected=a.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    		svg.selectAll("#"+idSelected)
    		.style("fill",function(d){
    			return color(d.properties.VALORE);
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
		// sposta all'angolo, poi scalo, poi sposta al centro
		selection.attr("transform", "translate("+xSecondTranslation+","+ySecondTranslation+")  scale("+maxScale+")  translate("+xFirstTranslation+","+yFirstTranslation+") " );
	
		// porporzione del bordo
		var newBorder=border/maxScale;
		selection.style("stroke-width",newBorder);
	
  
	});
}

</script>
