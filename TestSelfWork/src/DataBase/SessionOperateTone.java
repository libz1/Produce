package DataBase;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.eastsoft.util.Debug;

import Util.Util698;

// 单例对象进行数据库操作
public class SessionOperateTone {

	private volatile static SessionOperateTone uniqueInstance;

	public static SessionOperateTone getInstance() {
		if (uniqueInstance == null) {
			synchronized (SessionOperateTone.class) {
				if (uniqueInstance == null) {
					// 双重检查加锁
					uniqueInstance = new SessionOperateTone();
				}
			}
		}
		return uniqueInstance;
	}

	private SessionFactory sessionFactory = SessionFactoryTone.getInstance().getSessionFactory();

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		// 在于Spring整合的时候，用下面的方法
		// return sessionFactory.getCurrentSession();
		return sessionFactory.openSession();
	}

	private SessionOperateTone() {
	}

	public synchronized List operateDB(String type, Object data) {
		return operateDB(type, data, "","","",null);
	}

	public synchronized List operateDB(String type, int ID, Class<?> clz) {
		return operateDB(type, ID, "","","",clz);
	}

	public synchronized List operateDB(String type, Object data,String className,String where, String order, Class<?> clz) {
		List<Object> list = new ArrayList<Object>();
		Session session = null;
		Transaction tx = null;
		try {
			// 获取Session
			session = getSession();
			// 开启事务

			if  (type.equals("create") || type.equals("update"))
				tx = session.beginTransaction();
			// 用户的保存
			if (type.equals("create")){
				session.save(data);
				list.add(data);
			}
			else if (type.equals("update"))
				session.update(data);
			else if (type.equals("retrieve")){
				String sql = "from " + className + " " + where + " " + order;
				list = session.createQuery(sql).list();
			}
			else if (type.equals("retrieveByID")){
				list.add(session.get(clz, (int)data));
			}
			else if (type.equals("retrieveBySQL")){
				String sql = (String)data;
				list = session.createQuery(sql).list();
			}
			else if (type.equals("executeSQL")){
				String sql = (String)data;
				int ret = session.createQuery(sql).executeUpdate();
				list.add(ret);
			}

		} catch (Exception e) {
	        Util698.log(SessionOperateTone.class.getName(), "operateDB Exception"+e.getMessage()+" type"+type, Debug.LOG_INFO);

			// 进行事务的回滚
			if  (type.equals("create") || type.equals("update"))
				tx.rollback();
			throw new RuntimeException(e);
		} finally {
			try {
				// 进行事务的提交
				if  (type.equals("create") || type.equals("update"))
					tx.commit();
				// 关闭session
				session.close();
			} catch (Exception e) {
		        Util698.log(SessionOperateTone.class.getName(), "operateDB commit Exception"+e.getMessage()+" type"+type, Debug.LOG_INFO);
			}
		}
		return list;

	}

}
