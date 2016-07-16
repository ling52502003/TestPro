package test;

/**
 * アイテムの更新ロジックのインタフェースです。
 */
public interface Modifier {

	/**
	 * アイテムを更新します。
	 * @param item
	 * @throws ModifyException
	 */
	public void modifyItem(Item item)
		throws ModifyException;
	
}
