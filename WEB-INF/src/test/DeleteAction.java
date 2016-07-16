package test;

import com.google.inject.Inject;

/**
 * 削除をおこなうアクション。
 */
public class DeleteAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムIDを保持します。
	 */
	private String _itemID;

	/**
	 * アイテムを保持します。
	 */
	private Item _item;
	
	/**
	 * リポジトリを保持します。
	 */
	private Repository _repository;

	/**
	 * 構築します。
	 * @param repository
	 */
	@Inject
	public DeleteAction(Repository repository) {
		_repository = repository;
		_itemID = null;
		_item = null;
	}

	/**
	 * アイテムIDを設定します。
	 * 
	 * @param id
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * アイテムIDを取得します。
	 * 
	 * @return
	 */
	public String getItem_id() {
		return _itemID;
	}

	/**
	 * アイテムを取得します。
	 * 
	 * @return
	 */
	public Item getItem() {
		return _item;
	}

	/**
	 * 追加画面を表示します。
	 * 
	 * @return
	 */
	public String show() {
		_item = _repository.queryItem(_itemID);

		return "success";
	}

	/**
	 * 実行します。
	 * 
	 * @return
	 */
	public String execute() {
		try{
			_repository.deleteItem(_itemID);
			
			return "success";
		}catch(ModifyException e) {
			return showError(e.getMessage());
		}
	}
}
