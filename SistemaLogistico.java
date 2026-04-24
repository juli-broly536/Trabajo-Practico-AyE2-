import java.util.ArrayList;

public class SistemaLogistico {
    private ArrayList<Paquete<?>> inventario;
    private Camion camion;
    private CentroDistribucion centro;

    public SistemaLogistico() {
        inventario = new ArrayList<>();
        camion = new Camion();
        centro = new CentroDistribucion();
    }

    public void cargarInventarioInicial(String ruta) {
        ArrayList<Paquete<String>> paquetesIniciales = InventarioService.cargarDesdeJson(ruta);
        for (Paquete<String> paquete : paquetesIniciales) {
            agregarPaqueteManual(paquete);
        }
    }

    // O(n), porque valida que el ID sea unico antes de insertar.
    public boolean agregarPaqueteManual(Paquete<?> paquete) {
        if (buscarPaquetePorId(paquete.getId()) != null) {
            return false;
        }

        inventario.add(paquete);
        return true;
    }

    // O(n)
    public Paquete<?> buscarPaquetePorId(int id) {
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getId() == id) {
                return inventario.get(i);
            }
        }
        return null;
    }

    // O(n)
    public boolean cargarPaqueteEnCamion(int id) {
        Paquete<?> paquete = buscarPaquetePorId(id);

        if (paquete == null) {
            return false;
        }

        camion.cargarPaquete(paquete);
        inventario.remove(paquete);
        return true;
    }

    public Paquete<?> deshacerUltimaCargaCamion() {
        Paquete<?> paquete = camion.deshacerUltimaCarga();

        if (paquete != null) {
            inventario.add(paquete);
        }

        return paquete;
    }

    public Paquete<?> descargarDelCamionAlCentro() {
        Paquete<?> paquete = camion.descargarPaquete();

        if (paquete != null) {
            centro.recibirPaquete(paquete);
        }

        return paquete;
    }

    public Paquete<?> procesarSiguienteEnCentro() {
        return centro.procesarSiguiente();
    }

    public void mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("No hay paquetes en inventario.");
            return;
        }

        System.out.println("=== Inventario ===");
        for (int i = 0; i < inventario.size(); i++) {
            System.out.println(inventario.get(i));
        }
    }

    public void mostrarCamion() {
        camion.mostrarCamion();
    }

    public void mostrarCentro() {
        centro.mostrarColas();
    }
}