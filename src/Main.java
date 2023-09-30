import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        try {

          Connection conexion= null;

               conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tareacrud10","root","Powerdax1");


            while (true) {
                System.out.println("Menú:");
                System.out.println("1. Consultar información");
                System.out.println("2. Actualizar información");
                System.out.println("3. Agregar nueva persona");
                System.out.println("4. Eliminar persona");
                System.out.println("5. Salir");
                System.out.print("Elija una opción: ");

                Scanner scanner = new Scanner(System.in);
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        consultarInformacion(conexion);
                        break;
                    case 2:
                        actualizarInformacion(conexion);
                        break;
                    case 3:
                        agregarPersona(conexion);
                        break;
                    case 4:
                        eliminarPersona(conexion);
                        break;
                    case 5:
                        conexion.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void consultarInformacion(Connection conexion) throws SQLException {
        try {
            Statement statement = conexion.createStatement();
            String consulta = "SELECT * FROM personas";
            ResultSet resultado = statement.executeQuery(consulta);

            while (resultado.next()) {
                int codigo = resultado.getInt("codigo");
                String nombreApellido = resultado.getString("nombre_apellido");
                Date fechaRegistro = resultado.getDate("fecha_registro");
                double sueldoBase = resultado.getDouble("sueldo_base");
                String sexo = resultado.getString("sexo");
                int edad = resultado.getInt("edad");
                double latitud = resultado.getDouble("latitud");
                double longitud = resultado.getDouble("longitud");
                String comentarios = resultado.getString("comentarios");

                System.out.println("Código: " + codigo);
                System.out.println("Nombre y Apellido: " + nombreApellido);
                System.out.println("Fecha de Registro: " + fechaRegistro);
                System.out.println("Sueldo Base: " + sueldoBase);
                System.out.println("Sexo: " + sexo);
                System.out.println("Edad: " + edad);
                System.out.println("Latitud: " + latitud);
                System.out.println("Longitud: " + longitud);
                System.out.println("Comentarios: " + comentarios);
                System.out.println();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void actualizarInformacion(Connection conexion) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el código de la persona que desea actualizar: ");
        int codigo = scanner.nextInt();

        System.out.print("Ingrese el nuevo sueldo base: ");
        double nuevoSueldoBase = scanner.nextDouble();

        try {
            PreparedStatement statement = conexion.prepareStatement("UPDATE personas SET sueldo_base = ? WHERE codigo = ?");
            statement.setDouble(1, nuevoSueldoBase);
            statement.setInt(2, codigo);
            int filasActualizadas = statement.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Información actualizada exitosamente.");
            } else {
                System.out.println("No se encontró ninguna persona con el código especificado.");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void agregarPersona(Connection conexion) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre y apellido de la nueva persona: ");
        String nombreApellido = scanner.nextLine();

        System.out.print("Ingrese la fecha de registro (YYYY-MM-DD): ");
        String fechaRegistro = scanner.nextLine();

        System.out.print("Ingrese el sueldo base: ");
        double sueldoBase = scanner.nextDouble();

        System.out.print("Ingrese el sexo (M/F): ");
        String sexo = scanner.next();

        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();

        System.out.print("Ingrese la latitud de su casa: ");
        double latitud = scanner.nextDouble();

        System.out.print("Ingrese la longitud de su casa: ");
        double longitud = scanner.nextDouble();

        scanner.nextLine(); // Consumir la línea en blanco después de nextDouble()

        System.out.print("Ingrese comentarios: ");
        String comentarios = scanner.nextLine();

        try {
            PreparedStatement statement = conexion.prepareStatement("INSERT INTO personas (nombre_apellido, fecha_registro, sueldo_base, sexo, edad, latitud, longitud, comentarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, nombreApellido);
            statement.setString(2, fechaRegistro);
            statement.setDouble(3, sueldoBase);
            statement.setString(4, sexo);
            statement.setInt(5, edad);
            statement.setDouble(6, latitud);
            statement.setDouble(7, longitud);
            statement.setString(8, comentarios);
            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Nueva persona agregada exitosamente.");
            } else {
                System.out.println("No se pudo agregar la nueva persona.");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarPersona(Connection conexion) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el código de la persona que desea eliminar: ");
        int codigo = scanner.nextInt();

        try {
            PreparedStatement statement = conexion.prepareStatement("DELETE FROM personas WHERE codigo = ?");
            statement.setInt(1, codigo);
            int filasEliminadas = statement.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Persona eliminada exitosamente.");
            } else {
                System.out.println("No se encontró ninguna persona con el código especificado.");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
