package com.xgame.operation.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="redeemcode_package")
public class RedeemcodePackage {
	
	private String id;
	private String channel;
	private String packageName;
	private String packageDetail;
	private Integer num;
	private Date startDate;
	private Date endDate;
	private String creator;
	private Date createDate;
	private String creatorRealname;
	private String creatorId;
	
	private List<Redeemcode> redeemcodes;
	
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
	
	@Column(name="channel",length=50)
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Column(name="package_name",length=50)
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	@Column(name="package_detail",length=255)
	public String getPackageDetail() {
		return packageDetail;
	}
	public void setPackageDetail(String packageDetail) {
		this.packageDetail = packageDetail;
	}
	
	@Column(name="num",length=11)
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Column(name="start_date")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="end_date",length=50)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="creator",length=50)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="create_date",length=50)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name="creator_realname",length=50)
	public String getCreatorRealname() {
		return creatorRealname;
	}
	public void setCreatorRealname(String creatorRealname) {
		this.creatorRealname = creatorRealname;
	}
	
	@Column(name="creator_id",length=50)
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="redeemcodePackage",cascade={CascadeType.ALL})
	@JSONField(serialize=false)
	public List<Redeemcode> getRedeemcodes() {
		return redeemcodes;
	}
	public void setRedeemcodes(List<Redeemcode> redeemcodes) {
		this.redeemcodes = redeemcodes;
	}
	
}
