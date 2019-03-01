package ThinkInOO;

public class mainApp {

	public static void main(String[] args) {
		// 老张开车去东北
		// 1-new Laozhang() 太具体
		// new People() 太宽泛
		// new Driver()更好 更贴近具体的问题 老张是司机或是人的一个特例
		// 2-new Car()
		// 3-new Address()
		// laozhang.kaiche(dongbei);
		// 封装 看名字实现功能 复用 “封装、继承和多态”

		// 1)问题域中有哪些可以封装的类(名词)
		// 人-美国人(子类) 人-国籍(属性) 两种都正确
		// 某些名词是属性，有些是子类 不要追求完美，进行实现后进行调整
		// 2)考虑类的属性，但是属性必须与当前问题有关
		// 3)方法的封装 以某个对象为主语，看看他可以做什么事情
		// 4)类之间的关系 "老张"开"车" 车如果是老张家的，就可以做为他的一个属性，如果不是他家的，是任一辆车，则可以调用
		// 5)隐藏(封装) 降低耦合度 耦合至合适 如果新盖一个房子，必须拆掉原先的房子，则设计不合理
		// car.run(); -> 司机只是简单的调用这个函数即可，无需关注车开动的细节，比如挂档、比如加油，自动挡或手动挡等
		// 扩展性好！：添加而不是修改
		Driver d = new Driver();
		d.setName("老张");
		// d.drive(new Car());
		d.drive(new Car(), new Address("东北"));

	}

}
