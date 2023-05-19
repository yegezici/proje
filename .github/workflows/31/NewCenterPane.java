import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class NewCenterPane extends Pane {
	CenterPane center;
	Node[][] nodes = new Node[10][10];
	//Sehirler resimken yapildi sayisini tutmak icin cok gereksiz gibi duruyor duzeltilebilir
	ArrayList<ImageView> cities = new ArrayList<>();
	DoubleProperty xlength;
	DoubleProperty ylength;
	double cellWidth;
	double cellHeight;

	NewCenterPane(NewLevel lvl) {
		Image img = new Image("ist.png");
		Image img1 = new Image("mersin.png");
		Image img2 = new Image("city1.png");
		Image img3 = new Image("city2.png");
		Image img4 = new Image("city3.png");
		Image img5 = new Image("city4.png");
		Image img6 = new Image("fixedcellsign.jpg");
		Image[] imgarr = { img, img1, img2, img3, img4, img5 };
		
		this.setPrefSize(400, 400);
		cellWidth = this.getPrefWidth() / 10;
		cellHeight = this.getPrefHeight() / 10;
		
		int rectangleCount = 10;
		int i = 0, a = 0, b = 0, index = 0;
		for (int row = 0; row < rectangleCount; row++) {
			for (int col = 0; col < rectangleCount; col++) {
				if (a == lvl.cities.size())
					break;
				if (!(lvl.fixedCells.size() == b)) {
					ImageView iv = new ImageView(img6);
					iv.setFitWidth(35);
					iv.setFitHeight(35);
					nodes[(lvl.fixedCells.get(b) % 10) - 1][(lvl.fixedCells.get(b)) / 10] = iv;
					b++;
				}
				if (lvl.cities.get(a).getLocId() == i) { // koordinatların tam olması için 1 den çıkartmıyoruz
					CityButton cB = new CityButton(lvl.cities.get(a).getName(), imgarr[(int) (Math.random() * 6)],
							(lvl.cities.get(a).getLocId() % 10 == 0 ? (lvl.cities.get(a).getLocId() % 10) + 8
									: (lvl.cities.get(a).getLocId() % 10)),
							(lvl.cities.get(a).getLocId() % 10 == 0) ? (lvl.cities.get(a).getLocId() / 10)
									: (lvl.cities.get(a).getLocId()) / 10 + 1,
							lvl, a);
					NewCityButton button = new NewCityButton(lvl.cities.get(a).getName(), imgarr[(int) (Math.random() * 6)],
							(lvl.cities.get(a).getLocId() % 10 == 0 ? ((lvl.cities.get(a).getLocId() % 10) + 8) * cellWidth
									: ((lvl.cities.get(a).getLocId() % 10))) * cellWidth,
							(lvl.cities.get(a).getLocId() % 10 == 0) ? (lvl.cities.get(a).getLocId() / 10) * cellHeight
											: ((lvl.cities.get(a).getLocId()) / 10 + 1) * cellHeight);
					
					// bazilarini buranin ustunde silmis olabilirim
					// forNextCity = cB;
					// buttons[index++] =cB;
					if (lvl.cities.get(a).getLocId() % 10 == 0) {
						nodes[(lvl.cities.get(a).getLocId() % 10) + 9][(lvl.cities.get(a).getLocId() / 10)
								- 1] = button.createButton();
						cities.add(cB.ivOfimg);
					} else {
						nodes[(lvl.cities.get(a).getLocId() % 10) - 1][(lvl.cities.get(a).getLocId()
								/ 10)] = button.createButton();
						cities.add(cB.ivOfimg);
					}
					row = 0;
					col = 0;
					a++;
					i = 0;

				} else
					i++;
			}

		}
		this.setPrefSize(400, 400);
		cellWidth = this.getPrefWidth() / 10;
		cellHeight = this.getPrefHeight() / 10;

		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (nodes[row][col] != null) {
					Node willAdded = nodes[row][col];
					willAdded.setLayoutX(cellWidth * row);
					willAdded.setLayoutY(cellHeight * col);
					this.getChildren().add(willAdded);
				} else {
					Rectangle square = new Rectangle();
					square.setWidth(cellWidth);
					square.setHeight(cellHeight);
					square.setFill(Color.TRANSPARENT);
					square.setStroke(Color.BLACK);
					square.setLayoutX(cellWidth * row);
					square.setLayoutY(cellHeight * col);
					this.getChildren().add(square);

				}
			}
		}
		createVehicle(lvl);
		for (int f = 0; f < cities.size(); f++) {
			ImageView cityImageView = cities.get(f);
			cityImageView.setOnMouseClicked(event -> {
				if (event.getSource() == cityImageView) {
					double x = event.getX();
					double y = event.getY();
					System.out.println("x " + x + "-" + "y" + y);
					Vehicle vehicle = lvl.vehicles.get(0);
					
					createLine(vehicle.xCordinate.get(), vehicle.yCordinate.get(),1*cellWidth+ x, 6*cellHeight+y);
				}
			});
		}
	}

	public void createLine(double startX, double startY, double endX, double endY) {
		Polyline polyline = new Polyline();
		polyline.getPoints().addAll(startX, startY, endX, startY, endX, endY);
		polyline.setStroke(Color.GREEN);
		polyline.setStrokeWidth(7);
		this.getChildren().add(polyline);

	}

	public void createVehicle(NewLevel lvl) {
		Vehicle vehicle = lvl.vehicles.get(0);
		ImageView iv = vehicle.imageView;
		int location = 0;
		for (int i = 0; i < lvl.cities.size(); i++) {
			if (vehicle.getCityId() == lvl.cities.get(i).getId())
				location = lvl.cities.get(i).getLocId();
		}
		int vx = location % 10 - 1;
		int vy = location / 10;
		iv.setFitWidth(25);
		iv.setFitHeight(25);
		vehicle.xCordinate.bind(this.widthProperty().divide(10).multiply(vx));
		vehicle.yCordinate.bind(this.heightProperty().divide(10).multiply(vy));
		iv.layoutXProperty().bind(this.widthProperty().divide(10).multiply(vx));
		iv.layoutYProperty().bind(this.heightProperty().divide(10).multiply(vy));
		this.getChildren().add(iv);
	}
}