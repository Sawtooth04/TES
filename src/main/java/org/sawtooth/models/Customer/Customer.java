package org.sawtooth.models.Customer;

public record Customer (int customerID, String name, String passwordHash, String email) {}
