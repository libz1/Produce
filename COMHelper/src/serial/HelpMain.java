package serial;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HelpMain extends Application {
	String ver = " ver 0.01";
	String title = "校表辅助串口软件";

	int WINDOWWIDTH = 800, WINDOWHEIGHT = 500;

	@Override
	public void start(Stage primaryStage) throws Exception {

		{
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			Scene scene = new Scene(grid, 400, 300);
			primaryStage.setScene(scene);

			Text scenetitle = new Text("欢迎使用" + title + ver);
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);

			primaryStage.setTitle(title);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					Util698.log(HelpMain.class.getName(), "关闭软件" + title, Debug.LOG_INFO);

					// 如果关闭了此窗口，则全部关闭程序
					Platform.exit();
					System.exit(0);
				}
			});

			primaryStage.show();

			new Thread(() -> {
				HelpInstance.getInstance();
			}).start();

		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
