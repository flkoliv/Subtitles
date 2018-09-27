package com.flkoliv.subtitles.dao;

import com.flkoliv.subtitles.beans.Film;

import java.util.List;

public interface FilmDao {
    void ajouter( Film utilisateur );
    List<Film> lister();
}