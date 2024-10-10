package backend.entidades;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.ArrayList;

/**
 *
 * @author Daniel Arias
 */
public class Lugar {

    private String id;
    private Integer marcas;
    private ArrayList<Token> tokens;

    public Lugar(String id, Integer marcas) {
        this.id = id;
        this.marcas = marcas;
        this.tokens = new ArrayList<>();
    }

    public Lugar(String id, Integer marcas, ArrayList<Token> tokens) {
        this.id = id;
        this.marcas = marcas;
        this.tokens = tokens;
    }

    public void postDeserialization() {
        if (this.tokens == null) {
            this.tokens = new ArrayList<>();
            for (int i = 0; i < this.marcas; i++) {
                String color = colorRandom();
                this.tokens.add(new Token(UUID.randomUUID().toString().substring(0, 3), color));
            }
        }
    }

    public void removeToken(Token token) {
        this.tokens.remove(token);
    }

    public String colorRandom() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return String.format("#%02x%02x%02x", r, g, b);
    }

    public void addToken(Token token) {
        this.tokens.add(token);
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

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.marcas);
        hash = 89 * hash + Objects.hashCode(this.tokens);
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
        if (!Objects.equals(this.marcas, other.marcas)) {
            return false;
        }
        return Objects.equals(this.tokens, other.tokens);
    }

    @Override
    public Lugar clone() {
        ArrayList<Token> tokensClone = new ArrayList<>();
        for (Token token : tokens) {
            tokensClone.add(token.clone());
        }
        return new Lugar(this.id, this.marcas, tokensClone);
    }

    @Override
    public String toString() {
        return "Lugar{" + "id=" + id + ", marcas=" + marcas + ", tokens=" + tokens + '}';
    }

}
