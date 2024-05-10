package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.TipoPersona;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestorClientes extends BaseInput{
    private int contadorId = 0;
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
    }

    public void actualizarCliente(){
        System.out.print("\nIngrese número de identificación: ");
        int numeroId = input.nextInt();
        String id = Integer.toString(numeroId);
        Cliente clienteAActualizar = getClientById(id);

        if(clienteAActualizar != null) {
            System.out.println("\nIngresar datos del cliente:");
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

            update(clienteAActualizar, nombre, apellido, dni, fechaDeNacimiento, tipoPersona, banco, fechaDeAlta);
            System.out.println("Cambios guardados correctamente.");
        }
        else
            System.out.println("No se ha encontrado el usuario con la ID " + id);
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

    public void printClient(String id){
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
                    "------------------------------");
        }
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

    private LocalDate dateToString(String fechaString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fechaString, formatter);
    }

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
}
