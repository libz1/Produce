package Frame;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.eastsoft.util.Debug;

import DataBase.IBaseDao;
import DataBase.SessionFactoryTone;
import Entity.UserManager;
import Entity.UserManagerDaoImpl;
import Util.SoftParameter;
import Util.Util698;
import base.javafxutil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JFXMain extends Application {
    String ver = "0.01";
    int WINDOWWIDTH = 700, WINDOWHEIGHT = 500;
    // Boolean isDebug = true;
    Boolean isDebug = false;
    // Boolean QUICKRUN = false;admin
    // Boolean QUICKRUN = true; // 是否自动测试标志

    static String USERID = ""; // 自动测试时传入的用户ID
    static String USERPWD = ""; // 自动测试时传入的用户密码
    // @Override

    // public void stop(){
    // // 如果关闭了此窗口，则全部关闭程序
    // System.exit(0);
    // }
    TextField userTextField;
    PasswordField pwBox;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Util698.log(JFXMain.class.getName(), "开启软件 v"+ver, Debug.LOG_INFO);
        // xuky 2018.01.25 解决出现的"请对软件运行参数进行数据维护！"
//		new Thread(() -> {

        // xuky 2018.03.20 需要首先启动 数据库连接
//        SoftParameter.getInstance().refreshDataFromDB();
//		}).start();

//        new Thread(() -> {
//            PrefixWindow.getInstance().showFrame("通信服务器", 120, 510, 800, 200);
//        }).start();

        String svr_ip = "";
        svr_ip = svr_ip + "";
//        try {
//            Map<String, String> map = Util698.praseXml(SessionFactoryTone.getXML());
//            String str = map.get("session-factory");
//            String[] strs = str.split("/");
//            svr_ip = strs[2];
//        } catch (Exception e) {
//            Util698.log(JFXMain.class.getName(), "start Exception"+e.getMessage(), Debug.LOG_INFO);
//        }


        new Thread(() -> {
            Util698.setSystemDateTime(Util698.getURLDateTime1());
        }).start();

        // System.out.println("\r\n is for windows");

        {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Scene scene = new Scene(grid, 400, 300);
            primaryStage.setScene(scene);

            Text scenetitle = new Text(SoftParameter.getInstance().getProgeam_name()+"  ver " + ver);
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            // 创建Label对象，放到第0列，第1行
            Label userName = new Label("用户名:");
            grid.add(userName, 0, 1);

            // 创建文本输入框，放到第1列，第1行
            userTextField = new TextField();
            grid.add(userTextField, 1, 1);
            userTextField.requestFocus();

            Label pw = new Label("密码:");
            grid.add(pw, 0, 2);

            pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            Button btn = new Button("登录");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);// 将按钮控件作为子节点
            grid.add(hbBtn, 1, 4);// 将HBox pane放到grid中的第1列，第4行

//            Label ip = new Label("服务器IP地址 " + svr_ip);
//            grid.add(ip, 1, 5);

            userTextField.setOnAction((event) -> pwBox.requestFocus());
            pwBox.setOnAction((event) -> login(primaryStage));
            btn.setOnAction((event) -> login(primaryStage));

            primaryStage.setTitle(SoftParameter.getInstance().getProgeam_name());

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Util698.log(JFXMain.class.getName(), "关闭软件", Debug.LOG_INFO);

                    // 使用hibernate的方式进行数据操作
                    SessionFactoryTone.getInstance().close();

                    // 如果关闭了此窗口，则全部关闭程序
                    Platform.exit();
                    System.exit(0);
                }
            });

            primaryStage.show();

        }
        // xuky 2017.06.20 提前打开数据库连接，加速后续的数据提取速度
        // xuky 2017.07.24 为了提高软件的开启速度，所以使用Platform.runLater
        // Platform.runLater(() -> {
        // // 创建数据库连接单例对象
        // SessionFactoryTone.getInstance();
        // });

    }

    private void login(Stage primaryStage) {
        // System.out.println("login begin");
        IBaseDao<UserManager> userManager = new UserManagerDaoImpl();

        String user = userTextField.getText();
        String pwd = pwBox.getText();
        if (isDebug) {
            user = "admin";
            pwd = "6867";
        }
        List<UserManager> users = userManager.retrieve(" where userid='" + user + "' and userpwd='" + pwd + "'", "");
        if (users.size() > 0) {
            SoftParameter.getInstance().setUserManager(users.get(0));
            SoftParameter.getInstance().saveParam();
            primaryStage.close();
            // xuky 2017.11.07 根据网络时间设置本地时间
            openMain(primaryStage);
        } else {
            javafxutil.f_alert_informationDialog("操作提示", "用户名或密码错误");
        }
    }

    public void openMain(Stage primaryStage) {

        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);

        StackPane root = new StackPane();
        root.getChildren().add(swingNode);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle(SoftParameter.getInstance().getProgeam_name());
        primaryStage.setScene(scene);

        // 参考https://zhidao.baidu.com/question/1579040972785018580.html
        primaryStage.setResizable(false);

        // 如果关闭了此窗口，则全部关闭程序
        primaryStage.setOnCloseRequest(e -> {
            Util698.log(JFXMain.class.getName(), "关闭软件", Debug.LOG_INFO);

            // 使用hibernate的方式进行数据操作
            SessionFactoryTone.getInstance().close();

            // 如果关闭了此窗口，则全部关闭程序
            Platform.exit();
            System.exit(0);
        });

        // xuky 2017.02.13 设置为始终在前 ----
        // primaryStage.setAlwaysOnTop(true);
        // SoftParameter.getInstance().setUsingJavaFX(true);

        // xuky ----
        primaryStage.show();

    }

    private void createSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            // 通过JavaFX打开主界面，存在问题，模块窗口中点击开启的模块总是在主界面之后，没有提到前端
            JPanel panel = new MainPanel(ver);
            panel.setBounds(0, 0, WINDOWWIDTH, WINDOWHEIGHT);
            swingNode.setContent(panel);
        });
    }

    public static void main(String[] args) {
        // xuky 2018.05.10 使用外部的配置文件
        Util698.InitLog4jConfig();

        Util698.log(JFXMain.class.getName(), "软件入参个数:" + args.length, Debug.LOG_INFO);
        for (int i = 0; i < args.length; i++)
            Util698.log(JFXMain.class.getName(), "软件入参" + i + ":" + args[i], Debug.LOG_INFO);
        if (args.length == 2) {
            USERID = args[0];
            USERPWD = args[1];
        }
        launch(args);
    }

}
