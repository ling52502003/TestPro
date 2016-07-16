package test;

import javax.servlet.ServletException;

import com.google.inject.Inject;

/**
 * ログイン処理。
 */
public class LoginAction extends AbstractDBAction {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * リポジトリを保持します。
	 */
	private Repository _repository;

	/**
	 * ユーザIDを保持します。
	 */
	private String _userID;

	/**
	 * パスワードを保持します。
	 */
	private String _password;

	/**
	 * 構築します。
	 */
	@Inject
	public LoginAction(Repository repository) {
		_repository = repository;
		_userID = null;
		_password = null;
	}

	/**
	 * ユーザIDを設定します。
	 * 
	 * @param userId
	 */
	public void setUser_id(String userID) {
		_userID = userID;
	}

	/**
	 * パスワードを設定します。
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * 実行します。
	 */
	public String show() {
		return "success";
	}

	/**
	 * 実行します。
	 */
	public String execute() {
		User user = queryUser();
		if (user == null) {
			return showError("ユーザIDもしくはパスワードが不正です。");
		}

		setCurrentUser(user);
		return "success";
	}

	/**
	 * ユーザを取得します。
	 * 
	 * @return
	 * @throws ServletException
	 */
	private User queryUser() {
		// パラメータのチェック
		if (_userID == null) {
			return null;
		}
		if (_password == null) {
			return null;
		}
		if (isValidUserID(_userID) == false) {
			return null;
		}
		if (isValidPassword(_password) == false) {
			return null;
		}

		return _repository.queryValidUser(_userID, _password);
	}

	/**
	 * 有効なユーザIDかどうかを判定します。
	 * 
	 * @param name
	 * @return
	 */
	private boolean isValidUserID(String name) {
		return isAlphaOrDigit(name);
	}

	/**
	 * 有効なパスワードかどうかを判定します。
	 * 
	 * @param password
	 * @return
	 */
	private boolean isValidPassword(String password) {
		return isAlphaOrDigit(password);
	}

	/**
	 * 文字列が半角英数字から構成されているかどうかを判定します。
	 * 
	 * @param str
	 * @return
	 */
	private boolean isAlphaOrDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isAlphaOrDigit(ch) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字が半角英数字かどうかを判定します。
	 * 
	 * @param ch
	 * @return
	 */
	private boolean isAlphaOrDigit(char ch) {
		if ('A' <= ch && ch <= 'Z') {
			return true;
		}
		if ('a' <= ch && ch <= 'z') {
			return true;
		}
		if ('0' <= ch && ch <= '9') {
			return true;
		}
		return false;
	}

}
