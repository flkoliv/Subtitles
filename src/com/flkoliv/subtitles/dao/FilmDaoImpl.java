package com.flkoliv.subtitles.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.flkoliv.subtitles.beans.Film;

public class FilmDaoImpl implements FilmDao {

	private DaoFactory daoFactory;
	
	FilmDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

	@Override
	public void ajouter(Film film) {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO films(nom, langue, fichier) VALUES(?, ?, ?);");
            preparedStatement.setString(1, film.getNom());
            preparedStatement.setString(2, film.getLangueOriginale());
            preparedStatement.setString(3, film.getNomFichier());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public List<Film> lister() {
		List<Film> films = new ArrayList<Film>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT id, nom, langue, fichier FROM films;");

            while (resultat.next()) {
                Film film = new Film();
                film.setId(resultat.getInt("id"));
                film.setNom(resultat.getString("nom"));
                film.setLangueOriginale(resultat.getString("langue"));
                film.setLangueOriginale(resultat.getString("fichier"));
                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }
}


