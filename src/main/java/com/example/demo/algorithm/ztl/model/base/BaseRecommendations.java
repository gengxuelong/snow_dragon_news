package com.example.demo.algorithm.ztl.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRecommendations<M extends BaseRecommendations<M>> extends Model<M> implements IBean {

	public void setId(Long id) {
		set("id", id);
	}

	public Long getId() {
		return getLong("id");
	}

	public void setUserId(Long userId) {
		set("user_id", userId);
	}

	public Long getUserId() {
		return getLong("user_id");
	}

	public void setNewsId(Long newsId) {
		set("news_id", newsId);
	}

	public Long getNewsId() {
		return getLong("news_id");
	}

	public void setDeriveTime(java.util.Date deriveTime) {
		set("derive_time", deriveTime);
	}

	public java.util.Date getDeriveTime() {
		return getDate("derive_time");
	}

	public void setFeedback(Boolean feedback) {
		set("feedback", feedback);
	}

	public Boolean getFeedback() {
		return get("feedback");
	}

	public void setDeriveAlgorithm(Integer deriveAlgorithm) {
		set("derive_algorithm", deriveAlgorithm);
	}

	public Integer getDeriveAlgorithm() {
		return getInt("derive_algorithm");
	}
	
}

