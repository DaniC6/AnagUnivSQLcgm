package it.daniele;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        /**
         * L'AAPLICAZIONE DEVE:
         * CREARE DATABASE
         * CREARE DUE TABELLE : CORSI(id pk, nomecorso varchar, datainizio , insegnant)    INSEGNANTI(id pk, nome , cognome, id_corso chiave esterna)
         *
         * creare una join corsi.id = insegnanti.id_corso che facciamo richiamare da un metodo
         */


        DBUniversita dbUniversita = new DBUniversita ();
        try {

            dbUniversita.connetti ();
            System.out.println ("----------------------CREATE ------------------------");
            dbUniversita.creaDB ();

            System.out.println ("-----------------INSERT ----------------------------");
           // dbUniversita.inserisciDatiInsegnanti ( "Scotti", "Gerry", 3 );
           // dbUniversita.inserisciDatiCorsi ( "Psicologia", Date.valueOf ( LocalDate.now () ),"Emilio Solfrizzi" );

            System.out.println ("------------------ JOIN---------------------");
            dbUniversita.joinTabelle();

            System.out.println ("--------------------ESTRAI BY ID-------------------------");
            dbUniversita.estraiById ( "corsi",1 );
            dbUniversita.estraiById ( "insegnanti",2 );

            dbUniversita.disconnetti ();

        } catch (SQLException e) {
            throw new RuntimeException ( e );
        }

    }
}