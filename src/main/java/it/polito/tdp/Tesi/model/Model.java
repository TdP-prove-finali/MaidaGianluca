package it.polito.tdp.Tesi.model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;



public class Model {
	
	private List<Boat> best=new ArrayList<>();
	private List<Boat> best2=new ArrayList<>();
	private List<Boat> parziale=new ArrayList<>();

	

	public void barche(List<Boat> listaBarche, int budget, int lunghezza) {
		barche_ricorsiva(parziale, budget, listaBarche, lunghezza );
		System.out.println("La soluzione è:");
		if(this.calcola_guadagno(best)>this.calcola_guadagno(best2)) {
			for(Boat b:best){
				System.out.println(b);
			}
		}else {
			for(Boat b:best){
				System.out.println(b);
			}
			System.out.println("In alternativa:");
			for(Boat b:best2){
				System.out.println(b);
			}
		}
	
//		calprezzo(parziale)==budget
	}
	
	
	private void barche_ricorsiva(List<Boat> parziale, int budget, List<Boat> listaBarche, int lunghezza) {
		if(parziale.size()>0 ) {  //caso terminale
			int disponibile=budget-this.calcola_prezzo(parziale);
			int disponibile2=budget+(budget/100)-this.calcola_prezzo(parziale);
			int lunghezza_disponibile=lunghezza-this.calcola_lunghezza(parziale);
			if(calcola_guadagno(parziale)>calcola_guadagno(best)) { 
				if(disponibile>=0) {
					if((disponibilita(disponibile,listaBarche) && lunghezza_disponibile(lunghezza_disponibile,listaBarche) || disponibile==0 )){
						this.best=new ArrayList<>(parziale);
						return;
					}
				}else if((disponibilita2(disponibile2,listaBarche) && lunghezza_disponibile(lunghezza_disponibile,listaBarche) || disponibile2==0 )){
						if(disponibilita2(disponibile2,listaBarche)) {
							this.best2=new ArrayList<>(parziale);
							return;
						}
				}
			}
		}
		
		for(Boat b:listaBarche) {
			if(budget>=(calcola_prezzo(parziale)+b.prezzo) && !parziale.contains(b) && lunghezza>=(calcola_lunghezza(parziale)+b.lunghezza)) {
				parziale.add(b);
				this.barche_ricorsiva(parziale, budget, listaBarche,lunghezza);
				parziale.remove(b);
			}else if((budget+(budget/100))>=(calcola_prezzo(parziale)+b.prezzo) && !parziale.contains(b) && lunghezza>=(calcola_lunghezza(parziale)+b.lunghezza)) {
				parziale.add(b);
				this.barche_ricorsiva(parziale, budget, listaBarche,  lunghezza);
				parziale.remove(b);
			}
		}
		
	}
	
	
	public int calcola_prezzo(List<Boat> l) {
		int tot=0;
		for(Boat b:l){
			tot+=b.prezzo;
		}
		return tot;
	}
	public int calcola_guadagno(List<Boat> l) {
		int tot=0;
		for(Boat b:l){
			//tot+=b.guadagno;
		}
		return tot;
	}
	public int calcola_lunghezza(List<Boat> l) {
		int tot=0;
		for(Boat b:l){
			tot+=b.lunghezza;
		}
		return tot;
	}
	
	
	
	public boolean disponibilita(int disponibili,List<Boat> listaBarche) {
		ArrayList<Boat> lb=new ArrayList<>(listaBarche);
		Collections.sort(lb);
		for(Boat b:listaBarche){
			if(b.prezzo<=disponibili && !parziale.contains(b)){	
				return false;
			}
		}
		return true;
	}
	public boolean disponibilita2(int disponib,List<Boat> listaBarche) {
		ArrayList<Boat> lb=new ArrayList<>(listaBarche);
		Collections.sort(lb);
		for(Boat b:listaBarche){
			if(b.prezzo<=disponib && !parziale.contains(b)){	
				return false;
			}
		}
		return true;
	}
	public boolean lunghezza_disponibile(int l ,List<Boat> listaBarche) {
		ArrayList<Boat> lb=new ArrayList<>(listaBarche);
		Collections.sort(lb);
		for(Boat b:listaBarche){
			if(b.lunghezza<=l && !parziale.contains(b)){	
				return false;
			}
		}
		return true;
	}

	
	
	
	
	
}
