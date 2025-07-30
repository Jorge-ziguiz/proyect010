package es.cic.curso._5.proy009.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity //Marcamos que es una entidad 
@Table(name = "arbon")
public class Arbol {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Version
    private long version;

    private String especie;
    private int edad;
    private double altura;

    private List<Rama> ramas = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void agregarRama(Rama rama) {
        ramas.add(rama);
        rama.setArbol(this); // importante!
    }

    public void quitarRama(Rama rama) {
        ramas.remove(rama);
        rama.setArbol(null);
    }
    

}
