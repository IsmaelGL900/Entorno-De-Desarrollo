package modelos;

import java.util.List;

public class Equipo {

    private String nombreEquipo;
    private List<PersonajeMarvel> miembros;

    public Equipo(String nombreEquipo, List<PersonajeMarvel> miembros) {
        this.nombreEquipo = nombreEquipo;
        this.miembros = miembros;
    }


    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public List<PersonajeMarvel> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<PersonajeMarvel> miembros) {
        this.miembros = miembros;
    }

    public void agregarMiembro(PersonajeMarvel nuevoMiembro) {
        miembros.add(nuevoMiembro);
    }

    public void quitarMiembro(PersonajeMarvel miembro) {
        miembros.remove(miembro);
    }

    public double poderDelEquipo() {
        if (miembros.isEmpty()) {
            return 0;
        }
        double sumaPoderEquipo = 0;
        for (PersonajeMarvel miembro : miembros) {
            sumaPoderEquipo += miembro.getPoder();
        }
        return sumaPoderEquipo / miembros.size();
    }

    @Override
    public String toString() {
        return "Equipo " + nombreEquipo + ", " + "miembros: " + miembros + ".";
    }
}
