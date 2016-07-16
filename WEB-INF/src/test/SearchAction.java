package test;

import java.util.Calendar;

import com.google.inject.Inject;

/**
 * �����������Ȃ��A�N�V�����B
 */
public class SearchAction extends AbstractDBAction {

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * �A�C�e����ێ����܂��B
	 */
	private Item[] _items;

	/**
	 * �L�[���[�h��ێ����܂��B
	 */
	private String _keyword;
	
	/**
	 * ������ێ����܂��B
	 */
	private String _orderBy;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;

	/**
	 * �\�z���܂��B
	 */
	@Inject
	public SearchAction(Repository repository) {
		_repository = repository;
		_items = null;
		_keyword = null;
	}

	/**
	 * �A�C�e���̈ꗗ���擾���܂��B
	 * 
	 * @return
	 */
	public Item[] getItems() {
		return _items;
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
	 * �\�[�g�L�[��ێ����܂��B
	 * @param orderBy
	 */
	public void setOrder_by(String orderBy) {
		_orderBy = orderBy;
	}
	
	/**
	 * �\�[�g�L�[���擾���܂��B
	 * @return
	 */
	public String getOrder_by() {
		return _orderBy;
	}

	/**
	 * ���ݎ������擾���܂��B
	 * 
	 * @return
	 */
	public long getCurrentTime() {
		// ���ݎ������擾(������r�p: ���ȍ~��0�Ƀ��Z�b�g)
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long currentTime = calendar.getTimeInMillis();
		return currentTime;
	}

	@Override
	public User getCurrentUser() {
		return super.getCurrentUser();
	}

	/**
	 * ���s���܂��B
	 */
	public String show() {
		_items = _repository.queryItems(_keyword, _orderBy);

		return "success";
	}

}
