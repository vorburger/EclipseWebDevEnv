<%@page import="p.SomethingElse"%>
<%@page import="pp.Something"%>
<html>
<body>
<h2>Hello World!</h2>

Bla, <%= Something.message() %>.

<p><%= SomethingElse.message() %>

</body>
</html>
