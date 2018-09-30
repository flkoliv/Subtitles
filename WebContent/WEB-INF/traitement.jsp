<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Traduction du film ${film.nom}</h1>


<form method="post" action="">
	<input type="submit"name="submit" id="submit" value="sauvegarder">
	<c:forEach items="${ film.phrases }" var="phrase" varStatus="status">
		<fieldset>
       		<legend>Phrase N°<c:out value="${phrase.numero}"/></legend>
			<p>Début : <c:out value="${phrase.minutageDebut}" /> Fin : <c:out value="${phrase.minutageFin}" /></p>
			
			<textarea name="txtOriginal" id="txtOriginal" cols="40" rows="2" disabled><c:out value="${phrase.texteOriginal}" /> </textarea>
			<textarea name="txtTraduit<c:out value="${ phrase.numero }" />" id="txtTraduit<c:out value="${ phrase.numero }" />" cols="40" rows="2"><c:out value="${phrase.texteTraduit}" /> </textarea>
			
		</fieldset>
		
	</c:forEach>

</form>

</body>
</html>