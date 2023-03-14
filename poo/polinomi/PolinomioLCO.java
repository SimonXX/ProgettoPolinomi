package poo.polinomi;

import java.util.Iterator;


import poo.util.CollezioneOrdinata;
import poo.util.ListaConcatenataOrdinata;

public class PolinomioLCO extends PolinomioAstratto {

	CollezioneOrdinata<Monomio> lista = new ListaConcatenataOrdinata<>();
	
	public int size() {
		
		return lista.size();
		
	}//size
	
	public Iterator<Monomio> iterator(){
		return lista.iterator();
	}//iterator
	
	
	public void add(Monomio m) {
		
		if(m.getCoeff() == 0) return;
		
		Monomio corrente = lista.get(m);
		if(corrente==null) {
			
			lista.add(m);
			
		}else {
			
			lista.remove(corrente);
			Monomio ms = corrente.add(m);
			
			if(ms.getCoeff()!=0) {
				lista.add(ms);
			}
			
		}
		
	}//add
	
	public Polinomio factory() {
		return new PolinomioLCO();
	}//factory




}//PolinomioLCO
