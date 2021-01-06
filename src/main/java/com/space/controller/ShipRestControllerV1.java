package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/ships")
public class ShipRestControllerV1 {

    @Autowired
    private ShipService shipService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        Ship ship;
        if(id<=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!shipService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            ship = shipService.findById(id);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ship> createNewShip(@RequestBody Ship ship) {
        try{
            shipService.addNewShip(ship);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id,@RequestBody Ship ship) {

        if(id <= 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!shipService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            return new ResponseEntity<>(shipService.updateShip(id, ship), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        if(id <= 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!shipService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            shipService.deleteShip(id);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ship>> getAllShips(
            @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        List<Ship> shipsList = shipService.getAllShips(
                Specification.where(shipService.filterShipsByName(name)
                        .and(shipService.filterShipsByPlanet(planet)))
                        .and(shipService.filterShipsByShipType(shipType))
                        .and(shipService.filterShipsByDate(after, before))
                        .and(shipService.filterShipsByUsage(isUsed))
                        .and(shipService.filterShipsBySpeed(minSpeed, maxSpeed))
                        .and(shipService.filterShipsByCrewSize(minCrewSize, maxCrewSize))
                        .and(shipService.filterShipsByRating(minRating, maxRating)), pageable).getContent();


        if(shipsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shipsList, HttpStatus.OK);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> showShipsCount(@RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "planet", required = false) String planet,
                                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                                  @RequestParam(value = "after", required = false) Long after,
                                                  @RequestParam(value = "before", required = false) Long before,
                                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                                  @RequestParam(value = "maxRating", required = false) Double maxRating){
        Integer shipsCount = shipService.getAllShips(
                Specification.where(shipService.filterShipsByName(name)
                        .and(shipService.filterShipsByPlanet(planet)))
                        .and(shipService.filterShipsByShipType(shipType))
                        .and(shipService.filterShipsByDate(after, before))
                        .and(shipService.filterShipsByUsage(isUsed))
                        .and(shipService.filterShipsBySpeed(minSpeed, maxSpeed))
                        .and(shipService.filterShipsByCrewSize(minCrewSize, maxCrewSize))
                        .and(shipService.filterShipsByRating(minRating, maxRating)),
                Pageable.unpaged()).getNumberOfElements();
        return new ResponseEntity<>(shipsCount, HttpStatus.OK);
    }



}
