package test;

/**
 * 更新処理における例外です。
 */
public class ModifyException extends Exception {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 構築します。
	 * @param msg
	 */
	public ModifyException(String msg) {
		super(msg);
	}
	
}
