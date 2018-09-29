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
		// TODO Auto-generated method stub
		request.setAttribute("listFilms", filmDao.lister());
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitles.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (request.getParameter("submit").equals("upload")) {//si clic sur upload
			String cheminFichier = Upload.upload(request); //upload le fichier sur le serveur
			Film film = new Film( request.getParameter("nomFilm"), request.getParameter("langue"),cheminFichier);
			filmDao.ajouter(film);
			
			request.setAttribute("valeur", "nom film :"+ request.getParameter("nomFilm"));
		}
		
		
		request.setAttribute("listFilms", filmDao.lister());
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitles.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}
	
	

}
