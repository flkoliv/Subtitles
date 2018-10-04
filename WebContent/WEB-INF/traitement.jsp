<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Traduction</title>
<link rel="stylesheet" media="screen" type="text/css" title="css" href="/Subtitles/ressources/style.css" />

</head>
<body>
<header>
<h1>Traduction du film <br/>"${film.nom}"<br/>de ${film.langueOriginale } vers ${film.langueTraduction }</h1>
</header>


<form method="post" action="">
	<div>
		<input type="submit"name="submit" id="submit" value="Sauvegarder">
		<input type="button"name="annuler" id="annuler" value="Annuler">
	</div>
	<c:forEach items="${ film.phrases }" var="phrase" varStatus="status">
		<fieldset>
       		<legend>Phrase N°<c:out value="${phrase.numero}"/></legend>
			<p>Début : <c:out value="${phrase.minutageDebut}" /> Fin : <c:out value="${phrase.minutageFin}" /></p>
			
			<div>
				<textarea name="txtOriginal" id="txtOriginal" cols="40" rows="2" disabled><c:out value="${phrase.texteOriginal}" /> </textarea>
				<textarea name="txtTraduit<c:out value="${ phrase.numero }" />" id="txtTraduit<c:out value="${ phrase.numero }" />" cols="40" rows="2"><c:out value="${phrase.texteTraduit}" /></textarea>
			</div>
			
		</fieldset>
		
	</c:forEach>
	
</form>

</body>
</html>