package test;

import java.sql.Date;
import java.util.Calendar;

import com.google.inject.Inject;

/**
 * �ҏW�������Ȃ��A�N�V�����B
 */
public class EditAction extends AbstractDBAction
	implements Modifier{

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * �A�C�e��ID��ێ����܂��B
	 */
	private String _itemID;

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
	 * �����������ǂ�����ێ����܂��B
	 */
	private boolean _finished;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;

	/**
	 * �\�z���܂��B
	 * @param repository
	 */
	@Inject
	public EditAction(Repository repository) {
		_repository = repository;
		_users = null;
		_name = null;
		_userID = null;
		_calendar = Calendar.getInstance();
		_calendar.clear();
		_itemID = null;
		_finished = false;
	}

	/**
	 * �A�C�e����ID��ێ����܂��B
	 * 
	 * @param id
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * �A�C�e����ID���擾�v�Ă��B
	 * 
	 * @return
	 */
	public String getItem_id() {
		return _itemID;
	}

	/**
	 * �ǉ���ʂ�\�����܂��B
	 * 
	 * @return
	 */
	public String show() {
		Item currentItem = _repository.queryItem(_itemID);

		_users = _repository.queryUsers();
		_calendar = Calendar.getInstance();
		_calendar.setTime(currentItem.getExpireDate());

		_name = currentItem.getName();
		_userID = currentItem.getUser().getId();
		_finished = (currentItem.getFinishedDate() != null);

		return "success";
	}

	/**
	 * ���s���܂��B
	 * 
	 * @return
	 */
	public String execute() {
		try {
			_repository.updateItem(_itemID, this);

			return "success";
		} catch(ModifyException e) {
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
	 * ���[�UID���擾���܂��B
	 * 
	 * @return
	 */
	public String getUser_id() {
		return _userID;
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
	 * ���O���擾���܂��B
	 * 
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * �����������ǂ�����ݒ肵�܂��B
	 * 
	 * @param finished
	 */
	public void setFinished(String finished) {
		if ("true".equals(finished)) {
			_finished = true;
		} else {
			_finished = false;
		}
	}

	/**
	 * �����������ǂ������擾���܂��B
	 * 
	 * @return
	 */
	public String getFinished() {
		if (_finished) {
			return "true";
		} else {
			return "false";
		}
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

	/**
	 * �A�C�e�����X�V���܂��B
	 */
	public void modifyItem(Item item) throws ModifyException {
		User user = _repository.queryUser(_userID);
		if (user == null) {
			throw new ModifyException("�s���ȃp�����[�^�ł��B");
		}
		item.setUser(user);
		Date expireDate = new Date(_calendar.getTimeInMillis());
		item.setExpireDate(expireDate);
		item.setName(_name);
		if (_finished) {
			if (item.getFinishedDate() == null) {
				Calendar calendar = Calendar.getInstance();
				item.setFinishedDate(new Date(calendar
						.getTimeInMillis()));
			}
		} else {
			item.setFinishedDate(null);
		}
	}
}
