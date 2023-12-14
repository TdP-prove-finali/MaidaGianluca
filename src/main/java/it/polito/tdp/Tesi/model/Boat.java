package it.polito.tdp.Tesi.model;


public class Boat implements Comparable<Boat> {

	Integer id;
	Integer prezzo;
	String tipologia;
	String manifattura;
	String condizione;
	Integer anno;
	Double lunghezza;
	Double larghezza;
	String Luogo;
	Integer guadagno;
	
	public Boat(Integer id, Integer prezzo, String tipologia, String manifattura, String condizione, Integer anno,
			Double lunghezza, Double larghezza, String luogo) {
		super();
		this.id = id;
		this.prezzo = prezzo;
		this.tipologia = tipologia;
		this.manifattura = manifattura;
		this.condizione = condizione;
		this.anno = anno;
		this.lunghezza = lunghezza;
		this.larghezza = larghezza;
		Luogo = luogo;
		this.setGuadagno();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getManifattura() {
		return manifattura;
	}

	public void setManifattura(String manifattura) {
		this.manifattura = manifattura;
	}

	public String getCondizione() {
		return condizione;
	}

	public void setCondizione(String condizione) {
		this.condizione = condizione;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Double getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(Double larghezza) {
		this.larghezza = larghezza;
	}

	public String getLuogo() {
		return Luogo;
	}

	public void setLuogo(String luogo) {
		Luogo = luogo;
	}
	
	public Double getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public Integer getGuadagno() {
		return guadagno;
	}

	public void setGuadagno() {
		int tot=0;
		if(this.lunghezza<=10) {
			this.guadagno=100;
		}else {
			this.guadagno=500;
		}
//		if(this.){
//			
//		}else {
//			
//		}
//		this.guadagno = tot + 10 +this.prezzo;   // sistemare
	}




	@Override
	public String toString() {
		return "Boat [id=" + id + ", prezzo=" + prezzo + ", tipologia=" + tipologia + ", manifattura=" + manifattura
				+ ", condizione=" + condizione + ", anno=" + anno + ", lunghezza=" + lunghezza + ", larghezza="
				+ larghezza + ", Luogo=" + Luogo + "]";
	}

	@Override
	public int compareTo(Boat o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
}
