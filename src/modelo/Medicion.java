package modelo;



import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;



public class Medicion {

	private LocalDate fecha;
    private LocalTime hora;
    private int anchoBanda;
    private int ipConectadas;
	private int throughput;
	private int latencia;
	private double tasaErrores;
	private float flujoDatos;
	
	
	
	public Medicion() {
		this.setFecha(calcularFecha());
		this.setHora(calcularHora ());
		this.setAnchoBanda(calcularAnchoBanda ());
		this.setIpConectadas(calcularIpConectadas ());
		this.setThroughput(calcularThroughput ());
		this.setLatencia(calcularLatencia ());
		this.setTasaErrores(calcularTasaErrores ());
		this.setFlujoDatos(calcularFlujoDatos ());
		
	}
		
		protected LocalDate calcularFecha() {
			return LocalDate.now();
		}
		
		protected LocalTime calcularHora() {
			return LocalTime.now();
		}
		
		protected int calcularAnchoBanda () {
			Random aleatorio = new Random ();
			return aleatorio.nextInt (100+1-0)+0;
		}
		
		protected int calcularThroughput () {
			Random aleatorio = new Random ();
			return aleatorio.nextInt(1000+1-20)+20;
		}
		
		protected int calcularLatencia () {
			Random aleatorio = new Random ();
			return aleatorio.nextInt(150+1-0)+0;
		}
		
		protected double calcularTasaErrores () {
			Random aleatorio = new Random ();
			return aleatorio.nextDouble() * (0.00001-0.00000000001)+0.00000000001;
			
		}
		
		protected float calcularFlujoDatos () {
			Random aleatorio = new Random ();
			return aleatorio.nextFloat(0.01f+0.000000001f)+0.000000001f;
		}
		
		protected int calcularIpConectadas () {
			Random aleatorio = new Random ();
			return aleatorio.nextInt (500+1-0)+0;
		}
		
		public String medicionCsv() {
	    	String medicionCsv= this.getFecha().toString()+ ";"+ this.getHora()+ ";" +this.getAnchoBanda()+";"+this.getIpConectadas()+";"
	    						+this.getThroughput()+";"+this.getLatencia()+";"+this.getTasaErrores()+";"+this.getFlujoDatos();
	    		
		    return medicionCsv;
		}
		
		public String [] medicionJTable () {
			
			
			String [] medicionTabla = {getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),getHora().format(DateTimeFormatter.ofPattern("kk:mm:ss")),Integer.toString(getAnchoBanda()),
					                  Integer.toString(getIpConectadas()),Integer.toString(getThroughput()),Integer.toString(getLatencia()),
					                  Double.toString(getTasaErrores()),Float.toString(getFlujoDatos())};
			return medicionTabla;
		}
		public String medicionJTexPane() {
			String fechaHora = getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" - "+getHora().format(DateTimeFormatter.ofPattern("kk:mm:ss"))+ " - ";
			String anchoBandaError ="El ancho de banda de la red ha superado el 90% de su capacidad.\n";
			String latenciaError= "La red presenta problemas graves de latencia.\n";
			String tasaError= "La tasa de errores de la red es muy elevada.\n";
			String medicionMostrar=null;
			if(getAnchoBanda()>90) {
				medicionMostrar=fechaHora+anchoBandaError;
			}
			if(getLatencia()>100&&medicionMostrar!=null) {
				medicionMostrar=medicionMostrar+fechaHora+latenciaError;
			} else if (getLatencia()>100) {
				medicionMostrar=fechaHora+latenciaError;
			}
			//if(getTasaErrores()>0.00001&&medicionMostrar!=null) {
			//	medicionMostrar=medicionMostrar+fechaHora+tasaError;
			//} else if (getTasaErrores()>0.00001){
			//medicionMostrar=fechaHora+tasaError;
		//}
			
			return medicionMostrar;
		}
		
		public int medicionReportes () {
			int reportes = 0;
			if(getAnchoBanda()>90) {
				reportes++;
			}
			if (getLatencia()>100) {
				reportes++;
			}
			
			return reportes;
		}

		public LocalDate getFecha() {
			return fecha;
		}

		public void setFecha(LocalDate fecha) {
			this.fecha = fecha;
		}

		public LocalTime getHora() {
			return hora;
		}

		public void setHora(LocalTime hora) {
			this.hora = hora;
		}

		public int getAnchoBanda() {
			return anchoBanda;
		}

		public void setAnchoBanda(int anchoBanda) {
			this.anchoBanda = anchoBanda;
		}

		public int getIpConectadas() {
			return ipConectadas;
		}

		public void setIpConectadas(int ipConectadas) {
			this.ipConectadas = ipConectadas;
		}

		public int getThroughput() {
			return throughput;
		}

		public void setThroughput(int throughput) {
			this.throughput = throughput;
		}

		public int getLatencia() {
			return latencia;
		}

		public void setLatencia(int latencia) {
			this.latencia = latencia;
		}

		public double getTasaErrores() {
			return tasaErrores;
		}

		public void setTasaErrores(double tasaErrores) {
			this.tasaErrores = tasaErrores;
		}

		public float getFlujoDatos() {
			return flujoDatos;
		}

		public void setFlujoDatos(float flujoDatos) {
			this.flujoDatos = flujoDatos;
		}
}
