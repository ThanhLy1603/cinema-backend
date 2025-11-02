package com.example.backend.init;

public interface EntityInitialize {
    public void initializeRoles();
    public void initializeUsersAndUserRoles();
    public void initializeUserProfiles();
    public void initializeCategories();
    public void initializeFilmsAndFilmCategories();
    public void initializeSeatType();
    public void initializeRooms();
    public void initializeShowTimes();
    public void initializeSeats();
    public void initializeSchedules();
}
