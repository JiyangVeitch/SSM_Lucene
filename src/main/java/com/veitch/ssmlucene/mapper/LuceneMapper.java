package com.veitch.ssmlucene.mapper;

import com.veitch.ssmlucene.pojo.Lucene;
import com.veitch.ssmlucene.pojo.LuceneExample;
import java.util.List;

public interface LuceneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lucene record);

    int insertSelective(Lucene record);

    List<Lucene> selectByExample(LuceneExample example);

    Lucene selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lucene record);

    int updateByPrimaryKey(Lucene record);
}