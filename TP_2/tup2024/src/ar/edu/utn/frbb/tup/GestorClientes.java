package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.TipoPersona;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestorClientes extends BaseInput{
    private int contadorId = 1;
    private Map<String, Cliente> clientesPorId;

    public GestorClientes(){
        clientesPorId = new HashMap<>();
    }

    public void agregarCliente(){
        System.out.println("Ingresar datos del cliente:");
        System.out.print("Nombre: ");
        String nombre = input.nextLine();
        System.out.print("\nApellido: ");
        String apellido = input.nextLine();
        System.out.print("\nDNI: ");
        long dni = Long.parseLong(input.nextLine());
        System.out.print("\nFecha de nacimiento [Formato dd/MM/YYYY]: ");
        LocalDate fechaDeNacimiento = dateToString(input.nextLine());
        System.out.print("\nPersona [Física(F)- Jurídica(J)]: ");
        TipoPersona tipoPersona = personaToString();
        System.out.print("\nBanco: ");
        String banco = input.nextLine();
        System.out.print("\nFecha de alta [Formato dd/MM/YYYY]: ");
        LocalDate fechaDeAlta = dateToString(input.nextLine());

        add(nombre, apellido, dni, fechaDeNacimiento, tipoPersona, banco, fechaDeAlta);
        System.out.println("\nCliente creado con éxito.\n");
    }

    public void actualizarCliente(){
        System.out.print("\nIngrese número de identificación: ");
        int numeroId = input.nextInt();
        String id = Integer.toString(numeroId);
        Cliente clienteAActualizar = getClientById(id);

        if(clienteAActualizar != null) {
            System.out.println("\nIngresar datos del cliente:");
            String nombre = confirmarCambioString("el nombre", clienteAActualizar.getNombre());
            String apellido = confirmarCambioString("el apellido", clienteAActualizar.getApellido());
            long dni = confirmarCambioDni("el DNI", clienteAActualizar.getDni());
            LocalDate fechaDeNacimiento = confirmarCambioFecha("fecha de nacimiento", clienteAActualizar.getFechaNacimiento());
            TipoPersona tipoPersona = confirmarCambioTipo(clienteAActualizar.getTipoPersona());
            String banco = confirmarCambioString("el banco", clienteAActualizar.getApellido());
            LocalDate fechaDeAlta = confirmarCambioFecha("fecha de alta", clienteAActualizar.getFechaAlta());

            update(clienteAActualizar, nombre, apellido, dni, fechaDeNacimiento, tipoPersona, banco, fechaDeAlta);
            System.out.println("Cambios guardados correctamente.\n");
        }
        else
            System.out.println("No se ha encontrado el usuario con la ID " + id +"\n");
    }

    public Cliente getClientById(String id){
        return clientesPorId.get(id);
    }

    public void getClients(){
        System.out.println("\nLista de clientes:");
        for (Cliente cliente : clientesPorId.values()) {
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println("---------------------------\n");
        }
    }

    public void printClient(){
        System.out.println("Ingrese un número de identificación: ");
        int numeroId = input.nextInt();
        String id = Integer.toString(numeroId);
        Cliente clienteEncontrado = getClientById(id);

        if (clienteEncontrado != null){
            System.out.println("\n------------- CLIENTE ---------------" +
                    "\nNombre: " + clienteEncontrado.getNombre() +
                    "\nApellido: " + clienteEncontrado.getApellido() +
                    "\nDNI: " + clienteEncontrado.getDni() +
                    "\nFecha de nacimiento: " + clienteEncontrado.getFechaNacimiento() +
                    "\nPersona: " + clienteEncontrado.getTipoPersona() +
                    "\nBanco: " + clienteEncontrado.getBanco() +
                    "\nCuenta asociada: " + clienteEncontrado.getCuenta() +
                    "\nFecha de alta: " + clienteEncontrado.getFechaAlta() +
                    "\n------------------------------\n");
        }
        else
            System.out.println("No se ha encontrado el usuario con la ID " + id +"\n");
    }

    public void add(String nombre, String apellido, long dni, LocalDate fechaDeNacimiento, TipoPersona tipoPersona, String banco, LocalDate fechaAlta){
        Cliente cliente = new Cliente();
        String id = Integer.toString(contadorId);   //convierto la id a String para poder trabajar con Map
        cliente.setId(contadorId);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setFechaNacimiento(fechaDeNacimiento);
        cliente.setTipoPersona(tipoPersona);
        cliente.setBanco(banco);
        cliente.setFechaAlta(fechaAlta);
        clientesPorId.put(id, cliente);
        contadorId++;   //El id es autoincremental
    }

    public void delete(){
        System.out.print("Ingrese el número de identificación del usuario a eliminar: ");
        int numeroId = input.nextInt();
        String id = Integer.toString(numeroId);
        Cliente clienteAEliminar = getClientById(id);

        if (clienteAEliminar != null) {     // si se encuentra el cliente se elimina
            clientesPorId.remove(id);
            System.out.println("Cliente eliminado correctamente.");
        }
        else
            System.out.println("ID " + id + " No encontrada. Por favor, asegúrese de ingresar el número de identificación correcto.");
    }

    public void update(Cliente cliente, String nombre, String apellido, long dni, LocalDate fechaDeNacimiento, TipoPersona tipoPersona, String banco, LocalDate fechaAlta){
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setFechaNacimiento(fechaDeNacimiento);
        cliente.setTipoPersona(tipoPersona);
        cliente.setBanco(banco);
        cliente.setFechaAlta(fechaAlta);
    }

    //Devuelve como LocalDate la fecha ingresada como String
    private LocalDate dateToString(String fechaString) {
        boolean fechaValida = false;

        while (!fechaValida) {
            try {
                fechaValida = true;
                return LocalDate.parse(fechaString);
            }
            catch (Exception e) {
                System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD:");
            }
        }
        return null;
    }

    //Asigna un tipo de persona y, en caso de que se coloque mal la letra, selecciona persona física por defecto
    private TipoPersona personaToString(){
        String tipoPersonaString = input.nextLine().toUpperCase();
        for (TipoPersona tipoPersona : TipoPersona.values()){
            if (tipoPersona.getDescripcion().equals(tipoPersonaString)){
                return tipoPersona;
            }
        }
        System.out.println("Opción inválida. Se ha seleccionado Persona Física por defecto.");
        return TipoPersona.FISICA;
    }

    // métodos para confirmar si se desea actualizar
    private String confirmarCambioString(String mensaje, String valorActual){
        boolean continuar = true;
        String option;
        while (continuar) {
            System.out.println("¿Desea actualizar " + mensaje + "? [s/N]");
            option = input.nextLine();

            if (option.equalsIgnoreCase("s")) {
                System.out.print("Ingrese nuevo dato: ");
                return input.nextLine();
            }
            else if (option.equalsIgnoreCase("n")) {
                continuar = false;
                return valorActual;
            }
            else {
                System.out.println("Opción inválida. Seleccione [s/N]");
            }
        }
        return valorActual;
    }

    private long confirmarCambioDni(String mensaje, long valorActual){
        boolean continuar = true;
        String option;
        while (continuar) {
            System.out.println("¿Desea actualizar " + mensaje + "? [s/N]");
            option = input.nextLine();

            if (option.equalsIgnoreCase("s")) {
                System.out.print("Ingrese nuevo dato: ");
                return Long.parseLong(input.nextLine());
            }
            else if (option.equalsIgnoreCase("n"))
                continuar = false;
            else {
                System.out.println("Opción inválida. Seleccione [s/N]");
            }
        }
        return valorActual;
    }

    private LocalDate confirmarCambioFecha(String mensaje, LocalDate valorActual){
        boolean continuar = true;
        String option;
        while (continuar) {
            System.out.println("¿Desea actualizar " + mensaje + " [s/N]");
            option = input.nextLine();

            if (option.equalsIgnoreCase("s")) {
                System.out.print("Ingrese nuevo dato: ");
                dateToString(input.nextLine());
                continuar = false;
            }
            else if (option.equalsIgnoreCase("n"))
                continuar = false;
            else {
                System.out.println("Opción inválida. Seleccione [s/N]");
            }
        }
        return valorActual;
    }

    private TipoPersona confirmarCambioTipo(TipoPersona valorActual){
        boolean continuar = true;
        String option;
        while (continuar) {
            System.out.println("¿Desea actualizar el tipo? [s/N]");
            option = input.nextLine();

            if (option.equalsIgnoreCase("s")) {
                System.out.print("Ingrese nuevo dato: ");
                return personaToString();
            }
            else if (option.equalsIgnoreCase("n"))
                continuar = false;
            else {
                System.out.println("Opción inválida. Seleccione [s/N]");
            }
        }
        return valorActual;
    }
}
