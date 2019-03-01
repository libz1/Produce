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
	String stageNo;  // 台体号
	String meterNo;  // 表位号
	String yyyymm;  // 测试年月
	int testNo;  // 测试流水号
	String opName; // 操作人
	String opBTime; // 操作时间  开始
	String opETime; // 操作时间  结束
	String result; // 1:测试成功、0测试失败
	String errResult;  // 失败原因

	public TerminalLog(){
//		workStation = Debug.getHdSerialInfo();
//		workStation = SoftParameter.getInstance().getPCID();

	}

	public void init(){
		addr = "";
//		workStation = workStation = SoftParameter.getInstance().getPCID();; // 工位
		opName = ""; // 操作人
		opBTime = ""; // 操作时间
		opETime = ""; // 操作时间
		result = ""; // 操作内容  扫描条码(1)、测试成功(2)、测试失败(3)、用户设置异常(4)
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
