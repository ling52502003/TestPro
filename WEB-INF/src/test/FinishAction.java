package test;

import java.sql.Date;
import java.util.Calendar;

import com.google.inject.Inject;

/**
 * 完了/未完了切替をおこなうアクション。
 */
public class FinishAction extends AbstractDBAction
	implements Modifier{

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * アイテムIDを保持します。
	 */
	private String _itemID;

	/**
	 * キーワードを保持します。
	 */
	private String _keyword;
	
	/**
	 * リポジトリを保持します。
	 */
	private Repository _repository;

	/**
	 * 構築します。
	 */
	@Inject
	public FinishAction(Repository repository) {
		_repository = repository;
		_itemID = null;
		_keyword = null;
	}

	/**
	 * アイテムのIDを設定します。
	 * 
	 * @param id
	 * @return
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * キーワードを設定します。
	 * 
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	/**
	 * キーワードを取得します。
	 * 
	 * @return
	 */
	public String getKeyword() {
		return _keyword;
	}

	/**
	 * 実行します。
	 * 
	 * @return
	 */
	public String execute() {
		try {
			_repository.updateItem(_itemID, this);

			if (_keyword != null) {
				return "search";
			} else {
				return "list";
			}
		} catch(ModifyException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * アイテムを更新します。
	 */
	public void modifyItem(Item item) throws ModifyException {
		if (item.getFinishedDate() == null) {
			// 完了に変更
			Calendar calendar = Calendar.getInstance();
			item
					.setFinishedDate(new Date(calendar.getTimeInMillis()));
		} else {
			// 未完了に変更
			item.setFinishedDate(null);
		}
	}
	
}
