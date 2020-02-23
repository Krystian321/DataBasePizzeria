import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class JDBC {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:test1.db";

    private static Connection conn;
    private static Statement stmt;




    public static void main(String[] args) throws IOException {
        //dodaje klase JDBC
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika jdbc");
            e.printStackTrace();
        }

        //nawiazuje polaczenie z baza danych
        try{
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
        //tworzenie tabel

        String createLokal="CREATE TABLE if not exists lokal (\n" +
                "  idlokal INTEGER NOT NULL  ,\n" +
                "  nazwa VARCHAR(20)    ,\n" +
                "  miasto VARCHAR(20)    ,\n" +
                "  ulica VARCHAR(50)      ,\n" +
                "PRIMARY KEY(idlokal));";
        try{
            stmt.execute(createLokal);
        }catch (SQLException e){
            System.err.println("blad przy tworzeniu tabeli LOKAL");
            e.printStackTrace();
        }

        String createSamochod="CREATE TABLE if not exists samochod (\n" +
                "  idsamochod INTEGER NOT NULL ,\n" +
                "  lokal_idlokal INTEGER  NOT NULL  ,\n" +
                "  marka VARCHAR(20)    ,\n" +
                "  typ VARCHAR(20)    ,\n" +
                "  kolor VARCHAR(20)      ,\n" +
                "PRIMARY KEY(idsamochod)  ,\n" +
                "  FOREIGN KEY(lokal_idlokal)\n" +
                "    REFERENCES lokal(idlokal)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION);\n" +
                "\n" +
                "\n" +
                "CREATE INDEX samochod_FKIndex1 ON samochod (lokal_idlokal)";
        try{
            stmt.execute(createSamochod);
        }catch (SQLException e){
            System.err.println("blad przy tworzeniu tabeli SAMOCHOD");
            e.printStackTrace();
        }

        String createSerwis="CREATE TABLE if not exists serwis (\n" +
                "  idserwis INTEGER NOT NULL  ,\n" +
                "  nazwa VARCHAR(20)    ,\n" +
                "  miasto VARCHAR(20)    ,\n" +
                "  ulica VARCHAR(50)      ,\n" +
                "PRIMARY KEY(idserwis))";
        try{
            stmt.execute(createSerwis);
        }catch (SQLException e){
            System.err.println("blad przy tworzeniu tabeli Serwis");
            e.printStackTrace();
        }

        String createSerwis_samochodow="CREATE TABLE if not exists serwis_samochodow (\n" +
                "  samochod_idsamochod INTEGER  NOT NULL  ,\n" +
                "  serwis_idserwis INTEGER  NOT NULL  ,\n" +
                "  data_przyjecia DATE    ,\n" +
                "  data_oddania DATE    ,\n" +
                "  cena DECIMAL      ,\n" +
                "PRIMARY KEY(samochod_idsamochod, serwis_idserwis)    ,\n" +
                "  FOREIGN KEY(samochod_idsamochod)\n" +
                "    REFERENCES samochod(idsamochod)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY(serwis_idserwis)\n" +
                "    REFERENCES serwis(idserwis)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION);\n" +
                "\n" +
                "\n" +
                "CREATE INDEX samochod_has_serwis_FKIndex1 ON serwis_samochodow (samochod_idsamochod);\n" +
                "CREATE INDEX samochod_has_serwis_FKIndex2 ON serwis_samochodow (serwis_idserwis);";
        try{
            stmt.execute(createSerwis_samochodow);
        }catch (SQLException e){
            System.err.println("blad przy tworzeniu tabeli SERWIS_SAMOCHODOW");
            e.printStackTrace();
        }

        String createSerwisant="CREATE TABLE if not exists serwisant (\n" +
                "  idserwisant INTEGER  NOT NULL  ,\n" +
                "  serwis_idserwis INTEGER  NOT NULL  ,\n" +
                "  imie VARCHAR(20)    ,\n" +
                "  nazwisko VARCHAR(20)    ,\n" +
                "  pesel INTEGER(11)      ,\n" +
                "PRIMARY KEY(idserwisant)  ,\n" +
                "  FOREIGN KEY(serwis_idserwis)\n" +
                "    REFERENCES serwis(idserwis)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION);\n" +
                "\n" +
                "\n" +
                "CREATE INDEX serwisant_FKIndex1 ON serwisant (serwis_idserwis);";
        try{
            stmt.execute(createSerwisant);
        }catch (SQLException e){
            System.err.println("blad przy tworzeniu tabeli SERWISANT");
            e.printStackTrace();
        }


        //dodawanie rekordow

        String sql;
        try{
            sql="insert or replace into lokal(idlokal,nazwa,miasto,ulica) values (1,'Lozano','Gdansk','Lozanowa');";
            stmt.execute(sql);
            sql="insert or replace into lokal(idlokal,nazwa,miasto,ulica) values (2,'Kozano','Warszawa','Kozanowa');";
            stmt.execute(sql);
            sql="insert or replace into lokal(idlokal,nazwa,miasto,ulica) values (3,'Zozano','Krakow','Zozanowa');";
            stmt.execute(sql);}
        catch (SQLException e) {
            System.err.println("Blad przy dodawaniu rekordu LOKAL");
            e.printStackTrace();
        }

        try{


            sql="insert or replace into samochod(idsamochod,marka,typ,kolor,lokal_idlokal) values (1,'Opel','Vectra','Zielony',1);";
            stmt.execute(sql);
            sql="insert or replace into samochod(idsamochod,marka,typ,kolor,lokal_idlokal) values (2,'Honda','Civic','Czerowny',2);";
            stmt.execute(sql);
            sql="insert or replace into samochod(idsamochod,marka,typ,kolor,lokal_idlokal) values (3,'Skoda','Octavia','Niebieski',3);";
            stmt.execute(sql);}
        catch (SQLException e) {
            System.err.println("Blad przy dodawaniu rekordu SAMOCHOD");
            e.printStackTrace();
        }
        try{

            sql="insert or replace into serwis(idserwis,nazwa,miasto,ulica) values (1,'Romek','Gdansk','Gdanska');";
            stmt.execute(sql);
            sql="insert or replace into serwis(idserwis,nazwa,miasto,ulica) values (2,'Janek','Warszawa','Warszawska');";
            stmt.execute(sql);
            sql="insert or replace into serwis(idserwis,nazwa,miasto,ulica) values (3,'Tymek','Krakow','Krakowska');";
            stmt.execute(sql);
        }catch (SQLException e) {
            System.err.println("Blad przy dodawaniu rekordu SERWIS");
            e.printStackTrace();
        }
        try{

            sql="insert or replace into serwisant(idserwisant,imie,nazwisko,pesel,serwis_idserwis) values (1,'Robert','Robertowy',11111111111,1);";
            stmt.execute(sql);
            sql="insert or replace into serwisant(idserwisant,imie,nazwisko,pesel,serwis_idserwis) values (2,'Piotr','Piotrowski',22222222222,2);";
            stmt.execute(sql);
            sql="insert or replace into serwisant(idserwisant,imie,nazwisko,pesel,serwis_idserwis) values (3,'Jan','Janowski',33333333333,3);";
            stmt.execute(sql);
        }catch (SQLException e) {
            System.err.println("Blad przy dodawaniu rekordu SERWISANT");
            e.printStackTrace();
        }
        try{

            sql="insert or replace into serwis_samochodow(samochod_idsamochod,serwis_idserwis,data_przyjecia,data_oddania,cena) values (1,1,'2019-01-01','2019-01-02',100);";
            stmt.execute(sql);
            sql="insert or replace into serwis_samochodow(samochod_idsamochod,serwis_idserwis,data_przyjecia,data_oddania,cena) values (2,2,'2019-02-02','2019-02-03',200);";
            stmt.execute(sql);
            sql="insert or replace into serwis_samochodow(samochod_idsamochod,serwis_idserwis,data_przyjecia,data_oddania,cena) values (3,3,'2019-03-03','2019-03-04',300);";
            stmt.execute(sql);
        } catch (SQLException e) {
             System.err.println("Blad przy dodawaniu rekordu SERWIS_SAMOCHODOW");
             e.printStackTrace();
        }
        //wyswietlanie




        Scanner wybor = new Scanner(System.in);
        int menu=0;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("1) Wyswietlenie danych");
            System.out.println("2) Modyfikacja danych");
            System.out.println("3) Dodawanie danych");
            System.out.println("4) Usuwanie danych");
            System.out.println("5) WYJSCIE");
            System.out.print("Co chcesz zrobic ? Wybierz numer: ");
            menu = wybor.nextInt();


            switch (menu) {


                case 1: {
                    Scanner wyborPod = new Scanner(System.in);
                    int podMenu = 0;
                    do {
                        System.out.println("------------------------------------------------------");
                        System.out.println("1) wyswietl tabele LOKAL");
                        System.out.println("2) wyswietl tabele SAMOCHOD");
                        System.out.println("3) wyswietl tabele SERWISANT");
                        System.out.println("4) wyswietl tabele SERWIS");
                        System.out.println("5) wyswietl tabele SERWIS_SAMOCHODOW");
                        System.out.println("6) COFNIJ");
                        System.out.print("Co chcesz zrobic ? Wybierz numer: ");
                        podMenu = wyborPod.nextInt();


                        switch (podMenu) {
                            case 1: {

                                try {
                                    ResultSet result = stmt.executeQuery("Select * from lokal");
                                    int idlokal;
                                    String nazwa, miasto, ulica;
                                    while (result.next()) {
                                        idlokal = result.getInt("idlokal");
                                        nazwa = result.getString("nazwa");
                                        miasto = result.getString("miasto");
                                        ulica = result.getString("ulica");
                                        System.out.println("id = " + idlokal + " | " + "nazwa = " + nazwa + " | " + "miasto = " + miasto + " | " + "ulica = " + ulica);


                                    }
                                } catch (SQLException e) {
                                    System.err.println("Blad przy wprowadzaniu SELECT");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 2: {
                                try {
                                    ResultSet result = stmt.executeQuery("Select * from samochod");
                                    int idsamochod;
                                    String marka, typ, kolor;
                                    while (result.next()) {
                                        idsamochod = result.getInt("idsamochod");
                                        marka = result.getString("marka");
                                        typ = result.getString("typ");
                                        kolor = result.getString("kolor");
                                        System.out.println("id = " + idsamochod + " | " + "marka = " + marka + " | " + "typ = " + typ + " | " + "kolor = " + kolor);


                                    }
                                } catch (SQLException e) {
                                    System.err.println("Blad przy wprowadzaniu SELECT");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 3: {
                                try {
                                    ResultSet result = stmt.executeQuery("Select * from serwisant");
                                    int idserwisant;
                                    String imie, nazwisko, pesel;
                                    while (result.next()) {
                                        idserwisant = result.getInt("idserwisant");
                                        imie = result.getString("imie");
                                        nazwisko = result.getString("nazwisko");
                                        pesel = result.getString("pesel");
                                        System.out.println("id = " + idserwisant + " | " + "imie = " + imie + " | " + "nazwisko = " + nazwisko + " | " + "pesel = " + pesel);


                                    }
                                } catch (SQLException e) {
                                    System.err.println("Blad przy wprowadzaniu SELECT");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 4: {
                                try {
                                    ResultSet result = stmt.executeQuery("Select * from serwis");
                                    int idserwis;
                                    String nazwa, miasto, ulica;
                                    while (result.next()) {
                                        idserwis = result.getInt("idserwis");
                                        nazwa = result.getString("nazwa");
                                        miasto = result.getString("miasto");
                                        ulica = result.getString("ulica");
                                        System.out.println("id = " + idserwis + " | " + "nazwa = " + nazwa + " | " + "miasto = " + miasto + " | " + "ulica = " + ulica);


                                    }
                                } catch (SQLException e) {
                                    System.err.println("Blad przy wprowadzaniu SELECT");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 5: {
                                try {
                                    ResultSet result = stmt.executeQuery("Select * from serwis_samochodow");
                                    int samochod_idsamochod, serwis_idserwis, cena;
                                    Date data_przyjecia, data_oddania;
                                    while (result.next()) {
                                        samochod_idsamochod = result.getInt("samochod_idsamochod");
                                        serwis_idserwis = result.getInt("serwis_idserwis");
                                        data_przyjecia = result.getDate("data_przyjecia");
                                        data_oddania = result.getDate("data_oddania");
                                        cena = result.getInt("cena");
                                        System.out.println("samochod_idsamochod = " + samochod_idsamochod + " | " + "serwis_idserwis = " + serwis_idserwis + " | " + "data przyjecia = " + data_przyjecia + " | " + "data oddania = " + data_oddania + " | " + "cena = " + cena);


                                    }
                                } catch (SQLException e) {
                                    System.err.println("Blad przy wprowadzaniu SELECT");
                                    e.printStackTrace();
                                }
                                break;
                            }

                        }
                    }
                    while (podMenu != 6);
                    break;
                }

                case 2: {
                    Scanner updatePod = new Scanner(System.in);
                    int podUpdate = 0;
                    do {
                        System.out.println("------------------------------------------------------");
                        System.out.println("1) Modyfikuj tabele LOKAL");
                        System.out.println("2) Modyfikuj tabele SAMOCHOD");
                        System.out.println("3) Modyfikuj tabele SERWISANT");
                        System.out.println("4) Modyfikuj tabele SERWIS");
                        System.out.println("5) Modyfikuj tabele SERWIS_SAMOCHODOW");
                        System.out.println("6) COFNIJ");
                        System.out.print("Co chcesz zrobic ? Wybierz numer: ");
                        podUpdate = updatePod.nextInt();

                        switch (podUpdate) {
                            case 1: {
                                String colname;
                                String zmienna1;
                                int zmienna2;
                                Scanner Baza = new Scanner(System.in);

                                System.out.print("\n Podaj nazwę kolumny do modyfikacji: ");
                                colname = Baza.next();
                                System.out.print("\n Podaj nową nazwę: ");
                                zmienna1 = Baza.next();
                                System.out.print("\n Podaj numer id: ");
                                zmienna2 = Integer.parseInt(Baza.next());
                                try {
                                    PreparedStatement ps = conn.prepareStatement("Update lokal set " + colname + "=? where idlokal=?");
                                    ps.setString(1, zmienna1);
                                    ps.setInt(2, zmienna2);

                                    ps.executeUpdate();
                                    ps.close();


                                    System.out.println("Update został wykonany");
                                    break;


                                } catch (SQLException e) {
                                    System.err.println("Błąd przy wykonywaniu UPDATE");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 2: {
                                String colname;
                                String zmienna1;
                                int zmienna2;
                                Scanner Baza = new Scanner(System.in);

                                System.out.print("\n Podaj nazwę kolumny do modyfikacji: ");
                                colname = Baza.next();
                                System.out.print("\n Podaj nową nazwę: ");
                                zmienna1 = Baza.next();
                                System.out.print("\n Podaj numer id: ");
                                zmienna2 = Integer.parseInt(Baza.next());
                                try {
                                    PreparedStatement ps = conn.prepareStatement("Update samochod set " + colname + "=? where idlokal=?");
                                    ps.setString(1, zmienna1);
                                    ps.setInt(2, zmienna2);

                                    ps.executeUpdate();
                                    ps.close();


                                    System.out.println("Update został wykonany");
                                    break;


                                } catch (SQLException e) {
                                    System.err.println("Błąd przy wykonywaniu UPDATE");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 3: {
                                String colname;
                                String zmienna1;
                                int zmienna2;
                                Scanner Baza = new Scanner(System.in);

                                System.out.print("\n Podaj nazwę kolumny do modyfikacji: ");
                                colname = Baza.next();
                                System.out.print("\n Podaj nową nazwę: ");
                                zmienna1 = Baza.next();
                                System.out.print("\n Podaj numer id: ");
                                zmienna2 = Integer.parseInt(Baza.next());
                                try {
                                    PreparedStatement ps = conn.prepareStatement("Update serwisant set " + colname + "=? where idlokal=?");
                                    ps.setString(1, zmienna1);
                                    ps.setInt(2, zmienna2);

                                    ps.executeUpdate();
                                    ps.close();


                                    System.out.println("Update został wykonany");
                                    break;


                                } catch (SQLException e) {
                                    System.err.println("Błąd przy wykonywaniu UPDATE");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 4: {
                                String colname;
                                String zmienna1;
                                int zmienna2;
                                Scanner Baza = new Scanner(System.in);

                                System.out.print("\n Podaj nazwę kolumny do modyfikacji: ");
                                colname = Baza.next();
                                System.out.print("\n Podaj nową nazwę: ");
                                zmienna1 = Baza.next();
                                System.out.print("\n Podaj numer id: ");
                                zmienna2 = Integer.parseInt(Baza.next());
                                try {
                                    PreparedStatement ps = conn.prepareStatement("Update serwis set " + colname + "=? where idlokal=?");
                                    ps.setString(1, zmienna1);
                                    ps.setInt(2, zmienna2);

                                    ps.executeUpdate();
                                    ps.close();


                                    System.out.println("Update został wykonany");
                                    break;


                                } catch (SQLException e) {
                                    System.err.println("Błąd przy wykonywaniu UPDATE");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 5: {
                                String colname;
                                String zmienna1;
                                int zmienna2;
                                Scanner Baza = new Scanner(System.in);

                                System.out.print("\n Podaj nazwę kolumny do modyfikacji: ");
                                colname = Baza.next();
                                System.out.print("\n Podaj nową nazwę: ");
                                zmienna1 = Baza.next();
                                System.out.print("\n Podaj numer id: ");
                                zmienna2 = Integer.parseInt(Baza.next());
                                try {
                                    PreparedStatement ps = conn.prepareStatement("Update serwis_samochodow set " + colname + "=? where idlokal=?");
                                    ps.setString(1, zmienna1);
                                    ps.setInt(2, zmienna2);

                                    ps.executeUpdate();
                                    ps.close();


                                    System.out.println("Update został wykonany");
                                    break;


                                } catch (SQLException e) {
                                    System.err.println("Błąd przy wykonywaniu UPDATE");
                                    e.printStackTrace();
                                }
                                break;
                            }


                        }
                    } while (podUpdate != 6);
                    break;
                }


                case 3: {
                    Scanner dodawaniePod = new Scanner(System.in);
                    int podDodawanie = 0;
                    do {
                        System.out.println("------------------------------------------------------");
                        System.out.println("1) Dodaj do tabeli LOKAL");
                        System.out.println("2) Dodaj do tabeli SAMOCHOD");
                        System.out.println("3) Dodaj do tabeli SERWISANT");
                        System.out.println("4) Dodaj do tabeli SERWIS");
                        System.out.println("5) Dodaj do tabeli SERWIS_SAMOCHODOW");
                        System.out.println("6) COFNIJ");
                        System.out.print("Co chcesz zrobic ? Wybierz numer: ");
                        podDodawanie = dodawaniePod.nextInt();

                        switch (podDodawanie) {
                            case 1: {
                                try {

                                    PreparedStatement pr = conn.prepareStatement("Insert into lokal(idlokal,nazwa,miasto,ulica) values (?,?,?,?);");
                                    Scanner dodajID = new Scanner(System.in);
                                    int id = 0;
                                    System.out.println("Podaj ID: ");
                                    id = dodajID.nextInt();
                                    pr.setInt(1, id);

                                    Scanner dodajNazwe = new Scanner(System.in);
                                    String nazwa;
                                    System.out.println("Podaj Nazwe: ");
                                    nazwa = dodajNazwe.next();
                                    pr.setString(2, nazwa);

                                    Scanner dodajMiasto = new Scanner(System.in);
                                    String miasto;
                                    System.out.println("Podaj Miasto: ");
                                    miasto = dodajMiasto.next();
                                    pr.setString(3, miasto);

                                    Scanner dodajUlice = new Scanner(System.in);
                                    String ulica;
                                    System.out.println("Podaj Ulice: ");
                                    ulica = dodajUlice.next();
                                    pr.setString(4, ulica);

                                    pr.executeUpdate();
                                    pr.close();


                                    System.out.println("DODANO " + id + " " + nazwa + " " + miasto + " " + ulica);

                                } catch (SQLException e) {
                                    System.err.println("Blad przy DODAWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 2: {
                                try {

                                    PreparedStatement pr = conn.prepareStatement("Insert into samochod(idsamochod,marka,typ,kolor,lokal_idlokal) values (?,?,?,?,?);");
                                    Scanner dodajID = new Scanner(System.in);
                                    int id = 0;
                                    System.out.println("Podaj ID: ");
                                    id = dodajID.nextInt();
                                    pr.setInt(1, id);

                                    Scanner dodajNazwe = new Scanner(System.in);
                                    String nazwa;
                                    System.out.println("Podaj Marke: ");
                                    nazwa = dodajNazwe.next();
                                    pr.setString(2, nazwa);

                                    Scanner dodajMiasto = new Scanner(System.in);
                                    String miasto;
                                    System.out.println("Podaj Typ: ");
                                    miasto = dodajMiasto.next();
                                    pr.setString(3, miasto);

                                    Scanner dodajUlice = new Scanner(System.in);
                                    String ulica;
                                    System.out.println("Podaj Kolor: ");
                                    ulica = dodajUlice.next();
                                    pr.setString(4, ulica);

                                    Scanner dodajDo = new Scanner(System.in);
                                    int Do;
                                    System.out.println("Podaj ID LOKALU do jakiego dopisujemy SAMOCHOD: ");
                                    Do = dodajDo.nextInt();
                                    pr.setInt(5, Do);

                                    pr.executeUpdate();
                                    pr.close();


                                    System.out.println("DODANO " + id + " " + nazwa + " " + miasto + " " + ulica);

                                } catch (SQLException e) {
                                    System.err.println("Blad przy DODAWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 3: {
                                try {

                                    PreparedStatement pr = conn.prepareStatement("Insert into serwisant(idserwisant,imie,nazwisko,pesel,serwis_idserwis) values (?,?,?,?,?);");
                                    Scanner dodajID = new Scanner(System.in);
                                    int id = 0;
                                    System.out.println("Podaj ID: ");
                                    id = dodajID.nextInt();
                                    pr.setInt(1, id);

                                    Scanner dodajNazwe = new Scanner(System.in);
                                    String nazwa;
                                    System.out.println("Podaj Imie: ");
                                    nazwa = dodajNazwe.next();
                                    pr.setString(2, nazwa);

                                    Scanner dodajMiasto = new Scanner(System.in);
                                    String miasto;
                                    System.out.println("Podaj Nazwisko: ");
                                    miasto = dodajMiasto.next();
                                    pr.setString(3, miasto);

                                    Scanner dodajUlice = new Scanner(System.in);
                                    String ulica;
                                    System.out.println("Podaj Pesel: ");
                                    ulica = dodajUlice.next();
                                    pr.setString(4, ulica);

                                    Scanner dodajDo = new Scanner(System.in);
                                    int Do;
                                    System.out.println("Podaj ID SERWISU do jakiego dopisujemy SERWISANTA: ");
                                    Do = dodajDo.nextInt();
                                    pr.setInt(5, Do);

                                    pr.executeUpdate();
                                    pr.close();


                                    System.out.println("DODANO " + id + " " + nazwa + " " + miasto + " " + ulica);

                                } catch (SQLException e) {
                                    System.err.println("Blad przy DODAWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 4: {
                                try {

                                    PreparedStatement pr = conn.prepareStatement("Insert into serwis(idserwis,nazwa,miasto,ulica) values (?,?,?,?);");
                                    Scanner dodajID = new Scanner(System.in);
                                    int id = 0;
                                    System.out.println("Podaj ID: ");
                                    id = dodajID.nextInt();
                                    pr.setInt(1, id);

                                    Scanner dodajNazwe = new Scanner(System.in);
                                    String nazwa;
                                    System.out.println("Podaj Nazwe: ");
                                    nazwa = dodajNazwe.next();
                                    pr.setString(2, nazwa);

                                    Scanner dodajMiasto = new Scanner(System.in);
                                    String miasto;
                                    System.out.println("Podaj Miasto: ");
                                    miasto = dodajMiasto.next();
                                    pr.setString(3, miasto);

                                    Scanner dodajUlice = new Scanner(System.in);
                                    String ulica;
                                    System.out.println("Podaj Ulice: ");
                                    ulica = dodajUlice.next();
                                    pr.setString(4, ulica);


                                    pr.executeUpdate();
                                    pr.close();


                                    System.out.println("DODANO " + id + " " + nazwa + " " + miasto + " " + ulica);

                                } catch (SQLException e) {
                                    System.err.println("Blad przy DODAWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 5: {
                                try {

                                    PreparedStatement pr = conn.prepareStatement("Insert into serwis_samochodow(samochod_idsamochod,serwis_idserwis,data_przyjecia,data_oddania,cena) values (?,?,?,?,?);");
                                    Scanner dodajID = new Scanner(System.in);
                                    int id = 0;
                                    System.out.println("Podaj ID SAMOCHODU: ");
                                    id = dodajID.nextInt();
                                    pr.setInt(1, id);

                                    Scanner dodajNazwe = new Scanner(System.in);
                                    String nazwa;
                                    System.out.println("Podaj ID SERWISU: ");
                                    nazwa = dodajNazwe.next();
                                    pr.setString(2, nazwa);

                                    Scanner dodajMiasto = new Scanner(System.in);
                                    Date miasto;
                                    System.out.println("Podaj DATE PRZYJECIA w systemie rrrr-mm-dd: ");
                                    miasto = Date.valueOf(dodajMiasto.next());
                                    pr.setDate(3, miasto);

                                    Scanner dodajUlice = new Scanner(System.in);
                                    Date ulica;
                                    System.out.println("Podaj DATE ODDANIA w systemie rrrr-mm-dd: ");
                                    ulica = Date.valueOf(dodajUlice.next());
                                    pr.setDate(4, ulica);

                                    Scanner dodajDo = new Scanner(System.in);
                                    int Do;
                                    System.out.println("Podaj CENE: ");
                                    Do = dodajDo.nextInt();
                                    pr.setInt(5, Do);

                                    pr.executeUpdate();
                                    pr.close();


                                    System.out.println("DODANO " + id + " " + nazwa + " " + miasto + " " + ulica+ " "+ Do);

                                } catch (SQLException e) {
                                    System.err.println("Blad przy DODAWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }





                        }


                    } while (podDodawanie != 6);
                    break;
                }

                case 4: {
                    Scanner usuwaniePod = new Scanner(System.in);
                    int podUsuwanie = 0;
                    do {
                        System.out.println("------------------------------------------------------");
                        System.out.println("1) Usun z tabeli LOKAL");
                        System.out.println("2) Usun z tabeli SAMOCHOD");
                        System.out.println("3) Usun z tabeli SERWISANT");
                        System.out.println("4) Usun z tabeli SERWIS");
                        System.out.println("5) Usun z tabeli SERWIS_SAMOCHODOW");
                        System.out.println("6) COFNIJ");
                        System.out.print("Co chcesz zrobic ? Wybierz numer: ");
                        podUsuwanie = usuwaniePod.nextInt();

                        switch (podUsuwanie) {

                            case 1: {
                                Scanner wybor1 = new Scanner(System.in);

                                int rekord = 0;


                                System.out.print("Podaj rekord: ");
                                rekord = wybor1.nextInt();

                                try {
                                    sql = "DELETE FROM lokal WHERE idlokal= " + rekord;
                                    stmt.executeUpdate(sql);
                                    System.out.println("USUNIETO");

                                } catch (SQLException e) {
                                    System.err.println("Blad przy USUWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 2: {
                                Scanner wybor1 = new Scanner(System.in);
                                int rekord = 0;


                                System.out.print("Podaj rekord: ");
                                rekord = wybor1.nextInt();

                                try {
                                    sql = "DELETE FROM samochod WHERE idsamochod= " + rekord;
                                    stmt.executeUpdate(sql);
                                    System.out.println("USUNIETO");

                                } catch (SQLException e) {
                                    System.err.println("Blad przy USUWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 3: {
                                Scanner wybor1 = new Scanner(System.in);
                                int rekord = 0;


                                System.out.print("Podaj rekord: ");
                                rekord = wybor1.nextInt();

                                try {
                                    sql = "DELETE FROM serwisant WHERE idserwisant= " + rekord;
                                    stmt.executeUpdate(sql);
                                    System.out.println("USUNIETO");

                                } catch (SQLException e) {
                                    System.err.println("Blad przy USUWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 4: {
                                Scanner wybor1 = new Scanner(System.in);
                                int rekord = 0;


                                System.out.print("Podaj rekord: ");
                                rekord = wybor1.nextInt();

                                try {
                                    sql = "DELETE FROM serwis WHERE idserwis= " + rekord;
                                    stmt.executeUpdate(sql);
                                    System.out.println("USUNIETO");

                                } catch (SQLException e) {
                                    System.err.println("Blad przy USUWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case 5: {
                                Scanner wybor1 = new Scanner(System.in);
                                int rekord = 0;


                                System.out.print("Podaj rekord: ");
                                rekord = wybor1.nextInt();

                                try {
                                    sql = "DELETE FROM serwis_samochodow WHERE idlokal= " + rekord;
                                    stmt.executeUpdate(sql);
                                    System.out.println("USUNIETO");

                                } catch (SQLException e) {
                                    System.err.println("Blad przy USUWANIU");
                                    e.printStackTrace();
                                }
                                break;
                            }

                        }

                    } while (podUsuwanie != 6);
                    break;
                }

            case 5: {
                System.out.println("ZAKONCZONO");

            }
            break;
            default: {
                System.out.println("Nie ma takiego dzialania w MENU");
                break;

            }

        }
    }while(menu!=5);




    }

}
