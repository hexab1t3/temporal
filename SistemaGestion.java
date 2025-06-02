import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.io.*;

public class SistemaGestion implements RevisionDeCitas {
    // Colecciones para almacenar datos
    private List<Cliente> clientes = new ArrayList<>();
    private List<Mascota> mascotas = new ArrayList<>();
    private List<Mascota> mascotasDisponibles = new ArrayList<>();
    private List<Veterinario> veterinarios = new ArrayList<>();
    private List<Asistente> asistentes = new ArrayList<>();
    private List<Gerente> gerentes = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();
    private List<Paquete> paquetes = new ArrayList<>();
    private Map<Integer, Integer> adopciones = new HashMap<>(); // clienteId -> mascotaId

    private Scanner scanner = new Scanner(System.in);

    // Constructor - inicializa datos de ejemplo
    public SistemaGestion() {
        inicializarDatosEjemplo();
    }

    // Inicializa algunos datos de ejemplo para pruebas
    private void inicializarDatosEjemplo() {
        // Paquetes
        paquetes.add(new Paquete("Corte básico", "Corte de pelo básico", 200.0));
        paquetes.add(new Paquete("Baño completo", "Baño con champú especial", 150.0));
        paquetes.add(new Paquete("Desparasitación", "Tratamiento antiparasitario", 300.0));
        paquetes.add(new Paquete("Esterilización", "Cirugía de esterilización", 800.0));

        // Mascotas disponibles
        String[] vacunas = {"Rabia", "Parvovirus"};
        mascotasDisponibles.add(new Mascota("Firulais", "Labrador", 1, vacunas));
        mascotasDisponibles.add(new Mascota("Michi", "Persa", 2, vacunas));
    }

