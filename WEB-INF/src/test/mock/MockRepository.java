package test.mock;

import java.sql.Date;
import java.util.ArrayList;

import test.Item;
import test.Modifier;
import test.ModifyException;
import test.Repository;
import test.User;

/**
 * テスト用のRepositoryインタフェースの実装クラス。
 */
public class MockRepository implements Repository {
	
	/**
	 * ユーザ情報を定義する定数。
	 */
	private static User[] USERS = new User[]{ createUser(1), createUser(2), createUser(3) };
	
	/**
	 * アイテム情報を定義する変数。
	 */
	private static ArrayList<Item> _items = null;
	
	/**
	 * ユーザを生成します。
	 * @param index
	 * @return
	 */
	private static User createUser(int index) {
		User user = new User();
		user.setId("TEST" + index);
		user.setName("テスト" + index);
		user.setPassword("P" + index);
		
		return user;
	}
	
	/**
	 * 構築します。
	 */
	private MockRepository() {
		;
	}

	/**
	 * 有効なユーザを取得します。
	 */
	public User queryValidUser(String id, String password) {
		User user = findUser(id);
		if(user == null) {
			return null;
		}
		if(user.getPassword().equals(password) == false) {
			return null;
		}
		return user;
	}

	/**
	 * ユーザを検索します。
	 */
	public User queryUser(String userID) {
		return findUser(userID);
	}

	/**
	 * ユーザリストを取得します。
	 */
	public User[] queryUsers() {
		return USERS;
	}

	/**
	 * アイテムを削除します。
	 */
	public void deleteItem(String id) throws ModifyException {
		ArrayList<Item> items = getItems();
		Item targetItem = null;
		for(int i = 0; i < items.size(); i ++) {
			Item item = items.get(i);
			if(item.getId().equals(id)) {
				targetItem = item;
			}
		}
		if(targetItem == null) {
			return;
		}
		items.remove(targetItem);
	}

	/**
	 * アイテムを追加します。
	 */
	public void insertItem(Item item) throws ModifyException {
		item.setId(createNewItemId());
		
		ArrayList<Item> items = getItems();
		items.add(item);
	}

	/**
	 * アイテムを取得します。
	 */
	public Item queryItem(String id) {
		return findItem(id);
	}

	/**
	 * 検索します。
	 */
	public Item[] queryItems(String keywords, String orderBy) {
		ArrayList<Item> items = getItems();
		if(keywords != null) {
			ArrayList<Item> result = new ArrayList<Item>();
			for(int i = 0; i < items.size(); i ++) {
				Item item = items.get(i);
				if(item.getName().contains(keywords)) {
					result.add(item);
				}else if(item.getUser().getName().contains(keywords)) {
					result.add(item);
				}
			}
			return result.toArray(new Item[0]);
		}else{
			return items.toArray(new Item[0]);
		}
	}

	/**
	 * アイテムを更新します。
	 */
	public void updateItem(String id, Modifier modifier) throws ModifyException {
		Item item = findItem(id);
		modifier.modifyItem(item);
	}
	
	/**
	 * ユーザを検索します。
	 * @param id
	 * @return
	 */
	private User findUser(String id) {
		for(int i = 0; i < USERS.length; i ++) {
			User user = USERS[i];
			if(user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * アイテム一覧を取得します。
	 * @return
	 */
	private ArrayList<Item> getItems() {
		if(_items == null) {
			_items = createInitialItems();
		}
		return _items;
	}

	/**
	 * アイテムを検索します。
	 * @param id
	 * @return
	 */
	private Item findItem(String id) {
		ArrayList<Item> items = getItems();
		for(int i = 0; i < items.size(); i ++) {
			Item item = items.get(i);
			if(item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 新しいアイテムIDを生成します。
	 * @return
	 */
	private String createNewItemId() {
		String baseId = "ID" + System.currentTimeMillis();
		for(int i = 0; i < 10000; i ++) {
			String newId = baseId + "_" + i;
			if(findItem(newId) == null) {
				return newId;
			}
		}
		throw new IllegalStateException();
	}
	
	/**
	 * 初期アイテムリストを生成します。
	 * @return
	 */
	private ArrayList<Item> createInitialItems() {
		ArrayList<Item> result = new ArrayList<Item>();
		
		result.add(createItem("T1", "作業1", findUser("TEST1"), new Date(System.currentTimeMillis()), null));
		result.add(createItem("T2", "作業2", findUser("TEST2"), new Date(System.currentTimeMillis()), null));
		
		return result;
	}
	
	/**
	 * アイテムを生成します。
	 * @param id
	 * @param name
	 * @param user
	 * @param expireDate
	 * @param finishedDate
	 * @return
	 */
	private Item createItem(String id, String name, User user, Date expireDate, Date finishedDate) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setUser(user);
		item.setExpireDate(expireDate);
		item.setFinishedDate(finishedDate);
		
		return item;
	}
}
