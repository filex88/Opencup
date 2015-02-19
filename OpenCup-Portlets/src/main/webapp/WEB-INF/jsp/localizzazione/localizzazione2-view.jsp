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

<h1>Per Regioni del territorio: ${selectedTerritoryName} </h1>

<div id="italybymacroareas" style="text-align: left"></div>

<script>


var territorioSelezionato="${selectedTerritory}";
console.log(territorioSelezionato);



var namespace = "<portlet:namespace/>";
namespace = namespace.substring(1,namespace.length - 1);

var selectedTerritoryValues=null;

var colours = ["#F7F7F7", "#E8E8E8", "#D6D6D6", "#BFBFBF", "#B0B0B0", "#969696",
               "#787878", "#666666", "#555555", "#383838", "#1C1C1C"];

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

	var width = 500,
    	height = 500,
    	border=1
    	bordercolor='black',
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
    		var reg_selected=d.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    		return colours[calcolaIndice(selectedTerritoryValues.selectedTerritoryValues,reg_selected)];
    	})
    	.on("click", function(d){
    		var reg_selected=d.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    		var detailUrl=getUrlDetail(selectedTerritoryValues.selectedTerritoryValues,reg_selected);

    		window.location = detailUrl;
   		 })
    	.on("mouseover",function(a){
    		var idSelected=a.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    		svg.selectAll("#"+idSelected)
    		.style("fill","#FFFFCC");
    		
    		var mouse = d3.mouse(svg.node()).map( function(d) { return parseInt(d); } );
    		var valore_regione=getLocalizationValue(selectedTerritoryValues.selectedTerritoryValues,idSelected);
    	
    	tooltip.classed("nascosto", false)
        .attr("style", "left:"+(mouse[0]+25)+"px;top:"+(mouse[1]+height)+"px")
         .html('<p><strong>REGIONE: </strong>'+a.properties.REGIONE+'</p>'
         	  +'<p><strong>VALORE REGIONE: </strong>&euro;&nbsp;'+valore_regione+'</p>');
   		 })
    	.on("mouseout",function(a){
    		var idSelected=a.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    		svg.selectAll("#"+idSelected)
    		.style("fill",function(d){
    			var reg_selected=d.properties.REGIONE.replace(/'/g,"_").replace(/\s/g,"-");
    			return colours[calcolaIndice(selectedTerritoryValues.selectedTerritoryValues,reg_selected)];
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

function getLocalizationValue(localization_array,localization_label){
	var ritorno=null;
	  localization_array.forEach(function(d) {
	  	if (d.localizationLabel==localization_label){
	  		ritorno= (d.localizationValue).toFixed(2);
	  	}
    });
    return ritorno;
}

function getUrlDetail(localization_array,localization_label){
	var returnUrl=null;
	  localization_array.forEach(function(d) {
	  	if (d.localizationLabel==localization_label){
	  		returnUrl= d.detailUrl;
	  	}
    });
    console.log(returnUrl);
    return returnUrl;
}
function calcolaIndice(localization_array,localization_label){
	var ritorno=null;
	  localization_array.forEach(function(d) {
	  	if (d.localizationLabel==localization_label){
	  		ritorno= localization_array.indexOf(d);
	  	}
    });
    return ritorno;
}

</script>
