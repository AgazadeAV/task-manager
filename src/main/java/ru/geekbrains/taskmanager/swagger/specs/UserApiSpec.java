package ru.geekbrains.taskmanager.swagger.specs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.taskmanager.dto.UserDTO;
import ru.geekbrains.taskmanager.entity.User;

import java.io.IOException;
import java.util.List;

@ParameterObject
@Tag(name = "User API")
public interface UserApiSpec {

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user and returns its details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<UserDTO> createUser(UserDTO userDTO);

    @Operation(
            summary = "Get all users",
            description = "Fetches the list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    List<UserDTO> getAllUsers();

    @Operation(
            summary = "Get a user by ID",
            description = "Fetches the details of a user by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<UserDTO> getUserById(Long id);

    @Operation(
            summary = "Update a user by ID",
            description = "Updates the details of an existing user by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<UserDTO> updateUser(Long id, UserDTO userDTO);

    @Operation(
            summary = "Delete a user by ID",
            description = "Deletes a user by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<Void> deleteUser(Long id);

    @Operation(
            summary = "Export users to JSON",
            description = "Exports the list of users to a JSON file at the specified file path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users exported successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to export users",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<String> exportUsers(String filePath) throws IOException;

    @Operation(
            summary = "Import users from JSON",
            description = "Imports the list of users from a JSON file at the specified file path.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users imported successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to import users",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<String> importUsers(String filePath) throws IOException;
}
