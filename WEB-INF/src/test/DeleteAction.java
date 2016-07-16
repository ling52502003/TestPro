package test;

import com.google.inject.Inject;

/**
 * �폜�������Ȃ��A�N�V�����B
 */
public class DeleteAction extends AbstractDBAction {

	/**
	 * �V���A���o�[�W����UID�B
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * �A�C�e��ID��ێ����܂��B
	 */
	private String _itemID;

	/**
	 * �A�C�e����ێ����܂��B
	 */
	private Item _item;
	
	/**
	 * ���|�W�g����ێ����܂��B
	 */
	private Repository _repository;

	/**
	 * �\�z���܂��B
	 * @param repository
	 */
	@Inject
	public DeleteAction(Repository repository) {
		_repository = repository;
		_itemID = null;
		_item = null;
	}

	/**
	 * �A�C�e��ID��ݒ肵�܂��B
	 * 
	 * @param id
	 */
	public void setItem_id(String id) {
		_itemID = id;
	}

	/**
	 * �A�C�e��ID���擾���܂��B
	 * 
	 * @return
	 */
	public String getItem_id() {
		return _itemID;
	}

	/**
	 * �A�C�e�����擾���܂��B
	 * 
	 * @return
	 */
	public Item getItem() {
		return _item;
	}

	/**
	 * �ǉ���ʂ�\�����܂��B
	 * 
	 * @return
	 */
	public String show() {
		_item = _repository.queryItem(_itemID);

		return "success";
	}

	/**
	 * ���s���܂��B
	 * 
	 * @return
	 */
	public String execute() {
		try{
			_repository.deleteItem(_itemID);
			
			return "success";
		}catch(ModifyException e) {
			return showError(e.getMessage());
		}
	}
}
