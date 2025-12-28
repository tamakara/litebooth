package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.stock.StockCreateDTO;
import com.tamakara.litebooth.domain.dto.stock.StockUpdateDTO;
import com.tamakara.litebooth.domain.entity.Stock;
import com.tamakara.litebooth.domain.vo.stock.StockVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StockService extends IService<Stock> {

    List<StockVO> getStockListVO(Long itemId);

    void createStock(StockCreateDTO dto);

    void batchCreateStock(Long itemId, MultipartFile file);

    void updateStock(StockUpdateDTO dto);
}
