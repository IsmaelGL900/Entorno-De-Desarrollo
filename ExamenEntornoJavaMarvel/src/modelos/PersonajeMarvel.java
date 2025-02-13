package modelos;

public class PersonajeMarvel {
    private String nombre;
    private String alias;
    private boolean esHeroe;
    private String poderPrincipal;
    private String afiliacion;
    private double poder;

    public PersonajeMarvel(String nombre, String alias, boolean esHeroe, String poderPrincipal, String afiliacion, double poder) {
        this.nombre = nombre;
        this.alias = alias;
        this.esHeroe = esHeroe;
        this.poderPrincipal = poderPrincipal;
        this.afiliacion = afiliacion;
        this.poder = poder;
    }


    public String getNombre() {
        return nombre;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isEsHeroe() {
        return esHeroe;
    }

    public String getPoderPrincipal() {
        return poderPrincipal;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public double getPoder() {
        return poder;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEsHeroe(boolean esHeroe) {
        this.esHeroe = esHeroe;
    }

    public void setPoderPrincipal(String poderPrincipal) {
        this.poderPrincipal = poderPrincipal;
    }

    public void setAfiliacion(String afiliacion) {
        this.afiliacion = afiliacion;
    }

    public void setPoder(double poder) {
        this.poder = poder;
    }

    @Override
    public String toString() {
        return nombre + " (" + alias + "). Su poder principal es " + poderPrincipal + ". Su afiliacion, " + afiliacion + ". " + (esHeroe ? "Es" : "No es un") + " heroe. Su poder es de " + poder + ".";
    }
}
