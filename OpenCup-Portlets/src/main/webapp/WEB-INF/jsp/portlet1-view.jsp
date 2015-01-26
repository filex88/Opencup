<%@ page contentType="text/html" isELIgnored="false"%>

<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

PORTLET 1

<form method="POST"
	action='<portlet:actionURL> <portlet:param name="action" value="PublishEvent"/> </portlet:actionURL>'>
	<table>
		<tr>
			<td>First Name</td>
			<td><input type="text" name="<portlet:namespace />firstName" value=""></td>
		</tr>
		<tr>
			<td>Last Name</td>
			<td><input type="text" name="<portlet:namespace />lastName" value=""></td>
		</tr>
		<tr>
			<td><input type="submit" name="Submit" ></td>
		</tr>
	</table>
</form>

