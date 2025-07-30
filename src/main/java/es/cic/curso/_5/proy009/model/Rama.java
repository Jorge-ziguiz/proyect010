package es.cic.curso._5.proy009.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "rama")
public class Rama {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Version
        private Long version;

        private double longitud;
        private int nivel;
        private boolean tieneHojas;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "arbol")
        private Arbol arbol;

        //Getters And Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public double getLongitud() {
            return longitud;
        }

        public void setLongitud(double longitud) {
            this.longitud = longitud;
        }

        public int getNivel() {
            return nivel;
        }

        public void setNivel(int nivel) {
            this.nivel = nivel;
        }

        public boolean isTieneHojas() {
            return tieneHojas;
        }

        public void setTieneHojas(boolean tieneHojas) {
            this.tieneHojas = tieneHojas;
        }

        public Arbol getArbol() {
            return arbol;
        }

        public void setArbol(Arbol arbol) {
            this.arbol = arbol;
        }

        
}
