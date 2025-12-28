package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.stock.StockCreateDTO;
import com.tamakara.litebooth.domain.dto.stock.StockUpdateDTO;
import com.tamakara.litebooth.domain.vo.stock.StockVO;
import com.tamakara.litebooth.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "管理端仓库模块接口")
@RestController
@RequestMapping("/admin/stock")
@RequiredArgsConstructor
public class AdminStockController {
    private final StockService stockService;

    @GetMapping("/getStockListVO")
    public ResponseEntity<List<StockVO>> getStockListVO(
            @RequestParam("itemId") Long itemId
    ) {
        return ResponseEntity.ok(stockService.getStockListVO(itemId));
    }

    @PostMapping("/createStock")
    public ResponseEntity<Void> createStock(
            @RequestBody StockCreateDTO dto
    ) {
        stockService.createStock(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batchCreateStock")
    public ResponseEntity<Void> batchCreateStock(
            @RequestParam("itemId") Long itemId,
            @RequestParam("file") MultipartFile file
    ) {
        stockService.batchCreateStock(itemId, file);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateStock")
    public ResponseEntity<Void> updateStock(
            @RequestBody StockUpdateDTO dto
    ) {
        stockService.updateStock(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteStock/{id}")
    public ResponseEntity<Void> deleteStock(
            @PathVariable("id") Long id
    ) {
        stockService.removeById(id);
        return ResponseEntity.ok().build();
    }
}
