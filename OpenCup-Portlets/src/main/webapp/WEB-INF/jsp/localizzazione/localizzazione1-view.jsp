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

<h1>Per area geografica</h1>

<div id="italybymacroareas" style="text-align: left"></div>

<script>

var namespace = "<portlet:namespace/>";
namespace = namespace.substring(1,namespace.length - 1);

var allTerritoryValues=null;

var baseColor1="rgb(209,226,242)";

var baseColor2="rgb(114,178,215)";

var baseColor3="rgb(8,64,131)";

AUI().use('liferay-portlet-url', 'aui-base', 'aui-io-deprecated', function( A ) {
    
    var resourceURL = Liferay.PortletURL.createResourceURL();
    resourceURL.setPortletId(namespace);
    resourceURL.setResourceId("allTerritoryResource");
    resourceURL.setCopyCurrentRenderParameters(true);
    console.log(resourceURL.toString());
     
    A.io.request( resourceURL.toString(), {
        dataType: 'json',
        on: {
            	success: function(event, id, obj) 
            	{
            		allTerritoryValues = this.get('responseData');
           		 	drawGraphTerritori();
           		}
        	}
    	});
	});



function drawGraphTerritori(){

	var calculated_json=allTerritoryValues.allTerritoryValues;
	
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
			var label_toposon=territory_topojson[i].properties.TERR;
			for (var j=0;j<calculated_json.length;j++){
				if (label_toposon==calculated_json[j].localizationLabel){
					var valore=calculated_json[j].localizationValue.toFixed(2);
					var link=calculated_json[j].detailUrl;
					territory_topojson[i].properties.VALORE=valore;
					territory_topojson[i].properties.LINK=link+"&nomeTerr="+territory_topojson[i].properties.TERR_DESC;
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
     
                
     svg.selectAll("path").data(topojson.feature(it, it.objects.sub).features)
     	.enter().append("path")
    	.attr("class",function(d) { return d.properties.TERR; })
    	.attr("d",path)
    	.attr ("id",function(d) { 
    		return d.properties.ID_REG_TER; })
    	.style("fill", function(d){
    		return color(d.properties.VALORE);
    	})  
    	.on("click", function(d){
    		window.location = d.properties.LINK;
   		})
    	.on("mouseover",function(a){
    		svg.selectAll("path")
    		.style("fill",function (d){
    		if (d.properties.TERR==a.properties.TERR){
    			return "#FFFFCC";}
    		else{
    			return color(d.properties.VALORE); 
    			}
    		});
    		var mouse = d3.mouse(svg.node()).map( function(d) { 
    			return parseInt(d); 
    		});
    	
    		tooltip.classed("nascosto", false)
        	 .attr("style", "left:"+(mouse[0]+25)+"px;top:"+(mouse[1]+height-50)+"px")
        	 .html('<p><strong>TERRITORIO: </strong>'+a.properties.TERR_DESC+'</p>'
        	 +'<p><strong>VALORE TERRITORIO: </strong>&euro;&nbsp;'+a.properties.VALORE+'</p>');
    	})
    	.on("mouseout",function(a){
    		svg.selectAll("."+a.properties.TERR)
    		.style("fill",function(d){
    			return color(d.properties.VALORE);
    		});
    		
    	 	tooltip.classed("nascosto", true)
    	});
  
	});
}

</script>


