package com.xgame.operation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="redeemcode")
public class Redeemcode {
	
	private String id;
	private String redeemcode;
	private String status;
	private RedeemcodePackage redeemcodePackage;
	
	@Column(name="redeemcode",length=12)
	public String getRedeemcode() {
		return redeemcode;
	}
	public void setRedeemcode(String redeemcode) {
		this.redeemcode = redeemcode;
	}
	
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
	
	@Column(name="status",length=2)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="package_id")
	public RedeemcodePackage getRedeemcodePackage() {
		return redeemcodePackage;
	}
	public void setRedeemcodePackage(RedeemcodePackage redeemcodePackage) {
		this.redeemcodePackage = redeemcodePackage;
	}
	
	
	
}