    // Menú principal
    public void mostrarMenu() {
        while (true) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE MASCOTAS ===");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Alta de mascota");
            System.out.println("3. Alta y baja de veterinarios o asistente");
            System.out.println("4. Alta de gerente en sucursal");
            System.out.println("5. Registro de citas de veterinarios a domicilio");
            System.out.println("6. Alta de paquetes");
            System.out.println("7. Adopción o devolución de mascotas");
            System.out.println("8. Pago de paquetes");
            System.out.println("9. Consulta de citas");
            System.out.println("10. Consulta de paquetes");
            System.out.println("11. Consulta de adopciones");
            System.out.println("12. Consulta de veterinarios");
            System.out.println("13. Escritura a archivo de citas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1: altaCliente(); break;
                case 2: altaMascota(); break;
                case 3: altaBajaPersonal(); break;
                case 4: altaGerente(); break;
                case 5: registrarCita(); break;
                case 6: altaPaquete(); break;
                case 7: adopcionDevolucion(); break;
                case 8: pagoPaquetes(); break;
                case 9: consultaCitas(); break;
                case 10: consultaPaquetes(); break;
                case 11: consultaAdopciones(); break;
                case 12: consultaVeterinarios(); break;
                case 13: escribirCitasArchivo(); break;
                case 0: 
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    // 1. Alta de cliente
    private void altaCliente() {
        System.out.println("\n=== ALTA DE CLIENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido paterno: ");
        String paterno = scanner.nextLine();
        System.out.print("Apellido materno (opcional): ");
        String materno = scanner.nextLine();
        if (materno.trim().isEmpty()) materno = null;

        System.out.print("CURP: ");
        String curp = scanner.nextLine();

        Date fechaNacimiento = new Date(); // Simplificado para el ejemplo
        int numeroCliente = clientes.size() + 1;

        // Tarjeta
        System.out.print("Número de tarjeta: ");
        long numeroTarjeta = scanner.nextLong();
        System.out.print("CVC: ");
        short cvc = scanner.nextShort();
        scanner.nextLine();

        Tarjeta tarjeta = new Tarjeta(numeroTarjeta, new Date(), cvc);

        Cliente cliente;
        if (materno != null) {
            cliente = new Cliente(nombre, paterno, materno, fechaNacimiento, curp, numeroCliente, null, tarjeta);
        } else {
            cliente = new Cliente(nombre, paterno, fechaNacimiento, curp, numeroCliente, null, tarjeta);
        }

        clientes.add(cliente);
        System.out.println("Cliente registrado exitosamente con ID: " + numeroCliente);
    }

    // 2. Alta de mascota
    private void altaMascota() {
        System.out.println("\n=== ALTA DE MASCOTA ===");
        System.out.print("Nombre de la mascota: ");
        String nombre = scanner.nextLine();
        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        int numeroMascota = mascotas.size() + mascotasDisponibles.size() + 1;

        System.out.print("¿Cuántas vacunas tiene? ");
        int numVacunas = scanner.nextInt();
        scanner.nextLine();

        String[] vacunas = new String[numVacunas];
        for (int i = 0; i < numVacunas; i++) {
            System.out.print("Vacuna " + (i + 1) + ": ");
            vacunas[i] = scanner.nextLine();
        }

        Mascota mascota = new Mascota(nombre, raza, numeroMascota, vacunas);
        mascotasDisponibles.add(mascota);
        System.out.println("Mascota registrada exitosamente con ID: " + numeroMascota);
    }

    // 3. Alta y baja de personal
    private void altaBajaPersonal() {
        System.out.println("\n=== GESTIÓN DE PERSONAL ===");
        System.out.println("1. Alta veterinario");
        System.out.println("2. Alta asistente");
        System.out.println("3. Baja veterinario");
        System.out.println("4. Baja asistente");
        System.out.print("Opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1: altaVeterinario(); break;
            case 2: altaAsistente(); break;
            case 3: bajaVeterinario(); break;
            case 4: bajaAsistente(); break;
            default: System.out.println("Opción inválida");
        }
    }

    private void altaVeterinario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido paterno: ");
        String paterno = scanner.nextLine();
        System.out.print("Apellido materno (opcional): ");
        String materno = scanner.nextLine();
        if (materno.trim().isEmpty()) materno = null;

        System.out.print("CURP: ");
        String curp = scanner.nextLine();
        System.out.print("Número de cédula: ");
        int cedula = scanner.nextInt();

        Date fechaNacimiento = new Date();

        Veterinario vet;
        if (materno != null) {
            vet = new Veterinario(nombre, paterno, materno, fechaNacimiento, curp, cedula);
        } else {
            vet = new Veterinario(nombre, paterno, fechaNacimiento, curp, cedula);
        }

        veterinarios.add(vet);
        System.out.println("Veterinario registrado exitosamente");
    }

    private void altaAsistente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido paterno: ");
        String paterno = scanner.nextLine();
        System.out.print("Apellido materno (opcional): ");
        String materno = scanner.nextLine();
        if (materno.trim().isEmpty()) materno = null;

        System.out.print("CURP: ");
        String curp = scanner.nextLine();
        System.out.print("Número de asistente: ");
        int numeroAsistente = scanner.nextInt();

        Date fechaNacimiento = new Date();

        Asistente asist;
        if (materno != null) {
            asist = new Asistente(nombre, paterno, materno, fechaNacimiento, curp, numeroAsistente);
        } else {
            asist = new Asistente(nombre, paterno, fechaNacimiento, curp, numeroAsistente);
        }

