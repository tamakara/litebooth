package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.stock.StockCreateDTO;
import com.tamakara.litebooth.domain.dto.stock.StockUpdateDTO;
import com.tamakara.litebooth.domain.vo.stock.StockVO;
import com.tamakara.litebooth.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "管理端仓库模块接口")
@RestController
@RequestMapping("/admin/stocks")
@RequiredArgsConstructor
public class AdminStockController {
    private final StockService stockService;

    @GetMapping
    public Result<List<StockVO>> listStocks(
            @RequestParam("itemId") Long itemId
    ) {
        return Result.success(stockService.getStockListVO(itemId));
    }

    @PostMapping
    public Result<Void> createStock(
            @RequestBody @Valid StockCreateDTO dto
    ) {
        stockService.createStock(dto);
        return Result.success();
    }

    @PostMapping("/batch")
    public Result<Void> batchCreateStock(
            @RequestParam("itemId") Long itemId,
            @RequestParam("file") MultipartFile file
    ) {
        stockService.batchCreateStock(itemId, file);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateStock(
            @RequestBody @Valid StockUpdateDTO dto
    ) {
        stockService.updateStock(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteStock(
            @PathVariable("id") Long id
    ) {
        stockService.deleteStock(id);
        return Result.success();
    }
}
