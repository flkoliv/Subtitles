package com.flkoliv.subtitles.servlets;

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
@WebServlet("/Subtitles")
public class Subtitles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FilmDao filmDao; 
	private Film film;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Subtitles() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.filmDao = daoFactory.getFilmDao();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listFilms", filmDao.lister());
		request.setAttribute("listLangues", filmDao.listerLangues());
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitles.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listFilms", filmDao.lister());
		if (request.getParameter("submit").equals("upload")) {//si clic sur upload
			String cheminFichier = Upload.upload(request); //upload le fichier sur le serveur
			film = new Film( request ,cheminFichier);
			request.setAttribute("valeur", "nom film :"+ request.getParameter("nomFilm"));
			request.setAttribute("film", film);
			this.getServletContext().getRequestDispatcher("/WEB-INF/traitement.jsp").forward(request, response);
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}else if (request.getParameter("submit").equals("choixFichier")) {//si clic sur choix film
			film = new Film(request);
			request.setAttribute("film", film);
			this.getServletContext().getRequestDispatcher("/WEB-INF/traitement.jsp").forward(request, response);
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}else if (request.getParameter("submit").equals("sauvegarder")) {//si clic sur sauvegarder
			film.sauvegarder(request);
			System.out.println("Sauvegarde");
			doGet(request,response);
		}
	}
}
