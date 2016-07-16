package test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * DBアクセスするアクションの抽象クラス。
 */
public abstract class AbstractDBAction extends ActionSupport implements
		SessionAware {

	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 現在のユーザを保持するセッション変数を示します。
	 */
	protected static final String SESSION_CURRENT_USER = "currentUser";

	/**
	 * エラーメッセージを保持します。
	 */
	private String _errorMessage;

	/**
	 * セッション情報を保持します。
	 */
	private Map<String, Object> _session;

	/**
	 * EntityManagerを保持します。
	 */
	private EntityManager _entityManager;

	/**
	 * 構築します。
	 */
	public AbstractDBAction() {
		_errorMessage = null;
		_session = null;
		_entityManager = null;
	}

	/**
	 * エラーメッセージを取得します。
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		return _errorMessage;
	}

	/**
	 * セッション情報を保存します。
	 */
	public void setSession(Map<String, Object> session) {
		_session = session;
	}

	/**
	 * 現在のユーザを取得します。
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return (User) _session.get(SESSION_CURRENT_USER);
	}

	/**
	 * 現在のユーザを設定します。
	 * 
	 * @param user
	 */
	protected void setCurrentUser(User user) {
		_session.put(SESSION_CURRENT_USER, user);
	}

	/**
	 * エラーを表示します。
	 * 
	 * @param errorMessage
	 * @return
	 */
	protected String showError(String errorMessage) {
		_errorMessage = errorMessage;

		return "error";
	}

	/**
	 * EntityManagerを取得します。
	 * 
	 * @return
	 */
	protected EntityManager getEntityManager() {
		if (_entityManager == null) {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("sample");
			_entityManager = factory.createEntityManager();
		}
		return _entityManager;
	}

	/**
	 * アイテムを取得します。
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	protected Item queryItem(String id) {
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("SELECT item FROM Item item WHERE item.id='" + id + "'");
		List items = query.getResultList();
		if(items.size() == 0) {
			return null;
		}
		return (Item) items.get(0);
	}

}
