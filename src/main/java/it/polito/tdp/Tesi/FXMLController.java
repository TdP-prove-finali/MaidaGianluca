
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
	List<String> condi;
	double Lmin;
	double Lmax;
	String tipo;
	int Amin;
	int Amax;
	int budg;
	int id=0;
	Boat bid;
	List<Boat> lBoatSel = new ArrayList<Boat>();
	List<Boat> best= new ArrayList<Boat>();
	List<Boat> Allbest= new ArrayList<Boat>();


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
			id = Integer.parseInt(Id.getText());
			for(Boat b: this.lBoatSel) {
				if(b.getId()==id) {
					TxtResult.appendText("\nBarca inserita correttamente nella lista delle soluzioni\n");
					bid=b;
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
		TxtResult.clear();
		if(Lista.getText().equals("LISTA")){
			if(setFiltri()) {
				if (budg != 0) {
					if (lBoatSel.size() != 0) {
						TxtResult.appendText("LISTA\n\nLe barche che rispettano i criteri sono: \n\n");
						for (Boat b : lBoatSel) {
							TxtResult.appendText(b.toString() + "\n");
						}
					} else {
						TxtResult.appendText("LISTA\n\nNon ci sono barche che rispettano i criteri.\n\nModificare i parametri inseriti o effettuare una nuova ricerca ");
						Soluzione.setDisable(true);

					}
				}
			}
		}else if(Lista.getText().equals("ANNULLARE")){
			TxtResult.appendText("Acquisto annullato\nProcedere con una nuova soluzione");
			best.clear();
			Soluzione.setText("GENERA SOLUZIONE");
			Lista.setText("LISTA");
		}else {
			ActionResetFiltri(event);
			initialize();
			Allbest= new ArrayList<Boat>();
			Soluzione.setText("GENERA SOLUZIONE");
			Lista.setText("LISTA");
		}
			
	}

	@FXML  
	void ActionResetFiltri(ActionEvent event) {
		TxtResult.clear();
		Condizione.setValue("");
		AnnoMax.clear();
		AnnoMin.clear();
		Budget.clear();
		Id.clear();
		LunghezzaMax.clear();
		LunghezzaMin.clear();
		Tipologia.setValue("");
	}

	@FXML
	void ActionSoluzione(ActionEvent event) {
		TxtResult.clear();
		initialize();
		if(Soluzione.getText().equals("GENERA SOLUZIONE")){
			if(setFiltri()){
				best=new ArrayList<Boat>();
				if(id!=0) {
					budg-=bid.getPrezzo();
					lBoatSel.remove(bid);
					best.add(bid);
				}
				best.addAll(this.model.barche(this.lBoatSel, budg));
				//id=0;
				bid=null;			
				if (best.size() != 0) {
					TxtResult.appendText("SOLUZIONE\n\nLa soluzione ottimale è:\n ");
					for (Boat b : best) {
						TxtResult.appendText(b.toString() + "\n");
					}
					TxtResult.appendText("\nBudget speso: "+model.calcola_prezzo(best)+"\nGuadagno atteso: "+model.calcola_guadagno(best));
				} else {
					TxtResult.appendText("SOLUZIONE\n\nNon ci sono barche che rispettano i criteri per una soluzione ottima.\n\nModificare i parametri inseriti o effettuare una nuova ricerca ");
					Soluzione.setDisable(true);
				}				
				if (!this.model.secondasol().isEmpty()) {
					TxtResult.appendText("\n\nIn alternativa la soluzione ottimale potrebbe essere:\n ");
					for (Boat b : model.secondasol()) {
						TxtResult.appendText(b.toString() + "\n");
					}
					int diff = model.calcola_prezzo(model.secondasol()) - budg;
					TxtResult.appendText("\n\nSpendendo" + diff + " in più.");
					TxtResult.appendText("\n\nGuadagno atteso: " + this.model.calcola_guadagno(model.secondasol()));
				}
				Soluzione.setText("CONFERMARE");
				Lista.setText("ANNULLARE");
			}
		}else if(Soluzione.getText().equals("CONFERMARE")){
			TxtResult.appendText("Acquisto confermato con successo!\n\nLista barche acquistate fin'ora:\n");
			//stampare tutti gli acquisti E GUADAGNO ATTETSO;
			Allbest.addAll(best);
			for (Boat b : Allbest) {
				TxtResult.appendText(b.toString()+"\n");
			}
			TxtResult.appendText("\n\nProcedere con una nuova ricerca con il budget rimanente, modificare i filtri/budget o ricominciare\n");
			budg-=model.calcola_prezzo(best);
			Budget.setText(Integer.toString(budg));
			Soluzione.setText("NUOVA RICERCA");
			Lista.setText("RICOMINCIARE");
		}else {
			Soluzione.setText("GENERA SOLUZIONE");
			Lista.setText("LISTA");
			ActionLista(event);
		}
	}

	private boolean setFiltri() {
		try {
			condi= new ArrayList<String>(); 
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





