package test;

import javax.servlet.ServletException;

import test.db.DBRepository;

import com.google.inject.ImplementedBy;

/**
 * �f�[�^��~�ς��郊�|�W�g���̃C���^�t�F�[�X�B
 */
@ImplementedBy(DBRepository.class)
public interface Repository {

	/**
	 * �L���ȃ��[�U�I�u�W�F�N�g���擾���܂��B
	 * @param id
	 * @param password
	 * @return
	 */
	public User queryValidUser(String id, String password);
	
	/**
	 * ���[�U���擾���܂��B
	 * 
	 * @param userID
	 * @return
	 * @throws ServletException
	 */
	public User queryUser(String userID);
	
	/**
	 * ���[�U�ꗗ���擾���܂��B
	 * 
	 * @return
	 * @throws ServletException
	 */
	public User[] queryUsers();
	
	/**
	 * �A�C�e���̈ꗗ���擾���܂��B
	 * @param keywords
	 * 	�����L�[���[�h
	 * @param orderBy
	 *  �\�[�g�L�[
	 * @return
	 */
	public Item[] queryItems(String keywords, String orderBy);
	
	/**
	 * �A�C�e�����擾���܂��B
	 * @param id
	 * @return
	 */
	public Item queryItem(String id);
	
	/**
	 * �A�C�e�����폜���܂��B
	 * @param id
	 */
	public void deleteItem(String id)
		throws ModifyException;
	
	/**
	 * �A�C�e����ǉ����܂��B
	 * @param item
	 */
	public void insertItem(Item item)
		throws ModifyException;
	
	/**
	 * �A�C�e�����X�V���܂��B
	 * @param id
	 * @param modifier
	 * @return ���������true�B���s�����false�B
	 */
	public void updateItem(String id, Modifier modifier)
		throws ModifyException;
	
}
