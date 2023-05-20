import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class CityButton {
	String name;
	Image img;
	ArrayList<Integer> rowLinePoints = new ArrayList<>();
	ArrayList<Integer> colLinePoints = new ArrayList<>();
	ArrayList<String> startCity = new ArrayList<>();
	ArrayList<String> endCity = new ArrayList<>();
	ArrayList<Integer> thisCity = new ArrayList<>();
	static ArrayList<Integer> points = new ArrayList<Integer>();
	private static CityButton nextCity;
	int colIndex = 0, rowIndex = 0;
	int endColIndex = 0, endRowIndex = 0;
	int sRI, bRI, sCI, bCI;
	static ArrayList<Double> x = new ArrayList<>();
	static ArrayList<Double> y = new ArrayList<>();
	static CenterPane pane = new CenterPane();
	// Calculate score icin
	CityButton loc = this;
	int cityId, capacity, index = 0, indexOfCity;
	NewLevel lvl;

	CityButton() {

	}

	CityButton(String name, Image img, int colIndex, int rowIndex, NewLevel lvl, int index) {
		this.name = name;
		this.img = img;
		this.colIndex = colIndex;
		this.rowIndex = rowIndex;
		this.lvl = lvl;
		this.index = index;
		for (int i = 0; i < lvl.cities.size(); i++) {
			if (lvl.cities.get(i).getId() == lvl.vehicles.get(0).getCityId())
				indexOfCity = i;
		}
		for (int i = 0; i < lvl.passengers.size(); i++) {
			for (int j = 0; j < lvl.cities.size(); j++) {
				if (lvl.cities.get(j).getId() == lvl.passengers.get(i).getStartId())
					startCity.add(lvl.cities.get(j).getName());
				if (lvl.cities.get(j).getId() == lvl.passengers.get(i).getDestId())
					endCity.add(lvl.cities.get(j).getName());
			}
		}
	}

	public BorderPane cityButton(CenterPane pane, Text botText) {
		Button cB = new Button();
		CityButton.pane = pane;
		cB.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		cB.setOnMouseClicked(event -> {
			x.add(event.getX());
			y.add(event.getY());
			if (x.size() == 2)
			pane.createLine();
			
			points.add(colIndex - 1);
			points.add(rowIndex - 1);
			nextCity = this;
			loc = this;
			
			botText.setText(getCityInformation(points.get(0), points.get(1), points.get(2), points.get(3)) + "\n" + getPasssengerInformation());
			
		});

		Label label = new Label("  " + this.name);
		label.setAlignment(Pos.BOTTOM_CENTER);

		label.toFront();

		BorderPane bP = new BorderPane();

		ImageView iv = new ImageView(this.img);
		iv.setFitHeight(35);
		iv.setFitWidth(35);

		cB.setGraphic(iv);
		VBox vB = new VBox(1);
		vB.getChildren().addAll(cB, label);
		bP.setCenter(vB);

		return bP;

	}

	public int calculateDistance(int x1, int y1, int x2, int y2) {
		return (int) (Math.ceil(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow(y1 - y2, 2))));

	}

	public static CityButton getNextCity() {
		return nextCity;
	}

	public IntegerProperty calculateScore() {
		int cost = calculateDistance(points.get(0), points.get(1), points.get(2), points.get(3));
		System.out.println("cost " + cost);
		int income = (int) ((pane.level.vehicle.getCapacity()) * (cost) * (0.2));
		System.out.println("income " + income);
		int moveScore = income - cost;
		IntegerProperty returnValue = new SimpleIntegerProperty(moveScore);
		System.out.println(moveScore);
		points.clear();
		return returnValue;

	}

	public String getCityInformation(int x1, int y1, int x2, int y2) {
		return String.format("%s (City ID = %d, Distance = %d, Vehicle Capacity = %d)", lvl.cities.get(index).getName(),
				lvl.cities.get(index).getId(), calculateDistance(BottomPane.nextPointX, BottomPane.nextPointY, x2, y2), lvl.vehicles.get(0).getCapacity());
	}

	public String getPasssengerInformation() {
		String s1="";
		for(int i = 0 ; i < lvl.cities.size() ; i++) {
		   if(this.name.equals(startCity.get(i)) || this.name.equals(endCity.get(i))) {
			   s1 += String.format("%s > %s (%d passengers)\n", startCity.get(i), endCity.get(i), lvl.passengers.get(i).getNumOfPas());   
		   }
		}
		
		
		String s = "";
		for (int i = 0; i < startCity.size(); i++) {
			s += String.format("%s > %s (%d passengers)\n", startCity.get(i), endCity.get(i), lvl.passengers.get(i).getNumOfPas());
			
			
		}
		return s1;
	}
	
	
		public void transportPassengers() {
		    int capacity = lvl.vehicles.get(0).getCapacity();
		    for (int i = 0; i < lvl.passengers.size(); i++) {
		        if (startCity.get(i).equals(this.name)) {
		            int passengersToTransport = Math.min(capacity, lvl.passengers.get(i).getNumOfPas());
		            lvl.passengers.get(i).setNumOfPas(lvl.passengers.get(i).getNumOfPas() - passengersToTransport);
		            
		            System.out.println("yolcu taşındı");
		            // Yolcuyu hedef şehre taşı
		        }
		    }
		
	}
}