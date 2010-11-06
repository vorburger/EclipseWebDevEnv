<%@page import="ch.vorburger.demo.ext.SomethingElse"%>
<%@page import="ch.vorburger.demo.core.Something"%>
<html>
<body>
<h2>Hello World!</h2>

Bla, <%= Something.message() %>.

<p><%= SomethingElse.message() %>

</body>
</html>
