package com.flkoliv.subtitles.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flkoliv.subtitles.utilities.Upload;

/**
 * Servlet implementation class Subtitles
 */
@WebServlet("/Subtitles")
public class Subtitles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Subtitles() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitles.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (request.getParameter("submit").equals("upload")) {//si clic sur upload
			String nomfilm = request.getParameter("nomFilm");
			request = Upload.upload(request);
			request.setAttribute("valeur", "nom film :"+ nomfilm);
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/subtitles.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

}
