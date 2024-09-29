package backend.entidades;

import java.util.Objects;

/**
 *
 * @author Daniel Arias
 */
public class Arco {

    private String desde;
    private String hacia;
    private Integer peso;

    public Arco(String desde, String hacia, Integer peso) {
        this.desde = desde;
        this.hacia = hacia;
        this.peso = peso;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHacia() {
        return hacia;
    }

    public void setHacia(String hacia) {
        this.hacia = hacia;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.desde);
        hash = 79 * hash + Objects.hashCode(this.hacia);
        hash = 79 * hash + Objects.hashCode(this.peso);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Arco other = (Arco) obj;
        if (!Objects.equals(this.desde, other.desde)) {
            return false;
        }
        if (!Objects.equals(this.hacia, other.hacia)) {
            return false;
        }
        return Objects.equals(this.peso, other.peso);
    }

    @Override
    public String toString() {
        return "Arco{" + "desde=" + desde + ", hacia=" + hacia + ", peso=" + peso + '}';
    }

}
