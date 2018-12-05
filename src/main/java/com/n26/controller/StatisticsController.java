package com.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.entity.response.Statistic;
import com.n26.service.StatisticService;

/**
 * The Class StatisticsController.
 */
@RestController
@RequestMapping ( value = "/statistics" )
public class StatisticsController {

    /** The statistic service. */
    @Autowired
    private StatisticService statisticService;

    /**
     * Find.
     *
     * @return the response entity
     */
    @RequestMapping ( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Statistic> getStatistics() {

        Statistic statistics = statisticService.getStatistics();

        ResponseEntity<Statistic> response = new ResponseEntity<Statistic>( statistics, HttpStatus.OK );

        return response;
    }

}
