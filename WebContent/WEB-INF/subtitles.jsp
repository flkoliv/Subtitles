<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" media="screen" type="text/css" title="css" href="/Subtitles/ressources/style.css" />
<title>Subtitles Translate</title>
</head>
<body>
<h1>SubTranslate</h1>
	<form method="post" action="">
    	<fieldset class="choix" id="choix">
    	<legend>Choix film :</legend>
        <select name="film" id="listeFilms">
        	<c:forEach items="${ listFilms }" var="film" varStatus="status">
    			<option value="<c:out value="${ film.nom }" />"><c:out value="${ film.nom }" /></option>
			</c:forEach>
        </select>
        <select name="langueDestination" title="Langue de destination">
        	<c:forEach items="${ listLangues }" var="langue" varStatus="status">
    			
    				<option value="<c:out value="${ langue }" />"><c:out value="${ langue }" /></option>
    			
			</c:forEach>
        </select>
        <input type="submit" name="submit" id="submit" value="Traduire" class="bouton"/>
        <input type="submit" name="submit" id="submit" value="Télécharger" class="bouton"/>
        </fieldset>
	</form>
	<form method="post" action="" enctype="multipart/form-data">
    	<fieldset class="choix">
    		<legend>Nouveau film :</legend>
        	<div>
        	<input type="text" name="nomFilm" id="nomFilm" placeholder="nom film" required>
        	
        	
        	<select name="langue" title="Langue des sous-titres du fichier">
        	<c:forEach items="${ listLangues }" var="langue" varStatus="status">
    			
    				<option value="<c:out value="${ langue }" />"><c:out value="${ langue }" /></option>
    			
			</c:forEach>
        </select>
        	<input type="submit" name="submit" id="submit" value="Upload" class="bouton"/>
        	</div>
        	<input type="file" name="fichierFilm" id="fichierFilm" accept=".srt"  class="inputfile" required/>
        	<label for="fichierFilm"> <span>Choisissez un fichier</span></label>
        </fieldset>
	</form>
	<div id="message">
		<p id="msg">${ !empty message ? message: '' }</p>
	</div>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"
		type="text/javascript"></script>
	<script type="text/javascript" src="/Subtitles/ressources/script.js"></script>
</body>
</html> 