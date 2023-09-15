package it.daniele;

import java.sql.*;

public class DBUniversita {

    final String DB_URL = "jdbc:mysql://localhost:3306/";
    final String DB_USER = "root";
    final String DB_PASWORD = "060891";
    private Connection conn;
    private PreparedStatement prep;
    private Statement stat;
    private ResultSet res;


    public boolean connetti() throws SQLException {

        boolean connesso = false;

        conn = DriverManager.getConnection ( DB_URL, DB_USER, DB_PASWORD );
        // se conn è diverso da null la connessione è avvenuta senza problemi
        if (conn != null) {
            connesso = true;
            createStatement ();
            System.out.println ( "Connesso" );
        }
        return  connesso;

    }

    private void createStatement() {
        try {
            stat = conn.createStatement ();
        } catch (SQLException e) {
            System.out.println ( "Oggetto statement non creato : " + e.getMessage () );
        }
    }

    public void creaDB() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS universitacgm";
        stat.executeUpdate ( sql );
        sql = "USE universitacgm";
        stat.executeUpdate ( sql );
        creaTabellaCorsi ();
        creaTabellaInsegnanti ();
    }

    public void creaTabellaCorsi() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS corsi (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nome_corso VARCHAR(20) NOT NULL,\n" +
                "    data_inizio DATE,\n" +
                "    insegnante VARCHAR(20)\n" +
                ");";
        stat.executeUpdate ( sql );
    }

    public void creaTabellaInsegnanti() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS insegnanti (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    cognome VARCHAR(20) NOT NULL,\n" +
                "    nome VARCHAR(20) NOT NULL,\n" +
                "    id_corso INT,\n" +
                "    FOREIGN KEY (id_corso) REFERENCES corsi(id)\n" +
                ");";
        stat.executeUpdate ( sql );
    }


    public int inserisciDatiCorsi(String nomeCorso, Date date, String insegnante) throws SQLException {
        int recordInseriti = 0;
        String sql = "INSERT INTO corsi (id, nome_corso, data_inizio, insegnante) VALUES(null,?,?,?)";
        prep = conn.prepareStatement(sql);
        prep.setString (1, nomeCorso );
        prep.setDate ( 2,date);
        prep.setString ( 3,insegnante );


        recordInseriti =prep.executeUpdate ();

        return recordInseriti;
    }

    public int inserisciDatiInsegnanti(String cognome,String nome, int idCorso) throws SQLException {
        int recordInseriti = 0;
        String sql = "INSERT INTO insegnanti (id, cognome, nome, id_corso) VALUES(null,?,?,?)";
        prep = conn.prepareStatement(sql);
        prep.setString (1, cognome );
        prep.setString ( 2,nome);
        prep.setInt ( 3,idCorso );


        recordInseriti =prep.executeUpdate ();

        return recordInseriti;
    }


    public void estraiById(String tabella, int id) throws SQLException {
        String sql = "SELECT * FROM " + "nomeTabella" + " WHERE id=?";
        sql = sql.replace("nomeTabella", tabella);

        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);

        res = prep.executeQuery();

        while (res.next ()) {
            if(tabella == "corsi"){
                System.out.println ( "Record numero : " + res.getRow () );
                System.out.println ( "id: " + res.getInt ( "id" ) );
                System.out.println ( "Corso: " + res.getString ( "nome_corso" ) );
                System.out.println ( "Data inizio: " + res.getDate ( "data_inizio" ));
                System.out.println ( "Insegante: "+ res.getString ( "insegnante" ));
            }else if(tabella == "insegnanti"){
                System.out.println ( "Record numero : " + res.getRow () );
                System.out.println ( "id: " + res.getInt ( "id" ) );
                System.out.println ( "Cognome: " + res.getString ( "cognome" ) );
                System.out.println ( "Nome: " + res.getString ( "nome" ));
                System.out.println ( "Id corso: "+ res.getInt ( "id_corso" ));
            }

        }

    }

    public void joinTabelle() throws SQLException {
        String sql = " SELECT * FROM corsi INNER JOIN insegnanti ON corsi.id = insegnanti.id_corso";
        prep = conn.prepareStatement(sql);
        res = prep.executeQuery();

        while (res.next ()) {
            System.out.println ( "Record numero : " + res.getRow () );
            System.out.println ( "id: " + res.getInt ( "id" ) );
            System.out.println ( "Nome: " + res.getString ( "nome" ) );
            System.out.println ( "Cognome: " + res.getString ( "cognome" ) );
            System.out.println ( "Corso: " + res.getString ( "nome_corso" ) );


        }
    }

    public void disconnetti() throws SQLException {
        conn.close ();
        stat.close ();

        System.out.println ( "Disconnesso" );
    }
}



