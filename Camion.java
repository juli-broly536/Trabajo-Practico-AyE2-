import java.util.Stack;

public class Camion {
    private Stack<Paquete<?>> paquetes;

    public Camion() {
        paquetes = new Stack<>();
    }

    // O(1)
    public void cargarPaquete(Paquete<?> paquete) {
        paquetes.push(paquete);
    }

    // O(1)
    public Paquete<?> descargarPaquete() {
        if (paquetes.isEmpty()) {
            return null;
        }
        return paquetes.pop();
    }

    // O(1)
    public Paquete<?> deshacerUltimaCarga() {
        if (paquetes.isEmpty()) {
            return null;
        }
        return paquetes.pop();
    }

    public boolean estaVacio() {
        return paquetes.isEmpty();
    }

    public int cantidadPaquetes() {
        return paquetes.size();
    }

    public void mostrarCamion() {
        if (paquetes.isEmpty()) {
            System.out.println("El camion esta vacio.");
            return;
        }

        System.out.println("Paquetes en el camion:");

        for (Paquete<?> p : paquetes) {
            System.out.println(p);
        }
    }
}