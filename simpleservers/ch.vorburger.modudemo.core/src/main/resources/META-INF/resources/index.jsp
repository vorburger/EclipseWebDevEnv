<%@page import="ch.vorburger.demo.core.services.Something"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="strutsHtml" uri="http://struts.apache.org/tags-html" %>
<html>
<body>
<h2>Hello World!</h2>

Bla, <%= Something.message() %>.

<p>Core Std JSP Tag Library test: <c:out value="text from std tablib" /></p>

<p>Struts HTML Tag Library test: <strutsHtml:button value="I am a button" property="huh"/></p>

</body>
</html>
