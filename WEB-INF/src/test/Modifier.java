package test;

/**
 * �A�C�e���̍X�V���W�b�N�̃C���^�t�F�[�X�ł��B
 */
public interface Modifier {

	/**
	 * �A�C�e�����X�V���܂��B
	 * @param item
	 * @throws ModifyException
	 */
	public void modifyItem(Item item)
		throws ModifyException;
	
}
