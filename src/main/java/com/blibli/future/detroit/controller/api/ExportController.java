package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.service.StatisticService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ExportController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/statistic";
    public static final String GET_DATA_EXPORT = BASE_PATH + "/export-data";

    @Autowired
    StatisticService statisticService;

    @GetMapping(GET_DATA_EXPORT)
    public void getExportData(HttpServletResponse response) {
        try {
            byte[] content = statisticService.exportData();
            response.getOutputStream().write(content);
            response.setHeader("Content-Disposition", "attachment; filename=\"Statistic.xlsx\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
