package xyz.raichu.diplom.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.raichu.diplom.entity.File;
import xyz.raichu.diplom.model.DocumentPosition;
import xyz.raichu.diplom.exception.NotFoundException;
import xyz.raichu.diplom.service.PdfService;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 06.05.2021
 * xyz.raichu.diplom.controller.RaichuController
 * 18:41
 */
@RestController
@RequestMapping("/word")
@Slf4j
public class RaichuController {

    private final PdfService pdfService;

    public RaichuController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File getWordPos(@RequestParam("word") String word, @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        var split = word.split(",");
        Set<String> filtered = new HashSet<>();
        for (String s : split) {
            if(StringUtils.hasText(s)){
               filtered.add(s.trim());
            }
        }
        var position = pdfService.findFordPosition(file.getInputStream(), filtered, file.getOriginalFilename());
        log.info("got search reqeust from: {}", principal.getName());
//        if (position.isEmpty()) throw new NotFoundException("Word not found");

        return position;
    }
}
