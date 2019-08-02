package tran.compbuildbackend.services.computerbuild;

import tran.compbuildbackend.domain.computerbuild.*;
import tran.compbuildbackend.domain.user.ApplicationUser;
import tran.compbuildbackend.exceptions.computerbuild.ComputerBuildOwnerException;
import tran.compbuildbackend.exceptions.computerbuild.ComputerDetailIdentifierException;
import tran.compbuildbackend.exceptions.request.GenericRequestException;
import tran.compbuildbackend.repositories.computerbuild.ComputerBuildRepository;
import tran.compbuildbackend.services.security.utility.SecurityUtil;

import java.util.List;

import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.*;

public class ComputerBuildServiceUtility {

    public static ComputerBuild getComputerBuildByBuildId(ComputerBuildRepository computerBuildRepository, String buildIdentifier) {
        ComputerBuild computerBuild = computerBuildRepository.getComputerBuildByBuildIdentifier(buildIdentifier);
        if(computerBuild == null) {
            throw new GenericRequestException(COMPUTER_BUILD_DOES_NOT_EXIST);
        }
        if(computerBuild.getId() == null || computerBuild.getUser() == null) {
            throw new GenericRequestException(COMPUTER_BUILD_DOES_NOT_EXIST);
        }
        return computerBuild;
    }

    public static ComputerBuild verifyOwnerOfComputerBuild(ComputerBuildRepository computerBuildRepository, String buildIdentifier) {
        ApplicationUser user = SecurityUtil.getLoggedInUser();

        ComputerBuild oldBuild = getComputerBuildByBuildId(computerBuildRepository, buildIdentifier);

        if(oldBuild.getUser().getUsername().equals(user.getUsername())) {
            return oldBuild;
        }
        throw new ComputerBuildOwnerException(COMPUTER_BUILD_CANNOT_BE_MODIFIED);
    }

    /**
     * @param prefix The prefix based off the computer detail this string is a part of ('dir' for direction,
     *               'ocn' for overclockingnote, 'pur' for purpose, 'bn' for buildnote, or 'cp' for computer part).
     * @param computerBuild The computer build object to be able check the number of directions or overclocking notes, etc.
     * @return The unique identifier for a computer build detail.
     */
    public static String generateComputerBuildDetail(String prefix, ComputerBuild computerBuild) {
        StringBuilder uniqueIdentifier = new StringBuilder();
        String buildIdentifier = computerBuild.getBuildIdentifier();
        uniqueIdentifier.append(buildIdentifier);
        uniqueIdentifier.append(DETAIL_IDENTIFIER_SEPARATOR);
        switch(prefix) {
            case DIRECTION_ABBREVIATION:
                List<Direction> directions = computerBuild.getDirections();
                uniqueIdentifier.append(DIRECTION_ABBREVIATION);
                uniqueIdentifier.append(DETAIL_IDENTIFIER_SEPARATOR);
                uniqueIdentifier.append(directions.size() + 1);
                break;
            case COMPUTER_PART_ABBREVIATION:
                List<ComputerPart> computerParts = computerBuild.getComputerParts();
                uniqueIdentifier.append(COMPUTER_PART_ABBREVIATION);
                uniqueIdentifier.append(DETAIL_IDENTIFIER_SEPARATOR);
                uniqueIdentifier.append(computerParts.size() + 1);
                break;
            case PURPOSE_ABBREVIATION:
                List<Purpose> purposeList = computerBuild.getPurposeList();
                uniqueIdentifier.append(PURPOSE_ABBREVIATION);
                uniqueIdentifier.append(DETAIL_IDENTIFIER_SEPARATOR);
                uniqueIdentifier.append(purposeList.size() + 1);
                break;
            case OVERCLOCKING_NOTE_ABBREVIATION:
                List<OverclockingNote> overclockingNotes = computerBuild.getOverclockingNotes();
                uniqueIdentifier.append(OVERCLOCKING_NOTE_ABBREVIATION);
                uniqueIdentifier.append(DETAIL_IDENTIFIER_SEPARATOR);
                uniqueIdentifier.append(overclockingNotes.size() + 1);
                break;
            case BUILD_NOTE_ABBREVIATION:
                List<BuildNote> buildNotes = computerBuild.getBuildNotes();
                uniqueIdentifier.append(BUILD_NOTE_ABBREVIATION);
                uniqueIdentifier.append(DETAIL_IDENTIFIER_SEPARATOR);
                uniqueIdentifier.append(buildNotes.size() + 1);
                break;
            default:
                break;
        }
        return uniqueIdentifier.toString();
    }

    public static void setAbstractNote(ComputerBuild computerBuild, AbstractNote note, String noteAbbreviation) {
        if (note.getPriority() < LOWEST_PRIORITY || note.getPriority() > HIGHEST_PRIORITY) {
            note.setPriority(DEFAULT_PRIORITY);
        }
        note.setUniqueIdentifier(generateComputerBuildDetail(noteAbbreviation, computerBuild));
        note.setComputerBuild(computerBuild);
    }

    public static ComputerBuild verifyComputerDetailOwner(String uniqueIdentifier, ComputerBuildRepository computerBuildRepository) {
        String[] buildIdentifier = uniqueIdentifier.split("-");

        if(buildIdentifier.length != 3) {
            throw new ComputerDetailIdentifierException("invalid unique identifier format.");
        }

        return verifyOwnerOfComputerBuild(computerBuildRepository, buildIdentifier[0]);
    }

}
