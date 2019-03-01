package Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.eastsoft.util.Debug;

import SelfServer.MainServer;
import Util.MessageCenter;
import Util.Publisher;
import base.BaseFrame;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

// 辅助测试用窗口   接收到数据后，找到匹配的数据进行回复
public class FrameAssitant2 extends BaseFrame implements Observer {

	private TextArea txt_analy;

	Label msg1 = new Label("");

	private volatile static FrameAssitant2 uniqueInstance;

	public static FrameAssitant2 getInstance() {
		if (uniqueInstance == null) {
			synchronized (FrameAssitant2.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new FrameAssitant2();
					Publisher.getInstance().addObserver(uniqueInstance);
				}
			}
		}
		return uniqueInstance;
	}

	// 主界面中调用此 FrameAssitant.getInstance(1) 目的是可以不退出窗口的情况下从数据库刷新缓存数据
	public static FrameAssitant2 getInstance(int flag) {
		if (uniqueInstance == null) {
			synchronized (FrameAssitant2.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new FrameAssitant2();
					Publisher.getInstance().addObserver(uniqueInstance);
				}
			}
		}
		return uniqueInstance;
	}

	public FrameAssitant2() {
	}

	// 1、异步交互过程， （4）需要对接口的update函数进行自行实现，接收消息发布者的数据
	@Override
	public void update(Observable o, Object arg) {
//		Object[] s = (Object[]) arg;
//		if (s[0].equals("recv frame") && s[1].equals("user data")) {
//			showData(arg);
//		}
//		if (s[0].equals("ReadUntil") ) {
//			showTelnetData(arg);
//		}
	}

	@Override
	protected void init() {
		txt_analy = new TextArea();
//		txt_analy = new JTextArea("");

//		JScrollPane scroll_analy = new JScrollPane(txt_analy);
//		scroll_analy.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scroll_analy.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//		txt_analy.setLineWrap(true);// 激活自动换行功能
//		txt_analy.setWrapStyleWord(true);// 激活断行不断字功能

		//	txt_analy.setText(showMsg);
		// 光标放在最后的位置
//		txt_analy.setCaretPosition(txt_analy.getText().length());

		//https://www.yiibai.com/javafx/javafx_textfield.html
		// 将字符串值从TextField绑定到Stage Title
//		TextField titleTextField;
//	    titleTextField = new TextField();
//		StringProperty title = new SimpleStringProperty();
//		Scene scene  = new Scene(hBox,270,270);
//	    title.bind(titleTextField.textProperty());
//	    stage.setScene(scene);
//	    stage.titleProperty().bind(title);

//		msg1.textProperty();
//		txt_analy.property
//		txt_analy.property.addPropertyChangeListener(listener);

		int hgap = 3, vgap = 3;
		panel.setLayout(new BorderLayout(hgap, vgap));

//		panel.add(scroll_analy, BorderLayout.CENTER);
//		panel.add(txt_analy, BorderLayout.CENTER);

		// xuky 2017.07.28 telnet交互测试


		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());
		panel.add(jPanel, BorderLayout.SOUTH);

		JButton jb2 = new JButton("启动");
		jPanel.add(jb2);
		jb2.addActionListener(e -> {
			// 启动自检工装
			MainServer.getInstance();
		});

//		JButton jb3 = new JButton("测试过程汇总");
//		jPanel.add(jb3);
//
//		JButton jb4 = new JButton("内存占用情况测试");
//		jPanel.add(jb4);
//
//		jb3.addActionListener(e -> {
//		});
//
//		jb4.addActionListener(e -> {
//			memTest();
//		});
	}
	private void BindText() {
		Task task_3 = new Task() {
		    @Override
		    protected Object call() throws Exception {
		        while (true) {
//		            this.updateMessage(MessageCenter.getInstance().LinkRemain_Server);
		            Debug.sleep(200);
		        }
		    }
		};
		Thread t_3 = new Thread(task_3);
		msg1.textProperty().bind(task_3.messageProperty());
		t_3.start();

//		Task task_5 = new Task() {
//		    @Override
//		    protected Object call() throws Exception {
//		        while (true) {
//		            this.updateMessage(MessageCenter.getInstance().LinkRemain_Client);
//		            Debug.sleep(200);
//		        }
//		    }
//		};
//		Thread t_5 = new Thread(task_5);
//		msg5.textProperty().bind(task_5.messageProperty());
//		t_5.start();
	}


}
