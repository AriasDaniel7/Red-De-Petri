package backend.entidades;

import java.util.Objects;

/**
 *
 * @author Daniel Arias
 */
public class Lugar {

    private String id;
    private Integer marcas;

    public Lugar(String id, Integer marcas) {
        this.id = id;
        this.marcas = marcas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMarcas() {
        return marcas;
    }

    public void setMarcas(Integer marcas) {
        this.marcas = marcas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.marcas);
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
        final Lugar other = (Lugar) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.marcas, other.marcas);
    }

    @Override
    public String toString() {
        return "Lugar{" + "id=" + id + ", marcas=" + marcas + '}';
    }

}
