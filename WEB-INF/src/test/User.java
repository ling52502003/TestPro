package test;

/**
 * ユーザ情報を保持します。
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TODO_USER")
public class User {

	/**
	 * IDを保持します。
	 */
	private String _id;

	/**
	 * 名前を保持します。
	 */
	private String _name;

	/**
	 * パスワードを保持します。
	 */
	private String _password;

	/**
	 * 構築します。
	 */
	public User() {
		_id = null;
		_name = null;
		_password = null;
	}

	/**
	 * IDを取得します。
	 * 
	 * @return
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "ID")
	public String getId() {
		return _id;
	}

	/**
	 * IDを設定します。
	 * 
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * 名前を取得します。
	 * 
	 * @return
	 */
	@javax.persistence.Column(name = "NAME")
	public String getName() {
		return _name;
	}

	/**
	 * 名前を設定します。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * パスワードを取得します。
	 * 
	 * @return
	 */
	@javax.persistence.Column(name = "PASSWORD")
	public String getPassword() {
		return _password;
	}

	/**
	 * パスワードを設定します。
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		_password = password;
	}
}
