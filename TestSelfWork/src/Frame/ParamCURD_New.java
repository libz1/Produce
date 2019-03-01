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

// 辅助测试用窗口
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

	public static ParamCURD_New getInstance() {
		if (uniqueInstance == null) {
			synchronized (ParamCURD_New.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new ParamCURD_New();
				}
			}
		}
		// 实现窗口的前置效果  先置前，后取消，实现前置过程，但是又不会始终前置
		uniqueInstance.setAlwaysOnTop(true);
		uniqueInstance.setAlwaysOnTop(false);
		return uniqueInstance;
	}

	private ParamCURD_New() {
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
		Scene scene = new Scene(creatBorderPane(), 800, 600);
		stage.setScene(scene);
		stage.show();

//		BindText();

//		ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 50, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
//        pool.submit(new ListShowThread());
	}


	private Pane creatBorderPane(){
		// ------------------------------------------------
		// 明细信息用字段信息 内容较为全面
		String[] detail_colNames = { "参数名称","参数key","数值","参数类型;code:PARAMTYPE","执行次序","发前超时","等待时间","备注1" };
		String detail_export_columns = "name,keyname,value,type,serial,delaytime,waittime,note1";

		String[] table_colNames = { "ID[0]", "参数名称","参数key","数值","参数类型","执行次序","发前超时","等待时间","备注1" };
		String table_columns = "ID,name,keyname,value,type,serial,delaytime,waittime,note1";

		object_crud = new ObjectCURD<Param>(new ParamDaoImpl(), new TransImplObject(detail_export_columns, new Param(),detail_colNames),
					detail_colNames, detail_export_columns, table_colNames, table_columns, "getKeyname","","order by serial");
		return object_crud;
	}

	public void setAlwaysOnTop(Boolean top) {
		stage.setAlwaysOnTop(top);
		// xuky 2017.07.06 如果用户是点击最小化的，也可以展示出来
		stage.setIconified(false);
	}

	@Override
	public void start(Stage primaryStage) {
	}


}
