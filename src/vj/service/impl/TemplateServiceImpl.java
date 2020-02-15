package vj.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vj.dao.ITemplateDao;
import vj.domain.Template;
import vj.service.ITemplateService;
import vj.utils.UploadFileUtils;

@Service
@Transactional
public class TemplateServiceImpl implements ITemplateService {
	@Resource
	private ITemplateDao templateDao;
	
	public List<Template> findList() {
		return templateDao.findList();
	}

	/**
	 * 保存模板对象
	 */
	public void save(Template template) {
		templateDao.save(template);
	}
	
	/**
	 * 根据id删除模板对象及文件
	 */
	public void delById(Integer id) {		
		//删除文件
		Template template =  templateDao.findById(id);
		String docFilePath = template.getDocFilePath();
		File file = new File(docFilePath);
		if(file.exists()){
			file.delete();
		}
		
		//删除记录
		templateDao.del(template);
	}
	
	/**
	 * 根据id查询模板对象
	 */
	public Template findById(Integer id) {
		return templateDao.findById(id);
	}
	
	/**
	 * 修改模板
	 */
	public void editTemplate(File resource, Template template) {
		Template tp = templateDao.findById(template.getId());
		
		tp.setName(template.getName());
		tp.setPdKey(template.getPdKey());
		tp.setPdName(template.getPdName());
		
		//判断上传的临时文件是否为空，为空则保留原来文件
				if(resource != null){
					//上传了新文件，删除旧文件并更新记录
					String docFilePath = tp.getDocFilePath();
					File file =new File(docFilePath);
					if(file.exists()){
						file.delete();
					}
					docFilePath = UploadFileUtils.copy(resource);
					
					tp.setDocFilePath(docFilePath);
					
					templateDao.updata(tp);
				}
				
		
	}

	/**
	 * 根据id查询doc文件返回流
	 */
	public InputStream findDocById(Integer id) {
		InputStream in = null;
		Template tp = templateDao.findById(id);
		 try {
			in = new FileInputStream(new File(tp.getDocFilePath()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}

}
