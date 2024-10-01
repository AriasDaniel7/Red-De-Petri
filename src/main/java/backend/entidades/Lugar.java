package backend.entidades;

import java.util.Objects;
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

    public void postDeserialization() {
        if (this.tokens == null) {
            this.tokens = new ArrayList<>();
            for (int i = 0; i < this.marcas; i++) {
                this.tokens.add(new Token(UUID.randomUUID().toString(), "#000000"));
            }
        }
    }

    public void adjustTokens(int valorDmas, int valorDmenos) {
        int tokenChange = valorDmas - valorDmenos;
        for (Token token : tokens) {
            token.setColor("#000000");
            if (valorDmas > 0 && valorDmenos == valorDmas) {
                token.setColor("#FF0000");
            }
        }

        if (tokenChange > 0) {
            for (int i = 0; i < tokenChange; i++) {
                tokens.add(new Token(UUID.randomUUID().toString(), "#FF0000"));
            }

        } else {
            for (int i = 0; i < -tokenChange; i++) {
                if (!tokens.isEmpty()) {
                    tokens.remove(tokens.size() - 1);
                }
            }
        }

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
    public String toString() {
        return "Lugar{" + "id=" + id + ", marcas=" + marcas + ", tokens=" + tokens + '}';
    }

}
