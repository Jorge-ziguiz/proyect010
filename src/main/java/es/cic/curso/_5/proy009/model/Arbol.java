package es.cic.curso._5.proy009.model;

import java.util.ArrayList;
import java.util.List;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity //Marcamos que es una entidad 
@Table(name = "arbol")
public class Arbol {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Version
    private long version;

    private String especie;
    private int edad;
    private double altura;

    @OneToMany(
        mappedBy        = "arbol",          //LO dirige la propia clase
        cascade         = CascadeType.ALL,  //cascade : si
        orphanRemoval   = true,             //Borramos los que no tengan referencia
        fetch           = FetchType.LAZY    //cargamos solo al llegar al controller(?)
    )
    private List<Rama> listaRamas = new ArrayList<>();


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
    public List<Rama> getRamas() {
        return listaRamas;
    }
    public void setRamas(List<Rama> listaRamas) {
        this.listaRamas = listaRamas;
    }

    //Para poder añadir las ramas a la lista 
    public void addRama(Rama rama) {
        listaRamas.add(rama);
        rama.setArbol(this); // importante!
    }
    public void removeRama(Rama rama) {
        listaRamas.remove(rama);
        rama.setArbol(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rama other = (Rama) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "Arbol [id = " + id + ", especie = " + especie + ", edad = " + edad +
                ", altura =" + altura + ", ramas " + listaRamas +" ]";
    }
}
