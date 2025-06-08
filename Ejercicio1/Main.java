import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Usuario {
    private String nombre;
    private int edad;
    private String ciudad;

    public Usuario(String nombre, int edad, String ciudad) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiudad() {
        return ciudad;
    }
}

class ControladorUsuarios {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static double calcularPromedioEdad() {
        if (usuarios.isEmpty()) return 0;
        int sumaEdades = 0;
        for (Usuario usuario : usuarios) {
            sumaEdades += usuario.getEdad();
        }
        return (double) sumaEdades / usuarios.size();
    }

    public static Usuario buscarUsuarioPorNombre(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    public static ArrayList<Usuario> buscarUsuarioPorCiudad(String ciudad) {
        ArrayList<Usuario> encontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getCiudad().equalsIgnoreCase(ciudad)) {
                encontrados.add(usuario);
            }
        }
        return encontrados;
    }

    public static Usuario elegirUsuarioDestacado() {
        Random random = new Random();
        return usuarios.get(random.nextInt(usuarios.size()));
    }

    public static int getTotalUsuarios() {
        return usuarios.size();
    }

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios; // Método para acceder a la lista de usuarios
    }

    static class MensajeDecorativo {
        public static void mostrarMensaje(String mensaje) {
            System.out.println("********** " + mensaje + " **********");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ControladorUsuarios.MensajeDecorativo.mostrarMensaje("Registro de Usuarios");

        while (true) {
            System.out.print("Ingrese el nombre del usuario (o 'salir' para terminar): ");
            String nombre = scanner.nextLine();
            if (nombre.equalsIgnoreCase("salir")) {
                break;
            }

            int edad = 0;
            while (true) {
                try {
                    System.out.print("Ingrese la edad del usuario: ");
                    edad = Integer.parseInt(scanner.nextLine());
                    if (edad < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Edad no válida. Por favor, ingrese un número entero positivo.");
                }
            }

            System.out.print("Ingrese la ciudad del usuario: ");
            String ciudad = scanner.nextLine();

            Usuario usuario = new Usuario(nombre, edad, ciudad);
            ControladorUsuarios.registrarUsuario(usuario);
        }

        ControladorUsuarios.MensajeDecorativo.mostrarMensaje("Resumen de Usuarios");
        System.out.println("Total de usuarios registrados: " + ControladorUsuarios.getTotalUsuarios());
        System.out.println("Promedio de edad: " + ControladorUsuarios.calcularPromedioEdad());

        String nombreMasLargo = "";
        for (Usuario usuario : ControladorUsuarios.getUsuarios()) {
            if (usuario.getNombre().length() > nombreMasLargo.length()) {
                nombreMasLargo = usuario.getNombre();
            }
        }
        System.out.println("Nombre más largo ingresado: " + nombreMasLargo);

        if (!ControladorUsuarios.getUsuarios().isEmpty()) {
            Usuario usuarioDestacado = ControladorUsuarios.elegirUsuarioDestacado();
            System.out.println("Usuario destacado: " + usuarioDestacado.getNombre() + ", Edad: " + usuarioDestacado.getEdad() + ", Ciudad: " + usuarioDestacado.getCiudad());
        } else {
            System.out.println("No hay usuarios registrados para elegir un destacado.");
        }

        scanner.close();
    }
}
