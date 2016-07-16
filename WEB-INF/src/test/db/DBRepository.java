package test.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;

import test.Item;
import test.Modifier;
import test.ModifyException;
import test.Repository;
import test.User;

/**
 * DBから情報を取得するリポジトリです。
 */
public class DBRepository implements Repository {

	/**
	 * EntityManagerを保持します。
	 */
	private EntityManager _entityManager;
	
	/**
	 * 構築します。
	 */
	public DBRepository() {
		_entityManager = null;
	}

	/**
	 * 有効なユーザを取得します。
	 */
	public User queryValidUser(String id, String password) {
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT u FROM User u WHERE u.id='" + id
				+ "' AND u.password='" + password + "'";
		Query query = entityManager.createQuery(jpql);
		List users = query.getResultList();
		if (users.size() == 0) {
			return null;
		}
		return (User) users.get(0);
	}

	/**
	 * ユーザを取得します。
	 * 
	 * @param userID
	 * @return
	 * @throws ServletException
	 */
	public User queryUser(String userID) {
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.id='"
				+ userID + "'");
		List users = query.getResultList();
		if(users.size() == 0) {
			return null;
		}
		return (User) users.get(0);
	}

	/**
	 * ユーザ一覧を取得します。
	 * 
	 * @return
	 * @throws ServletException
	 */
	public User[] queryUsers() {
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("SELECT u FROM User u");
		List users = query.getResultList();
		return (User[])users.toArray(new User[0]);
	}

	/**
	 * アイテムを取得します。
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Item queryItem(String id) {
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("SELECT item FROM Item item WHERE item.id='" + id + "'");
		List items = query.getResultList();
		if(items.size() == 0) {
			return null;
		}
		return (Item) items.get(0);
	}

	/**
	 * アイテムを削除します。
	 */
	public void deleteItem(String id) throws ModifyException {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			Item currentItem = queryItem(id);
			if(currentItem == null) {
				throw new ModifyException("アイテムが見つかりません: " + id);
			}

			entityManager.remove(currentItem);
			tx.commit();
		} finally {
			entityManager.close();
		}
	}

	/**
	 * アイテムを追加します。
	 */
	public void insertItem(Item item) throws ModifyException {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			entityManager.persist(item);
			tx.commit();
		} finally {
			entityManager.close();
		}
	}

	/**
	 * アイテムの一覧を取得します。
	 */
	public Item[] queryItems(String keywords, String orderBy) {
		String ql = null;
		if(keywords != null) {
			// キーワード検索
			StringBuffer where = new StringBuffer();
			String[] patterns = createPatterns(keywords);
			if (patterns == null) {
				return new Item[0];
			}
			String[] fields = new String[] { "item.name", "item.expireDate",
					"item.user.name", "item.user.id", "item.finishedDate" };
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				for (int j = 0; j < patterns.length; j++) {
					String pattern = patterns[j];
					if (where.length() > 0) {
						where.append(" OR ");
					}
					where.append(field);
					where.append(" LIKE '%");
					where.append(pattern);
					where.append("%'");
				}
			}

			ql = "SELECT item FROM Item item WHERE " + where.toString();
		}else{
			ql = "SELECT item FROM Item item";
		}
		if(orderBy != null) {
			StringTokenizer tokenizer = new StringTokenizer(orderBy, " \t");
			StringBuffer orderByBuf = new StringBuffer();
			while(tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if(orderByBuf.length() > 0) {
					orderByBuf.append(", ");
				}
				String dir = "ASC";
				String name = token;
				if(token.startsWith("!")) {
					dir = "DESC";
					name = token.substring(1);
				}
				orderByBuf.append("item.");
				orderByBuf.append(name);
				orderByBuf.append(" ");
				orderByBuf.append(dir);
			}
			
			ql += " ORDER BY " + orderByBuf.toString();
		}
		
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery(ql);
		List items = query.getResultList();
		return (Item[])items.toArray(new Item[0]);
	}

	/**
	 * アイテムを更新します。
	 */
	public void updateItem(String id, Modifier modifier) throws ModifyException {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			Item currentItem = queryItem(id);
			modifier.modifyItem(currentItem);

			entityManager.persist(currentItem);

			tx.commit();
		} finally {
			entityManager.close();
		}
	}

	/**
	 * EntityManagerを取得します。
	 * 
	 * @return
	 */
	private EntityManager getEntityManager() {
		if (_entityManager == null) {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("sample");
			_entityManager = factory.createEntityManager();
		}
		return _entityManager;
	}

	/**
	 * パターンを生成します。
	 * 
	 * @param keyword
	 * @return
	 */
	private String[] createPatterns(String keyword) {
		StringTokenizer tokenizer = new StringTokenizer(keyword, " \t");
		if (tokenizer.countTokens() == 0) {
			return null;
		}
		ArrayList<String> keywords = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			keywords.add(token);
		}
		return keywords.toArray(new String[0]);
	}

}
