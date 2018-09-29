package com.flkoliv.subtitles.beans;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;;

public class Film {
	
	private int id;
	private String nom;
	private String langueOriginale;
	private String langueTraduction;
	private String cheminFichier;
	private ArrayList<Phrase> phrases = new ArrayList<Phrase>();
	private ArrayList<Phrase> phrasesTraduites = new ArrayList<Phrase>();
	
	

	public Film(String nom, String langueOriginale,String cheminFichier ) {
		this.nom = nom;
		this.langueOriginale = langueOriginale;
		this.cheminFichier = cheminFichier;
		creerPhrases();
		creerPhrasesTraduites();
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
	public ArrayList<Phrase> getPhrasesTraduites() {
		return phrasesTraduites;
	}

	public void setPhrasesTraduites(ArrayList<Phrase> phrasesTraduites) {
		this.phrasesTraduites = phrasesTraduites;
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
		            	phrase.setNumero(Integer.parseInt(line));
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
	
	private void creerPhrasesTraduites() {
		for(int i = 0; i < phrases.size(); i++) {
			Phrase p = new Phrase();
			p.setNumero(phrases.get(i).getNumero());
			p.setMinutageDebut(phrases.get(i).getMinutageDebut());
			p.setMinutageFin(phrases.get(i).getMinutageFin());
			phrasesTraduites.add(p);
	    }    
	}
	
	
}
