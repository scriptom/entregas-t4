package transportes;

/**
 *
 * @author Usuario Estandar
 */
public class Destino {
    public static final char VALENCIA = 'V';
    public static final char BARQUISIMETO = 'B';
    public static final char PTO_LA_CRUZ = 'P';
    
    public static String getDestinoStr(char d) {
        switch(d) {
            case VALENCIA:
                return "Valencia";
            case BARQUISIMETO:
                return "Barquisimeto";
            case PTO_LA_CRUZ:
                return "Puerto La Cruz";
            default:
                return "Destino inválido";
        }
    }
    
    public static boolean esDestinoValido(char d) {
        return (VALENCIA == d || BARQUISIMETO == d || PTO_LA_CRUZ == d);
    }
}
