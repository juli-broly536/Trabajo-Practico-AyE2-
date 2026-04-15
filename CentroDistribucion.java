import java.util.LinkedList;
import java.util.Queue;

public class CentroDistribucion {
    private Queue<Paquete<?>> colaPrioritaria;
    private Queue<Paquete<?>> colaEstandar;

    public CentroDistribucion() {
        colaPrioritaria = new LinkedList<>();
        colaEstandar = new LinkedList<>();
    }

    // O(1)
    public void recibirPaquete(Paquete<?> paquete) {
        if (paquete.esPrioritario()) {
            colaPrioritaria.offer(paquete);
        } else {
            colaEstandar.offer(paquete);
        }
    }

    // O(1)
    public Paquete<?> procesarSiguiente() {
        if (!colaPrioritaria.isEmpty()) {
            return colaPrioritaria.poll();
        }

        if (!colaEstandar.isEmpty()) {
            return colaEstandar.poll();
        }

        return null;
    }

    public boolean estaVacio() {
        return colaPrioritaria.isEmpty() && colaEstandar.isEmpty();
    }

    public void mostrarColas() {
        System.out.println("=== Cola prioritaria ===");
        if (colaPrioritaria.isEmpty()) {
            System.out.println("Sin paquetes prioritarios.");
        } else {
            for (Paquete<?> p : colaPrioritaria) {
                System.out.println(p);
            }
        }

        System.out.println("=== Cola estandar ===");
        if (colaEstandar.isEmpty()) {
            System.out.println("Sin paquetes estandar.");
        } else {
            for (Paquete<?> p : colaEstandar) {
                System.out.println(p);
            }
        }
    }
}