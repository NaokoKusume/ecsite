package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;

@Controller
@RequestMapping("/ecsite")
public class IndexController {

	@Autowired
	private MstGoodsMapper goodsMapper;
	
	@GetMapping("/")
			public String index(Model model){
		List<MstGoods>goods = goodsMapper.findAll();
		model.addAttribute("goods",goods);
		
		return "index";
	}
}
