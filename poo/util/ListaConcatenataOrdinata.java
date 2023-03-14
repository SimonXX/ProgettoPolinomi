package poo.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaConcatenataOrdinata <T extends Comparable<? super T>> extends CollezioneOrdinataAstratta<T>{
	
	
	private static class Nodo<E>{
		
		E info;
		Nodo<E> next; 
		
	}//Nodo
	
	private Nodo<T> testa=null;
	
	private int size = 0;
	
	private int contMod = 0; 
	
	public int size() {
		return size;
	}//size
	
	public void clear() {
		testa = null;
		size = 0;
		contMod++;
	}//clear
	
	public boolean contains(T e){
		
		Nodo<T>cor=testa; 
		while(cor != null) {
			
			if(cor.info.equals(e))return true;
			if(cor.info.compareTo(e)>0)return false;
			cor = cor.next;
			
		}
		
		return false;
		
	}//contains

	public T get(T e) {
			
		Nodo<T>cor=testa;
				
		while(cor != null) {
			
			if(cor.info.equals(e)) return cor.info;
			
			if(cor.info.compareTo(e)>0)return null;
			cor = cor.next;
				
		}
		
		return null;
	}//get
	
	public boolean isEmpty() {
		
		return testa == null;
	}//isEmpty
	
	public boolean isFull() {
		return false;
	}//isFull
	
	public void add(T e) {
		Nodo<T> n = new Nodo<>();
		n.info = e;
		
		if(testa == null||testa.info.compareTo(e)>0) {
			n.next = testa;
			testa = n;
		}
		else {
			Nodo<T> pre = testa; 
			Nodo<T> cor=testa.next;
			
			while(cor!=null&&cor.info.compareTo(e)<0) {
				pre = cor;
				cor = cor.next;
			}
			
			//inserisci n tra pre e cor
			pre.next = n;
			n.next = cor;
			
		}
		
		size++;
		contMod++;
	}//add
	
	public void remove(T e) {
		
		Nodo<T> pre = null;
		Nodo<T> cor = testa;
		
		while(cor!=null && cor.info.compareTo(e)<0) {
			//avanza
			pre=cor;
			cor=cor.next;
		}
		
	
		
		if(cor!=null && cor.info.equals(e)) {
			
			if(cor == testa) testa = testa.next;
			
	
			else pre.next=cor.next; //bypass
			size--;
			contMod++;
		}
		
	}//remove
	
	public Iterator<T> iterator(){
		return new IteratoreLista();
	}//iterator
	
	private class IteratoreLista implements Iterator<T>{

		
		private Nodo<T> pre = null, cor = null;	
		
		private int contModIte = contMod; 
		
		
	
		@Override
		public boolean hasNext() {
			
			
			if(cor==null) return testa != null;
	 
		
			return cor.next!=null;
			
		}//hasNext

		@Override
		public T next() {
			
			if(contModIte!=contMod)
				throw new ConcurrentModificationException();
			
			if(!hasNext()) throw new NoSuchElementException();
			

			
			if(cor==null)cor = testa;
			
			else {
				pre=cor;
				cor = cor.next;
			}
		
		
			return cor.info;
		}//next
		
		@Override 
		public void remove() {
			
			if(contModIte!=contMod)
				throw new ConcurrentModificationException();
			

			
			if(pre==cor)throw new IllegalStateException();
	
			if(cor==testa) testa = testa.next;
			
			else {
				
				pre.next = cor.next;
			}
			
			size --;
			cor=pre;
			
			contMod++; contModIte++;
			
		}//remove
		
	}//IteratoreLista
	

}//ListaConcatenataOrdinata
