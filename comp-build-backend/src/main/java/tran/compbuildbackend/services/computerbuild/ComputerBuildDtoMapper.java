package tran.compbuildbackend.services.computerbuild;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tran.compbuildbackend.domain.computerbuild.ComputerBuild;
import tran.compbuildbackend.dto.computerbuild.ComputerBuildDto;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that provides methods to map a collection of ComputerBuild objects to ComputerBuildDto objects and
 * a ComputerBuild object to a ComputerBuildDto.
 */
@Component
public class ComputerBuildDtoMapper {

    private ModelMapper modelMapper;

    public ComputerBuildDtoMapper() {
        modelMapper = new ModelMapper();
    }

    public ComputerBuildDto computerBuildToComputerBuildDto(ComputerBuild computerBuild) {
        ComputerBuildDto computerBuildDto = modelMapper.map(computerBuild, ComputerBuildDto.class);
        computerBuildDto.setUsername(computerBuild.getUser().getUsername());
        return computerBuildDto;
    }

    public Iterable<ComputerBuildDto> computerBuildsToComputerBuildDtos(Iterable<ComputerBuild> computerBuilds) {
        List<ComputerBuildDto> computerBuildDtos = new LinkedList<>();
        computerBuilds.forEach(computerBuild -> {
            ComputerBuildDto computerBuildDto = modelMapper.map(computerBuild, ComputerBuildDto.class);
            computerBuildDto.setUsername(computerBuild.getUser().getUsername());
            computerBuildDtos.add(computerBuildDto);
        });
        return computerBuildDtos;
    }
}
