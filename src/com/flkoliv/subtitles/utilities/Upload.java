package com.flkoliv.subtitles.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class Upload {

	public static final int TAILLE_TAMPON = 10240;

	public static String upload(HttpServletRequest request) throws IOException, ServletException {
		// On récupère le champ description comme d'habitude
		// String description = request.getParameter("description");
		// request.setAttribute("description", description);
		String CHEMIN_FICHIERS = request.getServletContext().getInitParameter("chemin"); // recupère le chemin dans
																							// web.xml
		// On récupère le champ du fichier
		Part part = request.getPart("fichierFilm");
		// On vérifie qu'on a bien reçu un fichier
		String nomFichier = getNomFichier(part);
		// Si on a bien un fichier
		if (nomFichier != null && !nomFichier.isEmpty()) {
			//String nomChamp = part.getName();
			// Corrige un bug du fonctionnement d'Internet Explorer
			nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
					.substring(nomFichier.lastIndexOf('\\') + 1);

			// On écrit définitivement le fichier sur le disque
			ecrireFichier(part, nomFichier, CHEMIN_FICHIERS);
			// request.setAttribute(nomChamp, nomFichier);
		}
		File fichier = new File(CHEMIN_FICHIERS + nomFichier);
		if (fichierValide(fichier)) {
			return CHEMIN_FICHIERS + nomFichier;
		} else {
			return null;
		}

	}

	private static void ecrireFichier(Part part, String nomFichier, String chemin) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {
			}
			try {
				entree.close();
			} catch (IOException ignore) {
			}
		}
	}

	private static String getNomFichier(Part part) {
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private static boolean fichierValide(File f) {

		try {

			FileReader fr = new FileReader(f);
			String line;
			try {

				BufferedReader br = new BufferedReader(fr);
				line = br.readLine();
				int compteurPhrase = 1;
				int i = 1;
				while (line != null) {
					
					System.out.println(line);
					if (i == 1) { // ligne des numéro de phrase
						line = line.replaceAll("[\\W]", "");
						if (!line.equals("")) {
							try {

								if (compteurPhrase != Integer.valueOf(line)) {
									System.out.println(i + " " + line);
									br.close();
									return false;
								} else {
									compteurPhrase++;
								}
							} catch (NumberFormatException ex) {
								System.out.println(i + " " + line);
								br.close();
								return false;
							}
						}else {
							i--;
						}
						
					} else if (i == 2) {
						if (!line.matches("^\\d\\d+:[0-5]\\d:[0-5]\\d,\\d{3} --> \\d\\d+:[0-5]\\d:[0-5]\\d,\\d{3}$")) {
							System.out.println(i + " " + line);
							br.close();
							return false;
						}
					} else if (i == 3) {
						if (line.equals("")) {
							System.out.println(i + " " + line);
							br.close();
							return false;
						}
					} else if (i == 4) {
						if (line.equals("")) {
							i = 0;
						} else {
							i = i - 1;
						}
					}
					i++;
					line = br.readLine();

				}
				br.close();
			} catch (IOException e) {

				e.printStackTrace();

				return false;

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();

			return false;
		}
		System.out.println("fichier valide");
		return true;

	}

}
