package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.form.UserForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;
import jp.co.internous.ecsite.model.mapper.TblPurchaseMapper;

@Controller
@RequestMapping("/ecsite")
public class IndexController {

	@Autowired
	private MstGoodsMapper goodsMapper;

	@Autowired
	private MstUserMapper userMapper;

	@Autowired
	private TblPurchaseMapper purchaseMapper;

	private Gson gson = new Gson();

	@RequestMapping("/")
	public String index(Model model) {
		List<MstGoods> goods = goodsMapper.findAll();
		model.addAttribute("goods", goods);

		return "index";
	}

	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm f) {
		MstUser user = userMapper.findByUserNameAndPassword(f);

		if (user == null) {
			user = new MstUser();
			user.setFullName("ゲスト");
		}

		return gson.toJson(user);
	}

	@RequestMapping("/singUp")
	public String singUp() {
		return "singup";

	}

	@PostMapping("/register")
	public String register(UserForm userForm,Model model) {
		
		MstUser newUser = userMapper.findByUser(userForm);
		
		if(newUser != null) {
			model.addAttribute("errMessage","このユーザー名はすでに使用されています。");
			model.addAttribute("userName",userForm.getUserName());
			model.addAttribute("fullName",userForm.getFullName());
			model.addAttribute("password",userForm.getPassword());
			return "forward:/ecsite/singUp";
		}
		
		MstUser user = new MstUser();
		
		user.setUserName(userForm.getUserName());
		user.setFullName(userForm.getFullName());
		user.setPassword(userForm.getPassword());
		
		if(userForm.getUserName().equals("") || userForm.getFullName().equals("") || userForm.getPassword().equals("")) {
			model.addAttribute("errMessage","全ての項目を入力してください。");
			model.addAttribute("userName",userForm.getUserName());
			model.addAttribute("fullName",userForm.getFullName());
			model.addAttribute("password",userForm.getPassword());
			return "forward:/ecsite/singUp";
		}
		
		userMapper.insert(user);

		return "forward:/ecsite/";
	}

	@ResponseBody
	@PostMapping("/api/purchase")
	public int purchaseApi(@RequestBody CartForm f) {

		f.getCartList().forEach((c) -> {
			int total = c.getPrice() * c.getCount();
			purchaseMapper.insert(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});
		return f.getCartList().size();
	}

	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm f) {
		int userId = f.getUserId();
		List<HistoryDto> history = purchaseMapper.findHistory(userId);

		return gson.toJson(history);
	}
}
