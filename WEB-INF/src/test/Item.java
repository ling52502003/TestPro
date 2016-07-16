package test;

import java.sql.Date;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * アイテム情報を保持します。
 */
@javax.persistence.Entity
@javax.persistence.Table(name="TODO_ITEM")
public class Item {

	/**
	 * IDを保持します。
	 */
	private String _id;
	
	/**
	 * 名前を保持します。
	 */
	private String _name;
	
	/**
	 * 担当者を保持します。
	 */
	private User _user;
	
	/**
	 * 期限を保持します。
	 */
	private Date _expireDate;
	
	/**
	 * 終了した日時を保持します。
	 */
	private Date _finishedDate;
	
	
	
	/**
	 * 構築します。
	 */
	public Item() {
		_id = null;
		_name = null;
		_user = null;
		_expireDate = null;
		_finishedDate = null;
	}
	
	/**
	 * IDを取得します。
	 * @return
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name="ID")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public String getId() {
		return _id;
	}
	
	/**
	 * IDを設定します。
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}
	
	/**
	 * 名前を取得します。
	 * @return
	 */
	@javax.persistence.Column(name="NAME")
	public String getName() {
		return _name;
	}

	/**
	 * 名前を設定します。
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * ユーザを取得します。
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
	 * ユーザを設定します。
	 * @param user
	 */
	public void setUser(User user) {
		_user = user;
	}
	
	/**
	 * 期限を取得します。
	 * @return
	 */
	@javax.persistence.Column(name="EXPIRE_DATE")
	public Date getExpireDate() {
		return _expireDate;
	}
	
	/**
	 * 期限を設定します。
	 * @param expireDate
	 */
	public void setExpireDate(Date expireDate) {
		_expireDate = expireDate;
	}
	
	/**
	 * 終了日時を取得します。
	 * @return
	 */
	@javax.persistence.Column(name="FINISHED_DATE")
	public Date getFinishedDate() {
		return _finishedDate;
	}
	
	/**
	 * 終了日時を設定します。
	 * @param finishedDate
	 */
	public void setFinishedDate(Date finishedDate) {
		_finishedDate = finishedDate;
	}
}
