package test;

import java.sql.Date;
import java.util.Calendar;

/**
 * �ǉ��������Ȃ��A�N�V�����ł��B
 */
public class AddAction extends AbstractDBAction {

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���[�U��ێ����܂��B
	 */
	private User[] _users;

	/**
	 * �J�����_�[��ێ����܂��B
	 */
	private Calendar _calendar;

	/**
	 * ��ƍ��ږ���ێ����܂��B
	 */
	private String _name;

	/**
	 * ���[�UID��ێ����܂��B
	 */
	private String _userID;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;

	/**
	 * �\�z���܂��B
	 * @param repository
	 */
	@com.google.inject.Inject
	public AddAction(Repository repository) {
		_repository = repository;
		_users = null;
		_name = null;
		_userID = null;
		_calendar = Calendar.getInstance();
		_calendar.clear();
	}

	/**
	 * �ǉ���ʂ�\�����܂��B
	 * 
	 * @return
	 */
	public String show() {
		_users = _repository.queryUsers();
		_calendar = Calendar.getInstance();

		return "success";
	}

	/**
	 * ���s���܂��B
	 * 
	 * @return
	 */
	public String execute() {
		Item targetItem = new Item();
		User user = _repository.queryUser(_userID);
		if (user == null) {
			return showError("�s���ȃp�����[�^�ł��B");
		}
		targetItem.setUser(user);
		Date expireDate = new Date(_calendar.getTimeInMillis());
		targetItem.setExpireDate(expireDate);
		targetItem.setName(_name);

		try{
			_repository.insertItem(targetItem);

			return "success";
		}catch(ModifyException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * ���[�UID��ݒ肵�܂��B
	 * 
	 * @param id
	 */
	public void setUser_id(String id) {
		_userID = id;
	}

	/**
	 * ���O��ێ����܂��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * ���[�U�ꗗ���擾���܂��B
	 * 
	 * @return
	 */
	public User[] getUsers() {
		return _users;
	}

	/**
	 * �N���擾���܂��B
	 * 
	 * @return
	 */
	public int getYear() {
		return _calendar.get(Calendar.YEAR);
	}

	/**
	 * �����擾���܂��B
	 * 
	 * @return
	 */
	public int getMonth() {
		return _calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * �����擾���܂��B
	 * 
	 * @return
	 */
	public int getDay() {
		return _calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * �N��ݒ肵�܂��B
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		_calendar.set(Calendar.YEAR, year);
	}

	/**
	 * ����ݒ肵�܂��B
	 * 
	 * @param year
	 */
	public void setMonth(int month) {
		_calendar.set(Calendar.MONTH, month - 1);
	}

	/**
	 * ����ݒ肵�܂��B
	 * 
	 * @param day
	 */
	public void setDay(int day) {
		_calendar.set(Calendar.DAY_OF_MONTH, day);
	}
}
