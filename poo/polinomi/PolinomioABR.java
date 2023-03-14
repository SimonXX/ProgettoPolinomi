package poo.polinomi;

import poo.util.ABR;
import poo.util.CollezioneOrdinata;
import java.util.Iterator;

public class PolinomioABR extends PolinomioAstratto{
	
	private CollezioneOrdinata<Monomio> abr = new ABR<>();
	
	public PolinomioABR factory() {return new PolinomioABR();}
	
	public int size() {
		
		return abr.size();
		
	}//size
	
	public Iterator<Monomio> iterator(){
		return abr.iterator();
	}//Iterator
	
	public void add(Monomio m) {
		
		if(m.getCoeff()==0) return;
		boolean flag = false;
		
	
		Iterator<Monomio> it = abr.iterator();
		Monomio cor = null;
		Monomio somma = null;
		
		while(it.hasNext()) {
			cor = it.next();
			if(cor.equals(m)) {
				somma = cor.add(m);
				it.remove();
				flag = true;
				break;
			}
		}
		
		if(flag && somma.getCoeff()!=0) {
			abr.add(somma);
		}
		
		if(!flag) {
			abr.add(m);
		}
		
	}//add
	
	

}//PolinomioABR
