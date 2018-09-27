package com.xgame.gm.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600402046540820008L;
	
	private String id;
	private String authorityName;
	private String authorityCode;
	private String url;
	private int itemNo;
	
	private Authority parent;
	private List<Authority> child = new ArrayList<Authority>(); 
	
	private Set<User> users;
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid")
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="authority_name",length=50)
	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	
	@Column(name="authority_code",length=20)
	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent_id")
	public Authority getParent() {
		return parent;
	}

	public void setParent(Authority parent) {
		this.parent = parent;
	}
	
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="parent",fetch=FetchType.EAGER)
	@OrderBy("itemNo")
	public List<Authority> getChild() {
		return child;
	}

	public void setChild(List<Authority> child) {
		this.child = child;
	}

	@Column(name="url",length=100)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="item_no",length=8)
	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="user_rela_authority",joinColumns={@JoinColumn(name="authority_id",nullable=false,updatable=false)},inverseJoinColumns={@JoinColumn(name="user_id",nullable=false,updatable=false)})
	@Transient
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	@Transient
	public String getAuthority() {
		return this.authorityCode;
	}

}
