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

// ���������ô���   ���յ����ݺ��ҵ�ƥ������ݽ��лظ�
public class FrameAssitant2 extends BaseFrame implements Observer {

	private TextArea txt_analy;

	Label msg1 = new Label("");

	private volatile static FrameAssitant2 uniqueInstance;

	public static FrameAssitant2 getInstance() {
		if (uniqueInstance == null) {
			synchronized (FrameAssitant2.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new FrameAssitant2();
					Publisher.getInstance().addObserver(uniqueInstance);
				}
			}
		}
		return uniqueInstance;
	}

	// �������е��ô� FrameAssitant.getInstance(1) Ŀ���ǿ��Բ��˳����ڵ�����´����ݿ�ˢ�»�������
	public static FrameAssitant2 getInstance(int flag) {
		if (uniqueInstance == null) {
			synchronized (FrameAssitant2.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new FrameAssitant2();
					Publisher.getInstance().addObserver(uniqueInstance);
				}
			}
		}
		return uniqueInstance;
	}

	public FrameAssitant2() {
	}

	// 1���첽�������̣� ��4����Ҫ�Խӿڵ�update������������ʵ�֣�������Ϣ�����ߵ�����
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
//		txt_analy.setLineWrap(true);// �����Զ����й���
//		txt_analy.setWrapStyleWord(true);// ������в����ֹ���

		//	txt_analy.setText(showMsg);
		// ����������λ��
//		txt_analy.setCaretPosition(txt_analy.getText().length());

		//https://www.yiibai.com/javafx/javafx_textfield.html
		// ���ַ���ֵ��TextField�󶨵�Stage Title
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

		// xuky 2017.07.28 telnet��������


		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());
		panel.add(jPanel, BorderLayout.SOUTH);

		JButton jb2 = new JButton("����");
		jPanel.add(jb2);
		jb2.addActionListener(e -> {
			// �����Լ칤װ
			MainServer.getInstance();
		});

//		JButton jb3 = new JButton("���Թ��̻���");
//		jPanel.add(jb3);
//
//		JButton jb4 = new JButton("�ڴ�ռ���������");
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
