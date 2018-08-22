/**
 * 
 */
package com.ys.business.action.album;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ys.business.action.model.common.AlbumInfo;
import com.ys.business.action.model.order.ContactModel;
import com.ys.system.action.common.BaseAction;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.I_BaseService;
import com.ys.system.service.common.I_MultiAlbumService;
import com.ys.util.CommonUtil;
import com.ys.util.UploadReceiver;

/**
 * 
 * @ClassName: AlbumController 
 * @Description: TODO
 * @author MingChuan
 * @date 2016��3��28�� 
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/album")
public class AlbumAction extends BaseAction {
	
	@Autowired
	private  HttpServletRequest request;	
	
	//@Autowired
	//private  ProductService productService;	
	
	@RequestMapping(value = "/album-upload-init")
	public ModelAndView ablumUploadInit(@RequestBody String data, @ModelAttribute("dataModels")ContactModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){	
		AlbumInfo info = new AlbumInfo();
		ModelAndView mv = new ModelAndView("/common/album/album-upload");
		String key = request.getParameter("key");
		String index = request.getParameter("index");
		info.setKey(key);
		info.setInfo(index);
		CommonUtil.mvAddMsg(mv);
		mv.addObject("DisplayData", info);
		
		return mv;
	}	
	
	/**
	 * 
	 * @Title: productAblumUploadPost
	 * @Description: TODO
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/album-upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> albumUploadPost(HttpServletRequest request,HttpServletResponse resp) throws Exception{
		
		Map<String, Object> modelMap = new HashMap<String, Object>(); 
		
		UploadReceiver uploadReceiver = new UploadReceiver();

		uploadReceiver.doPost(request, resp);
		
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * 
	 * @Title: productMediaUploadPost
	 * @Description: TODO
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/media-upload")
	@ResponseBody
	public Map<String, Object> mediaUploadPost(HttpServletRequest request,HttpServletResponse resp) throws Exception{
		
		Map<String, Object> modelMap = new HashMap<String, Object>(); 
		
		UploadReceiver uploadReceiver = new UploadReceiver();
		
		uploadReceiver.doPost(request, resp);
		
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * 
	 * @Title: productAblumPhotoShow
	 * @Description: TODO
	 * @param request
	 * @param src
	 * @return
	 */
	
	@RequestMapping(value = "/album-photo-show")
	public ModelAndView albumPhotoShow(HttpServletRequest request ) {
		
		ModelAndView mv = new ModelAndView("common/album/album-photo-show");

		
		//mv.addObject("msg", OVLoadProperties.getInstance()
		//		.getProperties("0001"));
		String className = request.getParameter("className");
		String fileName = request.getParameter("fileName");
		String key = request.getParameter("key");
		String index = request.getParameter("index");
		String albumCount = request.getParameter("albumCount");
		
		mv.addObject("fileName", fileName);
		
		mv.addObject("path", BusinessConstants.BUSINESSPHOTOPATH);
		
		mv.addObject("key", key);
		
		mv.addObject("className", className);
		
		mv.addObject("albumCount", albumCount);
		
		mv.addObject("index", index);
		
		CommonUtil.mvAddMsg(mv);
			
		return mv;
	}
	
	/**
	 * 
	 * @Title: setNowUseImage
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "/setNowUseImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setNowUseImage(HttpServletRequest request) throws NumberFormatException, Exception {
		
		Map<String, Object> map = new HashMap<String, Object>(1); 		
		
		try{	
			//获得参数
			String key = request.getParameter("key");
			String fileName = request.getParameter("fileName");
			String className = request.getParameter("className");
			String index = request.getParameter("index");
			String albumCount = request.getParameter("albumCount");
			
			if (index != null && !index.equals("")) {
				I_MultiAlbumService mBaseService = (I_MultiAlbumService)Class.forName(className).newInstance();
				mBaseService.setNowUseImage(key, Integer.parseInt(albumCount), Integer.parseInt(index), fileName);
			} else {
				I_BaseService iBaseService = (I_BaseService)Class.forName(className).newInstance();
				iBaseService.setNowUseImage(key, fileName);
			}
		
						
		}catch(Exception e){
			
			// 经过转换的异常
			map.put("retCode", "-100"); 
			map.put("errMsg", e.getMessage()); 
			
			//判断是否是version不一致，已被别的程序修改。
					
			map.put("retValue", "failure"); 	
			return map;
		}				

		map.put("retValue", "success"); 		
		
		return map;
	}

//	@RequestMapping(value = "/edit/{aid}.html", method = RequestMethod.GET)
//	public ModelAndView edit(@PathVariable String aid,
//			HttpServletRequest request) {
//		ModelAndView mv;
//		TContactFactory factoryContact = ContactFactoryService.load(Long.parseLong(aid, 10));
//		
//		mv = new ModelAndView("/buy002-factory-contact/factory-contact-edit");
//		mv.addObject("factoryContact", factoryContact);
//		
//		List<MDict> gerderList = dictionaryService.getDictionary("GENDER", false,false);
//		mv.addObject("genderList", gerderList);
//		
//		List<MDict> positionList = dictionaryService.getDictionary("POSITION", false,false);
//		mv.addObject("positionList", positionList);
//		
//		List<MDict> primaryList = dictionaryService.getDictionary("PRIMARY_FLAG", false,false);
//		mv.addObject("primaryList", primaryList);
//		
//		mv.addObject("msg", OVLoadProperties.getInstance()
//				.getProperties("0001"));
//		
//		return mv;
//	}
//
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(@ModelAttribute("factoryContact") TContactFactory factoryContact,
//			HttpServletRequest request) throws Exception {
//		ContactFactoryService.saveOrUpdate(factoryContact);
//		
//		return "redirect:retrieve.html";
//	}
//	
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String update(@ModelAttribute("factoryContact") TContactFactory factoryContact,
//			HttpServletRequest request) throws Exception {
//		ContactFactoryService.saveOrUpdate(factoryContact);
//		return "redirect:retrieve.html";
//	}
//
//	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
//	public ModelAndView retrieve_H(
//			@ModelAttribute("factoryContact") TContactFactory factoryContact,
//			HttpServletRequest request) {
//		ModelAndView mv;
//		
//		mv = new ModelAndView("buy002-factory-contact/factory-contact-manage");
//		mv.addObject("msg", OVLoadProperties.getInstance()
//				.getProperties("0001"));
//		return mv;
//	}
//
//	@RequestMapping(value = "/retrieve", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> retrieveAjax(HttpServletRequest request) throws Exception{
//		ModelAndView mv;
//						
//		return ContactFactoryService.retrieve(request);  
//	    
//		}
//
	
	/**
	 * 
	 * @Title: imageRemove
	 * @Description: 
	 * @param request
	 * @param boardIds
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "/image-remove", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> imageRemove(HttpServletRequest request) throws NumberFormatException, Exception {

		Map<String, Object> map = new HashMap<String, Object>(1); 
		UploadReceiver uploadReceiver = new UploadReceiver();
		
		//获得参数
		String key = request.getParameter("key");
		String fileName = request.getParameter("fileName");
		String className = request.getParameter("className");
		String index = request.getParameter("index");
		String albumCount = request.getParameter("albumCount");
		String nowImage = "";
		System.out.println("key:"+key+"::fileName:"+fileName);
		if (albumCount != null && !albumCount.equals("")) {
			uploadReceiver.deleteFile(request, key + File.separator + index, fileName);
			I_MultiAlbumService mBaseService = (I_MultiAlbumService)Class.forName(className).newInstance();
			nowImage = mBaseService.getNowUseImage(key, Integer.parseInt(index));
			if (fileName.equals(nowImage)){
				mBaseService.setNowUseImage(key, Integer.parseInt(albumCount), Integer.parseInt(index), "");
			}			
		} else {
			uploadReceiver.deleteFile(request, key, fileName);
			I_BaseService iBaseService = (I_BaseService)Class.forName(className).newInstance();
			nowImage = iBaseService.getNowUseImage(key);
			if (fileName.equals(nowImage)){
				iBaseService.setNowUseImage(key, "");
			}
		}

		map.put("retValue", "success"); 		
		
		return map;
		
		//uploadReceiver.deleteFolder(request, key);
	}
	
	/**
	 * 
	 * @Title: getFirstFilename
	 * @Description: 
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFirstFilename")
	@ResponseBody
	public Map<String, Object> getFirstFilename(HttpServletRequest request) throws NumberFormatException, Exception {
				
		Map<String, Object> modelMap = new HashMap<String, Object>(1); 
		
		String p_dir = request.getParameter("dir");
		String productId = request.getParameter("productId");
		String filename_temp = request.getParameter("filename");
		String image_filename = "";
		
		if (!(filename_temp == null||filename_temp.equals("")) ){ 			
			image_filename = filename_temp;
		}else{			
			//TProduct product = productService.load(Long.parseLong(productId));			
			//image_filename = product.getImageFilename();			
		}
		
		UploadReceiver uploadReceiver = new UploadReceiver();
		
//		if ((null == image_filename || image_filename.equals(""))){
//			modelMap.put("firstFilename", ""); 
//			return modelMap;
//		}
		
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ p_dir + productId + BusinessConstants.BUSINESSSMALLPHOTOPATH;   
		
		//String filename = uploadReceiver.getFirstFileName(dir);
		
		String filename = uploadReceiver.getNowUseFileName(dir,image_filename);
		
		modelMap.put("firstFilename", filename); 
		
		return modelMap;
		
	}
	
	/**
	 * 
	 * @Title: getNowUseFilename
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNowUseFilename")
	@ResponseBody
	public Map<String, Object> getNowUseFilename(HttpServletRequest request) throws NumberFormatException, Exception {
				
		Map<String, Object> modelMap = new HashMap<String, Object>(1); 
		
		String productId = request.getParameter("productId");
		
		//String filename = productService.load(Long.parseLong(productId)).getImageFilename();
		
		//modelMap.put("firstFilename", filename); 
		
		return modelMap;
		
	}
	
	/**
	 * 
	 * @Title: getFirstFilename
	 * @Description: TODO
	 * @param media
	 * @param path
	 * @param id
	 * @return
	 */
	public String getFirstFilename(String media,String path,String id )  {
		
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ media + "/" + path + "/" + id + "/"; 
				
		UploadReceiver uploadReceiver = new UploadReceiver();
		
		return uploadReceiver.getFirstFileName(dir);
		
	}
	
	
}
