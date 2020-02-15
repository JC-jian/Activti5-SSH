package vj.dao;

import java.util.List;

import vj.domain.Template;

public interface ITemplateDao {

	List<Template> findList();

	void save(Template template);


	Template findById(Integer id);

	void del(Template template);

	void updata(Template tp);

}
