package com.flkoliv.subtitles.beans;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.flkoliv.subtitles.dao.DaoFactory;
import com.flkoliv.subtitles.dao.FilmDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;;

public class Film {
	
	private int id;
	private String nom;
	private String langueOriginale;
	private int idLangueOriginale;
	private String langueTraduction;
	private int idLangueTraduction;
	private String cheminFichier;
	private ArrayList<Phrase> phrases = new ArrayList<Phrase>();
	
	private FilmDao filmDao; 
	
	public int getIdLangueOriginale() {
		return idLangueOriginale;
	}

	public void setIdLangueOriginale(int idLangueOriginale) {
		this.idLangueOriginale = idLangueOriginale;
	}

	public Film(HttpServletRequest request,String cheminFichier) {
		this.nom = request.getParameter("nomFilm");
		this.langueOriginale = request.getParameter("langue");
		this.cheminFichier = cheminFichier;
		creerPhrases();
		DaoFactory daoFactory = DaoFactory.getInstance();
        this.filmDao = daoFactory.getFilmDao();
        if (!this.filmDao.existe(this)) {
        	this.filmDao.ajouter(this);
        	request.setAttribute("message","Le nouveau fichier a été chargé !");
        }else {
        	request.setAttribute("message","Le film existe déjà !");
        }
	}
	
	public Film(HttpServletRequest request) {
		DaoFactory daoFactory = DaoFactory.getInstance();
        this.filmDao = daoFactory.getFilmDao();
		this.nom = request.getParameter("film");
		this.langueTraduction = request.getParameter("langueDestination");
		if (this.filmDao.existe(this)) {
			 this.filmDao.charger(this);
        }else {
        	request.setAttribute("message","Le film n'existe pas !");
        }
	}
	
	public Film() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getLangueOriginale() {
		return langueOriginale;
	}
	public void setLangueOriginale(String langueOriginale) {
		this.langueOriginale = langueOriginale;
	}
	public String getLangueTraduction() {
		return langueTraduction;
	}
	public void setLangueTraduction(String langueTraduction) {
		this.langueTraduction = langueTraduction;
	}
	public int getIdLangueTraduction() {
		return idLangueTraduction;
	}

	public void setIdLangueTraduction(int idLangueTraduction) {
		this.idLangueTraduction = idLangueTraduction;
	}
	public String getNomFichier() {
		return cheminFichier;
	}
	public void setNomFichier(String nomFichier) {
		this.cheminFichier = nomFichier;
	}
	public ArrayList<Phrase> getPhrases() {
		return phrases;
	}
	public void setPhrases(ArrayList<Phrase> phrases) {
		this.phrases = phrases;
	}
	public String getCheminFichier() {
		return cheminFichier;
	}
	public void setCheminFichier(String cheminFichier) {
		this.cheminFichier = cheminFichier;
	}
	
	
	private void creerPhrases() {
		try
		{
		    File f = new File (cheminFichier);
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		    try {
		        String line = br.readLine();
		        Phrase phrase = new Phrase();
		        int compteur = 1;
		        while (line != null) {
		        	
		        	if(line.length()==0) {
		            	phrases.add(phrase);
		        		phrase = new Phrase();
		            	compteur = 0;
		            }
		        	if (compteur == 1) {
          	           	line=line.replaceAll("[\\W]","");
		            	phrase.setNumero(Integer.parseInt(line.trim()));
		            }else if (compteur == 2 ) {
		            	String[] t = line.split(" --> ");
		            	phrase.setMinutageDebut(t[0]);
		            	phrase.setMinutageFin(t[1]);
		            }else if (compteur == 3) {
		            	phrase.setTexteOriginal(line);
		            }else if (compteur == 4) {
		            	String ph = phrase.getTexteOriginal();
		            	phrase.setTexteOriginal(ph + "\n" + line);
		            }
		            compteur++;
		            line = br.readLine();
		        }
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception)
		    {
		        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("Le fichier n'a pas été trouvé");
		}
	}
	
	
	public void sauvegarder(HttpServletRequest request) {
		DaoFactory daoFactory = DaoFactory.getInstance();
        this.filmDao = daoFactory.getFilmDao();
        for(int i = 0; i < phrases.size(); i++) {
        	int j = phrases.get(i).getNumero();
        	phrases.get(i).setTexteTraduit(request.getParameter("txtTraduit"+j));
        	System.out.println(phrases.get(i).getTexteOriginal());
        	System.out.println(phrases.get(i).getTexteTraduit());
        }
        filmDao.sauvegarder(this);
	}
	
	
}
