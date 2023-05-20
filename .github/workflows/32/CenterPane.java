import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CenterPane extends StackPane {
	int rectangleCount = 10;
    CityButton cB = new CityButton();
	CityButton[] buttons;
	Rectangle square = new Rectangle();
	NewLevel level = new NewLevel();
	private int vx;
	private int vy;
	private CityButton forNextCity;
	ImageView imageView = new ImageView();
	GridPane pane = new GridPane();
	public CenterPane() {
		
	}
	
	public CenterPane(NewLevel lvl , Text text) {
		level = lvl;
		int id = 1;
		buttons = new CityButton[lvl.cities.size()];	

		for (int row = 0; row < rectangleCount; row++) {
			for (int col = 0; col < rectangleCount; col++) {
				Rectangle square = new Rectangle();
				square.widthProperty().bind(widthProperty().divide(10).subtract(10));
				square.heightProperty().bind(heightProperty().divide(10).subtract(10));
				;
				// RENKSIZ OLMASI ICIN YAZDIM
				square.setFill(Color.TRANSPARENT);
				//square.setStroke(Color.BLACK);
				square.setId(String.valueOf(id));

				pane.add(square, col, row);

				id++;
			}
		}
		
		Image img = new Image("ist.png");
		Image img1 = new Image("mersin.png");
		Image img2 = new Image("city1.png");
		Image img3 = new Image("city2.png");
		Image img4 = new Image("city3.png");
		Image img5 = new Image("city4.png");
		Image img6 = new Image("fixedcellsign.jpg");
		Image[] imgarr = { img, img1, img2, img3, img4, img5 };
		int rectangleCount = 10;
		int i = 0, a = 0, b = 0,index = 0;
		for (int row = 0; row < rectangleCount; row++) {
			for (int col = 0; col < rectangleCount; col++) {

				if (a == lvl.cities.size())
					break;
				if (!(lvl.fixedCells.size() == b)) {
					ImageView iv = new ImageView(img6);
					iv.setFitWidth(35);
					iv.setFitHeight(35);
					pane.add(iv, (lvl.fixedCells.get(b) % 10) - 1, (lvl.fixedCells.get(b)) / 10);
					b++;
				}
				if (lvl.cities.get(a).getLocId() == i) { // koordinatların tam olması için 1 den çıkartmıyoruz
				    CityButton cB = new CityButton(lvl.cities.get(a).getName(), imgarr[(int) (Math.random() * 6)],
			        		(lvl.cities.get(a).getLocId()%10==0 ? (lvl.cities.get(a).getLocId()%10) + 8 : (lvl.cities.get(a).getLocId()%10)),
			        		(lvl.cities.get(a).getLocId()%10 == 0) ? (lvl.cities.get(a).getLocId()/10)  : (lvl.cities.get(a).getLocId())/10+1, 
			        		 lvl, a);
					forNextCity = cB;
					buttons[index++] =cB;
					if (lvl.cities.get(a).getLocId() % 10 == 0) {
						pane.add(cB.cityButton(this,text), (lvl.cities.get(a).getLocId() % 10) + 9,
								(lvl.cities.get(a).getLocId() / 10) - 1);
					} else {
						pane.add(cB.cityButton(this,text), (lvl.cities.get(a).getLocId() % 10) - 1,
								(lvl.cities.get(a).getLocId() / 10));
					}
					row = 0;
					col = 0;
					a++;
					i = 0;

				} else
					i++;
			}

		}
		createVehicle(lvl);
		pane.setHgap(0.1);
		pane.setVgap(0.1);
		pane.setAlignment(Pos.CENTER);
		this.getChildren().add(pane);
		pane.setAlignment(Pos.CENTER);
	}



	public void createVehicle(NewLevel lvl) {
		Vehicle vehicle = lvl.vehicles.get(0);
		int location = 0;
		for (int i = 0; i < lvl.cities.size(); i++) {
			if (vehicle.getCityId() == lvl.cities.get(i).getId())
				location = lvl.cities.get(i).getLocId();
		}
		vx = location % 10 - 1;
		vy = location / 10;
		pane.add(vehicle.imageView, vx, vy);
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}
	public CityButton getForNextCity() {
		return forNextCity;
	}
	public void createLine() {
		System.out.println(31);
		Line horizontal;
		Line vertical;
		if (CityButton.x.size() == 2) {
			horizontal = new Line(CityButton.x.get(0), CityButton.y.get(0), CityButton.x.get(1), CityButton.y.get(0));
			vertical = new Line(CityButton.x.get(1), CityButton.y.get(0), CityButton.x.get(1), CityButton.y.get(1));
			horizontal.setStroke(Color.RED);
			horizontal.setStrokeWidth(5);
			vertical.setStroke(Color.RED);
			vertical.setStrokeWidth(5);
			pane.getChildren().addAll(horizontal, vertical);
			CityButton.x.clear();
			CityButton.y.clear();
			if (pane.getChildren().contains(horizontal)) {
				System.out.println("Line created successfully!");
			} else {
				System.out.println("Line creation failed!");
			}
		}
	}
}