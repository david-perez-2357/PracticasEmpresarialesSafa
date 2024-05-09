package api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EnvReader {

    private final Map<String, String> envVariables;

    public EnvReader(String filePath) throws IOException {
        envVariables = new HashMap<>();
        filePath = filePath.replace("file:/", "");
        loadEnv(filePath);
    }

    private void loadEnv(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir la línea en nombre de variable y valor utilizando "=" como separador
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    // Agregar la variable y su valor al mapa de variables de entorno
                    envVariables.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    public String getValue(String key) {
        return envVariables.get(key);
    }
}
