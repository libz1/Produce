package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="param")
public class Param {
    int ID;
    String computer;  // 电脑识别码
    String type;  // 参数类型  {验证、设置}
    String name;  // 参数名称
    String keyname;  // 键
    String value;  // 值
    int serial;  // 序号信息
    String note1;  // 说明1
    String note2;  // 说明2
    int delaytime;  // 发前延时，单位毫秒
    int waittime;  // 等待超时时间

    public Param(){
    	type = "验证";
    	serial = 0;
    	delaytime = 100;
    	waittime = 2500;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getComputer() {
		return computer;
	}
	public void setComputer(String computer) {
		this.computer = computer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyname() {
		return keyname;
	}
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public int getSerial() {
		return serial;
	}

	public int getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(int delaytime) {
		this.delaytime = delaytime;
	}

	public int getWaittime() {
		return waittime;
	}

	public void setWaittime(int waittime) {
		this.waittime = waittime;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}


}
