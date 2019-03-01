package Frame;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.eastsoft.util.Debug;
import com.jfoenix.controls.JFXButton;

import SelfServer.MainSelfServer;
import Util.MessageCenter;
import Util.Publisher;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// 辅助测试用窗口
public class FrameAssitant extends Application implements Observer{
	private volatile static FrameAssitant uniqueInstance;
	private Stage stage;
	private TextArea textArea;
	private String oldMsg = "";

    Queue<Object[]> msgQueue = new LinkedList<Object[]>();
    Lock Queuelock = new ReentrantLock();
    public void push(Object[] data) {
        Queuelock.lock();
        try {
        	msgQueue.offer(data);
        } finally {
            Queuelock.unlock();
        }
    }

    // xuky 2018.06.01 调整为死等模式 无需调用者进行处理
    public Object[] pop() {
        Object[] obj = null;
        while (true) {
            Queuelock.lock();
            try {
                obj = msgQueue.poll();
                if (obj != null){
//					Util698.log(TerminalParameterController.class.getName(), "pop:"+obj, Debug.LOG_INFO);
                    return obj;
                }
            } finally {
                Queuelock.unlock();
            }
            Debug.sleep(50);
        }
    }

	public static FrameAssitant getInstance() {
		if (uniqueInstance == null) {
			synchronized (FrameAssitant.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new FrameAssitant();
				}
			}
		}
		// 实现窗口的前置效果  先置前，后取消，实现前置过程，但是又不会始终前置
		uniqueInstance.setAlwaysOnTop(true);
		uniqueInstance.setAlwaysOnTop(false);
		return uniqueInstance;
	}

	private FrameAssitant() {
		init0();
	}

	private void init0() {
		stage = new Stage();
		stage.setTitle("辅助功能");
		// 关闭时执行的代码
		stage.setOnCloseRequest((event)-> {
			stage.close();
			uniqueInstance = null;
		});

		// 控制展示界面的大小 将grid放在其中进行展示
//		Scene scene = new Scene(creatGridPane(), 300, 275);
		Scene scene = new Scene(creatBorderPane(), 600, 400);
		stage.setScene(scene);
		stage.show();

//		BindText();

//		ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 50, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
//        pool.submit(new ListShowThread());
		Publisher.getInstance().addObserver(this);
		new updateMsgThread().start();
	}

    public class updateMsgThread extends Thread {
    	public updateMsgThread() {
    		super.setName("updateMsgThread");
    	}
        @Override
        public void run() {
            while (true) {
            	Object[] obj = pop();
            	textArea.setText(textArea.getText()+"\n"+obj[2]);
            	Debug.sleep(100);
            }
        }
    }


	private Pane creatBorderPane(){
		BorderPane borderPane = new BorderPane();
		{
			// 添加多行信息控件
			textArea = new TextArea();
			String msg = "1111111111111111111111111111122222222222222222222333333333333333333";
			msg = msg + "\n" + "中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息中间区域信息";
			textArea.setText(msg);
			textArea.setWrapText(true);
			// 中间区域信息
			borderPane.setCenter(textArea);
		}
		{
			// 底部按钮信息
			HBox hbox = new HBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setPrefHeight(50.0);
			Button btn_1 = new Button("启动自检工装模拟器");
			btn_1.setOnAction(event->{
				textArea.setText("");
				// xuky 2018.09.26 类似这种会消耗大量时间运行的任务，放在线程中执行，否则会影响界面的显示
				new Thread(()->{
					MainSelfServer.getInstance();
				}).start();
			});
			// 添加按键间距信息
			Button btn_2 = new Button("其他测试项目");

			JFXButton jfxBtn_1 = new JFXButton("JFXButton");
//			jfxBtn_1.setButtonType(ButtonType.RAISED);
			jfxBtn_1.setStyle("-fx-background-color: blue;-fx-text-fill: white;-jfx-button-type: RAISED;");

			JFXButton jfxBtn_2 = new JFXButton("JFXButton");
//			jfxBtn_2.setStyle("-fx-background-color: blue;-fx-text-fill: ladder(background, white 49%, black 50%);");
//			jfxBtn_2.setStyle("-fx-text-fill: ladder(background, white 49%, black 50%);");
//			jfxBtn_2.setButtonType(ButtonType.RAISED);

			// 添加按键
			hbox.getChildren().add(btn_1);
			hbox.getChildren().add(btn_2);
			hbox.getChildren().add(jfxBtn_1);
			hbox.getChildren().add(jfxBtn_2);

			HBox.setMargin(btn_1,new Insets(5));
			HBox.setMargin(btn_2,new Insets(5));
			HBox.setMargin(jfxBtn_1,new Insets(5));

			borderPane.setBottom(hbox);

		}
		return borderPane;
	}

	public void setAlwaysOnTop(Boolean top) {
		stage.setAlwaysOnTop(top);
		// xuky 2017.07.06 如果用户是点击最小化的，也可以展示出来
		stage.setIconified(false);
	}

	@Override
	public void start(Stage primaryStage) {
	}

	// xuky 2018.09.26 因为是多线程并行操作，消息交互非常频繁，目前这样的交互过程，会导致传输消息的丢失
	private void BindText() {
		Task task_3 = new Task() {
		    @Override
		    protected Object call() throws Exception {
		        while (true) {
		        	String msg = MessageCenter.getInstance().SelfServer_msg;
		        	if (!oldMsg.equals(msg)){
		        		oldMsg = msg;
			        	msg = textArea.getText()+"\n"+ msg;
		        		System.out.println("textArea:"+msg);
			            this.updateMessage(msg);
		        	}
		        	else
		        		Debug.sleep(10);
		        }
		    }
		};
		Thread t_3 = new Thread(task_3);
		textArea.textProperty().bind(task_3.messageProperty());
		t_3.start();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			Object[] s = (Object[]) arg1;
			if (s[0].equals("SelfServer_msg")) {
				push(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
