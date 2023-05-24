import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class NewCenterPane extends Pane {
	// Keeps the nodes on this pane
	Node[][] nodes = new Node[10][10];
	// Keeps the current NewLevel object
	NewLevel lvl;
	// Width every cell
	double cellWidth;
	// Height of every cell
	double cellHeight;
	// Keeps a NewCityButton object
	NewCityButton city;

	public NewCenterPane(NewLevel lvl, Text text) {
		this.lvl = lvl;
		// These are our images that used on this pane
		Image img = new Image("ist.png");
		Image img1 = new Image("mersin.png");
		Image img2 = new Image("city1.png");
		Image img3 = new Image("city2.png");
		Image img4 = new Image("city3.png");
		Image img5 = new Image("city4.png");
		Image img6 = new Image("fixedcellsign.png");
		// Due to our cities's image are selected randomly we put them in an array
		Image[] imgarr = { img, img1, img2, img3, img4, img5 };

		int i = 0, a = 0, b = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (a == lvl.cities.size())
					break;
				if (!(lvl.fixedCells.size() == b)) {
					ImageView iv = new ImageView(img6);
					iv.setFitWidth(35);
					iv.setFitHeight(35);
					nodes[(lvl.fixedCells.get(b) % 10) - 1][(lvl.fixedCells.get(b)) / 10] = iv;
					b++;
				}
				if (lvl.cities.get(a).getLocId() == i) {
					NewCityButton button = new NewCityButton(lvl.cities.get(a).getName(),
							imgarr[(int) (Math.random() * 6)],
							(lvl.cities.get(a).getLocId() % 10 == 0
									? (((lvl.cities.get(a).getLocId() % 10) + 8) - 1) * cellWidth
									: (((lvl.cities.get(a).getLocId() % 10)) - 1) * cellWidth),
							(lvl.cities.get(a).getLocId() % 10 == 0)
									? ((lvl.cities.get(a).getLocId() / 10) - 1) * cellHeight
									: (((lvl.cities.get(a).getLocId()) / 10 + 1) - 1) * cellHeight,
							lvl, a);

					city = button;

					if (lvl.cities.get(a).getLocId() % 10 == 0) {
						nodes[(lvl.cities.get(a).getLocId() % 10) + 9][(lvl.cities.get(a).getLocId() / 10) - 1] = button
								.createButton(text);

					} else {
						nodes[(lvl.cities.get(a).getLocId() % 10) - 1][(lvl.cities.get(a).getLocId() / 10)] = button
								.createButton(text);
					}
					row = 0;
					col = 0;
					a++;
					i = 0;

				} else
					i++;
			}

		}
		// Sets this pane's size 500 width 500 height
		this.setPrefSize(500, 500);
		// Pane includes 100 cells so we divide width and height by 10
		cellWidth = this.getPrefWidth() / 10;
		cellHeight = this.getPrefHeight() / 10;

		// This nested loop looks every point of nodes array
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				// If this point includes an object
				if (nodes[row][col] != null) {
					// To arrange its positions and add to the pane
					Node willAdded = nodes[row][col];
					// To distinguish cities from others (our cities are represented on buttons)
					if (willAdded instanceof Button) {
						// To find exact location on the pane it is multiplied by cellWidth and height
						// We subtract 5 from their locations to gain better appearance
						willAdded.setLayoutX(cellWidth * row - 5);
						willAdded.setLayoutY(cellHeight * col - 5);
					} else {
						// To find exact location on the pane it is multiplied by cellWidth and height
						// We add 5 from their locations to gain better appearance
						willAdded.setLayoutX(cellWidth * row + 5);
						willAdded.setLayoutY(cellHeight * col + 5);
					} // Finally, it is added on the pane
					this.getChildren().add(willAdded);

				}
			}
		} // Vehicle created on the pane
		createVehicle(lvl);

	}

	public Polyline createLine(double startX, double startY, double endX, double endY) {
		Polyline polyline = new Polyline();
		int fixed = isThere(startX, startY, endX, endY);
		if (fixed != 0) {
			// To determine coordinates of fixed cell
			double xOfFixed = (fixed % 10 - 1) * cellWidth;
			double yOfFixed = (fixed / 10 + 1) * cellHeight - 5;
			if (startX > endX && startY < endY) {
				System.out.println(31);
				if (xOfFixed == endX) {
					System.out.println(32);
					polyline.getPoints().addAll(startX, startY, endX, startY, endX, yOfFixed - 50, endX + 50,
							yOfFixed - 50);
					fixed = isThere(endX + 50, yOfFixed - 50, endX, endY);
					if (fixed == 0) {
						polyline.getPoints().addAll(endX - 50, endY, endX, endY);
					} else {
						// ...
					}
				} else if (yOfFixed == startY) {
					System.out.println(33);
					polyline.getPoints().addAll(startX, startY, xOfFixed - 50, startY);
					fixed = isThere(xOfFixed - 50, startY, endX, endY);
					if (fixed == 0) {
						polyline.getPoints().addAll(xOfFixed - 50, endY, endX, endY);
					}
					// Bir fixed daha var mi diye bakiyor
					else {
						// Burasina devam edilecek
						xOfFixed = (fixed % 10 - 1) * cellWidth;
						yOfFixed = (fixed / 10) * cellHeight;
					}
				} else if (yOfFixed == endY) {
					System.out.println(34);
				} else if (xOfFixed == startX) {
					System.out.println(35);
				} else {
					polyline.getPoints().addAll(startX, startY, endX, startY, endX, endY);
				}
			} else if (startX < endX && startY > endY) {
				System.out.println(yOfFixed);
				if (startY == yOfFixed) {
					System.out.println(40);
					polyline.getPoints().addAll(startX, startY, xOfFixed - 50, startY, xOfFixed - 50, startY - 50);
					fixed = isThere(xOfFixed - 50, startY - 50, endX, endY);
					if (fixed == 0) {
						polyline.getPoints().addAll(endX, startY - 50, endX, endY);

					} else {

					}
				} else {
					polyline.getPoints().addAll(startX, startY, endX, startY, endX, endY);
				}
			} else {
				polyline.getPoints().addAll(startX, startY, endX, startY, endX, endY);
			}
		} else {
			polyline.getPoints().addAll(startX, startY, endX, startY, endX, endY);

		}
		polyline.setStroke(Color.GREEN);
		polyline.setStrokeWidth(7);
		return polyline;

	}

	public void createVehicle(NewLevel lvl) {
		int location = 0;
		for (int i = 0; i < lvl.cities.size(); i++) {
			if (lvl.vehicle.getCityId() == lvl.cities.get(i).getId())
				location = lvl.cities.get(i).getLocId();
		}
		int vx = location % 10 - 1;
		int vy = location / 10;
		lvl.vehicle.imageView.setFitWidth(35);
		lvl.vehicle.imageView.setFitHeight(35);
		lvl.vehicle.xCordinate = vx * cellWidth;
		lvl.vehicle.yCordinate = vy * cellHeight;
		lvl.vehicle.imageView.setLayoutX(lvl.vehicle.xCordinate);
		lvl.vehicle.imageView.setLayoutY(lvl.vehicle.yCordinate);
		this.getChildren().add(lvl.vehicle.imageView);

	}

	public void animation(Polyline polyline) {
		PathTransition transition = new PathTransition();
		transition.setDuration(Duration.millis(2000));
		transition.setPath(polyline);
		transition.setNode(lvl.vehicle.imageView);
		lvl.vehicle.imageView.toFront();
		transition.setCycleCount(1);
		transition.play();
	}

	public int isThere(double startX, double startY, double endX, double endY) {
		int fixed = 0;
		ArrayList<Double> x = new ArrayList<>();
		ArrayList<Double> y = new ArrayList<>();
		for (int i = 0; i < lvl.fixedCells.size(); i++) {
			x.add((lvl.fixedCells.get(i) % 10 - 1) * cellWidth);
			y.add((lvl.fixedCells.get(i) / 10) * cellHeight);
		}
		for (int i = 0; i < lvl.fixedCells.size(); i++) {
			if ((x.get(i) <= startX && x.get(i) >= endX) || (x.get(i) >= startX && x.get(i) <= endX)) {
				if ((y.get(i) <= startY && y.get(i) >= endY) || (y.get(i) >= startY && x.get(i) <= endY)) {
					fixed = lvl.fixedCells.get(i);
				}
			}
		}
		return fixed;
	}

}
