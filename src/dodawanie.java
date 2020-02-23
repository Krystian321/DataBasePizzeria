public class dodawanie {
    private String nazwa;
    private String miasto;
    private String ulica;

    public dodawanie(String nazwa, String miasto, String ulica) {
        this.nazwa = nazwa;
        this.miasto = miasto;
        this.ulica = ulica;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }



}