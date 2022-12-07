package analyser;
import java.io.IOException;


import java.util.ArrayList;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class FlowGraph extends javafx.application.Application {
	@Override
	public void start(javafx.stage.Stage stage) throws IOException{
		String filePath = Tools.getFile();
		
		//Liste des décodages complets des trames
		List<String> listeDecodages = Analyser.execute(filePath);
		
		//Liste du traffic entre les deux machines
		List<String> listeTrames= Visualiser.execute(filePath);
		
		String text = listeTrames+"\n"+listeDecodages;
		Tools.ecrire(text);

		//Création de la fenetre contenant la liste des trames
		VBox box = new VBox();

		ListView<Label> listView = new ListView<>();
		listView.setMinSize(600, 500);

		//Label pour afficher les IP des deux machines
		Label lIP = new Label(listeTrames.get(0));
		box.getChildren().add(lIP);
		
		for (int i = 0; i < listeTrames.size()-1; i++) {
			Label l = new Label(listeTrames.get(i+1));
			l.setMinSize(575, 65);
			listView.getItems().add(l);
//			box.getChildren().add(l);
			
			//Affichage pop up du décodage complet de la trame, sur clic
			Label l2 = new Label(listeDecodages.get(i));
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
		box.getChildren().add(listView);
		
		//titre de l'interface
		stage.setTitle("FlowGraph");
		
		Scene scene = new Scene(box,600,500);
		//On ajoute notre box au stage
		stage.setScene(scene);
		//On affiche tout
		stage.show();
	}
	public static void main(String[] args) {
		Tools.setFile(args[0]);
		launch(args); 
		}
}
