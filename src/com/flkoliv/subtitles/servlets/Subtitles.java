package com.flkoliv.subtitles.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flkoliv.subtitles.beans.Film;
import com.flkoliv.subtitles.dao.DaoFactory;
import com.flkoliv.subtitles.dao.FilmDao;
import com.flkoliv.subtitles.utilities.Upload;

/**
 * Servlet implementation class Subtitles
 */
@WebServlet("/Subtitles/sub")
public class Subtitles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FilmDao filmDao;
	private Film film;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Subtitles() {
		super();
	}

	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.filmDao = daoFactory.getFilmDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("listFilms", filmDao.lister());
		request.setAttribute("listLangues", filmDao.listerLangues());
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitles.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("listFilms", filmDao.lister());
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("submit").equals("Upload")) {// si clic sur upload
			String cheminFichier = Upload.upload(request); // upload le fichier sur le serveur
			film = new Film(request, cheminFichier);
			doGet(request, response);
		} else if (request.getParameter("submit").equals("Traduire")) {// si clic sur choix film
			film = new Film(request);

			if (film.getIdLangueOriginale() == film.getIdLangueTraduction()) {
				request.setAttribute("message", "Vous avez choisi la même langue que la langue originale");
				doGet(request, response);
			} else {
				request.setAttribute("film", film);

				this.getServletContext().getRequestDispatcher("/WEB-INF/traitement.jsp").forward(request, response);
				response.getWriter().append("Served at: ").append(request.getContextPath());

			}
		} else if (request.getParameter("submit").equals("Télécharger")) {// si clic sur Télécharger
			
			film = new Film(request);
			String chemin = this.getServletConfig().getInitParameter( "chemin" )+film.getNom().replaceAll(" ", "_")+"-"+film.getLangueTraduction()+".srt";
			File fichier = film.creerFichier(chemin);
			if (fichier!=null) {
				String type = getServletContext().getMimeType( fichier.getName() );
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
					final int TAILLE_TAMPON = 10240;
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
			}else {
				request.setAttribute("message", "La traduction n'existe pas encore.");
				doGet(request, response);
			}

		} else if (request.getParameter("submit").equals("Sauvegarder")) {// si clic sur sauvegarder
			film.sauvegarder(request);
			request.setAttribute("message", "Sauvegarde effectuée");
			doGet(request, response);
		}
	}
}
