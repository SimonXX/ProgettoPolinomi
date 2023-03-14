package poo.util;

import java.util.Iterator;

public class ABR <T extends Comparable<? super T>> extends CollezioneOrdinataAstratta<T>{
	
	private static class Nodo<E>{
		E info;
		Nodo<E> fS, fD;
		
	}
	
	private Nodo<T> radice = null;
	

	@Override
	public int size() {
		return size(radice);
	}
	
	private int size(Nodo<T> albero) {
		
		if(albero == null) return 0;
		
		return 1+size(albero.fS)+size(albero.fD);
		
	}//size
	
	@Override
	public void clear() {
		radice = null;
		
	}

	@Override
	public boolean contains(T x) {
		return contains(radice, x);
	}//contains
	
	private boolean contains(Nodo<T> albero, T x) {
		
		if(albero == null) return false;
		if(albero.info.equals(x)) return true;
		if(albero.info.compareTo(x)>0) return contains(albero.fS, x);
		
		return contains(albero.fD, x);
		
	}//contains

	@Override
	public void add(T x) {
		radice = add(radice, x);
		
	}
	
	private Nodo<T> add(Nodo<T> albero, T x){
		
		if(albero == null) {
			Nodo<T> n  = new Nodo<>();
			n.info = x;
			n.fS = null;
			n.fD = null;
			return n;
		}
		
		if(albero.info.compareTo(x)>0) {
				albero.fS = add(albero.fS, x);
				return albero;
		}
		
		albero.fD = add(albero.fD, x);
		
		return albero; 
		
	}//add

	@Override
	public void remove(T x) {

		radice = remove(radice, x);
		
	}
	
	private Nodo<T> remove(Nodo<T> albero, T x){
		if(albero == null) {
			return null;
		}
		
		if(albero.info.compareTo(x)>0) {
			albero.fS = remove(albero.fS, x);
			return albero;
		}
		
		if(albero.info.compareTo(x)<0) {
			albero.fD = remove(albero.fD, x);
			return albero;
		}
		
	
		if(albero.fS == null && albero.fD == null) return null;
		
		if(albero.fS == null) return albero.fD;
		
		if(albero.fD == null) return albero.fS;
		

		if(albero.fD.fS == null) {
			albero.info = albero.fD.info;
			albero.fD = albero.fD.fD;
			return albero;
		}
	
		Nodo<T> padre  = albero.fD;
		Nodo<T> figlio = padre.fS;
		
		while(figlio.fS!=null) {
			padre = figlio;
			figlio = figlio.fS;
		}
		
		//promozione
		albero.info = figlio.info;
		//rimozione della vittima
		padre.fS = figlio.fD;
		return albero;
		
	}//remove
	
	public void visitaSimmetrica(java.util.List<T> ls) {
		
		visitaSimmetrica(radice, ls);
		
	}//visitaSimmetrica

	private void visitaSimmetrica(Nodo<T> albero, java.util.List<T> ls) {
		
		if(albero!=null) {
			visitaSimmetrica(albero.fS, ls);
			ls.add(albero.info);
			visitaSimmetrica(albero.fD, ls);
		}
	}//visitaSimmetrica
	

	
	public Iterator<T> iterator(){
		
		return new ABRIterator();
		
	}//iterator
	
	private class ABRIterator implements Iterator<T>{


		private java.util.List<T> ls = new java.util.ArrayList<>();
		private Iterator<T> it = null;
		private T cor = null;
		
		public ABRIterator() {
			visitaSimmetrica(ls);
			it = ls.iterator();
		}
	
		public boolean hasNext() {
			return it.hasNext();
		}


		public T next() {
			cor = it.next();
			
			return cor;
			
		}
		
		public void remove() {
			it.remove();
			ABR.this.remove(cor);
			cor = null;
		}
		
	}//ABRIterator
	
	@Override
	public T get(T x) {
		return get(radice, x);
	}

	private T get(Nodo<T> albero, T x) {
		
		if(albero == null) return null;
		
		if(albero.info.equals(x)) return albero.info;
		
		if(albero.info.compareTo(x) > 0 ) return get(albero.fS, x);
		
		return get(albero.fD, x);
	}
	@Override
	public boolean isEmpty() {
		return radice == null;
	}

	@Override
	public boolean isFull() {
		return false;
	}
	
	public void visitaPerLivelli(java.util.List<T> ls) {
		
		
		java.util.LinkedList<Nodo<T>> coda = new java.util.LinkedList<>();
		
		coda.addLast(radice);
		while(!coda.isEmpty()) {
			Nodo<T> albero = coda.removeFirst();
			ls.add(albero.info);//visita
			if(albero.fS != null) coda.addLast(albero.fS);
			if(albero.fD != null) coda.addLast(albero.fD);
			
		}
		
	}//visitaPeLivelli
	

}//ABR
