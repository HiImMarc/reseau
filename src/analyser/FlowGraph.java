package analyser;
import java.util.ArrayList;

import java.util.List;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class FlowGraph extends javafx.application.Application {
	@Override
	public void start(javafx.stage.Stage stage) throws Exception {
		
		//Liste du traffic entre les deux machines
		List<String> listeTrames=  new ArrayList<>();
		listeTrames.add("	     65222 -> 65222 [ACK] Seq=2686070173 Ack=1004650073 Win=00d2\n" + 
				"			65222| <---------------------------------------------------------------- | 80");
		listeTrames.add("  65222 -> 80 [ACK] [PSH] Seq=1004650073 Ack=2686070173 Win=01f6\n" + 
				"			65222 | ----------------------------------------------------------------> | 80");	
		
		//Liste des décodages complets des trames
		List<String> listeDecodages = new ArrayList<>();
		
		
		//Création de la fenetre contenant la liste des trames
		VBox box = new VBox();
		
		//titre de l'interfaces
		stage.setTitle("FlowGraph");
		
		for (String trame : listeTrames) {
			Label l = new Label(trame);
			box.getChildren().add(l);
			
			
			//Affichage pop up du décodage complet de la trame, sur clic
			Label l2 = new Label("ANALYSER INFO");
			l.setOnMouseClicked(e -> {
				VBox box2 = new VBox();
				javafx.stage.Stage stage2 = new Stage();
				box2.getChildren().add(l2);
				stage2.setScene(new javafx.scene.Scene(box2));
				stage2.show();
			});
			
			//Surbrillance lorsqu'on mouse over
			l.setOnMouseEntered(e -> {
				l.setTextFill(Color.BLUE);
			});
			l.setOnMouseExited(e -> {
				l.setTextFill(Color.BLACK);
			});
			
			
		}
		
		
		stage.setScene(new javafx.scene.Scene(box));
		stage.show();
	}
	public static void main(String[] args) { launch(args); }
}
