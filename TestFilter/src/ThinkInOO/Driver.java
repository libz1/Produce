package ThinkInOO;

public class Driver {
	private String name;


	public void drive(Car car){
//		car.oil();
		car.run(new Address("¶«±±"));
	}

	public void drive(Car car, Address addr){
//		car.oil();
		car.run(addr);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
