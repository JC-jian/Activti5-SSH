package vj.domain;

import java.io.Serializable;

/**
 * 表单模板实体类
 * @author vjames
 *
 */


public class Template implements Serializable {
	public Integer id;
	public String name;
	public String pdName;
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public String pdKey;
	public String docFilePath;
	@Override
	public String toString() {
		return "Template [id=" + id + ", name=" + name + ", pdName=" + pdName + ", pdKey=" + pdKey + ", docFilePath="
				+ docFilePath + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPdKey() {
		return pdKey;
	}
	public void setPdKey(String pdKey) {
		this.pdKey = pdKey;
	}
	public String getDocFilePath() {
		return docFilePath;
	}
	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
	}
}
