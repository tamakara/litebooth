package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.stock.StockCreateDTO;
import com.tamakara.litebooth.domain.dto.stock.StockUpdateDTO;
import com.tamakara.litebooth.domain.entity.Stock;
import com.tamakara.litebooth.domain.vo.stock.StockVO;
import com.tamakara.litebooth.mapper.StockMapper;
import com.tamakara.litebooth.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {
    private final StockMapper stockMapper;

    @Override
    @Transactional(readOnly = true)
    public List<StockVO> getStockListVO(Long itemId) {
        List<Stock> list = stockMapper.selectList(new LambdaQueryWrapper<Stock>().eq(Stock::getItemId, itemId));
        return list.stream().map(stock -> {
            StockVO vo = new StockVO();
            BeanUtils.copyProperties(stock, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createStock(StockCreateDTO dto) {
        Stock stock = new Stock();
        stock.setItemId(dto.getItemId());
        stock.setContent(dto.getContent());
        stock.setIsSold(false);
        stockMapper.insert(stock);
    }

    @Override
    @Transactional
    public void batchCreateStock(Long itemId, MultipartFile file) {
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] items = content.split("=====");
            List<Stock> stocks = new ArrayList<>();
            for (String item : items) {
                if (item.trim().isEmpty()) continue;
                Stock stock = new Stock();
                stock.setItemId(itemId);
                stock.setContent(item.trim());
                stock.setIsSold(false);
                stocks.add(stock);
            }
            stockMapper.insert(stocks);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }

    @Override
    public void updateStock(StockUpdateDTO dto) {
        Stock stock = getById(dto.getId());
        if (stock != null) {
            if (dto.getOrderId() != null) stock.setOrderId(dto.getOrderId());
            if (dto.getContent() != null) stock.setContent(dto.getContent());
            if (dto.getIsSold() != null) stock.setIsSold(dto.getIsSold());
            updateById(stock);
        }
    }
}
