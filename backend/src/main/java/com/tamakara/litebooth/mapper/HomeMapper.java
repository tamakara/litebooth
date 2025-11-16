package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tamakara.litebooth.domain.entity.HomeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeMapper extends BaseMapper<HomeInfo> {
}
