package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// �豸������Ϣ
@Entity
@Table(name="terminalparam")
public class TerminalParam {

	int ID;
	String no; // ��ʾ����
	String name; // ��������
	String key;  // key��Ϣ
	String val;  // val��Ϣ


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
