import java.io.File;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	int currentLevel = 0;

	@Override
	public void start(Stage primaryStage) {
		// ana menü scene burda oluşuyor gösteriliyor
		primaryStage.setTitle("Term Project");
		Menu menu = new Menu();
		Scene scene = new Scene(menu, 500, 500);
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		TopPane tp = new TopPane();

		// bir sonraki levelin scene de burda normalde level atlıyordum tuşa basınca
		// fakat çok yavaş çalışıyordu ben de saldım
		// biraz yavaş çalışıyor yapacak bir şey yok
		menu.newGameButton.setOnAction(e -> {

			String[] lvlarr = { "level1.txt", "level2.txt", "level3.txt", "level4.txt", "level5.txt" };
			File name = new File(lvlarr[currentLevel]);
			NewLevel level = new NewLevel(name);
			level.readingFile();
			Text text = new Text();
			NewCenterPane root = new NewCenterPane(level,text);
			BottomPane bp = new BottomPane(level,text);
			MainPane pane = new MainPane(tp, root, bp);
			level.setPane(pane);
            Scene scene2 = new Scene(pane,500,600);
			primaryStage.setScene(scene2);

		});
		menu.continueGameButton.setOnAction(e -> {

			String[] lvlarr = { "level1.txt", "level2.txt", "level3.txt", "level4.txt", "level5.txt" };
			int index = 0;
			String lastLevel = SaveAndLoad.load();
			System.out.println(lastLevel);
			for (int i = 0; i < lvlarr.length; i++) {
				if (lvlarr[i].equals(lastLevel))
					index = i;
				System.out.println(lvlarr[i]);
			}
			System.out.println(index);
			File name = new File(lvlarr[index]);
			NewLevel level = new NewLevel(name);
			level.readingFile();
			Text text = new Text();
			NewCenterPane root = new NewCenterPane(level,text);
			BottomPane bp = new BottomPane(level,text);
			MainPane pane = new MainPane(tp, root, bp);
			level.setPane(pane);

			currentLevel = index;
			tp.t1.setText("LEVEL: " + (index + 1));
			Scene scene2 = new Scene(pane, 500, 600);
			primaryStage.setScene(scene2);
		});
		tp.b.setOnAction(e -> {
			// System.out.println("a");
			currentLevel++;
			String[] lvlarr = { "level1.txt", "level2.txt", "level3.txt", "level4.txt", "level5.txt" };
			SaveAndLoad sl = new SaveAndLoad(lvlarr[currentLevel]);
			File name = new File(lvlarr[currentLevel]);
			NewLevel level = new NewLevel(name);
			level.readingFile();
			Text text = new Text();
			NewCenterPane root = new NewCenterPane(level,text);
			BottomPane bp = new BottomPane(level,text);
			MainPane pane = new MainPane(tp, root, bp);
			level.setPane(pane);
			Scene scene2 = new Scene(pane, 500, 600);
			sl.save();
			primaryStage.setScene(scene2);

			tp.t1.setText("LEVEL: " + (currentLevel + 1));

		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}