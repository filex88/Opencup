<%@ page contentType="text/html" isELIgnored="false"%>

<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

PORTLET 2

<h2>Subscriber Portlet</h2>
<%=renderRequest.getParameter("firstName")!=null?"First Name : "+renderRequest.getParameter("firstName") : ""%><BR>
<%=renderRequest.getParameter("lastName")!=null?"Last Name : "+renderRequest.getParameter("lastName") : ""%>


