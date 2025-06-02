public class Main {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   BIENVENIDO AL SISTEMA DE GESTIÓN DE MASCOTAS");
        System.out.println("==============================================");
        System.out.println("Desarrollado por:");
        System.out.println("- Fernando Uriel Miranda Romero");
        System.out.println("- Patoni Velázquez Josué");
        System.out.println("==============================================\n");

        // Crear instancia del sistema y mostrar menú principal
        SistemaGestion sistema = new SistemaGestion();
        sistema.mostrarMenu();
    }
}