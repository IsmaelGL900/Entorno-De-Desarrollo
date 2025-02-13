package modelos;

import java.util.Random;

public class Enfrentamiento {

    private Equipo equipo1;
    private Equipo equipo2;

    public Enfrentamiento(Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

    public Enfrentamiento() {
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public String simularEnfrentamiento() {
        Random random = new Random();
        double poderequipo1 = equipo1.poderDelEquipo() * (1.0 + random.nextDouble());
        double poderequipo2 = equipo2.poderDelEquipo() * (1.0 + random.nextDouble());
        if (poderequipo1 > poderequipo2) {
            return "Ha ganado el equipo: " + equipo1.getNombreEquipo() +". " + "Con los miembros: " + equipo1.getMiembros() +":"  ;
        } else {
            return "Ha ganado el equipo: " + equipo2.getNombreEquipo() +". " + "Con los miembros: " + equipo2.getMiembros() +":"  ;
        }
    }
}
