package test;

import java.util.Calendar;

import com.google.inject.Inject;

/**
 * 一覧を表示するアクション。
 */
public class ListAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * アイテムを保持します。
	 */
	private Item[] _items;
	
	/**
	 * ソートキーを保持します。
	 */
	private String _orderBy;
	
	/**
	 * リポジトリを保持します。
	 */
	private Repository _repository;
	
	/**
	 * 構築します。
	 * @param repository
	 */
	@Inject
	public ListAction(Repository repository) {
		_repository = repository;
		_orderBy = null;
		_items = null;
	}
	
	/**
	 * アイテムの一覧を取得します。
	 * @return
	 */
	public Item[] getItems() {
		return _items;
	}
	
	/**
	 * ソートキーを保持します。
	 * @param orderBy
	 */
	public void setOrder_by(String orderBy) {
		_orderBy = orderBy;
	}
	
	/**
	 * ソートキーを取得します。
	 * @return
	 */
	public String getOrder_by() {
		return _orderBy;
	}
	
	/**
	 * 現在時刻を取得します。
	 * @return
	 */
	public long getCurrentTime() {
        // 現在時刻を取得(期限比較用: 分以降は0にリセット)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long currentTime = calendar.getTimeInMillis();
        return currentTime;
	}

	@Override
	public User getCurrentUser() {
		return super.getCurrentUser();
	}
	
	/**
	 * 実行します。
	 */
	public String show() {
		_items = _repository.queryItems(null, _orderBy);
		
		return "success";
	}

}
