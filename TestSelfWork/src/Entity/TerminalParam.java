package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// 设备参数信息
@Entity
@Table(name="terminalparam")
public class TerminalParam {

	int ID;
	String no; // 显示次序
	String name; // 参数名称
	String key;  // key信息
	String val;  // val信息


	public TerminalParam(){
	}

	public void init(){
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

}
