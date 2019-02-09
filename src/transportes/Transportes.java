package transportes;

import java.util.Scanner;

/**
 * Clase del programa principal
 * @author Tomas
 */
public class Transportes {
    public static Orden[] ordenes;
    public static Vehiculo[] vehiculos;
    public static int numOrdenes = 0;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        boolean continuar = true, valid;
        int cedula, pasajeros, indVehiculo;
        char destino;
        
        // adios memoria :)
        ordenes = new Orden[90];
        
        vehiculos = new Vehiculo[] { 
            new Vehiculo(Destino.BARQUISIMETO),
            new Vehiculo(Destino.BARQUISIMETO),
            new Vehiculo(Destino.BARQUISIMETO),
            new Vehiculo(Destino.VALENCIA),
            new Vehiculo(Destino.VALENCIA),
            new Vehiculo(Destino.VALENCIA),
            new Vehiculo(Destino.PTO_LA_CRUZ),
            new Vehiculo(Destino.PTO_LA_CRUZ),
            new Vehiculo(Destino.PTO_LA_CRUZ)
        };
        
        do {
            System.out.println("Mensaje de bienvenida");
            System.out.println("Ingrese el numero de cedula para colocar una orden");
            cedula = sc.nextInt();
            do {
                System.out.println("Destinos disponibles (Ingrese la letra del que desee):");
                System.out.println("\tV. Valencia");
                System.out.println("\tP. Puerto La Cruz");
                System.out.println("\tB. Barquisimeto");
                destino = sc.next().toUpperCase().charAt(0);
                valid = Destino.esDestinoValido(destino);
                if ( ! valid )
                    System.out.println("Destino " + destino + " invalido. Vuelva a ingresar");
                else {
                    valid = cuposDisponibles(destino) > 0;
                    if ( !valid )
                        System.out.println("No hay cupos disponibles para " + Destino.getDestinoStr(destino) + ".");
                }
            } while ( ! valid );
            do {
                System.out.println("Ingrese la cantidad de pasajeros que iran a " + Destino.getDestinoStr(destino));
                pasajeros = sc.nextInt();
                if (Vehiculo.MAX_PASAJEROS < pasajeros) {
                    System.out.println("No se pueden colocar mas de " + Vehiculo.MAX_PASAJEROS + " pasajeros");
                    System.out.println("Vuelva a intentar");
                }
            } while (Vehiculo.MAX_PASAJEROS < pasajeros);
            Vehiculo[] vehiculosDestino = getVehiculosPorDestino(destino);
            for (int i = 0; i < vehiculosDestino.length; i++) {
                System.out.print("Cupos disponibles en el vehiculo " + (i + 1) + " con destino a " + Destino.getDestinoStr(vehiculosDestino[i].getDestino()));
                System.out.println(" " + ( Vehiculo.MAX_PASAJEROS - vehiculosDestino[i].getPasajeros()));
            }
            do {
                System.out.println("Ingrese el numero del vehiculo donde quiera agregar los " + pasajeros + " pasjaeros");
                indVehiculo = sc.nextInt();
                if (indVehiculo < 1 || indVehiculo > 3)
                    System.out.println("Numero de vehiculo no reconocido. Vuelva a intentar");
                else {
                    valid = vehiculosDestino[indVehiculo].addPasajeros(pasajeros);
                    if ( !valid )
                        System.out.println("No se pueden ingresar todos esos pasajeros en este vehiculo. Escoja otro");
                }
            } while ( indVehiculo < 1 || indVehiculo > 3 || !valid );
            
            /* Para este punto todo deberia ser valido */ 
            ordenes[numOrdenes] = new Orden(cedula, vehiculosDestino[indVehiculo], pasajeros);
            
            // Si se colocaron mas de 4 pasajeros, hay que aplicar descuento
            if ( pasajeros > 4 ) {
                ordenes[numOrdenes].setDesc(20);
                System.out.println("Se le hara un descuento de 20% por pagar más de 4 personas");
            }
            
            // Si la orden es numero perfecto, la hacemos gratuita
            if ( esPerfecto(numOrdenes) ) {
                ordenes[numOrdenes].setDesc(100);
                System.out.println("Se le hara un descuento de 100% ser un cliente perfecto :0!");
            }
            
            // Terminamos de procesar esta orden
            printOrden(ordenes[numOrdenes++]);
            
            // Verificamos si no se llenaron todos los cupos
            if ( (cuposDisponibles(Destino.VALENCIA) == 0 )&&
                  (cuposDisponibles(Destino.BARQUISIMETO) == 0) &&
                  (cuposDisponibles(Destino.PTO_LA_CRUZ) == 0)
                ) {
                System.out.println("Se han llenado todos los cupos. El dia se da por terminado");
                continuar = false;
            } else {
                System.out.println("Desea inscribir otro cliente? \n0. No \nOtro valor. Si");
                continuar = sc.nextInt() != 0;
            }
        } while (continuar);
        
