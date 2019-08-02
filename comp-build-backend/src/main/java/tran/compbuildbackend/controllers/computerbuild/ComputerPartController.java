package tran.compbuildbackend.controllers.computerbuild;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tran.compbuildbackend.domain.computerbuild.ComputerPart;
import tran.compbuildbackend.payload.computerbuild.ComputerBuildDetailResponse;
import tran.compbuildbackend.services.computerbuild.ComputerPartService;
import tran.compbuildbackend.validator.MapValidationErrorService;

import javax.validation.Valid;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.COMPUTER_PART_DELETE_MESSAGE;
import static tran.compbuildbackend.constants.mapping.MappingConstants.*;

@RestController
@RequestMapping(COMPUTER_PART_API)
public class ComputerPartController {

    private ComputerPartService computerPartService;

    private MapValidationErrorService mapValidationErrorService;

    public ComputerPartController(ComputerPartService computerPartService, MapValidationErrorService mapValidationErrorService) {
        this.computerPartService = computerPartService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping(BUILD_IDENTIFIER_PATH_VARIABLE)
    public ResponseEntity<?> createComputerPart(@Valid @RequestBody ComputerPart computerPart, BindingResult bindingResult,
                                                @PathVariable String buildIdentifier) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);

        if(errorMap != null) return errorMap;

        // no errors so create the computer part.
        ComputerPart createdComputerPart = computerPartService.create(buildIdentifier, computerPart);

        return new ResponseEntity<>(createdComputerPart, HttpStatus.CREATED);
    }

    @PatchMapping(BUILD_IDENTIFIER_PATH_VARIABLE + URL_SEPARATOR + UNIQUE_IDENTIFIER_PATH_VARIABLE)
    public ResponseEntity<?> updateComputerPart(@Valid @RequestBody ComputerPart newComputerPart, BindingResult bindingResult,
                                                    @PathVariable String uniqueIdentifier, @PathVariable String buildIdentifier) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);

        if(errorMap != null) return errorMap;

        // no errors so update the computer part.
        ComputerPart updatedComputerPart = computerPartService.update(newComputerPart, uniqueIdentifier);
        return new ResponseEntity<>(updatedComputerPart, HttpStatus.OK);
    }

    @DeleteMapping(BUILD_IDENTIFIER_PATH_VARIABLE + URL_SEPARATOR + UNIQUE_IDENTIFIER_PATH_VARIABLE)
    public ResponseEntity<?> deleteComputerPart(@PathVariable String uniqueIdentifier, @PathVariable String buildIdentifier) {
        computerPartService.delete(uniqueIdentifier);

        // no errors at this point with deletion so return a success message.
        return new ResponseEntity<>(new ComputerBuildDetailResponse(COMPUTER_PART_DELETE_MESSAGE), HttpStatus.OK);
    }

    @GetMapping(BUILD_IDENTIFIER_PATH_VARIABLE + URL_SEPARATOR + UNIQUE_IDENTIFIER_PATH_VARIABLE)
    public ResponseEntity<?> getComputerPartByIdentifier(@PathVariable String uniqueIdentifier, @PathVariable String buildIdentifier) {
        // get the computer part and return its contents in the response.
        ComputerPart computerPart = computerPartService.getFromUniqueIdentifier(uniqueIdentifier);

        return new ResponseEntity<>(computerPart, HttpStatus.OK);
    }
}
