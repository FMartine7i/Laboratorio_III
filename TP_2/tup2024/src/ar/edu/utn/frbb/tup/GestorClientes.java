package ar.edu.utn.frbb.tup;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.TipoPersona;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class GestorClientes extends BaseInput{
    private int contadorId = 1;
    private Map<String, Cliente> clientesPorId;

    public GestorClientes(){ clientesPorId = new HashMap<>(); }

    //acceder al mapa desde otras clases
    public Map<String, Cliente> getClientesMap(){ return clientesPorId; }

    public void agregarCliente(){
        System.out.println("Ingresar datos del cliente:");
        System.out.print("Nombre: ");
        String nombre = input.nextLine();
        System.out.print("\nApellido: ");
        String apellido = input.nextLine();
        System.out.print("\nDNI: ");
        long dni = Long.parseLong(input.nextLine());
        System.out.print("\nFecha de nacimiento [Formato YYYY-MM-dd]: ");
        LocalDate fechaDeNacimiento = dateToString(input.nextLine());
        System.out.print("\nPersona [Física(F)- Jurídica(J)]: ");
        TipoPersona tipoPersona = personaToString(input.nextLine());
        System.out.print("\nBanco: ");
        String banco = input.nextLine();
        System.out.print("\nFecha de alta [Formato YYYY-MM-dd]: ");
        LocalDate fechaDeAlta = dateToString(input.nextLine());

        add(nombre, apellido, dni, fechaDeNacimiento, tipoPersona, banco, fechaDeAlta);
        System.out.println("\nCliente creado con éxito.\n");
    }

    public void actualizarCliente(){
        System.out.print("\nIngrese número de identificación: ");
        int numeroId = input.nextInt();
        input.nextLine(); // Consume el caracter de nueva línea
        String id = Integer.toString(numeroId);
        Cliente clienteAActualizar = getClientById(id);

        if(clienteAActualizar != null) {
            String nombre = "";
            String apellido = "";
            long dni = 0;
            LocalDate fechaDeNacimiento = null;
            TipoPersona tipoPersona = null;
            String banco = "";
            LocalDate fechaDeAlta = null;
            int option;

            do {
                System.out.println("¿Qué dato desea cambiar?");
                System.out.println("\n+----------- ACTUALIZAR -----------+");
                System.out.println("| 1. Nombre                        |");
                System.out.println("| 2. Apellido                      |");
                System.out.println("| 3. DNI                           |");
                System.out.println("| 4. Fecha de Nacimiento           |");
                System.out.println("| 5. Tipo                          |");
                System.out.println("| 6. Banco                         |");
                System.out.println("| 7. Fecha de alta                 |");
                System.out.println("| 0. Salir                         |");
                System.out.println("+----------------------------------+\n");
                option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Ingrese el nuevo nombre: ");
                        nombre = input.nextLine();
                        System.out.println("Nombre modificado con éxito.");
                        break;
                    case 2:
                        System.out.print("Ingrese el nuevo apellido: ");
                        apellido = input.nextLine();
                        System.out.println("Apellido modificado con éxito.");
                        break;
                    case 3:
                        System.out.print("Ingrese el nuevo DNI: ");
                        dni = Long.parseLong(input.nextLine());
                        System.out.println("DNI modificado con éxito.");
                        break;
                    case 4:
                        System.out.print("Ingrese la nueva fecha de nacimiento [YYYY-MM-dd]: ");
                        fechaDeNacimiento = dateToString(input.nextLine());
                        System.out.println("Fecha de nacimiento modificada con éxito.");
                        break;
                    case 5:
                        System.out.print("Ingrese el nuevo tipo [F-J]: ");
                        tipoPersona = personaToString(input.nextLine());
                        System.out.println("Tipo de persona modificado con éxito.");
                        break;
                    case 6:
                        System.out.print("Ingrese el nuevo Banco: ");
                        banco = input.nextLine();
                        System.out.println("Banco modificado con éxito.");
                        break;
                    case 7:
                        System.out.print("Ingrese la nueva fecha de alta [YYYY-MM-dd]: ");
                        fechaDeAlta = dateToString(input.nextLine());
                        System.out.println("Fecha de alta modificada con éxito.");
                        break;
                    case 0:
                        System.out.println("Saliendo de actualización...");
                        break;
                    default:
                        System.out.println("Opción inválida. Seleccione [0-7]");
                }
            } while (option != 0);

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
                    "\nCuenta asociada: " + clienteEncontrado.getCuentas() +
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
                System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-dd:");
            }
        }
        return null;
    }

    //Asigna un tipo de persona y, en caso de que se coloque mal la letra, selecciona persona física por defecto
    private TipoPersona personaToString(String tipoPersonaString){
        for (TipoPersona tipoPersona : TipoPersona.values()){
            if (tipoPersona.getDescripcion().equalsIgnoreCase(tipoPersonaString)){
                return tipoPersona;
            }
        }
        System.out.println("Opción inválida. Se ha seleccionado Persona Física por defecto.");
        return TipoPersona.FISICA;
    }
}
