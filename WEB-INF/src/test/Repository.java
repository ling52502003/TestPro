package test;

import javax.servlet.ServletException;

import test.db.DBRepository;

import com.google.inject.ImplementedBy;

/**
 * データを蓄積するリポジトリのインタフェース。
 */
@ImplementedBy(DBRepository.class)
public interface Repository {

	/**
	 * 有効なユーザオブジェクトを取得します。
	 * @param id
	 * @param password
	 * @return
	 */
	public User queryValidUser(String id, String password);
	
	/**
	 * ユーザを取得します。
	 * 
	 * @param userID
	 * @return
	 * @throws ServletException
	 */
	public User queryUser(String userID);
	
	/**
	 * ユーザ一覧を取得します。
	 * 
	 * @return
	 * @throws ServletException
	 */
	public User[] queryUsers();
	
	/**
	 * アイテムの一覧を取得します。
	 * @param keywords
	 * 	検索キーワード
	 * @param orderBy
	 *  ソートキー
	 * @return
	 */
	public Item[] queryItems(String keywords, String orderBy);
	
	/**
	 * アイテムを取得します。
	 * @param id
	 * @return
	 */
	public Item queryItem(String id);
	
	/**
	 * アイテムを削除します。
	 * @param id
	 */
	public void deleteItem(String id)
		throws ModifyException;
	
	/**
	 * アイテムを追加します。
	 * @param item
	 */
	public void insertItem(Item item)
		throws ModifyException;
	
	/**
	 * アイテムを更新します。
	 * @param id
	 * @param modifier
	 * @return 成功すればtrue。失敗すればfalse。
	 */
	public void updateItem(String id, Modifier modifier)
		throws ModifyException;
	
}
