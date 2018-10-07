package com.flkoliv.subtitles.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    private static DaoFactory instance = null;
    
    private DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        
        return instance;
    }
    
    public static DaoFactory getInstance(String url,String username,String password ) {
    	try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        } 
    	instance = new DaoFactory(url, username, password);
    	return instance;
    }
    
    
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Récupération du Dao
    public FilmDao getFilmDao() {
        return new FilmDaoImpl(this); 
        
        
    }

	
}