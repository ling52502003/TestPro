package test;

import java.sql.Date;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * �A�C�e������ێ����܂��B
 */
@javax.persistence.Entity
@javax.persistence.Table(name="TODO_ITEM")
public class Item {

	/**
	 * ID��ێ����܂��B
	 */
	private String _id;
	
	/**
	 * ���O��ێ����܂��B
	 */
	private String _name;
	
	/**
	 * �S���҂�ێ����܂��B
	 */
	private User _user;
	
	/**
	 * ������ێ����܂��B
	 */
	private Date _expireDate;
	
	/**
	 * �I������������ێ����܂��B
	 */
	private Date _finishedDate;
	
	
	
	/**
	 * �\�z���܂��B
	 */
	public Item() {
		_id = null;
		_name = null;
		_user = null;
		_expireDate = null;
		_finishedDate = null;
	}
	
	/**
	 * ID���擾���܂��B
	 * @return
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name="ID")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public String getId() {
		return _id;
	}
	
	/**
	 * ID��ݒ肵�܂��B
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}
	
	/**
	 * ���O���擾���܂��B
	 * @return
	 */
	@javax.persistence.Column(name="NAME")
	public String getName() {
		return _name;
	}

	/**
	 * ���O��ݒ肵�܂��B
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * ���[�U���擾���܂��B
	 * @return
	 */
    @javax.persistence.ManyToOne(targetEntity = User.class)
    @javax.persistence.JoinColumn(name = "USER",
            referencedColumnName = "ID")
    @NotFound(action=NotFoundAction.IGNORE)
	public User getUser() {
		return _user;
	}

	/**
	 * ���[�U��ݒ肵�܂��B
	 * @param user
	 */
	public void setUser(User user) {
		_user = user;
	}
	
	/**
	 * �������擾���܂��B
	 * @return
	 */
	@javax.persistence.Column(name="EXPIRE_DATE")
	public Date getExpireDate() {
		return _expireDate;
	}
	
	/**
	 * ������ݒ肵�܂��B
	 * @param expireDate
	 */
	public void setExpireDate(Date expireDate) {
		_expireDate = expireDate;
	}
	
	/**
	 * �I���������擾���܂��B
	 * @return
	 */
	@javax.persistence.Column(name="FINISHED_DATE")
	public Date getFinishedDate() {
		return _finishedDate;
	}
	
	/**
	 * �I��������ݒ肵�܂��B
	 * @param finishedDate
	 */
	public void setFinishedDate(Date finishedDate) {
		_finishedDate = finishedDate;
	}
}
