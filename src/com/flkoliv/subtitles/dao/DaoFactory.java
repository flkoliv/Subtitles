package com.flkoliv.subtitles.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        } 

        DaoFactory instance = new DaoFactory(
                "jdbc:mariadb://localhost:3306/subtitles", "root", "LoremIpsum05");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    	//return DriverManager.getConnection("jdbc:mariadb:localhost:3306/subtitles?user=root&password=LoremIpsum05");
    }

    // Récupération du Dao
    public FilmDao getFilmDao() {
        return new FilmDaoImpl(this); 
        
        
    }
}