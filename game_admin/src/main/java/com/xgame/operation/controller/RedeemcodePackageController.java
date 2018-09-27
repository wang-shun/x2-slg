package com.xgame.operation.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xgame.base.controller.BaseController;
import com.xgame.common.AppConfig;
import com.xgame.common.Pagination;
import com.xgame.operation.entity.Redeemcode;
import com.xgame.operation.entity.RedeemcodePackage;
import com.xgame.operation.service.IRedeemcodePackageService;
import com.xgame.operation.service.IRedeemcodeService;
import com.xgame.util.RandomGenerator;
import com.xgame.util.aop.SystemControllerLog;

@Controller
@RequestMapping("/operation/redeemcode")
public class RedeemcodePackageController extends BaseController {
	
	@Resource
	private IRedeemcodePackageService redeemcodePackageService;
	@Resource
	private IRedeemcodeService redeemcodeService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="查看兑换码记录")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/redeemcode/list");
		return mav;
	}
	
	/**
	 * 异步加载表格数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/json",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="加载兑换码记录")
	public @ResponseBody JSON json(HttpServletRequest request,HttpServletResponse response){
		String page =request.getParameter("page");
		String rows =request.getParameter("rows");
		Map<String,Object> params = new  HashMap<String,Object>();
		String packageName = request.getParameter("packageName");
		String channel = request.getParameter("channel");
		String startDt = request.getParameter("startDt");
		String endDt = request.getParameter("endDt");
		params.put("packageName", packageName);
		params.put("channel", channel);
		params.put("startDt", startDt);
		params.put("endDt", endDt);
		Pagination pagination = redeemcodePackageService.getPage(Integer.parseInt(page), Integer.parseInt(rows), params);
		//date类型处理
		JSONArray jsonArr = new JSONArray();
		for(Object obj : pagination.getList()){
			jsonArr.add(JSON.parse(JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat)));
		}
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("total", pagination.getTotal());
		jsonMap.put("rows", jsonArr);
		return new JSONObject(jsonMap);
	}
	
	@RequestMapping(value="/add",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="兑换码礼包新增")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/redeemcode/add");
		RedeemcodePackage redeemcodePackage = new RedeemcodePackage();
		mav.addObject("redeemcodePackage", redeemcodePackage);
		return mav;
	}
	
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="兑换码礼包保存")
	public @ResponseBody JSON save(HttpServletRequest request,HttpServletResponse response,RedeemcodePackage redeemcodePackage){
		String packageTranslate = request.getParameter("packageTranslate");
		Set<String> codes = RandomGenerator.generateRedeemCode(redeemcodePackage.getNum(), 12);
		List<Redeemcode> redeemcodeList = new ArrayList<Redeemcode>();
		for(String code : codes){
			Redeemcode redeemcode = new Redeemcode();
			redeemcode.setRedeemcode(code);
			redeemcode.setStatus("N");
			redeemcodeList.add(redeemcode);
		}
		redeemcodePackage.setCreator(getUser().getUsername());
		redeemcodePackage.setCreateDate(new Date());
		redeemcodePackage.setCreatorId(getUser().getId());
		redeemcodePackage.setCreatorRealname(getUser().getRealname());
		redeemcodePackageService.save(redeemcodePackage,redeemcodeList);
		generateRedeemcodeFile(redeemcodePackage, packageTranslate, codes);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("success", true);
		jsonMap.put("message", "保存成功");
		return new JSONObject(jsonMap);
	}
	
	@RequestMapping(value="/updatestatus",method={RequestMethod.GET,RequestMethod.POST})
	@SystemControllerLog(description="更新兑换码状态")
	public @ResponseBody JSON updateStatus(HttpServletRequest request,HttpServletResponse response,String id){
		redeemcodeService.updateStatusByPackageId(id);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("success", true);
		jsonMap.put("message", "修改成功");
		return new JSONObject(jsonMap);
	}
	
	private void generateRedeemcodeFile(RedeemcodePackage redeemcodePackage,String packageTranslate,Set<String> codes){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = sdf.format(new Date());
		String fileName = redeemcodePackage.getPackageName();
		String dirStr = AppConfig.getProperty("redeemcode.location.url")+getUser().getUsername()+"\\"+dateStr+"\\";
		File fileDir = new File(dirStr);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		String fileUrl = AppConfig.getProperty("redeemcode.location.url")+getUser().getUsername()+"\\"+dateStr+"\\"+fileName+".txt";
		File file = new File(fileUrl);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write("礼包备注："+redeemcodePackage.getPackageName()+"\n");
			writer.write("渠道："+redeemcodePackage.getChannel()+"\n");
			writer.write("有效时间："+sdf2.format(redeemcodePackage.getStartDate())+"-"+sdf2.format(redeemcodePackage.getEndDate())+"\n");
			writer.write("礼包内容："+packageTranslate+"\n");
			writer.write("数量："+redeemcodePackage.getNum()+"\n");
			writer.write("#####################################################\n");
			for(String code :codes){
				writer.write(code+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
