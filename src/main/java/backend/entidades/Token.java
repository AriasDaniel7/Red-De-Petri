package backend.entidades;

import java.util.Objects;

/**
 *
 * @author Daniel Arias
 */
public class Token {

    private String id;
    private String color;

    public Token(String id, String color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.color);
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
        final Token other = (Token) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.color, other.color);
    }

    @Override
    public Token clone() {
        return new Token(this.id, this.color);
    }

    @Override
    public String toString() {
        return "Token{" + "id=" + id + ", color=" + color + '}';
    }

}
