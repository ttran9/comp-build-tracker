package tran.compbuildbackend.controllers.computerbuild;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.mapper.ComputerBuildToComputerBuildResponse;
import tran.compbuildbackend.payload.computerbuild.ComputerBuildResponse;
import tran.compbuildbackend.services.computerbuild.ComputerBuildService;
import tran.compbuildbackend.validator.MapValidationErrorService;

import javax.validation.Valid;

import static tran.compbuildbackend.constants.mapping.MappingConstants.*;

@RestController
@RequestMapping(COMPUTER_BUILD_API)
public class ComputerBuildController {

    private MapValidationErrorService mapValidationErrorService;

    private ComputerBuildService computerBuildService;

    public ComputerBuildController(MapValidationErrorService mapValidationErrorService, ComputerBuildService computerBuildService) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.computerBuildService = computerBuildService;
    }

    @PostMapping
    public ResponseEntity<?> createComputerBuild(@RequestBody @Valid ComputerBuild computerBuild, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        ComputerBuild newComputerBuild = computerBuildService.createNewComputerBuild(computerBuild);
        ComputerBuildResponse response = ComputerBuildToComputerBuildResponse.computerBuildToComputerBuildResponseNameAndBuildIdentifier
                (newComputerBuild.getName(), newComputerBuild.getBuildIdentifier());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PatchMapping(UPDATE_OBJECT + BUILD_IDENTIFIER_PATH_VARIABLE)
//    public ResponseEntity<?> updateNumberOfDirectionsInBuild(@RequestBody @Valid ComputerBuild computerBuild,
//                                                             @PathVariable String buildIdentifier,
//                                                             BindingResult bindingResult) {
//
//        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
//        if(errorMap != null) return errorMap;
//
//        computerBuildService.updateNumberOfDirections(computerBuild, buildIdentifier);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping(BUILD_IDENTIFIER_PATH_VARIABLE)
    public ResponseEntity<?> getComputerBuildByIdentifier(@PathVariable String buildIdentifier) {
        ComputerBuild computerBuild = computerBuildService.getComputerBuildByBuildIdentifier(buildIdentifier);

        return new ResponseEntity<>(computerBuild, HttpStatus.FOUND);
    }

    @DeleteMapping(BUILD_IDENTIFIER_PATH_VARIABLE)
    public ResponseEntity<?> deleteComputerBuild(@PathVariable String buildIdentifier) {
        computerBuildService.deleteComputerBuild(buildIdentifier);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public Iterable<ComputerBuild> getAllComputerBuilds() {
        return computerBuildService.getAllComputerBuilds();
    }

    @GetMapping(USER_NAME_REQUEST + USER_NAME_PATH_VARIABLE)
    public Iterable<ComputerBuild> getAllComputerBuildsByUser(@PathVariable String username) {
        return computerBuildService.getAllComputerBuildsFromUser(username);
    }
}
