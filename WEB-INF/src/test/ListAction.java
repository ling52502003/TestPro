package test;

import java.util.Calendar;

import com.google.inject.Inject;

/**
 * �ꗗ��\������A�N�V�����B
 */
public class ListAction extends AbstractDBAction {

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �A�C�e����ێ����܂��B
	 */
	private Item[] _items;
	
	/**
	 * �\�[�g�L�[��ێ����܂��B
	 */
	private String _orderBy;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;
	
	/**
	 * �\�z���܂��B
	 * @param repository
	 */
	@Inject
	public ListAction(Repository repository) {
		_repository = repository;
		_orderBy = null;
		_items = null;
	}
	
	/**
	 * �A�C�e���̈ꗗ���擾���܂��B
	 * @return
	 */
	public Item[] getItems() {
		return _items;
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
		_items = _repository.queryItems(null, _orderBy);
		
		return "success";
	}

}
