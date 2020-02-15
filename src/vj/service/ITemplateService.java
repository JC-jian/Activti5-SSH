package vj.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import vj.domain.Template;

public interface ITemplateService {

	List<Template> findList();

	void save(Template template);

	void delById(Integer id);

	Template findById(Integer id);

	void editTemplate(File resource, Template template);

	InputStream findDocById(Integer id);

}
