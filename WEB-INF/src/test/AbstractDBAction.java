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
 * DB�A�N�Z�X����A�N�V�����̒��ۃN���X�B
 */
public abstract class AbstractDBAction extends ActionSupport implements
		SessionAware {

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���݂̃��[�U��ێ�����Z�b�V�����ϐ��������܂��B
	 */
	protected static final String SESSION_CURRENT_USER = "currentUser";

	/**
	 * �G���[���b�Z�[�W��ێ����܂��B
	 */
	private String _errorMessage;

	/**
	 * �Z�b�V��������ێ����܂��B
	 */
	private Map<String, Object> _session;

	/**
	 * EntityManager��ێ����܂��B
	 */
	private EntityManager _entityManager;

	/**
	 * �\�z���܂��B
	 */
	public AbstractDBAction() {
		_errorMessage = null;
		_session = null;
		_entityManager = null;
	}

	/**
	 * �G���[���b�Z�[�W���擾���܂��B
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		return _errorMessage;
	}

	/**
	 * �Z�b�V��������ۑ����܂��B
	 */
	public void setSession(Map<String, Object> session) {
		_session = session;
	}

	/**
	 * ���݂̃��[�U���擾���܂��B
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return (User) _session.get(SESSION_CURRENT_USER);
	}

	/**
	 * ���݂̃��[�U��ݒ肵�܂��B
	 * 
	 * @param user
	 */
	protected void setCurrentUser(User user) {
		_session.put(SESSION_CURRENT_USER, user);
	}

	/**
	 * �G���[��\�����܂��B
	 * 
	 * @param errorMessage
	 * @return
	 */
	protected String showError(String errorMessage) {
		_errorMessage = errorMessage;

		return "error";
	}

	/**
	 * EntityManager���擾���܂��B
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
	 * �A�C�e�����擾���܂��B
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
