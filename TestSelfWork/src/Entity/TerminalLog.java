package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="terminallog")
public class TerminalLog {

	int ID;
	String addr;  //MAC
	String stageNo;  // ̨���
	String meterNo;  // ��λ��
	String yyyymm;  // ��������
	int testNo;  // ������ˮ��
	String opName; // ������
	String opBTime; // ����ʱ��  ��ʼ
	String opETime; // ����ʱ��  ����
	String result; // 1:���Գɹ���0����ʧ��
	String errResult;  // ʧ��ԭ��

	public TerminalLog(){
//		workStation = Debug.getHdSerialInfo();
//		workStation = SoftParameter.getInstance().getPCID();

	}

	public void init(){
		addr = "";
//		workStation = workStation = SoftParameter.getInstance().getPCID();; // ��λ
		opName = ""; // ������
		opBTime = ""; // ����ʱ��
		opETime = ""; // ����ʱ��
		result = ""; // ��������  ɨ������(1)�����Գɹ�(2)������ʧ��(3)���û������쳣(4)
		errResult = "";

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getStageNo() {
		return stageNo;
	}

	public void setStageNo(String stageNo) {
		this.stageNo = stageNo;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}

	public int getTestNo() {
		return testNo;
	}

	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getOpBTime() {
		return opBTime;
	}

	public void setOpBTime(String opBTime) {
		this.opBTime = opBTime;
	}

	public String getOpETime() {
		return opETime;
	}

	public void setOpETime(String opETime) {
		this.opETime = opETime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrResult() {
		return errResult;
	}

	public void setErrResult(String errResult) {
		this.errResult = errResult;
	}




}
