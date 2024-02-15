
package it.polito.tdp.Tesi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.Tesi.db.BoatDAO;
import it.polito.tdp.Tesi.model.Boat;
import it.polito.tdp.Tesi.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	public Model model;
	public BoatDAO dao = new BoatDAO();
	List<Boat> lBoatSel = new ArrayList<Boat>();
	List<Boat> Allbest= new ArrayList<Boat>();
	List<Boat> best= new ArrayList<Boat>();
	List<Boat> best2= new ArrayList<Boat>();
	int budg;
	int insertId=0;
	Boat insertBoat;


	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField AnnoMax;

	@FXML
	private TextField AnnoMin;

	@FXML
	private Button BtnId;

	@FXML
	private TextField Budget;

	@FXML
	private ChoiceBox<String> Condizione;

	@FXML
	private TextField Id;

	@FXML
	private Button Lista;

	@FXML
	private TextField LunghezzaMax;

	@FXML
	private TextField LunghezzaMin;

	@FXML
	private Button ResetFiltri;

	@FXML
	private Button Soluzione;

	@FXML
	private ChoiceBox<String> Tipologia;

	@FXML
	private TextArea TxtResult;

	@FXML
	void ActionBtnId(ActionEvent event) {
			try {
				insertId = Integer.parseInt(Id.getText());
				for(Boat b: this.lBoatSel) {
					if(b.getId()==insertId) {
						TxtResult.appendText("\nBarca inserita correttamente nella lista delle soluzioni\n");
						insertBoat=b;
						Id.clear();
						BtnId.setDisable(true);
						return;
					}
				}
				TxtResult.appendText("Inserire un ID corretto \n");
			} catch (NumberFormatException e) {
				TxtResult.appendText("Inserire un ID corretto \n");
			}
	}

	@FXML
	void ActionLista(ActionEvent event) {
		initialize();
		switch(Lista.getText()) {
			case "LISTA":
				if(setFiltri()) {
					if (lBoatSel.size() != 0) {
						TxtResult.appendText("LISTA\n\nLe barche che rispettano i criteri sono: \n\n");
						for (Boat b : lBoatSel) {
							TxtResult.appendText(b.toString() + "\n");
						}
					} else {
						TxtResult.appendText("LISTA\n\nNon ci sono barche che rispettano i criteri.\n\nModificare i parametri inseriti"
												+ " o effettuare una nuova ricerca premendo sul bottone reset filtri");
						Soluzione.setDisable(true);
					}
				}
				break;
			case "ANNULLA":
				TxtResult.appendText("Acquisto annullato\nProcedere con una nuova soluzione");
				Soluzione.setText("GENERA SOLUZIONE");
				Lista.setText("LISTA");
				BtnId.setDisable(false);
				break;
			case "CONFERMA 2° SOL":
				TxtResult.appendText("Acquisto confermato con successo!\n\nLista barche acquistate fin'ora:\n");
				Allbest.addAll(best2);
				for (Boat b : Allbest) {
					TxtResult.appendText(b.toString()+"\n");
				}
				TxtResult.appendText("\nBudget speso: "+model.calcola_prezzo(this.Allbest)+"\nGuadagno totale atteso: "+model.calcola_guadagno(this.Allbest));
				TxtResult.appendText("\n\nProcedere con una nuova ricerca con il budget rimanente, modificare i filtri/budget o ricominciare\n");
				budg-=model.calcola_prezzo(best2);
				Budget.setText(Integer.toString(budg));
				Soluzione.setText("NUOVA RICERCA");
				Lista.setText("RESET");
				ResetFiltri.setText("RESET FILTRI");
				break;
			default:
				ActionResetFiltri(event);
				initialize();
				Allbest= new ArrayList<Boat>();
				Soluzione.setText("GENERA SOLUZIONE");
				Lista.setText("LISTA");
				ResetFiltri.setText("RESET FILTRI");
				BtnId.setDisable(false);
		}		
	}

	@FXML  
	void ActionResetFiltri(ActionEvent event) {
		initialize();
		if(ResetFiltri.getText().equals("ANNULLA")) {
			TxtResult.appendText("Acquisto annullato\nProcedere con una nuova soluzione");
			Soluzione.setText("GENERA SOLUZIONE");
			Lista.setText("LISTA");
			BtnId.setDisable(false);
			ResetFiltri.setText("RESET FILTRI");
			return;
		}
		initialize();
		Condizione.setValue("");
		AnnoMax.clear();
		AnnoMin.clear();
		Budget.clear();
		Id.clear();
		LunghezzaMax.clear();
		LunghezzaMin.clear();
		Tipologia.setValue("");
		Soluzione.setText("GENERA SOLUZIONE");
		Lista.setText("LISTA");
		BtnId.setDisable(false);
	}

	@FXML
	void ActionSoluzione(ActionEvent event) {
		initialize();
		switch(Soluzione.getText()) {
			case "GENERA SOLUZIONE":
				best= new ArrayList<Boat>();
				best2= new ArrayList<Boat>();
				if(setFiltri()){		
					if(insertId!=0) {
						budg-=insertBoat.getPrezzo();
						lBoatSel.remove(insertBoat);
						best.add(insertBoat);
						best2.add(insertBoat);
					}
					best.addAll(this.model.barche(this.lBoatSel, budg));  // RICERCA SOLUZIONE
					insertId=0;
					insertBoat=null;			
					if (best.size() != 0) {
						TxtResult.appendText("SOLUZIONE\n\nLa soluzione ottimale è:\n ");
						for (Boat b : best) {
							TxtResult.appendText(b.toString() + "\n");
						}
						TxtResult.appendText("\nBudget speso: "+model.calcola_prezzo(best)+"\nGuadagno totale atteso: "+model.calcola_guadagno(best));
					} else {
						TxtResult.appendText("SOLUZIONE\n\nNon ci sono barche che rispettano i criteri per una soluzione ottima.\n"
								+ "\nModificare i parametri inseriti o effettuare una nuova ricerca ");
						Soluzione.setDisable(true);
					}	
					Soluzione.setText("CONFERMA");
					Lista.setText("ANNULLA");
					if (!model.secondasol().isEmpty()) {   // NEL CASO IN CUI CI SIA UNA SECONDA SOLUZIONE 
						best2.addAll(model.secondasol());
						TxtResult.appendText("\n\nIn alternativa la soluzione ottima potrebbe essere:\n ");
						for (Boat b : best2) {
							TxtResult.appendText(b.toString() + "\n");
						}
						int diff = model.calcola_prezzo(model.secondasol()) - budg;
						TxtResult.appendText("\n\nSpendendo" + diff + " in più rispetto al budget.");
						TxtResult.appendText("\n\nGuadagno totale atteso dalla 2° soluzione: " + this.model.calcola_guadagno(model.secondasol()));
						Lista.setText("CONFERMA 2° SOL");
						ResetFiltri.setText("ANNULLA");
					}
				}
				break;			
			case "CONFERMA":
				TxtResult.appendText("Acquisto confermato con successo!\n\nLista barche acquistate fin'ora:\n");
				Allbest.addAll(best);
				for (Boat b : Allbest) {
					TxtResult.appendText(b.toString()+"\n");
				}
				TxtResult.appendText("\nBudget speso: "+model.calcola_prezzo(Allbest)+"\nGuadagno totale atteso: "+model.calcola_guadagno(Allbest));
				TxtResult.appendText("\n\nProcedere con una nuova ricerca con il budget rimanente, modificare i filtri/budget o ricominciare\n");
				budg-=model.calcola_prezzo(best);
				Budget.setText(Integer.toString(budg));
				Soluzione.setText("NUOVA RICERCA");
				Lista.setText("RESET");
				ResetFiltri.setText("RESET FILTRI");
				break;
			default:
				Soluzione.setText("GENERA SOLUZIONE");
				Lista.setText("LISTA");
				ResetFiltri.setText("RESET FILTRI");
				BtnId.setDisable(false);
				ActionLista(event);
		}
	}

	private boolean setFiltri() {
		try {
			double Lmin;
			double Lmax;
			String tipo;
			int Amin;
			int Amax;
			List<String> condi= new ArrayList<String>(); 
			if(Condizione.getValue()==null  || Condizione.getValue()=="") {
				TxtResult.appendText("ATTENZIONE!\nSelezionare una CONDIZIONE dalla ChoiceBox o in altenarnativa selezionare 'Qualsiasi'.\n");
				return false;
			}else {
				if (Condizione.getValue().equals("Qualsiasi")) { //set condizione
					condi.add("New boat");
					condi.add("Used boat");
				} else {
					condi.add(Condizione.getValue());
				}
			}		
			if (LunghezzaMin.getText().isEmpty()) { //set Lunghezza min
				Lmin = dao.getLmin();
			} else {
				Lmin = Double.parseDouble(LunghezzaMin.getText());
			}
			if (LunghezzaMax.getText().isEmpty()) { //set Lunghezza max
				Lmax = dao.getLmax();
			} else {
				Lmax = Double.parseDouble(LunghezzaMax.getText());
			}
			if(Tipologia.getValue()==null || Tipologia.getValue()=="" ) {
				TxtResult.appendText("ATTENZIONE!\nSelezionare una TIPOLOGIA dalla ChoiceBox o in altenarnativa selezionare 'Qualsiasi'.\n");
				return false;
			}else {
				tipo = Tipologia.getValue();
			}
			if (AnnoMin.getText().isEmpty()) { //set Anno min
				Amin = dao.getAmin();
			} else {
				Amin = Integer.parseInt(AnnoMin.getText());
			}
			if (AnnoMax.getText().isEmpty()) { //set Anno max
				Amax = dao.getAmax();
			} else {
				Amax = Integer.parseInt(AnnoMax.getText());
			}
//setto Budget per la ricerca in caso stampo errore   						
			if (Budget.getText().isEmpty() || Integer.parseInt(Budget.getText()) == 0) {
				TxtResult.appendText("ATTENZIONE!\nInserire un BUDGET maggiore.\n");
				return false;
			} else {
				budg = Integer.parseInt(Budget.getText());
			}
//setto lista per la ricerca
			lBoatSel = new ArrayList<Boat>(dao.listBoatSelected(budg, condi, Lmin, Lmax, tipo, Amin, Amax));
			lBoatSel.removeAll(Allbest);
			return true;
		} catch (NumberFormatException e) {
			TxtResult.appendText("Inserire un valore corretto \n");
			return false;
		}
	}

	@FXML
	void initialize() {
		assert AnnoMax != null : "fx:id=\"AnnoMax\" was not injected: check your FXML file 'Scene.fxml'.";
		assert AnnoMin != null : "fx:id=\"AnnoMin\" was not injected: check your FXML file 'Scene.fxml'.";
		assert BtnId != null : "fx:id=\"BtnId\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Budget != null : "fx:id=\"Budget\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Condizione != null : "fx:id=\"Condizione\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Id != null : "fx:id=\"Id\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Lista != null : "fx:id=\"Lista\" was not injected: check your FXML file 'Scene.fxml'.";
		assert LunghezzaMax != null : "fx:id=\"LunghezzaMax\" was not injected: check your FXML file 'Scene.fxml'.";
		assert LunghezzaMin != null : "fx:id=\"LunghezzaMin\" was not injected: check your FXML file 'Scene.fxml'.";
		assert ResetFiltri != null : "fx:id=\"ResetFiltri\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Soluzione != null : "fx:id=\"Soluzione\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Tipologia != null : "fx:id=\"Tipologia\" was not injected: check your FXML file 'Scene.fxml'.";
		assert TxtResult != null : "fx:id=\"TxtResult\" was not injected: check your FXML file 'Scene.fxml'.";
		Soluzione.setDisable(false);
		TxtResult.clear();
	}

	private void setComboItems() {
		List<String> l = new ArrayList<String>();
		l.add("Qualsiasi");
		l.add("New boat");
		l.add("Used boat");
		Condizione.getItems().setAll(l);
		List<String> l2 = new ArrayList<String>();
		l2.add("Qualsiasi");
		l2.addAll(dao.listAllTipologia());
		Tipologia.getItems().setAll(l2);
	}

	public void setModel(Model model) {
		this.model = model;
		setComboItems();
	}

}

