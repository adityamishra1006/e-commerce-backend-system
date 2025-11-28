package com.example.Minor_Project_01.controller;


import com.example.Minor_Project_01.dto.CreateResponseDTO;
import com.example.Minor_Project_01.dto.ProductDTO;
import com.example.Minor_Project_01.dto.ResponseDTO;
import com.example.Minor_Project_01.exception.NotFoundException;
import com.example.Minor_Project_01.services.SellerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SellerController.class);

    @Value("${image.upload.home}")
    private String imageUploadHome;

    @Value("${static.domain.name}")
    private String staticDomainName;

    @Autowired
    private SellerService sellerService;

    @PostMapping("/addProduct")
    public ResponseEntity<CreateResponseDTO> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok((sellerService.createProduct(productDTO)));
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        return ResponseEntity.ok(sellerService.getProducts());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(sellerService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(sellerService.deleteProduct(id));
    }

    @PostMapping("product/image")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file){
        LOGGER.info("Uploading image file: " + file.getOriginalFilename());
        String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        String uploadPath = imageUploadHome + fileName;
        String publicUrl = staticDomainName + "content/" + fileName;
        try {
            file.transferTo(new File(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(publicUrl);
    }

    @PostMapping("product/bulk")
    public ResponseEntity<List<CreateResponseDTO>> createProductInBulk(@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("Uploading file: " + file.getOriginalFilename());
        List<CreateResponseDTO> responseDTOList = new ArrayList<>();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        List<CSVRecord> csvRecords = csvParser.getRecords();
        for(CSVRecord csvRecord : csvRecords){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(csvRecord.get("name"));
            productDTO.setDescription(csvRecord.get("description"));
            productDTO.setPrice(Double.valueOf(csvRecord.get("price")));
            productDTO.setActive(Boolean.valueOf(csvRecord.get("active")));
            productDTO.setStock(Integer.valueOf(csvRecord.get("stock")));
            productDTO.setCompanyId(Long.valueOf(csvRecord.get("companyId")));
            productDTO.setCategoryId(Long.valueOf(csvRecord.get("categoryId")));
            productDTO.setImageUrl(csvRecord.get("imageUrl"));

            CreateResponseDTO responseDTO = sellerService.createProduct(productDTO);

            responseDTOList.add(responseDTO);
        }
        return ResponseEntity.ok(responseDTOList);
    }
}


//CSV Stands for comma separated values

//MultipartFile is a class from spring framework that allows us to upload filesjlk;