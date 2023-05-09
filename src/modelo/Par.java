package modelo;

public class Par <K,V> {

	private final K clave;

    private final V valor;

    public Par(final K clave, final V valor) {
        this.clave = clave;
        this.valor = valor;
    }
    
 

    public static <K, V> Par<K, V> of(K clave, V valor) {
        return new Par<>(clave, valor);
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }
	
	
}
