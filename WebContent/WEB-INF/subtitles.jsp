<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Subtitles Translate</title>
</head>
<body>
<h1>SubTranslate</h1>
	<form method="post" action="">
    	<label for="film">Choix film : </label>
        <select name="film">
        	<c:forEach items="${ listFilms }" var="film" varStatus="status">
    			
    			<option value="<c:out value="${ film.nom }" />"><c:out value="${ film.nom }" /></option>
			</c:forEach>
        </select>
        <select name="langueDestination">
        	<c:forEach items="${ listLangues }" var="langue" varStatus="status">
    			
    				<option value="<c:out value="${ langue }" />"><c:out value="${ langue }" /></option>
    			
			</c:forEach>
        </select>
        <input type="submit" name="submit" id="submit" value="choixFichier"/>
	</form>
	<form method="post" action="" enctype="multipart/form-data">
    	<label for="nom">Nouveau film : </label>
        <input type="text" name="nomFilm" id="nomFilm" placeholder="nom film">
        <input type="file" name="fichierFilm" id="fichierFilm" accept=".srt" placeholder="fichier film"/>
        <input type="text" name="langue" id="langue" placeholder="langue">
        <input type="submit" name="submit" id="submit" value="upload" />
        
	</form>
	${ !empty valeur ? valeur: 'pas de valeur !' }
	${ !empty erreur ? erreur: '' }
	
</body>
</html> 