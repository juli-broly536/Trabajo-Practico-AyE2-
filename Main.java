import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaLogistico sistema = new SistemaLogistico();

        sistema.cargarInventarioInicial("inventario.json");

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    cargarPaqueteManual(scanner, sistema);
                    break;
                case 2:
                    sistema.mostrarInventario();
                    break;
                case 3:
                    cargarPaqueteEnCamion(scanner, sistema);
                    break;
                case 4:
                    deshacerUltimaCarga(sistema);
                    break;
                case 5:
                    sistema.mostrarCamion();
                    break;
                case 6:
                    descargarCamionAlCentro(sistema);
                    break;
                case 7:
                    procesarSiguienteCentro(sistema);
                    break;
                case 8:
                    sistema.mostrarCentro();
                    break;
                case 9:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

            System.out.println();

        } while (opcion != 9);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("====== LOGI - UADE 2026 ======");
        System.out.println("1. Cargar paquete manualmente");
        System.out.println("2. Mostrar inventario");
        System.out.println("3. Cargar paquete en camion");
        System.out.println("4. Deshacer ultima carga del camion");
        System.out.println("5. Mostrar camion");
        System.out.println("6. Descargar paquete del camion al centro");
        System.out.println("7. Procesar siguiente paquete del centro");
        System.out.println("8. Mostrar centro de distribucion");
        System.out.println("9. Salir");
    }

    private static void cargarPaqueteManual(Scanner scanner, SistemaLogistico sistema) {
        int id = leerEntero(scanner, "Ingrese ID del paquete: ");
        double peso = leerDouble(scanner, "Ingrese peso del paquete: ");

        System.out.print("Ingrese destino: ");
        String destino = scanner.nextLine();

        System.out.print("Es urgente? (true/false): ");
        boolean urgente = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Ingrese contenido: ");
        String contenido = scanner.nextLine();

        Paquete<String> paquete = new Paquete<>(id, peso, destino, urgente, contenido);
        boolean agregado = sistema.agregarPaqueteManual(paquete);

        if (agregado) {
            System.out.println("Paquete agregado correctamente al inventario.");
        } else {
            System.out.println("No se pudo agregar. Ya existe un paquete con ese ID.");
        }
    }

    private static void cargarPaqueteEnCamion(Scanner scanner, SistemaLogistico sistema) {
        int id = leerEntero(scanner, "Ingrese el ID del paquete a cargar en camion: ");
        boolean ok = sistema.cargarPaqueteEnCamion(id);

        if (ok) {
            System.out.println("Paquete cargado en el camion.");
        } else {
            System.out.println("No se encontro un paquete con ese ID.");
        }
    }

    private static void deshacerUltimaCarga(SistemaLogistico sistema) {
        Paquete<?> paquete = sistema.deshacerUltimaCargaCamion();

        if (paquete == null) {
            System.out.println("No hay cargas para deshacer.");
        } else {
            System.out.println("Se deshizo la ultima carga: " + paquete);
        }
    }

    private static void descargarCamionAlCentro(SistemaLogistico sistema) {
        Paquete<?> paquete = sistema.descargarDelCamionAlCentro();

        if (paquete == null) {
            System.out.println("El camion esta vacio.");
        } else {
            System.out.println("Paquete descargado al centro: " + paquete);
        }
    }

    private static void procesarSiguienteCentro(SistemaLogistico sistema) {
        Paquete<?> paquete = sistema.procesarSiguienteEnCentro();

        if (paquete == null) {
            System.out.println("No hay paquetes en el centro.");
        } else {
            System.out.println("Paquete procesado: " + paquete);
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }
}
