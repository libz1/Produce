package base;

import com.eastsoft.util.DataConvert;

import Util.Util698;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class TransImplObject extends TransBehavior_javafx{


	private String detail_export_columns = "";
	private String[] detail_export_column_names;
	Object newObject = null;
	public TransImplObject(String columns,Object newObj,String columnNames[]){
		this.detail_export_columns = columns;
		this.detail_export_column_names = columnNames;
		this.newObject = newObj;
	}

//	ProduceCase obj;
	@Override
	public void setData(Object object) {

		if (object == null){
			clearComponent();
			return;
		}
		// 将传入的对象转变为界面中显示的数据
//		 obj = (ProduceCase)object;

//		 subid,name,send,expect,delaytime,waittime,protocol,retrys,note,caseno

		IDs[0] = DataConvert.int2String((int)Util698.getFieldValueTypeByName("ID",object)[0]);

//		List<Map> infoList = Util698.getFiledsInfo(object);
		int i = 0;
//	   	for( Map<String, Object> info:infoList ){
	   	for( String name: detail_export_columns.split(",") ){
//			String name = info.get("name").toString();
			Object[] obs = Util698.getFieldValueTypeByName(name,object);
			if (obs[0] == null) {
				// xuky 2017.08.02 如果数据为空，需要进行写入""操作
				((TextField) component[i]).setText("");
				i++;
				continue;
			}
			if (((String)obs[1]).indexOf("String") >= 0)
				if (detail_export_column_names[i].indexOf(";code:")>=0)
					((ComboBox) component[i]).setValue((String) obs[0]);
				else
					((TextField) component[i]).setText((String) obs[0]);
			else
				((TextField) component[i]).setText(DataConvert.int2String((int) obs[0]));
			i++;
	   	}
	}

	@Override
	public Object getData() {
		// 将界面中显示的数据转变为对象 需要进行数据类型的转换

		// xuky 2017.07.05 需要创建新的对象实例，否则会出现ObservableList<T> data_objs数据异常的情况
		// 参考http://www.cnblogs.com/zhangnanblog/archive/2012/04/27/2473820.html
		try {
			newObject = Class.forName(newObject.getClass().getName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Util698.setFieldValueByName("ID",newObject,DataConvert.String2Int(IDs[0]));

//		List<Map> infoList = Util698.getFiledsInfo(newObject);
		int i = 0;

	   	for( String name: detail_export_columns.split(",") ){
			String type = Util698.getFieldTypeByName(name,newObject);
			if (type.indexOf("String") >= 0)
				if (detail_export_column_names[i].indexOf(";code:")>=0)
					Util698.setFieldValueByName(name,newObject,((ComboBox) component[i]).getValue().toString());
				else
					Util698.setFieldValueByName(name,newObject,((TextField)component[i]).getText());
			else
				Util698.setFieldValueByName(name,newObject,DataConvert.String2Int(((TextField)component[i]).getText()));
			i++;
	   	}

		return newObject;
	}

	@Override
	public Object getDataWithID() {
		return null;
	}


}
