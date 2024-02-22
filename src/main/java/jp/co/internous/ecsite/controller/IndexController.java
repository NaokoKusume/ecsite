package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;

@Controller
@RequestMapping("/ecsite")
public class IndexController {

	@Autowired
	private MstGoodsMapper goodsMapper;
	
	@Autowired
	private MstUserMapper userMapper;
	
	private Gson gson = new Gson();
	
	@GetMapping("/")
			public String index(Model model){
		List<MstGoods>goods = goodsMapper.findAll();
		model.addAttribute("goods",goods);
		
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm f) {
		MstUser user = userMapper.findByUserNameAndPassword(f);
		
		if(user == null) {
			user = new MstUser();
			user.setFullName("ゲスト");
		}
		
		return gson.toJson(user);
	}
}