        asistentes.add(asist);
        System.out.println("Asistente registrado exitosamente");
    }

    private void bajaVeterinario() {
        System.out.print("Número de cédula del veterinario a dar de baja: ");
        int cedula = scanner.nextInt();

        veterinarios.removeIf(v -> v.getNumeroCedula() == cedula);
        System.out.println("Veterinario dado de baja");
    }

    private void bajaAsistente() {
        System.out.print("Número de asistente a dar de baja: ");
        int numero = scanner.nextInt();

        asistentes.removeIf(a -> a.getNumeroAsistente() == numero);
        System.out.println("Asistente dado de baja");
    }

    // 4. Alta de gerente
    private void altaGerente() {
        System.out.println("\n=== ALTA DE GERENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido paterno: ");
        String paterno = scanner.nextLine();
        System.out.print("Apellido materno (opcional): ");
        String materno = scanner.nextLine();
        if (materno.trim().isEmpty()) materno = null;

        System.out.print("CURP: ");
        String curp = scanner.nextLine();
        System.out.print("Número de gerente: ");
        int numeroGerente = scanner.nextInt();

        System.out.println("Sucursales disponibles:");
        for (Sucursal s : Sucursal.values()) {
            System.out.println("- " + s);
        }
        System.out.print("Seleccione sucursal: ");
        scanner.nextLine();
        String sucursalStr = scanner.nextLine().toUpperCase();
        Sucursal sucursal = Sucursal.valueOf(sucursalStr);

        Date fechaNacimiento = new Date();
        SucursalClass sucursalClass = new SucursalClass(sucursal);

        Gerente gerente;
        if (materno != null) {
            gerente = new Gerente(nombre, paterno, materno, fechaNacimiento, curp, numeroGerente, sucursalClass);
        } else {
            gerente = new Gerente(nombre, paterno, fechaNacimiento, curp, numeroGerente, sucursalClass);
        }

        gerentes.add(gerente);
        System.out.println("Gerente registrado exitosamente");
    }

    // 5. Registro de citas
    private void registrarCita() {
        try {
            System.out.println("\n=== REGISTRO DE CITA ===");
            System.out.print("ID del cliente: ");
            int clienteId = scanner.nextInt();

            Cliente cliente = buscarClientePorId(clienteId);
            if (cliente == null) {
                throw new ClienteNoEncontradoException("Cliente no encontrado");
            }

            System.out.print("ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            Mascota mascota = buscarMascotaPorId(mascotaId);
            if (mascota == null) {
                System.out.println("Mascota no encontrada");
                return;
            }

            if (!mascotaVacunada(mascotaId)) {
                throw new MascotaSinVacunasException("La mascota no tiene vacunas suministradas");
            }

            System.out.print("Fecha y hora (formato: yyyy-MM-dd HH:mm): ");
            scanner.nextLine();
            String fechaStr = scanner.nextLine();

            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr.replace(" ", "T"));
            Date fecha = Date.from(fechaHora.atZone(ZoneId.systemDefault()).toInstant());

            if (!revisarDisponibilidad(fecha)) {
                throw new CitaOcupadaException("No puede agendar la cita, ya se encuentra ocupada");
            }

            System.out.print("Descripción del servicio: ");
            String descripcion = scanner.nextLine();

            // Seleccionar veterinario o asistente
            System.out.print("¿Desea veterinario (V) o asistente (A)? ");
            String tipo = scanner.nextLine().toUpperCase();

            Persona encargado = null;
            if (tipo.equals("V")) {
                System.out.println("Veterinarios disponibles:");
                for (int i = 0; i < veterinarios.size(); i++) {
                    Veterinario v = veterinarios.get(i);
                    System.out.println((i + 1) + ". " + v.getNombre() + " " + v.getPaterno());
                }
                System.out.print("Seleccione veterinario: ");
                int vetIndex = scanner.nextInt() - 1;
                encargado = veterinarios.get(vetIndex);
            } else {
                System.out.println("Asistentes disponibles:");
                for (int i = 0; i < asistentes.size(); i++) {
                    Asistente a = asistentes.get(i);
                    System.out.println((i + 1) + ". " + a.getNombre() + " " + a.getPaterno());
                }
                System.out.print("Seleccione asistente: ");
                int asistIndex = scanner.nextInt() - 1;
                encargado = asistentes.get(asistIndex);
            }

            int numeroCita = citas.size() + 1;
            Cita cita = new Cita(numeroCita, fechaHora, cliente, mascota, encargado, descripcion);
            citas.add(cita);

            System.out.println("Cita registrada exitosamente con número: " + numeroCita);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 6. Alta de paquetes
    private void altaPaquete() {
        System.out.println("\n=== ALTA DE PAQUETE ===");
        System.out.print("Nombre del paquete: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        Paquete paquete = new Paquete(nombre, descripcion, precio);
        paquetes.add(paquete);
        System.out.println("Paquete registrado exitosamente");
    }

    // 7. Adopción o devolución
    private void adopcionDevolucion() {
        System.out.println("\n=== ADOPCIÓN/DEVOLUCIÓN ===");
        System.out.println("1. Adopción");
        System.out.println("2. Devolución");
        System.out.print("Opción: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1: procesarAdopcion(); break;
            case 2: procesarDevolucion(); break;
            default: System.out.println("Opción inválida");
        }
    }

    private void procesarAdopcion() {
        try {
            System.out.print("ID del cliente: ");
            int clienteId = scanner.nextInt();

            Cliente cliente = buscarClientePorId(clienteId);
            if (cliente == null) {
                throw new ClienteNoEncontradoException("Cliente no encontrado");
            }

            System.out.println("Mascotas disponibles:");
            for (int i = 0; i < mascotasDisponibles.size(); i++) {
                Mascota m = mascotasDisponibles.get(i);
                System.out.println((i + 1) + ". " + m.getNombre() + " - " + m.getRaza());
            }

            System.out.print("Seleccione mascota: ");
            int mascotaIndex = scanner.nextInt() - 1;

            if (mascotaIndex < 0 || mascotaIndex >= mascotasDisponibles.size()) {
                throw new MascotaNoDisponibleException("Mascota no disponible");
            }

            Mascota mascota = mascotasDisponibles.get(mascotaIndex);

            if (!mascotaVacunada(mascota.getNumeroMascota())) {
                throw new MascotaSinVacunasException("No tiene vacunas suministradas");
            }

            // Procesar adopción
            adopciones.put(clienteId, mascota.getNumeroMascota());
            mascotasDisponibles.remove(mascotaIndex);
            mascotas.add(mascota);

            System.out.println("Adopción procesada exitosamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void procesarDevolucion() {
        System.out.print("ID del cliente: ");
        int clienteId = scanner.nextInt();

        if (!adopciones.containsKey(clienteId)) {
            System.out.println("Cliente no tiene mascotas adoptadas");
            return;
        }

        int mascotaId = adopciones.get(clienteId);
        Mascota mascota = buscarMascotaPorId(mascotaId);

        System.out.print("¿La mascota presenta lesiones o maltrato? (s/n): ");
        scanner.nextLine();
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s")) {
            System.out.print("Monto del cargo por maltrato: ");
            double cargo = scanner.nextDouble();

            Cliente cliente = buscarClientePorId(clienteId);
            System.out.println("Cobrando $" + cargo + " a la tarjeta " + cliente.getTarjeta().getNumeroTarjeta());
        }

        // Procesar devolución
        adopciones.remove(clienteId);
        mascotas.remove(mascota);
        mascotasDisponibles.add(mascota);

        System.out.println("Devolución procesada exitosamente");
    }

    // 8. Pago de paquetes
    private void pagoPaquetes() {
        try {
            System.out.println("\n=== PAGO DE PAQUETES ===");
            System.out.print("ID del cliente: ");
            int clienteId = scanner.nextInt();

            Cliente cliente = buscarClientePorId(clienteId);
            if (cliente == null) {
                throw new ClienteNoEncontradoException("Cliente no encontrado");
            }

            System.out.println("Paquetes disponibles:");
            for (int i = 0; i < paquetes.size(); i++) {
                Paquete p = paquetes.get(i);
                System.out.println((i + 1) + ". " + p.getNombrePaquete() + " - $" + p.getPrecio());
            }

            System.out.print("¿Cuántos paquetes desea contratar? ");
            int numPaquetes = scanner.nextInt();

            double total = 0;
            for (int i = 0; i < numPaquetes; i++) {
                System.out.print("Seleccione paquete " + (i + 1) + ": ");
                int paqueteIndex = scanner.nextInt() - 1;
                total += paquetes.get(paqueteIndex).getPrecio();
            }

            System.out.println("Total a pagar: $" + total);
            System.out.println("Cobrando a la tarjeta: " + cliente.getTarjeta().getNumeroTarjeta());
            System.out.println("Pago procesado exitosamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 9-12. Consultas
    private void consultaCitas() {
        System.out.println("\n=== CONSULTA DE CITAS ===");
        System.out.println("1. Por fecha");
        System.out.println("2. Por nombre");
        System.out.println("3. Por apellido paterno");
        System.out.println("4. Por apellido materno");
        System.out.print("Ordenar por: ");

        int opcion = scanner.nextInt();
        List<Cita> citasOrdenadas = new ArrayList<>(citas);

        switch (opcion) {
            case 1:
                citasOrdenadas.sort((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()));
                break;
            case 2:
                citasOrdenadas.sort((c1, c2) -> c1.getCliente().getNombre().compareTo(c2.getCliente().getNombre()));
                break;
            case 3:
                citasOrdenadas.sort((c1, c2) -> c1.getCliente().getPaterno().compareTo(c2.getCliente().getPaterno()));
                break;
            case 4:
                citasOrdenadas.sort((c1, c2) -> {
                    String m1 = c1.getCliente().getMaterno() != null ? c1.getCliente().getMaterno() : "";
                    String m2 = c2.getCliente().getMaterno() != null ? c2.getCliente().getMaterno() : "";
                    return m1.compareTo(m2);
                });
                break;
        }

        for (Cita cita : citasOrdenadas) {
            System.out.println("Cita #" + cita.getNumeroCita() + " - " + 
                             cita.getCliente().getNombre() + " " + cita.getCliente().getPaterno() + 
                             " - " + cita.getFechaHora());
        }

        System.out.print("¿Desea descargar a archivo? (s/n): ");
        scanner.nextLine();
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s")) {
            escribirCitasArchivo(citasOrdenadas, "citas_ordenadas.txt");
        }
    }

    private void consultaPaquetes() {
        System.out.println("\n=== CONSULTA DE PAQUETES ===");
        System.out.print("¿Orden ascendente? (s/n): ");
        scanner.nextLine();
        boolean ascendente = scanner.nextLine().toLowerCase().equals("s");

        List<Paquete> paquetesOrdenados = new ArrayList<>(paquetes);
        if (ascendente) {
            paquetesOrdenados.sort((p1, p2) -> p1.getNombrePaquete().compareTo(p2.getNombrePaquete()));
        } else {
            paquetesOrdenados.sort((p1, p2) -> p2.getNombrePaquete().compareTo(p1.getNombrePaquete()));
        }

        for (Paquete paquete : paquetesOrdenados) {
            System.out.println(paquete.getNombrePaquete() + " - $" + paquete.getPrecio());
        }
    }

    private void consultaAdopciones() {
        System.out.println("\n=== CONSULTA DE ADOPCIONES ===");
        for (Map.Entry<Integer, Integer> entry : adopciones.entrySet()) {
            Cliente cliente = buscarClientePorId(entry.getKey());
            Mascota mascota = buscarMascotaPorId(entry.getValue());
            System.out.println("Cliente: " + cliente.getNombre() + " - Mascota: " + mascota.getNombre());
        }
    }

    private void consultaVeterinarios() {
        System.out.println("\n=== CONSULTA DE VETERINARIOS ===");
        System.out.print("¿Orden ascendente? (s/n): ");
        scanner.nextLine();
        boolean ascendente = scanner.nextLine().toLowerCase().equals("s");

        List<Veterinario> veterinariosOrdenados = new ArrayList<>(veterinarios);
        if (ascendente) {
            veterinariosOrdenados.sort((v1, v2) -> v1.getNombre().compareTo(v2.getNombre()));
        } else {
            veterinariosOrdenados.sort((v1, v2) -> v2.getNombre().compareTo(v1.getNombre()));
        }

        for (Veterinario vet : veterinariosOrdenados) {
            System.out.println("Dr. " + vet.getNombre() + " " + vet.getPaterno() + 
                             " - Cédula: " + vet.getNumeroCedula());
        }
    }

    // 13. Escritura a archivo
    private void escribirCitasArchivo() {
        System.out.println("\n=== ESCRITURA DE CITAS A ARCHIVO ===");
        System.out.println("1. Todas las citas");
        System.out.println("2. Citas de fecha específica");
        System.out.print("Opción: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                escribirCitasArchivo(citas, "todas_las_citas.txt");
                break;
            case 2:
                System.out.print("Fecha (yyyy-MM-dd): ");
                scanner.nextLine();
                String fechaStr = scanner.nextLine();

                List<Cita> citasFecha = new ArrayList<>();
                for (Cita cita : citas) {
                    String citaFecha = cita.getFechaHora().toString().substring(0, 10);
                    if (citaFecha.equals(fechaStr)) {
                        citasFecha.add(cita);
                    }
                }

                escribirCitasArchivo(citasFecha, "citas_" + fechaStr + ".txt");
                break;
            default:
                System.out.println("Opción inválida");
        }
    }

    private void escribirCitasArchivo(List<Cita> citasEscribir, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("=== REPORTE DE CITAS ===");
            writer.println("Fecha de generación: " + new Date());
            writer.println("Total de citas: " + citasEscribir.size());
            writer.println();

            for (Cita cita : citasEscribir) {
                writer.println("Cita #" + cita.getNumeroCita());
                writer.println("Cliente: " + cita.getCliente().getNombre() + " " + 
                              cita.getCliente().getPaterno() + " " + 
                              (cita.getCliente().getMaterno() != null ? cita.getCliente().getMaterno() : ""));
                writer.println("Mascota: " + cita.getMascota().getNombre() + " (" + cita.getMascota().getRaza() + ")");
                writer.println("Fecha y hora: " + cita.getFechaHora());
                writer.println("Encargado: " + cita.getEncargado().getNombre() + " " + cita.getEncargado().getPaterno());
                writer.println("Descripción: " + cita.getDescripcion());
                writer.println("------------------------");
            }

            System.out.println("Archivo generado exitosamente: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al escribir archivo: " + e.getMessage());
        }
    }

    // Métodos auxiliares para búsqueda
    private Cliente buscarClientePorId(int id) {
        return clientes.stream()
                      .filter(c -> c.getNumeroCliente() == id)
                      .findFirst()
                      .orElse(null);
    }

    private Mascota buscarMascotaPorId(int id) {
        // Buscar en mascotas adoptadas
        for (Mascota m : mascotas) {
            if (m.getNumeroMascota() == id) return m;
        }
        // Buscar en mascotas disponibles
        for (Mascota m : mascotasDisponibles) {
            if (m.getNumeroMascota() == id) return m;
        }
        return null;
    }

    // Implementación de la interfaz RevisionDeCitas
    @Override
    public boolean asistenteDisponible(String nombre, String paterno, String materno) {
        return asistentes.stream()
                        .anyMatch(a -> a.getNombre().equals(nombre) && 
                                      a.getPaterno().equals(paterno) &&
                                      (materno == null || a.getMaterno().equals(materno)));
    }

    @Override
    public boolean veterinarioDisponible(String nombre, String paterno, String materno) {
        return veterinarios.stream()
                          .anyMatch(v -> v.getNombre().equals(nombre) && 
                                        v.getPaterno().equals(paterno) &&
                                        (materno == null || v.getMaterno().equals(materno)));
    }

    @Override
    public boolean mascotaVacunada(int numeroMascota) {
        Mascota mascota = buscarMascotaPorId(numeroMascota);
        return mascota != null && mascota.getVacunasAplicadas() != null && 
               mascota.getVacunasAplicadas().length > 0;
    }

    @Override
    public boolean revisarDisponibilidad(Date fechaCita) {
        // Verificar que no haya otra cita en la misma fecha y hora (1 hora de duración)
        LocalDateTime fechaConsulta = fechaCita.toInstant()
                                              .atZone(ZoneId.systemDefault())
                                              .toLocalDateTime();

        for (Cita cita : citas) {
            LocalDateTime fechaCitaExistente = cita.getFechaHora();

            // Verificar conflicto (mismo día, misma hora)
            if (fechaCitaExistente.toLocalDate().equals(fechaConsulta.toLocalDate()) &&
                fechaCitaExistente.getHour() == fechaConsulta.getHour()) {
                return false;
            }
        }
        return true;
    }
}