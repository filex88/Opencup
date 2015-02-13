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

<h1>Per Territorio</h1>

<div id="italybymacroareas" style="text-align: left"></div>

<script>

var namespace = "<portlet:namespace/>";
namespace = namespace.substring(1,namespace.length - 1);

var allTerritoryValues=null;

AUI().use('liferay-portlet-url', 'aui-base', 'aui-io-deprecated', function( A ) {
    
    var resourceURL = Liferay.PortletURL.createResourceURL();
    resourceURL.setPortletId(namespace);
    resourceURL.setResourceId("allTerritoryResource");
    resourceURL.setCopyCurrentRenderParameters(true);
    //console.log(resourceURL.toString());
     
    A.io.request( resourceURL.toString(), {
        dataType: 'json',
        on: {
            	success: function(event, id, obj) 
            	{
            	allTerritoryValues = this.get('responseData');
           		 }
        	}
    	});
	});

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
        
     svg.selectAll("path").data(topojson.feature(it, it.objects.sub).features)
     .enter().append("path")
    .attr("class",function(d) { return d.properties.TERR; })
    .attr("d",path)
    .attr ("id",function(d) { return d.properties.ID_REG_TER; })
    .on("click", function(d){
    	
    	//alert(getLocalizationValue(allTerritoryValues.allTerritoryValues,"ALL_"+d.properties.TERR));
    })
    .on("mouseover",function(a){
    	svg.selectAll("path")
    	.style("fill",function (d){
    		if (d.properties.TERR==a.properties.TERR){
    		return "#FFFFCC"}
    	});
    	
    	var mouse = d3.mouse(svg.node()).map( function(d) { return parseInt(d); } );
    	var valore_terr=getLocalizationValue(allTerritoryValues.allTerritoryValues,"ALL_"+a.properties.TERR);
    	
    	tooltip.classed("nascosto", false)
        .attr("style", "left:"+(mouse[0]+25)+"px;top:"+(mouse[1]+height)+"px")
         .html('<p><strong>TERRITORIO: </strong>'+a.properties.TERR_DESC+'</p>'
         +'<p><strong>VALORE TERRITORIO: </strong>&euro;&nbsp;'+valore_terr+'</p>'
         +'<p><strong>VALORE REGIONE: </strong>'+a.properties.REGIONE+'</p>');
    })
    .on("mouseout",function(a){
    	svg.selectAll("."+a.properties.TERR)
    	.attr("style",null);
    	 tooltip.classed("nascosto", true)
    });
  
});


function getLocalizationValue(localization_array,localization_label){
	var ritorno=null;
	  localization_array.forEach(function(d) {
	  	if (d.localizationLabel==localization_label){
	  		console.log(d.localizationValue);
	  		ritorno= (d.localizationValue).toFixed(2);
	  	}
    });
    return ritorno;
}


</script>