        System.out.println("Hemos llegado al final del dia. Estadisticas:");
        char[] destinos = new char[] {'V', 'B', 'P'};
        String destinoStr;
        double totalDest = 0, total = 0;
        for(char d: destinos) {
            totalDest = totalesPorDestino(d);
            destinoStr = Destino.getDestinoStr(d);
            total += totalDest;
            System.out.println("Cantidad de clientes de " + destinoStr + ": " + ordenesPorDestino(d));
            System.out.println("Total facturado por " + destinoStr + ": " + totalDest);
            System.out.println("Cantidad de descuentos en " + destinoStr + ": " + descuentosPorDestino(d));
        }
        System.out.println("Total facturado por la wea: " + total);
    }
    
    public static double totalesPorDestino(char d) {
        double total = 0.0f;
        for(Orden orden: ordenes)
            if ( orden.getVehiculo().getDestino() == d )
                total += orden.getPrecioFinal();
        return total;
    }
        
    public static int ordenesPorDestino(char d) {
        int o = 0;
        for(Orden orden: ordenes)
            if (orden.getVehiculo().getDestino() == d) 
                o++;
        return o;
    }
    
    public static int descuentosPorDestino(char d) {
        int descuentos = 0;
        for(Orden orden: ordenes)
            if (orden.getVehiculo().getDestino() == d && orden.getDesc() != 0)
                descuentos++;
                
        return descuentos;
    }
    
    public static Vehiculo[] getVehiculosPorDestino(char destino) {
        Vehiculo[] retVal = new Vehiculo[3];
        int j = 0;
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getDestino() == destino) {
                retVal[j++] = vehiculo;
            }
        }
        return retVal;
    }
    
    public static boolean esPerfecto(int n) {
        int suma = 0, prod = 1;
        for (int i = n - 1; i >= 1; i-- )
            if ( i % n == 0 ) {
                suma += i;
                prod += i;
            }
        return suma == prod;
    }
    
    public static int cuposDisponibles(char d) {
        int cuposTotales = Vehiculo.MAX_PASAJEROS * 3,
            cuposTomados = 0;
        Vehiculo[] vehiculos = getVehiculosPorDestino(d);
        for(Vehiculo vehiculo: vehiculos)
            cuposTomados += vehiculo.getPasajeros();
        
        return cuposTotales - cuposTomados;
    }
    
    public static void printOrden(Orden orden) {
        System.out.println("----------------");
        System.out.println("Resumen de orden");
        System.out.println("Numero de cedula: " + orden.getCedula());
        System.out.println("Cantidad de pasajeros: " + orden.getPasajeros());
        System.out.println("Codigo y nombre del destino: \n\tCodigo: " + orden.getVehiculo().getDestino() + "\n\tDestino: " + Destino.getDestinoStr(orden.getVehiculo().getDestino()));
        System.out.println("Monto bruto: " + orden.getPrecio());
        System.out.println("Monto de descuento: " + (orden.getPrecio() * (orden.getDesc() / 100f)));
        System.out.println("Monto de impuesto: " + orden.getPrecioIVA());
        System.out.println("Monto neto: " + orden.getPrecioFinal());
        System.out.println("----------------");
    }
}
