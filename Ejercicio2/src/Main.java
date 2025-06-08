
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static abstract class Figura {
        public abstract double calcularArea(double parametro1);
        public abstract double calcularArea(double parametro1, double parametro2);
        public abstract double calcularArea(double parametro1, double parametro2, double parametro3);
    }

    public static class Circulo extends Figura {
        private double radio;

        @Override
        public double calcularArea(double radio) {
            this.radio = radio;
            return Math.PI * radio * radio;
        }

        @Override
        public double calcularArea(double parametro1, double parametro2) { 
            return 0; 
        }
        
        @Override
        public double calcularArea(double parametro1, double parametro2, double parametro3) { 
            return 0; 
        }
    }

    public static class Rectangulo extends Figura {
        private double base;
        private double altura;

        @Override
        public double calcularArea(double base, double altura) {
            this.base = base;
            this.altura = altura;
            return base * altura;
        }

        @Override
        public double calcularArea(double parametro1) { 
            return 0; 
        }
        @Override
        public double calcularArea(double parametro1, double parametro2, double parametro3) {
            return 0; 
        }
    }

    public static class Triangulo extends Figura {
        private double base;
        private double altura;

        @Override
        public double calcularArea(double base, double altura) {
            this.base = base;
            this.altura = altura;
            return (base * altura) / 2.0;
        }

        @Override
        public double calcularArea(double parametro1) { 
            return 0; 
        }
        @Override
        public double calcularArea(double parametro1, double parametro2, double parametro3) {
            return 0; 
        }
    }

    public static class Decorador {
        public static void printHeader(String titulo) {
            System.out.println();
            System.out.println("==========================================");
            System.out.println("          " + titulo);
            System.out.println("==========================================");
        }

        public static void printLine() {
            System.out.println("------------------------------------------");
        }
    }

    private static ArrayList<String> historial = new ArrayList<>();

    // Contadores para estadisticas
    private static int totalFiguras = 0;
    private static double sumaAreas = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        Decorador.printHeader("Sistema de Cálculo de Área de Figuras");

        while (!salir) {
            System.out.println("\nSeleccione el tipo de figura para calcular el área:");
            System.out.println("1. Círculo");
            System.out.println("2. Rectángulo");
            System.out.println("3. Triángulo");
            System.out.println("4. Salir");
            System.out.print("Opción: ");

            int opcion = leerEntero(sc);

            switch (opcion) {
                case 1:
                    calcularCirculo(sc);
                    break;
                case 2:
                    calcularRectangulo(sc);
                    break;
                case 3:
                    calcularTriangulo(sc);
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Error. Por favor, seleccione una opción válida.");
            }
        }

        mostrarHistorialYEstadisticas();

        sc.close();
    }

    private static void calcularCirculo(Scanner sc) {
        Decorador.printHeader("Calcular Área: Círculo");
        double radio = leerDoublePositivo(sc, "Ingrese el radio del círculo: ");
        Circulo c = new Circulo();
        double area = c.calcularArea(radio);
        System.out.printf("Área del círculo: %.4f\n", area);
        agregarHistorial("Círculo: radio=" + radio + " -> área=" + String.format("%.4f", area));
        actualizarEstadisticas(area);
    }

    private static void calcularRectangulo(Scanner sc) {
        Decorador.printHeader("Calcular Área: Rectángulo");
        double base = leerDoublePositivo(sc, "Ingrese la base del rectángulo: ");
        double altura = leerDoublePositivo(sc, "Ingrese la altura del rectángulo: ");
        Rectangulo r = new Rectangulo();
        double area = r.calcularArea(base, altura);
        System.out.printf("Área del rectángulo: %.4f\n", area);
        agregarHistorial("Rectángulo: base=" + base + ", altura=" + altura + " -> área=" + String.format("%.4f", area));
        actualizarEstadisticas(area);
    }

    private static void calcularTriangulo(Scanner sc) {
        Decorador.printHeader("Calcular Área: Triángulo");
        double base = leerDoublePositivo(sc, "Ingrese la base del triángulo: ");
        double altura = leerDoublePositivo(sc, "Ingrese la altura del triángulo: ");
        Triangulo t = new Triangulo();
        double area = t.calcularArea(base, altura);
        System.out.printf("Área del triángulo: %.4f\n", area);
        agregarHistorial("Triángulo: base=" + base + ", altura=" + altura + " -> área=" + String.format("%.4f", area));
        actualizarEstadisticas(area);
    }

    private static void agregarHistorial(String registro) {
        historial.add(registro);
    }

    private static void actualizarEstadisticas(double area) {
        totalFiguras++;
        sumaAreas += area;
    }

    private static int leerEntero(Scanner sc) {
        int valor = -1;
        while (true) {
            try {
                valor = sc.nextInt();
                sc.nextLine(); 
                if (valor < 0) {
                    System.out.print("Por favor, ingrese un número entero positivo: ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Error. Por favor, ingrese un número entero: ");
                sc.nextLine();
            }
        }
        return valor;
    }

    private static double leerDoublePositivo(Scanner sc, String mensaje) {
        double valor = -1;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = sc.nextDouble();
                sc.nextLine();
                if (valor <= 0) {
                    System.out.println("Por favor, ingrese un número positivo mayor que cero.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error. Por favor, ingrese un número válido.");
                sc.nextLine();
            }
        }
        return valor;
    }

    private static void mostrarHistorialYEstadisticas() {
        Decorador.printHeader("Historial de Cálculos Realizados");
        if (historial.isEmpty()) {
            System.out.println("No se realizaron cálculos.");
        } else {
            for (String registro : historial) {
                System.out.println("- " + registro);
            }
            Decorador.printLine();
            mostrarEstadisticas();
        }
        System.out.println("¡Hasta luego!");
    }

    // Método estatico para mostrar estadisticas
    public static void mostrarEstadisticas() {
        System.out.println("Total de figuras calculadas: " + totalFiguras);
        double promedio = (totalFiguras > 0) ? (sumaAreas / totalFiguras) : 0;
        System.out.printf("Promedio de áreas: %.4f\n", promedio);
    }
}
