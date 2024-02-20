package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;

@Controller
@RequestMapping("/ecsite/admin")

public class AdminController {
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private MstGoodsMapper goodsMapper;
	
	@RequestMapping("/")
	public String index() {
		return "admintop";
	}
	
	@PostMapping("/welcome")
	public String welcome(LoginForm form,Model model) {
		
		MstUser user = userMapper.findByUserNameAndPassword(form);
		
		if(user.getIsAdmin() == 0) {
			model.addAttribute("errMessage","ユーザー名またはパスワードが違います。");
			return "forward:/ecsite/admin/";
		}
		if(user == null) {
			model.addAttribute("errMessage","管理者ではありません。");
			return "forward:/ecsite/admin/";
		}
		
		List<MstGoods>goods = goodsMapper.findAll();
		model.addAttribute("userName",user.getUserName());
		model.addAttribute("password",user.getPassword());
		model.addAttribute("goods",goods);
		
		return "welcome";
	
	}
	
}
