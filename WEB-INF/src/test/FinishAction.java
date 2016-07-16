package test;

import java.sql.Date;
import java.util.Calendar;

import com.google.inject.Inject;

/**
 * ����/�������ؑւ������Ȃ��A�N�V�����B
 */
public class FinishAction extends AbstractDBAction
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
	 * �L�[���[�h��ێ����܂��B
	 */
	private String _keyword;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;

	/**
	 * �\�z���܂��B
	 */
	@Inject
	public FinishAction(Repository repository) {
		_repository = repository;
		_itemID = null;
		_keyword = null;
	}

	/**
	 * �A�C�e����ID��ݒ肵�܂��B
	 * 
	 * @param id
	 * @return
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * �L�[���[�h��ݒ肵�܂��B
	 * 
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	/**
	 * �L�[���[�h���擾���܂��B
	 * 
	 * @return
	 */
	public String getKeyword() {
		return _keyword;
	}

	/**
	 * ���s���܂��B
	 * 
	 * @return
	 */
	public String execute() {
		try {
			_repository.updateItem(_itemID, this);

			if (_keyword != null) {
				return "search";
			} else {
				return "list";
			}
		} catch(ModifyException e) {
			return showError(e.getMessage());
		}
	}

	/**
	 * �A�C�e�����X�V���܂��B
	 */
	public void modifyItem(Item item) throws ModifyException {
		if (item.getFinishedDate() == null) {
			// �����ɕύX
			Calendar calendar = Calendar.getInstance();
			item
					.setFinishedDate(new Date(calendar.getTimeInMillis()));
		} else {
			// �������ɕύX
			item.setFinishedDate(null);
		}
	}
	
}
