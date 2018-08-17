package com.veitch.ssmlucene.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.veitch.ssmlucene.pojo.Lucene;
import com.veitch.ssmlucene.service.LuceneService;
import com.veitch.ssmlucene.util.LuceneIndex;
import com.veitch.ssmlucene.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class LuceneController {

    @Autowired
    LuceneService luceneService;

    @RequestMapping("list")
    public String list(Model model, Page page) throws Exception {
        PageHelper.offsetPage(page.getStart(), page.getCount());

        List<Lucene> list = luceneService.selectAll();

        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);

        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return "list";
    }

    @RequestMapping("select")
    public String select(Model model, String keyword) throws Exception {
        LuceneIndex luceneIndex = new LuceneIndex();
        List<Lucene> selectList = luceneIndex.select(keyword);
        model.addAttribute("list", selectList);

        return "select";
    }

    @RequestMapping("index")
    public String index() throws Exception {
        List<Lucene> list = luceneService.selectAll();
        LuceneIndex luceneIndex = new LuceneIndex();
        luceneIndex.addListIndex(list);
        return "redirect:/list";
    }

}
