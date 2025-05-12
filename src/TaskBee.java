import java.io.*;
import java.util.*;

public class TaskBee {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Tarea> tareas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        leerUsuarios();
        leerTareas();
        mostrarMenuPrincipal();
        guardarUsuarios();
        guardarTareas();
    }

    private void mostrarMenuPrincipal() {
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("=== Bienvenido A TASKBEE ===");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Registrar administrador");
            System.out.println("3) Salir");
            System.out.print("Opción: ");

            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese solo números.");
                continue;
            }

            if (opcion == 1) {
                iniciarSesion();
            } else if (opcion == 2) {
                registrarAdministrador();
            } else if (opcion == 3) {
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opción no válida. Intente otra vez.");
            }
        }
    }

    private void leerUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                boolean admin = datos.length == 4;
                Usuario u = new Usuario(datos[0], datos[1], datos[2], admin);
                usuarios.add(u);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer user.txt");
        }
    }

    private void leerTareas() {
        try (BufferedReader br = new BufferedReader(new FileReader("tareas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                boolean completada = Boolean.parseBoolean(datos[4]);
                Tarea t = new Tarea(datos[0], datos[1], datos[2], datos[3], completada, datos[5]);
                tareas.add(t);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer tareas.txt");
        }
    }

    private void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter("user.txt")) {
            for (Usuario u : usuarios) {
                pw.println(u.toArchivo());
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar user.txt");
        }
    }

    private void guardarTareas() {
        try (PrintWriter pw = new PrintWriter("tareas.txt")) {
            for (Tarea t : tareas) {
                pw.println(t.toArchivo());
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar tareas.txt");
        }
    }

    private void iniciarSesion() {
        System.out.print("Nombre de usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String clave = scanner.nextLine();

        boolean validado = false;

        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(usuario) && u.getContrasena().equals(clave)) {
                validado = true;
                if (u.isAdmin()) {
                    menuAdmin(u);
                } else {
                    menuUsuario(u);
                }
                break;
            }
        }

        if (!validado) {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    private void registrarAdministrador() {
        System.out.print("Nuevo nombre de usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        String clave = generarContraseña();
        Usuario admin = new Usuario(nombre, clave, correo, true);
        usuarios.add(admin);

        System.out.println("Administrador creado. Contraseña generada: " + clave);
    }

    private String generarContraseña() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder clave = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int pos = rand.nextInt(letras.length());
            clave.append(letras.charAt(pos));
        }

        return clave.toString();
    }

    private void menuAdmin(Usuario admin) {
        int opcion = 0;
        while (opcion != 3) {
            System.out.println("--- MENÚ ADMIN ---");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Agregar tarea a usuario");
            System.out.println("3. Cerrar sesión");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Debe ser un número.");
                continue;
            }

            if (opcion == 1) {
                System.out.print("Nombre de usuario: ");
                String nombre = scanner.nextLine();
                System.out.print("Correo: ");
                String correo = scanner.nextLine();
                String clave = generarContraseña();
                Usuario nuevo = new Usuario(nombre, clave, correo, false);
                usuarios.add(nuevo);
                System.out.println("Usuario registrado. Contraseña: " + clave);
            } else if (opcion == 2) {
                System.out.print("Correo del usuario: ");
                String correo = scanner.nextLine();
                System.out.print("Título: ");
                String titulo = scanner.nextLine();
                System.out.print("Descripción: ");
                String descripcion = scanner.nextLine();
                System.out.print("Fecha creación: ");
                String fechaCreacion = scanner.nextLine();
                System.out.print("Fecha límite: ");
                String fechaLimite = scanner.nextLine();
                Tarea nueva = new Tarea(titulo, descripcion, fechaCreacion, fechaLimite, false, correo);
                tareas.add(nueva);
                System.out.println("Tarea agregada exitosamente.");
            } else if (opcion == 3) {
                System.out.println("Sesión cerrada.");
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private void menuUsuario(Usuario usuario) {
        int opcion = 0;
        while (opcion != 3) {
            System.out.println("\n--- MENÚ USUARIO ---");
            System.out.println("1. Agregar tarea propia");
            System.out.println("2. Ver mis tareas");
            System.out.println("3. Cerrar sesión");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Debe ser un número.");
                continue;
            }

            if (opcion == 1) {
                System.out.print("Título: ");
                String titulo = scanner.nextLine();
                System.out.print("Descripción: ");
                String descripcion = scanner.nextLine();
                System.out.print("Fecha creación: ");
                String fechaCreacion = scanner.nextLine();
                System.out.print("Fecha límite: ");
                String fechaLimite = scanner.nextLine();
                Tarea nueva = new Tarea(titulo, descripcion, fechaCreacion, fechaLimite, false, usuario.getCorreo());
                tareas.add(nueva);
                System.out.println("Tarea agregada correctamente.");
            } else if (opcion == 2) {
                boolean tieneTareas = false;
                for (Tarea t : tareas) {
                    if (t.getCorreo().equals(usuario.getCorreo())) {
                        tieneTareas = true;
                        System.out.println("\nTítulo: " + t.getTitulo());
                        System.out.println("Descripción: " + t.getDescripcion());
                        System.out.println("Fecha creación: " + t.getFechaCreacion());
                        System.out.println("Fecha límite: " + t.getFechaLimite());
                        System.out.println("Completada: " + (t.estaCompleta() ? "Sí" : "No"));
                    }
                }
                if (!tieneTareas) {
                    System.out.println("No tienes tareas asignadas.");
                }
            } else if (opcion == 3) {
                System.out.println("Sesión cerrada.");
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }
}
