package cl.ucn.consola;

import cl.ucn.dominio.Tarea;
import cl.ucn.repositorio.RepositorioTarea;
import cl.ucn.repositorio.RepositorioTareaImpl;
import cl.ucn.servicio.ServicioTarea;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
        RepositorioTarea repo = new RepositorioTareaImpl(emf.createEntityManager());
        ServicioTarea servicio = new ServicioTarea(repo);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Gestor de Tareas ===");
            System.out.println("1. Crear nueva tarea");
            System.out.println("2. Ver todas las tareas");
            System.out.println("3. Ver tareas pendientes");
            System.out.println("4. Marcar tarea como completada");
            System.out.println("5. Eliminar tarea");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descripción (opcional): ");
                    String descripcion = scanner.nextLine();

                    System.out.print("Fecha de vencimiento (YYYY-MM-DD, opcional): ");
                    String fechaInput = scanner.nextLine();
                    LocalDate fecha = fechaInput.isEmpty() ? null : LocalDate.parse(fechaInput);

                    Tarea nueva = servicio.crearTarea(titulo, descripcion, fecha);
                    System.out.println("✅ Tarea creada con ID: " + nueva.getId());
                    break;

                case 2:
                    List<Tarea> todas = servicio.obtenerTodasLasTareas();
                    if (todas.isEmpty()) {
                        System.out.println("No hay tareas registradas.");
                    } else {
                        todas.forEach(MainApp::mostrarTarea);
                    }
                    break;

                case 3:
                    List<Tarea> pendientes = servicio.obtenerTareasPendientes();
                    if (pendientes.isEmpty()) {
                        System.out.println("No hay tareas pendientes.");
                    } else {
                        pendientes.forEach(MainApp::mostrarTarea);
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el ID de la tarea a marcar como completada: ");
                    Long idCompletar = Long.parseLong(scanner.nextLine());
                    try {
                        servicio.marcarCompletada(idCompletar);
                        System.out.println("✅ Tarea marcada como completada.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Ingrese el ID de la tarea a eliminar: ");
                    Long idEliminar = Long.parseLong(scanner.nextLine());
                    servicio.deleteTask(idEliminar);
                    System.out.println("Tarea eliminada.");
                    break;

                case 6:
                    System.out.println("👋 Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);

        emf.close();
        scanner.close();
    }

    private static void mostrarTarea(Tarea tarea) {
        System.out.printf("ID: %d | Título: %s | Completada: %s | Vence: %s\n",
                tarea.getId(),
                tarea.getTitulo(),
                tarea.isCompletada() ? "Si" : "No",
                tarea.getFechaFinalizacion() != null ? tarea.getFechaFinalizacion() : "Sin fecha");
    }
}
