<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Subtitles Translate</title>
</head>
<body>
<h1>SubTranslate</h1>
	<form method="post" action="">
    	<label for="nom">Choix film : </label>
        <select name="film">
        	<option value="film1">Film1</option>
        	<option value="film2">Film2</option>
        	<option value="film3">Film3</option>
        </select>
        <input type="submit" value="ok" />
	</form>
	<form method="post" action="" enctype="multipart/form-data">
    	<label for="nom">Nouveau film : </label>
        <input type="text" name="nomFilm" id="nomFilm" placeholder="nom film">
        <input type="file" name="fichierFilm" id="fichierFilm" accept=".srt" placeholder="fichier film"/>
        <input type="text" name="langue" id="langue" placeholder="langue">
        <input type="submit" name="submit" id="submit" value="upload" />
        
	</form>
	${ !empty valeur ? valeur: 'pas de valeur !' }
</body>
</html> 