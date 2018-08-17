package com.veitch.ssmlucene.service.impl;



import com.veitch.ssmlucene.mapper.LuceneMapper;
import com.veitch.ssmlucene.pojo.Lucene;
import com.veitch.ssmlucene.pojo.LuceneExample;
import com.veitch.ssmlucene.service.LuceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuceneServiceImpl implements LuceneService {

    @Autowired
    private LuceneMapper luceneMapper;

    @Override
    public List<Lucene> selectAll() {
        LuceneExample example = new LuceneExample();
        example.setOrderByClause("id asc");
        return luceneMapper.selectByExample(example);
    }
}
