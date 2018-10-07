<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Traduction</title>
<link rel="stylesheet" media="screen" type="text/css" title="css"
	href="/Subtitles/ressources/style.css" />
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">

</head>
<body>
	<header>
		<h1>
			Traduction des sous-titres du film <br />"${film.nom}"<br />de ${film.langueOriginale }
			vers ${film.langueTraduction }
		</h1>
	</header>

	
	<form method="post" action="">
		<div class="corps">
		<div class="menu">
		
			<input type="submit" name="submit" id="submit" value="Sauvegarder" class="bouton">
			<input type="submit" name="submit" id="submit" value="Annuler" class="bouton">
			<input type="button" name="retour" id="return-to-top" value="En haut" class="bouton">
			
		
		</div>
		<div id="formulaire">
			<c:forEach items="${ film.phrases }" var="phrase" varStatus="status">
				<fieldset>
					<legend>
						Phrase N°
						<c:out value="${phrase.numero}" />
					</legend>
					<p>
						Début :
						<c:out value="${phrase.minutageDebut}" />
						 - Fin :
						<c:out value="${phrase.minutageFin}" />
					</p>

					<div>
						<textarea name="txtOriginal" id="txtOriginal" cols="45" rows="2"
							disabled><c:out value="${phrase.texteOriginal}" /> </textarea>
						<textarea name='txtTraduit<c:out value="${ phrase.numero }" />'
							id='txtTraduit<c:out value="${ phrase.numero }" />' cols="45"
							rows="2"><c:out value="${phrase.texteTraduit}" /></textarea>
					</div>

				</fieldset>

			</c:forEach>
		</div>
		</div>
	</form>
	
	<a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"
		type="text/javascript"></script>
	<script type="text/javascript" src="/Subtitles/ressources/script.js"></script>
	
</body>
</html>