package com.airgear.airgeardailyreport.service;


import com.airgear.airgeardailyreport.model.goods.Category;
import com.airgear.airgeardailyreport.repository.CategoryRepository;
import com.airgear.airgeardailyreport.repository.GoodsRepository;
import com.airgear.airgeardailyreport.repository.MessageRepository;
import com.airgear.airgeardailyreport.repository.UserRepository;
import com.airgear.airgeardailyreport.util.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.airgear.airgeardailyreport.exeption.PdfException.createPdfException;

@Slf4j
@Service
public class PdfProcessorService {

    private Util util;
    private GoodsRepository goodsRepository;
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public PdfProcessorService(Util util, GoodsRepository goodsRepository, UserRepository userRepository,
                               MessageRepository messageRepository, CategoryRepository categoryRepository) {
        this.util = util;
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createDailyStatReport(int days) {
        String folderName = "Daily Statistics Report";
        util.createFolder(folderName);

        Long countNewGoodsInDay = goodsRepository.countByCreatedAtBetween(OffsetDateTime.now().minusDays(days), OffsetDateTime.now());
        Long countByCreatedAtBetween = userRepository.countByCreatedAtBetween(OffsetDateTime.now().minusDays(days), OffsetDateTime.now());
        Long countByDeleteAtBetween = userRepository.countByDeleteAtBetween(OffsetDateTime.now().minusDays(days), OffsetDateTime.now());
        Long countMessage = messageRepository.countBySendAtBetween(OffsetDateTime.now().minusDays(days), OffsetDateTime.now());
        Map<String, Long> categoriesMap = getCategories(days);

        writePdfFile(countNewGoodsInDay, countByCreatedAtBetween, countByDeleteAtBetween, countMessage, categoriesMap, OffsetDateTime.now(), folderName);
    }

    public Map<String, Long> getCategories(int days) {
        Map<String, Long> categoriesMap = new HashMap<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            Long countNewGoodsByCategoryInDay = goodsRepository.countNewGoodsByCategoryInPeriod(category.getId(), OffsetDateTime.now().minusDays(days), OffsetDateTime.now());
            categoriesMap.put(category.getName(), countNewGoodsByCategoryInDay);
        }
        return categoriesMap;
    }

    public void writePdfFile(Long countNewGoodsInDay, Long countByCreatedAtBetween, Long countByDeleteAtBetween,
                             Long countMessage, Map<String, Long> categories, OffsetDateTime currentDate, String folderName) {

        DateTimeFormatter dateFormatWithTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter dateFormatForPath = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH.mm.ss");
        String path = folderName + File.separator + "DailyStatisticsReport_" + currentDate.format(dateFormatForPath) + ".pdf";
        String fileName = "DailyStatisticsReport_" + currentDate.format(dateFormatWithTime) + ".pdf";
        Document document = new Document();

        com.itextpdf.text.List list = new com.itextpdf.text.List();
        list.setListSymbol(" • ");

        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(new Paragraph("Daily Statistics Report"));
            document.add(new Paragraph("Date: " + currentDate.format(dateFormatWithTime)));
            document.add(new Paragraph("Number of new users registered per day: " + countByCreatedAtBetween));
            document.add(new Paragraph("Number of users deleted per day: " + countByDeleteAtBetween));
            document.add(new Paragraph("Number of new goods created per day: " + countMessage));
            document.add(new Paragraph("Number of new goods created per day: " + countNewGoodsInDay));
            document.add(new Paragraph("Number of new goods created per day by category:"));
            categories.forEach((key, value) -> list.add(key.replace("_", " ").toLowerCase() + ": " + value));
            document.add(list);

            document.close();
            log.info("PDF file " + fileName + " created successfully.");
        } catch (Exception e) {
            throw createPdfException(fileName);
        }
    }
}
