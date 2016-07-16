package test;

import javax.servlet.ServletException;

import com.google.inject.Inject;

/**
 * ���O�C�������B
 */
public class LoginAction extends AbstractDBAction {

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;

	/**
	 * ���[�UID��ێ����܂��B
	 */
	private String _userID;

	/**
	 * �p�X���[�h��ێ����܂��B
	 */
	private String _password;

	/**
	 * �\�z���܂��B
	 */
	@Inject
	public LoginAction(Repository repository) {
		_repository = repository;
		_userID = null;
		_password = null;
	}

	/**
	 * ���[�UID��ݒ肵�܂��B
	 * 
	 * @param userId
	 */
	public void setUser_id(String userID) {
		_userID = userID;
	}

	/**
	 * �p�X���[�h��ݒ肵�܂��B
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * ���s���܂��B
	 */
	public String show() {
		return "success";
	}

	/**
	 * ���s���܂��B
	 */
	public String execute() {
		User user = queryUser();
		if (user == null) {
			return showError("���[�UID�������̓p�X���[�h���s���ł��B");
		}

		setCurrentUser(user);
		return "success";
	}

	/**
	 * ���[�U���擾���܂��B
	 * 
	 * @return
	 * @throws ServletException
	 */
	private User queryUser() {
		// �p�����[�^�̃`�F�b�N
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
	 * �L���ȃ��[�UID���ǂ����𔻒肵�܂��B
	 * 
	 * @param name
	 * @return
	 */
	private boolean isValidUserID(String name) {
		return isAlphaOrDigit(name);
	}

	/**
	 * �L���ȃp�X���[�h���ǂ����𔻒肵�܂��B
	 * 
	 * @param password
	 * @return
	 */
	private boolean isValidPassword(String password) {
		return isAlphaOrDigit(password);
	}

	/**
	 * �����񂪔��p�p��������\������Ă��邩�ǂ����𔻒肵�܂��B
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
	 * ���������p�p�������ǂ����𔻒肵�܂��B
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
