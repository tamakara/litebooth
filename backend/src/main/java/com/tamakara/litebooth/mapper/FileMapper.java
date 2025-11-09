package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tamakara.litebooth.domain.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {
    default boolean existsByHash(String hash) {
        return exists(new LambdaQueryWrapper<File>()
                .eq(File::getHash, hash)
        );
    }
}
