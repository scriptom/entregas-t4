package transportes;

public class Orden {
    public static final double IVA = 0.12;
    private int pasajeros = 0;
    private Vehiculo vehiculo;
    private double precio;
    private int cedulaCliente;
    private double desc = 0.0f;
       
    public Orden(int cedulaCliente, Vehiculo vehiculo, int pasajeros) {
        this.cedulaCliente = cedulaCliente;
        this.vehiculo = vehiculo;
        this.pasajeros = pasajeros;
        switch(vehiculo.getDestino()) {
            case Destino.VALENCIA:
                this.precio = 12500;
                break;
            case Destino.PTO_LA_CRUZ:
                this.precio = 16000;
                break;
            case Destino.BARQUISIMETO:
                this.precio = 18500;
                break;
        }
        this.precio *= pasajeros;
    }
        
    public int getPasajeros() {
        return pasajeros;
    }
        
    public boolean addPasajeros(int pasajeros) {
        if (Vehiculo.MAX_PASAJEROS < this.pasajeros + pasajeros)
            return false;
        this.pasajeros += pasajeros;
        return true;
    }
        
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public double getDesc() {
        return desc;
    }
    
    public void setDesc(double desc) {
        if (desc > 100)
            desc = 100f;
        else if (desc < 0)
            desc = 0f;
        this.desc = desc/100f;
    }
    
    public double getPrecioFinal() {
        return getPrecioDesc() + getPrecioIVA();
    }
    
    public double getPrecioDesc() {
        return precio - (precio * (desc / 100f));
    }
    
    public double getPrecioIVA() {
        return getPrecioDesc() * IVA;
    }
    
    public int getCedula() {
        return cedulaCliente;
    }
    
    public void setCedula(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }
}
