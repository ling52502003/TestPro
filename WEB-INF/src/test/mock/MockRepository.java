package test.mock;

import java.sql.Date;
import java.util.ArrayList;

import test.Item;
import test.Modifier;
import test.ModifyException;
import test.Repository;
import test.User;

/**
 * �e�X�g�p��Repository�C���^�t�F�[�X�̎����N���X�B
 */
public class MockRepository implements Repository {
	
	/**
	 * ���[�U�����`����萔�B
	 */
	private static User[] USERS = new User[]{ createUser(1), createUser(2), createUser(3) };
	
	/**
	 * �A�C�e�������`����ϐ��B
	 */
	private static ArrayList<Item> _items = null;
	
	/**
	 * ���[�U�𐶐����܂��B
	 * @param index
	 * @return
	 */
	private static User createUser(int index) {
		User user = new User();
		user.setId("TEST" + index);
		user.setName("�e�X�g" + index);
		user.setPassword("P" + index);
		
		return user;
	}
	
	/**
	 * �\�z���܂��B
	 */
	private MockRepository() {
		;
	}

	/**
	 * �L���ȃ��[�U���擾���܂��B
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
	 * ���[�U���������܂��B
	 */
	public User queryUser(String userID) {
		return findUser(userID);
	}

	/**
	 * ���[�U���X�g���擾���܂��B
	 */
	public User[] queryUsers() {
		return USERS;
	}

	/**
	 * �A�C�e�����폜���܂��B
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
	 * �A�C�e����ǉ����܂��B
	 */
	public void insertItem(Item item) throws ModifyException {
		item.setId(createNewItemId());
		
		ArrayList<Item> items = getItems();
		items.add(item);
	}

	/**
	 * �A�C�e�����擾���܂��B
	 */
	public Item queryItem(String id) {
		return findItem(id);
	}

	/**
	 * �������܂��B
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
	 * �A�C�e�����X�V���܂��B
	 */
	public void updateItem(String id, Modifier modifier) throws ModifyException {
		Item item = findItem(id);
		modifier.modifyItem(item);
	}
	
	/**
	 * ���[�U���������܂��B
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
	 * �A�C�e���ꗗ���擾���܂��B
	 * @return
	 */
	private ArrayList<Item> getItems() {
		if(_items == null) {
			_items = createInitialItems();
		}
		return _items;
	}

	/**
	 * �A�C�e�����������܂��B
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
	 * �V�����A�C�e��ID�𐶐����܂��B
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
	 * �����A�C�e�����X�g�𐶐����܂��B
	 * @return
	 */
	private ArrayList<Item> createInitialItems() {
		ArrayList<Item> result = new ArrayList<Item>();
		
		result.add(createItem("T1", "���1", findUser("TEST1"), new Date(System.currentTimeMillis()), null));
		result.add(createItem("T2", "���2", findUser("TEST2"), new Date(System.currentTimeMillis()), null));
		
		return result;
	}
	
	/**
	 * �A�C�e���𐶐����܂��B
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
