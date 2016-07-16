package test;

/**
 * ���[�U����ێ����܂��B
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TODO_USER")
public class User {

	/**
	 * ID��ێ����܂��B
	 */
	private String _id;

	/**
	 * ���O��ێ����܂��B
	 */
	private String _name;

	/**
	 * �p�X���[�h��ێ����܂��B
	 */
	private String _password;

	/**
	 * �\�z���܂��B
	 */
	public User() {
		_id = null;
		_name = null;
		_password = null;
	}

	/**
	 * ID���擾���܂��B
	 * 
	 * @return
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "ID")
	public String getId() {
		return _id;
	}

	/**
	 * ID��ݒ肵�܂��B
	 * 
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * ���O���擾���܂��B
	 * 
	 * @return
	 */
	@javax.persistence.Column(name = "NAME")
	public String getName() {
		return _name;
	}

	/**
	 * ���O��ݒ肵�܂��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * �p�X���[�h���擾���܂��B
	 * 
	 * @return
	 */
	@javax.persistence.Column(name = "PASSWORD")
	public String getPassword() {
		return _password;
	}

	/**
	 * �p�X���[�h��ݒ肵�܂��B
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		_password = password;
	}
}
