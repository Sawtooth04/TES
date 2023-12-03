package org.sawtooth.models.customer;

public record Customer (int customerID, String name, String passwordHash, String email, int roleID, boolean verified) {}
