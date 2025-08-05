package es.cic.curso._5.proy009.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
        //@JoinColumn(name = "arbol_id")
        @JsonIgnore
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

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            long temp;
            temp = Double.doubleToLongBits(longitud);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            result = prime * result + nivel;
            result = prime * result + (tieneHojas ? 1231 : 1237);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            else if (obj == null)
                return false;
            else if (getClass() != obj.getClass())
                return false;
            Rama other = (Rama) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            else if (longitud != other.longitud)
                return false;
            else if (nivel != other.nivel)
                return false;
            
            return true;
        }

        
}
