package Geeks.languagecenterapp.Controller;

import Geeks.languagecenterapp.DTO.Request.BookRequestBody;
import Geeks.languagecenterapp.DTO.Request.PlacementTestRequestBody;
import Geeks.languagecenterapp.Model.PlacementTestEntity;
import Geeks.languagecenterapp.Service.PlacementTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/placementTest")
public class PlacementTestController {
    @Autowired
    private PlacementTestService placementTestService;
    //Create placement test
    @PostMapping("/add")
    public ResponseEntity addPlacementTest(@RequestBody PlacementTestRequestBody body){
    return placementTestService.add(body);
    }
    //update placement test
    @PostMapping("/update/{id}")
    public ResponseEntity updatePlacementTest(@PathVariable("id") int id ,@RequestBody PlacementTestRequestBody body){
        return placementTestService.update(body, id);
    }
    //delete placement test
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePlacementTest(@PathVariable("id") int id ){
        return placementTestService.delete(id);
    }
    //get All placement test
    @GetMapping("/get/all")
    public List<PlacementTestEntity> getAllPlacementTest(){
        return placementTestService.getAll();
    }
    //get All placement test filtered by language
    @GetMapping("/get/lan")
    public List<PlacementTestEntity> getAllPlacementTestByLanguage(@RequestParam String lan){
        return placementTestService.getAllByLanguage(lan);
    }
    //get All placement test filtered by max num
    @GetMapping("/get/num")
    public List<PlacementTestEntity> getAllPlacementTestByMaxNum(@RequestParam int num){
        return placementTestService.getAllByMaxNum(num);
    }
    @PostMapping("/book/{placementId}")
    public ResponseEntity bookPlacementTest(@PathVariable("placementId") int id ,@RequestBody BookRequestBody body){
        return placementTestService.book(body,id);
    }



}
