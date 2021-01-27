package pl.edu.pjwstk.jaz.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.entities.AuctionView;
import pl.edu.pjwstk.jaz.requests.AuctionRequest;
import pl.edu.pjwstk.jaz.services.AuctionService;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AuctionController {
    AuctionService auctionService;


    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/auctions")
    @Transactional
    public void createAuction(@RequestBody @Valid AuctionRequest auctionRequest, HttpServletResponse response){
        auctionService.createAuction(auctionRequest, response);
    }

    @PutMapping("/auctions/{auctionId}")
    @Transactional
    public void editAuction(@PathVariable("auctionId") Long auctionId, @RequestBody @Validated AuctionRequest auctionRequest, HttpServletResponse response){
        auctionService.editAuction(auctionId,auctionRequest, response);
    }

    @GetMapping("/auctions/{auctionID}")
    public AuctionView viewOneAuction(@PathVariable("auctionID") Long auctionId, HttpServletResponse response){
        return auctionService.viewAuction(auctionId, response);
    }

    @GetMapping("/auctions")
    public List<AuctionView> viewAllAuctions(HttpServletResponse response){
        return auctionService.viewAllAuctions(response);
    }
}
