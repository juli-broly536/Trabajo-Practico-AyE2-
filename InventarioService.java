import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InventarioService {

    public static ArrayList<Paquete<String>> cargarDesdeJson(String ruta) {
        ArrayList<Paquete<String>> paquetes = new ArrayList<>();

        try {
            String json = Files.readString(Paths.get(ruta));

            String contenidoInterno = obtenerContenidoEntreCorchetes(json);
            if (contenidoInterno.isEmpty()) {
                return paquetes;
            }

            String[] objetos = contenidoInterno.split("\\},\\s*\\{");

            for (String objeto : objetos) {
                String limpio = objeto.replace("{", "").replace("}", "").trim();

                int id = Integer.parseInt(obtenerValor(limpio, "id"));
                double peso = Double.parseDouble(obtenerValor(limpio, "peso"));
                String destino = sacarComillas(obtenerValor(limpio, "destino"));
                boolean urgente = Boolean.parseBoolean(obtenerValor(limpio, "urgente"));
                String contenido = sacarComillas(obtenerValor(limpio, "contenido"));

                paquetes.add(new Paquete<>(id, peso, destino, urgente, contenido));
            }

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo inventario.json");
        } catch (Exception e) {
            System.out.println("Error al interpretar el JSON. Revisar formato del archivo.");
        }

        return paquetes;
    }

    private static String obtenerContenidoEntreCorchetes(String json) {
        int inicio = json.indexOf("[");
        int fin = json.lastIndexOf("]");

        if (inicio == -1 || fin == -1 || fin <= inicio) {
            return "";
        }

        return json.substring(inicio + 1, fin).trim();
    }

    private static String obtenerValor(String objeto, String clave) {
        String[] pares = objeto.split(",");

        for (String par : pares) {
            String[] claveValor = par.split(":", 2);

            if (claveValor.length == 2) {
                String claveActual = sacarComillas(claveValor[0].trim());
                String valorActual = claveValor[1].trim();

                if (claveActual.equals(clave)) {
                    return valorActual;
                }
            }
        }

        return "";
    }

    private static String sacarComillas(String texto) {
        return texto.replace("\"", "").trim();
    }
}