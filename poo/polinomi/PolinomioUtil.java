package poo.polinomi;

import java.util.StringTokenizer;

public final class PolinomioUtil {
	
	//data una stringa contenente un monomio (scritto correttamente poiché validato precedentemente con il metodo monomioValido) crea il Monomio corrispondente 
	 	public static Monomio isolaMonomio(String monomio) {
	 		
	 		Monomio m;
	 		
	 		StringTokenizer st = new StringTokenizer(monomio, "x^", false);
	 		
	 		//abbiamo solo una x senza parte intera e senza grado
	 		if(monomio.equals("x"))
	 			return new Monomio(1, 1);
	 		
	 		String coeff = null;
	 		String grado = null;
	 		String tmp = st.nextToken();
	 		
	 		boolean c = false;//verifica presenza della parte intera
	 		boolean g = false;//verifica presenza della parte letterale
	 		
	 		if(monomio.contains("^")) {//abbiamo sicuramente l'esponente della x del monomio
	 			
	 			g = true;
	 			if(st.hasMoreTokens()) {//abbiamo parte intera e letterale
	 				c = true;
	 	 			grado = st.nextToken();
	 	 			if(tmp.equals("+") || tmp.equals("-"))//abbiamo solo il segno della parte intera
	 	 				coeff = tmp + "1";
	 	 			else//abbiamo una parte intera completa
	 	 				coeff = tmp;
	 	 		}else {//sapendo per certo di avere l'esponente, quando non abbiamo parte intera il grado è l'unico token trovato
	 	 			grado = tmp;
	 	 		}
	 			
	 		}else if(!monomio.contains("^")){//non abbiamo esponente, a meno di avere solo la x (grado = 1)
	 			
	 			c = true;
	 			if(tmp.equals("+") || tmp.equals("-"))//abbiamo solo il segno della parte intera
		 				coeff = tmp + "1";
		 			else//abbiamo una parte intera completa
		 				coeff = tmp;
	 			
	 			if(monomio.contains("x")) {//se non abbiamo esponente, ma esiste la x, allora il grado è 1
	 				g = true;
	 				grado = "1";
	 			}
	 			
	 		}
	 		
	 		if(!c)
	 			coeff = "1";
	 		if(!g)
	 			grado = "0";
	 		
	 	
	 		m = new Monomio(Integer.parseInt(coeff), Integer.parseInt(grado));
	 		
	 		return m;
	 		
	 	}//isolaMonomio
	 	
	 	//dato un monomio sottoforma di String, ne verifica la validità
	 	public static boolean monomioValido(String monomio) {
	 
			String COEF="\\d+";
			String LETT="x(\\^([2-9]|1\\d)\\d*)?";
			
			String UMON="("+COEF+"|"+LETT+"|"+COEF+LETT+")"; 
			
			//il monomio è valido se viene scritto con segno -; con segno +(anche se ridondante); con solo la parte letterale; con solo il coefficiente
			//i monomi immessi senza segno verranno assunti positivi
			String MONOMIO = "(\\-?||\\+?)" + UMON;
			
			
				
			if(monomio.matches(MONOMIO))
				return true;
			
			return false;
			
			
	 	}//monomioValido
	
	 	
	 	//dato un polinomio sottoforma di String, ne verifica la validità
	 	public static boolean polinomioValido(String polinomioString) {

	 		String SIGN="[\\-\\+]";
			String COEF="\\d+";
			String LETT="[xX](\\^([2-9]|1\\d)\\d*)?";
			
			String UMON="("+COEF+"|"+LETT+"|"+COEF+LETT+")"; 
			
			String POL="(\\-?||\\+?)"+UMON+"("+SIGN+UMON+")*";
			
			if(!polinomioString.matches(POL))
				return false;

	 		return true;
	 		
	 	}//polinomioValido
	 	
	 	
	 	public static boolean numeroRealeValido(String reale) {
	 		
	 		String REAL = "\\-?(\\d+|(\\d+)?\\.\\d+)";
	 		
	 		if(reale.matches(REAL))
	 			return true;
	 		
	 		return false;
	 		
	 	}//numeroRealeValido

}//PolinomioUtil
