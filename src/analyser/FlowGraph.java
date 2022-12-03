package analyser;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import Exception.IllegalHexException;
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
	public void start(javafx.stage.Stage stage) throws IOException{
		String filePath = "data/test6.txt";
		
		//Liste des décodages complets des trames

		List<String> listeDecodages =Analyser.execute(filePath);
//			System.out.println("TAILLE PREMIERE LISTE"+listeDecodages.size());
		
		//Liste du traffic entre les deux machines
		List<String> listeTrames= Visualiser.execute(filePath);
//		System.out.println("TAILLE DEUXIEME LISTE"+listeTrames.size());

		//Création de la fenetre contenant la liste des trames
		VBox box = new VBox();

		//titre de l'interfaces
		stage.setTitle("FlowGraph");
		
//		for (String trame : listeTrames) {
//			Label l = new Label(trame);
//			box.getChildren().add(l);
//			
//			
//			//Affichage pop up du décodage complet de la trame, sur clic
//			Label l2 = new Label("ANALYSER INFO");
//			l.setOnMouseClicked(e -> {
//				VBox box2 = new VBox();
//				javafx.stage.Stage stage2 = new Stage();
//				box2.getChildren().add(l2);
//				stage2.setScene(new javafx.scene.Scene(box2));
//				stage2.show();
//			});
//			
//			//Surbrillance lorsqu'on mouse over
//			l.setOnMouseEntered(e -> {
//				l.setTextFill(Color.BLUE);
//			});
//			l.setOnMouseExited(e -> {
//				l.setTextFill(Color.BLACK);
//			});
//			
//			
//		}
		
		Label lIP = new Label(listeTrames.get(0));
		box.getChildren().add(lIP);
		
		
		for (int i = 0; i < listeTrames.size()-1; i++) {
			Label l = new Label(listeTrames.get(i+1));
			box.getChildren().add(l);
			
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
		
		
		
		
		
		
		
		stage.setScene(new javafx.scene.Scene(box));
		stage.show();
	}
	public static void main(String[] args) { 
		launch(args); 

		}
}
