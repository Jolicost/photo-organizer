package main;

import java.util.Scanner;

import javax.swing.JFileChooser;

public class LectorConsola extends LectorParametres {

	public LectorConsola(String fitxer){
		this.fitxer = fitxer;
		escullidor = new EscullidorCarpeta();
	}
	
	/* Fitxer on guardar la configuracio */
	protected String fitxer;
	
	/* Escullidor de carpeta, fer servir aixo per escollir carpetes */
	protected IEscullidorCarpeta escullidor;

	/* Llegir una configuracio per consola, els fitxers han de ser llegits utilitzant la interf�cie
	 */
	@Override
	protected Configuracio obtenirConfiguracio() {
		/* Escriure el teu codi aqui */
		
		/* Codi de prova per llegir 1 carpeta, pots executar amb el main de la classe Main */
		Configuracio ret = new Configuracio();

		//EL TEU CODI AQUI, cal manipular ret fent skmervir un escaner i l'objecte escullidor
		try{
		  System.out.println("Entra una ordre: origen / desti / mode / ordena ");
		  
		  Scanner in = new Scanner(System.in);
		  String line = in.nextLine();
		  while (!(line.equals("ordena"))){
		  		if (line.equals("origen")){
		  			ret.add(escullidor.triarCarpeta());
		  			System.out.println("Afegida una carpeta d'origen");
		  		}
		  		if (line.equals("desti")){
		  			ret.setDesti(escullidor.triarCarpeta());
		  			System.out.println("S'ha establert una carpeta de desti");
		  		}
		  		if (line.equals("mode")){
		  			System.out.println("carpeta unica / arbre carpetes");
		  			String lectura = in.nextLine(); 
		  			if (lectura.equals("carpeta unica")){
		  				Mode m = Mode.carpeta_unica;
		  				ret.setMode(m);
		  			}
		  			else{
		  				Mode m = Mode.arbre_carpetes;
		  				ret.setMode(m);
		  			}
		  		}
		  		System.out.println("Entra una ordre: origen / desti / mode / ordena");
		  		line = in.nextLine();
		  }
		} catch (Exception e) {
			System.err.println("Hi ha hagut un error");
		}
		 
		return ret;
	}
	
	
	
	
	
	/* Tot el que hi ha d'aqui en avall ja esta fet */
	protected interface IEscullidorCarpeta{
		public String triarCarpeta() throws TriaCancelada,ErrorTria;
	}
	
	private class TriaCancelada extends Exception{
		public String toString(){
			return "La tria ha estat cancelada";
		}
	}
	private class ErrorTria extends Exception{
		public String toString(){
			return "Error al triar el fitxer";
		}
	}
	protected class EscullidorCarpeta implements IEscullidorCarpeta{

		@Override
		public String triarCarpeta() throws TriaCancelada, ErrorTria {
			JFileChooser j = new JFileChooser();
			j.setDialogTitle("Tria un directori");
			j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ret = j.showOpenDialog(null);
			if (ret == JFileChooser.CANCEL_OPTION) throw new TriaCancelada();
			else if (ret == JFileChooser.ERROR_OPTION) throw new ErrorTria();
			else return j.getSelectedFile().getAbsolutePath();
		}
		
	}
}