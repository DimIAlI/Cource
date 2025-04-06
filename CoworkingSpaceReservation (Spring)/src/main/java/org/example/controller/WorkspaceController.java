package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.validation.Validator;
import org.example.dto.service.space.WorkspaceDto;
import org.example.dto.view.AddWorkspaceDto;
import org.example.dto.view.AvailableSpaceDto;
import org.example.dto.view.DeleteSpaceDto;
import org.example.service.SpaceTypeService;
import org.example.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final SpaceTypeService spaceTypeService;
    private final Validator validator;

    @GetMapping("user/spaces")
    public String getCustomerChoice(@RequestParam(name = "action") String action,
                                    RedirectAttributes redirectAttributes) {

        return switch (action) {
            case "view" -> "redirect:/user/spaces/available";
            default -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid action parameter for customer spaces");
                yield "redirect:/dashboard";
            }
        };
    }

    @GetMapping("/admin/spaces")
    public String getAdminChoice(@RequestParam(name = "action") String action,
                                 RedirectAttributes redirectAttributes) {

        return switch (action) {
            case "add" -> "redirect:/admin/spaces/add-spaces-form";
            case "remove" -> "redirect:/admin/spaces/remove-spaces-form";
            default -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid action parameter for admin spaces");
                yield "redirect:/dashboard";
            }
        };
    }

    @GetMapping("/user/spaces/available")
    public String showAvailableSpaces(Model model) {

        model.addAttribute("availableDto", new AvailableSpaceDto());

        return "workspaces/available-form";
    }

    @GetMapping("/user/spaces/available-list")
    public String showAvailableSpaces(@Valid @ModelAttribute("availableDto") AvailableSpaceDto availableSpaceDto,
                                      BindingResult result,
                                      Model model) {

        if (!validator.isStartTimeBeforeEndTime(availableSpaceDto.getStartTime(), availableSpaceDto.getEndTime())) {
            result.rejectValue("startTime", "startTime.afterEndTime", "Start time must be before end time");
        }

        if (result.hasErrors()) {
            model.addAttribute("availableDto", availableSpaceDto);
            return "workspaces/available-form";
        }

        List<WorkspaceDto> availableSpaces = workspaceService.getAvailable(availableSpaceDto);
        model.addAttribute("availableSpaces", availableSpaces);

        return "workspaces/available-list";
    }

    @GetMapping("/admin/spaces/add-spaces-form")
    public String addWorkspace(Model model) {

        setSpaceTypeAttributes(model, new AddWorkspaceDto());
        setAllSpacesAttribute(model);

        return "workspaces/add-form";
    }

    @PostMapping("/admin/spaces")
    public String addWorkspace(@Valid @ModelAttribute("addWorkspace") AddWorkspaceDto workSpaceDto,
                               BindingResult result,
                               Model model) {

        boolean typeAllowed = validator.isTypeAllowed(workSpaceDto.getType());

        if (!typeAllowed) {
            result.rejectValue("type", "type.invalid", "Invalid workspace type selected");
        }
        if (result.hasErrors()) {

            setSpaceTypeAttributes(model, workSpaceDto);
            setAllSpacesAttribute(model);

            return "workspaces/add-form";
        }

        workspaceService.add(workSpaceDto);
        return "common/success-page";
    }

    @GetMapping("/admin/spaces/remove-spaces-form")
    public String removeWorkspace(Model model) {

        model.addAttribute("deleteDto", new DeleteSpaceDto());
        setAllSpacesAttribute(model);

        return "workspaces/remove-form";
    }

    @DeleteMapping("/admin/spaces")
    public String removeWorkspace(@Valid @ModelAttribute("deleteDto") DeleteSpaceDto deleteSpaceDto,
                                  BindingResult result,
                                  Model model) {

        if (result.hasErrors()) {

            model.addAttribute("deleteDto", deleteSpaceDto);
            setAllSpacesAttribute(model);

            return "workspaces/remove-form";
        }

        workspaceService.remove(deleteSpaceDto);

        return "common/success-page";
    }

    public void setSpaceTypeAttributes(Model model, AddWorkspaceDto workspaceDto) {

        Set<String> displayNames = spaceTypeService.getValues().keySet();
        model.addAttribute("addWorkspace", workspaceDto);
        model.addAttribute("displayNames", displayNames);
    }

    public void setAllSpacesAttribute(Model model) {
        List<WorkspaceDto> allSpaces = workspaceService.getAll();
        model.addAttribute("workspaces", allSpaces);
    }
}
