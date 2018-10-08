package com.flkoliv.subtitles.dao;

import com.flkoliv.subtitles.beans.Film;
import java.util.List;

public interface FilmDao {
	List<Film> lister();

	List<String> listerLangues();

	void ajouter(Film utilisateur);

	void charger(Film film);

	void sauvegarder(Film film);

	public boolean existe(Film film);
}