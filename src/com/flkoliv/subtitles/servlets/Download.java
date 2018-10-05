package com.flkoliv.subtitles.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int TAILLE_TAMPON = 10240;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String chemin = this.getServletConfig().getInitParameter( "chemin" );
		System.out.println(chemin);
		/* Récupération du chemin du fichier demandé au sein de l'URL de la requête */
		String fichierRequis = request.getPathInfo();
		System.out.println(fichierRequis);
		/* Vérifie qu'un fichier a bien été fourni */
		if ( fichierRequis == null || "/".equals( fichierRequis ) ) {
		    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
		    response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    return;
		}
		/* Décode le nom de fichier récupéré, susceptible de contenir des espaces et autres caractères spéciaux, et prépare l'objet File */
		fichierRequis = URLDecoder.decode( fichierRequis, "UTF-8");
		File fichier = new File( chemin, fichierRequis );
		
		        
		/* Vérifie que le fichier existe bien */
		if ( !fichier.exists() ) {
		    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
		    response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    System.out.println("existe pas");
		    return;
		}
		/* Récupère le type du fichier */
		String type = getServletContext().getMimeType( fichier.getName() );
		System.out.println(type);

		/* Si le type de fichier est inconnu, alors on initialise un type par défaut */
		if ( type == null ) {
		    type = "application/octet-stream";
		}
		
		final int DEFAULT_BUFFER_SIZE = 10240; // 10 ko


		/* Initialise la réponse HTTP */
		response.reset();
		response.setBufferSize( DEFAULT_BUFFER_SIZE );
		response.setContentType( type );
		response.setHeader( "Content-Length", String.valueOf( fichier.length() ) );
		response.setHeader( "Content-Disposition", "attachment; filename=\"" + fichier.getName() + "\"" );
		/* Prépare les flux */
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
		    /* Ouvre les flux */
		    entree = new BufferedInputStream( new FileInputStream( fichier ), TAILLE_TAMPON );
		    sortie = new BufferedOutputStream( response.getOutputStream(), TAILLE_TAMPON );
		    byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ( ( longueur= entree.read( tampon ) ) > 0 ) {
			    sortie.write( tampon, 0, longueur );
			}
		 
		    /* ... */
		} finally {
		    try {
		        sortie.close();
		    } catch ( IOException ignore ) {
		    }
		    try {
		        entree.close();
		    } catch ( IOException ignore ) {
		    }
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
