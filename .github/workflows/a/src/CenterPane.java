
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CenterPane extends GridPane {
    int rectangleCount = 10;

    public CenterPane() {
        int id = 1;
        Image img = new Image("ist.png");
        Image img1 = new Image("mersin.png");
        Image img2 = new Image("city1.png");
        Image img3 = new Image("city2.png");
        Image img4 = new Image("city3.png");
        Image img5 = new Image("city4.png");
        
        Image[] imgarr = new Image[6];
        imgarr[0] = img;
        imgarr[1] = img1;
        imgarr[2] = img2;
        imgarr[3] = img3;
        imgarr[4] = img4;
        imgarr[5] = img5;
        
        
        
        for (int row = 0; row < rectangleCount; row++) {
            for (int col = 0; col < rectangleCount; col++) {
                Rectangle square = new Rectangle();
                square.widthProperty().bind(widthProperty().divide(10).subtract(10));
                square.heightProperty().bind(heightProperty().divide(10).subtract(10));;
                square.setFill(Color.TRANSPARENT);
                square.setId(String.valueOf(id));
              //square.setStroke(Color.BLACK);

                add(square, col, row);

                if (id == 14) {
                	
                    Label label = new Label("Istanbul");
                    label.setAlignment(Pos.BOTTOM_CENTER);
                    label.setFont(Font.font("Arial" , FontWeight.BOLD , 12));
                   // add(label, 3, 1);
                    
                    label.toFront();
                    //label.setPadding(new Insets(0,0,0,15));
                    
                    ImageView iv = new ImageView(imgarr[(int)(Math.random() * 6)]);
                    iv.setFitHeight(35);
                    iv.setFitWidth(35);
                   // add(iv, 3, 1);
                    VBox vb = new VBox(5);
                    vb.getChildren().addAll(iv, label);
                    vb.setAlignment(Pos.CENTER);
                    add(vb, 3, 1);
                    
                    
                    
                }
                if (id == 62) {
                    Label label = new Label("Mersin");
                    add(label, 1, 6);
                    setPadding(new Insets(15,15,15,15));
                    
                    ImageView iv = new ImageView(imgarr[(int)(Math.random() * 6)]);
                    iv.setFitHeight(35);
                    iv.setFitWidth(35);
                   // add(iv, 3, 1);
                    VBox vb = new VBox(5);
                    vb.getChildren().addAll(iv, label);
                    vb.setAlignment(Pos.CENTER);
                    add(vb, 1, 6);
                }
                if (id == 89) {
                    Label label = new Label("Van");
                    add(label, 8, 8);
                    setPadding(new Insets(15,15,15,15));
                    
                    ImageView iv = new ImageView(imgarr[(int)(Math.random() * 6)]);
                    iv.setFitHeight(35);
                    iv.setFitWidth(35);
                   // add(iv, 3, 1);
                    VBox vb = new VBox(5);
                    vb.getChildren().addAll(iv, label);
                    vb.setAlignment(Pos.CENTER);
                    add(vb, 8, 8);
                }

                id++;
            }
        }
    }

	
}