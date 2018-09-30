package com.flkoliv.subtitles.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.flkoliv.subtitles.beans.Film;
import com.flkoliv.subtitles.beans.Phrase;

public class FilmDaoImpl implements FilmDao {

	private DaoFactory daoFactory;
	
	FilmDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

	@Override
	public void ajouter(Film film) {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        int langId = 0;
        int filmId = 0;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT id_langue, nom_langue FROM langues ");
            ResultSet resultat = preparedStatement.executeQuery();
            while ( resultat.next() ) {
            	if (resultat.getString("nom_langue").equals(film.getLangueOriginale())){
            		langId=resultat.getInt("id_langue");
            	}
            }
            if (langId ==0) {
        		preparedStatement = connexion.prepareStatement("INSERT INTO langues(nom_langue) VALUES(?);");
                preparedStatement.setString(1, film.getLangueOriginale());
                preparedStatement.executeUpdate();
        	}
            preparedStatement = connexion.prepareStatement("SELECT id_langue FROM langues  WHERE nom_langue = ?");
            preparedStatement.setString(1, film.getLangueOriginale());
            resultat = preparedStatement.executeQuery();
            resultat.next();
            langId=resultat.getInt("id_langue");
            
            
            preparedStatement = connexion.prepareStatement("INSERT INTO films(nom, id_langue, fichier) VALUES(?, ?, ?);");
            preparedStatement.setString(1, film.getNom());
            preparedStatement.setInt(2, langId);
            preparedStatement.setString(3, film.getNomFichier());
            preparedStatement.executeUpdate();
            preparedStatement = connexion.prepareStatement("SELECT id_film FROM films  WHERE nom = ?");
            preparedStatement.setString(1, film.getNom());
            resultat = preparedStatement.executeQuery();
            resultat.next();
            filmId=resultat.getInt("id_film");
            
            
            
            preparedStatement = connexion.prepareStatement("INSERT INTO phrases(numero, debut, fin, phrase, id_film, id_langue) VALUES(?, ?, ?, ?, ?, ?);");
            for (int i=0;i<film.getPhrases().size();i++) {
            	preparedStatement.setInt(1, film.getPhrases().get(i).getNumero());
            	preparedStatement.setString(2, film.getPhrases().get(i).getMinutageDebut());
            	preparedStatement.setString(3, film.getPhrases().get(i).getMinutageFin());
            	preparedStatement.setString(4, film.getPhrases().get(i).getTexteOriginal());
            	preparedStatement.setInt(5, filmId);
            	preparedStatement.setInt(6, langId);
            	preparedStatement.executeUpdate();
            }
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
            resultat = statement.executeQuery("SELECT id_film, nom FROM films;");

            while (resultat.next()) {
                Film film = new Film();
                film.setId(resultat.getInt("id_film"));
                film.setNom(resultat.getString("nom"));
                
                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }
	
	@Override
	public boolean existe(Film film) {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT nom FROM films ");
            ResultSet resultat = preparedStatement.executeQuery();
            while ( resultat.next() ) {
            	if (resultat.getString("nom").equals(film.getNom())){
            		return true;
            	}
            }
		}catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}

	@Override
	public void charger(Film film) {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM films INNER JOIN langues ON films.id_langue = langues.id_langue WHERE films.nom=?");
            preparedStatement.setString(1, film.getNom());
            ResultSet resultat = preparedStatement.executeQuery();
            resultat.next();
            film.setCheminFichier(resultat.getString("fichier"));
            film.setId(resultat.getInt("id_film"));
            film.setIdLangueOriginale(resultat.getInt("id_langue"));
            film.setLangueOriginale(resultat.getString("nom_langue"));
            preparedStatement = connexion.prepareStatement("SELECT * FROM phrases INNER JOIN films ON phrases.id_film = films.id_film WHERE films.nom=? AND phrases.id_langue=? ORDER BY numero ASC");
            preparedStatement.setString(1, film.getNom());
            preparedStatement.setInt(2, film.getIdLangueOriginale());
            System.out.println(preparedStatement.toString());
            resultat = preparedStatement.executeQuery();	
            ArrayList<Phrase> phrases = new ArrayList<Phrase>();
            while ( resultat.next() ) {
            	Phrase p = new Phrase();
            	p.setMinutageDebut(resultat.getString("debut"));
            	p.setMinutageFin(resultat.getString("fin"));
            	p.setNumero(resultat.getInt("numero"));
            	p.setTexteOriginal(resultat.getString("phrase"));
            	phrases.add(p);
            	System.out.println(p.toString());
            }
            film.setPhrases(phrases);		
            
		}catch (SQLException e) {
            e.printStackTrace();
        }
	
		
	}
}


