package Frame;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.eastsoft.util.Debug;

import Entity.Param;
import Entity.ParamDaoImpl;
import base.ObjectCURD;
import base.TransImplObject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// ���������ô���
public class ParamCURD_New extends Application{
	private volatile static ParamCURD_New uniqueInstance;
	private Stage stage;
	private ObjectCURD<Param> object_crud;


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

    // xuky 2018.06.01 ����Ϊ����ģʽ ��������߽��д���
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

	public static ParamCURD_New getInstance() {
		if (uniqueInstance == null) {
			synchronized (ParamCURD_New.class) {
				if (uniqueInstance == null) {
					// ˫�ؼ�����
					uniqueInstance = new ParamCURD_New();
				}
			}
		}
		// ʵ�ִ��ڵ�ǰ��Ч��  ����ǰ����ȡ����ʵ��ǰ�ù��̣������ֲ���ʼ��ǰ��
		uniqueInstance.setAlwaysOnTop(true);
		uniqueInstance.setAlwaysOnTop(false);
		return uniqueInstance;
	}

	private ParamCURD_New() {
		init0();
	}

	private void init0() {
		stage = new Stage();
		stage.setTitle("��������");
		// �ر�ʱִ�еĴ���
		stage.setOnCloseRequest((event)-> {
			stage.close();
			uniqueInstance = null;
		});

		// ����չʾ����Ĵ�С ��grid�������н���չʾ
//		Scene scene = new Scene(creatGridPane(), 300, 275);
		Scene scene = new Scene(creatBorderPane(), 800, 600);
		stage.setScene(scene);
		stage.show();

//		BindText();

//		ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 50, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
//        pool.submit(new ListShowThread());
	}


	private Pane creatBorderPane(){
		// ------------------------------------------------
		// ��ϸ��Ϣ���ֶ���Ϣ ���ݽ�Ϊȫ��
		String[] detail_colNames = { "��������","����key","��ֵ","��������;code:PARAMTYPE","ִ�д���","��ǰ��ʱ","�ȴ�ʱ��","��ע1" };
		String detail_export_columns = "name,keyname,value,type,serial,delaytime,waittime,note1";

		String[] table_colNames = { "ID[0]", "��������","����key","��ֵ","��������","ִ�д���","��ǰ��ʱ","�ȴ�ʱ��","��ע1" };
		String table_columns = "ID,name,keyname,value,type,serial,delaytime,waittime,note1";

		object_crud = new ObjectCURD<Param>(new ParamDaoImpl(), new TransImplObject(detail_export_columns, new Param(),detail_colNames),
					detail_colNames, detail_export_columns, table_colNames, table_columns, "getKeyname","","order by serial");
		return object_crud;
	}

	public void setAlwaysOnTop(Boolean top) {
		stage.setAlwaysOnTop(top);
		// xuky 2017.07.06 ����û��ǵ����С���ģ�Ҳ����չʾ����
		stage.setIconified(false);
	}

	@Override
	public void start(Stage primaryStage) {
	}


}
