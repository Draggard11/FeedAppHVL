package no.hvl.dat250.feedapphvl.dtos;

public record NewUserRequest(
        String username,
        String email,
        String password
) {
}