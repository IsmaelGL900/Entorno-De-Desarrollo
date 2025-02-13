import modelos.Enfrentamiento;
import modelos.Equipo;
import modelos.PersonajeMarvel;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PersonajeMarvel spiderman = new PersonajeMarvel("Peter Parker", "Spider-Man", true, "Sentido aracnido", "avengers", 8.5   );
        PersonajeMarvel ironman = new PersonajeMarvel("Tony Stark", "Iron Man", true, "Genio inventor con armadura avanzada", "avengers", 9.2   );
        PersonajeMarvel magneto =  new PersonajeMarvel("Erik Lehnsherr", "Magneto", false, "Control del magnetismo", "x-men", 9.5);
        PersonajeMarvel capitanamerica = new PersonajeMarvel("Steve Rogers", "Capitán América", true, "Fuerza sobrehumana, agilidad, y resistencia", "avengers", 8.8   );
        PersonajeMarvel blackwidow = new PersonajeMarvel("Natasha Romanoff", "Black Widow", true, "Espía experta, artes marciales", "avengers", 7.5);
        PersonajeMarvel hulk = new PersonajeMarvel("Bruce Banner", "Hulk", true, "Fuerza sobrehumana, resistencia", "avengers", 9.8);
        PersonajeMarvel thor = new PersonajeMarvel("Thor Odinson", "Thor", true, "Control del trueno y el relámpago, fuerza sobrehumana", "avengers", 9.9);
        PersonajeMarvel scarletwitch = new PersonajeMarvel("Wanda Maximoff", "Scarlet Witch", true, "Manipulación de la magia, alteración de la realidad", "avengers", 10.0);
        PersonajeMarvel loki = new PersonajeMarvel("Loki Laufeyson", "Loki", false, "Ilusionismo, manipulación mágica", "", 8.7);

        Equipo avengers = new Equipo("Avengers", new ArrayList<PersonajeMarvel>());
        Equipo otro = new Equipo("Otro", new ArrayList<PersonajeMarvel>());

        avengers.agregarMiembro(spiderman);
        avengers.agregarMiembro(ironman);
        avengers.agregarMiembro(capitanamerica);
        avengers.agregarMiembro(blackwidow);
        avengers.agregarMiembro(thor);

        otro.agregarMiembro(scarletwitch);
        otro.agregarMiembro(loki);
        otro.agregarMiembro(magneto);
        otro.agregarMiembro(hulk);

        Enfrentamiento enfrentamiento1 = new Enfrentamiento(avengers, otro);
        System.out.println(enfrentamiento1.simularEnfrentamiento());
    }

}

