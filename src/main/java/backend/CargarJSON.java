package backend;

import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import backend.entidades.Red;
import java.io.IOException;

/**
 *
 * @author Daniel Arias
 */
public class CargarJSON {

    private File file;
    private Gson gson;

    public CargarJSON(File file) {
        this.file = file;
        this.gson = new Gson();
    }

    public Red cargar() {
        try (FileReader reader = new FileReader(this.file)) {
            // Convertir JSON a objeto
            Red red = gson.fromJson(reader, Red.class);
            return red;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
